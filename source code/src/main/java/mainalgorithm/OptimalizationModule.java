package mainalgorithm;

import java.util.ArrayList;

import SLS.creepCoeficent.CreepCoeficent;
import diagnosis.BeamDiagnosis;
import diagnosis.CompressingDiagnosis;
import diagnosis.TensilingDiagnosis;
import javafx.scene.control.CheckBox;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.axisload.SymmetricalTensilingBeamReinforcement;
import reinforcement.bending.Column;

public class OptimalizationModule extends SymmetricalTensilingBeamReinforcement {

	private InternalForces forces;
	private ArrayList<ForcesCombination> combinations;
	private Steel steel;
	private Concrete concrete;
	private DimensionsOfCrossSectionOfConcrete dimensions;
	private RequiredReinforcement requireReinforcement;
	private Reinforcement reinforcement;
	private NominalStiffness stiffness;
	private Cement cement;
	private CreepCoeficent creep;
	private CheckBox checkbox;
	private double aS;
	private ForcesCombination selectedCombination;
	private double s;
	private BeamDiagnosis diagnosis;

	public OptimalizationModule(InternalForces forces, Steel steel,
			Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, 
			RequiredReinforcement requiredReinforcement, Reinforcement reinforcement,
			NominalStiffness stiffness, Cement cement, CreepCoeficent creep, 
			CheckBox checkbox) {
		super();
		this.forces = forces;
		this.steel = steel;
		this.concrete = concrete;
		this.dimensions = dimensions;
		this.requireReinforcement = requiredReinforcement;
		this.reinforcement = reinforcement;
		this.stiffness = stiffness;
		this.cement = cement;
		this.creep = creep;
		this.checkbox = checkbox;
	}

	public void executeAlgorithm() {
		System.out.println("\n\nWywo³ano algorytm optymalizacji doboru zbrojenia!\n\n");
		
		prepareCombinations();
		findMaxAs();
		if (selectedCombination != null) {

			setAsInitialValue();
			prepareEmin();
			changeAsIfEisLessThanEMin();
			countNrd();
			if (!checkIfNrdIsGreaterThanNed()) {
				System.out.println("\nTrzeba uruchomic algorytm KNG!\n");
				KNGAlgorithm algorithm = new KNGAlgorithm(concrete, steel, dimensions);
				algorithm.setAsBase(selectedCombination.getaS1req(), selectedCombination.getaS2req());
				algorithm.mainKNGAlgorithm(combinations);
				reinforcement.setRequiredUnsymmetricalAS1(algorithm.getAS1());
				reinforcement.setRequiredUnsymmetricalAS2(algorithm.getAS2());
				requireReinforcement.designUnsymmetricalReinforcement(reinforcement);
				reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
				reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
				
				KNGAlgorithmSymmetrical symAlgorithm = new KNGAlgorithmSymmetrical(concrete, steel, dimensions);
				symAlgorithm.setAsBase(selectedCombination.getAsSymmetricalReq(), selectedCombination.getAsSymmetricalReq());
				symAlgorithm.mainKNGAlgorithm(combinations);
				reinforcement.setRequiredSymmetricalAS1(symAlgorithm.getAS1());
				reinforcement.setRequiredSymmetricalAS2(symAlgorithm.getAS2());
				requireReinforcement.designSymmetricalReinforcement(reinforcement);
				reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
				reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
			} else {
				System.out.println("\nKoniec obliczen!\n");
			}
			eraseFilter();
		}

	}
	
	private void prepareCombinations() {
		Column column = new Column(requireReinforcement, true);
		column.prepareCombinationsCountRequireReinforcement(concrete, steel, forces, 
				dimensions, reinforcement, stiffness, cement, creep, checkbox);
		combinations = forces.getCombinations();
		combinationsFilter();
	}

	public void decideCompressOrTensile() {
		if(selectedCombination.getN()<0) {
			diagnosis = new TensilingDiagnosis();
 		} else {
 			diagnosis = new CompressingDiagnosis();
 		}
	}


	public void findMaxAs() {
		aS = 0;
		for(ForcesCombination fc : combinations) {
			if(fc.getaS1req()+fc.getaS2req()>aS) {
				aS = fc.getaS1req()+fc.getaS2req();
				selectedCombination = fc;
			}
		}
		System.out.println("\nSelected combination is: \nM " 
		+ selectedCombination.getM() + 
		"\tN " + selectedCombination.getN());
	}

	public void setAsInitialValue() {
		if (selectedCombination.isMedNegativ()) {
			selectedCombination.swapAs1WithAs2();
		}
	}


	public void prepareEmin() {
		setEMinTensile(selectedCombination.getaS1req(), selectedCombination.getaS2req(),
				dimensions.getH(), dimensions.getA1(), dimensions.getA2());
		setEMinCompress(selectedCombination.getaS1req(), selectedCombination.getaS2req(),
				dimensions.getH(), dimensions.getA1(), dimensions.getA2(), dimensions.getB(), steel, concrete);
	}

	public void changeAsIfEisLessThanEMin() {
		if (Math.abs(selectedCombination.getE()) < eMinTensile && selectedCombination.getN()<0) {
			selectedCombination.swapAs1WithAs2();
		} else if (Math.abs(selectedCombination.getE()) < eMinCompression && selectedCombination.getN()>0) {
			selectedCombination.swapAs1WithAs2();
		}
	}

	public void countNrd() {
		setCurrentForces(selectedCombination);
		decideCompressOrTensile();
		for (ForcesCombination fc : combinations) {
			diagnosis.runDiagnosis(concrete, steel, dimensions,
					fc.getmStiff(), fc.getN(), selectedCombination.getaS1req(), selectedCombination.getaS2req());
			fc.setnRd(diagnosis.getnRd());
			fc.setmRd(diagnosis.getmRd());
		}
	}

	public boolean checkIfNrdIsGreaterThanNed() {
		for (ForcesCombination fc : combinations) {
			if (Math.abs(fc.getM())>Math.abs(fc.getmRd()) || Math.abs(fc.getN())>Math.abs(fc.getnRd())) {
				return false;
			}
		}
		return true;
	}

	public void setCurrentForces(ForcesCombination combination) {
		forces.setM0Ed(combination.getM());
		forces.setmEd(combination.getmStiff());
		forces.setnEd(combination.getN());
	}
	
	private void combinationsFilter() {
		for (int i = 0; i< combinations.size(); i++) {
			if (combinations.get(i).getN()==0 && combinations.get(i).getM()==0) {
				combinations.remove(i);
				i--;
			}
			else if (combinations.get(i).isMedNegativ()){
				combinations.get(i).setM(-combinations.get(i).getM());
			}
		}
	}
	
	private void eraseFilter() {
		for (int i=0; i< combinations.size(); i++) {
			if (combinations.get(i).isMedNegativ()){
				combinations.get(i).setM(-combinations.get(i).getM());
			}
		}
	}
}
