package reinforcement.axisload;

import materials.Concrete;
import materials.Steel;
import util.PolynominalSolver;
import util.ResultsToPDF;

public class UnsymmetricalCompressingBeamReinforcement extends UnsymmetricalTensilingBeamReinforcement {

	protected double mS1;
	protected double f1;
	protected double f2;

	private void setSigmaS2InitialValue(Concrete concrete, Steel steel, double a2) {
		sigmaS2 = concrete.getEpsilonCU3() * (x - a2) / x * steel.getES() * 1000;

		System.out.println("sigmaS2 " + sigmaS2);
		if (sigmaS2 > steel.getFYd()) {
			sigmaS2 = steel.getFYd();
			System.out.println("sigmaS2 > fyd wiec sigmaS2= fYd " + sigmaS2);
		}
	}

	private void setAS2InitialValue(Concrete concrete, double nEd, double b, double d, double a2) {
		aS2 = (nEd * eS1 - concrete.getFCd() * 1000 * b * LAMBDA * x * (d - 0.5 * LAMBDA * x)) / (sigmaS2 * 1000 * (d - a2));
		System.out.println("aS2 " + aS2);

	}

	private void setAS1FirstCondition(Concrete concrete, Steel steel, double b, double nEd) {
		aS1 = (sigmaS2 * 1000 * aS2 + concrete.getFCd() * 1000 * b * LAMBDA * x - nEd) / (steel.getFYd() * 1000);
		System.out.println("aS1 " + aS1);

	}

	private void setMS1(Concrete concrete, Steel steel, double d, double a2) {
		mS1 = concrete.getEpsilonCU3() * steel.getES() * 1000000 * aSMin * (d - a2);
		System.out.println("mS1 " + mS1);
	}

	private void setCapitalAFirstCondition(double a2) {
		capitalA = (-2 * a2) / LAMBDA;
		System.out.println("capitalA " + capitalA);

	}

	private void setCapitalBFirstCondition(Concrete concrete, double nEd, double b) {
		capitalB = 2 * (nEd * eS2 + mS1) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * b);
		System.out.println("capitalB " + capitalB);

	}

	private void setCapitalCFirstCondition(Concrete concrete, double d, double b) {
		capitalC = (-2 * d * mS1) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * b);
		System.out.println("capitalC " + capitalC);

	}

	private void setMS2WhenXIsGreaterThenH(Concrete concrete, Steel steel, double d, double a2) {
		mS2 = concrete.getEpsilonC3() * steel.getES() * 1000000 * aSMin * (d - a2);
		System.out.println("mS2 " + mS2);

	}

	private void setCapitalASecondCondition(double a2) {
		capitalA = -(x0 + 2 * a2 / LAMBDA);
		System.out.println("capitalA " + capitalA);

	}

	private void setCapitalBSecondCondition(Concrete concrete, double nEd, double b, double a2) {
		capitalB = 2 * ((nEd * eS2 + mS2) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * b) + a2 / LAMBDA * x0);
		System.out.println("capitalB " + capitalB);

	}

	private void setCapitalCSecondCondition(Concrete concrete, double nEd, double d, double b) {
		capitalC = (-2 * (nEd * eS2 * x0 + d * mS2)) / (LAMBDA * LAMBDA * concrete.getFCd() * 1000 * b);
		System.out.println("capitalC " + capitalC);

	}

	private void setF1(Concrete concrete, double nEd, double b, double h, double a2, double d) {
		f1 = (-nEd * eS2 - concrete.getFCd() * 1000 * b * h * (0.5 * h - a2)) * (d - x0);
		System.out.println("f1 " + f1);

	}

	private void setF2(Concrete concrete, double nEd, double b, double h, double a1, double a2) {
		f2 = (nEd * eS1 - concrete.getFCd() * 1000 * b * h * (0.5 * h - a1)) * (x0 - a2);
		System.out.println("f2 " + f2);

	}

	private void setXWhenF2MinusF1IsGreaterThen0(double a2, double d) {
		x = (-f1 * a2 + f2 * d + Math.sqrt(f1 * f2) * (d - a2)) / (f2 - f1);
		System.out.println("x " + x);

	}

	private void setAS2WhenXIsLessOrEqualToH(Concrete concrete, Steel steel, double nEd, double b, double d, double a2) {
		aS2 = (nEd * eS1 - concrete.getFCd() * 1000 * b * LAMBDA * x * (d - 0.5 * LAMBDA * x)) / (steel.getFYd() * 1000 * (d - a2));
		System.out.println("aS2 " + aS2);

	}

	private void setSigmaS1(Concrete concrete, Steel steel, double d) {
		sigmaS1 = concrete.getEpsilonC3() * (d - x) / (x - x0) * steel.getES() * 1000;
		if (sigmaS1 > steel.getFYd()) {
			sigmaS1 = steel.getFYd();
		}
		System.out.println("sigmaS1 " + sigmaS1);

	}

	private void setSigmaS2SecondCondition(Concrete concrete, Steel steel, double a2) {
		sigmaS2 = concrete.getEpsilonC3() * (x - a2) / (x - x0) * steel.getES() * 1000;
		if (sigmaS2 > steel.getFYd()) {
			sigmaS2 = steel.getFYd();
		}
		System.out.println("sigmaS2 " + sigmaS2);

	}

	private void setAS1SecondCondition(Concrete concrete, double nEd, double b, double h, double a2, double d) {
		aS1 = (nEd * eS2 + concrete.getFCd() * 1000 * b * h * (0.5 * h - a2)) / (sigmaS1 * 1000 * (d - a2));
		System.out.println("aS1 " + aS1);

	}

	private void setAS2SecondCondition(Concrete concrete, double nEd, double b, double h, double a1, double a2, double d) {
		aS2 = (nEd * eS1 - concrete.getFCd() * 1000 * b * h * (0.5 * h - a1)) / (sigmaS2 * 1000 * (d - a2));
		System.out.println("aS2 " + aS2);
	}

	private void setAS1ThirdCondition(Concrete concrete, Steel steel, double nEd, double b) {
		aS1 = (sigmaS2 * 1000 * aS2 + concrete.getFCd() * 1000 * b * LAMBDA * x - nEd) / (steel.getFYd() * 1000);
		System.out.println("aS1 " + aS1);
	}

	private void setAs1EqualToAsMin() {
		aS1 = aSMin;
		System.out.println("aS1=aSMin " + aS1);
	}

	private void setXEqualToXlim() {
		x = xLim;
		System.out.println("x=xLim " + x);
	}

	private void pushResultsToPDF(Concrete concrete, Steel steel, double mEd, double nEd) {
		ResultsToPDF.addResults("**Przekrój œciskany zbrojenie niesymetryczne **\n\n ", "");
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
		ResultsToPDF.addResults("x0", String.valueOf(x0));
		ResultsToPDF.addResults("xMaxYd", String.valueOf(xMaxYd));
		ResultsToPDF.addResults("x", String.valueOf(x));
		ResultsToPDF.addResults("As1", String.valueOf(aS1));
		ResultsToPDF.addResults("As2", String.valueOf(aS2));
	}

	private void unsymmetricalAnalysisWhileASIsGreaterThenASMin(Concrete concrete, Steel steel, double nEd, double b, double h, double d, double a1, double a2) {
		setAS1FirstCondition(concrete, steel, b, nEd);

		if (aS1 < 0) {

			setASMin(steel, nEd, b, h);
			setAs1EqualToAsMin();
			setMS1(concrete, steel, d, a2);
			setCapitalAFirstCondition(a2);
			setCapitalBFirstCondition(concrete, nEd, b);
			setCapitalCFirstCondition(concrete, d, b);
			x = PolynominalSolver.getMaximumRootOfPolynominalABC(capitalA, capitalB, capitalC);
			if (x > h) {
				System.out.println("x>h ");
				setMS2WhenXIsGreaterThenH(concrete, steel, d, a2);
				setCapitalASecondCondition(a2);
				setCapitalBSecondCondition(concrete, nEd, b, a2);
				setCapitalCSecondCondition(concrete, nEd, d, b);
				x = PolynominalSolver.getMaximumRootOfPolynominalABC(capitalA, capitalB, capitalC);
				if (x > (h / LAMBDA)) {
					System.out.println("x>h/lambda ");
					setF1(concrete, nEd, b, h, a2, d);
					setF2(concrete, nEd, b, h, a1, a2);
					if ((f2 - f1) <= 0) {
						System.out.println("f2-f1<=0");
						x = Math.pow(10, 10);
					} else {
						System.out.println("f2-f1>0");
						setXWhenF2MinusF1IsGreaterThen0(a2, d);
					}
					setSigmaS1(concrete, steel, d);
					setSigmaS2SecondCondition(concrete, steel, a2);
					setAS1SecondCondition(concrete, nEd, b, h, a2, d);
					setAS2SecondCondition(concrete, nEd, b, h, a1, a2, d);
				} else {

					setAS2WhenXIsLessOrEqualToH(concrete, steel, nEd, b, d, a2);
				}
			} else {
				System.out.println("x<=h ");
				setAS2WhenXIsLessOrEqualToH(concrete, steel, nEd, b, d, a2);

			}
		}
	}

	public void fullUnsymmetricalCompressingBeamReinforcement(Concrete concrete, Steel steel, double mEd, double nEd, double b, double h, double d, double a1, double a2) {
		System.out.println("\n \n ****** zbrojenie niesymetryczne****** \n \n");
		mEd = ifMedIsLessThenZeroReturnAbsAndSetBoolean(mEd);
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		mEd = ifMedIsEqualZeroSetMedToVeryLowPercentOfNed(mEd, nEd);
		setEValuesForCompressing(h, a1, a2, mEd, nEd);
		setInitialXValues(concrete, steel, d, a2);
		setX0(concrete, steel, h);
		setXMaxYd(concrete, steel, a2);
		setXEqualToXlim();
		setSigmaS2InitialValue(concrete, steel, a2);
		setAS2InitialValue(concrete, nEd, b, d, a2);
		setASMin(steel, nEd, b, h);
		if (aS2 <= aSMin) {
			System.out.println("aS2<=AsMin");
			unsymmetricalAnalysisWhileAS2IsLessOrEqualToASMin(concrete, steel, nEd, d, a2, b);
			setAS1ThirdCondition(concrete, steel, nEd, b);
			changeAs1ToAsMinIfAs1IsLessThenAsMin();
		} else {
			System.out.println("aS2>AsMin");
			unsymmetricalAnalysisWhileASIsGreaterThenASMin(concrete, steel, nEd, b, h, d, a1, a2);

		}
		// changeAs1ToAsMinIfAs1IsLessThenAsMin();
		// changeAs2ToAsMinIfAs2IsLessThenAsMin();
		ifMedIsLessThenZeroSwapAs1WithAs2();
		System.out.println("x koncowe " + x);
		System.out.println("aS1 koncowe" + aS1);
		System.out.println("aS2 koncowe" + aS2);
		pushResultsToPDF(concrete, steel, mEd, nEd);
	}

}
