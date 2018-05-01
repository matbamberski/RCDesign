package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import materials.Steel;
import util.InputValidation;

public class SteelParametersController {

	public static void addPropertiesToFYkTF(Steel steel, TextField fYk) {
		addListenerToFYdTF(steel, fYk);
		setFYdInitialValue(fYk);
		addFocusFykListener(fYk);
	}

	private static void addListenerToFYdTF(Steel steel, TextField fYk) {
		fYk.textProperty().addListener(new FYdTFListener(steel));
	}

	private static boolean isFykInputCorrect;
	private static double fYkValue;

	private static void setFYdInitialValue(TextField fYk) {
		fYk.setText("500");
	}

	private static class FYdTFListener implements ChangeListener<String> {

		private Steel steel;

		protected FYdTFListener(Steel steel) {
			this.steel = steel;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replaceAll(",", ".");
			if (InputValidation.fYkTextFieldInputValidation(arg2)) {
				steel.setfYk(Integer.valueOf(arg2));
				isFykInputCorrect = true;
			} else {
				isFykInputCorrect = false;
			}
			fYkValue = Double.valueOf(arg2);
			System.out.println(fYkValue + " tutaj");
		}
	}

	private static void addFocusFykListener(TextField tf) {
		tf.focusedProperty().addListener(new FykFocusListener(tf));
	}

	private static class FykFocusListener implements ChangeListener<Boolean> {
		TextField tf;

		protected FykFocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifInputWasIncorrectSetValueToInitial() {
			if (isFykInputCorrect == false) {
				if (fYkValue <= 900 && fYkValue >= 200) {
					setFYdInitialValue(tf);
				}
				if (fYkValue > 900) {
					setFykTo900(tf);
				}
				if (fYkValue < 200) {
					setFykTo200(tf);
				}

			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifInputWasIncorrectSetValueToInitial();
			}

		}

	}

	private static void setFykTo200(TextField tf) {
		tf.setText("200");
	}

	private static void setFykTo900(TextField tf) {
		tf.setText("900");
	}
}