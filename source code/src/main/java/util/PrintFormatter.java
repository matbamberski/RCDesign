package util;

public class PrintFormatter extends OutputFormatter {

	public static String a1a2printformatter(double number) {
		String formattedNumber = String.format("%.03f", number) + " m";
		return formattedNumber;
	}

	public static String dimensionsPrintformatter(double number) {
		String formattedNumber = String.format("%.03f", number) + " m";
		return formattedNumber;
	}

	public static String as1as2PrintFormatter(double number) {
		number = number * 10000.0;
		String formattedNumber = String.format("%.03f", number) + "";
		return formattedNumber;
	}

	public static String s1s2PrintFormatter(double number) {
		String formattedNumber = String.format("%.03f", number) + " m";
		return formattedNumber;
	}
}
