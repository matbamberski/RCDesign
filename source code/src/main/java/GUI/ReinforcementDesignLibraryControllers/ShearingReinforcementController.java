package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import mainalgorithm.Reinforcement;
import util.InputValidation;
import util.StringToDouble;

public class ShearingReinforcementController {

	// ns1 design text field

	public static void addPropertiesToNS1TF(Reinforcement reinforcement, TextField nS1TF) {
		addListenerToNS1TF(reinforcement, nS1TF);
		addFocusListenerToNS1TF(nS1TF);
		setInitialNS1DesignScene(nS1TF);
	}

	private static boolean isNs1InputCorrect;

	private static void addListenerToNS1TF(Reinforcement reinforcement, TextField nS1) {
		nS1.textProperty().addListener(new nS1TFListener(reinforcement));
	}

	private static class nS1TFListener implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected nS1TFListener(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			if (InputValidation.nS1TextFieldInputValidation(arg2)) {
				reinforcement.setnS1(Double.parseDouble(arg2));
				isNs1InputCorrect = true;
			} else {
				isNs1InputCorrect = false;
			}
		}

	}

	private static void addFocusListenerToNS1TF(TextField tf) {
		tf.focusedProperty().addListener(new Ns1FocusListener(tf));
	}

	private static class Ns1FocusListener implements ChangeListener<Boolean> {

		TextField tf;

		protected Ns1FocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifNs1InputWasIncorrectChangeToInitialValue() {
			if (isNs1InputCorrect == false) {
				setInitialNS1DesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifNs1InputWasIncorrectChangeToInitialValue();
			}

		}

	}

	// ns 2 desgin scene text field

	public static void addPropertiesToNS2TFDesignScene(Reinforcement reinforcement, TextField nS2TF) {
		addListenerToNS2TFDesignScene(reinforcement, nS2TF);
		addFocusListenerNs2TFDesignScene(nS2TF);
		setInitialNS2DesignScene(nS2TF);
	}

	private static void addListenerToNS2TFDesignScene(Reinforcement reinforcement, TextField nS2) {
		nS2.textProperty().addListener(new nS2TFListenerDesignScene(reinforcement));
	}

	private static boolean isNs2InputCorrectDesignScene;

	private static class nS2TFListenerDesignScene implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected nS2TFListenerDesignScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if (InputValidation.nS2TextFieldInputValidation(newValue)) {
				reinforcement.setnS2Designed(Double.parseDouble(newValue));
				reinforcement.setnS2Required(Double.parseDouble(newValue));
				isNs2InputCorrectDesignScene = true;
			} else {
				isNs2InputCorrectDesignScene = false;
			}

		}
	}

	private static void addFocusListenerNs2TFDesignScene(TextField tf) {
		tf.focusedProperty().addListener(new Ns2FocusListenerDesignScene(tf));
	}

	private static class Ns2FocusListenerDesignScene implements ChangeListener<Boolean> {
		TextField tf;

		protected Ns2FocusListenerDesignScene(TextField tf) {
			this.tf = tf;
		}

		private void ifNs2InputWasIncorrectChangeToInitialValue() {
			if (isNs2InputCorrectDesignScene == false) {
				setInitialNS2DesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifNs2InputWasIncorrectChangeToInitialValue();
			}

		}

	}

	// ns2 diagnosis scene
	public static void addPropertiesToNS2TFDiagnosisScene(Reinforcement reinforcement, TextField nS2TF) {
		addListenerToNS2TFDiagnosisScene(reinforcement, nS2TF);
		addFocusListenerNs2TFDiagnosisScene(nS2TF);
		setInitialNS2DesignScene(nS2TF);
	}

	private static void addListenerToNS2TFDiagnosisScene(Reinforcement reinforcement, TextField nS2) {
		nS2.textProperty().addListener(new nS2TFListenerDiagnosisScene(reinforcement));
	}

	private static boolean isNs2InputCorrectDiagnosisScene;

	private static class nS2TFListenerDiagnosisScene implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected nS2TFListenerDiagnosisScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if (InputValidation.nS2TextFieldInputValidation(newValue)) {
				reinforcement.setnS2Designed(Double.parseDouble(newValue));
				reinforcement.setnS2Required(Double.parseDouble(newValue));
				isNs2InputCorrectDiagnosisScene = true;
			} else {
				isNs2InputCorrectDiagnosisScene = false;
			}
			if (newValue.equals("")) aSwValue = 0.0;
			else aSwValue = Double.parseDouble(newValue);
		}
	}

	private static void addFocusListenerNs2TFDiagnosisScene(TextField tf) {
		tf.focusedProperty().addListener(new Ns2FocusListenerDiagnosisScene(tf));
	}

	private static class Ns2FocusListenerDiagnosisScene implements ChangeListener<Boolean> {
		TextField tf;

		protected Ns2FocusListenerDiagnosisScene(TextField tf) {
			this.tf = tf;
		}

		private void ifNs2InputWasIncorrectChangeToInitialValue() {
			if (isNs2InputCorrectDiagnosisScene == false) {
				setInitialNS2DesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifNs2InputWasIncorrectChangeToInitialValue();
			}

		}

	}
	// phi st

	public static void addPropertiesToPhiStTFDesignScene(Reinforcement reinforcement, TextField phiStTF) {
		addListenerToPhiStTFDesignScene(reinforcement, phiStTF);
		setInitialPhiSt(phiStTF);
	}

	private static void addListenerToPhiStTFDesignScene(Reinforcement reinforcement, TextField phiStTF) {
		phiStTF.textProperty().addListener(new PhiStTFListener(reinforcement));
	}

	private static class PhiStTFListener implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected PhiStTFListener(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			reinforcement.setPhiSt(StringToDouble.stringToDouble(newValue));
		}

	}

	private static void setInitialPhiSt(TextField phiSt) {
		phiSt.setText("8");
	}

	private static void setInitialNS1DesignScene(TextField nS1) {
		nS1.setText("2");
	}

	private static void setInitialNS2DesignScene(TextField nS2) {
		nS2.setText("0");
	}

	// Asw1 tf

	public static void addPropertiesToAsw1TFDesignScene(Reinforcement reinforcement, TextField aSW1) {
		addListenerToAsw1TFDesignScene(reinforcement, aSW1);
		addFocusListenerToAsw1DesignScene(aSW1);
		setInitialAsw1ValueDesignScene(aSW1);
	}

	private static void addListenerToAsw1TFDesignScene(Reinforcement reinforcement, TextField aSW1) {
		aSW1.textProperty().addListener(new Asw1TFListenerDesignScene(reinforcement));
	}

	private static boolean isASw1InputCorrectDesignScene;

	private static class Asw1TFListenerDesignScene implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected Asw1TFListenerDesignScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.aSw1TextFieldInputValidation(newValue)) {
				reinforcement.setaSW1Diameter(StringToDouble.stringToDouble(newValue));
				isASw1InputCorrectDesignScene = true;
			} else {
				isASw1InputCorrectDesignScene = false;
			}
		}
	}

	private static void addFocusListenerToAsw1DesignScene(TextField tf) {
		tf.focusedProperty().addListener(new Asw1FocusListenerDesignScene(tf));
	}

	private static class Asw1FocusListenerDesignScene implements ChangeListener<Boolean> {
		TextField tf;

		protected Asw1FocusListenerDesignScene(TextField tf) {
			this.tf = tf;
		}

		private void ifAsw1WasIncorrectChangeValueToInitial() {
			if (isASw1InputCorrectDesignScene == false) {
				setInitialAsw1ValueDesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifAsw1WasIncorrectChangeValueToInitial();
			}

		}

	}

	private static void setInitialAsw1ValueDesignScene(TextField aSW1) {
		aSW1.setText("8");
	}

	// Asw2 tf

	public static void addPropertiesToAsw2TFDesignScene(Reinforcement reinforcement, TextField aSW2) {
		addListenerToAsw2TFDesignScene(reinforcement, aSW2);
		addFocusListenerToASw2DesignScene(aSW2);
		setInitialAsw2ValueDesignScene(aSW2);
	}

	private static void addListenerToAsw2TFDesignScene(Reinforcement reinforcement, TextField aSW1) {
		aSW1.textProperty().addListener(new Asw2TFListenerDesignScene(reinforcement));
	}

	private static boolean isASw2InputCorrectDesignScene;
	private static double aSwValue;

	private static class Asw2TFListenerDesignScene implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected Asw2TFListenerDesignScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.aSw1TextFieldInputValidation(newValue)) {
				reinforcement.setaSW2Diameter(StringToDouble.stringToDouble(newValue));
				isASw2InputCorrectDesignScene = true;
			} else {
				isASw2InputCorrectDesignScene = false;
			}

		}

	}

	private static void addFocusListenerToASw2DesignScene(TextField tf) {
		tf.focusedProperty().addListener(new Asw2FocusListenerDesignScene(tf));
	}

	private static class Asw2FocusListenerDesignScene implements ChangeListener<Boolean> {
		TextField tf;

		protected Asw2FocusListenerDesignScene(TextField tf) {
			this.tf = tf;
		}

		private void ifAsw2WasIncorrectChangeValueToInitial() {
			if (isASw2InputCorrectDesignScene == false) {
				setInitialAsw2ValueDesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifAsw2WasIncorrectChangeValueToInitial();
			}

		}

	}

	private static void setInitialAsw2ValueDesignScene(TextField aSW2) {
		aSW2.setText("16");
	}

	// s1 TF design

	public static void addPropertiesToS1TFDesignScene(Reinforcement reinforcement, TextField s1) {
		addListenerToS1TFDesignScene(reinforcement, s1);
		addFocusListenerToS1DesignScene(s1);
		setInitialS1ValueDesignScene(s1);
	}

	private static void addListenerToS1TFDesignScene(Reinforcement reinforcement, TextField tf) {
		tf.textProperty().addListener(new S1TFListenerDesignScene(reinforcement));
	}

	private static

	boolean isS1InputCorrectDesignScene;

	private static class S1TFListenerDesignScene implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected S1TFListenerDesignScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(",", ".");
			if (InputValidation.s1S2TextFieldInputValidation(arg2)) {
				reinforcement.setS1Required(Double.parseDouble(arg2));
				isS1InputCorrectDesignScene = true;
			} else {
				isS1InputCorrectDesignScene = false;
			}
		}

	}

	private static void addFocusListenerToS1DesignScene(TextField tf) {
		tf.focusedProperty().addListener(new S1FocusListenerDesignScene(tf));
	}

	private static class S1FocusListenerDesignScene implements ChangeListener<Boolean> {
		TextField tf;

		protected S1FocusListenerDesignScene(TextField tf) {
			this.tf = tf;
		}

		private void ifS1WasIncorrectChangeValueToInitial() {
			if (isS1InputCorrectDesignScene == false) {
				setInitialS1ValueDesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifS1WasIncorrectChangeValueToInitial();
			}

		}

	}

	private static void setInitialS1ValueDesignScene(TextField tf) {
		tf.setText("0");
	}

	// s1 tf diagnosis

	public static void addPropertiesToS1TFDiagnosisScene(Reinforcement reinforcement, TextField s1) {
		addListenerToS1TFDiagnosisScene(reinforcement, s1);
		addFocusListenerToS1DiagnosisScene(s1);
		setInitialS1ValueDiagnosisScene(s1);
	}

	private static void addListenerToS1TFDiagnosisScene(Reinforcement reinforcement, TextField tf) {
		tf.textProperty().addListener(new S1TFListenerDiagnosisScene(reinforcement));
	}

	private static

	boolean isS1InputCorrectDiagnosisScene;

	private static class S1TFListenerDiagnosisScene implements ChangeListener<String> {
		private Reinforcement reinforcement;

		protected S1TFListenerDiagnosisScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(",", ".");
			if (InputValidation.s1S2TextFieldInputValidation(arg2)) {
				reinforcement.setS1Designed(Double.parseDouble(arg2));
				isS1InputCorrectDiagnosisScene = true;
			} else {
				isS1InputCorrectDiagnosisScene = false;
			}
		}

	}

	private static void addFocusListenerToS1DiagnosisScene(TextField tf) {
		tf.focusedProperty().addListener(new S1FocusListenerDiagnosisScene(tf));
	}

	private static class S1FocusListenerDiagnosisScene implements ChangeListener<Boolean> {
		TextField tf;

		protected S1FocusListenerDiagnosisScene(TextField tf) {
			this.tf = tf;
		}

		private void ifS1WasIncorrectChangeValueToInitial() {
			if (isS1InputCorrectDiagnosisScene == false) {
				setInitialS1ValueDesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifS1WasIncorrectChangeValueToInitial();
			}

		}

	}

	private static void setInitialS1ValueDiagnosisScene(TextField tf) {
		tf.setText("0");
	}

	// s2 TF design

	public static void addPropertiesToS2TFDesignScene(Reinforcement reinforcement, TextField s2TF) {
		addListenerToS2TFDesignScene(reinforcement, s2TF);
		addFocusListenerToS2DesignScene(s2TF);
		setInitials2ValueDesignScene(s2TF);
	}

	private static void addListenerToS2TFDesignScene(Reinforcement reinforcement, TextField s2TF) {
		s2TF.textProperty().addListener(new S2TFListenerDesignScene(reinforcement));
	}

	private static boolean isS2InputCorrectDesignScene;
	private static double s2DesignValue;

	private static class S2TFListenerDesignScene implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected S2TFListenerDesignScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			
			
			newValue = newValue.replace(",", ".");
			if (InputValidation.s1S2TextFieldInputValidation(newValue)) {
				reinforcement.setS2Required(StringToDouble.stringToDouble(newValue));
				reinforcement.setS2Designed(StringToDouble.stringToDouble(newValue));
				isS2InputCorrectDesignScene = true;
			} else {
				isS2InputCorrectDesignScene = false;
			}
			if (newValue.equals("")) s2DesignValue = 0.0;
			else s2DesignValue = Double.parseDouble(newValue);
		}
	}

	private static void addFocusListenerToS2DesignScene(TextField tf) {
		tf.focusedProperty().addListener(new S2FocusListenerDesignScene(tf));
	}

	private static class S2FocusListenerDesignScene implements ChangeListener<Boolean> {
		TextField tf;

		protected S2FocusListenerDesignScene(TextField tf) {
			this.tf = tf;
		}

		private void ifS2WasIncorrectChangeValueToInitial() {
			if (isS2InputCorrectDesignScene == false) {
				setInitials2ValueDesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifS2WasIncorrectChangeValueToInitial();
			}
			if (aSwValue == 0) {
				setInitials2ValueDesignScene(tf);
			}

		}

	}

	private static void setInitials2ValueDesignScene(TextField s2) {
		s2.setText("0");
	}

	// s2 tf diagnosis

	public static void addPropertiesToS2TFDiagnosisScene(Reinforcement reinforcement, TextField s2TF) {
		addListenerToS2TFDiagnosisScene(reinforcement, s2TF);
		addFocusListenerToS2DiagnosisScene(s2TF);
		setInitials2ValueDiagnosisScene(s2TF);
	}

	private static void addListenerToS2TFDiagnosisScene(Reinforcement reinforcement, TextField s2TF) {
		s2TF.textProperty().addListener(new S2TFListenerDiagnosisScene(reinforcement));
	}

	private static boolean isS2InputCorrectDiagnosisScene;

	private static class S2TFListenerDiagnosisScene implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected S2TFListenerDiagnosisScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.s1S2TextFieldInputValidation(newValue)) {
				reinforcement.setS2Designed(StringToDouble.stringToDouble(newValue));
				reinforcement.setS2Required(StringToDouble.stringToDouble(newValue));
				isS2InputCorrectDiagnosisScene = true;
			} else {
				isS2InputCorrectDiagnosisScene = false;
			}
			if (newValue.equals("")) s2DesignValue = 0.0;
			else s2DesignValue = Double.parseDouble(newValue);
		}
	}

	private static void addFocusListenerToS2DiagnosisScene(TextField tf) {
		tf.focusedProperty().addListener(new S2FocusListenerDiagnosisScene(tf));
	}

	private static class S2FocusListenerDiagnosisScene implements ChangeListener<Boolean> {
		TextField tf;

		protected S2FocusListenerDiagnosisScene(TextField tf) {
			this.tf = tf;
		}

		private void ifS2WasIncorrectChangeValueToInitial() {
			if (isS2InputCorrectDiagnosisScene == false) {
				setInitials2ValueDesignScene(tf);
			}

		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifS2WasIncorrectChangeValueToInitial();
			}
			if (aSwValue == 0) {
				setInitials2ValueDesignScene(tf);
			}

		}

	}

	private static void setInitials2ValueDiagnosisScene(TextField s2) {
		s2.setText("0");
	}

	// Theta TF

	public static void addPropertiesToThetaTFDesignScene(Reinforcement reinforcement, TextField s2TF) {
		addListenerToThetaTFDesignScene(reinforcement, s2TF);
		addFocusListenerToThetaTFDesignScene(s2TF);
		setInitialThetaValueDesignScene(s2TF);
	}

	private static void addListenerToThetaTFDesignScene(Reinforcement reinforcement, TextField s2TF) {
		s2TF.textProperty().addListener(new ThetaTFListenerDesignScene(reinforcement));
	}

	private static boolean isThetaInputCorrectDesignScene;

	private static double thetaValue;

	private static class ThetaTFListenerDesignScene implements ChangeListener<String> {

		Reinforcement reinforcement;

		protected ThetaTFListenerDesignScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replaceAll(",", ".");
			if (InputValidation.thetaTextFieldInputValidation(newValue)) {
				newValue = newValue.replace(",", ".");
				reinforcement.setTheta(StringToDouble.stringToDouble(newValue));
				isThetaInputCorrectDesignScene = true;
				thetaValue = Double.valueOf(newValue);
			} else {
				isThetaInputCorrectDesignScene = false;
			}
			
		}
	}

	private static void addFocusListenerToThetaTFDesignScene(TextField tf) {
		tf.focusedProperty().addListener(new ThetaFocusListenerDesignScene(tf));
	}

	private static class ThetaFocusListenerDesignScene implements ChangeListener<Boolean> {
		TextField tf;

		protected ThetaFocusListenerDesignScene(TextField tf) {
			this.tf = tf;
		}

		private void ifThetaWasIncorrectChangeValueToInitial() {
			if (isThetaInputCorrectDesignScene == false) {
				if (thetaValue <= 2.5 && thetaValue >= 1) {
					setInitialThetaValueDesignScene(tf);
				}
				if (thetaValue > 2.5) {
					setThetaTo2dot5(tf);
				}
				if (thetaValue < 1) {
					setThetaTo1(tf);
				}
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifThetaWasIncorrectChangeValueToInitial();
			}

		}

	}

	private static void setInitialThetaValueDesignScene(TextField s2) {
		s2.setText("2");
	}

	private static void setThetaTo1(TextField tf) {
		tf.setText("1");
	}

	private static void setThetaTo2dot5(TextField tf) {
		tf.setText("2.5");
	}
	// Alfa TF

	public static void addPropertiesToAlfaTFDesignScene(Reinforcement reinforcement, TextField s2TF) {
		addListenerToAlfaTFDesignScene(reinforcement, s2TF);
		addFocusListenerToAlfaTFDesignScene(s2TF);
		setInitialAlfaValueDesignScene(s2TF);
	}

	private static void addListenerToAlfaTFDesignScene(Reinforcement reinforcement, TextField s2TF) {
		s2TF.textProperty().addListener(new AlfaListenerDesignScene(reinforcement));
	}

	private static boolean isAlfaInputCorrectDesignScene;

	private static class AlfaListenerDesignScene implements ChangeListener<String> {
		Reinforcement reinforcement;

		protected AlfaListenerDesignScene(Reinforcement reinforcement) {
			this.reinforcement = reinforcement;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if (InputValidation.alfaTextFieldInputValidation(newValue)) {
				reinforcement.setAlfa(StringToDouble.stringToDouble(newValue));
				isAlfaInputCorrectDesignScene = true;
			} else {
				isAlfaInputCorrectDesignScene = false;
			}
		}
	}

	private static void addFocusListenerToAlfaTFDesignScene(TextField tf) {
		tf.focusedProperty().addListener(new AlfaFocusListenerDesignScene(tf));
	}

	private static class AlfaFocusListenerDesignScene implements ChangeListener<Boolean> {
		TextField tf;

		protected AlfaFocusListenerDesignScene(TextField tf) {
			this.tf = tf;
		}

		private void ifAlfaWasIncorrectChangeValueToInitial() {
			if (isAlfaInputCorrectDesignScene == false) {
				setInitialAlfaValueDesignScene(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifAlfaWasIncorrectChangeValueToInitial();
			}

		}

	}

	private static void setInitialAlfaValueDesignScene(TextField s2) {
		s2.setText("45");
	}

}
