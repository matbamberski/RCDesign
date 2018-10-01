package GUI.ReinforcementDesignLibraryControllers;


import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mainalgorithm.InternalForces;
import materials.DimensionsOfCrossSectionOfConcrete;
import util.InputValidation;
import util.StringToDouble;

public class CrossSectionTypeController {
	

	public static void addPorpertiesToCrossSectionTypeChoiceBox(ChoiceBox<String> crossSectionTypeChoiceBox,
			TextField bEff, TextField tW, Label bEffLabel, Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete, HBox hBoxCombination,
			VBox befftHftVBox, AnchorPane mEdAnchorPane, HBox nEdHBox, ArrayList<TextField> list1,
			ArrayList<TextField> list2, ArrayList<TextField> list3, TextField befft, CheckBox columnCheckBox, InternalForces forces,
			TextField momentMmax, TextField normalnaMmax, ChoiceBox<String> typeOfLoadChoiceBox) {
		
		ChoiceBoxListener choiceBox = new ChoiceBoxListener(crossSectionTypeChoiceBox, bEff, tW, bEffLabel, bEffLowerLabel, 
				tWLabel, tWLowerLabel, dimensionsOfCrossSectionOfConcrete, hBoxCombination, befftHftVBox, mEdAnchorPane, nEdHBox, 
				list1, list2, list3, befft, typeOfLoadChoiceBox);
		initializeChoiceBox(crossSectionTypeChoiceBox);
		addListenerToChoiceBox(crossSectionTypeChoiceBox, bEff, tW, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
				dimensionsOfCrossSectionOfConcrete, hBoxCombination, befftHftVBox, mEdAnchorPane, nEdHBox, list1, list2,
				list3, befft, choiceBox);
		setCrossSectionTypeCBInitialValue(crossSectionTypeChoiceBox);
		addListenerToCheckBox(choiceBox, befft, tW, bEffLabel, bEffLowerLabel, tWLabel, 
				tWLowerLabel, hBoxCombination, dimensionsOfCrossSectionOfConcrete, 
				befftHftVBox, mEdAnchorPane, nEdHBox, list1, list3, columnCheckBox, crossSectionTypeChoiceBox, forces,
				momentMmax, normalnaMmax, typeOfLoadChoiceBox);
	}

	public static void addPropertiesToBTextField(TextField b, TextField beff, TextField befft,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerToBTextField(b, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerToBTextField(b, beff, befft);
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
		addFocusListenerToBEffTextField(bEff, b);
		setBEffInitialValue(bEff, b);
	}

	public static void addPropertiesToBEfftTextField(TextField bEfft, TextField b,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete) {
		addListenerToBEfftTextField(bEfft, dimensionsOfCrossSectionOfConcrete);
		addFocusListenerToBEfftTextField(bEfft, b);
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
		cb.setItems((FXCollections.observableArrayList("Prostok¹tny", "Teowy"
				//,"S³up"
				)));
	}
	
	private static void addListenerToCheckBox(ChoiceBoxListener choiceBox, TextField bEff, TextField tW, Label bEffLabel, Label bEffLowerLabel,
    		Label tWLabel, Label tWLowerLabel, HBox hBoxCombination, DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete,
    		VBox befftHftVBox, AnchorPane mEdAnchorPane, HBox nEdHBox, ArrayList<TextField> list1, ArrayList<TextField> list3, CheckBox cb, 
    		ChoiceBox<String> crossSectionTypeChoiceBox, InternalForces forces, TextField momentMmax, 
    		TextField normalnaMmax, ChoiceBox<String> typeOfLoadChoiceBox) {
		cb.selectedProperty().addListener(new CheckBoxListener(choiceBox, bEff, tW, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel, 
				hBoxCombination, dimensionsOfCrossSectionOfConcrete, befftHftVBox, mEdAnchorPane, nEdHBox, 
				list1, list3, crossSectionTypeChoiceBox, forces, momentMmax, normalnaMmax, typeOfLoadChoiceBox));
	}
	
	
	private static void addListenerToChoiceBox(ChoiceBox<String> crossSectionTypeChoiceBox, TextField bEff,
			TextField tW, Label bEffLabel, Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel,
			DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete, HBox hBoxCombination,
			VBox befftHftVBox, AnchorPane mEdAnchorPane, HBox nEdHBox, ArrayList<TextField> list1,
			ArrayList<TextField> list2, ArrayList<TextField> list3, TextField befft, ChoiceBoxListener choiceBox) {
		crossSectionTypeChoiceBox.getSelectionModel().selectedIndexProperty()
				.addListener(choiceBox);
		
		//.addListener(new ChoiceBoxListener(crossSectionTypeChoiceBox, bEff, tW, bEffLabel, bEffLowerLabel,
				//tWLabel, tWLowerLabel, dimensionsOfCrossSectionOfConcrete, hBoxCombination, befftHftVBox,
				//mEdAnchorPane, nEdHBox, list1, list2, list3, befft));
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

	private static void addListenerToBEffTextField(TextField bEff,
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

	private static void addFocusListenerToBEffTextField(TextField bEff, TextField b) {
		bEff.focusedProperty().addListener(new BEffTextFieldFocusListener(bEff, b));
	}

	private static class BEffTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField bEff;
		TextField b;

		protected BEffTextFieldFocusListener(TextField bEff, TextField b) {
			this.bEff = bEff;
			this.b = b;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (isBEffTextFieldInputCorrect == false) {
				setBEffInitialValue(bEff, b);
			}
		}

		private void checkBwithBEff() {
			if (BeffValue < BValue) {
				bEff.setText(BString);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
			checkBwithBEff();
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
		crossSectionTypeChoiceBox.setValue("Prostok¹tny");
	}

	private static void setBEffInitialValue(TextField bEff, TextField b) {
		bEff.setText(b.getText());
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
		private AnchorPane mEdAnchorPane;
		private HBox nEdHBox;
		private ArrayList<TextField> list1;
		private ArrayList<TextField> list2;
		private ArrayList<TextField> list3;
		private TextField befft;
		private ChoiceBox<String> typeOfLoadChoiceBox;
		
		protected ChoiceBoxListener(ChoiceBox<String> crossSectionTypeChoiceBox, TextField bEff, TextField tW,
				Label bEffLabel, Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel,
				DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete, HBox hBoxCombination,
				VBox befftHftVBox, AnchorPane mEdAnchorPane, HBox nEdHBox, ArrayList<TextField> list1,
				ArrayList<TextField> list2, ArrayList<TextField> list3, TextField befft, ChoiceBox<String> typeOfLoadChoiceBox) {
			this.bEff = bEff;
			this.tW = tW;
			this.bEffLabel = bEffLabel;
			this.bEffLowerLabel = bEffLowerLabel;
			this.tWLabel = tWLabel;
			this.tWLowerLabel = tWLowerLabel;
			this.hBoxCombination = hBoxCombination;
			this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;

			this.befftHftVBox = befftHftVBox;
			this.mEdAnchorPane = mEdAnchorPane;
			this.nEdHBox = nEdHBox;
			this.list1 = list1;
			this.list2 = list2;
			this.list3 = list3;
			this.befft = befft;
			this.typeOfLoadChoiceBox = typeOfLoadChoiceBox;
		}

		
		@Override
		public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue) {
			setTCrossSectionTextFields(bEff, tW, newValue.intValue(), bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
					hBoxCombination, befftHftVBox, mEdAnchorPane, nEdHBox, list1, list2, list3, typeOfLoadChoiceBox);
			/// arg0 - informacje co to itp System.out.println(arg0);
			/// arg1 poprzedni wybor System.out.println(arg1);
			/// newValue - nowy wybor System.out.println(newValue);
		}

		private void setTCrossSectionTextFields(TextField bEff, TextField tW, int choice, Label bEffLabel,
				Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox,
				AnchorPane mEdAnchorPane, HBox nEdHBox, ArrayList<TextField> list1, ArrayList<TextField> list2,
				ArrayList<TextField> list3, ChoiceBox<String> typeOfLoadChoiceBox) {
			if (choice == 0) {
				setTCrossSectionTextFieldsInvisible(bEff, tW, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
						hBoxCombination, befftHftVBox, mEdAnchorPane, nEdHBox, typeOfLoadChoiceBox);
				setBeamToRectangular(dimensionsOfCrossSectionOfConcrete, list1);
			} else if (choice == 1) {
				setTCrossSectionTextFieldsVisible(bEff, tW, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel,
						hBoxCombination, befftHftVBox, mEdAnchorPane, nEdHBox, typeOfLoadChoiceBox);
				setBeamToTrapeze(dimensionsOfCrossSectionOfConcrete, list2);
			} else {
				/*
				WYBÓR KOLUMNY W LIŒCIE 
				setColumnCrossSectionTextFieldsInvisible(bEff, tW,
				 bEffLabel, tWLabel, bEffLowerLabel, tWLowerLabel, hBoxCombination,
				 befftHftVBox, mEdAnchorPane, nEdHBox);
				 setColumn(dimensionsOfCrossSectionOfConcrete, list3);
				 */
			}
		}

		private void setColumn(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete,
				ArrayList<TextField> list) {
			dimensionsOfCrossSectionOfConcrete.setIsColumn(true);
			dimensionsOfCrossSectionOfConcrete.setisBeamRectangular(false);
			setTextFieldsToZero(list);
			bEff.setText(((Double) dimensionsOfCrossSectionOfConcrete.getB()).toString());
			befft.setText(((Double) dimensionsOfCrossSectionOfConcrete.getB()).toString());
		}

		private void setBeamToRectangular(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete,
				ArrayList<TextField> list) {
			dimensionsOfCrossSectionOfConcrete.setisBeamRectangular(true);
			dimensionsOfCrossSectionOfConcrete.setIsColumn(false);
			setTextFieldsToZero(list);
			bEff.setText(((Double) dimensionsOfCrossSectionOfConcrete.getB()).toString());
			befft.setText(((Double) dimensionsOfCrossSectionOfConcrete.getB()).toString());
		}

		private void setBeamToTrapeze(DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete,
				ArrayList<TextField> list) {
			dimensionsOfCrossSectionOfConcrete.setisBeamRectangular(false);
			dimensionsOfCrossSectionOfConcrete.setIsColumn(false);
			setTextFieldsToZero(list);
		}

		private void setTCrossSectionTextFieldsVisible(TextField bEff, TextField tW, Label bEffLabel,
				Label bEffLowerLabel, Label tWLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox,
				AnchorPane mEdAnchorPane, HBox nEdHBox, ChoiceBox<String> typeOfLoadChoiceBox) {
			bEff.setVisible(true);
			tW.setVisible(true);
			bEffLabel.setVisible(true);
			bEffLowerLabel.setVisible(true);
			tWLabel.setVisible(true);
			tWLowerLabel.setVisible(true);
			hBoxCombination.setVisible(false);
			befftHftVBox.setVisible(true);
			mEdAnchorPane.setVisible(true);
			nEdHBox.setVisible(false);
			befft.setVisible(true);
			typeOfLoadChoiceBox.setVisible(true);
		}

		private void setTCrossSectionTextFieldsInvisible(TextField bEff, TextField tW, Label bEffLabel, Label tWLabel,
				Label bEffLowerLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox,
				AnchorPane mEdAnchorPane, HBox nEdHBox, ChoiceBox<String> typeOfLoadChoiceBox) {
			bEff.setVisible(false);
			tW.setVisible(false);
			bEffLabel.setVisible(false);
			bEffLowerLabel.setVisible(false);
			tWLabel.setVisible(false);
			tWLowerLabel.setVisible(false);
			hBoxCombination.setVisible(false);
			befftHftVBox.setVisible(false);
			mEdAnchorPane.setVisible(true);
			nEdHBox.setVisible(true);
			typeOfLoadChoiceBox.setVisible(true);
		}

		private void setColumnCrossSectionTextFieldsInvisible(TextField bEff, TextField tW, Label bEffLabel,
				Label tWLabel, Label bEffLowerLabel, Label tWLowerLabel, HBox hBoxCombination, VBox befftHftVBox,
				AnchorPane mEdAnchorPane, HBox nEdHBox, ChoiceBox<String> typeOfLoadChoiceBox) {
			bEff.setVisible(false);
			tW.setVisible(false);
			bEffLabel.setVisible(false);
			bEffLowerLabel.setVisible(false);
			tWLabel.setVisible(false);
			tWLowerLabel.setVisible(false);
			hBoxCombination.setVisible(true);
			befftHftVBox.setVisible(false);
			mEdAnchorPane.setVisible(false);
			nEdHBox.setVisible(false);
			typeOfLoadChoiceBox.setVisible(false);
		}

		private void setTextFieldsToZero(ArrayList<TextField> list) {
			for (TextField tf : list) {
				tf.setText("0");
			}
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
				BefftValue = Double.parseDouble(newValue);
				BefftString = newValue;
			} else {
				isBEfftTextFieldInputCorrect = false;
				BefftValue = 0;
				BefftString = "0";
			}
		}

	}

	private static void addFocusListenerToBEfftTextField(TextField bEfft, TextField b) {
		bEfft.focusedProperty().addListener(new BEfftTextFieldFocusListener(bEfft, b));
	}

	private static class BEfftTextFieldFocusListener implements ChangeListener<Boolean> {

		TextField bEfft;
		TextField b;

		protected BEfftTextFieldFocusListener(TextField bEfft, TextField b) {
			this.bEfft = bEfft;
			this.b = b;
		}

		private void ifInputWasInCorrectSetValueToInitial() {
			if (isBEfftTextFieldInputCorrect == false) {
				setBEfftInitialValue(bEfft, b);
			}
		}

		private void checkBWithBefft() {
			if (BefftValue < BValue) {
				bEfft.setText(BString);
			}
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				ifInputWasInCorrectSetValueToInitial();
			}
			checkBWithBefft();
		}

	}

	private static void setBEfftInitialValue(TextField bEfft, TextField b) {
		bEfft.setText(b.getText());
	}

	private static void sethftInitialValue(TextField hft) {
		hft.setText("0");
	}
	
			
	private static class CheckBoxListener implements ChangeListener<Boolean>{
		private ChoiceBoxListener choiceBox;
		private TextField bEff;
        private TextField tW;
        private Label bEffLabel;
        private Label bEffLowerLabel;
        private Label tWLabel;
        private Label tWLowerLabel;
        private HBox hBoxCombination;
        private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;
        private VBox befftHftVBox;
        private AnchorPane mEdAnchorPane;
        private HBox nEdHBox;
        private ArrayList<TextField> list1;
        private ArrayList<TextField> list3;
        private ChoiceBox<String> crossSectionTypeChoiceBox;
        private InternalForces forces;
        private TextField momentMmax;
        private TextField normalnaMmax;
        private ChoiceBox<String> typeOfLoadChoiceBox;
		
		
        protected CheckBoxListener(ChoiceBoxListener choiceBox, TextField bEff, TextField tW, Label bEffLabel, Label bEffLowerLabel,
        		Label tWLabel, Label tWLowerLabel, HBox hBoxCombination, DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete,
        		VBox befftHftVBox, AnchorPane mEdAnchorPane, HBox nEdHBox, ArrayList<TextField> list1, ArrayList<TextField> list3, 
        		ChoiceBox<String> crossSectionTypeChoiceBox, InternalForces forces, TextField momentMmax, TextField normalnaMmax,
        		ChoiceBox<String> typeOfLoadChoiceBox) {
			this.choiceBox = choiceBox;
			this.bEff = bEff;
            this.tW = tW;
            this.bEffLabel = bEffLabel;
            this.bEffLowerLabel = bEffLowerLabel;
            this.tWLabel = tWLabel;
            this.tWLowerLabel = tWLowerLabel;
            this.hBoxCombination = hBoxCombination;
            this.dimensionsOfCrossSectionOfConcrete = dimensionsOfCrossSectionOfConcrete;
            this.befftHftVBox = befftHftVBox;
            this.mEdAnchorPane = mEdAnchorPane;
            this.nEdHBox = nEdHBox;
            this.list1 = list1;
            this.list3 = list3;
            this.crossSectionTypeChoiceBox = crossSectionTypeChoiceBox;
            this.forces = forces;
            this.normalnaMmax = normalnaMmax;
            this.momentMmax = momentMmax;
            this.typeOfLoadChoiceBox = typeOfLoadChoiceBox;
			
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2 == false) {
				crossSectionTypeChoiceBox.setValue("Prostok¹tny");
				forces.setmEd(0.0);
				forces.setM0Ed(0.0);
				forces.setnEd(0.0);
				forces.setMomentMmax(0.0);
				forces.setNormalnaMmax(0.0);
				forces.setMomentMmin(0.0);
				forces.setNormalnaMmin(0.0);
				forces.setMomentMmax(0.0);
				forces.setNormalnaMmax(0.0);
				forces.setMomentNmin(0.0);
				forces.setNormalnaNmin(0.0);
				
				choiceBox.setBeamToRectangular(dimensionsOfCrossSectionOfConcrete, list1);
				choiceBox.setTCrossSectionTextFieldsInvisible(bEff, tW, bEffLabel, tWLabel, bEffLowerLabel, 
						tWLowerLabel, hBoxCombination, befftHftVBox, mEdAnchorPane, nEdHBox, typeOfLoadChoiceBox);
				forces.setmEd(forces.getM0Ed());
			} else {
				//momentMmax.setText(String.valueOf(forces.getmEd()));
				//normalnaMmax.setText(String.valueOf(forces.getnEd()));
				crossSectionTypeChoiceBox.setValue("Prostok¹tny");
				forces.setmEd(0.0);
				forces.setM0Ed(0.0);
				forces.setnEd(0.0);
				forces.setMomentMmax(0.0);
				forces.setNormalnaMmax(0.0);
				forces.setMomentMmin(0.0);
				forces.setNormalnaMmin(0.0);
				forces.setMomentMmax(0.0);
				forces.setNormalnaMmax(0.0);
				forces.setMomentNmin(0.0);
				forces.setNormalnaNmin(0.0);
				
				choiceBox.setColumn(dimensionsOfCrossSectionOfConcrete, list3);
				choiceBox.setColumnCrossSectionTextFieldsInvisible(bEff, tW, bEffLabel, tWLabel, bEffLowerLabel, 
						tWLowerLabel, hBoxCombination, befftHftVBox, mEdAnchorPane, nEdHBox, typeOfLoadChoiceBox);
				forces.setmEd(forces.getM0Ed());
			}
			
		}
		
	}

}
