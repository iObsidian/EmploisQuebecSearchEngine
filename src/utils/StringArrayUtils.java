package utils;
import org.apache.commons.lang.StringUtils;

public class StringArrayUtils {

	public static boolean isBlank(String[] check) {

		for (String s : check) {
			if (!StringUtils.isBlank(s)) {
				return false;
			}
		}

		return true;

	}

}
