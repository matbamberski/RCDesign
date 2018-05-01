package reinforcement.bending;

import materials.Concrete;
import materials.Steel;

abstract class ClearBendingBeam {

	private final double LAMBDA = 0.8;
	double ksiLim;
	double dzetaLim;
	double dzeta;
	double a0Lim;
	double a0;
	// protected double d; // przekazujemy w cm podstawiamy w m
	// protected double b;

	protected double aS1;
	protected double aS2;
	protected double aS11;
	protected double aS12;
	protected double mRdLim;

	protected double aSMin;
	protected boolean isMedLessThen0;

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

	protected void setASMin(Steel steel, Concrete concrete, double b, double d) {
		aSMin = Math.max(0.0013 * b * d, 0.26 * concrete.getFCtm() / steel.getFYk() * b * d);
		System.out.println("aSMin " + aSMin);
	}

	private void setKsiLim(Concrete concrete, Steel steel) {
		ksiLim = LAMBDA * (concrete.getEpsilonCU3() / (concrete.getEpsilonCU3() + steel.getEpsilonYd()));
		System.out.println("ksi lim " + ksiLim);
	}

	private void setDzetaLim() {
		dzetaLim = 1 - 0.5 * ksiLim;
		System.out.println("dzeta lim " + dzetaLim);
	}

	private void setA0Lim() {
		a0Lim = ksiLim * dzetaLim;
		System.out.println("a0lim " + a0Lim);
	}

	protected void setCalculationModel(Concrete concrete, Steel steel) {
		setKsiLim(concrete, steel);
		setDzetaLim();
		setA0Lim();
	}

	protected void setDzeta() {
		dzeta = (1 + Math.sqrt(1 - 2 * a0)) / 2;

		System.out.println("dzeta " + dzeta);
	}

	protected void setA0(double mEd, Concrete concrete, double d, double b) {
		a0 = mEd / (concrete.getFCd() * 1000 * b * d * d);
		System.out.println("a0= " + a0 + "\n");
	}

	protected abstract void setAS1();

	public double getAS1() {
		return aS1;
	}

	protected abstract void setAS2();

	public double getAS2() {
		return aS2;
	}

	protected double getAS11() {
		return aS11;
	}

	protected double getAS12() {
		return aS12;
	}

	protected void setMRdLim(Concrete concrete, double b, double d) {
		mRdLim = a0Lim * concrete.getFCd() * 1000 * b * d * d;
		System.out.println("mRdLim " + mRdLim);
	}

	protected void clearVariables() {
		ksiLim = 0;
		dzetaLim = 0;
		dzeta = 0;
		a0Lim = 0;
		a0 = 0;
		aS1 = 0;
		aS2 = 0;
		aS11 = 0;
		aS12 = 0;
		mRdLim = 0;
		aSMin = 0;
	}
}
