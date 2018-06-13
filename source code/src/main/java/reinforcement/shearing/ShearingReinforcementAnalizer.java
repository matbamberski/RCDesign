package reinforcement.shearing;

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
	private double ny1;
	protected double cotTheta;
	protected double tanTheta;
	protected double sinAlfa;
	protected double cotAlfa;

	private double vRDC;
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
	protected double vRdS;
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
		this.s2 = reinforcement.getS2Required();
		setVRDC(concrete, forces, dimensions, reinforcement);
		setVRdMax(concrete, dimensions, reinforcement);
		maxValueWhichCanBeSustainedByTheYieldingShearReinforcement(steel, forces, reinforcement, dimensions, concrete);
		ResultsToPDF.addResults("vRdC", String.valueOf(vRDC));
		ResultsToPDF.addResults("vRdMax", String.valueOf(vRdMax));
		reinforcement.setS1Required(s1);
		reinforcement.setS(s1);
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
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		System.out.println("\n \n Scinanie \n \n");
		ResultsToPDF.addResults("Zbrojenie poprzeczne \n\n", "");
		doFullShearingReinforcementAnalysis(concrete, steel, forces, dimensions, reinforcement);
		setVrdDiagnosis(steel);
	}

	public void doFullSheringReinforcementWitDesign(Concrete concrete, Steel steel,
			InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		System.out.println("\n \n Scinanie \n \n");
		ResultsToPDF.addResults("Zbrojenie poprzeczne \n\n", "");
		doFullShearingReinforcementAnalysis(concrete, steel, forces, dimensions, reinforcement);
		designSheringReinforcement(reinforcement);
	}

	protected void setVrdDiagnosis (Steel steel) {
		vRdS1 = (aSw1/s1)*z*steel.getFYd()*1000*cotTheta;
		vRdS2 = (aSw2/s2)*z*steel.getFYd()*1000*(cotTheta+cotAlfa)*sinAlfa;
		vRdS = Math.min(vRdS1+vRdS2, 2*vRdS1);
		vRd = Math.max(Math.min(vRdS, vRdMax), vRDC);
	}

	private void designSheringReinforcement(Reinforcement reinforcement) {
		if (reinforcement.getnS2Required()>0 && reinforcement.getS2Required()>0) {
			reinforcement.setS2Designed(0.05*(Math.floor(Math.abs(s2/0.05))));
		}
		reinforcement.setS1Designed(0.05*(Math.floor(Math.abs(s1/0.05))));
	}

	protected void pushResultsToReinforcementClass(Reinforcement reinforcement) {
		reinforcement.setlW(lW);
		reinforcement.setS(s);
		reinforcement.setS1Required(s1);
		reinforcement.setS2Required(s2);
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

	private void setNy1(Concrete concrete) {
		ny1 = 0.6 * (1 - (double) concrete.getFCk() / 250);
		System.out.println("ny1 " + ny1);
	}

	private void setVRdMax(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
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

	private void setSigmaCp(DimensionsOfCrossSectionOfConcrete dimension, InternalForces forces, Concrete concrete) {
		sigmaCp = Math.min(10*forces.getnEd() / (dimension.getB() * 100 * dimension.getD() * 100), 0.2*concrete.getFCd());
		System.out.println("sigmaCp " + sigmaCp);

	}

	private void setK(DimensionsOfCrossSectionOfConcrete dimensions) {
		k = Math.min(2, 1 + Math.sqrt(200 / (dimensions.getD() * 1000)));
		System.out.println("k " + k);

	}

	private void setRoMin1(Reinforcement reinforcement, DimensionsOfCrossSectionOfConcrete dimensions) {
		roMin1 = Math.min(reinforcement.getRequiredSymmetricalAS1() * 10000 / (dimensions.getB() * 100 * dimensions.getD() * 100), 0.02);
		System.out.println("Pole zbrojenia do scinania: " + reinforcement.getRequiredSymmetricalAS1());
		System.out.println("roMin1 " + roMin1);
	}

	private void setNyMin(Concrete concrete) {
		nyMin = 0.035 * Math.pow(k, (double) 3 / 2) * Math.sqrt(concrete.getFCk());
		System.out.println("nyMin " + nyMin);
	}

	private void setVRDCDependencies(Concrete concrete, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setK(dimensions);
		setRoMin1(reinforcement, dimensions);
		setSigmaCp(dimensions, forces, concrete);
		setNyMin(concrete);
	}

	private void setVRDC(Concrete concrete, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setVRDCDependencies(concrete, forces, dimensions, reinforcement);
		vRDC = Math.min((CRDC * k * Math.pow((100 * roMin1 * concrete.getFCk()), (double) 1 / 3) + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000,
				(nyMin + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000);

		vRDC = vRDC / 1000;
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
			double Vrds2 = (aSw2/s2)*z*steel.getFYd()*(cotTheta+cotAlfa)*sinAlfa;
			Veds1 = Math.max(forces.getvEd()-Vrds2, 0.5*forces.getvEd());
		} else
			Veds1 = forces.getvEd();
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
		if (forces.getvEd()<= vRDC) {
			System.out.println("Ved < Vrdc");
			countS1WhenVedLessThanVrdc(steel, dimensions, concrete);
		} else if (forces.getvEd() > vRDC && forces.getvEd()<= vRdMax) {
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

	//

	protected void setV1(Concrete concrete) {
		v1 = 0.6 * (1 - (double) concrete.getFCk() / 250);
		System.out.println("v1 " + v1);
	}

	protected void setRoWMin(Concrete concrete, Steel steel) {
		roWMin = 0.08 * Math.sqrt((double) concrete.getFCk() * 1000) / (steel.getFYk() * 1000);
		System.out.println("roWMin " + roWMin);
	}

	protected void setVrdDiagnosis (Steel steel) {
		vRdS1 = (aSw1/s1)*z*steel.getFYd()*1000*cotTheta;
		vRdS2 = (aSw2/s2)*z*steel.getFYd()*1000*(cotTheta+cotAlfa)*sinAlfa;
		vRdS = Math.min(vRdS1+vRdS2, 2*vRdS1);
		vRd = Math.min(vRdS, vRdMax);
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
