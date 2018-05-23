package mainalgorithm;

import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.axisload.SymmetricalCompressingBeamReinforcement;
import reinforcement.axisload.SymmetricalTensilingBeamReinforcement;
import reinforcement.axisload.UnsymmetricalCompressingBeamReinforcement;
import reinforcement.axisload.UnsymmetricalTensilingBeamReinforcement;
import reinforcement.bending.Column;
import reinforcement.bending.RectangularBeam;
import reinforcement.bending.TrapezeBeam;
import reinforcement.shearing.ShearingReinforcementAnalizer;

public class RequiredReinforcement {
	ShearingReinforcementAnalizer shearingReinforcementAnalizer = new ShearingReinforcementAnalizer();

	private void printInputValuesInConsole(Concrete concrete, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions) {
		System.out.println("\n ");
		System.out.println("b " + dimensions.getB());
		System.out.println("h " + dimensions.getH());
		System.out.println("a1 " + dimensions.getA1());
		System.out.println("a2 " + dimensions.getA2());
		System.out.println("d " + dimensions.getD());
		System.out.println("nEd " + internalForces.getnEd());
		System.out.println("mEd " + internalForces.getmEd());
		System.out.println("fcd " + concrete.getFCd());
		System.out.println("Mmax " + internalForces.getMomentMmax() + " Nodp " + internalForces.getNormalnaMmax());
		System.out.println("Mmin " + internalForces.getMomentMmin() + " Nodp " + internalForces.getNormalnaMmin());
		System.out.println("Modp " + internalForces.getMomentNmax() + " Nmax " + internalForces.getNormalnaNmax());
		System.out.println("Modp " + internalForces.getMomentNmin() + " Nmin " + internalForces.getNormalnaNmin());
	}

	private void setRequiredSymmetricalReinforcementForRectangularBeam(RectangularBeam beam,
			Reinforcement reinforcement) {
		reinforcement.setRequiredSymmetricalAS1(beam.getAS1());
		reinforcement.setRequiredSymmetricalAS2(beam.getAS2());
	}

	private void setRequiredSymmetricalReinforcementForTrapeze(TrapezeBeam beam, Reinforcement reinforcement) {
		reinforcement.setRequiredSymmetricalAS1(beam.getAS1());
		reinforcement.setRequiredSymmetricalAS2(beam.getAS2());

	}

	private void setRequiredSymmetricalReinforcementCompressing(SymmetricalCompressingBeamReinforcement beam,
			Reinforcement reinforcement) {
		reinforcement.setRequiredSymmetricalAS1(beam.getaS());
		reinforcement.setRequiredSymmetricalAS2(beam.getaS());

	}

	private void setRequiredUnsymmetricalReinforcementCompressing(UnsymmetricalCompressingBeamReinforcement beam,
			Reinforcement reinforcement) {
		reinforcement.setRequiredUnsymmetricalAS1(beam.getaS1());
		reinforcement.setRequiredUnsymmetricalAS2(beam.getaS2());

	}

	private void setRequiredSymmetricalReinforcementTensiling(SymmetricalTensilingBeamReinforcement beam,
			Reinforcement reinforcement) {
		reinforcement.setRequiredSymmetricalAS1(beam.getaS());
		reinforcement.setRequiredSymmetricalAS2(beam.getaS());
	}

	private void setRequiredUnsymmetricalReinforcementTensiling(UnsymmetricalTensilingBeamReinforcement beam,
			Reinforcement reinforcement) {
		reinforcement.setRequiredUnsymmetricalAS1(beam.getaS1());
		reinforcement.setRequiredUnsymmetricalAS2(beam.getaS2());

	}

	private void designSymmetricalReinforcement(Reinforcement reinforcement) {
		double aS1Diameter = reinforcement.getDesignedDiameterSymmetricalAS1();
		double aS2Diameter = reinforcement.getDesignedDiameterSymmetricalAS2();
		double aS1Required = reinforcement.getRequiredSymmetricalAS1();
		double aS2Required = reinforcement.getRequiredSymmetricalAS2();
		double aS1SurfaceArea = (aS1Diameter / 1000) * (aS1Diameter / 1000) * Math.PI / 4;
		double aS2SurfaceArea = (aS2Diameter / 1000) * (aS2Diameter / 1000) * Math.PI / 4;
		double aS1Designed = 0;
		double aS2Designed = 0;
		int numberOfAS1Rods = 0;
		int numberOfAS2Rods = 0;
		do {
			aS1Designed = aS1Designed + aS1SurfaceArea;
			numberOfAS1Rods = numberOfAS1Rods + 1;
		} while (aS1Designed < aS1Required);

		if (numberOfAS1Rods < 2) {
			numberOfAS1Rods = 2;
			aS1Designed = aS1Designed + aS1SurfaceArea;
		}
		if (aS1Required == 0) {
			aS1Designed = 0;
			numberOfAS1Rods = 0;
		}
		reinforcement.setDesignedSymmetricalAS1(aS1Designed);
		reinforcement.setRequiredNumberOfSymmetricalRodsAS1(numberOfAS1Rods);

		//// To chyba niepotrzebne skoro ma byc symetryczne?
		do {
			aS2Designed = aS2Designed + aS2SurfaceArea;
			numberOfAS2Rods = numberOfAS2Rods + 1;
		} while (aS2Designed < aS2Required);
		if (numberOfAS2Rods < 2) {
			numberOfAS2Rods = 2;
			aS2Designed = 2 * aS2SurfaceArea;
		}
		if (aS2Required == 0) {
			aS2Designed = 0;
			numberOfAS2Rods = 0;
		}
		reinforcement.setDesignedSymmetricalAS2(aS2Designed);
		reinforcement.setRequiredNumberOfSymmetricalRodsAS2(numberOfAS2Rods);

	}

	private void designUnsymmetricalReinforcement(Reinforcement reinforcement) {
		double aS1Diameter = reinforcement.getDesignedDiameterSymmetricalAS1();
		double aS2Diameter = reinforcement.getDesignedDiameterSymmetricalAS2();
		double aS1Required = reinforcement.getRequiredUnsymmetricalAS1();
		double aS2Required = reinforcement.getRequiredUnsymmetricalAS2();
		double aS1SurfaceArea = (aS1Diameter / 1000) * (aS1Diameter / 1000) * Math.PI / 4;
		double aS2SurfaceArea = (aS2Diameter / 1000) * (aS2Diameter / 1000) * Math.PI / 4;
		double aS1Designed = 0;
		double aS2Designed = 0;
		int numberOfAS1Rods = 0;
		int numberOfAS2Rods = 0;
		do {
			aS1Designed = aS1Designed + aS1SurfaceArea;
			numberOfAS1Rods = numberOfAS1Rods + 1;
		} while (aS1Designed < aS1Required);

		if (numberOfAS1Rods < 2) {
			numberOfAS1Rods = 2;
			aS1Designed = 2 * aS1SurfaceArea;
		}
		reinforcement.setDesingedUnsymmetricalAS1(aS1Designed);
		reinforcement.setRequiredNumberOfUnsymmetricalRodsAS1(numberOfAS1Rods);
		do {
			aS2Designed = aS2Designed + aS2SurfaceArea;
			numberOfAS2Rods = numberOfAS2Rods + 1;
		} while (aS2Designed < aS2Required);
		if (numberOfAS2Rods < 2) {
			numberOfAS2Rods = 2;
			aS2Designed = aS2Designed + aS2SurfaceArea;
		}
		reinforcement.setDesignedUnsymmetricalAS2(aS2Designed);
		reinforcement.setRequiredNumberOfUnsymmetricalRodsAS2(numberOfAS2Rods);

	}

	private void designShearingReinforcement(Concrete concrete, Steel steel, InternalForces forces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		shearingReinforcementAnalizer.doFullShearingReinforcementAnalysis(concrete, steel, forces, dimensions,
				reinforcement);
	}

	// ta juz dziala
	private boolean checkIfRodsCanBePutInTheSameRow(Reinforcement reinforcement,
			DimensionsOfCrossSectionOfConcrete dimensions) {
		boolean canBePutInTheSameRow = true;
		double sL = 0;
		int i = reinforcement.getRequiredNumberOfSymmetricalRodsAS1();
		reinforcement.setSLmin(Math.max(reinforcement.getDesignedDiameterSymmetricalAS1() / 1000, 0.021));
		sL = (dimensions.getB() - 2 * dimensions.getA1() - i * reinforcement.getDesignedDiameterSymmetricalAS1() / 1000)
				/ (i - 1);
		System.out.println("sL [m]" + sL + " slMin " + reinforcement.getsLmin());
		reinforcement.setSL(sL);
		if (sL < reinforcement.getsLmin()) {
			canBePutInTheSameRow = false;
		}
		System.out.println("can be put in the same row " + canBePutInTheSameRow);
		return canBePutInTheSameRow;

	}

	// do przetestowania
	private void changeDIfRodsCanNotBePutInTheSameRow(boolean canNotBePutInTheSameRow,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		if (canNotBePutInTheSameRow) {
			dimensions.setA1(dimensions.getA1() + reinforcement.getsLmin() / 2);
		}
	}

	public void checkWhatIsRequiredReinforcementAndDesign(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {

		printInputValuesInConsole(concrete, internalForces, dimensions);
		if (!dimensions.getIsColumn()) {
			if (internalForces.getnEd() == 0) {
				if (dimensions.getisBeamRectangular()) {
					rectangularBeamBendingReinforcementWithDesign(concrete, steel, internalForces, dimensions,
							reinforcement);
				} else {
					if (dimensions.gettW() == 0) {
						rectangularBeamBendingReinforcementWithDesign(concrete, steel, internalForces, dimensions,
								reinforcement);
					} else {
						traptezeBeamBendingReinforcementWithDesign(concrete, steel, internalForces, dimensions,
								reinforcement);
					}
				}
			} else {
				if (internalForces.getnEd() > 0) {
					System.out.println("ï¿½ciskanie ");
					rectangularBeamCompressingForcesSymmetricalReinforcementWithDesign(concrete, steel, internalForces,
							dimensions, reinforcement);
					rectangularBeamCompressingForcesUnsymmetricalReinforcementWithDesign(concrete, steel,
							internalForces, dimensions, reinforcement);
				}
				if (internalForces.getnEd() < 0) {
					System.out.println("rozciï¿½ganie ");
					// ROZCIAGANIE , W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN
					// PODSTAWIONE Z + !!!
					rectangularBeamTensilingForcesSymmetricalReinforcementWithDesign(concrete, steel, internalForces,
							dimensions, reinforcement);
					rectangularBeamTensilingForcesUnsymmetricalReinforcementWithDesign(concrete, steel, internalForces,
							dimensions, reinforcement);
				}
			}
			designShearingReinforcement(concrete, steel, internalForces, dimensions, reinforcement);
		} else {
			NominalStiffness stiffness = new NominalStiffness();
			columnRequiredReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness, true);
			System.err.println("S³up");
		}
	}

	///////// Slupy

	public void columnCompressingForcesSymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {
		SymmetricalCompressingBeamReinforcement column = new SymmetricalCompressingBeamReinforcement();
		column.fullSymmetricalCompressingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getA1(),
				dimensions.getA2(), dimensions.getD(), dimensions.getB(), dimensions.getH());
		setRequiredSymmetricalReinforcementCompressing(column, reinforcement);
		designSymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	public void columnTensilingForcesSymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {
		SymmetricalTensilingBeamReinforcement column = new SymmetricalTensilingBeamReinforcement();
		column.fullSymetricalTensilingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getA1(),
				dimensions.getA2(), dimensions.getD(), dimensions.getB(), dimensions.getH());
		setRequiredSymmetricalReinforcementTensiling(column, reinforcement);
		designSymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	public void columnCompressingForcesUnSymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {
		UnsymmetricalCompressingBeamReinforcement column = new UnsymmetricalCompressingBeamReinforcement();
		column.fullUnsymmetricalCompressingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getB(), dimensions.getH(),
				dimensions.getD(), dimensions.getA1(), dimensions.getA2());
		setRequiredUnsymmetricalReinforcementCompressing(column, reinforcement);
		designUnsymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
	}

	public void columnTensilingForcesUnsymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {

		UnsymmetricalTensilingBeamReinforcement column = new UnsymmetricalTensilingBeamReinforcement();
		column.fullUnsymmetricalTensilingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getD(), 
				dimensions.getA1(), dimensions.getA2(), dimensions.getB(), dimensions.getH());
		setRequiredUnsymmetricalReinforcementTensiling(column, reinforcement);
		designUnsymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
	}
	
	
	//without design
	public void columnCompressingForcesSymmetricalReinforcement(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {
		SymmetricalCompressingBeamReinforcement column = new SymmetricalCompressingBeamReinforcement();
		column.fullSymmetricalCompressingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getA1(),
				dimensions.getA2(), dimensions.getD(), dimensions.getB(), dimensions.getH());
		setRequiredSymmetricalReinforcementCompressing(column, reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	public void columnTensilingForcesSymmetricalReinforcement(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {
		SymmetricalTensilingBeamReinforcement column = new SymmetricalTensilingBeamReinforcement();
		column.fullSymetricalTensilingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getA1(),
				dimensions.getA2(), dimensions.getD(), dimensions.getB(), dimensions.getH());
		setRequiredSymmetricalReinforcementTensiling(column, reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	public void columnCompressingForcesUnSymmetricalReinforcement(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {
		UnsymmetricalCompressingBeamReinforcement column = new UnsymmetricalCompressingBeamReinforcement();
		column.fullUnsymmetricalCompressingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getB(), dimensions.getH(),
				dimensions.getD(), dimensions.getA1(), dimensions.getA2());
		setRequiredUnsymmetricalReinforcementCompressing(column, reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
	}

	public void columnTensilingForcesUnsymmetricalReinforcement(Concrete concrete, Steel steel,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double mEd, double nEd) {

		UnsymmetricalTensilingBeamReinforcement column = new UnsymmetricalTensilingBeamReinforcement();
		column.fullUnsymmetricalTensilingBeamReinforcement(concrete, steel, mEd, nEd, dimensions.getD(), 
				dimensions.getA1(), dimensions.getA2(), dimensions.getB(), dimensions.getH());
		setRequiredUnsymmetricalReinforcementTensiling(column, reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
	}
	
	////////////////////////WITH DESIGN

	private void rectangularBeamBendingReinforcementWithDesign(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		RectangularBeam beam = new RectangularBeam();
		beam.fullRectangularBeamReinforcementAnalysis(internalForces.getmEd(), concrete, steel, dimensions.getA1(),
				dimensions.getA2(), dimensions.getH(), dimensions.getB(), dimensions.getD());
		setRequiredSymmetricalReinforcementForRectangularBeam(beam, reinforcement);
		designSymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);

	}

	private void traptezeBeamBendingReinforcementWithDesign(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		TrapezeBeam beam = new TrapezeBeam();
		beam.fullTrapezeBeamReinforcementAnalysis(internalForces.getmEd(), concrete, steel, dimensions.getA1(),
				dimensions.getA2(), dimensions.getH(), dimensions.getB(), dimensions.getbEff(), dimensions.gettW(),
				dimensions.getD());
		setRequiredSymmetricalReinforcementForTrapeze(beam, reinforcement);
		designSymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	private void rectangularBeamCompressingForcesSymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		SymmetricalCompressingBeamReinforcement beamSymmetricalReinforcement = new SymmetricalCompressingBeamReinforcement();
		beamSymmetricalReinforcement.fullSymmetricalCompressingBeamReinforcement(concrete, steel,
				internalForces.getmEd(), internalForces.getnEd(), dimensions.getA1(), dimensions.getA2(),
				dimensions.getD(), dimensions.getB(), dimensions.getH());
		setRequiredSymmetricalReinforcementCompressing(beamSymmetricalReinforcement, reinforcement);
		designSymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	private void rectangularBeamCompressingForcesUnsymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		UnsymmetricalCompressingBeamReinforcement beamUnsymmetricalReinforcement = new UnsymmetricalCompressingBeamReinforcement();
		beamUnsymmetricalReinforcement.fullUnsymmetricalCompressingBeamReinforcement(concrete, steel,
				internalForces.getmEd(), internalForces.getnEd(), dimensions.getB(), dimensions.getH(),
				dimensions.getD(), dimensions.getA1(), dimensions.getA2());
		setRequiredUnsymmetricalReinforcementCompressing(beamUnsymmetricalReinforcement, reinforcement);
		designUnsymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
	}

	// ROZCIAGANIE , W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN PODSTAWIONE Z +
	// !!!

	private void rectangularBeamTensilingForcesSymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		SymmetricalTensilingBeamReinforcement beamSymmetricalReinforcement = new SymmetricalTensilingBeamReinforcement();
		beamSymmetricalReinforcement.fullSymetricalTensilingBeamReinforcement(concrete, steel, internalForces.getmEd(),
				-internalForces.getnEd(), dimensions.getA1(), dimensions.getA2(), dimensions.getD(), dimensions.getB(),
				dimensions.getH());
		setRequiredSymmetricalReinforcementTensiling(beamSymmetricalReinforcement, reinforcement);
		designSymmetricalReinforcement(reinforcement);

		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);

	}

	private void rectangularBeamTensilingForcesUnsymmetricalReinforcementWithDesign(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		UnsymmetricalTensilingBeamReinforcement beamUnsymmetricalReinforcement = new UnsymmetricalTensilingBeamReinforcement();
		beamUnsymmetricalReinforcement.fullUnsymmetricalTensilingBeamReinforcement(concrete, steel,
				internalForces.getmEd(), -internalForces.getnEd(), dimensions.getD(), dimensions.getA1(),
				dimensions.getA2(), dimensions.getB(), dimensions.getH());
		setRequiredUnsymmetricalReinforcementTensiling(beamUnsymmetricalReinforcement, reinforcement);

		designUnsymmetricalReinforcement(reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
	}

	// without designing

	private void rectangularBeamBendingReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		RectangularBeam beam = new RectangularBeam();
		beam.fullRectangularBeamReinforcementAnalysis(internalForces.getmEd(), concrete, steel, dimensions.getA1(),
				dimensions.getA2(), dimensions.getH(), dimensions.getB(), dimensions.getD());
		setRequiredSymmetricalReinforcementForRectangularBeam(beam, reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	private void traptezeBeamBendingReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		TrapezeBeam beam = new TrapezeBeam();
		beam.fullTrapezeBeamReinforcementAnalysis(internalForces.getmEd(), concrete, steel, dimensions.getA1(),
				dimensions.getA2(), dimensions.getH(), dimensions.getB(), dimensions.getbEff(), dimensions.gettW(),
				dimensions.getD());
		setRequiredSymmetricalReinforcementForTrapeze(beam, reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	private void rectangularBeamCompressingForcesSymmetricalReinforcement(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		SymmetricalCompressingBeamReinforcement beamSymmetricalReinforcement = new SymmetricalCompressingBeamReinforcement();
		beamSymmetricalReinforcement.fullSymmetricalCompressingBeamReinforcement(concrete, steel,
				internalForces.getmEd(), internalForces.getnEd(), dimensions.getA1(), dimensions.getA2(),
				dimensions.getD(), dimensions.getB(), dimensions.getH());
		setRequiredSymmetricalReinforcementCompressing(beamSymmetricalReinforcement, reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	private void rectangularBeamCompressingForcesUnsymmetricalReinforcement(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		UnsymmetricalCompressingBeamReinforcement beamUnsymmetricalReinforcement = new UnsymmetricalCompressingBeamReinforcement();
		beamUnsymmetricalReinforcement.fullUnsymmetricalCompressingBeamReinforcement(concrete, steel,
				internalForces.getmEd(), internalForces.getnEd(), dimensions.getB(), dimensions.getH(),
				dimensions.getD(), dimensions.getA1(), dimensions.getA2());
		setRequiredUnsymmetricalReinforcementCompressing(beamUnsymmetricalReinforcement, reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);

	}

	///////////////////////////////////////

	// ROZCIAGANIE , W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN PODSTAWIONE Z +
	// !!!

	private void rectangularBeamTensilingForcesSymmetricalReinforcement(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		SymmetricalTensilingBeamReinforcement beamSymmetricalReinforcement = new SymmetricalTensilingBeamReinforcement();
		beamSymmetricalReinforcement.fullSymetricalTensilingBeamReinforcement(concrete, steel, internalForces.getmEd(),
				-internalForces.getnEd(), dimensions.getA1(), dimensions.getA2(), dimensions.getD(), dimensions.getB(),
				dimensions.getH());
		setRequiredSymmetricalReinforcementTensiling(beamSymmetricalReinforcement, reinforcement);
		reinforcement.setDegreeOfComputedSymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedSymmetricalReinforcement(dimensions);
	}

	private void rectangularBeamTensilingForcesUnsymmetricalReinforcement(Concrete concrete, Steel steel,
			InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		UnsymmetricalTensilingBeamReinforcement beamUnsymmetricalReinforcement = new UnsymmetricalTensilingBeamReinforcement();
		beamUnsymmetricalReinforcement.fullUnsymmetricalTensilingBeamReinforcement(concrete, steel,
				internalForces.getmEd(), -internalForces.getnEd(), dimensions.getD(), dimensions.getA1(),
				dimensions.getA2(), dimensions.getB(), dimensions.getH());
		setRequiredUnsymmetricalReinforcementTensiling(beamUnsymmetricalReinforcement, reinforcement);
		reinforcement.setDegreeOfComputedUnsymmetricalReinforcementRectangular(dimensions);
		reinforcement.setDegreeOfDesignedUnsymmetricalReinforcement(dimensions);
	}

	public void checkWhatIsRequiredReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {

		printInputValuesInConsole(concrete, internalForces, dimensions);

		/// Jesli zaznaczono belke
		if (!dimensions.getIsColumn()) {
			if (internalForces.getnEd() == 0) {
				if (dimensions.getisBeamRectangular()) {
					rectangularBeamBendingReinforcement(concrete, steel, internalForces, dimensions, reinforcement);
					System.err.println("Belka prostokï¿½tna, zginanie");
				} else {
					if (dimensions.gettW() == 0) {
						rectangularBeamBendingReinforcement(concrete, steel, internalForces, dimensions, reinforcement);
						System.err.println("Belka prostokï¿½tna, zginanie");
					} else {
						traptezeBeamBendingReinforcement(concrete, steel, internalForces, dimensions, reinforcement);
						System.err.println("Belka trapezowa, zginanie");
					}
				}
			} else {
				if (internalForces.getnEd() > 0) {
					System.out.println("ï¿½ciskanie ");
					rectangularBeamCompressingForcesSymmetricalReinforcement(concrete, steel, internalForces,
							dimensions, reinforcement);
					System.err.println("Belka prostokï¿½tna, ï¿½ciskanie");
					rectangularBeamCompressingForcesUnsymmetricalReinforcement(concrete, steel, internalForces,
							dimensions, reinforcement);
				}
				if (internalForces.getnEd() < 0) {
					System.out.println("rozciï¿½ganie ");
					// ROZCIAGANIE , W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN
					// PODSTAWIONE Z + !!!
					rectangularBeamTensilingForcesSymmetricalReinforcement(concrete, steel, internalForces, dimensions,
							reinforcement);
					System.err.println("Belka prostokï¿½tna, rozciï¿½ganie");
					rectangularBeamTensilingForcesUnsymmetricalReinforcement(concrete, steel, internalForces,
							dimensions, reinforcement);
				}
			}
			designShearingReinforcement(concrete, steel, internalForces, dimensions, reinforcement);
		}
		//// Jesli zaznaczono slup
		else {
			NominalStiffness stiffness = new NominalStiffness();
			columnRequiredReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness, false);
			System.err.println("Sï¿½up");
		}

	}

	//// Sï¿½upy

	public void columnRequiredReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, 
			NominalStiffness stiffness, boolean withDesign) {
		Column column = new Column(this, withDesign);
		column.countColumnReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness);
		/*
		 * double e1 = (Math.abs(internalForces.getMomentMmax()) /
		 * Math.abs(internalForces.getNormalnaMmax())); double e2 =
		 * (Math.abs(internalForces.getMomentMmin()) /
		 * Math.abs(internalForces.getNormalnaMmin())); double e3 =
		 * (Math.abs(internalForces.getMomentNmax()) /
		 * Math.abs(internalForces.getNormalnaNmax())); double e4 =
		 * (Math.abs(internalForces.getMomentNmin()) /
		 * Math.abs(internalForces.getNormalnaNmin())); double maxE1 = Math.max(e1, e2);
		 * double maxE2 = Math.max(e3, e4); double maxE = Math.max(maxE1, maxE2);
		 * System.out.println("obliczanie sï¿½upa"); System.out.println(""); ///
		 * kombinacje Mmax,Nodp; Mmin,Nodp; Modp,Nmax; Modp,Nmin
		 * 
		 * Map<Double, Double> combinations = Map.ofEntries( /// entry(key,value) ->
		 * entry(moment, normalna) entry(internalForces.getMomentMmax(),
		 * internalForces.getNormalnaMmax()), entry(internalForces.getMomentMmin(),
		 * internalForces.getNormalnaMmin()), entry(internalForces.getMomentNmax(),
		 * internalForces.getNormalnaNmax()), entry(internalForces.getMomentNmin(),
		 * internalForces.getNormalnaNmin()));
		 * 
		 * HashMap<Double, Double> combinationsMed = new HashMap<Double, Double>(); ///
		 * put(key,value) -> entry(mimoï¿½rï¿½d, moment) combinationsMed.put(e1,
		 * internalForces.getMomentMmax()); combinationsMed.put(e2,
		 * internalForces.getMomentMmin()); combinationsMed.put(e3,
		 * internalForces.getMomentNmax()); combinationsMed.put(e4,
		 * internalForces.getMomentNmin());
		 * 
		 * HashMap<Double, Double> combinationsNed = new HashMap<Double, Double>(); ///
		 * put(key,value) -> entry(mimoï¿½rï¿½d, normalna) combinationsNed.put(e1,
		 * internalForces.getNormalnaMmax()); combinationsNed.put(e2,
		 * internalForces.getNormalnaMmin()); combinationsNed.put(e3,
		 * internalForces.getNormalnaNmax()); combinationsNed.put(e4,
		 * internalForces.getNormalnaNmin());
		 * 
		 * double n0Ed = combinationsNed.get(maxE); // nie jestem pewny czy to dziaï¿½a
		 * double m0Ed = combinationsMed.get(maxE); // nie jestem pewny czy to dziaï¿½a
		 * 
		 * System.err.println("Max mimoï¿½rï¿½d: " + maxE);
		 * System.err.println("Moment z max mimoï¿½rodu to: " + m0Ed + "kNm");
		 * System.err.println("Normalna z max mimoï¿½rodu to: " + n0Ed + "kN"); /* for
		 * (Map.Entry<Double, Double> forces : combinations.entrySet()) {
		 * System.out.println("Moment: " + forces.getKey() + " Normalna: " +
		 * forces.getValue()); }
		 */
		/*
		 * double mimos[][] = { {e1, internalForces.getMomentMmax(),
		 * internalForces.getNormalnaMmax()}, {e2, internalForces.getMomentMmin(),
		 * internalForces.getNormalnaMmin()}, {e3, internalForces.getMomentNmax(),
		 * internalForces.getNormalnaNmax()}, {e4, internalForces.getMomentNmin(),
		 * internalForces.getNormalnaNmin()}, };
		 * 
		 * 
		 * /// dla kazdej kombinacji wykonaj obliczenia // for (Map.Entry<Double,
		 * Double> forces : combinations.entrySet()) {
		 * 
		 * 
		 * if (n0Ed != 0.0) {
		 * 
		 * double reinforcementRatio1 = 0.0; // stopieï¿½ na poczï¿½tku pï¿½tli double
		 * reinforcementRatio2 = 0.0; // stopieï¿½ na koï¿½cu pï¿½tli double
		 * reinforcementRatio3 = 0.0; // stopieï¿½ ktï¿½ry jest ï¿½redniï¿½
		 * arytmetycznï¿½ dwï¿½ch powyï¿½szych do porï¿½wnania // z reinforcementRatio2
		 * // ostatecznym
		 * 
		 * /// Zbrojenie symetryczne
		 * 
		 * double mEd = m0Ed; System.err.println("Moment poczï¿½tkowy: " + mEd); ///
		 * Petla dopoki stopien zbrojenia dobranego i zaprojektowanego jest wieksza ///
		 * rowna 0.1 do { if (reinforcementRatio1 == 0.0) { /// Przyjmij stopien
		 * zbrojenia (0,04 AC) reinforcementRatio1 = 0.04 * dimensions.getAc(); } else
		 * if (reinforcementRatio1 >= 0.04 * dimensions.getAc()) {
		 * System.out.println("Stopieï¿½ zbrojenia przekracza warunek normowy: 0,04 *AC"
		 * ); } else { /// pobierz stopieï¿½ zbrojenia na poczï¿½tku pï¿½tli
		 * reinforcementRatio1 = reinforcementRatio3; }
		 * 
		 * reinforcement.setReinforcementRatio(reinforcementRatio1);
		 * stiffness.setRoS1(reinforcementRatio1); stiffness.setM0Ed(m0Ed);
		 * stiffness.setN0Ed(n0Ed); stiffness.CountNominalStiffness(steel, concrete,
		 * internalForces, dimensions, mEd, n0Ed);
		 * 
		 * /// Metoda nominalnej sztywnosci - nowy moment mEd = stiffness.getmEd();
		 * internalForces.setmEd(mEd);
		 * System.err.println("Moment po nominalnej sztywnoï¿½ci: " + mEd);
		 * 
		 * 
		 * // Obliczenie zbrojenia if (n0Ed > 0) { System.out.println("ï¿½ciskanie ");
		 * columnCompressingForcesSymmetricalReinforcement(concrete, steel, dimensions,
		 * reinforcement, mEd, n0Ed); // pobiera stopieï¿½ zbrojenia na koï¿½cu pï¿½tli
		 * reinforcementRatio2 = reinforcement.getReinforcementRatio();
		 * reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;
		 * 
		 * } else if (n0Ed < 0) { System.out.println("rozciï¿½ganie "); // ROZCIAGANIE ,
		 * W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN // PODSTAWIONE Z + !!!
		 * columnTensilingForcesSymmetricalReinforcement(concrete, steel, dimensions,
		 * reinforcement, mEd, n0Ed); // pobiera stopieï¿½ zbrojenia na koï¿½cu pï¿½tli
		 * reinforcementRatio2 = reinforcement.getReinforcementRatio();
		 * reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;
		 * 
		 * } else break; } while ((Math.min(reinforcementRatio3, reinforcementRatio2) /
		 * Math.max(reinforcementRatio3, reinforcementRatio2)) <= 0.99
		 * 
		 * /* Math.abs((reinforcement.getReinforcementRatio() -
		 * reinforcement.getDegreeOfDesignedSymmetricalReinforcement()) /
		 * reinforcement.getReinforcementRatio()) >= 0.1 );
		 * 
		 * // Zbrojenie niesymetryczne
		 * 
		 * /// Petla dopoki stopien zbrojenia dobranego i zaprojektowanego jest wieksza
		 * /// rowna 0.1
		 * 
		 * do { /// Przyjmij stopien zbrojenia
		 * 
		 * if (reinforcementRatio1 == 0.0) { /// Przyjmij stopien zbrojenia (0,04 AC)
		 * reinforcementRatio1 = 0.04 * dimensions.getAc(); } else if
		 * (reinforcementRatio1 >= 0.04 * dimensions.getAc()) {
		 * System.out.println("Stopieï¿½ zbrojenia przekracza warunek normowy: 0,04 *AC"
		 * ); } else { /// pobierz stopieï¿½ zbrojenia na poczï¿½tku pï¿½tli
		 * reinforcementRatio1 = reinforcementRatio3; }
		 * 
		 * reinforcement.setReinforcementRatio(reinforcementRatio1);
		 * stiffness.setRoS1(reinforcementRatio1);
		 * stiffness.CountNominalStiffness(steel, concrete, internalForces, dimensions,
		 * mEd, n0Ed);
		 * 
		 * /// Metoda nominalnej sztywnosci - nowy moment mEd = stiffness.getmEd();
		 * internalForces.setmEd(mEd); // Obliczenie zbrojenia if (n0Ed > 0) {
		 * System.out.println("ï¿½ciskanie ");
		 * columnCompressingForcesUnSymmetricalReinforcement(concrete, steel,
		 * dimensions, reinforcement, mEd, n0Ed); // pobiera stopieï¿½ zbrojenia na
		 * koï¿½cu pï¿½tli reinforcementRatio2 = reinforcement.getReinforcementRatio();
		 * reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;
		 * 
		 * } else if (n0Ed < 0) { System.out.println("rozciï¿½ganie "); // ROZCIAGANIE ,
		 * W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN // PODSTAWIONE Z + !!!
		 * columnTensilingForcesUnsymmetricalReinforcement(concrete, steel, dimensions,
		 * reinforcement, mEd, n0Ed); // pobiera stopieï¿½ zbrojenia na koï¿½cu pï¿½tli
		 * reinforcementRatio2 = reinforcement.getReinforcementRatio();
		 * reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0; }
		 * else break; } while ((Math.min(reinforcementRatio3, reinforcementRatio2) /
		 * Math.max(reinforcementRatio3, reinforcementRatio2)) <= 0.99 /*
		 * Math.abs((reinforcement.getReinforcementRatio() -
		 * reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement()) /
		 * reinforcement.getReinforcementRatio()) >= 0.1
		 * 
		 * ); }
		 */

	}

}

// }
