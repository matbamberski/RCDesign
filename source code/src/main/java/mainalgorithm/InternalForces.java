package mainalgorithm;

public class InternalForces {

	private double mEdObliczeniowe;
	private double nEd;
	private double vEd;
	
	private double momentMmax;
	private double momentMmin;
	private double momentNmax;
	private double momentNmin;
	private double normalnaMmax;
	private double normalnaMmin;
	private double normalnaNmax;
	private double normalnaNmin;
	

	private double gPlusQForShearing;

	private double characteristicMEd;
	private double characteristicMEdCalkowita;
	private double characteristicMEdDlugotrwale;
	private double characteristicNEd;
	private double characteristicVEd;

	private double alfaM;
	private boolean isLoadSustained;

	public void checkIsLoadSustained(int i) {
		if (i == 0) {
			isLoadSustained = true;
		} else {
			isLoadSustained = false;
		}
		System.out.println(" obciazenie dlugotrwale ? " + isLoadSustained);
	}

	public boolean isLoadSustained() {
		return isLoadSustained;
	}

	public double getAlfaM() {
		return alfaM;
	}

	public void setAlfaM(double alfaM) {
		this.alfaM = alfaM;
		System.out.println("alfaM " + alfaM);
	}

	public double getCharacteristicMEdCalkowita() {
		return characteristicMEdCalkowita;
	}

	public double getCharacteristicMEdDlugotrwale() {
		return characteristicMEdDlugotrwale;
	}

	public void setCharacteristicMEdCalkowita(double characteristicMEdCalkowita) {
		this.characteristicMEdCalkowita = characteristicMEdCalkowita;
		System.out.println("mEd char.calk " + characteristicMEdCalkowita);
	}

	public void setCharacteristicMEdDlugotrwale(double characteristicMEdDlugotrwale) {
		this.characteristicMEdDlugotrwale = characteristicMEdDlugotrwale;
		System.out.println("mEd char.dlugo " + characteristicMEdDlugotrwale);
	}

	public double getgPlusQForShearing() {
		return gPlusQForShearing;
	}

	public double getmEd() {
		return mEdObliczeniowe;
	}

	public double getnEd() {
		return nEd;
	}

	public double getvEd() {
		return vEd;
	}

	public void setgPlusQForShearing(double gPlusQForShearing) {
		this.gPlusQForShearing = gPlusQForShearing;
		System.out.println("g+q" + gPlusQForShearing);
	}

	public void setmEd(double mEd) {
		this.mEdObliczeniowe = mEd;
		System.out.println("mEd obliczeniowe " + mEd);
	}

	public void setnEd(double nEd) {
		this.nEd = nEd;
		System.out.println("nEd " + nEd);

	}

	public void setvEd(double vEd) {
		this.vEd = vEd;
		System.out.println("vEd " + vEd);
	}

	public double getMomentMmax() {
		return momentMmax;
	}

	public void setMomentMmax(double momentMmax) {
		this.momentMmax = momentMmax;
		System.out.println("MomentMmax: "+momentMmax);
	}

	public double getMomentMmin() {
		return momentMmin;
	}

	public void setMomentMmin(double momentMmin) {
		this.momentMmin = momentMmin;
		System.out.println("MomentMmin: "+momentMmin);
	}

	public double getMomentNmax() {
		return momentNmax;
	}

	public void setMomentNmax(double momentNmax) {
		this.momentNmax = momentNmax;
		System.out.println("MomentNmax: "+momentNmax);
	}

	public double getMomentNmin() {
		return momentNmin;
	}

	public void setMomentNmin(double momentNmin) {
		this.momentNmin = momentNmin;
		System.out.println("MomentNmin: "+momentNmin);
	}

	public double getNormalnaMmax() {
		return normalnaMmax;
	}

	public void setNormalnaMmax(double normalnaMmax) {
		this.normalnaMmax = normalnaMmax;
		System.out.println("normalnaMmax: "+normalnaMmax);
	}

	public double getNormalnaMmin() {
		return normalnaMmin;
	}

	public void setNormalnaMmin(double normalnaMmin) {
		this.normalnaMmin = normalnaMmin;
		System.out.println("normalnaMmin: "+normalnaMmin);
	}

	public double getNormalnaNmax() {
		return normalnaNmax;
	}

	public void setNormalnaNmax(double normalnaNmax) {
		this.normalnaNmax = normalnaNmax;
		System.out.println("normalnaNmax: "+normalnaNmax);
	}

	public double getNormalnaNmin() {
		return normalnaNmin;
	}

	public void setNormalnaNmin(double normalnaNmin) {
		this.normalnaNmin = normalnaNmin;
		System.out.println("normalnaNmin: "+normalnaNmin);
	}
	
	

}
