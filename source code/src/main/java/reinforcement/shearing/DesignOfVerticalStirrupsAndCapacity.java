package reinforcement.shearing;

import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import util.ResultsToPDF;

public class DesignOfVerticalStirrupsAndCapacity extends ShearingReinforcementAnalizer {

	private double vRdMax;

	private void setRoW(DimensionsOfCrossSectionOfConcrete dimensions) {
		double roWTemporary = aSw1 / (s1 * dimensions.getB());
		System.out.println("roWtemp " + roWTemporary);
		if (roWTemporary >= roWMin) {
			roW = roWTemporary;
		} else {
			setRoWEqualToRoWMin();
			setSWhenRoWIsLessThenRoWMin(dimensions);
		}
	}

	private void setRoWEqualToRoWMin() {
		roW = roWMin;
	}

	private void setSWhenRoWIsLessThenRoWMin(DimensionsOfCrossSectionOfConcrete dimensions) {
		s1 = aSw1 / (roW * dimensions.getB());
	}
/*
	private void setVRd(Concrete concrete, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions) {
		vRd = aSw1 / s1 * z * steel.getFYd() * cotTheta;
		vRdMax = alfaCw * dimensions.getB() * z * v1 * concrete.getFCd() * 1000 / (tanTheta + cotTheta);
		vRd = Math.min(vRd, vRdMax);
		System.out.println("vRd " + vRd);
	}
*/
	private void pushResultsToPDF(InternalForces forces) {

		ResultsToPDF.addResults("Ved", String.valueOf(forces.getvEd()));
		ResultsToPDF.addResults("RoWMin", String.valueOf(roWMin));
		ResultsToPDF.addResults("z", String.valueOf(z));
		ResultsToPDF.addResults("Asw", String.valueOf(aSw1));
		ResultsToPDF.addResults("sLMax", String.valueOf(sLMax));
		ResultsToPDF.addResults("s", String.valueOf(s1));
		ResultsToPDF.addResults("roW", String.valueOf(roW));
		//ResultsToPDF.addResults("Vrd", String.valueOf(vRd));
	}

	protected void designVerticalStirrupsAndCheckCapacity(Concrete concrete, Steel steel, InternalForces forces, DimensionsOfCrossSectionOfConcrete dimensions, Reinforcement reinforcement) {
		calculateCotTheta(reinforcement.getTheta());
		calculateTanTheta(reinforcement.getTheta());
		calculateSinAlfa(reinforcement.getAlfa());
		calculateCotAlfa(reinforcement.getAlfa());
		setV1(concrete);
		setRoWMin(concrete, steel);
		setZ(dimensions);
		setASwVerticalStirrup(reinforcement.getnS1(), reinforcement.getaSW1Diameter());
		setSlMax(dimensions.getD());
		setS(steel, forces.getvEd());
		setRoW(dimensions);
		//setVRd(concrete, steel, dimensions);
		pushResultsToReinforcementClass(reinforcement);
		pushResultsToPDF(forces);
	}

}
