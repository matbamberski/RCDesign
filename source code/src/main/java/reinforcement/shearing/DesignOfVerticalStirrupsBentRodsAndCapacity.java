package reinforcement.shearing;

import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.ResultsToPDF;

public class DesignOfVerticalStirrupsBentRodsAndCapacity extends ShearingReinforcementAnalizer {

	private double vEdS1;

	protected void designVerticalStirrupsBentRodsAndCheckCapacity50PercentVedAtVerticallStirrups(Concrete concrete, Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions,
			Reinforcement reinforcement) {
		calculateCotTheta(reinforcement.getTheta());
		calculateTanTheta(reinforcement.getTheta());
		calculateSinAlfa(reinforcement.getAlfa());
		calculateCotAlfa(reinforcement.getAlfa());
		setV1(concrete);
		setRoWMin(concrete, steel);
		setZ(dimensions);
		setASwVerticalStirrup(reinforcement.getnS1(), reinforcement.getaSW1Diameter());
		setASwBentRods(reinforcement.getnS2Required(), reinforcement.getaSW2Diameter());
		setSlMax(dimensions.getD());
		setSBMax(dimensions);
		setS150Percent(steel, forces);
		setVRSd1(steel);
		setS2(steel, forces);
		setVRDS2(steel);
		calculateRoW(reinforcement, dimensions);
		checkIfRoWIsGreterOrEqualToRoWMinR();
		calculateSWhenRoWIsLessThenRoWMin(dimensions);
		pushResultsToReinforcementClass(reinforcement);
		pushResultsToPDF50PercentVedAtVerticallStirrups(forces);

	}

	protected void designVerticalStirrupsBentRodsAndCheckCapacityWhenS2WasGiven(Concrete concrete, Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions,
			Reinforcement reinforcement) {
		setS2(reinforcement.getS2Required());
		calculateCotTheta(reinforcement.getTheta());
		calculateTanTheta(reinforcement.getTheta());
		calculateSinAlfa(reinforcement.getAlfa());
		calculateCotAlfa(reinforcement.getAlfa());
		setV1(concrete);
		setRoWMin(concrete, steel);
		setZ(dimensions);
		setASwVerticalStirrup(reinforcement.getnS1(), reinforcement.getaSW1Diameter());
		setASwBentRods(reinforcement.getnS2Required(), reinforcement.getaSW2Diameter());
		setSlMax(dimensions.getD());
		setSBMax(dimensions);
		ifS2IsMoreThenSbMaxSetS2EqualToSbMax();
		setVRDS2(steel);
		calculateVEdS1WhenS2WasGiven(forces);
		setS1WhenS2WasGiven(steel);
		setVRSd1(steel);
		calculateRoW(reinforcement, dimensions);
		checkIfRoWIsGreterOrEqualToRoWMinR();
		calculateSWhenRoWIsLessThenRoWMin(dimensions);
		pushResultsToReinforcementClass(reinforcement);
		pushResultsToPDFWhenS2WasGiven(forces);
	}

	private void pushResultsToPDF50PercentVedAtVerticallStirrups(InternalForces forces) {

		ResultsToPDF.addResults("Ved", String.valueOf(forces.getvEd()));
		ResultsToPDF.addResults("RoWMin", String.valueOf(roWMin));
		ResultsToPDF.addResults("z", String.valueOf(z));
		ResultsToPDF.addResults("Asw1", String.valueOf(aSw1));
		ResultsToPDF.addResults("Asw2", String.valueOf(aSw2));
		ResultsToPDF.addResults("sLMax", String.valueOf(sLMax));
		ResultsToPDF.addResults("sBMax", String.valueOf(sBMax));
		ResultsToPDF.addResults("s1", String.valueOf(s1));
		ResultsToPDF.addResults("vRdS1", String.valueOf(vRdS1));
		ResultsToPDF.addResults("s2", String.valueOf(s2));
		ResultsToPDF.addResults("vRdS2", String.valueOf(vRdS2));
		ResultsToPDF.addResults("roW", String.valueOf(roW));
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
		ResultsToPDF.addResults("vEds1", String.valueOf(vEdS1));
		ResultsToPDF.addResults("s1", String.valueOf(s1));
		ResultsToPDF.addResults("vRdS1", String.valueOf(vRdS1));
		ResultsToPDF.addResults("roW", String.valueOf(roW));
	}

	private void setVRSd1(Steel steel) {
		vRdS1 = aSw1 / s1 * z * steel.getFYd() * 1000 * cotTheta;
	}

	private void setS150Percent(Steel steel, InternalForces forces) {
		double s1Temp = aSw1 / (0.5 * forces.getvEd()) * z * steel.getFYd() * 1000 * cotTheta;
		s1 = Math.min(s1Temp, sLMax);
	}

	private void setS2(Steel steel, InternalForces forces) {
		double s1Temp = aSw2 * z * steel.getFYd() * 1000 * (tanTheta + cotAlfa) * sinAlfa / (forces.getvEd() - vRdS1);
		s2 = Math.min(s1Temp, sBMax);
	}

	private void setSBMax(DimensionsOfCrossSectionOfConcrete dimensions) {
		sBMax = 0.6 * dimensions.getD() * (1 + cotAlfa);
	}

	private void setVRDS2(Steel steel) {
		vRdS2 = aSw2 / s2 * z * steel.getFYd() * 1000 * (tanTheta + cotAlfa) * sinAlfa;
	}

	private void ifS2IsMoreThenSbMaxSetS2EqualToSbMax() {
		if (s2 > sBMax) {
			s2 = sBMax;
		}
	}

	private void calculateVEdS1WhenS2WasGiven(InternalForces forces) {
		vEdS1 = Math.max((forces.getvEd() - vRdS2), 0.5 * forces.getvEd());
	}

	private void setS1WhenS2WasGiven(Steel steel) {
		double s1Temp = aSw1 / vEdS1 * z * steel.getFYd() * 1000 * cotTheta;
		s1 = Math.min(s1Temp, sLMax);
	}

	private void calculateRoW(Reinforcement reinforcement, DimensionsOfCrossSectionOfConcrete dimensions) {
		roW = aSw1 / (s1 * dimensions.getB()) + aSw2 / (s2 * dimensions.getB() * sinAlfa);
	}

	boolean isRoWGreaterThenRoWMin = true;

	private void checkIfRoWIsGreterOrEqualToRoWMinR() {
		if (roW < roWMin) {
			isRoWGreaterThenRoWMin = false;
			roW = roWMin;
		}

	}

	private void calculateSWhenRoWIsLessThenRoWMin(DimensionsOfCrossSectionOfConcrete dimensions) {
		if (isRoWGreaterThenRoWMin = false) {
			double roWOfBentRods = aSw2 / (s2 * dimensions.getB() * sinAlfa);
			s1 = (roW - roWOfBentRods) * dimensions.getB() / (aSw1);
		}
	}

	private void setS2(double s2) {
		this.s2 = s2;
	}

}
