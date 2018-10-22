package util;

public class CapitalizeFirstLetter {

	public static String capitalizeFirstLetter(String line) {
		return line.substring(0, 1).toUpperCase() + line.substring(1);
	}

}
