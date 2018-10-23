package util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

public class StringUtil {

	public static List<String> getStringsBetween(List<String> f, String start, String end) {

		List<String> content = new ArrayList<String>();

		boolean isInside = false;

		for (String s : f) {

			if (s.contains(start)) {
				isInside = true;
			}

			if (s.contains(end)) {
				isInside = false;
			}

			if (isInside) {
				content.add(s);
			}

		}

		return f;

	}

	public static String capitalizeFirstLetter(String line) {
		return line.substring(0, 1).toUpperCase() + line.substring(1);
	}

	public static String getStringBetween(String f, String begin, String end) {
		if (f == null || begin == null || end == null) {
			return "Invalid data :  Null value : Input : '" + f + "', Begin : '" + begin + "', End : '" + end + "'.";
		}

		if (!f.contains(begin) || !f.contains(end)) {
			return "Invalid data : Input does not contain delimiters : Input : '" + f + "', Begin : '" + begin
					+ "', End : '" + end + "'.";
		}

		return f.substring(f.lastIndexOf(begin) + begin.length(), f.lastIndexOf(end));
	}

	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}

}
