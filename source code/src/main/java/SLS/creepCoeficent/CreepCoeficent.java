package SLS.creepCoeficent;

import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;

public class CreepCoeficent {

	private double creepCoeficent;

	public double getCreepCoeficent() {
		return creepCoeficent;
	}

	private double t0;
	private double t0T;
	private double alfa1;
	private double alfa2;
	private double alfa3;
	private double phiRh;
	private double phi0;
	private double betaFcm;
	private double betaT0;
	private double betaH;
	private double betaC;
	private double t = Math.pow(10, 10);

	private void calculateT0T(Concrete concrete) {
		t0T = concrete.getT0();

	}

	private void calculateT0(Cement cement) {
		double part1 = t0T * Math.pow((9 / (2 + Math.pow(t0T, 1.2)) + 1), cement.getAlfa());
		t0 = Math.max(part1, 0.5);

	}

	private void calculateAlfa1(Concrete concrete) {
		alfa1 = Math.pow(35 / (double) concrete.getFCm(), 0.7);

	}

	private void calculateAlfa2(Concrete concrete) {
		alfa2 = Math.pow(35 / (double) concrete.getFCm(), 0.2);

	}

	private void calculateAlfa3(Concrete concrete) {
		alfa3 = Math.pow(35 / (double) concrete.getFCm(), 0.5);

	}

	private void calculatePhiRH(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		if (concrete.getFCm() <= 35) {
			phiRh = 1 + (1 - concrete.getrH() / 100) / (0.1 * Math.cbrt(dimensions.getH0() * 1000));
		}
		if (concrete.getFCm() > 35) {
			phiRh = (1 + (1 - concrete.getrH() / 100) / (0.1 * Math.cbrt(dimensions.getH0() * 1000)) * alfa1) * alfa2;
		}

	}

	private void calculateBetaFcm(Concrete concrete) {
		betaFcm = 16.8 / Math.sqrt(concrete.getFCm());

	}

	private void calculateBetaT0() {
		betaT0 = 1 / (0.1 + Math.pow(t0, 0.2));
	}

	private void calculatePhi0() {
		phi0 = phiRh * betaFcm * betaT0;
	}

	private void calculateBetaH(Concrete concrete, DimensionsOfCrossSectionOfConcrete dimensions) {
		if (concrete.getFCm() <= 35) {
			betaH = Math.min(1.5 * (1 + Math.pow((0.0012 * concrete.getrH()), 12)) * dimensions.getH0() + 250, 1500);
		} else {
			betaH = Math.min(1.5 * (1 + Math.pow((0.0012 * concrete.getrH()), 12)) * dimensions.getH0() + 250 * alfa3, 1500 * alfa3);
		}
	}

	private void calculateBetaC(Concrete concrete) {
		betaC = Math.pow((t - t0) / (betaH + t - t0), 0.3);
	}

	private void calculateCreepCoeficent() {
		creepCoeficent = phi0 * betaC;
		System.out.println("creepCoeficent " + creepCoeficent);
	}

	public void runCreepCoeficentCalculations(Concrete concrete, Cement cement, DimensionsOfCrossSectionOfConcrete dimensions) {
		calculateAlfa1(concrete);
		calculateAlfa2(concrete);
		calculateAlfa3(concrete);
		calculateT0T(concrete);
		calculateT0(cement);
		calculatePhiRH(concrete, dimensions);
		calculateBetaFcm(concrete);
		calculateBetaT0();
		calculatePhi0();
		calculateBetaH(concrete, dimensions);
		calculateBetaC(concrete);
		calculateCreepCoeficent();

	}

}
