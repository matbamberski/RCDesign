package mainalgorithm;

import materials.DimensionsOfCrossSectionOfConcrete;

public class Reinforcement {

	// bending and axisloads

	private double requiredUnsymmetricalAS1;
	private double requiredUnsymmetricalAS2;
	private double requiredSymmetricalAS1;
	private double requiredSymmetricalAS2;

	private int requiredNumberOfSymmetricalRods;
	private int requiredNumberOfSymmetricalRodsAS1;
	private int requiredNumberOfSymmetricalRodsAS2;
	private int requiredNumberOfUnsymmetricalRodsAS1;
	private int requiredNumberOfUnsymmetricalRodsAS2;

	private double designedDiameterAS1;
	private double designedDiameterAS2;

	private double designedSymmetricalAS1;
	private double designedSymmetricalAS2;
	private double desingedUnsymmetricalAS1;
	private double designedUnsymmetricalAS2;

	private double degreeOfComputedSymmetricalReinforcement;
	private double degreeOfDesignedSymmetricalReinforcement;
	private double degreeOfComputedUnsymmetricalReinforcement;
	private double degreeOfDesignedUnsymmetricalReinforcement;
	
	private double reinforcementRatio;
	private boolean degreeExceededSymmetrical = false;
	private boolean degreeExceededUnsymmetrical = false;
	private boolean degreeExceeded = false;


	// shearing
	private double sLmin;
	private double nS1;
	private double nS2Required;
	private double nS2Designed;
	private double phiSt;
	private double s;
	private double s1Required;
	private double s1Designed;
	private double s2Required;
	private double s2Designed;
	private double sL;
	private double aSW1;
	private double aSW2;
	private double aSW1Diameter;
	private double aSW2Diameter;
	private double theta;
	private double alfa;
	
	private double requiredAS1ManyForces;
	private double requiredAS2ManyForces;
	private double degreeManyForces;
	
	
	
	public double getDegreeManyForces() {
		return degreeManyForces;
	}

	public void setDegreeManyForces(double degreeManyForces) {
		this.degreeManyForces = degreeManyForces;
	}

	public double getRequiredAS1ManyForces() {
		return requiredAS1ManyForces;
	}

	public void setRequiredAS1ManyForces(double requiredAS1ManyForces) {
		this.requiredAS1ManyForces = requiredAS1ManyForces;
	}

	public double getRequiredAS2ManyForces() {
		return requiredAS2ManyForces;
	}

	public void setRequiredAS2ManyForces(double requiredAS2ManyForces) {
		this.requiredAS2ManyForces = requiredAS2ManyForces;
	}

	public boolean isDegreeExceeded() {
		return degreeExceeded;
	}

	public void setDegreeExceeded(boolean degreeExceeded) {
		this.degreeExceeded = degreeExceeded;
	}

	public boolean isDegreeExceededUnsymmetrical() {
		return degreeExceededUnsymmetrical;
	}

	public void setDegreeExceededUnsymmetrical(boolean degreeExceededUnsymmetrical) {
		this.degreeExceededUnsymmetrical = degreeExceededUnsymmetrical;
	}

	public boolean isDegreeExceededSymmetrical() {
		return degreeExceededSymmetrical;
	}

	public void setDegreeExceededSymmetrical(boolean degreeExceededSymmetrical) {
		this.degreeExceededSymmetrical = degreeExceededSymmetrical;
	}

	public double getnS2Designed() {
		return nS2Designed;
	}

	public double getS1Designed() {
		return s1Designed;
	}

	public double getS2Designed() {
		return s2Designed;
	}

	public void setnS2Designed(double nS2Designed) {
		this.nS2Designed = nS2Designed;
		System.out.println("nS2Designed" + nS2Designed);
	}

	public void setS1Designed(double s1Designed) {
		this.s1Designed = s1Designed;
		System.out.println("s1Designed" + s1Designed);
	}

	public void setS2Designed(double s2Designed) {
		this.s2Designed = s2Designed;
		System.out.println("s1Designed" + s2Designed);

	}
	
	///Obliczenie pola zbrojenia
	

	public double getReinforcementRatio() {
		return reinforcementRatio;
	}

	public void setReinforcementRatio(double reinforcementRatio) {
		this.reinforcementRatio = reinforcementRatio;
	}

	public void calculateDesignedSymmetricalAs1RodsGiven() {
		designedSymmetricalAS1 = requiredNumberOfSymmetricalRodsAS1 * Math.PI * (designedDiameterAS1 / 1000) * (designedDiameterAS1 / 1000) / 4;
		System.out.println("designedSymmetricalAS1 " + designedSymmetricalAS1);
	}

	public void calculateDesignedSymmetricalAs2RodsGiven() {
		designedSymmetricalAS2 = requiredNumberOfSymmetricalRodsAS2 * Math.PI * (designedDiameterAS2 / 1000) * (designedDiameterAS2 / 1000) / 4;
		System.out.println("designedSymmetricalAS2 " + designedSymmetricalAS2);
	}

	public void calculateDesignedUnsymmetricalAs1RodsGiven() {
		desingedUnsymmetricalAS1 = requiredNumberOfUnsymmetricalRodsAS1 * Math.PI * (designedDiameterAS1 / 1000) * (designedDiameterAS1 / 1000) / 4;
		System.out.println("desingedUnsymmetricalAS1 " + desingedUnsymmetricalAS1);
	}

	public void calculateDesignedUnsymmetricalAs2RodsGiven() {
		designedUnsymmetricalAS2 = requiredNumberOfUnsymmetricalRodsAS2 * Math.PI * (designedDiameterAS2 / 1000) * (designedDiameterAS2 / 1000) / 4;
		System.out.println("desingedUnsymmetricalAS2 " + designedUnsymmetricalAS2);
	}

	public double getTheta() {
		return theta;
	}

	public double getAlfa() {
		return alfa;
	}

	public void setTheta(double Theta) {
		this.theta = Theta;
		System.out.println(" Theta " + Theta);
	}

	public void setAlfa(double Alfa) {
		this.alfa = Alfa;
		System.out.println("Alfa " + Alfa);
	}

	public double getaSW1Diameter() {
		return aSW1Diameter;
	}

	public double getaSW2Diameter() {
		return aSW2Diameter;
	}

	public void setaSW1Diameter(double aSW1Decimal) {
		this.aSW1Diameter = aSW1Decimal;
		System.out.println("aSW1Diameter " + aSW1Decimal);
	}

	public void setaSW2Diameter(double aSW2Decimal) {
		this.aSW2Diameter = aSW2Decimal;
		System.out.println("aSW2Decimal " + aSW2Decimal);
	}

	public double getaSW1() {
		return aSW1;
	}

	public double getaSW2() {
		return aSW2;
	}

	public void setaSW1(double aSW1) {
		this.aSW1 = aSW1;
		System.out.println("aSw1 " + aSW1);
	}

	public void setaSW2(double aSW2) {
		this.aSW2 = aSW2;
		System.out.println("aSw2 " + aSW2);
	}

	public void setSL(double sL) {
		this.sL = sL;
	}

	public double getSL() {
		return sL;
	}

	public void setSLmin(double sLmin) {
		this.sLmin = sLmin;
	}

	public double getS1Required() {
		return s1Required;
	}

	public double getS2Required() {
		return s2Required;
	}

	public void setS1Required(double s1) {
		this.s1Required = s1;
		System.out.println("s1 " + s1);
	}

	public void setS2Required(double s2) {
		this.s2Required = s2;
		System.out.println("s2 " + s2);
	}

	private double lW;

	public double getnS1() {
		return nS1;
	}

	public double getnS2Required() {
		return nS2Required;
	}

	public void setnS1(double nS1) {
		this.nS1 = nS1;
		System.out.println("nS1 " + nS1);
	}

	public void setnS2Required(double nS2) {
		this.nS2Required = nS2;
		System.out.println("nS2 " + nS2);
	}

	public void setS(double s) {
		this.s = s;
		System.out.println("s " + s);
	}

	public void setlW(double lW) {
		this.lW = lW;
		System.out.println("lW " + lW);
	}

	public int getRequiredNumberOfSymmetricalRods() {
		return requiredNumberOfSymmetricalRods;
	}

	public int getRequiredNumberOfSymmetricalRodsAS1() {
		return requiredNumberOfSymmetricalRodsAS1;
	}

	public int getRequiredNumberOfSymmetricalRodsAS2() {
		return requiredNumberOfSymmetricalRodsAS2;
	}

	public int getRequiredNumberOfUnsymmetricalRodsAS1() {
		return requiredNumberOfUnsymmetricalRodsAS1;
	}

	public int getRequiredNumberOfUnsymmetricalRodsAS2() {
		return requiredNumberOfUnsymmetricalRodsAS2;
	}

	public double getDesignedSymmetricalAS1() {
		return designedSymmetricalAS1;
	}

	public double getDesignedSymmetricalAS2() {
		return designedSymmetricalAS2;
	}

	public double getDesingedUnsymmetricalAS1() {
		return desingedUnsymmetricalAS1;
	}

	public double getDesignedUnsymmetricalAS2() {
		return designedUnsymmetricalAS2;
	}

	public void setRequiredNumberOfSymmetricalRods(int requiredNumberOfSymmetricalRods) {
		this.requiredNumberOfSymmetricalRods = requiredNumberOfSymmetricalRods;
		System.out.println("requiredNumberOfSymmetricalRods " + requiredNumberOfSymmetricalRods);
	}

	public void setRequiredNumberOfSymmetricalRodsAS1(int requiredNumberOfSymmetricalRodsAS1) {
		this.requiredNumberOfSymmetricalRodsAS1 = requiredNumberOfSymmetricalRodsAS1;
		System.out.println("requiredNumberOfSymmetricalRodsAS1 " + requiredNumberOfSymmetricalRodsAS1);
	}

	public void setRequiredNumberOfSymmetricalRodsAS2(int requiredNumberOfSymmetricalRodsAS2) {
		this.requiredNumberOfSymmetricalRodsAS2 = requiredNumberOfSymmetricalRodsAS2;
		System.out.println("requiredNumberOfSymmetricalRodsAS2 " + requiredNumberOfSymmetricalRodsAS2);
	}

	public void setRequiredNumberOfUnsymmetricalRodsAS1(int requiredNumberOfUnsymmetricalRodsAS1) {
		this.requiredNumberOfUnsymmetricalRodsAS1 = requiredNumberOfUnsymmetricalRodsAS1;
		System.out.println("requiredNumberOfUnsymmetricalRodsAS1 " + requiredNumberOfUnsymmetricalRodsAS1);
	}

	public void setRequiredNumberOfUnsymmetricalRodsAS2(int requiredNumberOfUnsymmetricalRodsAS2) {
		this.requiredNumberOfUnsymmetricalRodsAS2 = requiredNumberOfUnsymmetricalRodsAS2;
		System.out.println("requiredNumberOfUnsymmetricalRodsAS2 " + requiredNumberOfUnsymmetricalRodsAS2);
	}

	public void setDesignedSymmetricalAS1(double designedSymmetricalAS1) {
		this.designedSymmetricalAS1 = designedSymmetricalAS1;
		System.out.println("designedSymmetricalAS1 " + designedSymmetricalAS1);
	}

	public void setDesignedSymmetricalAS2(double designedSymmetricalAS2) {
		this.designedSymmetricalAS2 = designedSymmetricalAS2;
		System.out.println("designedSymmetricalAS2 " + designedSymmetricalAS2);
	}

	public void setDesingedUnsymmetricalAS1(double desingedUnsymmetricalAS1) {
		this.desingedUnsymmetricalAS1 = desingedUnsymmetricalAS1;
		System.out.println("desingedUnsymmetricalAS1 " + desingedUnsymmetricalAS1);
	}

	public void setDesignedUnsymmetricalAS2(double designedUnsymmetricalAS2) {
		this.designedUnsymmetricalAS2 = designedUnsymmetricalAS2;
		System.out.println("desingedUnsymmetricalAS2 " + designedUnsymmetricalAS2);
	}

	public double getPhiSt() {
		return phiSt;
	}

	public double getDegreeOfComputedSymmetricalReinforcement() {
		return degreeOfComputedSymmetricalReinforcement;
	}

	public double getDegreeOfDesignedSymmetricalReinforcement() {
		return degreeOfDesignedSymmetricalReinforcement;
	}

	public double getDegreeOfComputedUnsymmetricalReinforcement() {
		return degreeOfComputedUnsymmetricalReinforcement;
	}

	public double getDegreeOfDesignedUnsymmetricalReinforcement() {
		return degreeOfDesignedUnsymmetricalReinforcement;
	}

	public double getRequiredSymmetricalAS1() {
		return requiredSymmetricalAS1;
	}

	public double getRequiredSymmetricalAS2() {
		return requiredSymmetricalAS2;
	}

	public double getRequiredUnsymmetricalAS1() {
		return requiredUnsymmetricalAS1;
	}

	public double getRequiredUnsymmetricalAS2() {
		return requiredUnsymmetricalAS2;
	}

	public double getDesignedDiameterSymmetricalAS1() {
		return designedDiameterAS1;
	}

	public double getDesignedDiameterSymmetricalAS2() {
		return designedDiameterAS2;
	}

	public void setRequiredUnsymmetricalAS1(double requiredAS1) {
		this.requiredUnsymmetricalAS1 = requiredAS1;
		System.out.println("requiredUnsymmetricalAS1 " + requiredUnsymmetricalAS1);
	}

	public void setRequiredUnsymmetricalAS2(double requiredAS2) {
		this.requiredUnsymmetricalAS2 = requiredAS2;
		System.out.println("requiredUnsymmetricalAS2 " + requiredUnsymmetricalAS2);
	}

	public void setRequiredSymmetricalAS1(double requiredSymmetricalAS) {
		this.requiredSymmetricalAS1 = requiredSymmetricalAS;
		System.out.println("requiredSymmetricalAS1 " + requiredSymmetricalAS1);
	}

	public void setRequiredSymmetricalAS2(double requiredSymmetricalAS2) {
		this.requiredSymmetricalAS2 = requiredSymmetricalAS2;
		System.out.println("requiredSymmetricalAS2 " + requiredSymmetricalAS2);
	}

	public void setDesignedDiammeterAS1(double designedAS1) {
		this.designedDiameterAS1 = designedAS1;
		System.out.println("designedAS1 " + designedAS1);
	}

	public void setDesignedDiameterAS2(double designedAS2) {
		this.designedDiameterAS2 = designedAS2;
		System.out.println("designedAS2 " + designedAS2);
	}

	///Obliczenie stopni zbrojenia
	public void setDegreeOfComputedSymmetricalReinforcementRectangular(DimensionsOfCrossSectionOfConcrete dimensions) {
		this.degreeOfComputedSymmetricalReinforcement = (requiredSymmetricalAS1 + requiredSymmetricalAS2) / (dimensions.getB() * dimensions.getH());
		System.out.println("degreeOfComputedSymmetricalReinforcement " + degreeOfComputedSymmetricalReinforcement);
	}

	public void setDegreeOfDesignedSymmetricalReinforcement(DimensionsOfCrossSectionOfConcrete dimensions) {
		this.degreeOfDesignedSymmetricalReinforcement = (designedSymmetricalAS1 + designedSymmetricalAS2) / (dimensions.getB() * dimensions.getH());
		System.out.println("degreeOfDesignedSymmetricalReinforcement " + degreeOfDesignedSymmetricalReinforcement);
	}

	public void setDegreeOfComputedUnsymmetricalReinforcementRectangular(DimensionsOfCrossSectionOfConcrete dimensions) {
		this.degreeOfComputedUnsymmetricalReinforcement = (requiredUnsymmetricalAS1 + requiredUnsymmetricalAS2) / (dimensions.getB() * dimensions.getH());
		System.out.println("degreeOfComputedUnsymmetricalReinforcement " + degreeOfComputedUnsymmetricalReinforcement);
	}

	public void setDegreeOfDesignedUnsymmetricalReinforcement(DimensionsOfCrossSectionOfConcrete dimensions) {
		this.degreeOfDesignedUnsymmetricalReinforcement = (desingedUnsymmetricalAS1 + designedUnsymmetricalAS2) / (dimensions.getB() * dimensions.getH());
		System.out.println("degreeOfDesignedUnsymmetricalReinforcement " + degreeOfDesignedUnsymmetricalReinforcement);
	}
	
	public void setDegreeOfSymmetricalReinforcement(double degree) {
		this.degreeOfComputedSymmetricalReinforcement = degree;
	}
	
	public void setDegreeOfUnsymmetricalReinforcement(double degree) {
		this.degreeOfComputedUnsymmetricalReinforcement = degree;
	}

	public void setPhiSt(double phiSt) {

		this.phiSt = phiSt;
		System.out.println("phi St " + phiSt);
	}

	public double getsLmin() {
		return sLmin;
	}

	public double getS() {
		return s;
	}

	public double getlW() {
		return lW;
	}
}
