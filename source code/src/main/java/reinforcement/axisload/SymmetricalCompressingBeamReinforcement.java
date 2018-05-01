package reinforcement.axisload;

import materials.Concrete;
import materials.Steel;
import util.ResultsToPDF;

public class SymmetricalCompressingBeamReinforcement extends SymmetricalTensilingBeamReinforcement {

	protected double mS1;
	protected double mS2;
	protected double f1;
	protected double f2;

	private void setXFirstCondition(Concrete concrete, double nEd, double b) {
		x = (1 / LAMBDA) * nEd / (concrete.getFCd() * 1000 * b);
		System.out.println("x" + x);

	}

	private void setCapitalAFirstCondition(Concrete concrete, Steel steel) {
		capitalA = LAMBDA * (steel.getFYd() * 1000 + concrete.getEpsilonCU3() * steel.getES() * 1000000);
		System.out.println("capitalA" + capitalA);

	}

	private void setCapitalBFirstCondition(Concrete concrete, Steel steel, double a2, double d) {
		capitalB = -2 * (steel.getFYd() * 1000 * a2 + concrete.getEpsilonCU3() * steel.getES() * 1000000 * d * (1 + 0.5 * LAMBDA));
		System.out.println("capitalB" + capitalB);

	}

	private void setCapitalCFirstCondition(Concrete concrete, Steel steel, double d, double b, double nEd) {
		capitalC = 2 * ((nEd * (steel.getFYd() * 1000 * eS2 + concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS1) / (LAMBDA * concrete.getFCd() * 1000 * b))
				+ concrete.getEpsilonCU3() * steel.getES() * 1000000 * d * d);
		System.out.println("capitalC" + capitalC);

	}

	private void setCapitalDFirstCondition(Concrete concrete, Steel steel, double nEd, double b, double d) {
		capitalD = -(2 * nEd / (LAMBDA * concrete.getFCd() * 1000 * b)) * concrete.getEpsilonCU3() * steel.getES() * 1000000 * d * eS1;
		System.out.println("capitalD" + capitalD);

	}

	private void setCapitalASecondCondition(Concrete concrete, Steel steel) {
		capitalA = LAMBDA * (steel.getFYd() * 1000 + concrete.getEpsilonC3() * steel.getES() * 1000000);
		System.out.println("capitalA " + capitalA);

	}

	private void setCapitalBSecondCondition(Concrete concrete, Steel steel, double a2, double d) {
		capitalB = -2 * (steel.getFYd() * 1000 * (a2 + 0.5 * LAMBDA * x0) + concrete.getEpsilonC3() * steel.getES() * 1000000 * d * (1 + 0.5 * LAMBDA));
		System.out.println("capitalB " + capitalB);

	}

	private void setCapitalCSecondCondition(Concrete concrete, Steel steel, double d, double b, double nEd, double a2) {
		double licznik = (nEd * (steel.getFYd() * 1000 * eS2 + concrete.getEpsilonC3() * steel.getES() * 1000000 * eS1));
		double mianownik = (LAMBDA * concrete.getFCd() * 1000 * b);
		double part1 = concrete.getEpsilonC3() * steel.getES() * 1000000 * d * d;
		double part2 = steel.getFYd() * 1000 * a2 * x0;
		capitalC = 2 * ((licznik / mianownik) + part1 + part2);
		System.out.println("capitalC " + capitalC);

	}

	private void setCapitalDSecondCondition(Concrete concrete, Steel steel, double nEd, double b, double d) {
		capitalD = -(2 * nEd / (LAMBDA * concrete.getFCd() * 1000 * b)) * (concrete.getEpsilonC3() * steel.getES() * 1000000 * d * eS1 + steel.getFYd() * 1000 * x0 * eS2);
		System.out.println("capitalD " + capitalD);

	}

	private void setF1FirstCondition(Concrete concrete, double nEd, double b, double h, double a1) {
		f1 = nEd * eS1 - concrete.getFCd() * 1000 * b * h * (0.5 * h - a1);
		System.out.println("f1 " + f1);

	}

	private void setF2FirstCondition(Concrete concrete, double nEd, double b, double h, double a2) {
		f2 = nEd * eS2 + concrete.getFCd() * 1000 * b * h * (0.5 * h - a2);
		System.out.println("f2 " + f2);

	}

	private void setXSecondCondition(Concrete concrete, Steel steel, double d) {
		double licznik = (concrete.getEpsilonC3() * steel.getES() * 1000000 * d * f1 + steel.getFYd() * 1000 * x0 * f2);
		double mianownik = (concrete.getEpsilonC3() * steel.getES() * 1000000 * f1 + steel.getFYd() * 1000 * f2);
		x = licznik / mianownik;
		System.out.println("x " + x);

	}

	private void setF1SecondCondition(Concrete concrete, double nEd, double d, double a2, double b, double h, double a1) {
		f1 = nEd * (eS1 * d + eS2 * a2) + concrete.getFCd() * 1000 * b * h * 0.5 * ((a1 - a2) * (d + a2) - (d - a2) * (d - a2));
		System.out.println("f1 " + f1);

	}

	private void setF2SecondCondition(Concrete concrete, double nEd, double b, double h, double a1, double a2) {
		f2 = nEd * (eS1 + eS2) + concrete.getFCd() * 1000 * b * h * (a1 - a2);
		System.out.println("f2 " + f2);

	}

	private void setSigmaS1FirstCondition(Concrete concrete, Steel steel, double d) {
		sigmaS1 = concrete.getEpsilonCU3() * (d - x) / (x) * steel.getES() * 1000;
		System.out.println("sigmaS1 " + sigmaS1);

	}

	private void setSigmaS1SecondCondition(Concrete concrete, Steel steel, double d) {
		sigmaS1 = concrete.getEpsilonC3() * (d - x) / (x - x0) * steel.getES() * 1000;
		System.out.println("sigmaS1 " + sigmaS1);

	}

	private void setSigmaS2SecondCondition(Concrete concrete, Steel steel, double a2) {
		sigmaS2 = concrete.getEpsilonC3() * (x - a2) / (x - x0) * steel.getES() * 1000;
		System.out.println("sigmaS2 " + sigmaS2);

	}

	private void setSigmaS2EqualToFYd(Steel steel) {
		sigmaS2 = steel.getFYd();
		System.out.println("sigmaS2 = fYd" + sigmaS2);
	}

	private void setSigmaS1EqualToFYd(Steel steel) {
		sigmaS1 = steel.getFYd();
		System.out.println("sigmaS1 = fYd " + sigmaS1);
	}

	private void setXEqualToHDividedByLambda(double h) {
		x = h / LAMBDA;
		System.out.println("x=h/lambda" + x);
	}

	private void setXEqualToF1DividedByF2() {
		x = f1 / f2;
		System.out.println("x " + x);
	}

	private void pushResultsToPDF(Concrete concrete, Steel steel, double mEd, double nEd) {
		ResultsToPDF.addResults("**Przekrój œciskany zbrojenie symetryczne **\n\n ", "");
		ResultsToPDF.addResults("Med", String.valueOf(mEd));
		ResultsToPDF.addResults("Ned", String.valueOf(nEd));
		ResultsToPDF.addResults("Fcd", String.valueOf(concrete.getFCd()));
		ResultsToPDF.addResults("Fyd", String.valueOf(steel.getFYd()));
		ResultsToPDF.addResults("Es", String.valueOf(steel.getES()));
		ResultsToPDF.addResults("e", String.valueOf(e));
		ResultsToPDF.addResults("eS1", String.valueOf(eS1));
		ResultsToPDF.addResults("eS2", String.valueOf(eS2));
		ResultsToPDF.addResults("xMinYd", String.valueOf(xMinYd));
		ResultsToPDF.addResults("xMin-Yd", String.valueOf(xMinMinusYd));
		ResultsToPDF.addResults("x0", String.valueOf(x0));
		ResultsToPDF.addResults("xMaxYd", String.valueOf(xMaxYd));
		ResultsToPDF.addResults("x", String.valueOf(x));
		ResultsToPDF.addResults("As", String.valueOf(aS));
		ResultsToPDF.addResults("As", String.valueOf(aS));
	}

	protected void symmetricalAnalysisWhileXIsLessOrEqualXLim(Concrete concrete, Steel steel, double nEd, double a1, double a2, double d, double b, double h) {

		setSigmaS2EqualToFYd(steel);
		setCapitalAFirstCondition(concrete, steel);
		setCapitalBFirstCondition(concrete, steel, a2, d);
		setCapitalCFirstCondition(concrete, steel, d, b, nEd);
		setCapitalDFirstCondition(concrete, steel, nEd, b, d);
		solvePolynominalABCD();
		if (x <= h) {
			System.out.println(" x<=h");
			setSigmaS1FirstCondition(concrete, steel, d);
			setAS(concrete, nEd, b, a2, d);
		} else {
			System.out.println(" x>h");
			setCapitalASecondCondition(concrete, steel);
			setCapitalBSecondCondition(concrete, steel, a2, d);
			setCapitalCSecondCondition(concrete, steel, d, b, nEd, a2);
			setCapitalDSecondCondition(concrete, steel, nEd, b, d);
			solvePolynominalABCD();
			if (x > (h / LAMBDA)) {
				System.out.println(" x>h/lambda");
				setF1FirstCondition(concrete, nEd, b, h, a1);
				setF2FirstCondition(concrete, nEd, b, h, a2);
				setXSecondCondition(concrete, steel, d);
				if ((x <= (h / LAMBDA)) || (x >= xMaxYd)) {
					System.out.println(" x nie nalezy do ( h/lambda ; xMaxYd");
					setF1SecondCondition(concrete, nEd, d, a2, b, h, a1);
					setF2SecondCondition(concrete, nEd, b, h, a1, a2);
					setXEqualToF1DividedByF2();
				} else {
					System.out.println(" x  nalezy do ( h/lambda ; xMaxYd");
				}

			} else {
				System.out.println("x<=h/lambda");
			}

			setSigmaS1SecondCondition(concrete, steel, d);
			setSigmaS2SecondCondition(concrete, steel, a2);
			if (x > (h / LAMBDA)) {
				System.out.println(" x>h/lambda");
				setXEqualToHDividedByLambda(h);
			}
			setAS(concrete, nEd, b, a2, d);

		}

	}

	public void fullSymmetricalCompressingBeamReinforcement(Concrete concrete, Steel steel, double mEd, double nEd, double a1, double a2, double d, double b, double h) {
		System.out.println(" \n \n  ********  ZBROJENIE SYMETRYCZNE ***** \n \n ");
		mEd = ifMedIsLessThenZeroReturnAbsAndSetBoolean(mEd);
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		mEd = ifMedIsEqualZeroSetMedToVeryLowPercentOfNed(mEd, nEd);
		setEValuesForCompressing(h, a1, a2, mEd, nEd);
		setInitialXValues(concrete, steel, d, a2);
		setX0(concrete, steel, h);
		setXMaxYd(concrete, steel, a2);
		setASMin(steel, nEd, b, h);
		setXFirstCondition(concrete, nEd, b);
		if (x <= xLim) {
			System.out.println(" x<=xLim");
			setSigmaS1EqualToFYd(steel);
			if (x < xMinYd) {
				System.out.println(" x<xMinYd ");
				symmetricalAnalysisWhileXIsGreaterOrEqualXLim(concrete, steel, nEd, a2, d, b);
			} else {
				setSigmaS2EqualToFYd(steel);
			}
			setAS(concrete, nEd, b, a2, d);
		} else {
			System.out.println("x>xLim");
			symmetricalAnalysisWhileXIsLessOrEqualXLim(concrete, steel, nEd, a1, a2, d, b, h);
		}
		changeAsToAsMinIfAsIsLessThenAsMin();
		changeAs1ToAsMinIfAs1IsLessThenAsMin();
		changeAs2ToAsMinIfAs2IsLessThenAsMin();
		ifMedIsLessThenZeroSwapAs1WithAs2();
		pushResultsToPDF(concrete, steel, mEd, nEd);
	}

}
