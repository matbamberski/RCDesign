package diagnosis;

import materials.Concrete;
import materials.Steel;

public class RectangularDiagnosis extends BeamDiagnosis {

	protected void setCapitalA(Concrete concrete, Steel steel, double aS2, double aS1, double b) {
		capitalA = (concrete.getEpsilonCU3() * steel.getES() * 1000000 * aS2 - steel.getFYd() * 1000 * aS1) / (concrete.getFCd() * 1000 * b);
		System.out.println("aa " + capitalA);
	}

	protected void setCapitalB(Concrete concrete, Steel steel, double aS2, double a2, double b) {
		capitalB = (4 * LAMBDA * concrete.getEpsilonCU3() * steel.getES() * 1000000 * aS2) / (concrete.getFCd() * 1000 * b) * a2;
		System.out.println("bb " + capitalB);
	}

	protected void setXFirstCondition(Concrete concrete, Steel steel, double aS1, double aS2, double b) {
		x = (1 / LAMBDA) * (steel.getFYd() * 1000 * (aS1 - aS2)) / (concrete.getFCd() * 1000 * b);
		System.out.println(" x" + x);
	}

	protected void setXSecondCondition(Concrete concrete, Steel steel, double aS1, double aS2) {
		x = (1 / LAMBDA) * ((-capitalA + Math.sqrt(capitalA * capitalA + capitalB)) / 2);
		System.out.println("x " + x);
	}

	protected void setXThirdCondition(Concrete concrete, Steel steel, double aS1, double aS2, double b) {
		x = (1 / LAMBDA) * (steel.getFYd() * 1000 * (aS1 + aS2)) / (concrete.getFCd() * 1000 * b);
		System.out.println(" x " + x);
	}

	protected void setSigmaS2(Concrete concrete, double a2, Steel steel) {
		sigmaS2 = concrete.getEpsilonCU3() * (x - a2) * steel.getES() * 1000 / x;
		System.out.println("simgaS2 " + sigmaS2);
	}

	protected void setSigmaS1(Concrete concrete, Steel steel, double aS2, double aS1, double b) {
		sigmaS1 = (concrete.getFCd() * 1000 * b * x + steel.getFYd() * 1000 * aS2) / aS1;
		System.out.println(" sigmaS1" + sigmaS1);
	}

	protected void setMRdRectangular(Concrete concrete, double aS2, double a2, double b, double d) {
		mRd = concrete.getFCd() * 1000 * b * LAMBDA * x * (d - 0.5 * LAMBDA * x) + sigmaS2 * 1000 * aS2 * (d - a2 / 10);
		System.out.println("mRd " + mRd);
	}

	protected void setSigmaS1EqualToFYd(Steel steel) {
		sigmaS1 = steel.getFYd();
	}

	protected void setSigmaS1EqualToMinusFYd(Steel steel) {
		sigmaS1 = -steel.getFYd();
	}

	protected void setSigmaS2EqualToFYd(Steel steel) {
		sigmaS2 = steel.getFYd();
	}

	protected void setXEqualToXLim() {
		x = xLim;
	}

	protected void setXFourthCondition(Concrete concrete, Steel steel, double b, double aS2, double aS1) {
		sigmaS1 = (concrete.getFCd() * 1000 * b * LAMBDA * x + steel.getFYd() * 1000 * aS2) / aS1;
	}

	protected void rectangularBeamReinforcementDiagnosis(Concrete concrete, Steel steel, double b, double a1, double a2, double aS2, double aS1, double d) {
		setXFirstCondition(concrete, steel, aS1, aS2, b);
		if (x < xMinYd) {
			setSigmaS1EqualToFYd(steel);
			setCapitalA(concrete, steel, aS2, aS1, b);
			setCapitalB(concrete, steel, aS2, a2, b);
			setXSecondCondition(concrete, steel, aS1, aS2);
			if (x < xMinMinusYd) {
				setXThirdCondition(concrete, steel, aS1, aS2, b);
				setSigmaS1EqualToMinusFYd(steel);
			} else {
				setSigmaS2(concrete, a2, steel);
			}
		} else {
			setSigmaS2EqualToFYd(steel);
			if (x <= xLim) {
				setSigmaS1EqualToFYd(steel);
			} else {
				setXEqualToXLim();
				setXFourthCondition(concrete, steel, b, aS2, aS1);
			}
		}
		setMRdRectangular(concrete, aS2, a2, b, d);
	}

	public void fullRectangularBeamReinforcementDiagnosis(Concrete concrete, Steel steel, double b, double a1, double a2, double aS1, double aS2, double d) {
		setXLimits(concrete, steel, d, a2);
		rectangularBeamReinforcementDiagnosis(concrete, steel, b, a1, a2, aS2, aS1, d);
	}

}
