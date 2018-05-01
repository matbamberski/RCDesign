package materials;

public class Steel {

	public Steel() {
	}

	public Steel(int fYk) {
		setfYk(fYk);
	}

	private int fYk = 500; // charakterystyczna granica plastycznosci [MPa]
	private double fYd; // obliczeniowa granica plastycznosci [MPa]
	private double eS = 200; // moduł sprezystosci podluznej [GPa]
	private double epsilonYd;

	public void setfYk(int fYk) {
		this.fYk = fYk;
		System.out.println("fYk " + fYk);
		setfYd();
	}

	private void setfYd() {
		this.fYd = fYk / 1.15;
		System.out.println("fYd " + fYd);
		setEpsilonYd();
	}

	public void seteS(double eS) {
		this.eS = eS;
		System.out.println("eS " + eS);

	}

	private void setEpsilonYd() {
		this.epsilonYd = fYd / (eS * 1000);
		System.out.println("epsilonYd " + epsilonYd);

	}

	public int getFYk() {
		return fYk;
	}

	public double getFYd() {
		return fYd;
	}

	public double getES() {
		return eS;
	}

	public double getEpsilonYd() {
		return epsilonYd;
	}
}
