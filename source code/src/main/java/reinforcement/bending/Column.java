package reinforcement.bending;

import java.util.ArrayList;

import SLS.creepCoeficent.CreepCoeficent;
import ch.qos.logback.core.net.SyslogOutputStream;
import javafx.scene.control.CheckBox;
import mainalgorithm.InternalForces;
import mainalgorithm.ForcesCombination;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import mainalgorithm.RequiredReinforcement;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class Column extends ClearBendingBeam {

	private double m0Ed;
	private double reinforcementRatio;
	private RequiredReinforcement requiredReinforcement;
	private boolean withDesign;
	private double aS1 = 0;
	private double aS2 = 0;
	private static final double MAX_REINFORCEMENT_DEGREE = 0.08;

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
	
	private void checkInputForces(InternalForces internalForces) {
		if (internalForces.getMomentMmax() != 0 || internalForces.getNormalnaMmax() != 0
				|| internalForces.getMomentMmin() != 0 || internalForces.getNormalnaMmin() != 0
				|| internalForces.getMomentNmax() != 0 || internalForces.getNormalnaNmax() != 0
				|| internalForces.getMomentNmin() != 0 || internalForces.getNormalnaNmin() != 0) {
			internalForces.countECombinations(internalForces.getCombinations());
		} else {
			internalForces.setMedCombination();
		}
	}
	
	public void prepareForNominalStiffness(DimensionsOfCrossSectionOfConcrete dimensions) {
		dimensions.calculateIc();
		dimensions.calculateAc();
	}
	
	public void setM0EdWhenMIsNegative(ForcesCombination combination) {
		if (combination.isMedNegativ())
			setM0Ed(-combination.getM());
		else
			setM0Ed(combination.getM());
	}
	
	private void setMAfterNomialStiffness(ForcesCombination combination, CheckBox checkbox, 
			Reinforcement reinforcement, NominalStiffness stiffness) {
		if (checkbox.isSelected() && combination.getN() > 0
				&& reinforcement.getDegreeOfDesignedSymmetricalReinforcement() < MAX_REINFORCEMENT_DEGREE)
			combination.setmStiff(stiffness.getmEd());
		else
			combination.setmStiff(m0Ed);
	}
	
	
	public void prepareCombinationsCountRequireReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness,
			Cement cement, CreepCoeficent creep, CheckBox checkbox) {
		
		checkInputForces(internalForces);
		prepareForNominalStiffness(dimensions);
		for (ForcesCombination combination : internalForces.getCombinations()) {
			setM0EdWhenMIsNegative(combination);
			double tempM = internalForces.getmEd();
			double tempN = internalForces.getnEd();
			setCombintionToInternalForces(internalForces, combination);
			
			if (combination.getN() != 0.0) {

				countSymmetricalReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness,
						combination, cement, creep, checkbox);
				countUnsymmetricalReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness,
						combination, cement, creep, checkbox);

				setMAfterNomialStiffness(combination, checkbox, reinforcement, stiffness);
			} else {
				requiredReinforcement.rectangularColumnBeamBendingReinforcementWithDesign(concrete, steel, dimensions,
						reinforcement, m0Ed);
				System.err.println("Belka prostokatna, zginanie");

				internalForces.setmEd(combination.getM());

			}
			
			setForcesForInternalForces(internalForces, tempM, tempN);
			
			combination.setaS1prov(reinforcement.getDesingedUnsymmetricalAS1());
			combination.setaS2prov(reinforcement.getDesignedUnsymmetricalAS2());
			combination.setAsSymmetricalProv(reinforcement.getDesignedSymmetricalAS1());
			combination.setAsSymmetricalReq(reinforcement.getRequiredSymmetricalAS1());
			combination.setaS1req(reinforcement.getRequiredUnsymmetricalAS1());
			combination.setaS2req(reinforcement.getRequiredUnsymmetricalAS2());
		}
		
	}
	
	
	public void setCombintionToInternalForces(InternalForces forces, ForcesCombination combination) {
		if (combination.isMedNegativ())
			setForcesForInternalForces(forces, -combination.getM(), combination.getN());
		else setForcesForInternalForces(forces, combination.getM(), combination.getN());
	}

	
	public void setForcesForInternalForces(InternalForces forces, double m, double n) {
		forces.setmEd(m);
		forces.setnEd(n);
	}
	


	public void countColumnReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness,
			Cement cement, CreepCoeficent creep, CheckBox checkbox) {

		if (internalForces.getMomentMmax() != 0 || internalForces.getNormalnaMmax() != 0
				|| internalForces.getMomentMmin() != 0 || internalForces.getNormalnaMmin() != 0
				|| internalForces.getMomentNmax() != 0 || internalForces.getNormalnaNmax() != 0
				|| internalForces.getMomentNmin() != 0 || internalForces.getNormalnaNmin() != 0) {
			internalForces.countECombinations(internalForces.getCombinations());
		} else {
			internalForces.setMedCombination();
		}
		dimensions.calculateIc();
		dimensions.calculateAc();

		ForcesCombination combination = internalForces.getMaxECombination();
		System.out
				.println("emax " + combination.getE() + " modp " + combination.getM() + " nodp " + combination.getN());

		if (combination.isMedNegativ())
			setM0Ed(-combination.getM());
		else
			setM0Ed(combination.getM());

		if (combination.getN() != 0.0) {

			countSymmetricalReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness,
					combination, cement, creep, checkbox);
			countUnsymmetricalReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness,
					combination, cement, creep, checkbox);

			if (checkbox.isSelected() && combination.getN() > 0
					&& reinforcement.getDegreeOfDesignedSymmetricalReinforcement() < 0.08)
				combination.setmStiff(stiffness.getmEd());
			else
				combination.setmStiff(m0Ed);
		} else {
			requiredReinforcement.rectangularColumnBeamBendingReinforcementWithDesign(concrete, steel, dimensions,
					reinforcement, m0Ed);
			System.err.println("Belka prostokatna, zginanie");

			internalForces.setmEd(combination.getM());

		}

	}

	public void countColumnReinforcementDiagnosis(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness,
			Cement cement, CreepCoeficent creep, CheckBox checkbox) {

		if (internalForces.getMomentMmax() != 0 || internalForces.getNormalnaMmax() != 0
				|| internalForces.getMomentMmin() != 0 || internalForces.getNormalnaMmin() != 0
				|| internalForces.getMomentNmax() != 0 || internalForces.getNormalnaNmax() != 0
				|| internalForces.getMomentNmin() != 0 || internalForces.getNormalnaNmin() != 0) {
			internalForces.countECombinations(internalForces.getCombinationDiagnosis());
		} else {
			internalForces.setMedCombination();
		}
		dimensions.calculateIc();
		dimensions.calculateAc();

		ArrayList<ForcesCombination> combination = internalForces.getCombinationDiagnosis();
		// System.out.println("emax " + combination.getE() + " modp " +
		// combination.getM() + " nodp " + combination.getN());

		for (ForcesCombination combination1 : combination) {
			if (combination1.isMedNegativ())
				setM0Ed(-combination1.getM());
			else
				setM0Ed(combination1.getM());

			if (combination1.getN() != 0.0) {

				countSymmetricalReinforcement(concrete, steel, internalForces, dimensions, reinforcement, stiffness,
						combination1, cement, creep, checkbox);
				/*
				countUnsymmetricalReinforcement(concrete, steel, internalForces, dimensions,
				reinforcement, stiffness, combination1, cement, creep, checkbox);
				*/
				if (checkbox.isSelected() && combination1.getN() >= 0
						&& reinforcement.getDegreeOfDesignedSymmetricalReinforcement() < 0.08)
					combination1.setmStiff(stiffness.getmEd());
				else
					combination1.setmStiff(m0Ed);
			} else {
				requiredReinforcement.rectangularColumnBeamBendingReinforcement(concrete, steel, dimensions,
						reinforcement, m0Ed);
				System.err.println("Belka prostokatna, zginanie");
				internalForces.setmEd(combination1.getM());
			}

		}

	}

	int i = 0;

	public void countSymmetricalReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness,
			ForcesCombination combination, Cement cement, CreepCoeficent creep, CheckBox checkbox) {
		double reinforcementRatio1 = 0.0;
		double reinforcementRatio2 = 0.0;
		double reinforcementRatio3 = 0.0;

		if (combination.getN() > 0) {

			if (withDesign) {
				requiredReinforcement.columnCompressingForcesSymmetricalReinforcementWithDesign(concrete, steel,
						dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
				// pobiera stpien zbrojenia na koncu petli
			} else {
				requiredReinforcement.columnCompressingForcesSymmetricalReinforcement(concrete, steel, dimensions,
						reinforcement, internalForces.getmEd(), internalForces.getnEd());
				// pobiera stpien zbrojenia na koncu petli
			}

			reinforcementRatio1 = (0.08 + reinforcement.getDegreeOfDesignedSymmetricalReinforcement()) / 2;
			reinforcementRatio3 = (0.08 + reinforcement.getDegreeOfDesignedSymmetricalReinforcement()) / 2;
			

			do {
				if (reinforcementRatio1 == 0.0) {
					reinforcementRatio1 = 0.08;
					System.out.println("\nPrzyjmij stopien zbrojenia (0,04 AC)/AC ");
					//reinforcement.setDegreeExceededSymmetrical(false);

				} else if (reinforcementRatio1 > 0.08) {
					System.out.println("\nStopien zbrojenia przekracza warunek normowy: 0,04 *AC\n");
					//reinforcement.setDegreeExceededSymmetrical(true);
					reinforcementRatio1 = reinforcementRatio3;
					// reinforcementRatio1 = 0.08;

				} else { /// pobierz stpien zbrojenia na poczatku petli
					reinforcementRatio1 = reinforcementRatio3;
					System.out.println("\nWez srednie zbrojenie\n");
					//reinforcement.setDegreeExceededSymmetrical(false);
				}

				double Ro = reinforcement.getDegreeOfDesignedSymmetricalReinforcement();
				System.out.println("Moment: " + internalForces.getmEd());
				System.out.println("Ro: " + Ro);
				
				aS1 = reinforcement.getDesignedSymmetricalAS1();
				aS2 = reinforcement.getDesignedSymmetricalAS2();
				
				reinforcement.setReinforcementRatio(reinforcementRatio1);
				stiffness.setRoS1(reinforcementRatio1);
				stiffness.setM0Ed(m0Ed);
				stiffness.setN0Ed(combination.getN());
				if (internalForces.getMomentMmax() != 0 || internalForces.getNormalnaMmax() != 0
						|| internalForces.getMomentMmin() != 0 || internalForces.getNormalnaMmin() != 0
						|| internalForces.getMomentNmax() != 0 || internalForces.getNormalnaNmax() != 0
						|| internalForces.getMomentNmin() != 0 || internalForces.getNormalnaNmin() != 0) {

					if (checkbox.isSelected() && combination.getN() >= 0
							&& reinforcement.getDegreeOfDesignedSymmetricalReinforcement() < 0.08) {
						stiffness.CountNominalStiffness(steel, concrete, internalForces, dimensions, m0Ed,
								combination.getN(), cement, creep, aS1, aS2, checkbox);
						internalForces.setnCritSymmetrical(stiffness.getnB());
						if (stiffness.isnBExceeded()) {
							internalForces.setmEd(9999999.9);
							internalForces.setnEd(combination.getN());
						} else {
							internalForces.setmEd(stiffness.getmEd());
							internalForces.setnEd(combination.getN());
						}
						System.err.println("Moment po nominalnej sztywnosci: " + internalForces.getmEd());
					} else {
						internalForces.setmEd(m0Ed);
						internalForces.setnEd(combination.getN());
						System.err.println("Moment bez nominalnej sztywnosci: " + internalForces.getmEd());
					}
				} else {
					if (checkbox.isSelected() && combination.getN() >= 0
							&& reinforcement.getDegreeOfDesignedSymmetricalReinforcement() < 0.08) {
						stiffness.CountNominalStiffness(steel, concrete, internalForces, dimensions, m0Ed,
								combination.getN(), cement, creep, aS1, aS2, checkbox);
						internalForces.setnCritSymmetrical(stiffness.getnB());
						if (stiffness.isnBExceeded()) {
							internalForces.setmEd(9999999.9);
							internalForces.setnEd(combination.getN());
						} else {
							/// Metoda nominalnej sztywnosci - nowy moment
							internalForces.setmEd(stiffness.getmEd());
							internalForces.setnEd(combination.getN());
						}
						System.out.println("Moment po nominalnej sztywnosci: " + internalForces.getmEd());
					} else {
						internalForces.setmEd(internalForces.getM0Ed());
						internalForces.setnEd(combination.getN());
						System.out.println("Moment bez nominalnej sztywnosci: " + internalForces.getmEd());
					}
				}
				
				if (checkbox.isSelected()) {
					if (reinforcementRatio1 > 0.08 || reinforcementRatio2 > 0.08 || reinforcementRatio2 > 0.08) {
						internalForces.setmEd(m0Ed);
						combination.setmStiff(m0Ed);
						stiffness.setmEd(m0Ed);
						stiffness.setAborted(true);
						reinforcement.setDegreeExceededSymmetrical(true);
						System.out.println("!!! Zalecane zwiekszenie przekroju !!!");
					} else {
						stiffness.setAborted(false);
						reinforcement.setDegreeExceededSymmetrical(false);
					}
				} else {
					stiffness.setAborted(false);
					reinforcement.setDegreeExceededSymmetrical(false);
				}
				// Obliczenie zbrojenia
				if (combination.getN() > 0) {
					System.out.println("sciskanie ");
					if (withDesign) {
						requiredReinforcement.columnCompressingForcesSymmetricalReinforcementWithDesign(concrete, steel,
								dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
						// pobiera stpien zbrojenia na koncu petli
					} else {
						requiredReinforcement.columnCompressingForcesSymmetricalReinforcement(concrete, steel,
								dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
						// pobiera stpien zbrojenia na koncu petli
					}
					reinforcementRatio2 = reinforcement.getDegreeOfDesignedSymmetricalReinforcement();
					reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;

					System.out.println("");
					System.out.println("*** Porównanie stopni zbrojenia ***");
					System.out.println("Pêtla numer: " + i);
					System.out.println("Stopieñ porównawczy: " + reinforcementRatio2);
					System.out.println("Stopieñ do porównania: " + reinforcementRatio1);

				}
				/*
				 * System.out.println("\nratio1 " + reinforcementRatio1);
				 * System.out.println("ratio2 " + reinforcementRatio2);
				 * System.out.println("ratio3 " + reinforcementRatio3);
				 * System.out.println("licznik " + Math.min(reinforcementRatio3,
				 * reinforcementRatio2)); System.out.println("mianownik " +
				 * Math.max(reinforcementRatio3, reinforcementRatio2) + "\n");
				 */
				if (checkbox.isSelected()) {
					if (reinforcementRatio1 > 0.08 || reinforcementRatio2 > 0.08 || reinforcementRatio2 > 0.08) {
						internalForces.setmEd(m0Ed);
						combination.setmStiff(m0Ed);
						stiffness.setmEd(m0Ed);
						stiffness.setAborted(true);
						reinforcement.setDegreeExceededSymmetrical(true);
						System.out.println("!!! Zalecane zwiekszenie przekroju !!!");
					} else {
						stiffness.setAborted(false);
						reinforcement.setDegreeExceededSymmetrical(false);
					}
				} else {
					stiffness.setAborted(false);
					reinforcement.setDegreeExceededSymmetrical(false);
				}
			} while (((Math.min(reinforcementRatio3, reinforcementRatio2)
					/ Math.max(reinforcementRatio3, reinforcementRatio2)) <= 0.95) && reinforcementRatio1 <= 0.08
					&& reinforcementRatio2 <= 0.08 && reinforcementRatio3 <= 0.08);

			// while ((reinforcementRatio1-reinforcementRatio2)/reinforcementRatio1 > 0.1);
			System.out.println("");
		}
		if (combination.getN() > 0) {
			System.out.println("sciskanie ");
			if (stiffness.isnBExceeded()) {
				internalForces.setmEd(9999999.9);
				internalForces.setnEd(combination.getN());
			}
			if (withDesign) {
				requiredReinforcement.columnCompressingForcesSymmetricalReinforcementWithDesign(concrete, steel,
						dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
				// pobiera stpien zbrojenia na koncu petli
			} else {
				requiredReinforcement.columnCompressingForcesSymmetricalReinforcement(concrete, steel, dimensions,
						reinforcement, internalForces.getmEd(), internalForces.getnEd());
				// pobiera stpien zbrojenia na koncu petli
			}
		} else if (combination.getN() < 0) {

			System.out.println("rozciaganie ");
			// ROZCIAGANIE ,W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN // PODSTAWIONE Z + !!!
			if (withDesign) {
				requiredReinforcement.columnTensilingForcesSymmetricalReinforcementWithDesign(concrete, steel,
						dimensions, reinforcement, internalForces.getmEd(), Math.abs(internalForces.getnEd()));
				// pobiera stopien zbrojenia na koncu petli
			} else {
				requiredReinforcement.columnTensilingForcesSymmetricalReinforcement(concrete, steel, dimensions,
						reinforcement, internalForces.getmEd(), Math.abs(internalForces.getnEd()));
			}
			reinforcementRatio2 = reinforcement.getDegreeOfDesignedSymmetricalReinforcement();
			reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;
		} else {
			System.out.println("N rowne 0 ");
		}

	}

	public void countUnsymmetricalReinforcement(Concrete concrete, Steel steel, InternalForces internalForces,
			DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, NominalStiffness stiffness,
			ForcesCombination combination, Cement cement, CreepCoeficent creep, CheckBox checkbox) {
		double reinforcementRatio1 = 0.0;
		double reinforcementRatio2 = 0.0;
		double reinforcementRatio3 = 0.0;

		System.out.println("\nZbrojenie niesymetryczne\n");

		if (combination.getN() > 0) {
			if (stiffness.isnBExceeded()) {
				internalForces.setmEd(9999999.9);
				internalForces.setnEd(combination.getN());
			}
			if (withDesign) {
				requiredReinforcement.columnCompressingForcesUnSymmetricalReinforcementWithDesign(concrete, steel,
						dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
				// pobiera stpien zbrojenia na koncu petli
			} else {
				requiredReinforcement.columnCompressingForcesUnSymmetricalReinforcement(concrete, steel, dimensions,
						reinforcement, internalForces.getmEd(), internalForces.getnEd());

			}

			reinforcementRatio1 = (0.08 + reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement()) / 2;
			reinforcementRatio3 = (0.08 + reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement()) / 2;

			do {
				if (reinforcementRatio1 == 0.0) {
					reinforcementRatio1 = 0.08;
					System.out.println("\nPrzyjmij stopien zbrojenia (0,04 AC)/AC ");
					//reinforcement.setDegreeExceededUnsymmetrical(false);

				} else if (reinforcementRatio1 > 0.08) {
					System.out.println("\nStopien zbrojenia przekracza warunek normowy: 0,04 *AC\n");
					//reinforcement.setDegreeExceededUnsymmetrical(true);
					reinforcementRatio1 = reinforcementRatio3;
					// reinforcementRatio1 = 0.08;

				} else { /// pobierz stpien zbrojenia na poczatku petli
					reinforcementRatio1 = reinforcementRatio3;
					System.out.println("\nWez srednie zbrojenie\n");
					//reinforcement.setDegreeExceededUnsymmetrical(false);
				}

				reinforcement.setReinforcementRatio(reinforcementRatio1);
				
				if(withDesign) {
					aS1 = reinforcement.getDesingedUnsymmetricalAS1();
					aS2 = reinforcement.getDesignedUnsymmetricalAS2();
				} else {
					aS1 = reinforcement.getDesignedSymmetricalAS1();
					aS2 = reinforcement.getDesignedSymmetricalAS2();
				}
				
				stiffness.setRoS1(reinforcementRatio1);
				stiffness.setM0Ed(m0Ed);
				stiffness.setN0Ed(combination.getN());

				if (internalForces.getMomentMmax() != 0 || internalForces.getNormalnaMmax() != 0
						|| internalForces.getMomentMmin() != 0 || internalForces.getNormalnaMmin() != 0
						|| internalForces.getMomentNmax() != 0 || internalForces.getNormalnaNmax() != 0
						|| internalForces.getMomentNmin() != 0 || internalForces.getNormalnaNmin() != 0) {

					if (checkbox.isSelected() && combination.getN() >= 0
							&& reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement() < 0.08) {
						stiffness.CountNominalStiffness(steel, concrete, internalForces, dimensions, m0Ed,
								combination.getN(), cement, creep, aS1, aS2, checkbox);
						internalForces.setnCritUnsymmetrical(stiffness.getnB());
						/// Metoda nominalnej sztywnosci - nowy moment
						if (stiffness.isnBExceeded()) {
							internalForces.setmEd(9999999.9);
							internalForces.setnEd(combination.getN());
						} else {
							/// Metoda nominalnej sztywnosci - nowy moment
							internalForces.setmEd(stiffness.getmEd());
							internalForces.setnEd(combination.getN());
						}
						System.err.println("Moment po nominalnej sztywnosci: " + internalForces.getmEd());
					} else {
						internalForces.setmEd(m0Ed);
						internalForces.setnEd(combination.getN());
						System.err.println("Moment bez nominalnej sztywnosci: " + internalForces.getmEd());
					}
				} else {

					if (checkbox.isSelected() && combination.getN() >= 0
							&& reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement() < 0.08) {
						stiffness.CountNominalStiffness(steel, concrete, internalForces, dimensions, m0Ed,
								combination.getN(), cement, creep, aS1, aS2, checkbox);
						internalForces.setnCritUnsymmetrical(stiffness.getnB());
						/// Metoda nominalnej sztywnosci - nowy moment
						if (stiffness.isnBExceeded()) {
							internalForces.setmEd(9999999.9);
							internalForces.setnEd(combination.getN());
						} else {
							internalForces.setmEd(stiffness.getmEd());
							internalForces.setnEd(combination.getN());
						}
						System.err.println("Moment po nominalnej sztywnosci: " + internalForces.getmEd());
					} else {
						internalForces.setmEd(internalForces.getM0Ed());
						internalForces.setnEd(combination.getN());
						System.err.println("Moment bez nominalnej sztywnosci: " + internalForces.getmEd());
					}

				}
				
				if (checkbox.isSelected()) {
					if (reinforcementRatio1 > 0.08 || reinforcementRatio2 > 0.08 || reinforcementRatio2 > 0.08) {
						internalForces.setmEd(m0Ed);
						combination.setmStiff(m0Ed);
						stiffness.setmEd(m0Ed);
						stiffness.setAborted(true);
						reinforcement.setDegreeExceededUnsymmetrical(true);
						System.out.println("!!! Zalecane zwiekszenie przekroju !!!");
					} else {
						stiffness.setAborted(false);
						reinforcement.setDegreeExceededUnsymmetrical(false);
					}
				} else {
					stiffness.setAborted(false);
					reinforcement.setDegreeExceededUnsymmetrical(false);
				}
				
				// Obliczenie zbrojenia
				if (combination.getN() > 0) {
					if (stiffness.isnBExceeded()) {
						internalForces.setmEd(9999999.9);
						internalForces.setnEd(combination.getN());
					}
					System.out.println("sciskanie ");
					if (withDesign) {
						requiredReinforcement.columnCompressingForcesUnSymmetricalReinforcementWithDesign(concrete,
								steel, dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
						// pobiera stpien zbrojenia na koncu petli
					} else {
						requiredReinforcement.columnCompressingForcesUnSymmetricalReinforcement(concrete, steel,
								dimensions, reinforcement, internalForces.getmEd(), internalForces.getnEd());
						// pobiera stpien zbrojenia na koncu petli
					}
					reinforcementRatio2 = reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement();
					reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;

				} /*
					 * else if (combination.getN() < 0) { System.out.println("rozciaganie "); if
					 * (withDesign) { // ROZCIAGANIE ,W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN //
					 * PODSTAWIONE Z + !!! requiredReinforcement.
					 * columnTensilingForcesUnsymmetricalReinforcementWithDesign(concrete, steel,
					 * dimensions, reinforcement, internalForces.getmEd(),
					 * Math.abs(internalForces.getnEd())); // pobiera stopien zbrojenia na koncu/
					 * petli } else {
					 * requiredReinforcement.columnTensilingForcesUnsymmetricalReinforcement(
					 * concrete, steel, dimensions, reinforcement, internalForces.getmEd(),
					 * Math.abs(internalForces.getnEd())); // pobiera stopien zbrojenia na koncu/
					 * petli } reinforcementRatio2 =
					 * reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement();
					 * reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;
					 * 
					 * }
					 */
				else
					break;

				System.out.println("\nratio1 " + reinforcementRatio1);
				System.out.println("ratio2 " + reinforcementRatio2);
				System.out.println("ratio3 " + reinforcementRatio3);
				System.out.println("licznik " + Math.min(reinforcementRatio3, reinforcementRatio2));
				System.out.println("mianownik " + Math.max(reinforcementRatio3, reinforcementRatio2) + "\n");
				
			} while (((Math.min(reinforcementRatio3, reinforcementRatio2)
					/ Math.max(reinforcementRatio3, reinforcementRatio2)) <= 0.95) && reinforcementRatio1 <= 0.08
					&& reinforcementRatio2 <= 0.08 && reinforcementRatio3 <= 0.08);
			// while ((reinforcementRatio1-reinforcementRatio2)/reinforcementRatio1 > 0.1);

			if (combination.getN() > 0) {
				if (stiffness.isnBExceeded()) {
					internalForces.setmEd(9999999.9);
					internalForces.setnEd(combination.getN());
				}
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
			}

		}
		if (combination.getN() > 0) {
			if (stiffness.isnBExceeded()) {
				internalForces.setmEd(9999999.9);
				internalForces.setnEd(combination.getN());
			}
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
		} else if (combination.getN() < 0) {
			System.out.println("rozciaganie ");
			if (withDesign) {
				// ROZCIAGANIE ,W GUI WPROWADZONE Z MINUSEM ALE DO ROWNAN // PODSTAWIONE Z + !!!
				requiredReinforcement.columnTensilingForcesUnsymmetricalReinforcementWithDesign(concrete, steel,
						dimensions, reinforcement, internalForces.getmEd(), Math.abs(internalForces.getnEd()));
				// pobiera stopien zbrojenia na koncu/ petli
			} else {
				requiredReinforcement.columnTensilingForcesUnsymmetricalReinforcement(concrete, steel, dimensions,
						reinforcement, internalForces.getmEd(), Math.abs(internalForces.getnEd()));
				// pobiera stopien zbrojenia na koncu/ petli
			}
			reinforcementRatio2 = reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement();
			reinforcementRatio3 = (reinforcementRatio1 + reinforcementRatio2) / 2.0;

		} else {
			System.out.println("N rowne 0 ");
		}

	}
}
