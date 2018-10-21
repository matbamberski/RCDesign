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
		super(concrete.getLAMBDA(), concrete.getETA());
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
			if (!checkIfNrdIsGreaterThanNedUnSym()) {
				System.out.println("\nTrzeba uruchomic algorytm KNG! - unsymmetrical\n");
				KNGAlgorithm algorithm = new KNGAlgorithm(concrete, steel, dimensions);
				algorithm.setAsBase(selectedCombination.getaS1req(), selectedCombination.getaS2req());
				algorithm.mainKNGAlgorithm(combinations);
				reinforcement.setRequiredUnsymmetricalAS1(algorithm.getAS1());
				reinforcement.setRequiredUnsymmetricalAS2(algorithm.getAS2());
				requireReinforcement.designUnsymmetricalReinforcement(reinforcement);
				reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
				reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);	
				selectedCombination.getRs().setXunsym(algorithm.getX());
				
			} else {
				reinforcement.setRequiredUnsymmetricalAS1(selectedCombination.getaS1req());
				reinforcement.setRequiredUnsymmetricalAS2(selectedCombination.getaS2req());
				requireReinforcement.designUnsymmetricalReinforcement(reinforcement);
				reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
				reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
				
			}
			
			if (!checkIfNrdIsGreaterThanNedSym()) {
				System.out.println("\nTrzeba uruchomic algorytm KNG! - symmetrical\n");
				KNGAlgorithmSymmetrical symAlgorithm = new KNGAlgorithmSymmetrical(concrete, steel, dimensions);
				symAlgorithm.setAsBase(selectedCombination.getAsSymmetricalReq(), selectedCombination.getAsSymmetricalReq());
				symAlgorithm.mainKNGAlgorithm(combinations);
				reinforcement.setRequiredSymmetricalAS1(symAlgorithm.getAS1());
				reinforcement.setRequiredSymmetricalAS2(symAlgorithm.getAS2());
				requireReinforcement.designSymmetricalReinforcement(reinforcement);
				reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
				reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
				selectedCombination.getRs().setXsym(symAlgorithm.getX());
				
				reinforcement.setRequiredAS1ManyForces(symAlgorithm.getAS1());
				reinforcement.setRequiredAS2ManyForces(symAlgorithm.getAS2());
				reinforcement.setDegreeManyForces(reinforcement.getDegreeOfComputedSymmetricalReinforcement());
			} else {
				System.out.println("\nKoniec obliczen!\n");
				reinforcement.setRequiredSymmetricalAS1(selectedCombination.getAsSymmetricalReq());
				reinforcement.setRequiredSymmetricalAS2(selectedCombination.getAsSymmetricalReq());
				requireReinforcement.designSymmetricalReinforcement(reinforcement);
				reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
				reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);

				reinforcement.setRequiredAS1ManyForces(selectedCombination.getAsSymmetricalReq());
				reinforcement.setRequiredAS2ManyForces(selectedCombination.getAsSymmetricalReq());
				reinforcement.setDegreeManyForces(reinforcement.getDegreeOfComputedSymmetricalReinforcement());
			}
			eraseFilter();
		}

	}
	
	private void prepareCombinations() {
		Column column = new Column(requireReinforcement, true, concrete);
		column.prepareCombinationsCountRequireReinforcement(concrete, steel, forces, 
				dimensions, reinforcement, stiffness, cement, creep, checkbox);
		combinations = forces.getCombinations();
		combinationsFilter();
	}

	public void decideCompressOrTensile() {
		if(selectedCombination.getN()<0) {
			diagnosis = new TensilingDiagnosis(concrete.getLAMBDA(), concrete.getETA());
 		} else {
 			diagnosis = new CompressingDiagnosis(concrete.getLAMBDA(), concrete.getETA());
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
		//decideCompressOrTensile();
		for (ForcesCombination fc : combinations) {
			double as1 = selectedCombination.getaS1prov();
			double as2 = selectedCombination.getaS2prov();
			
			if (!selectedCombination.equals(fc) && fc.isMedNegativ() && !selectedCombination.isMedNegativ()) {
				as1 = selectedCombination.getaS2prov();
				as2 = selectedCombination.getaS1prov();
			}
			
			if (!selectedCombination.equals(fc) && !fc.isMedNegativ() && selectedCombination.isMedNegativ()) {
				as1 = selectedCombination.getaS2prov();
				as2 = selectedCombination.getaS1prov();
			}
			
			if (fc.getN()<0) {
				TensilingDiagnosis tensile = new TensilingDiagnosis(concrete.getLAMBDA(), concrete.getETA());
				tensile.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, 
						fc.getM(), fc.getN(), as1, as2);
				fc.setnRd(tensile.getnRd());
				fc.setmRd(tensile.getmRd());
				tensile.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, 
						fc.getM(), fc.getN(), selectedCombination.getAsSymmetricalProv(), selectedCombination.getAsSymmetricalProv());
				fc.setmRdSym(tensile.getmRd());
				fc.setnRdSym(tensile.getnRd());
			} else {
				CompressingDiagnosis compression = new CompressingDiagnosis(concrete.getLAMBDA(), concrete.getETA());
				compression.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions,
						as1, as2, fc.getmStiff(), fc.getN());
				fc.setnRd(compression.getnRd());
				fc.setmRd(compression.getmRd());
				compression.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions,
						selectedCombination.getAsSymmetricalProv(), selectedCombination.getAsSymmetricalProv(), fc.getmStiff(), fc.getN());
				fc.setnRdSym(compression.getnRd());
				fc.setmRdSym(compression.getmRd());
			}
		}
	}
	
	private void prepareCombinationForDiagnosis(ForcesCombination fc) {
		if (fc.isMedNegativ()) {
			fc.swapAs1WithAs2();
		}
	}

	public boolean checkIfNrdIsGreaterThanNedUnSym() {
		for (ForcesCombination fc : combinations) {
			if (Math.abs(fc.getM())>Math.abs(fc.getmRd()) || Math.abs(fc.getN())>Math.abs(fc.getnRd())) {
				return false;
			}
		}
		return true;
	}

	public boolean checkIfNrdIsGreaterThanNedSym() {
		for (ForcesCombination fc : combinations) {
			if (Math.abs(fc.getM())>Math.abs(fc.getmRdSym()) || Math.abs(fc.getN())>Math.abs(fc.getnRdSym())) {
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
			else if (checkbox.isSelected() && Math.abs(combinations.get(i).getM())<Math.abs(combinations.get(i).getmStiff())) {
				combinations.get(i).setM(combinations.get(i).getmStiff());
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
