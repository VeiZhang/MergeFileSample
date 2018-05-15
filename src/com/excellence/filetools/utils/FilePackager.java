package com.excellence.filetools.utils;

import com.excellence.filetools.bean.Header;
import com.excellence.filetools.bean.Header.HeaderInfoListBean;
import com.excellence.filetools.utils.Constants.TYPE;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import static com.excellence.filetools.utils.Constants.BUF_LEN;
import static com.excellence.filetools.utils.Constants.BYTE;
import static com.excellence.filetools.utils.Constants.HEADER_LEN;
import static com.excellence.filetools.utils.Constants.MERGE;
import static com.excellence.filetools.utils.Constants.SUFFIX_BIN;
import static com.excellence.filetools.utils.Constants.TYPE.OTT;
import static com.excellence.filetools.utils.Constants.TYPE.TV;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/5/15
 *     desc   :
 * </pre>
 */

public class FilePackager
{
	private File mOttFile = null;
	private String mOttVersion = null;
	private File mTvFile = null;
	private String mTvVersion = null;
	private File mMergeFile = null;
	private int mMergeVersion = 0;

	public FilePackager(String ottFilePath, String ottVersion, String tvFilePath, String tvVersion)
	{
		mOttFile = new File(ottFilePath);
		mOttVersion = ottVersion;
		mTvFile = new File(tvFilePath);
		mTvVersion = tvVersion;
	}

	public void packMergeFile() throws Exception
	{
		if (!mOttFile.exists() && !mTvFile.exists())
		{
			throw new Exception("OTT和TV更新文件都不存在");
		}

		String dir = System.getProperty("user.dir");
		mMergeFile = new File(dir, generateMergeFile());
		if (mMergeFile.exists())
		{
			mMergeFile.delete();
		}

		RandomAccessFile accessFile = new RandomAccessFile(mMergeFile, "rw");
		Header header = new Header();
		List<HeaderInfoListBean> headerInfoList = new ArrayList<HeaderInfoListBean>();
		/**
		 * 优先保存OTT软件，方便{@link java.nio.channels.FileChannel#truncate(long)}：保留前多少byte的文件
		 */
		saveUpgradeFile(headerInfoList, OTT, mOttFile, mOttVersion, accessFile);
		saveUpgradeFile(headerInfoList, TV, mTvFile, mTvVersion, accessFile);
		header.setHeaderInfoList(headerInfoList);

		/**
		 * 结尾存入头信息，方便保证OTT软件
		 */
		writeHeader(accessFile, header);

		accessFile.close();
	}

	private void writeHeader(RandomAccessFile accessFile, Header header) throws Exception
	{
		String headerStr = new Gson().toJson(header);
		System.out.println(headerStr);
		byte[] headerBytes = CommonUtil.string2Bytes(headerStr);
		if (headerBytes.length > HEADER_LEN - 1)
		{
			throw new Exception("Header is error");
		}

		byte[] fileBytes = new byte[HEADER_LEN];
		for (int i = 0; i < fileBytes.length - 1; i++)
		{
			if (i < headerBytes.length)
			{
				fileBytes[i] = headerBytes[i];
			}
			else
			{
				fileBytes[i] = BYTE;
			}

			fileBytes[fileBytes.length - 1] ^= fileBytes[i];
		}
		/**
		 * 测试：读bytes
		 */
		readHeaderInfo(fileBytes);
		accessFile.write(fileBytes);
	}

	private String readHeaderInfo(byte[] fileBytes)
	{
		int crc = 0;
		int pos = -1;
		for (int i = 0; i < fileBytes.length - 1; i++)
		{
			if (pos == -1 && fileBytes[i] == BYTE)
			{
				pos = i;
			}
			crc ^= fileBytes[i];
		}
		if (crc != fileBytes[fileBytes.length - 1])
		{
			System.out.println("Error CRC");
			return null;
		}

		/**
		 * 因为第pos的位置是0，所以长度不能是pos，而是pos-1
		 */
		byte[] headerInfo = new byte[pos];
		for (int i = 0; i < headerInfo.length; i++)
		{
			headerInfo[i] = fileBytes[i];
		}
		String headerStr = CommonUtil.bytes2String(headerInfo, headerInfo.length);
		System.out.println("============");
		System.out.println(headerStr);
		System.out.println("============");
		return headerStr;
	}

	private void saveUpgradeFile(List<HeaderInfoListBean> headerInfoList, TYPE type, File upgradeFile, String version, RandomAccessFile accessFile) throws Exception
	{
		if (!upgradeFile.exists())
		{
			return;
		}
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(upgradeFile));
		int len = 0;
		byte[] buf = new byte[BUF_LEN];
		while ((len = (is.read(buf))) != -1)
		{
			accessFile.write(buf, 0, len);
		}
		is.close();
		headerInfoList.add(new HeaderInfoListBean(type, upgradeFile.length(), upgradeFile.exists(), Integer.parseInt(version)));
	}

	private String generateMergeFile() throws Exception
	{
		String mergePath = null;
		mMergeVersion = 0;
		if (mOttFile.exists() && mTvFile.exists())
		{
			if (isEmpty(mTvVersion) && isEmpty(mOttVersion))
			{
				throw new Exception("OTT版本和TV版本必填");
			}
			mMergeVersion = Integer.parseInt(Integer.parseInt(mTvVersion) > Integer.parseInt(mOttVersion) ? mTvVersion : mOttVersion);
		}
		else if (mTvFile.exists())
		{
			if (isEmpty(mTvVersion))
			{
				throw new Exception("TV版本必填");
			}
			mMergeVersion = Integer.parseInt(mTvVersion);
		}
		else if (mOttFile.exists())
		{
			if (isEmpty(mOttVersion))
			{
				throw new Exception("OTT版本必填");
			}
			mMergeVersion = Integer.parseInt(mOttVersion);
		}
		mergePath = MERGE + "_" + mMergeVersion + SUFFIX_BIN;
		return mergePath;
	}

	public File getMergeFilePath()
	{
		return mMergeFile;
	}

	public int getMergeVersion()
	{
		return mMergeVersion;
	}

	public static boolean isEmpty(String string)
	{
		return string == null || string.isEmpty();
	}

    public void deleteTempFile() {
        if (mMergeFile != null && mMergeFile.exists())
            mMergeFile.delete();
    }
}
