package SLS;

import SLS.cracks.Scratch;
import SLS.creepCoeficent.CreepCoeficent;
import SLS.deflection.DeflectionControl;
import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class Sls {

	private double alfaE;

	private double phiMaxSymmetricalRequired;
	private double phiMaxSymmetricalDesigned;
	private double phiMaxUnsymmetricalRequired;
	private double phiMaxUnsymmetricalDesigned;
	private double wSymmetricalRequired;
	private double wSymmetricalDesigned;
	private double wUnsymmetricalRequired;
	private double wUnsymmetricalDesigned;
	private double lDividedByDLimSymmetricalRequired;
	private double lDividedByDLimSymmetricalDesigned;
	private double lDividedByDLimUnsymmetricalRequired;
	private double lDividedByDLimUnsymmetricalDesigned;
	private double fSymmetricalRequired;
	private double fSymmetricalDesigned;
	private double fUnsymmetricalRequired;
	private double fUnsymmetricalDesigned;

	private boolean isMedLessThen0;

	private double ifMedIsLessThenZeroReturnAbsAndSetBoolean(double mEd) {
		isMedLessThen0 = false;
		if (mEd < 0) {
			isMedLessThen0 = true;
			mEd = Math.abs(mEd);
		}
		return mEd;
	}

	private void calculateEffectiveModulusOfElasticityOfConcrete(Concrete concrete, CreepCoeficent creepCoeficent) {
		concrete.calculateECEff(creepCoeficent.getCreepCoeficent());
	}

	private void calculateAlfaE(Concrete concrete, Steel steel, InternalForces forces) {
		if (forces.isLoadSustained()) {
			alfaE = steel.getES() / concrete.geteCEff();
		} else {
			alfaE = steel.getES() / concrete.getECm();
		}
	}

	private void calculateCrossSectionOfConcrete(DimensionsOfCrossSectionOfConcrete dimensions) {
		dimensions.calculateSc();
		dimensions.calculateAc();
		dimensions.calculateU();
		dimensions.calculateh0();
		dimensions.calculateXc();
		dimensions.calculateIc();
		dimensions.calculateWc();

	}

	private void calculateCrossSectionPhase1(DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		dimensions.calculateInitialS1(alfaE, aS1, aS2);
		dimensions.calculateAC1(alfaE, aS1, aS2);
		dimensions.calculateX1();
		dimensions.calculateI1(alfaE, aS1, aS2);
		dimensions.calculateS1(aS1, aS2);
	}

	private void calculateCrossSectionPhase2(DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		//dimensions.calculateX2(alfaE, aS1, aS2);
		//dimensions.calculateI2(alfaE, aS1, aS2);
		//dimensions.claculateS2(aS1);
		dimensions.calculateX2AndS2AndI2(alfaE, aS1, aS2);
	}

	private void calculateReinforcementCrossSectionOfConcreteCharacteristics(DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		calculateCrossSectionPhase1(dimensions, aS1, aS2);
		calculateCrossSectionPhase2(dimensions, aS1, aS2);

	}

	private void slsRequiredSymmetricalReinforcement(Concrete concrete, Steel steel, Cement cement, 
			Reinforcement reinforcement, InternalForces forces, CreepCoeficent creepCoeficent,
			DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2, double aSRozciagane, 
			double aSTensiledDiameter, double mEd, double nEd, int nSSymmetrical, Scratch scratches, DeflectionControl deflection) {
		calculateReinforcementCrossSectionOfConcreteCharacteristics(dimensions, aS1, aS2);
		//Scratch scratches = new Scratch();
		//phiMaxSymmetricalRequired = scratches.checkScratchesWithoutCalculatingCrackWidth(concrete, dimensions, mEd, alfaE, dimensions.getI2(), dimensions.getX2());
		wSymmetricalRequired = scratches.checkScratchesWithCalculatingCrackWidth(steel, concrete, dimensions, 
				reinforcement, forces, dimensions.getX2(), alfaE, aSRozciagane, aSTensiledDiameter, mEd,
				nEd, nSSymmetrical);
		//DeflectionControl deflection = new DeflectionControl();
		lDividedByDLimSymmetricalRequired = deflection.runDeflectionControlWithoutCalculating(concrete, steel, dimensions, aS1);
		fSymmetricalRequired = deflection.runDeflectionControlWithCalculatingDeflection(concrete, steel, cement, dimensions, forces, scratches.getMCr(), forces.getAlfaM(), mEd, aSRozciagane,
				aSTensiledDiameter, reinforcement.getaSW1Diameter());
		System.out.println("a");
	}

	private void slsDesignedSymmetricalReinforcement(Concrete concrete, Steel steel, Cement cement, 
			Reinforcement reinforcement, InternalForces forces, CreepCoeficent creepCoeficent,
			DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2, double aSRozciagane, 
			double aSTensiledDiameter, double mEd, double nEd, int nSSymmetrical, Scratch scratches, DeflectionControl deflection) {
		calculateReinforcementCrossSectionOfConcreteCharacteristics(dimensions, aS1, aS2);
		//Scratch scratchess = new Scratch();
		//phiMaxSymmetricalDesigned = scratchess.checkScratchesWithoutCalculatingCrackWidth(concrete, dimensions, mEd, alfaE, dimensions.getI2(), dimensions.getX2());
		wSymmetricalDesigned = scratches.checkScratchesWithCalculatingCrackWidth(steel, concrete, dimensions, reinforcement, forces, dimensions.getX2(), alfaE, aSRozciagane, aSTensiledDiameter, mEd,
				nEd, nSSymmetrical);
		//DeflectionControl deflection = new DeflectionControl();
		lDividedByDLimSymmetricalDesigned = deflection.runDeflectionControlWithoutCalculating(concrete, steel, dimensions, aS1);
		fSymmetricalDesigned = deflection.runDeflectionControlWithCalculatingDeflection(concrete, steel, cement, dimensions, forces, scratches.getMCr(), forces.getAlfaM(), mEd, aSRozciagane,
				aSTensiledDiameter, reinforcement.getaSW1Diameter());
		System.out.println("a");
	}

	private void slsRequiredUnsymmetricalReinforcement(Concrete concrete, Steel steel, Cement cement, 
			Reinforcement reinforcement, InternalForces forces, CreepCoeficent creepCoeficent,
			DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2, double aSRozciagane, 
			double aSTensiledDiameter, double mEd, double nEd, int nSUnsymmetrical, Scratch scratches, DeflectionControl deflection) {
		calculateReinforcementCrossSectionOfConcreteCharacteristics(dimensions, aS1, aS2);
		//Scratch scratches = new Scratch();
		//phiMaxUnsymmetricalRequired = scratches.checkScratchesWithoutCalculatingCrackWidth(concrete, dimensions, mEd, alfaE, dimensions.getI2(), dimensions.getX2());
		wUnsymmetricalRequired = scratches.checkScratchesWithCalculatingCrackWidth(steel, concrete, dimensions, reinforcement, forces, dimensions.getX2(), alfaE, aSRozciagane, aSTensiledDiameter, mEd,
				nEd, nSUnsymmetrical);
		//DeflectionControl deflection = new DeflectionControl();
		lDividedByDLimUnsymmetricalRequired = deflection.runDeflectionControlWithoutCalculating(concrete, steel, dimensions, aS1);
		fUnsymmetricalRequired = deflection.runDeflectionControlWithCalculatingDeflection(concrete, steel, cement, dimensions, forces, scratches.getMCr(), forces.getAlfaM(), mEd, aSRozciagane,
				aSTensiledDiameter, reinforcement.getaSW1Diameter());
		System.out.println("a");
	}

	private void slsDesignedUnsymmetricalReinforcement(Concrete concrete, Steel steel, Cement cement, 
			Reinforcement reinforcement, InternalForces forces, CreepCoeficent creepCoeficent,
			DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2, double aSRozciagane, 
			double aSTensiledDiameter, double mEd, double nEd, int nSSymmetrical, Scratch scratches, DeflectionControl deflection) {
		calculateReinforcementCrossSectionOfConcreteCharacteristics(dimensions, aS1, aS2);
		//Scratch scratches = new Scratch();
		//phiMaxUnsymmetricalDesigned = scratches.checkScratchesWithoutCalculatingCrackWidth(concrete, dimensions, mEd, alfaE, dimensions.getI2(), dimensions.getX2());
		wUnsymmetricalDesigned = scratches.checkScratchesWithCalculatingCrackWidth(steel, concrete, dimensions, reinforcement, forces, dimensions.getX2(), alfaE, aSRozciagane, aSTensiledDiameter, mEd,
				nEd, nSSymmetrical);
		//DeflectionControl deflection = new DeflectionControl();
		lDividedByDLimUnsymmetricalDesigned = deflection.runDeflectionControlWithoutCalculating(concrete, steel, dimensions, aS1);
		fUnsymmetricalDesigned = deflection.runDeflectionControlWithCalculatingDeflection(concrete, steel, cement, dimensions, forces, scratches.getMCr(), forces.getAlfaM(), mEd, aSRozciagane,
				aSTensiledDiameter, reinforcement.getaSW1Diameter());

	}

	public void runSLS(Concrete concrete, Cement cement, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, CreepCoeficent creepCoeficent, Reinforcement reinforcement,
			InternalForces forces, Scratch scratches, DeflectionControl deflection) {
		/*
		if (forces.getCharacteristicMEdCalkowita()==0) {
			forces.setCharacteristicMEdCalkowita(forces.getmEd()/1.45);
		}
		if (forces.getCharacteristicMEdDlugotrwale()==0) {
			forces.setCharacteristicMEdDlugotrwale(forces.getCharacteristicMEdCalkowita()*0.85);
		}
*/
		System.out.println("\nSLS\n\n");
		double mEd = forces.getCharacteristicMEdDlugotrwale();
		if (forces.isLoadSustained() == false) {
			mEd = forces.getCharacteristicMEdCalkowita();
		}
		
		double nEd = forces.getnEd();
		double RequiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS1();
		double RequiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS2();
		double DesignedDiameterSymmetricalAS1 = reinforcement.getDesignedDiameterSymmetricalAS1();
		double DesignedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS1();
		double DesignedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS2();
		double RequiredUnsymmetricalAS1 = reinforcement.getRequiredUnsymmetricalAS1();
		double RequiredUnsymmetricalAS2 = reinforcement.getRequiredUnsymmetricalAS2();
		double DesingedUnsymmetricalAS1 = reinforcement.getDesingedUnsymmetricalAS1();
		double DesignedUnsymmetricalAS2 = reinforcement.getDesignedUnsymmetricalAS2();
		int nSUnsymmetrical = reinforcement.getRequiredNumberOfUnsymmetricalRodsAS1();
		if (mEd < 0) {
			RequiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS2();
			RequiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS1();
			DesignedDiameterSymmetricalAS1 = reinforcement.getDesignedDiameterSymmetricalAS2();
			DesignedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS2();
			DesignedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS1();
			RequiredUnsymmetricalAS1 = reinforcement.getRequiredUnsymmetricalAS2();
			RequiredUnsymmetricalAS2 = reinforcement.getRequiredUnsymmetricalAS1();
			DesingedUnsymmetricalAS1 = reinforcement.getDesignedUnsymmetricalAS2();
			DesignedUnsymmetricalAS2 = reinforcement.getDesingedUnsymmetricalAS1();
			nSUnsymmetrical = reinforcement.getRequiredNumberOfUnsymmetricalRodsAS2();
		}
		mEd = ifMedIsLessThenZeroReturnAbsAndSetBoolean(mEd);
		calculateCrossSectionOfConcrete(dimensions);
		creepCoeficent.runCreepCoeficentCalculations(concrete, cement, dimensions);
		calculateEffectiveModulusOfElasticityOfConcrete(concrete, creepCoeficent);
		calculateAlfaE(concrete, steel, forces);
		
		System.out.println("\nSLS REQUIRE SYMMETRICAL\n\n");
		slsRequiredSymmetricalReinforcement(concrete, steel, cement, reinforcement, forces, creepCoeficent, 
				dimensions, RequiredSymmetricalAS1, RequiredSymmetricalAS2, RequiredSymmetricalAS1,
				DesignedDiameterSymmetricalAS1, mEd, nEd, nSUnsymmetrical, scratches, deflection);
		
		System.out.println("\nSLS DESIGNED SYMMETRICAL\n\n");
		slsDesignedSymmetricalReinforcement(concrete, steel, cement, reinforcement, forces, creepCoeficent, 
				dimensions, DesignedSymmetricalAS1, DesignedSymmetricalAS2, DesignedSymmetricalAS1,
				DesignedDiameterSymmetricalAS1, mEd, nEd, nSUnsymmetrical, scratches, deflection);
		
		System.out.println("\nSLS REQUIRE UNSYMMETRICAL\n\n");
		slsRequiredUnsymmetricalReinforcement(concrete, steel, cement, reinforcement, forces, creepCoeficent, 
				dimensions, RequiredUnsymmetricalAS1, RequiredUnsymmetricalAS2, RequiredUnsymmetricalAS1,
				DesignedDiameterSymmetricalAS1, mEd, nEd, nSUnsymmetrical, scratches, deflection);
		
		System.out.println("\nSLS DESIGNED UNSYMMETRICAL\n\n");
		slsDesignedUnsymmetricalReinforcement(concrete, steel, cement, reinforcement, forces, creepCoeficent, 
				dimensions, DesingedUnsymmetricalAS1, DesignedUnsymmetricalAS2, DesingedUnsymmetricalAS1,
				DesignedDiameterSymmetricalAS1, mEd, nEd, nSUnsymmetrical, scratches, deflection);
		
	}

	public double getPhiMaxSymmetricalRequired() {
		return phiMaxSymmetricalRequired;
	}

	public double getPhiMaxSymmetricalDesigned() {
		return phiMaxSymmetricalDesigned;
	}

	public double getPhiMaxUnsymmetricalRequired() {
		return phiMaxUnsymmetricalRequired;
	}

	public double getPhiMaxUnsymmetricalDesigned() {
		return phiMaxUnsymmetricalDesigned;
	}

	public double getwSymmetricalRequired() {
		return wSymmetricalRequired;
	}

	public double getwSymmetricalDesigned() {
		return wSymmetricalDesigned;
	}

	public double getwUnsymmetricalRequired() {
		return wUnsymmetricalRequired;
	}

	public double getwUnsymmetricalDesigned() {
		return wUnsymmetricalDesigned;
	}

	public double getlDividedByDLimSymmetricalRequired() {
		return lDividedByDLimSymmetricalRequired;
	}

	public double getlDividedByDLimSymmetricalDesigned() {
		return lDividedByDLimSymmetricalDesigned;
	}

	public double getlDividedByDLimUnsymmetricalRequired() {
		return lDividedByDLimUnsymmetricalRequired;
	}

	public double getlDividedByDLimUnsymmetricalDesigned() {
		return lDividedByDLimUnsymmetricalDesigned;
	}

	public double getfSymmetricalRequired() {
		return fSymmetricalRequired;
	}

	public double getfSymmetricalDesigned() {
		return fSymmetricalDesigned;
	}

	public double getfUnsymmetricalRequired() {
		return fUnsymmetricalRequired;
	}

	public double getfUnsymmetricalDesigned() {
		return fUnsymmetricalDesigned;
	}

}
