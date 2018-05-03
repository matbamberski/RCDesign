package mainalgorithm;

import materials.Concrete;
import materials.Steel;
import reinforcement.axisload.SymmetricalCompressingBeamReinforcement;
import reinforcement.axisload.SymmetricalTensilingBeamReinforcement;
import reinforcement.axisload.UnsymmetricalCompressingBeamReinforcement;
import reinforcement.axisload.UnsymmetricalTensilingBeamReinforcement;
import reinforcement.bending.RectangularBeam;

public class test123 {

	public static void main(String[] args) {

		double b = 0.3;
		double h = 0.6;
		double a1 = 0.05;
		double a2 = 0.05;
		double d = h - a1;

		double nEd = 5;
		double mEd = 10;

		System.out.println("b " + b);
		System.out.println("h " + h);
		System.out.println("a1 " + a1);
		System.out.println("a2 " + a2);
		System.out.println("d " + d);
		System.out.println("nEd " + nEd + " kN");
		System.out.println("mEd " + mEd + " kNm");

		Boolean isBeamRectangular = true;

		Concrete concrete = new Concrete(4);
		Steel steel = new Steel();

		if (nEd == 0) {
			if (isBeamRectangular) {
				RectangularBeam beam = new RectangularBeam();
				beam.fullRectangularBeamReinforcementAnalysis(mEd, concrete, steel, a1, a2, h, b, d);
			} else {
				// TrapezeBeam beam=new TrapezeBeam();
				// beam.fullTrapezeBeamReinforcementAnalysis(mEd, concrete,
				// steel, a1, a2, h, bW, bEff, hF, mEd);
			}
		} else {
			if (nEd < 0) {
				SymmetricalCompressingBeamReinforcement beamSymmetricalReinforcement = new SymmetricalCompressingBeamReinforcement();
				beamSymmetricalReinforcement.fullSymmetricalCompressingBeamReinforcement(concrete, steel, mEd, nEd, a1,
						a2, d, b, h);

				UnsymmetricalCompressingBeamReinforcement beamUnsymmetricalReinforcement = new UnsymmetricalCompressingBeamReinforcement();
				beamUnsymmetricalReinforcement.fullUnsymmetricalCompressingBeamReinforcement(concrete, steel, mEd, nEd,
						b, h, d, a1, a2);
			}
			if (nEd > 0) {
				SymmetricalTensilingBeamReinforcement beamSymmetricalReinforcement = new SymmetricalTensilingBeamReinforcement();
				beamSymmetricalReinforcement.fullSymetricalTensilingBeamReinforcement(concrete, steel, mEd, nEd, a1, a2,
						d, b, h);

				UnsymmetricalTensilingBeamReinforcement beamUnsymmetricalReinforcement = new UnsymmetricalTensilingBeamReinforcement();
				beamUnsymmetricalReinforcement.fullUnsymmetricalTensilingBeamReinforcement(concrete, steel, mEd, nEd, d,
						a1, a2, b, h);
			}
		}

	}
}
