package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import mainalgorithm.Reinforcement;
import util.InputValidation;
import util.StringToDouble;

public class UsersDesignedReinforcementController {

	public static void addPropertiesToDesignedAs1TF(Reinforcement reinforcement, TextField aS1) {
		addAS1Listener(reinforcement, aS1);
		addAs1FocusListener(aS1);
		setAs1InitialValue(aS1);
	}

	public static void addPropertiesToDesignedAs2TF(Reinforcement reinforcement, TextField aS2) {
		addAS2Listener(reinforcement, aS2);
		addAs2FocusListener(aS2);
		setAs2InitialValue(aS2);
	}

	// as1

	private static void addAS1Listener(Reinforcement reinforcement, TextField aS1) {
		aS1.textProperty().addListener(new AS1Listener(reinforcement));
	}

	private static boolean isAs1InputCorrect;

	private static class AS1Listener implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected AS1Listener(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(",", ".");
			if (InputValidation.aS1aS2TextFieldInputValidation(arg2)) {
				reinforcement.setDesignedDiammeterAS1(StringToDouble.stringToDouble(arg2));
				reinforcement.calculateDesignedSymmetricalAs1RodsGiven();
				reinforcement.calculateDesignedUnsymmetricalAs1RodsGiven();
				isAs1InputCorrect = true;
			} else {
				isAs1InputCorrect = false;
			}

		}
	}

	private static void addAs1FocusListener(TextField tf) {
		tf.focusedProperty().addListener(new As1FocusListener(tf));
	}

	private static class As1FocusListener implements ChangeListener<Boolean> {
		private TextField tf;

		protected As1FocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifAs1InputWasInccorectSetValueToInitial() {
			if (isAs1InputCorrect == false) {
				setAs1InitialValue(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifAs1InputWasInccorectSetValueToInitial();
			}

		}

	}

	// as2
	private static void addAS2Listener(Reinforcement reinforcement, TextField aS2) {
		aS2.textProperty().addListener(new AS2Listener(reinforcement));
	}

	private static class AS2Listener implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected AS2Listener(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.aS1aS2TextFieldInputValidation(newValue)) {
				reinforcement.setDesignedDiameterAS2(StringToDouble.stringToDouble(newValue));
				reinforcement.calculateDesignedSymmetricalAs2RodsGiven();
				reinforcement.calculateDesignedUnsymmetricalAs2RodsGiven();
				isAs2InputCorrect = true;
			} else {
				isAs2InputCorrect = false;
			}
		}
	}

	private static boolean isAs2InputCorrect;

	private static void addAs2FocusListener(TextField tf) {
		tf.focusedProperty().addListener(new As2FocusListener(tf));
	}

	private static class As2FocusListener implements ChangeListener<Boolean> {
		private TextField tf;

		protected As2FocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifAs2InputWasInccorectSetValueToInitial() {
			if (isAs2InputCorrect == false) {
				setAs2InitialValue(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifAs2InputWasInccorectSetValueToInitial();
			}

		}

	}

	private static void setAs1InitialValue(TextField aS1) {
		aS1.setText("16");
	}

	private static void setAs2InitialValue(TextField aS2) {
		aS2.setText("16");
	}

}
