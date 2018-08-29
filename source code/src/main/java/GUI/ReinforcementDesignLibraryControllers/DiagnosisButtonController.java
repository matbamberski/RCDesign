package GUI.ReinforcementDesignLibraryControllers;

import java.util.ArrayList;
import java.util.Arrays;

import SLS.Sls;
import SLS.cracks.Scratch;
import SLS.creepCoeficent.CreepCoeficent;
import SLS.deflection.DeflectionControl;
import diagnosis.DiagnosisMainAlgorithm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import mainalgorithm.ForcesCombination;
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
			NominalStiffness stiffness, CheckBox checkbox, TableView<ForcesCombination> tableViewCombinations, 
			CheckBox combinationsCheckBox, Scratch scratch, DeflectionControl deflection) {
		addListenerToDiagnosisButton(button, requiredReinforcement, concrete, steel, internalForces, dimensions,
				reinforcement, resultsPaneControllerDiagnosis, cement, sls, forces, creep, diagnosisMainAlgorithm,
				stiffness, checkbox, tableViewCombinations, combinationsCheckBox, scratch, deflection);
	}

	private static void addListenerToDiagnosisButton(Button button, RequiredReinforcement requiredReinforcement,
			Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement,
			ResultsPaneControllerDiagnosis resultsPaneControllerDiagnosis, Cement cement, Sls sls,
			InternalForces forces, CreepCoeficent creep, DiagnosisMainAlgorithm diagnosisMainAlgorithm,
			NominalStiffness stiffness, CheckBox checkbox, TableView<ForcesCombination> tableViewCombinations,
			CheckBox combinationsCheckBox, Scratch scratch, DeflectionControl deflection) {
		button.setOnAction(new PresedDiagnosisButton(requiredReinforcement, concrete, steel, internalForces, dimensions,
				reinforcement, resultsPaneControllerDiagnosis, cement, sls, forces, creep, diagnosisMainAlgorithm,
				stiffness, checkbox, tableViewCombinations, combinationsCheckBox, scratch, deflection));
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
		TableView<ForcesCombination> tableViewCombinations;
		CheckBox combinationsCheckBox;
		Scratch scratch;
		DeflectionControl deflection;

		protected PresedDiagnosisButton(RequiredReinforcement requiredReinforcement, Concrete concrete, Steel steel,
				InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions,
				Reinforcement reinforcement, ResultsPaneControllerDiagnosis resultsPaneControllerDiagnosis,
				Cement cement, Sls sls, InternalForces forces, CreepCoeficent creep,
				DiagnosisMainAlgorithm diagnosisMainAlgorithm, NominalStiffness stiffness, CheckBox checkbox, 
				TableView<ForcesCombination> tableViewCombinations, CheckBox combinationsCheckBox, Scratch scratch,
				DeflectionControl deflection) {
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
			this.tableViewCombinations = tableViewCombinations;
			this.combinationsCheckBox = combinationsCheckBox;
			this.scratch = scratch;
			this.deflection = deflection;
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
			boolean withDesign = false;
			diagnosisMainAlgorithm.setmRdExceeded(false);
			ResultsToPDF.clearResults();
			requiredReinforcement.checkWhatIsRequiredReinforcement(concrete, steel, internalForces, dimensions,
					reinforcement, stiffness, cement, creep, checkbox);
			sls.runSLS(concrete, cement, steel, dimensions, creep, reinforcement, forces, scratch, deflection, withDesign);
			sls.printReport(dimensions, creep, concrete, deflection, scratch, "DIAG");
			if (internalForces.getMomentMmax() == 0 && internalForces.getNormalnaMmax() == 0
					&& internalForces.getMomentMmin() == 0 && internalForces.getNormalnaMmin() == 0
							&& internalForces.getMomentNmax() == 0 && internalForces.getNormalnaNmin() == 0
									&& internalForces.getMomentNmin() == 0 && internalForces.getNormalnaNmin() == 0) {
					
					internalForces.setMedCombination();
					ForcesCombination combination = internalForces.getMaxECombination();
					diagnosisMainAlgorithm.runDiagnosis(concrete, steel, internalForces.getmEd(),internalForces.getnEd(), dimensions, reinforcement, forces);
					internalForces.setmEdStiff(stiffness.getmEd());
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
					
					ArrayList<String> names = new ArrayList<>(Arrays.asList("Mmax", "Mmin", "Nmax", "Nmin"));
					
					for (int i = 0; i < momenty.size(); i++) {
						mEd = momenty.get(i);
						nEd = normalne.get(i);
						diagnosisMainAlgorithm.runDiagnosis(concrete, steel, mEd, nEd, dimensions, reinforcement, forces);
						System.err.println("Dla si³ MEd = "+ mEd + ", i NEd = " + nEd);
						System.err.println("MRd Deisgned Symmetrical = " + diagnosisMainAlgorithm.getmRdDesignedSymmetrical());
						normalneR.add(diagnosisMainAlgorithm.getnRdRequiredSymmetrical());
						normalneD.add(diagnosisMainAlgorithm.getnRdDesignedSymmetrical());
						/////////Czemu symetryczne przypisujemy? to chyba zalezy co wpisza
						/////////Usunalem z momentem ujemnym, wiec jeszcze raz trzeba ogarnac
						if (checkIfSymmetrical()) {
							internalForces.getCombinationDiagnosis().get(i).setnRd(diagnosisMainAlgorithm.getnRdDesignedSymmetrical());
							internalForces.getCombinationDiagnosis().get(i).newName(names.get(i));
							
							
							momentyR.add(diagnosisMainAlgorithm.getmRdRequiredSymmetrical());
							momentyD.add(diagnosisMainAlgorithm.getmRdDesignedSymmetrical());
							
							internalForces.getCombinationDiagnosis().get(i).setmRd(diagnosisMainAlgorithm.getmRdDesignedSymmetrical());
						} else {
							internalForces.getCombinationDiagnosis().get(i).setnRd(diagnosisMainAlgorithm.getnRdDesignedUnsymmetrical());
							internalForces.getCombinationDiagnosis().get(i).newName(names.get(i));
							
							
							momentyR.add(diagnosisMainAlgorithm.getmRdRequiredUnsymmetrical());
							momentyD.add(diagnosisMainAlgorithm.getmRdDesignedUnsymmetrical());
							
							internalForces.getCombinationDiagnosis().get(i).setmRd(diagnosisMainAlgorithm.getmRdDesignedUnsymmetrical());
						}
						
						
						if (i==0) {
							internalForces.setMomentMmaxStiff(internalForces.getCombinationDiagnosis().get(0).getmStiff());
						} else if (i==1) {
							internalForces.setMomentMminStiff(internalForces.getCombinationDiagnosis().get(1).getmStiff());
						} else if (i==2) {
							internalForces.setMomentNmaxStiff(internalForces.getCombinationDiagnosis().get(2).getmStiff());
						} else if (i==3) {
							internalForces.setMomentNminStiff(internalForces.getCombinationDiagnosis().get(3).getmStiff());
						}
						
						if(diagnosisMainAlgorithm.getmRdDesignedSymmetrical() != 0) {
							if(Math.abs(mEd) > Math.abs(diagnosisMainAlgorithm.getmRdDesignedSymmetrical())) {
								diagnosisMainAlgorithm.setmRdExceeded(true);
							}
						}
						
						if (diagnosisMainAlgorithm.getmRdDesignedUnsymmetrical() != 0) {
							if (Math.abs(mEd) > Math.abs(diagnosisMainAlgorithm.getmRdDesignedUnsymmetrical())) {
								diagnosisMainAlgorithm.setmRdExceeded(true);
							}
						}
						
						if(diagnosisMainAlgorithm.getnRdDesignedSymmetrical() != 0) {
							if(Math.abs(nEd) > Math.abs(diagnosisMainAlgorithm.getnRdDesignedSymmetrical())) {
								diagnosisMainAlgorithm.setmRdExceeded(true);
							}
						}
						
						if (diagnosisMainAlgorithm.getnRdDesignedUnsymmetrical() != 0) {
							if (Math.abs(nEd) > Math.abs(diagnosisMainAlgorithm.getnRdDesignedUnsymmetrical())) {
								diagnosisMainAlgorithm.setmRdExceeded(true);
							}
						}
						
					}	
					/*
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
			*/
			prepareTable();
			}
			if (combinationsCheckBox.isSelected()) {
				tableViewCombinations.setVisible(true);
			} else tableViewCombinations.setVisible(false);
			
			if (internalForces.getNormalnaMmax() == 0
                    && internalForces.getNormalnaMmin() == 0
                    && internalForces.getNormalnaNmax() == 0
                    && internalForces.getNormalnaNmin() == 0) {
                tableViewCombinations.setVisible(false);
            }
			
			resultsPaneControllerDiagnosis.dispResults();
			normalne.clear();
			momenty.clear();
			normalneR.clear();
			momentyR.clear();
			normalneD.clear();
			momentyD.clear();
			
			internalForces.saveCombinationToPdfCombination();
			internalForces.getCombinations().clear();
			internalForces.getCombinationDiagnosis().clear();
			
			SaveFileButtonController.enableButtonDiagnosis();
			
			
			
		}
		
		private void prepareTable() {
			for(ForcesCombination f : internalForces.getCombinationDiagnosis())
				f.setTableValues();
			
			ObservableList<ForcesCombination> data =
			        FXCollections.observableArrayList(
			        	internalForces.getCombinationDiagnosis()	
			        );
			tableViewCombinations.setItems(data);
			
		}
		
		private boolean checkIfSymmetrical() {
			if (reinforcement.getRequiredNumberOfSymmetricalRodsAS1()== reinforcement.getRequiredNumberOfSymmetricalRodsAS2()) return true;
			else return false;
		}
	}

}
