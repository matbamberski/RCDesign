package mainalgorithm;

import java.util.ArrayList;
import java.util.Collections;

public class InternalForces {

	private double mEdObliczeniowe;
	private double m0Ed;
	private double nEd;
	private double vEd;
	private double vEdRed;
	private double nCritSymmetrical = 0;
	private double nCritUnsymmetrical = 0;

	protected double momentMmax;
	protected double momentMmin;
	protected double momentNmax;
	protected double momentNmin;
	protected double normalnaMmax;
	protected double normalnaMmin;
	protected double normalnaNmax;
	protected double normalnaNmin;

	protected double momentMmaxStiff;
	protected double momentMminStiff;
	protected double momentNmaxStiff;
	protected double momentNminStiff;

	private double mEdStiff;

	ArrayList<ForcesCombination> combinations = new ArrayList<>();
	ArrayList<ForcesCombination> combinationDiagnosis = new ArrayList<>();
	ArrayList<ForcesCombination> pdfCombinations;

	private double gPlusQForShearing;

	private double characteristicMEd;
	private double characteristicMEdCalkowita;
	private double characteristicMEdDlugotrwale;
	private double characteristicNEd;
	private double characteristicVEd;
	private double characteristicVEdRed;

	private double alfaM;
	private boolean isLoadSustained;

	public void countECombinations(ArrayList<ForcesCombination> combination) {
		ForcesCombination Mmax = new ForcesCombination(Math.abs(getMomentMmax()), getNormalnaMmax());
		if (getMomentMmax() < 0)
			Mmax.setMedNegativ(true);

		ForcesCombination Mmin = new ForcesCombination(Math.abs(getMomentMmin()), getNormalnaMmin());
		if (getMomentMmin() < 0)
			Mmin.setMedNegativ(true);

		ForcesCombination Nmax = new ForcesCombination(Math.abs(getMomentNmax()), getNormalnaNmax());
		if (getMomentNmax() < 0)
			Nmax.setMedNegativ(true);

		ForcesCombination Nmin = new ForcesCombination(Math.abs(getMomentNmin()), getNormalnaNmin());
		if (getMomentNmin() < 0)
			Nmin.setMedNegativ(true);

		Collections.addAll(combination, Mmax, Mmin, Nmax, Nmin);
		System.out.println("liczy kombinacje si³");
	}
	
	public ArrayList<ForcesCombination> getForcesCombinations() {
		ArrayList<ForcesCombination> combination = new ArrayList<>();
		
		ForcesCombination Mmax = new ForcesCombination(getMomentMmax(), getNormalnaMmax());

		ForcesCombination Mmin = new ForcesCombination(getMomentMmin(), getNormalnaMmin());

		ForcesCombination Nmax = new ForcesCombination(getMomentNmax(), getNormalnaNmax());

		ForcesCombination Nmin = new ForcesCombination(getMomentNmin(), getNormalnaNmin());

		Collections.addAll(combination, Mmax, Mmin, Nmax, Nmin);
		System.out.println("liczy kombinacje si³");
		
		return combination;
	}

	public void setMedCombination() {
		ForcesCombination Mmax = new ForcesCombination(Math.abs(getmEd()), getnEd());
		if (getmEd() < 0)
			Mmax.setMedNegativ(true);

		Collections.addAll(combinations, Mmax);
	}

	public ForcesCombination getMaxECombination() {
		double eResult = 0;
		double mResult = 0;
		double nResult = 0;
		ForcesCombination combination = null;
		boolean isSelected = false;
		if (normalnaMmax ==0 && normalnaMmin ==0 
				&& normalnaNmax == 0 && normalnaNmin ==0) {
			for (ForcesCombination fc : combinations) {
				if (mResult <= fc.getM()) {
					combination = fc;
					mResult = fc.getM();
				}
			}
		} else {
		
		for (ForcesCombination fc : combinations) {
			if (!(fc.getM() == 0 && fc.getN() == 0)) {
				if (eResult < fc.getE()) {
					combination = fc;
					eResult = fc.getE();
					nResult = fc.getN();
					isSelected = true;
				} else if (eResult == fc.getE() && Math.abs(nResult) < Math.abs(fc.getN())) {
					combination = fc;
					eResult = fc.getE();
					nResult = fc.getN();
					isSelected = true;
				} else if (eResult == fc.getE() && Math.abs(nResult) == Math.abs(fc.getN()) && 
						fc.getN() < 0) {
					combination = fc;
					eResult = fc.getE();
					nResult = fc.getN();
					isSelected = true;
				}
			}
		}
		if (!isSelected) {
			combination = combinations.get(0);
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
		System.out.println("MomentMmax: " + momentMmax);
	}

	public double getMomentMmin() {
		return momentMmin;
	}

	public void setMomentMmin(double momentMmin) {
		this.momentMmin = momentMmin;
		System.out.println("MomentMmin: " + momentMmin);
	}

	public double getMomentNmax() {
		return momentNmax;
	}

	public void setMomentNmax(double momentNmax) {
		this.momentNmax = momentNmax;
		System.out.println("MomentNmax: " + momentNmax);
	}

	public double getMomentNmin() {
		return momentNmin;
	}

	public void setMomentNmin(double momentNmin) {
		this.momentNmin = momentNmin;
		System.out.println("MomentNmin: " + momentNmin);
	}

	public double getNormalnaMmax() {
		return normalnaMmax;
	}

	public void setNormalnaMmax(double normalnaMmax) {
		this.normalnaMmax = normalnaMmax;
		System.out.println("normalnaMmax: " + normalnaMmax);
	}

	public double getNormalnaMmin() {
		return normalnaMmin;
	}

	public void setNormalnaMmin(double normalnaMmin) {
		this.normalnaMmin = normalnaMmin;
		System.out.println("normalnaMmin: " + normalnaMmin);
	}

	public double getNormalnaNmax() {
		return normalnaNmax;
	}

	public void setNormalnaNmax(double normalnaNmax) {
		this.normalnaNmax = normalnaNmax;
		System.out.println("normalnaNmax: " + normalnaNmax);
	}

	public double getNormalnaNmin() {
		return normalnaNmin;
	}

	public void setNormalnaNmin(double normalnaNmin) {
		this.normalnaNmin = normalnaNmin;
		System.out.println("normalnaNmin: " + normalnaNmin);
	}

	public double getM0Ed() {
		return m0Ed;
	}

	public void setM0Ed(double m0Ed) {
		this.m0Ed = m0Ed;
	}

	public ArrayList<ForcesCombination> getCombinations() {
		return combinations;
	}

	public ArrayList<ForcesCombination> getCombinationDiagnosis() {
		return combinationDiagnosis;
	}

	public void setCombinationDiagnosis(ArrayList<ForcesCombination> combinationDiagnosis) {
		this.combinationDiagnosis = combinationDiagnosis;
	}

	public double getMomentMmaxStiff() {
		return momentMmaxStiff;
	}

	public void setMomentMmaxStiff(double momentMmaxStiff) {
		this.momentMmaxStiff = momentMmaxStiff;
	}

	public double getMomentMminStiff() {
		return momentMminStiff;
	}

	public void setMomentMminStiff(double momentMminStiff) {
		this.momentMminStiff = momentMminStiff;
	}

	public double getMomentNmaxStiff() {
		return momentNmaxStiff;
	}

	public void setMomentNmaxStiff(double momentNmaxStiff) {
		this.momentNmaxStiff = momentNmaxStiff;
	}

	public double getMomentNminStiff() {
		return momentNminStiff;
	}

	public void setMomentNminStiff(double momentNminStiff) {
		this.momentNminStiff = momentNminStiff;
	}

	public double getmEdStiff() {
		return mEdStiff;
	}

	public void setmEdStiff(double mEdStiff) {
		this.mEdStiff = mEdStiff;
	}

	public void saveCombinationToPdfCombination() {
		pdfCombinations = new ArrayList<>(combinationDiagnosis);
	}

	public ArrayList<ForcesCombination> getPdfCombinations() {
		return pdfCombinations;
	}
	
	
	
	public double getnCritSymmetrical() {
		return nCritSymmetrical;
	}

	public void setnCritSymmetrical(double nCritSymmetrical) {
		this.nCritSymmetrical = nCritSymmetrical;
	}

	public double getnCritUnsymmetrical() {
		return nCritUnsymmetrical;
	}

	public void setnCritUnsymmetrical(double nCritUnsymmetrical) {
		this.nCritUnsymmetrical = nCritUnsymmetrical;
	}

	public int checkHowManyCombinationsWithMandN() {
		int i = 0;
		for (ForcesCombination fc : pdfCombinations) {
			if (fc.getM()!=0 && fc.getN()!=0) i++;
		}
		return i;
	}
	
	public int checkHowManyCombinationsWithMorN() {
		int i = 0;
		for (ForcesCombination fc : pdfCombinations) {
			if (fc.getM()!=0 || fc.getN()!=0) i++;
		}
		return i;
	}
}
