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
	
	public static String cNomPrintFormatter(double number) {
		Double cnom = new Double(number);
		int value = cnom.intValue();
		String formattedNumber = String.format("%d", value) + " mm";
		return formattedNumber;
	}
	
	public static String alfaMFormatter(double number) {
		String formattedNumber = String.format("%.03f", number);
		return formattedNumber;
	}
	
	public static String doubletoIntPrintFormatter(double number) {
		Double cnom = new Double(number);
		int value = cnom.intValue();
		String formattedNumber = String.format("%d", value);
		return formattedNumber;
	}
	
	public static String momentFormatter(double m) {
		String out = String.format("%.02f", m) + " kNm";
		return out;
	}


	public static String forcesFormatter(double m) {
		String out = String.format("%.02f", m) + " kN";
		return out;
	}
}
