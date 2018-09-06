package GUI.ReinforcementDesignLibraryControllers;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import SLS.Sls;
import SLS.cracks.Scratch;
import SLS.creepCoeficent.CreepCoeficent;
import SLS.deflection.DeflectionControl;
import diagnosis.DiagnosisMainAlgorithm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.shearing.ShearingReinforcementAnalizer;
import util.ResultsToPDF;

public class SaveFileButtonController {

	// design scene

	private static Button designSaveButton;

	public static void addPropertiesToDesignSceneSaveButton(Button bt, Concrete concrete, Steel steel, Reinforcement reinforcement, InternalForces forces,
			DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch, CreepCoeficent creep,
			DeflectionControl deflection, CheckBox nominalCheckBox, NominalStiffness stiffness) {
		designSaveButton = bt;
		disableButtonDesign(bt);
		addClickListenerToDesignSceneSaveButton(bt, concrete, steel, reinforcement, forces, dimensions, sls, scratch, 
				creep, deflection, nominalCheckBox, stiffness);
	}

	private static void disableButtonDesign(Button bt) {
		bt.setDisable(true);
	}

	protected static void enableSaveButtonDesignScene() {
		designSaveButton.setDisable(false);
	}

	private static void addClickListenerToDesignSceneSaveButton(Button bt, Concrete concrete, Steel steel, Reinforcement reinforcement, InternalForces forces,
			DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch, CreepCoeficent creep,
			DeflectionControl deflection, CheckBox nominalCheckBox, NominalStiffness stiffness) {
		bt.setOnAction(new SaveButtonListenerDesignScene(concrete, steel, reinforcement, forces, 
				dimensions, sls, scratch, creep, deflection, nominalCheckBox, stiffness));
	}

	private static class SaveButtonListenerDesignScene implements EventHandler<ActionEvent> {
		Reinforcement reinforcement;
		InternalForces forces;
		DimensionsOfCrossSectionOfConcrete dimensions;
		Concrete concrete;
		Steel steel;
		Sls sls;
		Scratch scratch;
		CreepCoeficent creep;
		DeflectionControl deflection;
		CheckBox nominalCheckBox;
		NominalStiffness stiffness;
		

		protected SaveButtonListenerDesignScene(Concrete concrete, Steel steel, Reinforcement reinforcement, InternalForces forces, 
				DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, Scratch scratch, 
				CreepCoeficent creep, DeflectionControl deflection, CheckBox nominalCheckBox
				,NominalStiffness stiffness) {
			this.reinforcement = reinforcement;
			this.forces = forces;
			this.dimensions = dimensions;
			this.concrete = concrete;
			this.steel = steel;
			this.sls = sls;
			this.scratch = scratch;
			this.creep = creep;
			this.deflection = deflection;
			this.nominalCheckBox = nominalCheckBox;
			this.stiffness = stiffness;
			
		}

		@Override
		public void handle(ActionEvent arg0) {
			try {
				ResultsToPDF.saveDesingResultsToPDF(concrete, steel, reinforcement, forces, dimensions, 
						sls, scratch, creep, deflection, nominalCheckBox, stiffness);
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// diagnosis scene

	private static Button diagnosisSaveButton;

	public static void addPropertiesToDiagnosisSceneSaveButton(Button bt, Concrete concrete, Steel steel, Reinforcement reinforcement, InternalForces forces,
			DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, DiagnosisMainAlgorithm diagnosis, 
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, 
			CheckBox nominalCheckBox,NominalStiffness stiffness) {
		diagnosisSaveButton = bt;
		disableButtonDiagnosis(bt);
		addClickListenerToDiagnosisSceneSaveButton(bt, concrete, steel, reinforcement, forces, dimensions, sls, diagnosis,
				scratch, creep, deflection, nominalCheckBox, stiffness);
	}

	private static void disableButtonDiagnosis(Button bt) {
		bt.setDisable(true);
	}

	protected static void enableButtonDiagnosis() {
		if (diagnosisSaveButton.isDisable()) {
			diagnosisSaveButton.setDisable(false);
		}
	}

	private static void addClickListenerToDiagnosisSceneSaveButton(Button bt, Concrete concrete, Steel steel, Reinforcement reinforcement, InternalForces forces,
			DimensionsOfCrossSectionOfConcrete dimensions, Sls sls, DiagnosisMainAlgorithm diagnosis,
			Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, 
			CheckBox nominalCheckBox,NominalStiffness stiffness) {
		bt.setOnAction(new SaveButtonListenerDiagnosisScene(concrete, steel, reinforcement, forces, dimensions, sls, diagnosis, 
				scratch, creep, deflection, nominalCheckBox, stiffness));
	}

	private static class SaveButtonListenerDiagnosisScene implements EventHandler<ActionEvent> {
		Reinforcement reinforcement;
		InternalForces forces;
		DimensionsOfCrossSectionOfConcrete dimensions;
		Concrete concrete;
		Steel steel;
		Sls sls;
		DiagnosisMainAlgorithm diagnosis;
		Scratch scratch;
		CreepCoeficent creep;
		DeflectionControl deflection;
		CheckBox nominalCheckBox;
		NominalStiffness stiffness;

		protected SaveButtonListenerDiagnosisScene(Concrete concrete, Steel steel, Reinforcement reinforcement, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Sls sls,
				DiagnosisMainAlgorithm diagnosis, Scratch scratch, CreepCoeficent creep, DeflectionControl deflection, 
				CheckBox nominalCheckBox,NominalStiffness stiffness) {
			this.reinforcement = reinforcement;
			this.forces = forces;
			this.dimensions = dimensions;
			this.concrete = concrete;
			this.steel = steel;
			this.sls = sls;
			this.diagnosis = diagnosis;
			this.scratch = scratch;
			this.creep = creep;
			this.deflection = deflection;
			this.nominalCheckBox = nominalCheckBox;
			this.stiffness = stiffness;
		}

		@Override
		public void handle(ActionEvent arg0) {

			try {
				ResultsToPDF.saveDiagnosisResultsToPDF(concrete, steel, reinforcement, forces, dimensions, sls, diagnosis, 
						scratch, creep, deflection, nominalCheckBox, stiffness);
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
