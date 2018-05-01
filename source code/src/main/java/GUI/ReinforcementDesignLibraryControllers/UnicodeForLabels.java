package GUI.ReinforcementDesignLibraryControllers;

import javafx.scene.control.Label;

public class UnicodeForLabels {

	public static void addUnicodeForLabels(Label cotTheta, Label alfa, Label alfaM) {
		setTextCotTheta(cotTheta);
		setTextAlfa(alfa);
		setTextAlfaM(alfaM);
	}

	private static void setTextCotTheta(Label label) {
		label.setText("ctg \u03B8");
	}

	private static void setTextAlfa(Label label) {
		label.setText("\u03B1");
	}

	private static void setTextAlfaM(Label label) {
		label.setText("\u03B1      [-]");
	}
}
