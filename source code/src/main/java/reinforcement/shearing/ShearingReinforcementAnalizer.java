package reinforcement.shearing;

import java.util.ArrayList;

import mainalgorithm.ForcesCombination;
//import diagnosis.ShearingDiagnosis;
import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.ResultsToPDF;

public class ShearingReinforcementAnalizer {

	private double vRdMax;
	protected final double alfaCw = 1;
	protected double z;
	protected double ny1; 
	protected double cotTheta;
	protected double tanTheta;
	protected double sinAlfa;
	
	protected double cotAlfa;

	protected double vRDC;
	private final double CRDC = 0.129;
	private double k;
	private final double K1 = 0.15;
	private double roMin1;
	private double nyMin;
	private double sigmaCp;

	protected double aSw2;
	protected double aSw1;
	private double lW;
	protected double s;
	protected double s1;
	protected double s2;
	protected double sLMax;

	protected double roW;
	protected double vRd;
	protected double vRdS1;
	protected double vRdS2;
	protected double v1;
	protected double sBMax;
	protected double roWMin;


	////nowe
	protected double deltaV;
	protected double sMin;
	protected double a;
	protected double vRdS;
	



	public void doFullShearingReinforcementAnalysis(Concrete concrete, Steel steel,
		InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		calculateCotTheta(reinforcement.getTheta());
		calculateTanTheta(reinforcement.getTheta());
		calculateSinAlfa(reinforcement.getAlfa());
		calculateCotAlfa(reinforcement.getAlfa());
		this.s1 = 0.0;
		this.s2 = reinforcement.getS2Required();
		calculateSBMax(dimensions);
		if(s2 > sBMax) {
			s2 = sBMax;
			reinforcement.setS2Required(sBMax);
		}
		setVRDC(concrete, forces, dimensions, reinforcement);
		setVRdMax(concrete, dimensions, reinforcement);
		maxValueWhichCanBeSustainedByTheYieldingShearReinforcement(steel, forces, reinforcement, dimensions, concrete);
		ResultsToPDF.addResults("vRdC", String.valueOf(vRDC));
		ResultsToPDF.addResults("vRdMax", String.valueOf(vRdMax));
		reinforcement.setS1Required(s1);
		reinforcement.setS(s1);
		System.out.println("Przekazana wartosc VEd = " + forces.getvEd());
		System.out.println("Przekazana wartosc VEdRed = " + forces.getvEdRed());
		setVrdDiagnosis(steel, reinforcement.getS1Designed(), reinforcement.getS2Designed());
		forces.setvRdCdesign(vRDC);
		forces.setvRdSdesign(vRdS);
		forces.setvRdMaxdesign(vRdMax);
		forces.setVrds1design(vRdS1);
		forces.setVrds2design(vRdS2);
		forces.setSigmaCP(sigmaCp);
	}
	
		
	public void doFullShearingReinforcementAnalysisDiagnosis(Concrete concrete, Steel steel,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		calculateCotTheta(reinforcement.getTheta());
		calculateTanTheta(reinforcement.getTheta());
		calculateSinAlfa(reinforcement.getAlfa());
		calculateCotAlfa(reinforcement.getAlfa());
		this.s2 = reinforcement.getS2Required();
		calculateSBMax(dimensions);
		if(s2 > sBMax) {
			s2 = sBMax;
			reinforcement.setS2Required(sBMax);
		}
		setVRDCDiagnosis(concrete, forces, dimensions, reinforcement);
		setVRdMax(concrete, dimensions, reinforcement);
		maxValueWhichCanBeSustainedByTheYieldingShearReinforcement(steel, forces, reinforcement, dimensions, concrete);
		ResultsToPDF.addResults("vRdC", String.valueOf(vRDC));
		ResultsToPDF.addResults("vRdMax", String.valueOf(vRdMax));
		reinforcement.setS1Required(s1);
		reinforcement.setS(s1);
		System.out.println("Przekazana wartosc VEd = " + forces.getvEd());
		System.out.println("Przekazana wartosc VEdRed = " + forces.getvEdRed());
		setVrdDiagnosis(steel, reinforcement.getS1Designed(), reinforcement.getS2Designed());
		forces.setvRdCdiagnosis(vRDC);
		forces.setvRdSdiagnosis(vRdS);
		forces.setvRdMaxdiagnosis(vRdMax);
		forces.setVrds1diagnosis(vRdS1);
		forces.setVrds2diagnosis(vRdS2);
		forces.setSigmaCP(sigmaCp);
		/*
		if (reinforcement.getnS2Required() == 0 && reinforcement.getS2Designed() == 0) {
			System.out.println(" projektowanie zbrojenia poprzecznego bez pretow odgietych");
			DesignOfVerticalStirrupsAndCapacity design = new DesignOfVerticalStirrupsAndCapacity();
			design.designVerticalStirrupsAndCheckCapacity(concrete, steel, forces, dimensions, reinforcement);
		} else {
			System.out.println(" projektowanie zbrojenia poprzecznego oraz pretow odgietych");
			DesignOfVerticalStirrupsBentRodsAndCapacity design = new DesignOfVerticalStirrupsBentRodsAndCapacity();
			design.designVerticalStirrupsBentRodsAndCheckCapacityWhenS2WasGiven(concrete, steel, forces, dimensions, reinforcement);
		}
		*/
	}

	
	public void doFullSheringReinforcementWithoutDesign(Concrete concrete, Steel steel,
		InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement, double s1, double s2) {
		System.out.println("\n \n Scinanie \n \n");
		ResultsToPDF.addResults("Zbrojenie poprzeczne \n\n", "");
		doFullShearingReinforcementAnalysisDiagnosis(concrete, steel, forces, dimensions, reinforcement);
		setVrdDiagnosis(steel, s1, s2);
	}

	public void doFullSheringReinforcementWitDesign(Concrete concrete, Steel steel,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		System.out.println("\n \n Scinanie \n \n");
		ResultsToPDF.addResults("Zbrojenie poprzeczne \n\n", "");
		doFullShearingReinforcementAnalysis(concrete, steel, forces, dimensions, reinforcement);
		designSheringReinforcement(reinforcement);
	}

	protected void setVrdDiagnosis (Steel steel, double s1, double s2) {
		if (s1 != 0.0)
			vRdS1 = (aSw1/s1)*z*steel.getFYd()*1000*cotTheta;
		else vRdS1 = 0.0;
		if (s2 != 0) {
			vRdS2 = (aSw2/s2)*z*steel.getFYd()*1000*(cotTheta+cotAlfa)*sinAlfa;
		} else {
			vRdS2 = 0.0;
		}
		vRdS = Math.min(vRdS1+vRdS2, 2*vRdS1);
		vRd = Math.max(Math.min(vRdS, vRdMax), vRDC);
	}

	private void designSheringReinforcement(Reinforcement reinforcement) {
		if (reinforcement.getnS2Required()>0 && reinforcement.getS2Required()>0) {
			reinforcement.setS2Designed(0.01*(Math.floor(Math.abs(s2/0.01))));
		}
		reinforcement.setS1Designed(0.01*(Math.floor(Math.abs(s1/0.01))));
	}

	protected void pushResultsToReinforcementClass(Reinforcement reinforcement) {
		reinforcement.setlW(lW);
		reinforcement.setS(s);
		reinforcement.setS1Required(s1);
		reinforcement.setS2Required(s2);
	}

	protected void calculateSBMax(DimensionsOfCrossSectionOfConcrete dimensions) {
		sBMax = 0.6*dimensions.getD()*(1+cotAlfa);
	}
	
	protected void calculateCotTheta(double theta) {
		cotTheta = theta;
		System.out.println("cotTheta " + cotTheta);
	}

	protected void calculateDeltaV(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimension) {
		deltaV = (alfaCw * dimension.getB() * z * ny1 * concrete.getFCd() * 1000)*cotTheta*cotAlfa/((1+Math.pow(cotTheta, 2))*(2*cotTheta+cotAlfa));
		System.out.println("deltaV " + deltaV);
	}

	protected void calculateTanTheta(double theta) {
		tanTheta = 1 / cotTheta;
		System.out.println("tanTheta " + tanTheta);
	}

	protected void calculateSinAlfa(double alfa) {
		sinAlfa = Math.sin(alfa * Math.PI / 180);
		System.out.println("sin alfa " + sinAlfa);
	}

	protected void calculateCotAlfa(double alfa) {
		cotAlfa = 1 / Math.tan(alfa * Math.PI / 180);
		System.out.println("cot alfa" + cotAlfa);
	}

	//

	protected void setZ(DimensionsOfCrossSectionOfConcrete dimensions) {
		z = 0.9 * dimensions.getD();
		System.out.println("z " + z);
	}

	protected void setNy1(Concrete concrete) {
		ny1 = 0.6 * (1 - (double) concrete.getFCk() / 250);
		System.out.println("ny1 " + ny1);
	}

	protected void setVRdMax(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setZ(dimensions);
		setNy1(concrete);
		vRdMax = (alfaCw * dimensions.getB() * z * ny1 * concrete.getFCd() * 1000) / (tanTheta + cotTheta);
		if (reinforcement.getnS2Required()>0 && reinforcement.getS2Required()>0) {
			calculateDeltaV(concrete, dimensions);
			vRdMax += deltaV;
			System.out.println("Projektowanie strzemion odgietych!!!!");
		} else
			System.out.println("Projektowanie strzemion pionowych!!!");
		System.out.println("vRdMax " + vRdMax);
	}

	protected double getVRdMax() {
		return vRdMax;
	}

	// shearingResistanceWithoutShearingReinforcement
	// vRdc

	private void setSigmaCpDiagnosis(DimensionsOfCrossSectionOfConcrete dimension, InternalForces forces, Concrete concrete) {
		double nEd = 0;
		if (forces.getMomentMmax() == 0 && forces.getNormalnaMmax() == 0 && forces.getMomentMmin() == 0
				&& forces.getNormalnaMmin() == 0 && forces.getMomentNmax() == 0 && forces.getNormalnaNmax() == 0
						&& forces.getMomentNmin() == 0 && forces.getNormalnaNmin() == 0) {
			nEd = Math.abs(forces.getnEd());
		} else {
			ArrayList<Double> list = new ArrayList<>();
			//Dodane wartosc bezwezgledne
			if(forces.getMomentMmax() != 0 && forces.getNormalnaMmax() != 0) {
				list.add(Math.abs(forces.getNormalnaMmax()));
			}
			if(forces.getMomentMmin() != 0 && forces.getNormalnaMmin() != 0) {
				list.add(Math.abs(forces.getNormalnaMmin()));
			}
			if(forces.getMomentNmax() != 0 && forces.getNormalnaNmax() != 0) {
				list.add(Math.abs(forces.getNormalnaNmax()));
			}
			if(forces.getMomentNmin() != 0 && forces.getNormalnaNmin() != 0) {
				list.add(Math.abs(forces.getNormalnaNmin()));
			}
			
			if (forces.getNormalnaMmax() == 0 && forces.getNormalnaMmin() == 0 &&
					forces.getNormalnaNmax() ==0 && forces.getNormalnaNmin() ==0) {
				nEd = Math.abs(forces.getnEd());
			} else {
			double nMin = list.get(0);
			
			for (int i = 0; i < list.size(); i++) {
				if (nMin >= list.get(i)) {
					nMin = list.get(i);
				}
			}
			list.clear();
			nEd = nMin;
			}
			
		}
		System.out.println("Przyjeto NEd w scinaniu: " + nEd);
		sigmaCp = Math.min(10 * nEd / (dimension.getB() * 100 * dimension.getH() * 100), 0.2*concrete.getFCd());
		System.out.println("sigmaCp " + sigmaCp);

	}
	
	private void setSigmaCp(DimensionsOfCrossSectionOfConcrete dimension, InternalForces forces, Concrete concrete) {
		//dodano wartosc bezwzgledna
		System.out.println("Przyjeto NEd w scinaniu: " + Math.abs(forces.getnEd()));
		sigmaCp = Math.min(10 * Math.abs(forces.getnEd()) / (dimension.getB() * 100 * dimension.getH() * 100), 0.2*concrete.getFCd());
		System.out.println("sigmaCp " + sigmaCp);

	}

	private void setK(DimensionsOfCrossSectionOfConcrete dimensions) {
		k = Math.min(2, 1 + Math.sqrt(200 / (dimensions.getD() * 1000)));
		System.out.println("k " + k);

	}

	private void setRoMin1(Reinforcement reinforcement, DimensionsOfCrossSectionOfConcrete dimensions, double Asl) {
		roMin1 = Math.min(Asl * 10000 / (dimensions.getB() * 100 * dimensions.getD() * 100), 0.02);
		System.out.println("Pole zbrojenia do scinania: " + Asl);
		System.out.println("roMin1 " + roMin1);
	}

	private void setNyMin(Concrete concrete) {
		nyMin = 0.035 * Math.pow(k, (double) 3 / 2) * Math.sqrt(concrete.getFCk());
		System.out.println("nyMin " + nyMin);
	}

	private void setVRDCDependencies(Concrete concrete, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setK(dimensions);
		double Asl;
		if (forces.getmEd()<0)	Asl = reinforcement.getRequiredSymmetricalAS2();
		else Asl = reinforcement.getRequiredSymmetricalAS1();
		setRoMin1(reinforcement, dimensions, Asl);
		setSigmaCp(dimensions, forces, concrete);
		setNyMin(concrete);
	}

	private void setVRDCDependenciesDiagnosis(Concrete concrete, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setK(dimensions);
		double Asl;
		if (forces.getmEd()<0)	Asl = reinforcement.getRequiredSymmetricalAS2();
		else Asl = reinforcement.getRequiredSymmetricalAS1();
		setRoMin1(reinforcement, dimensions, Asl);
		setSigmaCpDiagnosis(dimensions, forces, concrete);
		setNyMin(concrete);
	}
	
	private void setVRDC(Concrete concrete, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setVRDCDependencies(concrete, forces, dimensions, reinforcement);
		vRDC = Math.max((CRDC * k * Math.pow((100 * roMin1 * concrete.getFCk()), (double) 1 / 3) + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000,
				(nyMin + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000);

		vRDC = vRDC / 1000;
		
		if(vRDC <= 0) {
			vRDC = 0;
		}
		
		System.out.println("vRDC " + vRDC);
	}
	
	private void setVRDCDiagnosis(Concrete concrete, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setVRDCDependenciesDiagnosis(concrete, forces, dimensions, reinforcement);
		double power = 1.0/3.0;
		//Co z sila ujemna? wtedy vrdc = 0 ???
		vRDC = Math.max((CRDC * k * Math.pow((100 * roMin1 * concrete.getFCk()), power) + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000,
				(nyMin + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000);

		vRDC = vRDC / 1000;
		
		if(vRDC <= 0) {
			vRDC = 0;
		}
		
		System.out.println("vRDC " + vRDC);
	}

	public double getVRDC() {
		return vRDC;
	}

	// maxValueWhichCanBeSustainedByTheYieldingShearReinforcement
	// Vrdc<Ved<=Vrdmax

	private void maxValueWhichCanBeSustainedByTheYieldingShearReinforcement(Steel steel, InternalForces forces,
			Reinforcement reinforcement, DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete) {
		setASwVerticalStirrup(reinforcement.getnS1(), reinforcement.getaSW1Diameter());
		if (reinforcement.getnS2Required()>0 && reinforcement.getS2Required()>0)
			setASwBentRods(reinforcement.getnS2Required(), reinforcement.getaSW2Diameter());
		setSlMax(dimensions.getD());
		setSMin(steel, dimensions, concrete);
		setS1Algorith(steel, dimensions, concrete, forces, reinforcement);
		//setS(steel, forces.getvEd());
		//setlW(forces);
	}

	private void countS1WhenVedLessThanVrdc(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete) {
		double b = Math.min(sLMax, a);
		s1 = Math.max(b,sMin);
		System.out.println("s1 " + s1);
	}


	private void countS1WhenVedGreaterThanVrdcAndLessVrdMax(Steel steel, InternalForces forces, Reinforcement reinforcement) {
		double Veds1 = 0;
		if (reinforcement.getnS2Required()>0 && reinforcement.getS2Required()>0) {
			double Vrds2 = (aSw2/s2)*z*steel.getFYd()*1000*(cotTheta+cotAlfa)*sinAlfa;
			Veds1 = Math.max(forces.getvEdRed()-Vrds2, 0.5*forces.getvEdRed());
		} else {
			Veds1 = forces.getvEdRed();
		}
		System.out.println("Veds1 " + Veds1);
		double b = aSw1/Veds1*z*steel.getFYd()*1000*cotTheta;
		double c = Math.min(b, sLMax);
		double d = Math.min(c, a);
		s1 = Math.max(d, sMin);
		System.out.println("s1 " + s1);
	}

	protected void setASwVerticalStirrup(double nS1, double phiSt) {
		aSw1 = nS1 * Math.PI * (phiSt / 1000) * (phiSt / 1000) / 4;
		System.out.println("aSw1 " + aSw1);
	}

	protected void setASwBentRods(double nS2, double phiSt) {
		aSw2 = nS2 * Math.PI * (phiSt / 1000) * (phiSt / 1000) / 4;
		System.out.println("aSw2 " + aSw2);
	}

	protected void setSlMax(double d) {
		sLMax = 0.75 * d;
		System.out.println("sLMax " + sLMax);
	}

	protected void setSMin(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions,
			Concrete concrete) {
		//TU SKO�CZYLI�MY
		sMin = 2*aSw1*steel.getFYd()/(dimensions.getB()*alfaCw*ny1*concrete.getFCd());
		System.out.println("sMin " + sMin);
	}

	protected void setS1Algorith(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions,
			Concrete concrete, InternalForces forces, Reinforcement reinforcement) {
		a = aSw1 * steel.getFYk()/(0.08*dimensions.getB()*Math.sqrt(concrete.getFCk()));
		System.out.println("a " + a);
		if (forces.getvEdRed()<= vRDC) {
			System.out.println("Ved < Vrdc");
			countS1WhenVedLessThanVrdc(steel, dimensions, concrete);
		} else if (forces.getvEdRed() > vRDC && forces.getvEd()<= vRdMax) {
			countS1WhenVedGreaterThanVrdcAndLessVrdMax(steel, forces, reinforcement);
		} else {
			s1 = 0.0;
			System.out.println("s1 " + s1);
		}
	}

	protected void setS(Steel steel, double vEd) {

		double a = aSw1 / vEd * z * steel.getFYd() * 1000 * cotTheta;
		s1 = Math.min(a, sLMax);
		System.out.println("s tutaj" + s1);
	}

	private void setlW(InternalForces internalForces) {
		lW = (internalForces.getvEd() - getVRDC()) / internalForces.getgPlusQForShearing();
		System.out.println("lw " + lW);
	}
	
	public double getvRd() {
		return vRd;
	}

	//

	protected void setV1(Concrete concrete) {
		v1 = 0.6 * (1 - (double) concrete.getFCk() / 250);
		System.out.println("v1 " + v1);
	}

	protected void setRoWMin(Concrete concrete, Steel steel) {
		roWMin = 0.08 * Math.sqrt((double) concrete.getFCk() * 1000) / (steel.getFYk() * 1000);
		System.out.println("roWMin " + roWMin);
	}

	


	public double getvRdMax() {
		return vRdMax;
	}

	public double getvRDC() {
		return vRDC;
	}

	public double getvRdS() {
		return vRdS;
	}

	private void pushResultsToPDFWhenS2WasGiven(InternalForces forces) {

		ResultsToPDF.addResults("Ved", String.valueOf(forces.getvEd()));
		ResultsToPDF.addResults("RoWMin", String.valueOf(roWMin));
		ResultsToPDF.addResults("z", String.valueOf(z));
		ResultsToPDF.addResults("Asw1", String.valueOf(aSw1));
		ResultsToPDF.addResults("Asw2", String.valueOf(aSw2));
		ResultsToPDF.addResults("sLMax", String.valueOf(sLMax));
		ResultsToPDF.addResults("sBMax", String.valueOf(sBMax));
		ResultsToPDF.addResults("s2", String.valueOf(s2));
		ResultsToPDF.addResults("vRdS2", String.valueOf(vRdS2));
		//ResultsToPDF.addResults("vEds1", String.valueOf());
		ResultsToPDF.addResults("s1", String.valueOf(s1));
		ResultsToPDF.addResults("vRdS1", String.valueOf(vRdS1));
		ResultsToPDF.addResults("roW", String.valueOf(roW));
	}

}
