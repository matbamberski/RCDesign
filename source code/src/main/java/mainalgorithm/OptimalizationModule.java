package mainalgorithm;

import java.util.ArrayList;

import diagnosis.BeamDiagnosis;
import diagnosis.CompressingDiagnosis;
import diagnosis.TensilingDiagnosis;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.axisload.SymmetricalTensilingBeamReinforcement;

public class OptimalizationModule extends SymmetricalTensilingBeamReinforcement {

	private InternalForces forces;
	private ArrayList<ForcesCombination> combinations;
	private Steel steel;
	private Concrete concrete;
	private DimensionsOfCrossSectionOfConcrete dimensions;
	private double aS;
	private ForcesCombination selectedCombination;
	private double s;
	private BeamDiagnosis diagnosis;

	public OptimalizationModule(InternalForces forces, Steel steel,
			Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		super();
		this.forces = forces;
		//this.combinations = forces.getForcesCombinations();
		this.steel = steel;
		this.concrete = concrete;
		this.dimensions = dimensions;
	}

	public void executeAlgorithm() {
		findMaxAs();
		if (selectedCombination != null) {

			setAsInitialValue();
			prepareEmin();
			changeAsIfEisLessThanEMin();
			countNrd();
			if (!checkIfNrdIsGreaterThanNed()) {
				System.out.println("Trzeba uruchomic algorytm KNG!");
			} else {
				System.out.println("Koniec obliczen!");
			}
		}

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
			if(fc.getaS1req()+fc.getaS2req()>aS)
				aS = fc.getaS1req()+fc.getaS2req();
				selectedCombination = fc;
		}

	}

	public void setAsInitialValue() {
		if (selectedCombination.isMedNegativ()) {
			selectedCombination.swapAs1WithAs2();
		}
	}

	public void prepareXValues() {
		setX0(concrete, steel, dimensions.getH());
		setXLim(concrete, steel, dimensions.getD());
		setXMaxYd(concrete, steel, dimensions.getA2());
		setXMinMinusYd(concrete, steel, dimensions.getA2());
		setXMinYd(concrete, steel, dimensions.getA2());
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
		for (ForcesCombination fc : combinations) {
			diagnosis.runDiagnosis(concrete, steel, dimensions,
					fc.getmStiff(), fc.getN(), fc.getaS1req(), fc.getaS2req());
			fc.setnRd(diagnosis.getnRd());
		}
	}

	public boolean checkIfNrdIsGreaterThanNed() {
		for (ForcesCombination fc : combinations) {
			if (fc.getM()>fc.getmRd()) {
				return false;
			}
		}
		return true;
	}


}
