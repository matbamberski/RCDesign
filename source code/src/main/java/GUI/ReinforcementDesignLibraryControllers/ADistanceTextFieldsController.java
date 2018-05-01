package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import materials.DimensionsOfCrossSectionOfConcrete;
import util.InputValidation;
import util.StringToDouble;

public class ADistanceTextFieldsController {

	// a1 textfield

	public static void addPropertiesToA1TextField(TextField aDistanceTextFieldOfA1, DimensionsOfCrossSectionOfConcrete dimensions) {
		addInputListenerToA1Field(aDistanceTextFieldOfA1, dimensions);
		setA1InitialValue(aDistanceTextFieldOfA1);
		addFocusListenerToA1Field(aDistanceTextFieldOfA1, dimensions);
	}

	private static void addInputListenerToA1Field(TextField aDistanceTextFieldOfA1, DimensionsOfCrossSectionOfConcrete dimensions) {
		aDistanceTextFieldOfA1.textProperty().addListener(new InputValueListenerOfA1(dimensions));
	}

	private static Boolean isInputOfA1TextFieldCorrect = true;
	private static double a1Value;

	private static class InputValueListenerOfA1 implements ChangeListener<String> {

		private DimensionsOfCrossSectionOfConcrete dimensions;

		protected InputValueListenerOfA1(DimensionsOfCrossSectionOfConcrete dimensions) {
			this.dimensions = dimensions;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replaceAll(",", ".");
			if (InputValidation.aDistanceTextFieldInputValidation(newValue)) {
				dimensions.setA1(StringToDouble.stringToDouble(newValue));
				dimensions.calculateD();
				isInputOfA1TextFieldCorrect = true;
			} else {
				isInputOfA1TextFieldCorrect = false;
			}
			a1Value = Double.parseDouble(newValue);
		}

	}

	private static void addFocusListenerToA1Field(TextField aDistanceTextFieldOfA1, DimensionsOfCrossSectionOfConcrete dimensions) {
		aDistanceTextFieldOfA1.focusedProperty().addListener(new FocusListenerOfA1(aDistanceTextFieldOfA1, dimensions));
	}

	private static class FocusListenerOfA1 implements ChangeListener<Boolean> {

		private TextField a1;
		DimensionsOfCrossSectionOfConcrete dimensions;

		protected FocusListenerOfA1(TextField a1, DimensionsOfCrossSectionOfConcrete dimensions) {
			this.a1 = a1;
			this.dimensions = dimensions;
		}

		private void ifInputWasIncorectChangeValueToInitial() {
			if (isInputOfA1TextFieldCorrect == false) {
				setA1InitialValue(a1);
			}
		}

		private void ifa1IsGreaterThen04hChangeValueTo04h(DimensionsOfCrossSectionOfConcrete dimensions) {
			if (a1Value > 0.4 * dimensions.getH()) {
				a1.setText(Double.toString(0.4 * dimensions.getH()));
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasIncorectChangeValueToInitial();
			}
			ifa1IsGreaterThen04hChangeValueTo04h(dimensions);
		}
	}

	private static void setA1InitialValue(TextField aDistanceTextFieldOfA1) {
		aDistanceTextFieldOfA1.setText("0.05");
	}

	// a2 textfield

	public static void addPropertiesToA2TextField(TextField aDistanceTextFieldOfA2, DimensionsOfCrossSectionOfConcrete dimensions) {
		addInputListenerToA2Field(aDistanceTextFieldOfA2, dimensions);
		addFocusListenerToA2Field(aDistanceTextFieldOfA2, dimensions);
		setA2InitialValue(aDistanceTextFieldOfA2);
	}

	private static void addInputListenerToA2Field(TextField aDistanceTextFieldOfA2, DimensionsOfCrossSectionOfConcrete dimensions) {
		aDistanceTextFieldOfA2.textProperty().addListener(new InputValueListenerOfA2(dimensions));
	}

	private static Boolean isInputOfA2TextFieldCorrect = true;
	private static double a2Value;
	private static String a2StringValue;

	private static class InputValueListenerOfA2 implements ChangeListener<String> {

		private DimensionsOfCrossSectionOfConcrete dimensions;

		protected InputValueListenerOfA2(DimensionsOfCrossSectionOfConcrete dimensions) {
			this.dimensions = dimensions;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replaceAll(",", ".");
			if (InputValidation.aDistanceTextFieldInputValidation(newValue)) {
				dimensions.setA2(StringToDouble.stringToDouble(newValue));
				dimensions.calculateD();
				isInputOfA2TextFieldCorrect = true;
			} else {
				isInputOfA2TextFieldCorrect = false;
			}
			a2Value = Double.parseDouble(newValue);
			a2StringValue = newValue;
		}
	}

	private static void addFocusListenerToA2Field(TextField a2, DimensionsOfCrossSectionOfConcrete dimensions) {
		a2.focusedProperty().addListener(new FocusListenerOfA2(a2, dimensions));
	}

	private static class FocusListenerOfA2 implements ChangeListener<Boolean> {

		TextField a2;
		DimensionsOfCrossSectionOfConcrete dimensions;

		protected FocusListenerOfA2(TextField a2, DimensionsOfCrossSectionOfConcrete dimensions) {
			this.a2 = a2;
			this.dimensions = dimensions;
		}

		private void ifInputWasIncorectChangeValueToInitial() {
			if (isInputOfA2TextFieldCorrect == false) {
				setA2InitialValue(a2);
			}
		}

		private void ifa2IsGreaterThen04hChangeValueTo04h(DimensionsOfCrossSectionOfConcrete dimensions) {
			if (a2Value > 0.4 * dimensions.getH()) {
				a2.setText(Double.toString(0.4 * dimensions.getH()));
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasIncorectChangeValueToInitial();
			}
			ifa2IsGreaterThen04hChangeValueTo04h(dimensions);

		}
	}

	private static void setA2InitialValue(TextField aDistanceTextFieldOfA2) {
		aDistanceTextFieldOfA2.setText("0.05");
	}

}
