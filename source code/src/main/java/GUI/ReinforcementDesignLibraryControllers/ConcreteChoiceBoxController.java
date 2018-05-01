package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import materials.Concrete;

public class ConcreteChoiceBoxController {

	public static void addPropertiesToConcreteChoiceBox(Concrete concrete, ChoiceBox<String> concreteChoiceBox) {
		initializeConcreteTypesIntoChoiceBox(concreteChoiceBox);
		addListenerToConcreteCB(concrete, concreteChoiceBox);
		setConcreteCBInitialValue(concreteChoiceBox);
	}

	private static void initializeConcreteTypesIntoChoiceBox(ChoiceBox<String> concreteChoiceBox) {
		concreteChoiceBox.setItems((FXCollections.observableArrayList(Concrete.concreteClass)));
	}

	private static void addListenerToConcreteCB(Concrete concrete, ChoiceBox<String> concreteChoiceBox) {
		concreteChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new choiceListener(concrete));
	}

	private static void setConcreteCBInitialValue(ChoiceBox<String> concreteChoiceBox) {
		concreteChoiceBox.setValue("C30/37");
	}

	private static class choiceListener implements ChangeListener<Number> {

		private Concrete concrete;

		protected choiceListener(Concrete concrete) {
			this.concrete = concrete;

		}

		@Override
		public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
			System.out.println(Concrete.concreteClass[newValue.intValue()]);
			concrete.setConcreteClassNumber(newValue.intValue());
			System.out.println(concrete.getFCd() + " fCd");

		}

	}
}
