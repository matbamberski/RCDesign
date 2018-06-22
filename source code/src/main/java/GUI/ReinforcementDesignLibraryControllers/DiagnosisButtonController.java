package GUI.ReinforcementDesignLibraryControllers;

import java.util.ArrayList;

import SLS.Sls;
import SLS.creepCoeficent.CreepCoeficent;
import diagnosis.DiagnosisMainAlgorithm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import mainalgorithm.RequiredReinforcement;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.ResultsToPDF;

public class DiagnosisButtonController {
	
	

	public static void addPropertiesToDiagnosisButton(Button button, RequiredReinforcement requiredReinforcement,
			Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement,
			ResultsPaneControllerDiagnosis resultsPaneControllerDiagnosis, Cement cement, Sls sls,
			InternalForces forces, CreepCoeficent creep, DiagnosisMainAlgorithm diagnosisMainAlgorithm,
			NominalStiffness stiffness, CheckBox checkbox) {
		addListenerToDiagnosisButton(button, requiredReinforcement, concrete, steel, internalForces, dimensions,
				reinforcement, resultsPaneControllerDiagnosis, cement, sls, forces, creep, diagnosisMainAlgorithm,
				stiffness, checkbox);
	}

	private static void addListenerToDiagnosisButton(Button button, RequiredReinforcement requiredReinforcement,
			Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement,
			ResultsPaneControllerDiagnosis resultsPaneControllerDiagnosis, Cement cement, Sls sls,
			InternalForces forces, CreepCoeficent creep, DiagnosisMainAlgorithm diagnosisMainAlgorithm,
			NominalStiffness stiffness, CheckBox checkbox) {
		button.setOnAction(new PresedDiagnosisButton(requiredReinforcement, concrete, steel, internalForces, dimensions,
				reinforcement, resultsPaneControllerDiagnosis, cement, sls, forces, creep, diagnosisMainAlgorithm,
				stiffness, checkbox));
	}

	private static class PresedDiagnosisButton implements EventHandler<ActionEvent> {
		Concrete concrete;
		Steel steel;
		InternalForces internalForces;
		DimensionsOfCrossSectionOfConcrete dimensions;
		RequiredReinforcement requiredReinforcement;
		Reinforcement reinforcement;
		Cement cement;
		Sls sls;
		ResultsPaneControllerDiagnosis resultsPaneControllerDiagnosis;
		InternalForces forces;
		CreepCoeficent creep;
		DiagnosisMainAlgorithm diagnosisMainAlgorithm;
		NominalStiffness stiffness;
		CheckBox checkbox;

		protected PresedDiagnosisButton(RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel,
				InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions,
				Reinforcement reinforcement, ResultsPaneControllerDiagnosis resultsPaneControllerDiagnosis,
				Cement cement, Sls sls, InternalForces forces, CreepCoeficent creep,
				DiagnosisMainAlgorithm diagnosisMainAlgorithm, NominalStiffness stiffness, CheckBox checkbox) {
			this.requiredReinforcement = requiredReinforcement;
			this.concrete = concrete;
			this.steel = steel;
			this.internalForces = internalForces;
			this.dimensions = dimensions;
			this.reinforcement = reinforcement;
			this.resultsPaneControllerDiagnosis = resultsPaneControllerDiagnosis;
			this.cement = cement;
			this.sls = sls;
			this.forces = forces;
			this.creep = creep;
			this.diagnosisMainAlgorithm = diagnosisMainAlgorithm;
			this.stiffness = stiffness;
			this.checkbox = checkbox;
		}
		private ArrayList<Double> normalne = new ArrayList<Double>();
		private ArrayList<Double> momenty = new ArrayList<Double>();
		private ArrayList<Double> normalneR = new ArrayList<Double>();
		private ArrayList<Double> momentyR = new ArrayList<Double>();
		private ArrayList<Double> normalneD = new ArrayList<Double>();
		private ArrayList<Double> momentyD = new ArrayList<Double>();
		double mEd = 0;
		double nEd = 0;
		@Override
		public void handle(ActionEvent arg0) {
			ResultsToPDF.clearResults();
			requiredReinforcement.checkWhatIsRequiredReinforcement(concrete, steel, internalForces, dimensions,
					reinforcement, stiffness, cement, creep, checkbox);
			sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces);
			if (internalForces.getMomentMmax() == 0 & internalForces.getNormalnaMmax() == 0
					& internalForces.getMomentMmin() == 0 & internalForces.getNormalnaMmin() == 0
					& internalForces.getMomentNmax() == 0 & internalForces.getNormalnaNmin() == 0
					& internalForces.getMomentNmin() == 0 & internalForces.getNormalnaNmin() == 0) {
				
					diagnosisMainAlgorithm.runDiagnosis(concrete, steel, internalForces.getmEd(),internalForces.getnEd(), dimensions, reinforcement, forces);
					
					System.out.println("Policzono nosnosc pojedynczego zestawu sil");
					
				
			} else {
					momenty.add(internalForces.getMomentMmax());
					momenty.add(internalForces.getMomentMmin());
					momenty.add(internalForces.getMomentNmax());
					momenty.add(internalForces.getMomentNmin());
					normalne.add(internalForces.getNormalnaMmax());
					normalne.add(internalForces.getNormalnaMmin());
					normalne.add(internalForces.getNormalnaNmax());
					normalne.add(internalForces.getNormalnaNmin());

					for (int i = 0; i < momenty.size(); i++) {
						mEd = momenty.get(i);
						nEd = normalne.get(i);
						diagnosisMainAlgorithm.runDiagnosis(concrete, steel, mEd, nEd, dimensions, reinforcement, forces);
						System.err.println("Dla si³ MEd = "+ mEd + ", i NEd = " + nEd);
						System.err.println("MRd Deisgned Symmetrical = " + diagnosisMainAlgorithm.getmRdDesignedSymmetrical());
						normalneR.add(diagnosisMainAlgorithm.getnRdRequiredSymmetrical());
						normalneD.add(diagnosisMainAlgorithm.getnRdDesignedSymmetrical());
						momentyR.add(diagnosisMainAlgorithm.getmRdRequiredSymmetrical());
						momentyD.add(diagnosisMainAlgorithm.getmRdDesignedSymmetrical());
					}	
			resultsPaneControllerDiagnosis.setmRd1D(momentyD.get(0));
			resultsPaneControllerDiagnosis.setmRd2D(momentyD.get(1));
			resultsPaneControllerDiagnosis.setmRd3D(momentyD.get(2));
			resultsPaneControllerDiagnosis.setmRd4D(momentyD.get(3));
			resultsPaneControllerDiagnosis.setnRd1D(normalneD.get(0));
			resultsPaneControllerDiagnosis.setnRd2D(normalneD.get(1));
			resultsPaneControllerDiagnosis.setnRd3D(normalneD.get(2));
			resultsPaneControllerDiagnosis.setnRd4D(normalneD.get(3));
			resultsPaneControllerDiagnosis.setmRd1R(momentyR.get(0));
			resultsPaneControllerDiagnosis.setmRd2R(momentyR.get(1));
			resultsPaneControllerDiagnosis.setmRd3R(momentyR.get(2));
			resultsPaneControllerDiagnosis.setmRd4R(momentyR.get(3));
			resultsPaneControllerDiagnosis.setnRd1R(normalneR.get(0));
			resultsPaneControllerDiagnosis.setnRd2R(normalneR.get(1));
			resultsPaneControllerDiagnosis.setnRd3R(normalneR.get(2));
			resultsPaneControllerDiagnosis.setnRd4R(normalneR.get(3));
			}
			resultsPaneControllerDiagnosis.dispResults();
			normalne.clear();
			momenty.clear();
			normalneR.clear();
			momentyR.clear();
			normalneD.clear();
			momentyD.clear();
			
			SaveFileButtonController.enableButtonDiagnosis();
			
		}
	}

}
