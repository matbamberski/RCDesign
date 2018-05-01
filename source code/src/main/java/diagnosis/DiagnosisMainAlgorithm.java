package diagnosis;

import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class DiagnosisMainAlgorithm {

	private double mRdRequiredSymmetrical;
	private double nRdRequiredSymmetrical;
	// private double vRdRequiredSymmetrical;
	private double mRdDesignedSymmetrical;
	private double nRdDesignedSymmetrical;
	// private double vRdDesignedSymmetrical;
	private double mRdRequiredUnsymmetrical;
	private double nRdRequiredUnsymmetrical;
	// private double vRdRequiredUnsymmetrical;
	private double mRdDesignedUnsymmetrical;
	private double nRdDesignedUnsymmetrical;
	// private double vRdDesignedUnsymmetrical;
	private double vRdRequired;
	private double vRdDesigned;

	public void runDiagnosis(Concrete concrete, Steel steel, double mEd, double nEd, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		if (mEd == 0) {
			mEd = nEd / 1000000;
		}

		if (nEd == 0) {
			double requiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS1();
			double requiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS2();
			double designedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS1();
			double designedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS2();

			// if Ned = 0 reinforcement is only symmetrical
			if (dimensions.getisBeamRectangular()) {
				RectangularDiagnosis diagnosis = new RectangularDiagnosis();
				// required
				diagnosis.fullRectangularBeamReinforcementDiagnosis(concrete, steel, dimensions.getB(), dimensions.getA1(), dimensions.getA2(), requiredSymmetricalAS1, requiredSymmetricalAS2,
						dimensions.getD());
				mRdRequiredSymmetrical = diagnosis.getmRd();
				// designed
				diagnosis.fullRectangularBeamReinforcementDiagnosis(concrete, steel, dimensions.getB(), dimensions.getA1(), dimensions.getA2(), designedSymmetricalAS1, designedSymmetricalAS2,
						dimensions.getD());
				mRdDesignedSymmetrical = diagnosis.getmRd();
			} else {
				if (dimensions.gettW() == 0) {
					RectangularDiagnosis diagnosis = new RectangularDiagnosis();
					// required
					diagnosis.fullRectangularBeamReinforcementDiagnosis(concrete, steel, dimensions.getB(), dimensions.getA1(), dimensions.getA2(), requiredSymmetricalAS1, requiredSymmetricalAS2,
							dimensions.getD());
					mRdRequiredSymmetrical = diagnosis.getmRd();
					// designed
					diagnosis.fullRectangularBeamReinforcementDiagnosis(concrete, steel, dimensions.getB(), dimensions.getA1(), dimensions.getA2(), designedSymmetricalAS1, designedSymmetricalAS2,
							dimensions.getD());
					mRdDesignedSymmetrical = diagnosis.getmRd();
				} else {
					TrapezeDiagnosis diagnosis = new TrapezeDiagnosis();
					// required
					diagnosis.fullTrapezeBeamReinforcementDiagnosis(concrete, steel, dimensions.getB(), dimensions.getbEff(), dimensions.getA1(), dimensions.getA2(), requiredSymmetricalAS1,
							requiredSymmetricalAS2, dimensions.gettW(), dimensions.getD());
					mRdRequiredSymmetrical = diagnosis.getmRd();
					// designed
					diagnosis.fullTrapezeBeamReinforcementDiagnosis(concrete, steel, dimensions.getB(), dimensions.getbEff(), dimensions.getA1(), dimensions.getA2(), designedSymmetricalAS1,
							designedSymmetricalAS2, dimensions.gettW(), dimensions.getD());
					mRdDesignedSymmetrical = diagnosis.getmRd();
				}
			}
		}
		if (nEd > 0) {
			CompressingDiagnosis diagnosis = new CompressingDiagnosis();
			// required symmetrical
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, reinforcement.getRequiredSymmetricalAS1(), reinforcement.getRequiredSymmetricalAS2(), mEd, nEd);
			mRdRequiredSymmetrical = diagnosis.getmRd();
			nRdRequiredSymmetrical = diagnosis.getnRd();
			// required unsymmetrical
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, reinforcement.getRequiredUnsymmetricalAS1(), reinforcement.getRequiredUnsymmetricalAS2(), mEd, nEd);
			mRdRequiredUnsymmetrical = diagnosis.getmRd();
			nRdRequiredUnsymmetrical = diagnosis.getnRd();
			// designed symmetrical
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, reinforcement.getDesignedSymmetricalAS1(), reinforcement.getDesignedSymmetricalAS2(), mEd, nEd);
			mRdDesignedSymmetrical = diagnosis.getmRd();
			nRdDesignedSymmetrical = diagnosis.getnRd();
			// designed unsymmetrical
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, reinforcement.getDesingedUnsymmetricalAS1(), reinforcement.getDesignedUnsymmetricalAS2(), mEd, nEd);
			mRdDesignedUnsymmetrical = diagnosis.getmRd();
			nRdDesignedUnsymmetrical = diagnosis.getnRd();
		}
		if (nEd < 0) {
			TensilingDiagnosis diagnosis = new TensilingDiagnosis();
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, reinforcement.getRequiredSymmetricalAS1(), reinforcement.getRequiredSymmetricalAS2());
			mRdRequiredSymmetrical = diagnosis.getmRd();
			nRdRequiredSymmetrical = diagnosis.getnRd();
			// required unsymmetrical
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, reinforcement.getRequiredUnsymmetricalAS1(), reinforcement.getRequiredUnsymmetricalAS2());
			mRdRequiredUnsymmetrical = diagnosis.getmRd();
			nRdRequiredUnsymmetrical = diagnosis.getnRd();
			// designed symmetrical
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, reinforcement.getDesignedSymmetricalAS1(), reinforcement.getDesignedSymmetricalAS2());
			mRdDesignedSymmetrical = diagnosis.getmRd();
			nRdDesignedSymmetrical = diagnosis.getnRd();
			// designed unsymmetrical
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, reinforcement.getDesingedUnsymmetricalAS1(), reinforcement.getDesignedUnsymmetricalAS2());
			mRdDesignedUnsymmetrical = diagnosis.getmRd();
			nRdDesignedUnsymmetrical = diagnosis.getnRd();
		}
		ShearingDiagnosis shearingDiagnosis = new ShearingDiagnosis();
		shearingDiagnosis.runShearingDiagnosis(concrete, steel, reinforcement, dimensions, reinforcement.getS1Required(), reinforcement.getS2Required(), reinforcement.getnS2Required());
		vRdRequired = shearingDiagnosis.getvRd();
		ShearingDiagnosis shearingDiagnosis2 = new ShearingDiagnosis();
		shearingDiagnosis2.runShearingDiagnosis(concrete, steel, reinforcement, dimensions, reinforcement.getS1Designed(), reinforcement.getS2Designed(), reinforcement.getnS2Designed());
		vRdDesigned = shearingDiagnosis2.getvRd();
	}

	public double getmRdRequiredSymmetrical() {
		return mRdRequiredSymmetrical;
	}

	public double getnRdRequiredSymmetrical() {
		return nRdRequiredSymmetrical;
	}

	public double getmRdDesignedSymmetrical() {
		return mRdDesignedSymmetrical;
	}

	public double getnRdDesignedSymmetrical() {
		return nRdDesignedSymmetrical;
	}

	public double getmRdRequiredUnsymmetrical() {
		return mRdRequiredUnsymmetrical;
	}

	public double getnRdRequiredUnsymmetrical() {
		return nRdRequiredUnsymmetrical;
	}

	public double getmRdDesignedUnsymmetrical() {
		return mRdDesignedUnsymmetrical;
	}

	public double getnRdDesignedUnsymmetrical() {
		return nRdDesignedUnsymmetrical;
	}

	public double getvRdRequired() {
		return vRdRequired;
	}

	public double getvRdDesigned() {
		return vRdDesigned;
	}

}
