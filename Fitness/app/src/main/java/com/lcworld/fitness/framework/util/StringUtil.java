package com.lcworld.fitness.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断字符串是否为null或�?空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = false;
		if (null == str || "".equals(str.trim())) {
			result = true;
		}
		return result;
	}

	/**
	 * 判断字符串是否6位数字（邮编）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPostCode(String str) {
		boolean result = false;
		Pattern p = Pattern.compile("^\\d{6}$");
		Matcher m = p.matcher(str);
		if (m.matches()) {
			result = true;
		}
		return result;

	}

	/**
	 * 如果i小于10，添�?后生成string
	 * 
	 * @param i
	 * @return
	 */
	public static String addZreoIfLessThanTen(int i) {

		String string = "";
		int ballNum = i + 1;
		if (ballNum < 10) {
			string = "0" + ballNum;
		} else {
			string = ballNum + "";
		}
		return string;
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotNullAndEmpty(String string) {
		if (null != string && !"".equals(string.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 去掉�?��字符串中的所有的单个空格" "
	 * 
	 * @param string
	 */
	public static String replaceSpaceCharacter(String string) {
		if (null == string || "".equals(string)) {
			return "";
		}
		return string.replace(" ", "");
	}

	/**
	 * 获取小数位为6位的经纬�?
	 * 
	 * @param string
	 * @return
	 */
	public static String getStringLongitudeOrLatitude(String string) {
		if (StringUtil.isNullOrEmpty(string)) {
			return "";
		}
		if (string.contains(".")) {
			String[] splitArray = string.split("\\.");
			if (splitArray[1].length() > 6) {
				String substring = splitArray[1].substring(0, 6);
				return splitArray[0] + "." + substring;
			} else {
				return string;
			}
		} else {
			return string;
		}
	}

}
