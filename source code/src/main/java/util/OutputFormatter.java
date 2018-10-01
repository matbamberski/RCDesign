package util;

import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class OutputFormatter {

	public static String metersToCentimetersForReinforcement(double number) {
		number = number * 10000.0;
		String formattedNumber = String.format("%.03f", number) + " cm" + "\u00B2";
		return formattedNumber;
	}
	

	public static String showPercentages(double number) {
		number = number * 100.0;
		String formattedNumber = String.format("%.02f", number) + " %";
		return formattedNumber;
	}

	public static String test(String string) {
		AttributedString attributed = new AttributedString(string);
		attributed.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 0, 1);
		return attributed.toString();
	}
	
	public static String wFormatterSingle(double number) {
		String formatterNumber = String.format("%.03f", number) + " mm";
		return formatterNumber;
	}

	public static String fFormatterSingle(double number) {
		number = number * 100;
		String formatterNumber = String.format("%.02f", number) + " cm";
		return formatterNumber;
	}
	
	public static String wFormatter(double number) {
		String formatterNumber = String.format("%.03f", number) + " mm";
		return formatterNumber;
	}
	/*
	public static String fFormatter(double number) {
		number = number * 100;
		String formatterNumber = String.format("%.03f", number) + " cm";
		return formatterNumber;
	}
	*/
	////////////////////////////********£amane sls*************///////////
	/*
	public static String wFormatter(double number1, double number2) {
		String formatterNumber = String.format("%.03f", number1) + "/" + String.format("%.03f", number2)+ " mm";
		return formatterNumber;
	}
	*/
	public static String fFormatter(double number1, double number2) {
		number1 = number1 * 100;
		number2 = number2 * 100;
		String formatterNumber = String.format("%.02f", number1) + "/" + String.format("%.02f", number2) + " cm";
		return formatterNumber;
	}

////////////////////////////********£amane sls*************///////////
	public static String diagnosisMed(double number) {
		String formatterNumber = String.format("%.02f", number) + " kNm";
		return formatterNumber;
	}

	public static String diagnosisVedAndNed(double number) {
		String formatterNumber = String.format("%.02f", number) + " kN";
		return formatterNumber;
	}

	public static String s1s2(double number) {
		String formattedNumber = String.format("%.03f", number) + " m";
		return formattedNumber;
	}

	public static String formatAs1As2(double number) {
		String formattedNumber;
		if (number % 1 == 0) {
			formattedNumber = String.format("%,.0f", number);
		} else {
			formattedNumber = String.format("%,.2f", number);
		}
		return formattedNumber;
	}

}
