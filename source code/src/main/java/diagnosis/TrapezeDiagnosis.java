package diagnosis;

import materials.Concrete;
import materials.Steel;

public class TrapezeDiagnosis extends RectangularDiagnosis {

	protected double aC1;

	private void setAC1(double bEff, double b, double hF) {
		aC1 = (bEff - b) * hF;
	}

	private void setXFirstConditionTrapeze(Steel steel, Concrete concrete, double aS1, double aS2, double b) {
		x = (1 / LAMBDA) * ((steel.getFYd() * 1000 * (aS1 - aS2) / concrete.getFCd() * 1000 * b) - aC1 / b);
	}

	private void setXThridConditionTrapeze(Steel steel, Concrete concrete, double aS1, double aS2, double b) {
		x = (1 / LAMBDA) * ((steel.getFYd() * 1000 * (aS1 + aS2) / concrete.getFCd() * 1000 * b) - aC1 / b);
	}

	private void setCapitalATrapeze(Concrete concrete, Steel steel, double aS1, double aS2, double b) {
		capitalA = (aC1 / b) + (concrete.getEpsilonCU3() * steel.getES() * 1000 * aS2 - steel.getFYd() * 1000 * aS1) / (concrete.getFCd() * 1000 * b);
	}

	private void setCapitalBTrapeze(Concrete concrete, Steel steel, double aS2, double a2, double b) {
		capitalB = 4 * LAMBDA * concrete.getEpsilonCU3() * steel.getES() * 1000000 * aS2 / (concrete.getFCd() * 1000 * b) * a2;
	}

	private void setSigmaS1Trapeze(Concrete concrete, Steel steel, double aS2, double aS1, double b) {
		sigmaS1 = (concrete.getFCd() * 1000 * (aC1 + b * LAMBDA * x) + steel.getFYd() * 1000 * aS2) / aS1;
	}

	private void setMRdTrapeze(Concrete concrete, double aS2, double a2, double b, double d, double hF) {
		mRd = concrete.getFCd() * 1000 * (aC1 * (d - 0.5 * hF) + b * LAMBDA * x * (d - 0.5 * LAMBDA * x)) + sigmaS2 * 1000 * aS2 * (d - a2);
		System.out.println("mRd " + mRd);
	}

	protected void setSigmaS2EqualToMinusFYd(Steel steel) {
		sigmaS2 = -steel.getFYd();
	}

	private void trapezeBeamReinforcementDiagnosis(Concrete concrete, Steel steel, double bW, double bEff, double a1, double a2, double aS2, double aS1, double hF, double d) {

		setXFirstCondition(concrete, steel, aS1, aS2, bEff);
		if (x <= (hF / LAMBDA) || hF == 0) {
			rectangularBeamReinforcementDiagnosis(concrete, steel, bEff, a1, a2, aS2, aS1, d);
		} else {
			setAC1(bEff, bW, hF);
			setXFirstConditionTrapeze(steel, concrete, aS1, aS2, bW);
			if (x < xMinYd) {
				setSigmaS1EqualToFYd(steel);
				setCapitalATrapeze(concrete, steel, aS1, aS2, bW);
				setCapitalBTrapeze(concrete, steel, aS2, a2, bW);
				setXSecondCondition(concrete, steel, aS1, aS2);
				if (x < xMinMinusYd) {
					setXThridConditionTrapeze(steel, concrete, aS1, aS2, bW);
					setSigmaS2EqualToMinusFYd(steel);
				} else {
					setSigmaS2(concrete, a2, steel);
				}
			} else {
				setSigmaS2EqualToFYd(steel);
				if (x <= xLim) {
					setSigmaS1EqualToFYd(steel);
				} else {
					setSigmaS1Trapeze(concrete, steel, aS2, aS1, bW);
				}
			}
			setMRdTrapeze(concrete, aS2, a2, bW, d, hF);
		}
	}

	public void fullTrapezeBeamReinforcementDiagnosis(Concrete concrete, Steel steel, double bW, double bEff, double a1, double a2, double aS1, double aS2, double hF, double d) {
		setXLimits(concrete, steel, d, a2);
		trapezeBeamReinforcementDiagnosis(concrete, steel, bW, bEff, a1, a2, aS2, aS1, hF, d);
	}

}
