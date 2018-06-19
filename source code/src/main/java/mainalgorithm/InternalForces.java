package mainalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

public class InternalForces {

	private double mEdObliczeniowe;
	private double nEd;
	private double vEd;
	private double vEdRed;

	protected double momentMmax;
	protected double momentMmin;
	protected double momentNmax;
	protected double momentNmin;
	protected double normalnaMmax;
	protected double normalnaMmin;
	protected double normalnaNmax;
	protected double normalnaNmin;

	ArrayList<ForcesCombination> combinations = new ArrayList<>();


	private double gPlusQForShearing;

	private double characteristicMEd;
	private double characteristicMEdCalkowita;
	private double characteristicMEdDlugotrwale;
	private double characteristicNEd;
	private double characteristicVEd;
	private double characteristicVEdRed;

	private double alfaM;
	private boolean isLoadSustained;
	
	public class ForcesCombination {
		private double M;
		private double N;
		private double e;
		
		public ForcesCombination(double M, double N) {
			this.M = M;
			this.N = N;
			countE();
		}
		
		public void countE() {
			if (this.N!=0) {
				this.e = M/N;
			} else this.e = 0;
		}

		public double getE() {
			return e;
		}

		public double getM() {
			return M;
		}

		public double getN() {
			return N;
		}
		
	}

	public void countECombinations() {
		combinations.clear();
		ForcesCombination Mmax = new ForcesCombination(getMomentMmax(), getNormalnaMmax());
		ForcesCombination Mmin = new ForcesCombination(getMomentMmin(), getNormalnaMmin());
		ForcesCombination Nmax = new ForcesCombination(getMomentNmax(), getNormalnaNmax());
		ForcesCombination Nmin = new ForcesCombination(getMomentNmin(), getNormalnaNmin());
		Collections.addAll(combinations, Mmax, Mmin, Nmax, Nmin);
	}

	public ForcesCombination getMaxECombination() {
		double eResult = 0;
		ForcesCombination combination = null;
		for(ForcesCombination fc : combinations ) {
			if (eResult<=fc.getE()) {
				combination = fc;
				eResult = fc.getE();
			}
		}
		return combination;
	}
	

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
	
	public double getvEdRed() {
		return vEdRed;
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
	
	public void setvEdRed(double vEdRed) {
		this.vEdRed = vEdRed;
		System.out.println("vEdRed " + vEdRed);
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
