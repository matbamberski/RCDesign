package reinforcement.axisload;

import materials.Concrete;
import materials.Steel;
import util.PolynominalSolver;
import util.ResultsToPDF;

public class UnsymmetricalTensilingBeamReinforcement extends BeamMimosrodowoObciazona {

	protected double mS2;

	private void setXFirstCondition(Concrete concrete, double nEd, double d, double a2, double b) {
		double licznik = 2 * (nEd * eS1 - sigmaS2 * 1000 * aS2 * (d - a2));
		double mianownik = (concrete.getFCd() * 1000 * b);
		x = (1 / LAMBDA) * (d - Math.sqrt(d * d - licznik / mianownik));
		System.out.println("x " + x);
	}

	private void setMS2(Concrete concrete, Steel steel, double d, double a2) {
		mS2 = concrete.getEpsilonCU3() * steel.getES() * 1000000 * aSMin * (d - a2);
		System.out.println("mS2 " + mS2);
	}

	private void setCapitalA(double d) {
		capitalA = -2 * d / LAMBDA;
		System.out.println("capitalA " + capitalA);

	}

	private void setCapitalB(Concrete concrete, double nEd, double b) {
		capitalB = 2 * (nEd * eS1 - mS2) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * b);
		System.out.println("capitalB " + capitalB);

	}

	private void setCapitalC(Concrete concrete, double a2, double b) {
		capitalC = 2 * a2 * mS2 / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * b);
		System.out.println("capitalC " + capitalC);

	}

	private void setSigmaS2(Concrete concrete, Steel steel, double a2) {
		sigmaS2 = concrete.getEpsilonCU3() * (x - a2) / x * steel.getES() * 1000;
		System.out.println("sigmaS2 " + sigmaS2);

	}

	private void setXSecondCondition(Concrete concrete, Steel steel, double nEd, double d, double a2, double b) {

		x = (1 / LAMBDA) * (d - Math.sqrt(d * d - (2 * (nEd * eS1 + steel.getFYd() * 1000 * aS2 * (d - a2)) / (concrete.getFCd() * 1000 * b))));
		System.out.println("x " + x);

	}

	private void setAS1FirstCondition(Concrete concrete, Steel steel, double b, double nEd) {
		aS1 = (sigmaS2 * 1000 * aS2 + concrete.getFCd() * 1000 * b * LAMBDA * x + nEd) / (steel.getFYd() * 1000);
		System.out.println("aS1 " + aS1);

	}

	private void setAS2WhileXIsGreaterThen0(Concrete concrete, double nEd, double b, double d, double a2) {
		aS2 = (nEd * eS1 - concrete.getFCd() * 1000 * b * LAMBDA * x * (d - 0.5 * LAMBDA * x)) / (sigmaS2 * 1000 * (d - a2));
		System.out.println("aS2 " + aS2);

	}

	private void setAs1WhenAs2IsLessThenAsMin(Concrete concrete, Steel steel, double nEd, double b) {

		aS1 = (sigmaS2 * 1000 * aS2 + concrete.getFCd() * 1000 * b * LAMBDA * x + nEd) / (steel.getFYd() * 1000);
	}

	private void setAS1WhileXIsLessThen0(Steel steel, double nEd, double d, double a2) {
		aS1 = (nEd * eS2) / (steel.getFYd() * 1000 * (d - a2));
		System.out.println("aS1 " + aS1);
	}

	private void setAS2WhileXIsLessThen0(Steel steel, double nEd, double d, double a2) {
		aS2 = (nEd * eS1) / (-steel.getFYd() * 1000 * (d - a2));
		System.out.println("aS2 " + aS2);
	}

	private void setAs2EqualToAsMin() {
		aS2 = aSMin;
		System.out.println("aS2=aSmin " + aS2);
	}

	private void setSigmaS2EqualToMinusFYd(Steel steel) {
		sigmaS2 = -steel.getFYd();
		System.out.println("sigmaS2 = -fYd " + sigmaS2);
	}

	private void setSigmaS2EqualToFYd(Steel steel) {
		sigmaS2 = steel.getFYd();
		System.out.println("sigmaS2 = fYd " + sigmaS2);
	}

	private void pushResultsToPDF(Concrete concrete, Steel steel, double mEd, double nEd) {
		ResultsToPDF.addResults("**Przekrój rozci¹gany zbrojenie niesymetryczne **\n\n ", "");
		ResultsToPDF.addResults("Med", String.valueOf(mEd));
		ResultsToPDF.addResults("Ned", String.valueOf(nEd));
		ResultsToPDF.addResults("Fcd", String.valueOf(concrete.getFCd()));
		ResultsToPDF.addResults("Fyd", String.valueOf(steel.getFYd()));
		ResultsToPDF.addResults("Es", String.valueOf(steel.getES()));
		ResultsToPDF.addResults("e", String.valueOf(e));
		ResultsToPDF.addResults("eS1", String.valueOf(eS1));
		ResultsToPDF.addResults("eS2", String.valueOf(eS2));
		ResultsToPDF.addResults("xMin-Yd", String.valueOf(xMinMinusYd));
		ResultsToPDF.addResults("xMinYd", String.valueOf(xMinYd));
		ResultsToPDF.addResults("x", String.valueOf(x));
		ResultsToPDF.addResults("As1", String.valueOf(aS1));
		ResultsToPDF.addResults("As2", String.valueOf(aS2));
	}

	protected void unsymmetricalAnalysisWhileAS2IsLessOrEqualToASMin(Concrete concrete, Steel steel, double nEd, double d, double a2, double b) {
		setAs2EqualToAsMin();
		setXFirstCondition(concrete, nEd, d, a2, b);
		if (x < xMinYd) {
			System.out.println(" x< xMinYd");
			setMS2(concrete, steel, d, a2);
			setCapitalA(d);
			setCapitalB(concrete, nEd, b);
			setCapitalC(concrete, a2, b);
			x = PolynominalSolver.getMinimumRootOfPolynoiminalABC(capitalA, capitalB, capitalC);

			if (x <= xMinMinusYd) {
				System.out.println(" x<= xMinMinusYd");
				setSigmaS2EqualToMinusFYd(steel);
				setXSecondCondition(concrete, steel, nEd, d, a2, b);
			} else {
				System.out.println(" x> xMinMinusYd");
				setSigmaS2(concrete, steel, a2);
			}
		} else {
			System.out.println(" x>= xMinMinusYd");
			setSigmaS2EqualToFYd(steel);
		}
	}

	public void fullUnsymmetricalTebsilingBeamReinforcement(Concrete concrete, Steel steel, double mEd, double nEd, double d, double a1, double a2, double b, double h) {
		System.out.println("\n \n niesymetryczne rozciaganie \n \n ");
		mEd = ifMedIsLessThenZeroReturnAbsAndSetBoolean(mEd);
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		mEd = ifMedIsEqualZeroSetMedToVeryLowPercentOfNed(mEd, nEd);
		setEValuesForTensiling(h, a1, a2, mEd, nEd);
		setInitialXValues(concrete, steel, d, a2);
		x = xLim;
		setSigmaS2(concrete, steel, a2);

		if (sigmaS2 > steel.getFYd()) {
			sigmaS2 = steel.getFYd();
			System.out.println(sigmaS2);
		}

		setAS2WhileXIsGreaterThen0(concrete, nEd, b, d, a2);
		setASMin(steel, nEd, b, h);
		if (aS2 <= aSMin) {
			unsymmetricalAnalysisWhileAS2IsLessOrEqualToASMin(concrete, steel, nEd, d, a2, b);
			if (x > 0) {
				setAS1FirstCondition(concrete, steel, b, nEd);

			} else {
				x = 0;
				setAS1WhileXIsLessThen0(steel, nEd, d, a2);
				setAS2WhileXIsLessThen0(steel, nEd, d, a2);
			}
		} else {
			setAs1WhenAs2IsLessThenAsMin(concrete, steel, nEd, b);
		}
		System.out.println(sigmaS2);
		// changeAs1ToAsMinIfAs1IsLessThenAsMin();
		// changeAs2ToAsMinIfAs2IsLessThenAsMin();
		ifMedIsLessThenZeroSwapAs1WithAs2();
		pushResultsToPDF(concrete, steel, mEd, nEd);
	}

}
