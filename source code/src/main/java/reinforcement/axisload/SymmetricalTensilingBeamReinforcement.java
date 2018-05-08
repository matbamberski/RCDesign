package reinforcement.axisload;

import materials.Concrete;
import materials.Steel;
import util.PolynominalSolver;
import util.ResultsToPDF;

public class SymmetricalTensilingBeamReinforcement extends BeamMimosrodowoObciazona {
	
	
	
	protected void setCapitalA(Concrete concrete, Steel steel) {
		capitalA = LAMBDA * (steel.getFYd() * 1000 - concrete.getEpsilonCU3() * steel.getES() * 1000000);
		System.out.println("capitalA " + capitalA);

	}

	protected void setCapitalB(Concrete concrete, Steel steel, double a2, double d) {
		capitalB = -2 * (steel.getFYd() * 1000 * d - concrete.getEpsilonCU3() * steel.getES() * 1000000 * a2 * (1 + 0.5 * LAMBDA));
		System.out.println("capitalB " + capitalB);

	}

	protected void setCapitalC(Concrete concrete, Steel steel, double nEd, double b, double a2) {
		double licznik = nEd * (steel.getFYd() * 1000 * eS1 - concrete.getEpsilonCU3() * steel.getES() * 1000000 * eS2);
		double mianownik = (concrete.getFCd() * 1000 * LAMBDA * b);
		double part1 = concrete.getEpsilonCU3() * steel.getES() * 1000000 * a2 * a2;
		capitalC = 2 * (licznik / mianownik - part1);
		System.out.println("capitalC " + capitalC);

	}

	protected void setCapitalD(Concrete concrete, Steel steel, double nEd, double a2, double b) {
		double licznik = (2 * nEd * concrete.getEpsilonCU3() * steel.getES() * 1000000 * a2 * eS2);
		double mianownik = (LAMBDA * concrete.getFCd() * 1000 * b);
		capitalD = licznik / mianownik;
		System.out.println("capitalD " + capitalD);

	}

	protected void setSigmaS2(Concrete concrete, Steel steel, double a2) {
		sigmaS2 = concrete.getEpsilonCU3() * (x - a2) / x * steel.getES() * 1000;
		System.out.println("sigmaS2 " + sigmaS2);

	}

	protected void setX(Concrete concrete, double nEd, double d, double a2, double b) {
		x = (1 / (2 * LAMBDA)) * ((d + a2) - Math.sqrt((d + a2) * (d + a2) - 4 * nEd * (eS1 + eS2) / (concrete.getFCd() * 1000 * b)));
		System.out.println("x  " + x);
	}

	protected void setAS(Concrete concrete, double nEd, double b, double a2, double d) {
		double aSv1 = (nEd * eS2 + concrete.getFCd() * 1000 * b * LAMBDA * x * (0.5 * LAMBDA * x - a2)) / (sigmaS1 * 1000 * (d - a2));
		System.out.println("aS v1 " + aSv1);
		double aSv2 = (nEd * eS1 - concrete.getFCd() * 1000 * b * LAMBDA * x * (d - 0.5 * LAMBDA * x)) / (sigmaS2 * 1000 * (d - a2));
		System.out.println("aS v2 " + aSv2);
		aS = Math.max(aSv1, aSv2);
		System.out.println("aS " + aS);
	}

	private void setSigmaS2EqualToMinusFYd(Steel steel) {
		sigmaS2 = -steel.getFYd();
		System.out.println("sigmaS2 = -steelFyd " + sigmaS2);
	}

	private void setSigmaS1AndSigmaS2EqualToFYd(Steel steel) {
		sigmaS1 = steel.getFYd();
		System.out.println("sigmaS1 = fYd " + sigmaS1);
		sigmaS2 = steel.getFYd();
		System.out.println("sigmaS2 = fYd " + sigmaS2);
	}

	protected void solvePolynominalABCD() {
		x = PolynominalSolver.getMaximumRootOfPolynominalABCD(capitalA, capitalB, capitalC, capitalD);
		System.out.println("x " + x);
	}

	private void pushResultsToPDF(Concrete concrete, Steel steel, double mEd, double nEd) {
		ResultsToPDF.addResults("**Przekrój rozci¹gany zbrojenie symetryczne **\n\n ", "");
		ResultsToPDF.addResults("Med", String.valueOf(mEd));
		ResultsToPDF.addResults("Ned", String.valueOf(nEd));
		ResultsToPDF.addResults("Fcd", String.valueOf(concrete.getFCd()));
		ResultsToPDF.addResults("Fyd", String.valueOf(steel.getFYd()));
		ResultsToPDF.addResults("Es", String.valueOf(steel.getES()));
		ResultsToPDF.addResults("e", String.valueOf(e));
		ResultsToPDF.addResults("eS1", String.valueOf(eS1));
		ResultsToPDF.addResults("eS2", String.valueOf(eS2));
		ResultsToPDF.addResults("xMin-Yd", String.valueOf(xMinMinusYd));
		ResultsToPDF.addResults("x", String.valueOf(x));
		ResultsToPDF.addResults("As", String.valueOf(aS));
	}

	protected void symmetricalAnalysisWhileXIsGreaterOrEqualXLim(Concrete concrete, Steel steel, double nEd, double a2, double d, double b) {
		setCapitalA(concrete, steel);
		setCapitalB(concrete, steel, a2, d);
		setCapitalC(concrete, steel, nEd, b, a2);
		setCapitalD(concrete, steel, nEd, a2, b);

		solvePolynominalABCD();

		if (x <= xMinMinusYd) {
			System.out.println("x<=xMinMinusYd");
			setSigmaS2EqualToMinusFYd(steel);
			setX(concrete, nEd, d, a2, b);
			if (x>0) x = 0;
		} else {
			System.out.println("x>xMinMinusYd");
			setSigmaS2(concrete, steel, a2);
		}

	}

	public void fullSymetricalTensilingBeamReinforcement(Concrete concrete, Steel steel, double mEd, double nEd, double a1, double a2, double d, double b, double h) {
		System.out.println("\n \n Symetryczne rozciaganie \n \n ");
		mEd = ifMedIsLessThenZeroReturnAbsAndSetBoolean(mEd);
		nEd = checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(nEd, mEd);
		mEd = ifMedIsEqualZeroSetMedToVeryLowPercentOfNed(mEd, nEd);
		setEValuesForTensiling(h, a1, a2, mEd, nEd);
		setXMinMinusYd(concrete, steel, a2);
		setASMin(steel, nEd, b, h);
		setSigmaS1AndSigmaS2EqualToFYd(steel);
		symmetricalAnalysisWhileXIsGreaterOrEqualXLim(concrete, steel, nEd, a2, d, b);
		//if (x > 0) {
		setAS(concrete, nEd, b, a2, d);
		//} else {
		//	x = 0;
			//setAS(concrete, nEd, b, a2, d);
		//}
		// changeAsToAsMinIfAsIsLessThenAsMin();
		// changeAs1ToAsMinIfAs1IsLessThenAsMin();
		// changeAs2ToAsMinIfAs2IsLessThenAsMin();
		ifMedIsLessThenZeroSwapAs1WithAs2();
		pushResultsToPDF(concrete, steel, mEd, nEd);
	}

}
