package SLS.cracks;

import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.Interpolation;

public class Scratch {

	private double wK;

	private double sRMax;

	private static double K1 = 0.8;
	private double k2;
	private static double K3 = 3.4;
	private static double K4 = 0.425;

	private double roPEff;
	private double c;
	private double aS; // area of tensiling reinforcement
	private double aCEff;

	// scratches without crack width

	private double phiS;
	private double phiSStar;
	private double phi;
	private final double Kc = 0.4;
	private double hCr;
	private double sigmaS;
	private final double[] wK04 = { 40, 32, 20, 16, 12, 10, 8, 6 };
	private final double[] wK03 = { 32, 25, 16, 12, 10, 8, 6, 5 };
	private final double[] wK02 = { 25, 16, 12, 8, 6, 5, 4, 0 };
	private final double[] sigmaSTab = { 160, 200, 240, 280, 320, 360, 400, 450 };

	// cracj width

	private double mCr;

	public double getMCr() {
		return mCr;
	}

	private double epsilonSmMinusEpsilonCm;
	private double kT;
	private double wMax;
	// scratchet without calculatinc crack width

	private void calculatePhiSStar(DimensionsOfCrossSectionOfConcrete dimensions) {
		double[] tab = wK03;
		if (dimensions.getwLim() == 0.2) {
			tab = wK02;
		}
		if (dimensions.getwLim() == 0.3) {
			tab = wK03;
		}
		if (dimensions.getwLim() == 0.4) {
			tab = wK04;
		}
		if (sigmaS <= 450 && sigmaS >= 160) {
			phiSStar = Interpolation.interpolate(sigmaSTab, tab, sigmaS);
		}
		if (sigmaS > 450) {
			phiSStar = tab[7];
		}
		if (sigmaS < 160) {
			phiSStar = tab[0];
		}

	}

	private void calculateSigmaS(double alfaE, double mEd, double i2, double x2, DimensionsOfCrossSectionOfConcrete dimensions) {
		sigmaS = (alfaE * mEd * 1000 / i2 * (dimensions.getD() - x2)) / 1000000;
		System.out.println("SIGMA S" + sigmaS);
	}

	private void calculateHcr(DimensionsOfCrossSectionOfConcrete dimensions) {
		hCr = 0.5 * dimensions.getH();
	}

	private void calculatePhiS(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		phiS = phiSStar * concrete.getFCtm() / 2.9 * Kc * hCr / (2 * (dimensions.getH() - dimensions.getD()));
	}

	public double checkScratchesWithoutCalculatingCrackWidth(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, double mEd, double alfaE, double i2, double x2) {
		calculateSigmaS(alfaE, mEd, i2, x2, dimensions);
		calculatePhiSStar(dimensions);
		calculateHcr(dimensions);
		calculatePhiS(concrete, dimensions);
		return phiS;
	}

	//////////////////////////////
	private void calculateMCr(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		mCr = concrete.getFCtm() * dimensions.getWc() * 1000;
	}

	private void calculateKt(InternalForces forces) {
		if (forces.isLoadSustained()) {
			kT = 0.4;
		} else {
			kT = 0.6;
		}
	}

	private void calculateACEff(DimensionsOfCrossSectionOfConcrete dimensions, double x2) {
		aCEff = dimensions.getB() * Math.min(2.5 * (dimensions.getH() - dimensions.getD()), (dimensions.getH() - x2) / 3);
	}

	private void calculateAs(double aSRozciagane) {
		aS = aSRozciagane;
	}

	private void calculateRoPEff() {
		roPEff = aS / aCEff;
	}

	private void calculateEpislonSmMinusEpsilonCm(Steel steel, Concrete concrete, InternalForces forces, double alfaE) {
		double partOne = (sigmaS - kT * concrete.getFCtm() / roPEff * (1 + alfaE * roPEff)) / (steel.getES() * 1000);
		double partTwo = 0.6 * sigmaS / (steel.getES() * 1000);
		epsilonSmMinusEpsilonCm = Math.max(partOne, partTwo);
	}

	private void calculateC(DimensionsOfCrossSectionOfConcrete dimensions, double aSTensiledDiameter, Reinforcement reinforcement, double mEd) {
		if (mEd > 0) {
			c = dimensions.getA1() - aSTensiledDiameter / 1000 / 2;
		} else {
			c = dimensions.getA2() - aSTensiledDiameter / 1000 / 2;
		}
	}

	private void calculateK2(double mEd, double nEd) {
		if (mEd == 0 && nEd < 0) {
			k2 = 1;
		} else {
			k2 = 0.5;
		}
	}

	private void calculateSRMax(double aSTensiledDiameter) {
		sRMax = K3 * c + K1 * k2 * K4 * (aSTensiledDiameter / 1000) / roPEff;
	}

	private void calculateWMax() {
		wMax = sRMax * 1000 * epsilonSmMinusEpsilonCm;
	}

	private void setMCrEqualTo0() {
		mCr = 0;
	}

	public double checkScratchesWithCalculatingCrackWidth(Steel steel, Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, InternalForces forces, double x2,
			double alfaE, double aSRozciagane, double aSTensiledDiameter, double mEd, double nEd) {
		calculateMCr(concrete, dimensions);
		if (mCr < Math.abs(forces.getCharacteristicMEdCalkowita())) {
			calculateKt(forces);
			calculateACEff(dimensions, x2);
			calculateAs(aSRozciagane);
			calculateRoPEff();
			calculateEpislonSmMinusEpsilonCm(steel, concrete, forces, alfaE);
			calculateK2(mEd, nEd);
			calculateC(dimensions, aSTensiledDiameter, reinforcement, mEd);
			calculateSRMax(aSTensiledDiameter);
			calculateWMax();
		} else {
			setMCrEqualTo0();
		}
		if (mEd == 0) {
			wMax = 0;
		}
		return wMax;
	}

}
