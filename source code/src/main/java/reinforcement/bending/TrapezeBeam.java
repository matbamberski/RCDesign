package reinforcement.bending;

import materials.Concrete;
import materials.Steel;
import util.ResultsToPDF;

public final class TrapezeBeam extends RectangularBeam {

	private double mRdHf;
	private double mRd1;
	private double aS13;
	private double mRdEff;

	private void setMRdHf(Concrete concrete, double bEff, double hF, double d) {
		mRdHf = concrete.getFCd() * 1000 * bEff * hF * (d - 0.5 * hF);

		System.out.println("mRdHf= " + mRdHf);
	}

	private void setMRd1(Concrete concrete, double bEff, double hF, double d, double bW) {
		mRd1 = concrete.getFCd() * 1000 * (bEff - bW) * hF * (d - 0.5 * hF);
		System.out.println("mRd1= " + mRd1);
	}

	protected void setAS11(Concrete concrete, Steel steel, double hF, double bW, double bEff) {
		aS11 = (bEff - bW) * hF * concrete.getFCd() * 1000 / (steel.getFYd() * 1000);
		System.out.println("aS11= " + aS11);
	}

	private void setAS13(double mEd, double mRd1, double mRdLim, Steel steel, double a2, double d) {
		aS13 = (mEd - mRd1 - mRdLim) / (steel.getFYd() * 1000 * (d - a2 / 10));
		System.out.println("aS13= " + aS13);
	}

	private void setAS1(double aS11, double aS12, double aS13) {
		this.aS11 = aS11 + aS12 + aS13;
		System.out.println("aS11=aS11+aS12+aS13 " + aS11);
	}

	@Override
	protected void clearVariables() {
		super.clearVariables();
		mRdHf = 0;
		mRd1 = 0;
		aS13 = 0;
	}

	private void calculateMrdEff(double mEd) {
		mRdEff = mEd - mRd1;
	}

	private void setAS12WhenA0IsLessOrEqualA0Lim(double mEd, Steel steel, double a2, double d) {
		aS12 = (mEd) / (steel.getFYd() * 1000 * dzeta * d);

		System.out.println("aS12 " + aS12);
	}

	private void setAs12(Concrete concrete, Steel steel, double d, double b) {
		aS12 = dzetaLim * b * d * concrete.getFCd() / steel.getFYd();
	}

	private void pushResultsToPDF(Concrete concrete, Steel steel, double mEd) {
		ResultsToPDF.addResults("**PRZEKRÓJ TEOWY**\n\n ", "");
		ResultsToPDF.addResults("Med", String.valueOf(mEd));
		ResultsToPDF.addResults("Fcd", String.valueOf(concrete.getFCd()));
		ResultsToPDF.addResults("Fyd", String.valueOf(steel.getFYd()));
		ResultsToPDF.addResults("Es", String.valueOf(steel.getES()));
		ResultsToPDF.addResults("KsiLim", String.valueOf(ksiLim));
		ResultsToPDF.addResults("DzetaLim", String.valueOf(dzetaLim));
		ResultsToPDF.addResults("A0,lim", String.valueOf(a0Lim));
		ResultsToPDF.addResults("Mrd,hf", String.valueOf(mRdHf));
		ResultsToPDF.addResults("A0", String.valueOf(a0));
		ResultsToPDF.addResults("As1", String.valueOf(aS1));
		ResultsToPDF.addResults("As2", String.valueOf(aS2));
	}

	private void reinforcementTrapezeBeam(double mEd, Concrete concrete, Steel steel, double a1, double a2, double h, double bW, double bEff, double hF, double d) {

		setMRdHf(concrete, bEff, hF, d);
		if (mEd <= mRdHf) {
			System.out.println("pozornie teowy");
			rectangularBeamReinforcement(mEd, concrete, steel, a1, a2, h, bEff, d);
		} else {
			System.out.println("teowy");
			setMRd1(concrete, bEff, hF, d, bW);
			setAS11(concrete, steel, hF, bW, bEff);
			calculateMrdEff(mEd);
			setA0(mRdEff, concrete, d, bW);
			if (a0 <= a0Lim) {
				setDzeta();
				setAS12WhenA0IsLessOrEqualA0Lim(mRdEff, steel, a2, d);
				setAS1();
				// changeAs1ToAsMinIfAs1IsLessThenAsMin();
				aS2 = 0;
			} else {
				setMRdLim(concrete, bEff, d);
				setAs12(concrete, steel, d, bW);
				setAS13(mEd, mRd1, mRdLim, steel, a2, d);
				setAS1(aS11, aS12, aS13);
				// changeAs1ToAsMinIfAs1IsLessThenAsMin();
				aS2 = aS13;
				// changeAs2ToAsMinIfAs2IsLessThenAsMin();
			}
		}
	}

	public void fullTrapezeBeamReinforcementAnalysis(double mEd, Concrete concrete, Steel steel, double a1, double a2, double h, double bW, double bEff, double hF, double d) {
		clearVariables();
		setCalculationModel(concrete, steel);
		reinforcementTrapezeBeam(mEd, concrete, steel, a1, a2, h, bW, bEff, hF, d);
		pushResultsToPDF(concrete, steel, mEd);
	}

}
