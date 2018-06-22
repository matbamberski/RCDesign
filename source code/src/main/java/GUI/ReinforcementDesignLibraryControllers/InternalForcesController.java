package GUI.ReinforcementDesignLibraryControllers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.itextpdf.layout.border.RoundDotsBorder;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
import util.InputValidation;
import util.StringToDouble;

public class InternalForcesController {
	
	///3.0 ver
	
	public static void addPropertiesToTextField(InternalForces internalForces, NominalStiffness stiffness, TextField tf, String className) {
		addInputListener(internalForces, stiffness, tf, className);
		addFocusListener(tf, className);
		setInitialValue(tf, className);
	}
	
	///////

	public static void addPropertiesToMEdTextField(InternalForces internalForces, TextField mEd, TextField mEdCharCalk,
			TextField mEdCharDlug) {
		addInputMEdListener(internalForces, mEd);
		addFocusMEdListener(mEd, mEdCharCalk, mEdCharDlug);
		setMedInitialValue(mEd);
	}
	

	public static void addPropertiesToMEdCharCalk(InternalForces forces, TextField mEd, TextField mEdCharCalk,
			TextField mEdCharDlug) {
		addInputMEdCharCalListener(forces, mEdCharCalk);
		addFocusMEdCharCalListener(mEd, mEdCharCalk, mEdCharDlug);
		setMedInitialValue(mEdCharCalk);
	}

	public static void addPropertiesToMEdCharDlug(InternalForces forces, TextField mEd, TextField mEdCharCalk,
			TextField mEdCharDlug) {
		addInputMEdCharDlugListener(forces, mEdCharDlug);
		addFocusMEdCharDlugListener(mEdCharDlug);
		setMedInitialValue(mEdCharDlug);
	}

	public static void addPropertiesToNEdTextField(InternalForces internalForces, TextField nEd,
			ChoiceBox<String> crossSectionTypeChoiceBox) {
		addInputNEdListener(internalForces, nEd, crossSectionTypeChoiceBox);
		addFocusNEdListener(nEd);
		setNedInitialValue(nEd);
	}

	public static void addPropertiesToVEdTextField(InternalForces internalForces, TextField vEd) {
		addInputVEdListener(internalForces, vEd);
		addFocusVEdListener(vEd);
		setVedInitialValue(vEd);
		System.out.println("Ved = " + vEd);
	}

	public static void addPropertiesToVEdRedTextField(InternalForces internalForces, TextField vEdRed) {
		addInputVEdRedListener(internalForces, vEdRed);
		addFocusVEdRedListener(vEdRed);
		setVedRedInitialValue(vEdRed);
		System.out.println("VedREd = " + vEdRed.getText());
	}

	// mEd text field

	private static void addInputMEdListener(InternalForces internalForces, TextField mEd) {
		mEd.textProperty().addListener(new MEdInputListener(internalForces));
	}

	private static boolean isMEdInputCorrect;
	private static double mEdValue;
	private static double mEdValueToCharacteristic;
	private static String mEdStringValue;
	private static String mEdToCharacteristicString;
	private static double mEkToMEkLtValue;
	private static String mEkToMEkLtString;

	private static class MEdInputListener implements ChangeListener<String> {
		private InternalForces internalForces;

		private MEdInputListener(InternalForces internalForces) {
			this.internalForces = internalForces;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

			// System.out.println(arg2);
			arg2 = arg2.replaceAll(",", ".");
			if (InputValidation.mEdInputValidation(arg2)) {
				internalForces.setmEd(StringToDouble.stringToDouble(arg2));
				isMEdInputCorrect = true;
				
				mEdValue = Double.parseDouble(arg2);
				mEdValueToCharacteristic = mEdValue/1.45;
				mEkToMEkLtValue = mEdValueToCharacteristic*0.85;
				
				mEdStringValue = arg2;
				mEdToCharacteristicString = String.format("%.2f", mEdValueToCharacteristic);
				mEkToMEkLtString = String.format("%.2f", mEkToMEkLtValue);
			} else {
				isMEdInputCorrect = false;
				
				mEdValue = 0.0;
				mEdValueToCharacteristic = 0.0;
				mEkToMEkLtValue = 0.0;
				
				mEdStringValue = "0";
				mEdToCharacteristicString = "0";
				mEkToMEkLtString = "0";
			}

		}

	}

	private static void addFocusMEdListener(TextField mEd, TextField mEdCharCalk, TextField mEdCharDlug) {
		mEd.focusedProperty().addListener(new MEdFocusListener(mEd, mEdCharCalk, mEdCharDlug));
	}

	private static class MEdFocusListener implements ChangeListener<Boolean> {

		TextField mEd;
		TextField mEdCharCalk;
		TextField mEdCharDlug;

		protected MEdFocusListener(TextField mEd, TextField mEdCharCalk, TextField mEdCharDlug) {
			this.mEd = mEd;
			this.mEdCharCalk = mEdCharCalk;
			this.mEdCharDlug = mEdCharDlug;
		}

		private void ifInputWasIncorrectSetValueToInitial() {
			if (isMEdInputCorrect == false) {
				setMedInitialValue(mEd);
			}
		}

		private void fuction() {
			
			if (mEdValue < mEdCharCalValue) {
				mEdCharCalk.setText(mEdToCharacteristicString);
				//mEdCharCalk.setText(mEdStringValue);
				if (mEdCharCalValue < mEdCharDlugValue) {
					mEdCharDlug.setText(mEkToMEkLtString);
					//mEdCharDlug.setText(mEdCharCalStringValue);
				}
			}
			
			mEdCharCalk.setText(mEdToCharacteristicString);
			mEdCharDlug.setText(mEkToMEkLtString);
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasIncorrectSetValueToInitial();
			}
			fuction();
		}

	}

	private static void addInputNEdListener(InternalForces internalForces, TextField nEd,
			ChoiceBox<String> crossSectionTypeChoiceBox) {
		nEd.textProperty().addListener(new NEdInputListener(internalForces, crossSectionTypeChoiceBox, nEd));
	}

	// mEdCharCalk Mek textfield

	private static void addInputMEdCharCalListener(InternalForces internalForces, TextField mEdCharObl) {
		mEdCharObl.textProperty().addListener(new MEdCharCalInputListener(internalForces));
	}

	private static boolean isMEdCharCalInputCorrect;
	private static double mEdCharCalValue;
	private static String mEdCharCalStringValue;

	private static class MEdCharCalInputListener implements ChangeListener<String> {
		private InternalForces internalForces;

		private MEdCharCalInputListener(InternalForces internalForces) {
			this.internalForces = internalForces;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replaceAll(",", ".");
			if (InputValidation.mEdInputValidation(arg2)) {
				internalForces.setCharacteristicMEdCalkowita(StringToDouble.stringToDouble(arg2));
				isMEdCharCalInputCorrect = true;
				
				mEdCharCalValue = Double.parseDouble(arg2);
				mEkToMEkLtValue = mEdCharCalValue*0.85;
				
				mEdCharCalStringValue = arg2;
				mEkToMEkLtString = String.format("%.2f", mEkToMEkLtValue);
			} else {
				isMEdCharCalInputCorrect = false;
				mEdCharCalValue = 0.0;
				mEkToMEkLtValue = 0.0;
				
				mEkToMEkLtString = "0";
				mEdCharCalStringValue = "0";
			}

		}
	}

	private static void addFocusMEdCharCalListener(TextField mEd, TextField mEdCharCalk, TextField mEdCharDlug) {
		mEdCharCalk.focusedProperty().addListener(new MEdCharCalFocusListener(mEd, mEdCharCalk, mEdCharDlug));
	}

	private static class MEdCharCalFocusListener implements ChangeListener<Boolean> {
		TextField mEd;
		TextField mEdCharCalk;
		TextField mEdCharDlug;

		protected MEdCharCalFocusListener(TextField mEd, TextField mEdCharCalk, TextField mEdCharDlug) {
			this.mEd = mEd;
			this.mEdCharCalk = mEdCharCalk;
			this.mEdCharDlug = mEdCharDlug;
		}

		private void ifInputWasIncorrectSetValueToInitial() {
			if (isMEdCharCalInputCorrect == false) {
				setMedInitialValue(mEdCharCalk);
			}
		}

		private void ifMekIsGreaterThenMedChangeValueToEqual() {
			if (mEdCharCalValue > mEdValue) {
				mEdCharCalk.setText(mEdToCharacteristicString);

			}
		}

		private void function() {
			/*
			if (mEdCharCalValue < mEdCharDlugValue && mEdCharCalValue > 0) {
				mEdCharDlug.setText(mEkToMEkLtString);

			}
			*/
			mEdCharDlug.setText(mEkToMEkLtString);
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			ifMekIsGreaterThenMedChangeValueToEqual();
			function();
			if (arg2 == false) {
				ifInputWasIncorrectSetValueToInitial();
			}

		}

	}

	// mEdCharDlug Mek,lt textField

	private static void addInputMEdCharDlugListener(InternalForces internalForces, TextField mEdCharDlug) {
		mEdCharDlug.textProperty().addListener(new MEdCharDlugInputListener(internalForces));
	}

	private static boolean isMEdCharDlugInputCorrect;
	private static double mEdCharDlugValue;
	private static String mEdCharString;

	private static class MEdCharDlugInputListener implements ChangeListener<String> {
		private InternalForces internalForces;

		private MEdCharDlugInputListener(InternalForces internalForces) {
			this.internalForces = internalForces;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replaceAll(",", ".");
			if (InputValidation.mEdInputValidation(arg2)) {
				internalForces.setCharacteristicMEdDlugotrwale(StringToDouble.stringToDouble(arg2));
				isMEdCharDlugInputCorrect = true;
				mEdCharDlugValue = Double.parseDouble(arg2);
				mEdCharString = arg2;
			} else {
				isMEdCharDlugInputCorrect = false;
				mEdCharDlugValue = 0.0;
				mEdCharString = "0";
			}

		}
	}

	private static void addFocusMEdCharDlugListener(TextField mEd) {
		mEd.focusedProperty().addListener(new MEdCharDlugFocusListener(mEd));
	}

	private static class MEdCharDlugFocusListener implements ChangeListener<Boolean> {

		TextField meklt;

		protected MEdCharDlugFocusListener(TextField mEd) {
			this.meklt = mEd;
		}

		private void compareMedCharAndCal() {
			if (mEdCharDlugValue > mEdCharCalValue) {
				meklt.setText(mEkToMEkLtString);
			}
		}

		private void ifInputWasIncorrectSetValueToInitial() {
			if (isMEdCharDlugInputCorrect == false) {
				setMedInitialValue(meklt);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

			if (arg2 == false) {
				ifInputWasIncorrectSetValueToInitial();
			}
			compareMedCharAndCal();
		}

	}

	// nEd text field

	private static boolean isNEdInputCorrect;

	private static class NEdInputListener implements ChangeListener<String> {
		private InternalForces internalForces;
		private ChoiceBox<String> crossSectionTypeChoiceBox;
		private TextField nEd;

		private NEdInputListener(InternalForces internalForces, ChoiceBox<String> crossSectionTypeChoiceBox,
				TextField nEd) {
			this.internalForces = internalForces;
			this.crossSectionTypeChoiceBox = crossSectionTypeChoiceBox;
			this.nEd = nEd;
		}

		private void disableCbWhenNedIsNotEqual0() {
			crossSectionTypeChoiceBox.setDisable(true);
			crossSectionTypeChoiceBox.setValue("Przekrój prostok¹tny");
		}

		private void enableCbWhenNedIsEqual0() {
			crossSectionTypeChoiceBox.setDisable(false);
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(',', '.');
			if (InputValidation.internalForcesTextFieldInputValidation(arg2)) {
				internalForces.setnEd(StringToDouble.stringToDouble(arg2));
				if (nEd.textProperty().getValue().equals("0")) {
					enableCbWhenNedIsEqual0();
				} else {
					disableCbWhenNedIsNotEqual0();
				}
				isNEdInputCorrect = true;
			} else {
				isNEdInputCorrect = false;
			}

		}

	}

	private static void addFocusNEdListener(TextField nEd) {
		nEd.focusedProperty().addListener(new NEdFocusListener(nEd));
	}

	private static class NEdFocusListener implements ChangeListener<Boolean> {

		TextField ned;

		protected NEdFocusListener(TextField nEd) {
			this.ned = nEd;
		}

		private void ifInputWasIncorrectSetValueToInitial() {
			if (isNEdInputCorrect == false) {
				setNedInitialValue(ned);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasIncorrectSetValueToInitial();
			}

		}

	}

	// vEd text field
	//
	private static boolean isVEdInputCorrect;
	
	private static void addInputVEdListener(InternalForces internalForces, TextField vEd) {
		vEd.textProperty().addListener(new VEdInputListener(internalForces));
	}
	

	private static class VEdInputListener implements ChangeListener<String> {
		private InternalForces internalForces;

		private VEdInputListener(InternalForces internalForces) {
			this.internalForces = internalForces;
		}

		
		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(',', '.');
			if (InputValidation.vEdTextFieldInputValidation(arg2)) {
				internalForces.setvEd(StringToDouble.stringToDouble(arg2));
				isVEdInputCorrect = true;
			} else {
				isVEdInputCorrect = false;
			}

		}

	}
	
	private static void addFocusVEdListener(TextField vEd) {
		vEd.focusedProperty().addListener(new VEdFocusListener(vEd));
	}
	
	private static class VEdFocusListener implements ChangeListener<Boolean> {

		private TextField vEd;
		
		protected VEdFocusListener(TextField vEd) {
			this.vEd = vEd;
		}
				
		private void ifInputWasIncorrectSetValueToInitial() {
			if (isVEdInputCorrect == false) {
				setNedInitialValue(vEd);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasIncorrectSetValueToInitial();
			}
		}

	}

	//
	// vEdRed text field
	private static boolean isVEdRedInputCorrect;
	
	private static void addInputVEdRedListener(InternalForces internalForces, TextField vEdRed) {
		vEdRed.textProperty().addListener(new VEdRedInputListener(internalForces));
	}
	

	private static class VEdRedInputListener implements ChangeListener<String> {
		private InternalForces internalForces;

		private VEdRedInputListener(InternalForces internalForces) {
			this.internalForces = internalForces;
		}

		
		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(',', '.');
			if (InputValidation.vEdRedTextFieldInputValidation(arg2)) {
				internalForces.setvEdRed(StringToDouble.stringToDouble(arg2));
				isVEdRedInputCorrect = true;
			} else {
				isVEdRedInputCorrect = false;
			}

		}

	}
	
	private static void addFocusVEdRedListener(TextField vEdRed) {
		vEdRed.focusedProperty().addListener(new VEdRedFocusListener(vEdRed));
	}
	
	private static class VEdRedFocusListener implements ChangeListener<Boolean> {

		private TextField vEdRed;
		
		protected VEdRedFocusListener(TextField vEdRed) {
			this.vEdRed = vEdRed;
		}
				
		private void ifInputWasIncorrectSetValueToInitial() {
			if (isVEdRedInputCorrect == false) {
				setNedInitialValue(vEdRed);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasIncorrectSetValueToInitial();
			}
		}

	}
	//

	
	private static void setMedInitialValue(TextField mEd) {
		mEd.setText("0");
	}

	private static void setNedInitialValue(TextField nEd) {
		nEd.setText("0");
	}

	private static void setVedInitialValue(TextField vEd) {
		vEd.setText("0");
	}
	
	private static void setVedRedInitialValue(TextField vEdRed) {
		vEdRed.setText("0");
	}

	////// Ogolna metoda na sprawdzanie wprowadzanych danych

	// text field
	private static boolean isInputCorrect;

	private static void addInputListener(InternalForces internalForces, NominalStiffness stiffness, TextField tf, String className) {
		tf.textProperty().addListener(new InputListener(internalForces, stiffness, tf, className));
	}

	private static class InputListener implements ChangeListener<String> {
		private InternalForces internalForces;
		private TextField tf;
		private NominalStiffness stiffness;
		private String className;

		private InputListener(InternalForces internalForces, NominalStiffness stiffness, TextField tf, String className) {
			this.internalForces = internalForces;
			this.tf = tf;
			this.stiffness = stiffness;
			this.className = className;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(',', '.');
			if (InputValidation.internalForcesTextFieldInputValidation(arg2)) {
				String name = tf.getId();
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				Method method = null;
				
				try {
					method = Class.forName(className).getMethod("set"+name, double.class);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try {
					if (className.equals("mainalgorithm.InternalForces")) {
						method.invoke(internalForces, StringToDouble.stringToDouble(arg2));
					}
					else if (className.equals("mainalgorithm.NominalStiffness")) {
						method.invoke(stiffness, StringToDouble.stringToDouble(arg2));
					}
					
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//internalForces.setvEd(StringToDouble.stringToDouble(arg2));
				isInputCorrect = true;
			} else {
				isInputCorrect = false;
			}

		}

	}

	private static void addFocusListener(TextField ForceEd, String className) {
		ForceEd.focusedProperty().addListener(new FocusListener(ForceEd, className));
	}

	private static class FocusListener implements ChangeListener<Boolean> {

		private TextField ForceEd;
		private String className;

		protected FocusListener(TextField ForceEd, String className) {
			this.ForceEd = ForceEd;
			this.className = className;
		}

		private void ifInputWasIncorrectSetValueToInitial() {
			if (isInputCorrect == false) {
				setInitialValue(ForceEd, className);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasIncorrectSetValueToInitial();
			}
		}

	}
	
	private static void setInitialValue(TextField tf, String className) {
		if (className.equals("mainalgorithm.NominalStiffness")) {
			tf.setText("1");
		} else {
			tf.setText("0");
		}
	}

}
