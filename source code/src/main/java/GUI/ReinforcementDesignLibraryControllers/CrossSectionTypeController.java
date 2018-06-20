package GUI.ReinforcementDesignLibraryControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import materials.DimensionsOfCrossSectionOfConcrete;
import util.InputValidation;
import util.StringToDouble;

public class CrossSectionTypeController {

	public static void addPorpertiesToCrossSectionTypeChoiceBox(ChoiceBox<String> crossSectionTypeChoiceBox,
			TextField bEff, TextField tW, Label bEffLabel, Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete, HBox hBoxCombination, VBox befftHftVBox) {
		initializeChoiceBox(crossSectionTypeChoiceBox);
		addListenerToChoiceBox(crossSectionTypeChoiceBox, bEff, tW, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
				dimensionsOfCrossSectionOfConcrete, hBoxCombination, befftHftVBox);
		setCrossSectionTypeCBInitialValue(crossSectionTypeChoiceBox);
	}

	public static void addPropertiesToBTextField(TextField b, TextField beff, TextField befft,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerToBTextField(b, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerToBTextField(b);
		setBInitialValue(b);
	}

	public static void addPropertiesToHTextField(TextField h,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerToHTextField(h, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerToHTextField(h);
		setH(h);
	}

	public static void addPropertiesToBEffTextField(TextField bEff, TextField b,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerToBEffTextField(bEff, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerToBEffTextField(bEff);
		setBEffInitialValue(bEff, b);
	}
	
	
	public static void addPropertiesToBEfftTextField(TextField bEfft, TextField b,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerToBEfftTextField(bEfft, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerToBEfftTextField(bEfft);
		setBEfftInitialValue(bEfft, b);
	}
	
	
	public static void addPropertiesTohftTextField(TextField hft,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerTohftTextField(hft, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerTohftTextField(hft);
		sethftInitialValue(hft);
	}

	public static void addPropertiesToTWTextField(TextField tW,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerToTWTextField(tW, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerToTwTextField(tW);
		setTWInitialValue(tW);
	}

	public static void addPropertiesToCNomTextField(TextField tf, DimensionsOfCrossSectionOfConcrete dimensions) {
		addListenerToCNomTF(tf, dimensions);
		addFocusListenerToCNomTF(tf);
		setInitialCNomValue(tf);

	}

	private static void initializeChoiceBox(ChoiceBox<String> cb) {
		cb.setItems((FXCollections.observableArrayList("Przekr�j prostok�tny", "Przekr�j teowy", "S�up")));
	}

	private static void addListenerToChoiceBox(ChoiceBox<String> crossSectionTypeChoiceBox, TextField bEff,
			TextField tW, Label bEffLabel, Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete, HBox hBoxCombination, VBox befftHftVBox) {
		crossSectionTypeChoiceBox.getSelectionModel().selectedIndexProperty()
				.addListener(new ChoiceBoxListener(crossSectionTypeChoiceBox, bEff, tW, bEffLabel, bEffLowerLabel,
						tWLabel, tWLowerLabel, dimensionsOfCrossSectionOfConcrete, hBoxCombination,	befftHftVBox));
	}

	// b text fiedl

	private static boolean isBTextFieldInputCorrect;
	private static String BString;
	private static double BValue;

	private static void addListenerToBTextField(TextField b,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		b.textProperty().addListener(new BTextFieldListener(dimensionsOfCrossSectionOfConcrete));
	}

	private static class BTextFieldListener implements ChangeListener<String> {
		private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;

		protected BTextFieldListener(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.dimensionTextFieldInputValidation(newValue)) {
				dimensionsOfCrossSectionOfConcrete.setB(StringToDouble.stringToDouble(newValue));
				isBTextFieldInputCorrect = true;
				BValue = Double.parseDouble(newValue);
				BString = newValue;
			} else {
				isBTextFieldInputCorrect = false;
				BValue = 0.0;
				BString = "0";
			}
		}

	}

	private static void addFocusListenerToBTextField(TextField b, TextField beff, TextField befft) {
		b.focusedProperty().addListener(new BTextFieldFocusListener(b, beff, befft));
	}

	private static class BTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField b;
		TextField beff;
		TextField befft;

		protected BTextFieldFocusListener(TextField b, TextField beff, TextField befft) {
			this.b = b;
			this.beff = beff;
			this.befft = befft;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (isBTextFieldInputCorrect == false) {
				setBInitialValue(b);
			}
		}
		
		private void checkBeff() {
			if (BValue > BeffValue) {
				beff.setText(BString);
			}
			if (BValue > BefftValue) {
				befft.setText(BString);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
			checkBeff();
		}

	}

	// h text field
	private static boolean isHTextFieldInputCorrect;

	private static void addListenerToHTextField(TextField h,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		h.textProperty().addListener(new HTextFieldListener(dimensionsOfCrossSectionOfConcrete));
	}

	private static class HTextFieldListener implements ChangeListener<String> {

		private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;

		protected HTextFieldListener(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.dimensionTextFieldInputValidation(newValue)) {
				dimensionsOfCrossSectionOfConcrete.setH(StringToDouble.stringToDouble(newValue));
				isHTextFieldInputCorrect = true;
			} else {
				isHTextFieldInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerToHTextField(TextField h) {
		h.focusedProperty().addListener(new HTextFieldFocusListener(h));
	}

	private static class HTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField h;

		protected HTextFieldFocusListener(TextField h) {
			this.h = h;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (isHTextFieldInputCorrect == false) {
				setH(h);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
		}

	}

	// beff text field
	private static boolean isBEffTextFieldInputCorrect;
	private static double BeffValue;
	private static String BeffString;


	private static void addListenerToBEffTextField(TextField bEff, TextField b,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		bEff.textProperty().addListener(new BEffTextFieldListener(dimensionsOfCrossSectionOfConcrete));
	}

	private static class BEffTextFieldListener implements ChangeListener<String> {

		private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;

		protected BEffTextFieldListener(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.dimensionTextFieldInputValidation(newValue)) {

				dimensionsOfCrossSectionOfConcrete.setbEff(StringToDouble.stringToDouble(newValue));
				isBEffTextFieldInputCorrect = true;
				BeffString = newValue;
				BeffValue = Double.parseDouble(newValue);
			} else {
				isBEffTextFieldInputCorrect = false;
				BeffString = "0";
				BeffValue = 0.0;
			}
		}

	}

	private static void addFocusListenerToBEffTextField(TextField bEff) {
		bEff.focusedProperty().addListener(new BEffTextFieldFocusListener(bEff));
	}
	


	private static class BEffTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField bEff;

		protected BEffTextFieldFocusListener(TextField bEff) {
			this.bEff = bEff;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (isBEffTextFieldInputCorrect == false) {
				setBEffInitialValue(bEff);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
		}

	}

	// tw text field

	private static boolean isTwTextFieldInputCorrect;

	private static void addListenerToTWTextField(TextField tW,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		tW.textProperty().addListener(new TWTextFieldListener(dimensionsOfCrossSectionOfConcrete));
	}

	private static class TWTextFieldListener implements ChangeListener<String> {

		private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;

		protected TWTextFieldListener(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.internalForcesTextFieldInputValidation(newValue)) {
				dimensionsOfCrossSectionOfConcrete.settW(StringToDouble.stringToDouble(newValue));
				isTwTextFieldInputCorrect = true;
			} else {
				isTwTextFieldInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerToTwTextField(TextField tW) {
		tW.focusedProperty().addListener(new TwTextFieldFocusListener(tW));
	}

	private static class TwTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField tW;

		protected TwTextFieldFocusListener(TextField tW) {
			this.tW = tW;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (isTwTextFieldInputCorrect == false) {
				setTWInitialValue(tW);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
		}

	}

	// cNom Tf;

	private static boolean isCnomTFInputCorrect;

	private static void addListenerToCNomTF(TextField tf, DimensionsOfCrossSectionOfConcrete dimensions) {
		tf.textProperty().addListener(new CNomTFListener(dimensions));
	}

	private static class CNomTFListener implements ChangeListener<String> {

		DimensionsOfCrossSectionOfConcrete dimensions;

		CNomTFListener(DimensionsOfCrossSectionOfConcrete dimensions) {
			this.dimensions = dimensions;
		}

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			if (InputValidation.cNomTextFieldInputValidation(arg2)) {
				dimensions.setcNom(Double.parseDouble(arg2));
				isCnomTFInputCorrect = true;
			} else

			{
				isCnomTFInputCorrect = false;
			}

		}
	}

	private static void addFocusListenerToCNomTF(TextField tf) {
		tf.focusedProperty().addListener(new CNomTFFocusListener(tf));
	}

	private static class CNomTFFocusListener implements ChangeListener<Boolean> {
		TextField tf;

		protected CNomTFFocusListener(TextField tf) {
			this.tf = tf;
		}

		private void ifCNomInputWasIncorrectChangeValueToInitial() {
			if (isCnomTFInputCorrect == false) {
				setInitialCNomValue(tf);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifCNomInputWasIncorrectChangeValueToInitial();
			}
		}

	}

	private static void setInitialCNomValue(TextField tf) {
		tf.setText("25");
	}
	// rest

	private static void setCrossSectionTypeCBInitialValue(ChoiceBox<String> crossSectionTypeChoiceBox) {
		crossSectionTypeChoiceBox.setValue("Przekr�j prostok�tny");
	}

	private static void setBEffInitialValue(TextField bEff) {
		bEff.setText("0");
	}

	private static void setTWInitialValue(TextField tW) {
		tW.setText("0");
	}

	private static void setBInitialValue(TextField b) {
		b.setText("0.30");
	}

	private static void setH(TextField h) {
		h.setText("0.60");
	}

	private static class ChoiceBoxListener implements ChangeListener<Number> {
		private TextField bEff;
		private TextField tW;
		private Label bEffLabel;
		private Label bEffLowerLabel;
		private Label tWLabel;
		private Label tWLowerLabel;
		private HBox hBoxCombination;
		private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;
		private VBox befftHftVBox;

		protected ChoiceBoxListener(ChoiceBox<String> crossSectionTypeChoiceBox, TextField bEff, TextField tW,
				Label bEffLabel, Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel,
				DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete, HBox hBoxCombination,VBox befftHftVBox) {
			this.bEff = bEff;
			this.tW = tW;
			this.bEffLabel = bEffLabel;
			this.bEffLowerLabel = bEffLowerLabel;
			this.tWLabel = tWLabel;
			this.tWLowerLabel = tWLowerLabel;
			this.hBoxCombination = hBoxCombination;
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;

			this.befftHftVBox = befftHftVBox;

		}

		@Override
		public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
			setTCrossSectionTextFields(bEff, tW, newValue.intValue(), bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
					hBoxCombination, befftHftVBox);
			/// arg0 - informacje co to itp System.out.println(arg0);
			/// arg1 poprzedni wybor System.out.println(arg1);
			/// newValue - nowy wybor System.out.println(newValue);
		}

		private void setTCrossSectionTextFields(TextField bEff, TextField tW, int choice, Label bEffLabel,
				Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox) {
			if (choice == 0) {
				setTCrossSectionTextFieldsInvisible(bEff, tW, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
						hBoxCombination, befftHftVBox);
				setBeamToRectangular(dimensionsOfCrossSectionOfConcrete);
			} else if (choice == 1) {
				setTCrossSectionTextFieldsVisible(bEff, tW, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
						hBoxCombination,befftHftVBox);
				setBeamToTrapeze(dimensionsOfCrossSectionOfConcrete);
			} else {
				setColumnCrossSectionTextFieldsInvisible(bEff, tW, bEffLabel, tWLabel, bEffLowerLabel, tWLowerLabel, hBoxCombination, befftHftVBox);
				setColumn(dimensionsOfCrossSectionOfConcrete);
			}
		}

		private void setColumn(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			dimensionsOfCrossSectionOfConcrete.setIsColumn(true);
			dimensionsOfCrossSectionOfConcrete.setisBeamRectangular(false);
		}

		private void setBeamToRectangular(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			dimensionsOfCrossSectionOfConcrete.setisBeamRectangular(true);
			dimensionsOfCrossSectionOfConcrete.setIsColumn(false);
		}

		private void setBeamToTrapeze(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			dimensionsOfCrossSectionOfConcrete.setisBeamRectangular(false);
			dimensionsOfCrossSectionOfConcrete.setIsColumn(false);
		}

		private void setTCrossSectionTextFieldsVisible(TextField bEff, TextField tW, Label bEffLabel,
				Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox) {
			bEff.setVisible(true);
			tW.setVisible(true);
			bEffLabel.setVisible(true);
			bEffLowerLabel.setVisible(true);
			tWLabel.setVisible(true);
			tWLowerLabel.setVisible(true);
			hBoxCombination.setVisible(false);
			befftHftVBox.setVisible(true);
		}

		private void setTCrossSectionTextFieldsInvisible(TextField bEff, TextField tW, Label bEffLabel, Label tWLabel,
				Label bEffLowerLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox) {
			bEff.setVisible(false);
			tW.setVisible(false);
			bEffLabel.setVisible(false);
			bEffLowerLabel.setVisible(false);
			tWLabel.setVisible(false);
			tWLowerLabel.setVisible(false);
			hBoxCombination.setVisible(false);
			befftHftVBox.setVisible(false);
		}

		private void setColumnCrossSectionTextFieldsInvisible(TextField bEff, TextField tW, Label bEffLabel,
				Label tWLabel, Label bEffLowerLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox) {
			bEff.setVisible(false);
			tW.setVisible(false);
			bEffLabel.setVisible(false);
			bEffLowerLabel.setVisible(false);
			tWLabel.setVisible(false);
			tWLowerLabel.setVisible(false);
			hBoxCombination.setVisible(true);
			befftHftVBox.setVisible(false);
		}
	}
	
	////////////////////////////
	
	
	
	private static boolean ishftTextFieldInputCorrect;

	private static void addListenerTohftTextField(TextField hft,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		hft.textProperty().addListener(new HftTextFieldListener(dimensionsOfCrossSectionOfConcrete));
	}

	private static class HftTextFieldListener implements ChangeListener<String> {

		private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;

		protected HftTextFieldListener(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.dimensionTextFieldInputValidation(newValue)) {

				dimensionsOfCrossSectionOfConcrete.setHft(StringToDouble.stringToDouble(newValue));
				ishftTextFieldInputCorrect = true;
			} else {
				ishftTextFieldInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerTohftTextField(TextField hft) {
		hft.focusedProperty().addListener(new HftTextFieldFocusListener(hft));
	}

	private static class HftTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField hft;

		protected HftTextFieldFocusListener(TextField hft) {
			this.hft = hft;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (ishftTextFieldInputCorrect == false) {
				sethftInitialValue(hft);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
		}

	}
	
	
	///////////////
	
	
	private static boolean isBEfftTextFieldInputCorrect;
	private static double BefftValue;
	private static String BefftString;

	private static void addListenerToBEfftTextField(TextField bEfft,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		bEfft.textProperty().addListener(new BEfftTextFieldListener(dimensionsOfCrossSectionOfConcrete));
	}

	private static class BEfftTextFieldListener implements ChangeListener<String> {

		private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;

		protected BEfftTextFieldListener(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			newValue = newValue.replace(",", ".");
			if (InputValidation.dimensionTextFieldInputValidation(newValue)) {

				dimensionsOfCrossSectionOfConcrete.setBefft(StringToDouble.stringToDouble(newValue));
				isBEfftTextFieldInputCorrect = true;
			} else {
				isBEfftTextFieldInputCorrect = false;
			}
		}

	}

	private static void addFocusListenerToBEfftTextField(TextField bEfft) {
		bEfft.focusedProperty().addListener(new BEfftTextFieldFocusListener(bEfft));
	}

	private static class BEfftTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField bEfft;

		protected BEfftTextFieldFocusListener(TextField bEfft) {
			this.bEfft = bEfft;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (isBEfftTextFieldInputCorrect == false) {
				setBEfftInitialValue(bEfft);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
		}

	}
	
	private static void setBEfftInitialValue(TextField bEfft) {
		bEfft.setText("0");
	}
	private static void sethftInitialValue(TextField hft) {
		hft.setText("0");
	}

}
