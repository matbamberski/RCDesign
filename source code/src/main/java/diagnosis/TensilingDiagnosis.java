package diagnosis;

import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.PolynominalSolver;

public class TensilingDiagnosis extends BeamDiagnosis {


	public TensilingDiagnosis(double lAMBDA, double eTA) {
		super(lAMBDA, eTA);
		// TODO Auto-generated constructor stub
	}

	protected double e;
	protected double eMin;
	protected double eS1;
	protected double eS2;

	protected void calculateE(double mEd, double nEd, double h) {
		if (mEd == 0) {
			e = h / 30;
			System.out.println("e= e0 ( h/30 )" + e);
			if (e < 0.02) {
				e = 0.02;
				System.out.println("e= e0 ( 20mm  )" + e);
			}
		} else {
			e = Math.abs(mEd / nEd);
			System.out.println("e " + e);
		}
	}

	private void calculateEmin(double aS1, double aS2, DimensionsOfCrossSectionOfConcrete dimensions) {
		eMin = (aS1 * (0.5 * dimensions.getH() - dimensions.getA1()) - aS2 * (0.5 * dimensions.getH() - dimensions.getA2())) / (aS1 + aS2);
		System.out.println("eMin " + eMin);

	}

	private void calculateES1(DimensionsOfCrossSectionOfConcrete dimensions) {
		eS1 = e - 0.5 * dimensions.getH() + dimensions.getA1();
		System.out.println("eS1 " + eS1);
	}

	private void calculateES2(DimensionsOfCrossSectionOfConcrete dimensions) {
		eS2 = e + 0.5 * dimensions.getH() - dimensions.getA2();
		System.out.println("eS2 " + eS2);
	}

	private void calculateInitialX(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		x = (1 / LAMBDA) * ((eS2 + dimensions.getA2())
				- Math.sqrt((eS2 + dimensions.getA2()) * (eS2 + dimensions.getA2()) - (2 * steel.getFYd() * 1000 * (aS1 * eS1 - aS2 * eS2)) / (ETA*concrete.getFCd() * 1000 * dimensions.getB())));
		System.out.println("x " + x);
	}

	protected void calculateCapitalAWhileXIsLessThenXMinYd(DimensionsOfCrossSectionOfConcrete dimensions) {
		capitalA = -2 * (eS2 + dimensions.getA2()) / LAMBDA;
		System.out.println("capitalA " + capitalA);
	}

	protected void calculateCapitalBWhileXIsLessThenXMinYd(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		capitalB = 2 * (aS1 * steel.getFYd() * 1000 * eS1 - aS2 * concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS2) / (LAMBDA * LAMBDA * ETA*concrete.getFCd() * 1000 * dimensions.getB());
		System.out.println("capitalB " + capitalB);
	}

	protected void calculateCapitalCWhileXIsLessThenXMinYd(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS2) {
		capitalC = 2 * aS2 * concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS2 * dimensions.getA2() / (LAMBDA * LAMBDA * ETA*concrete.getFCd() * 1000 * dimensions.getB());
		System.out.println("capitalC " + capitalC);
	}

	protected void solvePolynominalABC() {
		x = PolynominalSolver.getMinimumRootOfPolynoiminalABC(capitalA, capitalB, capitalC);
		System.out.println("x " + x);
	}

	protected void calculateXWhenXIsLessThenXMinMinusYd(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		x = (1 / LAMBDA) * ((eS2 + dimensions.getA2())
				- Math.sqrt((eS2 + dimensions.getA2()) * (eS2 + dimensions.getA2()) - (2 * steel.getFYd() * 1000 * (aS1 * eS1 + aS2 * eS2)) / (ETA*concrete.getFCd() * 1000 * dimensions.getB())));
		System.out.println("x " + x);
	}

	protected void setSigmaS1EqualToFyd(Steel steel) {
		sigmaS1 = steel.getFYd();
		System.out.println("sigmaS1 " + sigmaS1);
	}

	protected void setSigmaS2EqualToMinusFyd(Steel steel) {
		sigmaS2 = -steel.getFYd();
		System.out.println("sigmaS2 " + sigmaS2);
	}

	protected void calculateSigmaS1(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, Steel steel) {
		sigmaS1 = concrete.getEpsilonCU3() * (dimensions.getD() - x) / x * steel.getES() * 1000;
		System.out.println("sigmaS1 " + sigmaS1);
	}

	protected void ifSigmaS1IsGreaterThenFYdThenSigmaS1IsEqualFyd(Steel steel) {
		if (sigmaS1 > steel.getFYd()) {
			sigmaS1 = steel.getFYd();
			System.out.println("sigmaS1 = fYd " + sigmaS1);
		}
	}

	protected void calculateSigmaS2(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, Steel steel) {
		sigmaS2 = concrete.getEpsilonCU3() * (x - dimensions.getA2()) / x * steel.getES() * 1000;
		System.out.println("sigmaS2 " + sigmaS2);
	}

	protected void ifSigmaS2IsGreaterThenFYdThenSigmaS2IsEqualFyd(Steel steel) {
		if (sigmaS2 > steel.getFYd()) {
			sigmaS2 = steel.getFYd();
			System.out.println("sigmaS2 > fYd -> sigmaS2= " + sigmaS2);
		}
	}

	protected void ifSigmaS2IsLessThenMinusFYdThenSigmaS2IsEqualMinusFyd(Steel steel) {
		if (sigmaS2 < -steel.getFYd()) {
			sigmaS2 = -steel.getFYd();
			System.out.println("sigmaS2 < - fYd -> sigmaS2= " + sigmaS2);
		}
	}

	protected void calculateNRd(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		nRd = ETA*concrete.getFCd() * 1000 * dimensions.getB() * LAMBDA * x - sigmaS1 * 1000 * aS1 + sigmaS2 * 1000 * aS2;
		System.out.println("nRd " + nRd);

	}

	protected void calculateMRd(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2, double nEd) {
		mRd = ETA*concrete.getFCd() * 1000 * dimensions.getB() * LAMBDA * x * (dimensions.getD() - 0.5 * LAMBDA * x) + sigmaS2 * 1000 * aS2 * (dimensions.getD() - dimensions.getA2())
				- nRd * (0.5 * dimensions.getH() - dimensions.getA1());

		System.out.println("mRd " + mRd);
	}

	protected void aS1IsEqualAS2andAS2isEqualAS1(double aS1, double aS2) {
		double aStemp = aS1;
		aS1 = aS2;
		aS2 = aStemp;
	}

	protected double setAs1EqualtoAs2(double aS2) {
		double aS1;
		aS1 = aS2;
		return aS1;
	}

	protected double setAs2EqualToAs1(double aS1) {
		double aS2;
		aS2 = aS1;
		return aS2;
	}

	private double ifNedIsLessThen0returnAbs(double nEd) {
		if (nEd < 0) {
			nEd = Math.abs(nEd);
		}
		return nEd;
	}

	public void doFullTensilingReinforcementDiagnosis(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double mEd, double nEd, double aS1, double aS2) {
		nEd = ifNedIsLessThen0returnAbs(nEd);
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		setXLimits(concrete, steel, dimensions.getD(), dimensions.getA2());
		calculateE(mEd, nEd, dimensions.getH());
		calculateEmin(aS1, aS2, dimensions);
		if (e < eMin) {
			System.out.println("e<eMin");
			System.out.println("aS1 = aS2 aS2=aS1");
			double aS1Temporary = aS1;
			aS1 = setAs1EqualtoAs2(aS2);
			aS2 = setAs2EqualToAs1(aS1Temporary);
		}
		calculateES1(dimensions);
		calculateES2(dimensions);
		calculateInitialX(concrete, steel, dimensions, aS1, aS2);
		if (x < xMinYd) {
			System.out.println("x<xMinYd");
			calculateCapitalAWhileXIsLessThenXMinYd(dimensions);
			calculateCapitalBWhileXIsLessThenXMinYd(concrete, steel, dimensions, aS1, aS2);
			calculateCapitalCWhileXIsLessThenXMinYd(concrete, steel, dimensions, aS2);
			solvePolynominalABC();
			if (x < xMinMinusYd) {
				System.out.println("x<xMinMinusYd");
				calculateXWhenXIsLessThenXMinMinusYd(concrete, steel, dimensions, aS1, aS2);
			}
		} else {
			System.out.println("x>=xMinYd");
		}
		if (x > 0) {
			System.out.println("x>0");
			calculateSigmaS1(concrete, dimensions, steel);
			ifSigmaS1IsGreaterThenFYdThenSigmaS1IsEqualFyd(steel);
			calculateSigmaS2(concrete, dimensions, steel);
			ifSigmaS2IsGreaterThenFYdThenSigmaS2IsEqualFyd(steel);
			ifSigmaS2IsLessThenMinusFYdThenSigmaS2IsEqualMinusFyd(steel);
		} else {
			x = 0;
			System.out.println("x<=0");
			setSigmaS1EqualToFyd(steel);
			setSigmaS2EqualToMinusFyd(steel);
		}
		calculateNRd(concrete, dimensions, aS1, aS2);
		calculateMRd(concrete, dimensions, aS1, aS2, nEd);

	}
	
	public void runDiagnosis(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double mEd, double nEd, double aS1, double aS2) {
		nEd = ifNedIsLessThen0returnAbs(nEd);
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		setXLimits(concrete, steel, dimensions.getD(), dimensions.getA2());
		calculateE(mEd, nEd, dimensions.getH());
		calculateEmin(aS1, aS2, dimensions);
		if (e < eMin) {
			System.out.println("e<eMin");
			System.out.println("aS1 = aS2 aS2=aS1");
			double aS1Temporary = aS1;
			aS1 = setAs1EqualtoAs2(aS2);
			aS2 = setAs2EqualToAs1(aS1Temporary);
		}
		calculateES1(dimensions);
		calculateES2(dimensions);
		calculateInitialX(concrete, steel, dimensions, aS1, aS2);
		if (x < xMinYd) {
			System.out.println("x<xMinYd");
			calculateCapitalAWhileXIsLessThenXMinYd(dimensions);
			calculateCapitalBWhileXIsLessThenXMinYd(concrete, steel, dimensions, aS1, aS2);
			calculateCapitalCWhileXIsLessThenXMinYd(concrete, steel, dimensions, aS2);
			solvePolynominalABC();
			if (x < xMinMinusYd) {
				System.out.println("x<xMinMinusYd");
				calculateXWhenXIsLessThenXMinMinusYd(concrete, steel, dimensions, aS1, aS2);
			}
		} else {
			System.out.println("x>=xMinYd");
		}
		if (x > 0) {
			System.out.println("x>0");
			calculateSigmaS1(concrete, dimensions, steel);
			ifSigmaS1IsGreaterThenFYdThenSigmaS1IsEqualFyd(steel);
			calculateSigmaS2(concrete, dimensions, steel);
			ifSigmaS2IsGreaterThenFYdThenSigmaS2IsEqualFyd(steel);
			ifSigmaS2IsLessThenMinusFYdThenSigmaS2IsEqualMinusFyd(steel);
		} else {
			x = 0;
			System.out.println("x<=0");
			setSigmaS1EqualToFyd(steel);
			setSigmaS2EqualToMinusFyd(steel);
		}
		calculateNRd(concrete, dimensions, aS1, aS2);
		calculateMRd(concrete, dimensions, aS1, aS2, nEd);

	}

}
