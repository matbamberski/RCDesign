package SLS.deflection;

import mainalgorithm.InternalForces;
import mainalgorithm.ForcesCombination;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class DeflectionControl {

	// variables without calculating deflection
	private double delta1;
	private double delta2;
	private double delta3 = 1;
	private double ro0;
	private double ro;
	private double roPrim = 0;
	private double lDividedByDLim;
	private double lDividedByDLimEff;
	// without calculating deflection
	private double epsilonCS;
	private double epsilonCD;
	private double epsilonCDInfinity;
	private double epsilonCD0;
	private double epsilonCA;
	private double betaRH;
	private double kH;
	private double[] h0Tab = { 100, 200, 300, 500 };
	private double[] kHTab = { 1, 0.85, 0.75, 0.7 };
	private double alfaCS = 0.125;
	private double b1;
	private double b2;
	private double beta;
	private double dzeta;
	
	private double f1;
	private double f2;
	private double f1Cs;
	private double f2Cs;
	private double f;
	
	private double fws;
	private double f1ws;
	private double f2ws;
	private double f1Csws;
	private double f2Csws;
	
	private double r;

	// without calculating deflection
	private void calculateRo0(Concrete concrete) {
		ro0 = Math.sqrt(concrete.getFCk()) / 1000;
	}

	private void calculateRo(DimensionsOfCrossSectionOfConcrete dimensions, double aSProv) {
		ro = aSProv / (dimensions.getB() * dimensions.getD());
	}

	private void calculateLDividedByDLim(Concrete concrete) {
		lDividedByDLim = 11 + 1.5 * Math.sqrt(concrete.getFCk()) * ro0 / (ro - roPrim) + 1 / 12 * Math.sqrt(concrete.getFCk() * roPrim / ro0);
	}

	private void calculateDelta1(Steel steel, double aSProv, double aSReq) {
		delta1 = 500 * aSProv / (steel.getFYk() * aSReq);
	}

	private void calculateDelta2(DimensionsOfCrossSectionOfConcrete dimensions) {
		if (dimensions.getbEff() <= dimensions.getB() * 3) {
			delta2 = 1;
		} else {
			delta2 = 0.8;
		}
	}

	private void calculateLDEff() {
		lDividedByDLimEff = delta1 * delta2 * delta3 * lDividedByDLim;
	}

	public double runDeflectionControlWithoutCalculating(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1) {
		calculateRo0(concrete);
		calculateRo(dimensions, aS1);
		calculateLDividedByDLim(concrete);
		// calculateDelta1(steel, aSProv, aSReq);
		// calculateDelta2(dimensions);
		// calculateLDEff();
		return lDividedByDLim;
	}

	// without calculating deflection
	private void calculateBetaRH(Concrete concrete) {
		betaRH = 1.55 * (1 - (concrete.getrH() / 100) * (concrete.getrH() / 100) * (concrete.getrH() / 100));
	}

	private void calculateEpsilonCD0(Concrete concrete, Cement cement) {
		double a = cement.getAlfaDS1();
		double b = -cement.getAlfaDS2();
		double c = concrete.getFCm();

		epsilonCD0 = 0.85 * (220 + 110 * cement.getAlfaDS1()) * Math.exp(-cement.getAlfaDS2() * concrete.getFCm() / 10) * betaRH / 1000000;
	}

	private void calculateKh(DimensionsOfCrossSectionOfConcrete dimensions) {
		if (dimensions.getH0() * 1000 > 500) {
			kH = 0.7;
		} else {
			kH = hHInteprolation(dimensions);
		}
	}

	private double hHInteprolation(DimensionsOfCrossSectionOfConcrete dimensions) {
		double kh = 0;

		if (dimensions.getH0() * 1000 < 100) {
			kh = 1;
		}
		if (dimensions.getH0() * 1000 > 500) {
			kh = 0.7;
		}
		if (dimensions.getH0() * 1000 >= 100 && dimensions.getH0() * 1000 <= 500) {
			if (dimensions.getH0() * 1000 >= 100 && dimensions.getH0() * 1000 < 200) {
				kh = 1 + (dimensions.getH0() * 1000 - 100) / (200 - 100) * (0.85 - 1);
			}
			if (dimensions.getH0() * 1000 >= 200 && dimensions.getH0() * 1000 < 300) {
				kh = 0.85 + (dimensions.getH0() * 1000 - 200) / (300 - 200) * (0.75 - 0.85);
			}
			if (dimensions.getH0() * 1000 >= 300 && dimensions.getH0() * 1000 < 500) {
				kh = 0.75 + (dimensions.getH0() * 1000 - 300) / (500 - 300) * (0.7 - 0.75);
			}
		}

		return kh;
	}

	private void calculateEpsilonCD() {
		epsilonCD = epsilonCD0 * kH;
	}

	private void calculateEpsilonCA(Concrete concrete) {
		epsilonCA = 2.5 * (concrete.getFCk() - 10) / 1000000;
	}

	private void calculateEpsilonCS() {
		epsilonCS = epsilonCD + epsilonCA;
	}

	private void calculateB1(InternalForces forces, Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		if (forces.isLoadSustained()) {
			b1 = concrete.geteCEff() * dimensions.getI1() * 1000;
		} else {
			b1 = concrete.getECm() * dimensions.getI1() * 1000;
		}
	}

	private void calculateB2(InternalForces forces, Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		if (forces.isLoadSustained()) {
			b2 = concrete.geteCEff() * dimensions.getI2() * 1000;
		} else {
			b2 = concrete.getECm() * dimensions.getI2() * 1000;
		}
	}

	private void calculateBeta(InternalForces forces) {
		if (forces.isLoadSustained()) {
			beta = 0.5;
		} else {
			beta = 1;
		}
	}

	private void calculateDzeta(double mEd, double mCr) {
		dzeta = 1 - beta * (mCr / mEd) * (mCr / mEd);
	}

	private void calculateF1(double mEd, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f1 = alfaM * mEd * dimensions.getlEff() * dimensions.getlEff() / (b1 * 1000);
	}

	private void calculateF2(double mEd, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f2 = alfaM * mEd * dimensions.getlEff() * dimensions.getlEff() / (b2 * 1000);
	}

	private void calculateF1Cs(Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f1Cs = -alfaCS * steel.getES() * 1000 * epsilonCS * dimensions.getS1() * dimensions.getlEff() * dimensions.getlEff() / b1;
	}

	private void calculateF2Cs(Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f2Cs = -alfaCS * steel.getES() * 1000 * epsilonCS * dimensions.getS2() * dimensions.getlEff() * dimensions.getlEff() / b2;
	}

	private void calculateR(DimensionsOfCrossSectionOfConcrete dimensions, double aSTensiled, double aSTensiledDiameter, double aSW1) {
		r = dimensions.getB() - aSTensiled * 4 / aSTensiledDiameter - (aSTensiled * 4 / (aSTensiledDiameter * aSTensiledDiameter) - 1) * 5 * (dimensions.getA1() - aSTensiledDiameter / 2)
				- 2 * (dimensions.getA1() - aSTensiledDiameter / 2 - aSW1);
	}

	private void calculateF(DimensionsOfCrossSectionOfConcrete dimensions, InternalForces forces) {
		//if (r < 0) {
		if (forces.isLoadSustained())
			f = dzeta * (f2 + f2Cs) + (1 - dzeta) * (f1 + f1Cs);
		else f= dzeta * f2 + (1-dzeta)*f1;
		//} else {
		//	f = 1.3 * (dimensions.getH() - dimensions.getX2());
		//}
	}
	
	
	private void calculateFWithoutShrinkage(DimensionsOfCrossSectionOfConcrete dimensions, InternalForces forces) {
		//if (r < 0) {
		if (forces.isLoadSustained())
			fws = dzeta * f2ws + (1 - dzeta) * f1ws;
		else fws = dzeta * f2ws + (1-dzeta) * f1ws;
		//} else {
		//	f = 1.3 * (dimensions.getH() - dimensions.getX2());
		//}
	}
	
	private void calculateF1WithoutShrinkage(double mEd, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f1ws = alfaM * mEd * dimensions.getlEff() * dimensions.getlEff() / (b1 * 1000);
	}

	private void calculateF2WithoutShrinkage(double mEd, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f2ws = alfaM * mEd * dimensions.getlEff() * dimensions.getlEff() / (b2 * 1000);
	}
	/*
	private void calculateF1CsWithoutShrinkage(Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f1Csws = alfaM * steel.getES() * 1000 * epsilonCS * dimensions.getS1() * dimensions.getlEff() * dimensions.getlEff() / b1;
	}

	private void calculateF2CsWithoutShrinkage(Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, double alfaM) {
		f2Csws = alfaM * steel.getES() * 1000 * epsilonCS * dimensions.getS2() * dimensions.getlEff() * dimensions.getlEff() / b2;
	}
	*/
	public double getEpsilonCD() {
		return epsilonCD;
	}

	public double getEpsilonCS() {
		return epsilonCS;
	}

	public double getEpsilonCA() {
		return epsilonCA;
	}

	public double runDeflectionControlWithCalculatingDeflection(Concrete concrete, Steel steel, 
			Cement cement, DimensionsOfCrossSectionOfConcrete dimensions, InternalForces forces, double mCr,
			double alfaM, double mEd, double aSTensiled, double aSTensiledDiameter, double aSW1) {
		calculateBetaRH(concrete);
		calculateEpsilonCD0(concrete, cement);
		calculateKh(dimensions);
		calculateEpsilonCD();
		calculateEpsilonCA(concrete);
		calculateEpsilonCS();
		calculateB1(forces, concrete, dimensions);
		calculateB2(forces, concrete, dimensions);
		calculateBeta(forces);
		calculateDzeta(mEd, mCr);
		calculateF1(mEd, dimensions, alfaM);
		calculateF2(mEd, dimensions, alfaM);
		calculateF1Cs(steel, forces, dimensions, alfaCS);
		calculateF2Cs(steel, forces, dimensions, alfaCS);
		//calculateR(dimensions, aSTensiled, aSTensiledDiameter, aSW1);
		calculateF(dimensions, forces);
		if (mEd == 0) {
			f = 0;
		}
		return f;
	}
	
	public double runDeflectionControlWithCalculatingDeflectionWithoutShrinkage(Concrete concrete, Steel steel, 
			Cement cement, DimensionsOfCrossSectionOfConcrete dimensions, InternalForces forces, double mCr,
			double alfaM, double mEd, double aSTensiled, double aSTensiledDiameter, double aSW1) {
		calculateBetaRH(concrete);
		calculateEpsilonCD0(concrete, cement);
		calculateKh(dimensions);
		calculateEpsilonCD();
		calculateEpsilonCA(concrete);
		calculateEpsilonCS();
		calculateB1(forces, concrete, dimensions);
		calculateB2(forces, concrete, dimensions);
		calculateBeta(forces);
		calculateDzeta(mEd, mCr);
		calculateF1WithoutShrinkage(mEd, dimensions, alfaM);
		calculateF2WithoutShrinkage(mEd, dimensions, alfaM);
		/*
		calculateF1CsWithoutShrinkage(steel, forces, dimensions, alfaCS);
		calculateF2CsWithoutShrinkage(steel, forces, dimensions, alfaCS);
		*/
		//calculateR(dimensions, aSTensiled, aSTensiledDiameter, aSW1);
		calculateFWithoutShrinkage(dimensions, forces);
		if (mEd == 0) {
			fws = 0;
		}
		return fws;
	}
	

}
