package materials;

public class Concrete {

	// constuctors
	public Concrete() {
	}

	public Concrete(int concreteType) {
		setConcreteClassNumber(concreteType);
	}

	// veriables
	public final static String[] concreteClass = { "C12/15", "C16/20", "C20/25", "C25/30", "C30/37", "C35/45", "C40/50", "C45/55", "C50/60" };
	private final static int[] fCCube = { 15, 20, 25, 30, 37, 45, 50, 55, 60 }; // wytrzymalosc
																				// gwarantowana
																				// [MPa]
	private final static int[] fCk = { 12, 16, 20, 25, 30, 35, 40, 45, 50 }; // wytrzymalosc
																				// charakterystyczna
																				// na
																				// sciskanie
																				// [MPa]
	private final static int[] fCm = { 20, 24, 28, 33, 38, 43, 48, 53, 58, 63, 68, 78, 88, 98 };

	private final static double[] fCtk = { 1.1, 1.3, 1.5, 1.8, 2, 2.2, 2.5, 2.7, 2.9 }; // wytrzymalosc
																						// srednia
																						// na
																						// rozciaganie
																						// [MPa]
	private final static double[] fCtm = { 1.6, 1.9, 2.2, 2.6, 2.9, 3.2, 3.5, 3.8, 4.1 }; // wytrzymalosc
																							// srednia
																							// na
																							// rozciaganie
																							// [MPa]
	private final static double[] fCd = { 8, 10.6, 14.28, 16.7, 21.43, 23.3, 26.7, 30, 33.3 }; // wytrzymalosc
																								// obliczeniowa
																								// na
																								// sciskanie
																								// [MPa]
	private final static double[] fCtd = { 0.73, 0.87, 1, 1.2, 1.33, 1.47, 1.67, 1.8, 1.93 }; // wytrzymalosc
																								// obliczeniowa
																								// na
																								// rozciaganie
																								// [MPa]
	private final static int[] eCm = { 27, 29, 30, 31, 32, 34, 35, 36, 37 }; // modul
																				// sprezystosci
																				// [GPa]
	private double eCEff;

	private final static int volumeWeightOfConcrete = 25; // ciezar objetoscowy
															// betonu [kN/m3]
	private double epsilonC3 = 0.00175;
	private double epsilonCU3 = 0.0035; // graniczne odksztalcenie
										// betonu
	private final static double vC = 0.2; // wspolczynnik odksztalcenia
											// poprzecznego
	private final static double alfaT = 0.00001; // wspolczynnik liniowej
													// rozszerzalnosci
													// termicznej [1/C ]

	private int concreteClassNumber; // numer used to return concrete properties
										// from tables above ex from
										// concreteClass or f_ck

	private int t0;
	private double rH;

	public int getT0() {
		return t0;
	}

	public double getrH() {
		return rH;
	}

	public void setT0(int t0) {
		this.t0 = t0;
		System.out.println("t0 " + t0);
	}

	public void setrH(double rH) {
		this.rH = rH;
		System.out.println("rH " + rH);
	}

	public void setConcreteClassNumber(int concreteType) {
		concreteClassNumber = concreteType;
	}

	public int returnConcreteteClassNumber() {
		return concreteClassNumber;
	}

	public String getConcreteClass() {
		return concreteClass[concreteClassNumber];
	}

	public int getFCCube() {
		return fCCube[concreteClassNumber];
	}

	public int getFCk() {
		return fCk[concreteClassNumber];
	}

	public int getFCm() {
		return fCm[concreteClassNumber];
	}

	public double getFCtk() {
		return fCtk[concreteClassNumber];
	}

	public double getFCtm() {
		return fCtm[concreteClassNumber];
	}

	public double getFCd() {
		return fCd[concreteClassNumber];
	}

	public double getFctd() {
		return fCtd[concreteClassNumber];
	}

	public int getECm() {

		return eCm[concreteClassNumber];
	}

	public double geteCEff() {
		return eCEff;
	}

	public void calculateECEff(double creepCoeficent) {
		eCEff = getECm() / (1 + creepCoeficent);
		System.out.println("Ec,eff " + eCEff);
	}

	public void seteCEff(double eCEff) {
		this.eCEff = eCEff;
	}

	public int getVolumeWeightOfConcrete() {
		return volumeWeightOfConcrete;
	}

	public double getEpsilonCU3() {
		return epsilonCU3;
	}

	public double getEpsilonC3() {
		return epsilonC3;
	}

	public double getVC() {
		return vC;
	}

	public double getAlfaT() {
		return alfaT;
	}

}
