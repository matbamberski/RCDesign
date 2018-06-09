package util;

import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class OutputFormatter {

	public static String metersToCentimetersForReinforcement(double number) {
		number = number * 10000.0;
		String formattedNumber = String.format("%.03f", number) + " cm2";
		return formattedNumber;
	}

	public static String showPercentages(double number) {
		number = number * 100.0;
		String formattedNumber = String.format("%.02f", number) + " %";
		return formattedNumber;
	}

	public static AttributedString test(String string) {
		AttributedString attributed = new AttributedString(string);
		attributed.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 1, 2);
		return attributed;
	}

	public static String wFormatter(double number) {
		String formatterNumber = String.format("%.03f", number) + " mm";
		return formatterNumber;
	}

	public static String fFormatter(double number) {
		number = number * 100;
		String formatterNumber = String.format("%.03f", number) + " cm";
		return formatterNumber;
	}

	public static String diagnosisMed(double number) {
		String formatterNumber = String.format("%.03f", number) + " kNm";
		return formatterNumber;
	}

	public static String diagnosisVedAndNed(double number) {
		String formatterNumber = String.format("%.03f", number) + " kN";
		return formatterNumber;
	}

	public static String s1s2(double number) {
		String formattedNumber = String.format("%.05f", number) + " m";
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
