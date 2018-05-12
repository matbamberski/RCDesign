package mainalgorithm;

import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

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
	
	private double e1;
	private double e2;
	private double e3;
	private double e4;
	

	private double gPlusQForShearing;

	private double characteristicMEd;
	private double characteristicMEdCalkowita;
	private double characteristicMEdDlugotrwale;
	private double characteristicNEd;
	private double characteristicVEd;

	private double alfaM;
	private boolean isLoadSustained;
	
	public void countE() {
		if (this.normalnaMmax!=0) {
			setE1(getMomentMmax()/getNormalnaMmax());
		} else setE1(0);
		
		if (this.normalnaMmin!=0) {
			setE2(getMomentMmin()/getNormalnaMmin());
		} else setE2(0);
		
		if (this.normalnaNmax!=0) {
			setE3(getMomentNmax()/getNormalnaNmax());
		} else setE3(0);
		
		if (this.normalnaNmin!=0) {
			setE4(getMomentNmin()/getNormalnaNmin());
		} else setE4(0);
		
	}
	
	public double getMaxE() {
		OptionalDouble max = DoubleStream.of(Math.abs(this.e1), Math.abs(this.e2), Math.abs(this.e3), Math.abs(this.e4)).max();
		return max.orElse(0);
	}
	

	public void checkIsLoadSustained(int i) {
		if (i == 0) {
			isLoadSustained = true;
		} else {
			isLoadSustained = false;
		}
		System.out.println(" obciazenie dlugotrwale ? " + isLoadSustained);
	}
	
	

	public double getE1() {
		return e1;
	}


	public void setE1(double e1) {
		this.e1 = e1;
	}


	public double getE2() {
		return e2;
	}


	public void setE2(double e2) {
		this.e2 = e2;
	}


	public double getE3() {
		return e3;
	}


	public void setE3(double e3) {
		this.e3 = e3;
	}


	public double getE4() {
		return e4;
	}


	public void setE4(double e4) {
		this.e4 = e4;
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
