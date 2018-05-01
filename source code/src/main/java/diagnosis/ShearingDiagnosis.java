package diagnosis;

import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class ShearingDiagnosis {
	private double vRdMax;
	private double z;
	private double tanTheta;
	private double cotTheta;
	private double sinAlfa;
	private double cotAlfa;
	private double aSw2;
	private double aSw1;
	private double vRd;
	private double vRdS1;
	private double vRdS2;
	private double v1;
	private double deltaV;

	public void runShearingDiagnosis(Concrete concrete, Steel steel, Reinforcement reinforcement, DimensionsOfCrossSectionOfConcrete dimensions, double s1, double s2, double n2) {
		calculateTanTheta(reinforcement.getTheta());
		calculateCotTheta();
		calculateSinAlfa(reinforcement.getAlfa());
		calculateCotAlfa(reinforcement.getAlfa());
		calculateV1(concrete);
		calculateZ(dimensions);
		setASwVerticalStirrup(reinforcement.getnS1(), reinforcement.getaSW1Diameter());
		setASwBentRods(reinforcement.getnS2Required(), reinforcement.getaSW2Diameter());
		calculateVRdS1(steel, s1);
		calculateVRdS2(steel, s2, n2);
		calculateDeltaV(dimensions, concrete);
		calculateVRdMax(dimensions, concrete);
		calculateVRd();
	}

	private void calculateVRd() {
		double first = vRdS1 + vRdS2;
		// double second=vRdS1/beta3;
		double third = vRdMax;
		vRd = Math.min(first, third) * 1000;
	}

	private void calculateVRdMax(DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete) {
		vRdMax = dimensions.getB() * z * v1 * concrete.getFCd() / (cotTheta + tanTheta) + deltaV;
	}

	private void calculateDeltaV(DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete) {
		deltaV = dimensions.getB() * z * v1 * concrete.getFCd() * (cotTheta * cotAlfa) / ((1 + cotTheta * cotTheta) * (2 * cotTheta + cotAlfa));
	}

	private void calculateVRdS1(Steel steel, double s1) {
		vRdS1 = aSw1 / s1 * z * steel.getFYd() * cotTheta;
	}

	private void calculateVRdS2(Steel steel, double s2, double n2) {
		if (n2 != 0) {
			vRdS2 = aSw2 / s2 * z * steel.getFYd() * (cotTheta + cotAlfa) * sinAlfa;
		} else {
			vRdS2 = 0;
		}
	}

	protected void calculateTanTheta(double theta) {
		tanTheta = theta;
		System.out.println("tanTheta " + tanTheta);
	}

	protected void calculateCotTheta() {
		cotTheta = 1 / tanTheta;
		System.out.println("cotTheta " + cotTheta);
	}

	protected void calculateSinAlfa(double alfa) {
		sinAlfa = Math.sin(alfa * Math.PI / 180);
		System.out.println("sin alfa " + sinAlfa);
	}

	protected void calculateCotAlfa(double alfa) {
		cotAlfa = 1 / Math.tan(alfa * Math.PI / 180);
		System.out.println("cot alfa" + cotAlfa);
	}

	protected void calculateZ(DimensionsOfCrossSectionOfConcrete dimensions) {
		z = 0.9 * dimensions.getD();
		System.out.println("z " + z);
	}

	public double getvRd() {
		return vRd;
	}

	protected void setASwVerticalStirrup(double nS1, double phiSt) {
		aSw1 = nS1 * Math.PI * (phiSt / 1000) * (phiSt / 1000) / 4;
		System.out.println("aSw1 " + aSw1);
	}

	protected void setASwBentRods(double nS2, double phiSt) {
		aSw2 = nS2 * Math.PI * (phiSt / 1000) * (phiSt / 1000) / 4;
		System.out.println("aSw2 " + aSw2);
	}

	protected void calculateV1(Concrete concrete) {
		v1 = 0.6 * (1 - (double) concrete.getFCk() / 250);
		System.out.println("v1 " + v1);
	}

}
