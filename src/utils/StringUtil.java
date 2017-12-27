package utils;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static boolean isBlank(String[] check) {

		for (String s : check) {
			if (!StringUtils.isBlank(s)) {
				return false;
			}
		}

		return true;

	}

	//Capitalises the first char of a string
	public static String capitalise(String check) {
		return Character.toString(check.charAt(0)).toUpperCase() + check.substring(1);
	}

}
