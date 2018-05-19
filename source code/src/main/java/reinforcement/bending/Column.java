package reinforcement.bending;

import mainalgorithm.InternalForces;
import mainalgorithm.InternalForces.ForcesCombination;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import mainalgorithm.RequiredReinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class Column extends ClearBendingBeam {

	private double m0Ed;
	private double reinforcementRatio;
	private RequiredReinforcement requiredReinforcement;
	private boolean withDesign;

	public Column(RequiredReinforcement reqReinforcement, boolean withDesign) {
		this.requiredReinforcement = reqReinforcement;
		this.withDesign = withDesign;
	}

	@Override
	protected void setAS1() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setAS2() {
		// TODO Auto-generated method stub

	}

	public void setM0Ed(double mEd) {
		this.m0Ed = mEd;
	}

	public void countColumnReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness) {

		internalForces.countECombinations();
		dimensions.calculateIc();
		dimensions.calculateAc();

		ForcesCombination combination = internalForces.getMaxECombination();
		System.out
				.println("emax " + combination.getE() + " modp " + combination.getM() + " nodp " + combination.getN());

		setM0Ed(combination.getM());

		if (combination.getN() != 0.0) {
			countSymmetricalReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness,
					combination);
			countUnsymmetricalReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness,
					combination);
		}

	}

	public void countSymmetricalReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness,
			ForcesCombination combination) {
		double reinforcementRatio1 = 0.0;
		double reinforcementRatio2 = 0.0;
		double reinforcementRatio3 = 0.0;

		do {
			if (reinforcementRatio1 == 0.0) {
				/// Przyjmij stopien zbrojenia (0,04 AC)
				reinforcementRatio1 = 0.04 * dimensions.getAc();
				System.out.println("\nPrzyjmij stopien zbrojenia (0,04 AC) " + reinforcementRatio1 + " AC "
						+ dimensions.getAc() + "\n");
			} else if (reinforcementRatio1 > 0.04 * dimensions.getAc()) {
				System.out.println("\nStopien zbrojenia przekracza warunek normowy: 0,04 *AC\n");
				reinforcementRatio1 = reinforcementRatio3;
			} else { /// pobierz stpien zbrojenia na poczatku petli
				reinforcementRatio1 = reinforcementRatio3;
				System.out.println("\nWez srednie zbrojenie\n");
			}

			reinforcement.setReinforcementRatio(reinforcementRatio1);
			stiffness.setRoS1(reinforcementRatio1);
			stiffness.setM0Ed(combination.getM());
			stiffness.setN0Ed(combination.getN());
			stiffness.CountNominalStiffness(steel, concrete, internalForces, dimensions, combination.getM(),
					combination.getN());

			/// Metoda nominalnej sztywnosci - nowy moment
			internalForces.setmEd(stiffness.getmEd());
			internalForces.setnEd(combination.getN());
			System.err.println("Moment po nominalnej sztywnosci: " + internalForces.getmEd());

			// Obliczenie zbrojenia
			if (combination.getN() > 0) {
				System.out.println("sciskanie ");
				if (withDesign) {
					requiredReinforcement.columnCompressingForcesSymmetricalReinforcementWithDesign(concrete, steel,
							dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
					// pobiera stpien zbrojenia na koncu petli
				} else {
					requiredReinforcement.columnCompressingForcesSymmetricalReinforcement(concrete, steel, dimensions,
							reinforcement, internalForces.getmEd(), internalForces.getnEd());
					// pobiera stpien zbrojenia na koncu petli
				}
				reinforcementRatio2 = reinforcement.getDegreeOfDesignedSymmetricalReinforcement();
				reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;

			} else if (combination.getN() < 0) {
				System.out.println("rozciaganie ");
				// ROZCIAGANIE ,W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN // PODSTAWIONE Z + !!!
				if (withDesign) {
					requiredReinforcement.columnTensilingForcesSymmetricalReinforcementWithDesign(concrete, steel,
							dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
					// pobiera stopien zbrojenia na koncu petli
				} else {
					requiredReinforcement.columnTensilingForcesSymmetricalReinforcement(concrete, steel, dimensions,
							reinforcement, internalForces.getmEd(), internalForces.getnEd());
				}
				reinforcementRatio2 = reinforcement.getDegreeOfDesignedSymmetricalReinforcement();
				reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;

			} else
				break;

			System.out.println("\nratio1 " + reinforcementRatio1);
			System.out.println("ratio2 " + reinforcementRatio2);
			System.out.println("ratio3 " + reinforcementRatio3);
			System.out.println("licznik " + Math.min(reinforcementRatio3, reinforcementRatio2));
			System.out.println("mianownik " + Math.max(reinforcementRatio3, reinforcementRatio2) + "\n");

		} while ((Math.min(reinforcementRatio3, reinforcementRatio2)
				/ Math.max(reinforcementRatio3, reinforcementRatio2)) <= 0.90);
		// while ((reinforcementRatio1-reinforcementRatio2)/reinforcementRatio1 > 0.1);
	}

	public void countUnsymmetricalReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness,
			ForcesCombination combination) {
		double reinforcementRatio1 = 0.0;
		double reinforcementRatio2 = 0.0;
		double reinforcementRatio3 = 0.0;

		System.out.println("\nZbrojenie niesymetryczne\n");

		do {
			if (reinforcementRatio1 == 0.0) {
				/// Przyjmij stopien zbrojenia (0,04 AC)
				reinforcementRatio1 = 0.04 * dimensions.getAc();
				System.out.println("\nPrzyjmij stopien zbrojenia (0,04 AC) " + reinforcementRatio1 + " AC "
						+ dimensions.getAc() + "\n");
			} else if (reinforcementRatio1 > 0.04 * dimensions.getAc()) {
				System.out.println("\nStopien zbrojenia przekracza warunek normowy: 0,04 *AC\n");
				reinforcementRatio1 = reinforcementRatio3;
			} else { /// pobierz stpien zbrojenia na poczatku petli
				reinforcementRatio1 = reinforcementRatio3;
				System.out.println("\nWez srednie zbrojenie\n");
			}

			reinforcement.setReinforcementRatio(reinforcementRatio1);
			stiffness.setRoS1(reinforcementRatio1);
			stiffness.setM0Ed(combination.getM());
			stiffness.setN0Ed(combination.getN());
			stiffness.CountNominalStiffness(steel, concrete, internalForces, dimensions, combination.getM(),
					combination.getN());

			/// Metoda nominalnej sztywnosci - nowy moment
			internalForces.setmEd(stiffness.getmEd());
			internalForces.setnEd(combination.getN());
			System.err.println("Moment po nominalnej sztywnosci: " + internalForces.getmEd());

			// Obliczenie zbrojenia
			if (combination.getN() > 0) {
				System.out.println("sciskanie ");
				if (withDesign) {
					requiredReinforcement.columnCompressingForcesUnSymmetricalReinforcementWithDesign(concrete, steel,
							dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
					// pobiera stpien zbrojenia na koncu petli
				} else {
					requiredReinforcement.columnCompressingForcesUnSymmetricalReinforcement(concrete, steel, dimensions,
							reinforcement, internalForces.getmEd(), internalForces.getnEd());
					// pobiera stpien zbrojenia na koncu petli
				}
				reinforcementRatio2 = reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement();
				reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;

			} else if (combination.getN() < 0) {
				System.out.println("rozciaganie ");
				if (withDesign) {
					// ROZCIAGANIE ,W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN // PODSTAWIONE Z + !!!
					requiredReinforcement.columnTensilingForcesUnsymmetricalReinforcementWithDesign(concrete, steel,
							dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
					// pobiera stopien zbrojenia na koncu/ petli
				} else {
					requiredReinforcement.columnTensilingForcesUnsymmetricalReinforcement(concrete, steel, dimensions,
							reinforcement, internalForces.getmEd(), internalForces.getnEd());
					// pobiera stopien zbrojenia na koncu/ petli
				}
				reinforcementRatio2 = reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement();
				reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;

			} else
				break;

			System.out.println("\nratio1 " + reinforcementRatio1);
			System.out.println("ratio2 " + reinforcementRatio2);
			System.out.println("ratio3 " + reinforcementRatio3);
			System.out.println("licznik " + Math.min(reinforcementRatio3, reinforcementRatio2));
			System.out.println("mianownik " + Math.max(reinforcementRatio3, reinforcementRatio2) + "\n");

		} while ((Math.min(reinforcementRatio3, reinforcementRatio2)
				/ Math.max(reinforcementRatio3, reinforcementRatio2)) <= 0.90);
		// while ((reinforcementRatio1-reinforcementRatio2)/reinforcementRatio1 > 0.1);
	}

}
