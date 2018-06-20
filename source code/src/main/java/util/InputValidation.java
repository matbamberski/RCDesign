package util;

import java.util.regex.Pattern;

public class InputValidation {

	public static boolean aDistanceTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0]{1}[,\\.][1-9]{1}");
		Pattern pattern2 = Pattern.compile("[0]{1}[,\\.][0-9]{1}[0-9]{1}");
		Pattern pattern3 = Pattern.compile("[0]{1}[,\\.][0-9]{1}[0-9]{1}[0-9]{1}");
		if (pattern.matcher(string).matches() || pattern2.matcher(string).matches() || pattern3.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean mEdInputValidation(String string) {
		boolean isCorrect = false;
		if (!string.isEmpty()) {
			Pattern pattern = Pattern.compile("[0-9]+[,\\.]*[0-9]*");
			if (pattern.matcher(string).matches()) {
				isCorrect = true;
			}
		}
		return isCorrect;
	}

	public static boolean internalForcesTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[-]*[0-9]+[,\\.]*[0-9]*");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean vEdTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0-9]+[,\\.]*[0-9]*");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}
	
	public static boolean vEdRedTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0-9]+[,\\.]*[0-9]*");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean dimensionTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern2 = Pattern.compile("[0-9]+[,\\.]{1}[0-9]+");
		Pattern pattern3 = Pattern.compile("[0-9]+");

		if (pattern2.matcher(string).matches() || pattern3.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean fYkTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[2-8]{1}[0-9]{2}");
		Pattern pattern1 = Pattern.compile("[9]{1}[0]{2}");
		Pattern pattern4 = Pattern.compile("[2-8]{1}[0-9]{2}[,\\.]*[0]*");
		if (pattern.matcher(string).matches() || pattern1.matcher(string).matches() || pattern4.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean cNomTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0-9]{1}[0-9]{1}[0-9]?");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean aS1aS2TextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern1 = Pattern.compile("[0-5]{1}[0-9]{1}?");
		Pattern pattern2 = Pattern.compile("[0-9]{1}");
		Pattern pattern3 = Pattern.compile("[0-9]{1}[,\\.]*[0-9]*");
		Pattern pattern4 = Pattern.compile("[0-5]{1}[0-9]{1}[,\\.]*[0-9]*");
		if (pattern1.matcher(string).matches() || pattern2.matcher(string).matches() || pattern3.matcher(string).matches() || pattern4.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean alfaTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern1 = Pattern.compile("[3-8]{1}[0-9]{1}?");
		Pattern pattern2 = Pattern.compile("[9]{1}[0]{1}");

		if (pattern1.matcher(string).matches() || pattern2.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean nS1TextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern1 = Pattern.compile("[1-9]{1}[0-9]*");

		if (pattern1.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean nS2TextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern1 = Pattern.compile("[1-9]{1}[0-9]*");
		Pattern pattern2 = Pattern.compile("[0]{1}");

		if (pattern1.matcher(string).matches() || pattern2.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean aSw1TextFieldInputValidation(String string) {
		boolean isCorrect = false;

		Pattern pattern2 = Pattern.compile("[4-9]{1}[,\\.]{1}[0-9]*");
		Pattern pattern3 = Pattern.compile("[1]{1}[0-5]{1}[,\\.]{1}[0-9]*");
		Pattern pattern1 = Pattern.compile("[1]{1}[6]{1}");
		Pattern pattern4 = Pattern.compile("[4-9]");
		Pattern pattern5 = Pattern.compile("[1]{1}[0-6]");
		Pattern pattern6 = Pattern.compile("[1-9]{1}[0-9]?[,\\.]?[0-9]*");
		if (pattern1.matcher(string).matches() || pattern2.matcher(string).matches() || pattern3.matcher(string).matches() || pattern4.matcher(string).matches() || pattern5.matcher(string).matches()
				|| pattern6.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean s1S2TextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern1 = Pattern.compile("[0]{1}[\\.]{1}[0-9]{1}[0-9]?");
		Pattern pattern2 = Pattern.compile("[0]{1}");
		if (pattern1.matcher(string).matches() || pattern2.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean thetaTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern1 = Pattern.compile("[1]{1}[,\\.]*[0-9]*");

		Pattern pattern2 = Pattern.compile("[2]{1}[,\\.]*[0-5]*");
		if (pattern1.matcher(string).matches() || pattern2.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean lEffTextFieldInputValidation(String string) {

		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0-9]+[,\\.]*[0-9]*");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean alfaMTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0]{1}[,\\.]{1}[0-9]+");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean t0TextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0-9]+");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean RhTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0-9]{2}");
		Pattern pattern1 = Pattern.compile("(100){1}");
		Pattern pattern3 = Pattern.compile("[0-9]{1}");
		if (pattern.matcher(string).matches() || pattern1.matcher(string).matches() || pattern3.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean wLimTextFieldInputValidation(String string) {
		boolean isCorrect = false;
		Pattern pattern = Pattern.compile("[0]{1}[,\\.]{1}[2-4]{1}");
		if (pattern.matcher(string).matches()) {
			isCorrect = true;
		}
		return isCorrect;
	}

}
