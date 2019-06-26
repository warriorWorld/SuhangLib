package com.insightsurface.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCheck {
	/**
	 * 
	 * @Description: 校验手机号
	 * @param @param phone
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isPhone(String phone) {
		// return (null != phone && phone.matches("^\\s*1\\d{10}\\s*$"));
		return (null != phone && phone.matches("[1][34578]+\\d{9}"));
	}

	/**
	 * 验证字符串数据的长度范围是否符合要求。
	 * 
	 * @param val
	 *            字符串数据
	 * @param minLength
	 *            字符串最小长度限定值
	 * @param maxLength
	 *            字符串最大长度限定值
	 * @return true，合法；false，非法。
	 */
	public static boolean isValidLengthBounds(String val, int minLength,
			int maxLength) {
		if (val == null)
			return false;
		int length = val.length();
		return (length >= minLength && length <= maxLength);
	}

	/**
	 * 是不是合法的身份证号
	 * 
	 * @param s_aStr
	 * @return
	 */
	public static boolean isIdCard(String s_aStr) {
		String reg15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		String reg18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
		Pattern pattern15 = Pattern.compile(reg15);
		Pattern pattern18 = Pattern.compile(reg18);
		Matcher matcher15 = pattern15.matcher(s_aStr);
		Matcher matcher18 = pattern18.matcher(s_aStr);
		return matcher15.find() || matcher18.find();
	}

	/**
	 * 是不是2-7位的中文字符
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isChineseName(String name) {
		if (null == name
				|| !name.matches("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*"))
			return false;
		else
			return true;
	}

	/**
	 * 验证是否是字母数字组合
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isValidNumAndLetter(String val) {
		// ^[A-Za-z0-9]+$

		if (null == val) {
			return false;
		}

		if (val.matches("[A-Za-z0-9]+")) {
			Pattern p1 = Pattern.compile("[a-z]+");
			Pattern p2 = Pattern.compile("[A-Z]+");
			Pattern p3 = Pattern.compile("[0-9]+");
			Matcher m = p3.matcher(val);
			if (!m.find())
				return false;
			else {
				m.reset().usePattern(p2);
				if (!m.find() && !m.reset().usePattern(p1).find()) {
					return false;
				}

				return true;
			}
		} else {
			return false;
		}

		// Pattern p1= Pattern.compile("^[a-zA-Z0-9]+$");
		//
		// return p1.matcher(val).find();

		// return (null != val &&
		// val.matches("/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{2,})$/"));
	}

	/**
	 * 
	 * @Description: 校验公司和手机号
	 * @param @param phone
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isWorkPhone(String phone) {

		// TODO
		if (!isPhone(phone)) {
			return phone.matches("([0][0-9]{2,3}-?)[0-9]{7,8}")
					|| phone.matches("[1][34578]+\\d{9}");
		}
		return true;
	}
}
