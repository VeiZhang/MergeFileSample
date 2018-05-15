package com.excellence.filetools.utils;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/4/19
 *     desc   : 256字节的固定头，最后一位校验
 *              Header + TV + OTT
 *              <p>
 *                  软件类型:TV、OTT
 *                  软件文件长度Len
 *                  是否更新:是、否
 *              </p>
 * </pre>
 */

public class Constants
{
	public static final String MERGE = "merge";
	public static final String SUFFIX_BIN = ".bin".toLowerCase();

	public static final byte BYTE = 0;

	public static final int HEADER_LEN = 256;
	public static final int BUF_LEN = 6 * 1024;

	public enum TYPE
	{
		TV, OTT
	}

}
