package com.excellence.filetools.utils;

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2017/9/6
 *     desc   :
 * </pre>
 */

public class CommonUtil
{
	public static short bytes2Short(byte[] buffer, int offset)
	{
		short value;
		value = (short) (((buffer[offset] & 0xFF) << 8) | (buffer[offset + 1] & 0xFF));
		return value;
	}

	public static byte[] shortToByte(short value)
	{
		byte[] bytes = new byte[2];
		for (int i = 0; i < bytes.length; i++)
		{
			int offset = (bytes.length - 1 - i) * 8;
			bytes[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return bytes;
	}

	/**
	 * byte 转二进制
	 *
	 * @param b
	 * @return
	 */
	public static String byte2BinStr(byte b)
	{
		String result = "";
		byte a = b;
		for (int i = 0; i < 8; i++)
		{
			byte c = a;
			a = (byte) (a >> 1);// 每移一位如同将10进制数除以2并去掉余数。
			a = (byte) (a << 1);
			if (a == c)
			{
				result = "0" + result;
			}
			else
			{
				result = "1" + result;
			}
			a = (byte) (a >> 1);
		}
		return result;
	}

	public static String byte2BinStr(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
		{
			sb.append(byte2BinStr(bytes[i]));
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 字符串转二进制
	 *
	 * @param str
	 * @return
	 */
	public static String str2Binstr(String str)
	{
		char[] strChar = str.toCharArray();
		String result = "";
		for (int i = 0; i < strChar.length; i++)
		{
			String binStr = Integer.toBinaryString(strChar[i]);
			result += String.format("%08d", Integer.valueOf(binStr)) + " ";
		}
		return result;
	}

	/**
	 * 二进制转十六进制
	 *
	 * @param bString
	 * @return
	 */
	public static String binaryString2hexString(String bString)
	{
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuilder tmp = new StringBuilder();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4)
		{
			iTmp = 0;
			for (int j = 0; j < 4; j++)
			{
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	// 将二进制字符串转换成Unicode字符串
	public static String binstrToStr(String binStr)
	{
		String[] tempStr = StrToStrArray(binStr);
		char[] tempChar = new char[tempStr.length];
		for (int i = 0; i < tempStr.length; i++)
		{
			tempChar[i] = binstrToChar(tempStr[i]);
		}
		return String.valueOf(tempChar);
	}

	// 将初始二进制字符串转换成字符串数组，以空格相隔
	public static String[] StrToStrArray(String str)
	{
		return str.split(" ");
	}

	// 将二进制字符串转换为char
	public static char binstrToChar(String binStr)
	{
		int[] temp = binstrToIntArray(binStr);
		int sum = 0;
		for (int i = 0; i < temp.length; i++)
		{
			sum += temp[temp.length - 1 - i] << i;
		}
		return (char) sum;
	}

	// 将二进制字符串转换成int数组
	public static int[] binstrToIntArray(String binStr)
	{
		char[] temp = binStr.toCharArray();
		int[] result = new int[temp.length];
		for (int i = 0; i < temp.length; i++)
		{
			result[i] = temp[i] - 48;
		}
		return result;
	}

	/**
	 * http://www.cnblogs.com/SharkBin/p/4250596.html
	 *
	 * @param b
	 * @param size
	 * @return
	 */
	public static String bytes2HexString(byte[] b, int size)
	{
		if (b == null || b.length <= 0)
		{
			return null;
		}
		StringBuilder result = new StringBuilder();
		String hex;
		for (int i = 0; i < size; i++)
		{
			hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			result.append(hex.toUpperCase());
		}
		return result.toString();
	}

	/**
	 * 16进制字符串转byte
	 *
	 * @param src
	 * @return
	 */
	public static byte[] hexString2Bytes(String src)
	{
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++)
		{
			ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return ret;
	}

	/**
	 * 字符串转16进制
	 *
	 * @param strPart
	 * @return
	 */
	public static String string2HexString(String strPart)
	{
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < strPart.length(); i++)
		{
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			hexString.append(strHex);
		}
		return hexString.toString();
	}

	/**
	 * 16进制转字符串
	 *
	 * @param src
	 * @return
	 */
	public static String hexString2String(String src)
	{
		String temp = "";
		for (int i = 0; i < src.length() / 2; i++)
		{
			temp = temp + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return temp;
	}

	/**
	 * 字符串转byte数组
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] string2Bytes(String src)
	{
		String hexStr = string2HexString(src);
		return hexString2Bytes(hexStr);
	}

	/**
	 * byte数组转字符串（有乱码）
	 *
	 * @param bytes
	 * @param length
	 * @return
	 */
	public static String bytes2String(byte[] bytes, int length)
	{
		String hexStr = bytes2HexString(bytes, length);
		return hexString2String(hexStr);
	}

	/**
	 * byte数组转有符号int
	 *
	 * @param b
	 * @return
	 */
	public static long byte2Int(byte[] b)
	{
		return ((b[0] & 0xff) << 24) | ((b[1] & 0xff) << 16) | ((b[2] & 0xff) << 8) | (b[3] & 0xff);
	}

	/**
	 * int转4位byte数组
	 *
	 * @param n
	 * @return
	 */
	public static byte[] int2Byte(int n)
	{
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	/**
	 * 四字节byte数组转无符号long
	 *
	 * @param res
	 * @return
	 */
	public static long unintbyte2long(byte[] res)
	{
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		int index = 0;
		firstByte = (0x000000FF & ((int) res[index]));
		secondByte = (0x000000FF & ((int) res[index + 1]));
		thirdByte = (0x000000FF & ((int) res[index + 2]));
		fourthByte = (0x000000FF & ((int) res[index + 3]));
		index = index + 4;
		return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
	}
}
