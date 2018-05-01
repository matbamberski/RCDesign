package reinforcement.shearing;

import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.ResultsToPDF;

public class ShearingReinforcementAnalizer {

	public void doFullShearingReinforcementAnalysis(Concrete concrete, Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		System.out.println("\n \n Scinanie \n \n");
		ResultsToPDF.addResults("Zbrojenie poprzeczne \n\n", "");
		calculateCotTheta(reinforcement.getTheta());
		calculateTanTheta(reinforcement.getTheta());
		setVRDC(concrete, forces, dimensions, reinforcement);
		setVRdMax(concrete, dimensions);
		maxValueWhichCanBeSustainedByTheYieldingShearReinforcement(steel, forces, reinforcement, dimensions);
		ResultsToPDF.addResults("vRdC", String.valueOf(vRDC));
		ResultsToPDF.addResults("vRdMax", String.valueOf(vRdMax));
		if (reinforcement.getnS2Required() == 0 && reinforcement.getS2Designed() == 0) {
			System.out.println(" projektowanie zbrojenia poprzecznego bez pretow odgietych");
			DesignOfVerticalStirrupsAndCapacity design = new DesignOfVerticalStirrupsAndCapacity();
			design.designVerticalStirrupsAndCheckCapacity(concrete, steel, forces, dimensions, reinforcement);
		} else {
			System.out.println(" projektowanie zbrojenia poprzecznego oraz pretow odgietych");
			DesignOfVerticalStirrupsBentRodsAndCapacity design = new DesignOfVerticalStirrupsBentRodsAndCapacity();
			design.designVerticalStirrupsBentRodsAndCheckCapacityWhenS2WasGiven(concrete, steel, forces, dimensions, reinforcement);
		}
	}

	protected void pushResultsToReinforcementClass(Reinforcement reinforcement) {
		reinforcement.setlW(lW);
		reinforcement.setS(s);
		reinforcement.setS1Required(s1);
		reinforcement.setS2Required(s2);
	}

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
	protected double vRdS1;
	protected double vRdS2;
	protected double v1;
	protected double sBMax;
	protected double roWMin;

	protected void calculateCotTheta(double theta) {
		cotTheta = theta;
		System.out.println("cotTheta " + cotTheta);
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

	private void setVRdMax(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		setZ(dimensions);
		setNy1(concrete);
		vRdMax = (alfaCw * dimensions.getB() * z * ny1 * concrete.getFCd() * 1000) / (tanTheta + cotTheta);
		System.out.println("vRdMax " + vRdMax);
	}

	protected double getVRdMax() {
		return vRdMax;
	}

	// shearingResistanceWithoutShearingReinforcement
	// vRdc

	private void setSigmaCp(DimensionsOfCrossSectionOfConcrete dimension, InternalForces forces) {
		sigmaCp = forces.getnEd() / (dimension.getB() * 100 * dimension.getD() * 100);
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
		setSigmaCp(dimensions, forces);
		setNyMin(concrete);
	}

	private void setVRDC(Concrete concrete, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		setVRDCDependencies(concrete, forces, dimensions, reinforcement);
		vRDC = Math.max((CRDC * k * Math.pow((100 * roMin1 * concrete.getFCk()), (double) 1 / 3) + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000,
				(nyMin + K1 * sigmaCp) * dimensions.getB() * 1000 * dimensions.getD() * 1000);

		vRDC = vRDC / 1000;
		System.out.println("vRDC " + vRDC);
	}

	public double getVRDC() {
		return vRDC;
	}

	// maxValueWhichCanBeSustainedByTheYieldingShearReinforcement
	// Vrdc<Ved<=Vrdmax

	private void maxValueWhichCanBeSustainedByTheYieldingShearReinforcement(Steel steel, InternalForces forces, Reinforcement reinforcement, DimensionsOfCrossSectionOfConcrete dimensions) {
		setASwVerticalStirrup(reinforcement.getnS1(), reinforcement.getaSW1Diameter());
		setSlMax(dimensions.getD());
		setS(steel, forces.getvEd());
		setlW(forces);
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

}
