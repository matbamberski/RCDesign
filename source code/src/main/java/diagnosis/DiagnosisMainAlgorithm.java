package diagnosis;

import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.shearing.ShearingReinforcementAnalizer;

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
	private boolean mRdExceeded;

	public boolean ismRdExceeded() {
		return mRdExceeded;
	}

	public void setmRdExceeded(boolean mRdExceeded) {
		this.mRdExceeded = mRdExceeded;
	}

	public void runDiagnosis(Concrete concrete, Steel steel, double mEd, double nEd, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, InternalForces forces) {
		double requiredUnSymmetricalAS1 = reinforcement.getRequiredUnsymmetricalAS1();
		double requiredUnSymmetricalAS2 = reinforcement.getRequiredUnsymmetricalAS2();
		double designedUnSymmetricalAS1 = reinforcement.getDesingedUnsymmetricalAS1();
		double designedUnSymmetricalAS2 = reinforcement.getDesignedUnsymmetricalAS2();
		double requiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS1();
		double requiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS2();
		double designedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS1();
		double designedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS2();
		
		if (reinforcement.getDesignedSymmetricalAS1() < reinforcement.getDesignedSymmetricalAS2()) {
			requiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS2();
			requiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS1();
			designedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS2();
			designedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS1();
			requiredUnSymmetricalAS1 = reinforcement.getRequiredUnsymmetricalAS2();
			requiredUnSymmetricalAS2 = reinforcement.getRequiredUnsymmetricalAS1();
			designedUnSymmetricalAS1 = reinforcement.getDesignedUnsymmetricalAS2();
			designedUnSymmetricalAS2 = reinforcement.getDesingedUnsymmetricalAS1();
			if (mEd <0) {
				reinforcement.setRequiredSymmetricalAS1(requiredSymmetricalAS2);
				reinforcement.setRequiredSymmetricalAS2(requiredSymmetricalAS1);
				reinforcement.setRequiredUnsymmetricalAS1(requiredUnSymmetricalAS1);
				reinforcement.setRequiredUnsymmetricalAS2(requiredUnSymmetricalAS2);
			} else {
				reinforcement.setRequiredSymmetricalAS1(requiredSymmetricalAS1);
				reinforcement.setRequiredSymmetricalAS2(requiredSymmetricalAS2);
				reinforcement.setRequiredUnsymmetricalAS1(requiredUnSymmetricalAS2);
				reinforcement.setRequiredUnsymmetricalAS2(requiredUnSymmetricalAS1);
			}
		} else {
			requiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS1();
			requiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS2();
			designedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS1();
			designedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS2();
			requiredUnSymmetricalAS1 = reinforcement.getRequiredUnsymmetricalAS1();
			requiredUnSymmetricalAS2 = reinforcement.getRequiredUnsymmetricalAS2();
			designedUnSymmetricalAS1 = reinforcement.getDesingedUnsymmetricalAS1();
			designedUnSymmetricalAS2 = reinforcement.getDesignedUnsymmetricalAS2();
			if (mEd <0) {
				reinforcement.setRequiredSymmetricalAS1(requiredSymmetricalAS2);
				reinforcement.setRequiredSymmetricalAS2(requiredSymmetricalAS1);
				reinforcement.setRequiredUnsymmetricalAS1(requiredUnSymmetricalAS1);
				reinforcement.setRequiredUnsymmetricalAS2(requiredUnSymmetricalAS2);
			} else {
				reinforcement.setRequiredSymmetricalAS1(requiredSymmetricalAS1);
				reinforcement.setRequiredSymmetricalAS2(requiredSymmetricalAS2);
				reinforcement.setRequiredUnsymmetricalAS1(requiredUnSymmetricalAS2);
				reinforcement.setRequiredUnsymmetricalAS2(requiredUnSymmetricalAS1);
			}
		}
		
		
		if (mEd == 0) {
			mEd = nEd / 1000000;
		}
		/*
		if (mEd <0) {
			requiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS2();
			requiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS1();
			designedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS2();
			designedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS1();
			
		}
		*/

		if (nEd == 0) {
			/*
			if (mEd >= 0) {
				requiredSymmetricalAS1 = reinforcement.getRequiredSymmetricalAS1();
				requiredSymmetricalAS2 = reinforcement.getRequiredSymmetricalAS2();
				designedSymmetricalAS1 = reinforcement.getDesignedSymmetricalAS1();
				designedSymmetricalAS2 = reinforcement.getDesignedSymmetricalAS2();
			}
			 */
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
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, requiredSymmetricalAS1, requiredSymmetricalAS2, mEd, nEd);
			mRdRequiredSymmetrical = diagnosis.getmRd();
			nRdRequiredSymmetrical = diagnosis.getnRd();
			// required unsymmetrical
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, requiredUnSymmetricalAS1, requiredUnSymmetricalAS2, mEd, nEd);
			mRdRequiredUnsymmetrical = diagnosis.getmRd();
			nRdRequiredUnsymmetrical = diagnosis.getnRd();
			// designed symmetrical
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, designedSymmetricalAS1, designedSymmetricalAS2, mEd, nEd);
			mRdDesignedSymmetrical = diagnosis.getmRd();
			nRdDesignedSymmetrical = diagnosis.getnRd();
			// designed unsymmetrical
			diagnosis.doFullCompresingReinforcementDiagnosis(concrete, steel, dimensions, designedUnSymmetricalAS1, designedUnSymmetricalAS2, mEd, nEd);
			mRdDesignedUnsymmetrical = diagnosis.getmRd();
			nRdDesignedUnsymmetrical = diagnosis.getnRd();
		}
		if (nEd < 0) {
			TensilingDiagnosis diagnosis = new TensilingDiagnosis();
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, requiredSymmetricalAS1, requiredSymmetricalAS2);
			mRdRequiredSymmetrical = diagnosis.getmRd();
			nRdRequiredSymmetrical = diagnosis.getnRd();
			// required unsymmetrical
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, requiredUnSymmetricalAS1, requiredUnSymmetricalAS2);
			mRdRequiredUnsymmetrical = diagnosis.getmRd();
			nRdRequiredUnsymmetrical = diagnosis.getnRd();
			// designed symmetrical
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, designedSymmetricalAS1, designedSymmetricalAS2);
			mRdDesignedSymmetrical = diagnosis.getmRd();
			nRdDesignedSymmetrical = diagnosis.getnRd();
			// designed unsymmetrical
			diagnosis.doFullTensilingReinforcementDiagnosis(concrete, steel, dimensions, mEd, nEd, designedUnSymmetricalAS1, designedUnSymmetricalAS2);
			mRdDesignedUnsymmetrical = diagnosis.getmRd();
			nRdDesignedUnsymmetrical = diagnosis.getnRd();
		}
		ShearingReinforcementAnalizer shearingDiagnosis = new ShearingReinforcementAnalizer();
		shearingDiagnosis.doFullSheringReinforcementWithoutDesign(concrete, steel, forces, dimensions, reinforcement, reinforcement.getS1Required(), reinforcement.getS2Required());
		//shearingDiagnosis.runShearingDiagnosis(concrete, steel, reinforcement, dimensions, reinforcement.getS1Required(), reinforcement.getS2Required(), reinforcement.getnS2Required(), shearingDiagnosis, forces);
		vRdRequired = shearingDiagnosis.getvRd();
		System.out.println("vRd Required = " + vRdRequired);
		//ShearingReinforcementAnalizer shearingDiagnosis2 = new ShearingReinforcementAnalizer();
		shearingDiagnosis.doFullSheringReinforcementWithoutDesign(concrete, steel, forces, dimensions, reinforcement, reinforcement.getS1Designed(), reinforcement.getS2Designed());
		//shearingDiagnosis2.runShearingDiagnosis(concrete, steel, reinforcement, dimensions, reinforcement.getS1Designed(), reinforcement.getS2Designed(), reinforcement.getnS2Designed(), shearingDiagnosis2, forces);
		vRdDesigned = shearingDiagnosis.getvRd();
		System.out.println("vRd Designed = " + vRdDesigned);
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
