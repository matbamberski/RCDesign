package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import mainalgorithm.InternalForces;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import util.InputValidation;

public class AdditionalVariablesController {

	public static void addPropertiesToLEffTF(DimensionsOfCrossSectionOfConcrete dimensions, TextField tf) {
		addListenerToLEffTF(dimensions, tf);
		addFocusListenerLeff(tf);
		setInitialValueLEffTF(tf);
	}

	public static void addPropertiesToAlfaMTF(InternalForces forces, TextField tf) {
		addListenerToAlfaM(forces, tf);
		addFocusListenerAlfaM(tf);
		setInitialValueAlfaMTF(tf);
	}

	public static void addPropertiesToT0TF(Concrete concrete, TextField tf) {
		addListenerToT0TF(concrete, tf);
		addFocusListenerT0(tf);
		setInitialValueT0TF(tf);
	}

	public static void addPropertiesToRHTF(Concrete concrete, TextField tf) {
		addListenerToRHTF(concrete, tf);
		addFocusListenerRh(tf);
		setInitialValueRHTF(tf);
	}

	public static void addPropertiesToCementClass(ChoiceBox<String> cb, Cement cement) {
		addInitialValuesToCementClassCB(cb);
		addListenerToCementTypeCB(cb, cement);
		setInitialValueOfCementTypeCB(cb);
	}

	public static void addPropertiesToTypeOfLoad(InternalForces forces, ChoiceBox<String> cb) {
		addInitialValuesToTypeOfLoadsCB(cb);
		addListenerToTypeOfLoadsCB(forces, cb);
		setInitialValueOfTypeOfLoadsCB(cb);
	}

	public static void addPropertiesToWLimTF(DimensionsOfCrossSectionOfConcrete dimensions, TextField tf) {
		addListenerToWLimTF(dimensions, tf);
		addFocusListenerWlim(tf);
		setInitialValueWLimTF(tf);
	}

	public static void addPropertiesToALimTF(DimensionsOfCrossSectionOfConcrete dimensions, TextField tf) {
		addListenerToALimTF(dimensions, tf);
		setInitialValueOfALimTF(tf);
	}

	// Leff TF

	private static void addListenerToLEffTF(DimensionsOfCrossSectionOfConcrete dimensions, TextField tf) {
		tf.textProperty().addListener(new LEffListener(dimensions));
	}

	private static boolean isLeffInputCorrect;

	private static class LEffListener implements ChangeListener<String> {

		DimensionsOfCrossSectionOfConcrete dimensions;

		protected LEffListener(DimensionsOfCrossSectionOfConcrete dimensions) {
			this.dimensions = dimensions;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.lEffTextFieldInputValidation(newValue)) {
				dimensions.setlEff(Double.parseDouble(newValue));
				isLeffInputCorrect = true;
			} else {
				isLeffInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerLeff(TextField tf) {
		tf.focusedProperty().addListener(new LEffFocusListener(tf));
	}

	private static class LEffFocusListener implements ChangeListener<Boolean> {
		TextField tf;

		protected LEffFocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifLEffWasIncorectSetValueToInitial() {
			if (isLeffInputCorrect == false) {
				setInitialValueLEffTF(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifLEffWasIncorectSetValueToInitial();
			}

		}

	}

	private static void setInitialValueLEffTF(TextField tf) {
		tf.setText("6.00");
	}

	// alfaM tf

	private static void addListenerToAlfaM(InternalForces forces, TextField tf) {
		tf.textProperty().addListener(new AlfaMListener(forces));
	}

	private static boolean isAlfaMInputCorrect;

	private static class AlfaMListener implements ChangeListener<String> {

		InternalForces forces;

		protected AlfaMListener(InternalForces forces) {
			this.forces = forces;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.alfaMTextFieldInputValidation(newValue)) {
				forces.setAlfaM(Double.parseDouble(newValue));
				isAlfaMInputCorrect = true;
			} else {
				isAlfaMInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerAlfaM(TextField tf) {
		tf.focusedProperty().addListener(new AlfaMFocusListener(tf));
	}

	private static class AlfaMFocusListener implements ChangeListener<Boolean> {
		TextField tf;

		protected AlfaMFocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifAlfaMfWasIncorectSetValueToInitial() {
			if (isAlfaMInputCorrect == false) {
				setInitialValueAlfaMTF(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifAlfaMfWasIncorectSetValueToInitial();
			}

		}

	}

	private static void setInitialValueAlfaMTF(TextField tf) {
		tf.setText("0.104");
	}

	// t0 tf
	private static void addListenerToT0TF(Concrete concrete, TextField tf) {
		tf.textProperty().addListener(new T0Listener(concrete));
	}

	private static boolean isT0InputCorrect;

	private static class T0Listener implements ChangeListener<String> {

		Concrete concrete;

		protected T0Listener(Concrete concrete) {
			this.concrete = concrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if (InputValidation.t0TextFieldInputValidation(newValue)) {
				concrete.setT0(Integer.parseInt(newValue));
				isT0InputCorrect = true;

			} else {
				isT0InputCorrect = false;
			}
		}
	}

	private static void addFocusListenerT0(TextField tf) {
		tf.focusedProperty().addListener(new T0FocusListener(tf));
	}

	private static class T0FocusListener implements ChangeListener<Boolean> {
		TextField tf;

		protected T0FocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifT0WasIncorectSetValueToInitial() {
			if (isT0InputCorrect == false) {
				setInitialValueT0TF(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifT0WasIncorectSetValueToInitial();
			}

		}

	}

	private static void setInitialValueT0TF(TextField tf) {
		tf.setText("28");
	}

	// Rh tf

	private static void addListenerToRHTF(Concrete concrete, TextField tf) {
		tf.textProperty().addListener(new RHListener(concrete));
	}

	private static boolean isRhInputCorrect;

	private static class RHListener implements ChangeListener<String> {

		Concrete concrete;

		protected RHListener(Concrete concrete) {
			this.concrete = concrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if (InputValidation.RhTextFieldInputValidation(newValue)) {
				concrete.setrH(Double.parseDouble(newValue));
				isRhInputCorrect = true;
			} else {
				isRhInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerRh(TextField tf) {
		tf.focusedProperty().addListener(new RhFocusListener(tf));
	}

	private static class RhFocusListener implements ChangeListener<Boolean> {
		TextField tf;

		protected RhFocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifRhWasIncorectSetValueToInitial() {
			if (isRhInputCorrect == false) {
				setInitialValueRHTF(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifRhWasIncorectSetValueToInitial();
			}

		}

	}

	private static void setInitialValueRHTF(TextField tf) {
		tf.setText("50");
	}

	// cement class choicebox

	private static void addInitialValuesToCementClassCB(ChoiceBox<String> cb) {
		cb.setItems(FXCollections.observableArrayList("Cement klasy S", "Cement klasy N", "Cement klasy R"));
	}

	private static void addListenerToCementTypeCB(ChoiceBox<String> cb, Cement cement) {
		cb.getSelectionModel().selectedIndexProperty().addListener(new CementTypeListener(cement));
	}

	private static class CementTypeListener implements ChangeListener<Number> {

		Cement cement;

		protected CementTypeListener(Cement cement) {
			this.cement = cement;
		}

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			cement = new Cement(newValue.intValue());
		}

	}

	private static void setInitialValueOfCementTypeCB(ChoiceBox<String> cb) {
		cb.setValue("Cement klasy N");
	}
	// type of loads choice box

	private static void addInitialValuesToTypeOfLoadsCB(ChoiceBox<String> cb) {
		cb.setItems(FXCollections.observableArrayList("Obc. d≥ugotrwa≥e", "Obc. doraüne"));
	}

	private static void addListenerToTypeOfLoadsCB(InternalForces forces, ChoiceBox<String> concreteChoiceBox) {
		concreteChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new TypeOfLoadsListener(forces));
	}

	private static class TypeOfLoadsListener implements ChangeListener<Number> {

		InternalForces forces;

		protected TypeOfLoadsListener(InternalForces forces) {
			this.forces = forces;
		}

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			forces.checkIsLoadSustained(newValue.intValue());

		}

	}

	private static void setInitialValueOfTypeOfLoadsCB(ChoiceBox<String> cb) {
		cb.setValue("Obc. d≥ugotrwa≥e");
	}

	// w lim textfield

	private static void addListenerToWLimTF(DimensionsOfCrossSectionOfConcrete dimensions, TextField tf) {
		tf.textProperty().addListener(new WLimListener(dimensions));
	}

	private static boolean isWlimInputCorrect;

	private static class WLimListener implements ChangeListener<String> {

		DimensionsOfCrossSectionOfConcrete dimensions;

		protected WLimListener(DimensionsOfCrossSectionOfConcrete dimensions) {
			this.dimensions = dimensions;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			arg2 = arg2.replace(",", ".");
			if (InputValidation.wLimTextFieldInputValidation(arg2)) {
				dimensions.setwLim(Double.parseDouble(arg2));
				isWlimInputCorrect = true;
			} else {
				isWlimInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerWlim(TextField tf) {
		tf.focusedProperty().addListener(new WlimFocusListener(tf));
	}

	private static class WlimFocusListener implements ChangeListener<Boolean> {
		TextField tf;

		protected WlimFocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifWlimWasIncorectSetValueToInitial() {
			if (isWlimInputCorrect == false) {
				setInitialValueWLimTF(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue == false) {
				ifWlimWasIncorectSetValueToInitial();
			}

		}

	}

	private static void setInitialValueWLimTF(TextField tf) {
		tf.setText("0.3");
	}

	// alim Tf

	private static void addListenerToALimTF(DimensionsOfCrossSectionOfConcrete dimensions, TextField tf) {
		tf.textProperty().addListener(new ALimTFListener(dimensions));
	}

	private static class ALimTFListener implements ChangeListener<String> {
		DimensionsOfCrossSectionOfConcrete dimensions;

		protected ALimTFListener(DimensionsOfCrossSectionOfConcrete dimensions) {
			this.dimensions = dimensions;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			dimensions.setaLim(Double.parseDouble(arg2));
		}

	}

	private static void setInitialValueOfALimTF(TextField tf) {
		tf.setText("3");
	}

}
