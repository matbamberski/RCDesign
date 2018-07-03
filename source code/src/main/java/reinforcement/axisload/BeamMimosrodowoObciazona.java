package reinforcement.axisload;

import materials.Concrete;
import materials.Steel;

abstract class BeamMimosrodowoObciazona {

	protected double e;
	protected double eS1;
	protected double eS2;

	protected double x;
	protected double xLim;
	protected double xMinYd;
	protected double xMinMinusYd;
	protected double x0;
	protected double xMaxYd;

	protected double sigmaS1;
	protected double sigmaS2;

	protected static final double LAMBDA = 0.8;

	protected double capitalA;
	protected double capitalB;
	protected double capitalC;
	protected double capitalD;

	protected double aS;
	protected double aS1;
	protected double aS2;

	protected double aSMin;

	protected boolean isMedLessThen0;

	protected void setASMin(Steel steel, double nEd, double b, double h) {
		double value1 = (0.002 * b * h)/2;
		double value2 = (0.1 * nEd / (steel.getFYd() * 1000))/2;
		aSMin = Math.max(value1, value2);
		System.out.println("aSMin " + aSMin);
	}

	protected void changeAsToAsMinIfAsIsLessThenAsMin() {
		if (aS < aSMin) {
			aS = aSMin;
			System.out.println("aS < aSMin   -> aS=aSMin");
		}
	}

	protected void changeAs1ToAsMinIfAs1IsLessThenAsMin() {
		if (aS1 < aSMin) {
			aS1 = aSMin;
			System.out.println("aS1 < aSMin   -> aS1=aSMin");
		}
	}

	protected void changeAs2ToAsMinIfAs2IsLessThenAsMin() {
		if (aS2 < aSMin) {
			aS2 = aSMin;
			System.out.println("aS2 < aSMin   -> aS2=aSMin");
		}
	}

	protected void setEValuesForTensiling(double h, double a1, double a2, double mEd, double nEd) {

		e = mEd / nEd;
		System.out.println("e " + e);

		eS1 = e - 0.5 * h + a1;
		System.out.println("eS1 " + eS1);
		eS2 = e + 0.5 * h - a2;
		System.out.println("eS2 " + eS2);

	}

	protected void setEValuesForCompressing(double h, double a1, double a2, double mEd, double nEd) {
		System.out.println("mimosrody dla sciskania");
		if (mEd == 0) {
			e = h / 30;
			System.out.println("e= e0 ( h/30 )" + e);
			if (e < 0.02) {
				e = 0.02;
				System.out.println("e= e0 ( 20mm  )" + e);
			}
		} else {
			e = mEd / nEd;
			System.out.println("e " + e);
		}
		eS1 = e + 0.5 * h - a1;
		System.out.println("eS1 " + eS1);
		eS2 = e - 0.5 * h + a2;
		System.out.println("eS2 " + eS2);

	}

	protected void setXLim(Concrete concrete, Steel steel, double d) {
		xLim = concrete.getEpsilonCU3() / (concrete.getEpsilonCU3() + steel.getFYd() / (steel.getES() * 1000)) * d;
		System.out.println("xLim " + xLim);
	}

	protected void setXMinYd(Concrete concrete, Steel steel, double a2) {
		xMinYd = concrete.getEpsilonCU3() / (concrete.getEpsilonCU3() - steel.getEpsilonYd()) * a2;
		System.out.println("xMinYd " + xMinYd);

	}

	protected void setXMinMinusYd(Concrete concrete, Steel steel, double a2) {
		xMinMinusYd = concrete.getEpsilonCU3() / (concrete.getEpsilonCU3() + steel.getEpsilonYd()) * a2;
		System.out.println("xMinMinusYd " + xMinMinusYd);
	}

	protected void setX0(Concrete concrete, Steel steel, double h) {
		x0 = (1 - concrete.getEpsilonC3() / concrete.getEpsilonCU3()) * h;
		System.out.println("x0 " + x0);
	}

	protected void setXMaxYd(Concrete concrete, Steel steel, double a2) {
		xMaxYd = (steel.getEpsilonYd() * x0 - concrete.getEpsilonC3() * a2) / (steel.getEpsilonYd() - concrete.getEpsilonC3());
		System.out.println("xMaxYd " + xMaxYd);

	}

	protected void setInitialXValues(Concrete concrete, Steel steel, double d, double a2) {
		setXLim(concrete, steel, d);
		setXMinYd(concrete, steel, a2);
		setXMinMinusYd(concrete, steel, a2);
	}

	public double getaS() {
		return aS;
	}

	public double getaS1() {
		return aS1;
	}

	public double getaS2() {
		return aS2;
	}

	protected double checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(double nEd, double mEd) {
		double arg = 0.05 / 100 * mEd;
		if (nEd > 0 && nEd <= arg) {
			nEd = arg;
		}
		return nEd;
	}

	protected double ifMedIsLessThenZeroReturnAbsAndSetBoolean(double mEd) {
		isMedLessThen0 = false;
		if (mEd < 0) {
			isMedLessThen0 = true;
			mEd = Math.abs(mEd);
		}
		return mEd;
	}

	protected void ifMedIsLessThenZeroSwapAs1WithAs2() {
		double aSUpper;
		double aSLower;
		if (isMedLessThen0) {
			aSUpper = aS1;
			aSLower = aS2;
			aS1 = aSLower;
			aS2 = aSUpper;
		}
	}

	protected double ifMedIsEqualZeroSetMedToVeryLowPercentOfNed(double mEd, double nEd) {
		if (mEd == 0) {
			mEd = nEd / 1000000;
		}
		return mEd;
	}

}
