package diagnosis;

import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.PolynominalSolver;

public class CompressingDiagnosis extends TensilingDiagnosis {

	private void eIsEqualMinusMedDividedByNed(double mEd, double nEd) {
		e = -mEd / nEd;
	}

	private void calculateEmin(Concrete concrete, Steel steel, double aS1, double aS2, DimensionsOfCrossSectionOfConcrete dimensions) {
		double nominator = concrete.getEpsilonC3() * steel.getES() * 1000000 * (aS2 * (0.5 * dimensions.getH() - dimensions.getA2()) - aS1 * (0.5 * dimensions.getH() - dimensions.getA1()));
		double denominator = (concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH() + concrete.getEpsilonC3() * steel.getES() * 1000000 * (aS1 + aS2));
		eMin = nominator / denominator;

		System.out.println("eMin " + eMin);

	}

	private void calculateES1(DimensionsOfCrossSectionOfConcrete dimensions) {
		eS1 = e + 0.5 * dimensions.getH() - dimensions.getA1();
		System.out.println("eS1 " + eS1);
	}

	private void calculateES2(DimensionsOfCrossSectionOfConcrete dimensions) {
		eS2 = e - 0.5 * dimensions.getH() + dimensions.getA2();
		System.out.println("eS2 " + eS2);
	}

	private void calculateInitialX(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		x = (1 / LAMBDA) * (-(eS2 - dimensions.getA2())
				+ Math.sqrt((eS2 - dimensions.getA2()) * (eS2 - dimensions.getA2()) + (2 * steel.getFYd() * 1000 * (aS1 * eS1 - aS2 * eS2)) / (concrete.getFCd() * 1000 * dimensions.getB())));
		System.out.println("x " + x);
	}

	protected void calculateCapitalAWhileXIsGreaterThenXLim(DimensionsOfCrossSectionOfConcrete dimensions) {
		capitalA = 2 * (eS2 - dimensions.getA2()) / LAMBDA;
		System.out.println("capitalA " + capitalA);
	}

	protected void calculateCapitalBWhileXIsGreaterThenXLim(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		capitalB = 2 * (aS2 * steel.getFYd() * 1000 * eS2 + aS1 * concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS1) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * dimensions.getB());
		System.out.println("capitalB " + capitalB);
	}

	protected void calculateCapitalCWhileXIsGreaterThenXLim(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1) {
		capitalC = -2 * aS1 * concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS1 * dimensions.getD() / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * dimensions.getB());
		System.out.println("capitalC " + capitalC);
	}

	@Override
	protected void solvePolynominalABC() {
		x = PolynominalSolver.getMinimumRootOfPolynoiminalABC(capitalA, capitalB, capitalC);
		System.out.println("x " + x);
	}

	protected void solvePolynominalABCAndReturnMaxX() {
		x = PolynominalSolver.getMaximumRootOfPolynominalABC(capitalA, capitalB, capitalC);
		System.out.println("x " + x);
	}

	private void calculateCapitalAWhileXIsGreaterThenH(DimensionsOfCrossSectionOfConcrete dimensions) {
		capitalA = -x0 + 2 * (eS2 - dimensions.getA2()) / LAMBDA;
	}

	private void calculateCapitalBWhileXIsGreaterThenH(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		double firstHalf = (steel.getFYd() * 1000 * aS2 * eS2 + concrete.getEpsilonC3() * steel.getES() * 1000000 * aS1 * eS1) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * dimensions.getB());
		double secondHalf = -(eS2 - dimensions.getA2()) / LAMBDA * x0;
		// cuz it might be out of memory
		capitalB = 2 * (firstHalf + secondHalf);
	}

	private void calculateCapitalCWhileXIsGreaterThenH(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		capitalC = -2 * (steel.getFYd() * 1000 * aS2 * x0 * eS2 + concrete.getEpsilonC3() * steel.getES() * 1000000 * aS1 * eS1 * dimensions.getD())
				/ (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * dimensions.getB());
	}

	private void calculateXWhileXIsGreaterThenHDividedByLambda(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		double counter = concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH() * e * x0
				+ concrete.getEpsilonC3() * steel.getES() * 1000000 * (aS1 * eS1 * dimensions.getD() + aS2 * eS2 * dimensions.getA2());
		double denominator = concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH() * e + concrete.getEpsilonC3() * steel.getES() * 1000000 * (aS1 * eS1 + aS2 * eS2);
		x = counter / denominator;
	}

	private void calculateXWhileXIsLessOrEqualToXMaxYd(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		double counter = (concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH() * e + steel.getFYd() * 1000 * eS2 * aS2) * x0
				+ concrete.getEpsilonC3() * steel.getES() * 1000000 * aS1 * eS1 * dimensions.getD();
		double denominator = concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH() * e + steel.getFYd() * 1000 * eS2 * aS2 + concrete.getEpsilonC3() * steel.getES() * 1000000 * aS1 * eS1;
		x = counter / denominator;
	}

	private void calculateSigmaS1WhenXIsLessOrEqualToXLim(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions) {
		sigmaS1 = concrete.getEpsilonC3() * (dimensions.getD() - x) / (x - x0) * steel.getES() * 1000;
		if (sigmaS1 < -steel.getFYd()) {
			sigmaS1 = -steel.getFYd();
		}
	}

	private void calculateSigmaS2WhenXIsLessOrEqualToXLim(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions) {
		sigmaS2 = concrete.getEpsilonC3() * (x - dimensions.getA2()) / (x - x0) * steel.getES() * 1000;
		if (sigmaS2 > steel.getFYd()) {
			sigmaS2 = steel.getFYd();
		}
	}

	private void xIsEqualToHDividedByLambda(DimensionsOfCrossSectionOfConcrete dimensions) {
		x = dimensions.getH() / LAMBDA;
	}

	private void calculateSigmaS1WhileXIsGreaterThenH(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions) {
		sigmaS1 = concrete.getEpsilonCU3() * (dimensions.getD() - x) / x * steel.getES() * 1000;
		if (sigmaS1 > steel.getFYd()) {
			sigmaS1 = steel.getFYd();
		}
	}

	private void calculateSigmaS2WhileXIsGreaterThenH(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions) {
		sigmaS2 = concrete.getEpsilonCU3() * (x - dimensions.getA2()) / x * steel.getES() * 1000;
		if (sigmaS2 > steel.getFYd()) {
			sigmaS2 = steel.getFYd();
		}
		if (sigmaS2 < -steel.getFYd()) {
			sigmaS2 = -steel.getFYd();
		}
	}

	protected void calculateCapitalAWhileXIsLessOrEqualToXLim(DimensionsOfCrossSectionOfConcrete dimensions) {
		capitalA = 2 * (eS2 - dimensions.getA2()) / LAMBDA;
		System.out.println("capitalA " + capitalA);
	}

	protected void calculateCapitalBWhileXIsLessOrEqualToXLim(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		capitalB = -2 * (aS1 * steel.getFYd() * 1000 * eS1 - aS2 * concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS2) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * dimensions.getB());
		System.out.println("capitalB " + capitalB);
	}

	protected void calculateCapitalCWhileXIsLessOrEqualToXLim(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS2) {
		capitalC = -2 * aS2 * concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS2 * dimensions.getA2() / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * dimensions.getB());
		System.out.println("capitalC " + capitalC);
	}

	private void calculateXWhenXIsLessOrEqualToXMinMinusYd(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		x = 1 / LAMBDA * (-(eS2 - dimensions.getA2())
				+ Math.sqrt((eS2 - dimensions.getA2()) * (eS2 - dimensions.getA2()) + 2 * steel.getFYd() * (aS1 * eS1 + aS2 * eS2) / (concrete.getFCd() * dimensions.getB())));
	}

	@Override
	protected void calculateNRd(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2) {
		nRd = concrete.getFCd() * 1000 * dimensions.getB() * LAMBDA * x - sigmaS1 * 1000 * aS1 + sigmaS2 * 1000 * aS2;
		System.out.println("nRd " + nRd);
	}

	@Override
	protected void calculateMRd(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2, double nEd) {
		mRd = concrete.getFCd() * 1000 * dimensions.getB() * LAMBDA * x * (dimensions.getD() - 0.5 * LAMBDA * x) + sigmaS2 * 1000 * aS2 * (dimensions.getD() - dimensions.getA2())
				- nRd * (0.5 * dimensions.getH() - dimensions.getA1());
		System.out.println("mRd " + mRd);
	}

	public void doFullCompresingReinforcementDiagnosis(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double aS1, double aS2, double mEd, double nEd) {
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		calculateE(mEd, nEd, dimensions.getH());
		setXLimits(concrete, steel, dimensions.getD(), dimensions.getA2());
		setX0(concrete, steel, dimensions.getH());
		setXMaxYd(concrete, steel, dimensions.getA2());
		calculateEmin(concrete, steel, aS1, aS2, dimensions);
		if (e < eMin) {
			double aS1Temporary = aS1;
			aS1 = setAs1EqualtoAs2(aS2);
			aS2 = setAs2EqualToAs1(aS1Temporary);
		}
		calculateES1(dimensions);
		calculateES2(dimensions);
		calculateInitialX(concrete, steel, dimensions, aS1, aS2);
		if (x > xLim) {
			calculateCapitalAWhileXIsGreaterThenXLim(dimensions);
			calculateCapitalBWhileXIsGreaterThenXLim(concrete, steel, dimensions, aS1, aS2);
			calculateCapitalCWhileXIsGreaterThenXLim(concrete, steel, dimensions, aS1);
			solvePolynominalABC();
			if (x > dimensions.getH()) {
				calculateCapitalAWhileXIsGreaterThenH(dimensions);
				calculateCapitalBWhileXIsGreaterThenH(concrete, steel, dimensions, aS1, aS2);
				calculateCapitalCWhileXIsGreaterThenH(concrete, steel, dimensions, aS1, aS2);
				solvePolynominalABCAndReturnMaxX();
				if (x > (dimensions.getH() / LAMBDA)) {
					calculateXWhileXIsGreaterThenHDividedByLambda(concrete, steel, dimensions, aS1, aS2);
					if (x <= xMaxYd) {
						calculateXWhileXIsLessOrEqualToXMaxYd(concrete, steel, dimensions, aS1, aS2);
					}
				}
				calculateSigmaS1WhenXIsLessOrEqualToXLim(concrete, steel, dimensions);
				calculateSigmaS2WhenXIsLessOrEqualToXLim(concrete, steel, dimensions);
				if (x > (dimensions.getH() / LAMBDA)) {
					xIsEqualToHDividedByLambda(dimensions);
				}
			} else {
				calculateSigmaS1WhileXIsGreaterThenH(concrete, steel, dimensions);
				calculateSigmaS2WhileXIsGreaterThenH(concrete, steel, dimensions);
			}
		} else {
			if (x < xMinYd) {
				calculateCapitalAWhileXIsLessOrEqualToXLim(dimensions);
				calculateCapitalBWhileXIsLessOrEqualToXLim(concrete, steel, dimensions, aS1, aS2);
				calculateCapitalCWhileXIsLessOrEqualToXLim(concrete, steel, dimensions, aS2);
				solvePolynominalABC();
				if (x <= xMinMinusYd) {
					calculateXWhenXIsLessOrEqualToXMinMinusYd(concrete, steel, dimensions, aS1, aS2);
				}
			}
			calculateSigmaS1WhileXIsGreaterThenH(concrete, steel, dimensions);
			calculateSigmaS2WhileXIsGreaterThenH(concrete, steel, dimensions);
		}
		calculateNRd(concrete, dimensions, aS1, aS2);
		calculateMRd(concrete, dimensions, aS1, aS2, nEd);
		System.out.println("aa");
	}
	
	public void runDiagnosis(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, double mEd, double nEd, double aS1, double aS2) {
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		calculateE(mEd, nEd, dimensions.getH());
		setXLimits(concrete, steel, dimensions.getD(), dimensions.getA2());
		setX0(concrete, steel, dimensions.getH());
		setXMaxYd(concrete, steel, dimensions.getA2());
		calculateEmin(concrete, steel, aS1, aS2, dimensions);
		if (e < eMin) {
			double aS1Temporary = aS1;
			aS1 = setAs1EqualtoAs2(aS2);
			aS2 = setAs2EqualToAs1(aS1Temporary);
		}
		calculateES1(dimensions);
		calculateES2(dimensions);
		calculateInitialX(concrete, steel, dimensions, aS1, aS2);
		if (x > xLim) {
			calculateCapitalAWhileXIsGreaterThenXLim(dimensions);
			calculateCapitalBWhileXIsGreaterThenXLim(concrete, steel, dimensions, aS1, aS2);
			calculateCapitalCWhileXIsGreaterThenXLim(concrete, steel, dimensions, aS1);
			solvePolynominalABC();
			if (x > dimensions.getH()) {
				calculateCapitalAWhileXIsGreaterThenH(dimensions);
				calculateCapitalBWhileXIsGreaterThenH(concrete, steel, dimensions, aS1, aS2);
				calculateCapitalCWhileXIsGreaterThenH(concrete, steel, dimensions, aS1, aS2);
				solvePolynominalABCAndReturnMaxX();
				if (x > (dimensions.getH() / LAMBDA)) {
					calculateXWhileXIsGreaterThenHDividedByLambda(concrete, steel, dimensions, aS1, aS2);
					if (x <= xMaxYd) {
						calculateXWhileXIsLessOrEqualToXMaxYd(concrete, steel, dimensions, aS1, aS2);
					}
				}
				calculateSigmaS1WhenXIsLessOrEqualToXLim(concrete, steel, dimensions);
				calculateSigmaS2WhenXIsLessOrEqualToXLim(concrete, steel, dimensions);
				if (x > (dimensions.getH() / LAMBDA)) {
					xIsEqualToHDividedByLambda(dimensions);
				}
			} else {
				calculateSigmaS1WhileXIsGreaterThenH(concrete, steel, dimensions);
				calculateSigmaS2WhileXIsGreaterThenH(concrete, steel, dimensions);
			}
		} else {
			if (x < xMinYd) {
				calculateCapitalAWhileXIsLessOrEqualToXLim(dimensions);
				calculateCapitalBWhileXIsLessOrEqualToXLim(concrete, steel, dimensions, aS1, aS2);
				calculateCapitalCWhileXIsLessOrEqualToXLim(concrete, steel, dimensions, aS2);
				solvePolynominalABC();
				if (x <= xMinMinusYd) {
					calculateXWhenXIsLessOrEqualToXMinMinusYd(concrete, steel, dimensions, aS1, aS2);
				}
			}
			calculateSigmaS1WhileXIsGreaterThenH(concrete, steel, dimensions);
			calculateSigmaS2WhileXIsGreaterThenH(concrete, steel, dimensions);
		}
		calculateNRd(concrete, dimensions, aS1, aS2);
		calculateMRd(concrete, dimensions, aS1, aS2, nEd);
		System.out.println("aa");
	}

}
