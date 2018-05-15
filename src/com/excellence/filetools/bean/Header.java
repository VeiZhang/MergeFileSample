package com.excellence.filetools.bean;

import com.excellence.filetools.utils.Constants.TYPE;

import java.util.List;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2018/4/19
 *     desc   :
 * </pre>
 */

public class Header
{

	private List<HeaderInfoListBean> headerInfoList;

	public List<HeaderInfoListBean> getHeaderInfoList()
	{
		return headerInfoList;
	}

	public void setHeaderInfoList(List<HeaderInfoListBean> headerInfoList)
	{
		this.headerInfoList = headerInfoList;
	}

	public static class HeaderInfoListBean
	{
		/**
		 * type : TV
		 * len : 2951352
		 * isUpgrade : true
		 * version : 999
		 */

		private TYPE type;
		private long len;
		private boolean isUpgrade;
		private int version;

		public HeaderInfoListBean(TYPE type, long len, boolean isUpgrade, int version)
		{
			this.type = type;
			this.len = len;
			this.isUpgrade = isUpgrade;
			this.version = version;
		}

		public TYPE getType()
		{
			return type;
		}

		public void setType(TYPE type)
		{
			this.type = type;
		}

		public long getLen()
		{
			return len;
		}

		public void setLen(long len)
		{
			this.len = len;
		}

		public boolean isIsUpgrade()
		{
			return isUpgrade;
		}

		public void setIsUpgrade(boolean isUpgrade)
		{
			this.isUpgrade = isUpgrade;
		}

		public int getVersion()
		{
			return version;
		}

		public void setVersion(int version)
		{
			this.version = version;
		}
	}
}
