package reinforcement.bending;

import materials.Concrete;
import materials.Steel;
import util.ResultsToPDF;

public class RectangularBeam extends ClearBendingBeam {



	public RectangularBeam(double lAMBDA, double eTA) {
		super(lAMBDA, eTA);
		// TODO Auto-generated constructor stub
	}

	private void setAS1(double mEd, Steel steel, double d) {
		aS1 = mEd / (dzeta * steel.getFYd() * 1000 * d);

		System.out.println("aS1 " + aS1);
	}

	@Override
	protected void setAS1() {
		aS1 = aS11 + aS12;

		System.out.println("aS1 " + aS1);
	}

	@Override
	protected void setAS2() {
		aS2 = aS12;

		System.out.println("aS2 " + aS2);
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

	protected void setAS11(Concrete concrete, Steel steel, double d, double b) {
		aS11 = ksiLim * b * d * ETA*concrete.getFCd() * 1000 / (steel.getFYd() * 1000);

		System.out.println("aS11 " + aS11);
	}

	protected void setAS12(double mEd, Steel steel, double a2, double d) {
		aS12 = (mEd - mRdLim) / (steel.getFYd() * 1000 * (d - a2));

		System.out.println("aS12 " + aS12);
	}

	private void pushResultsToPDF(Concrete concrete, Steel steel, double mEd) {
		ResultsToPDF.addResults("**PRZEKRÓJ PROSTOK¥TNY**\n\n ", "");
		ResultsToPDF.addResults("Med", String.valueOf(mEd));
		ResultsToPDF.addResults("Fcd", String.valueOf(concrete.getFCd()));
		ResultsToPDF.addResults("Fyd", String.valueOf(steel.getFYd()));
		ResultsToPDF.addResults("Es", String.valueOf(steel.getES()));
		ResultsToPDF.addResults("KsiLim", String.valueOf(ksiLim));
		ResultsToPDF.addResults("DzetaLim", String.valueOf(dzetaLim));
		ResultsToPDF.addResults("A0,lim", String.valueOf(a0Lim));
		ResultsToPDF.addResults("A0", String.valueOf(a0));
		ResultsToPDF.addResults("As1", String.valueOf(aS1));
		ResultsToPDF.addResults("As2", String.valueOf(aS2));
	}

	protected void rectangularBeamReinforcement(double mEd, Concrete concrete, Steel steel, double a1, double a2, double h, double b, double d) {
		mEd = ifMedIsLessThenZeroReturnAbsAndSetBoolean(mEd);
		setA0(mEd, concrete, d, b);
		if (a0 <= a0Lim) {
			setDzeta();
			setAS1(mEd, steel, d);
			changeAs1ToAsMinIfAs1IsLessThenAsMin();
			aS2 = 0;
		} else {
			setMRdLim(concrete, b, d);
			setAS11(concrete, steel, d, b);
			setAS12(mEd, steel, a2, d);
			setAS1();
			setAS2();
			changeAs1ToAsMinIfAs1IsLessThenAsMin();
			changeAs2ToAsMinIfAs2IsLessThenAsMin();
		}
		ifMedIsLessThenZeroSwapAs1WithAs2();
	}

	public void fullRectangularBeamReinforcementAnalysis(double mEd, Concrete concrete, Steel steel, double a1, double a2, double h, double b, double d) {
		clearVariables();
		setCalculationModel(concrete, steel);
		setASMin(steel, concrete, b, d);
		rectangularBeamReinforcement(mEd, concrete, steel, a1, a2, h, b, d);
		pushResultsToPDF(concrete, steel, mEd);

	}
}
