package diagnosis;

import materials.Concrete;
import materials.Steel;

abstract class BeamDiagnosis {

	protected double xLim;
	protected double xMinYd;
	protected double xMinMinusYd;
	protected double xMaxYd;
	protected double x0;
	protected double x;
	protected final double LAMBDA = 0.8;
	protected double sigmaS1;
	protected double sigmaS2;
	protected double capitalA; // to jest A
	protected double capitalB; // to jest B
	protected double capitalC; // to jest C
	protected double mRd;
	protected double nRd;
	protected boolean isMedLessThen0;
	protected double e;

	protected double checkIfNedIsCloseToZeroAndReplaceItWithHalfPercentOfMed(double nEd, double mEd) {
		double arg = 0.05 / 100 * mEd;
		if (nEd > 0 && nEd <= arg) {
			nEd = arg;
		}
		return nEd;
	}

	private void setXLim(Concrete concrete, Steel steel, double d) {

		xLim = concrete.getEpsilonCU3() / (concrete.getEpsilonCU3() + steel.getFYd() / (steel.getES() * 1000)) * d;
		System.out.println("xLim " + xLim);
	}

	private void setXMinYd(Concrete concrete, Steel steel, double a2) {
		xMinYd = concrete.getEpsilonCU3() / (concrete.getEpsilonCU3() - steel.getEpsilonYd()) * a2;
		System.out.println("xMinYd " + xMinYd);

	}

	private void setXMinMinusYd(Concrete concrete, Steel steel, double a2) {
		xMinMinusYd = concrete.getEpsilonCU3() / (concrete.getEpsilonCU3() + steel.getEpsilonYd()) * a2;
		System.out.println("xMinMinusYd " + xMinMinusYd);
	}

	protected void setXLimits(Concrete concrete, Steel steel, double d, double a2) {
		setXLim(concrete, steel, d);
		setXMinYd(concrete, steel, a2);
		setXMinMinusYd(concrete, steel, a2);
	}

	protected void setXMaxYd(Concrete concrete, Steel steel, double a2) {
		xMaxYd = (steel.getEpsilonYd() * x0 - concrete.getEpsilonC3() * a2) / (steel.getEpsilonYd() - concrete.getEpsilonC3());
		System.out.println("xMaxYd " + xMaxYd);

	}

	protected void setX0(Concrete concrete, Steel steel, double h) {
		x0 = (1 - concrete.getEpsilonC3() / concrete.getEpsilonCU3()) * h;
		System.out.println("x0 " + x0);
	}

	public double getX() {
		return x;
	}

	public double getmRd() {
		return mRd;
	}

	public double getnRd() {
		return nRd;
	}

	protected double ifMedIsLessThenZeroReturnAbsAndSetBoolean(double mEd) {
		isMedLessThen0 = false;
		if (mEd < 0) {
			isMedLessThen0 = true;
			mEd = Math.abs(mEd);
		}
		return mEd;
	}

}
