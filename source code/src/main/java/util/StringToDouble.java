package util;

public class StringToDouble {

	private static double number;

	public static double stringToDouble(String string) {
		try {
			number = Double.parseDouble(string);
		} catch (NumberFormatException e) {

		}
		return number;
	}
}
