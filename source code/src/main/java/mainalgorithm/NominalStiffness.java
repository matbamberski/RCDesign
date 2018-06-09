package mainalgorithm;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class NominalStiffness {
	
	private double mEd;
	private double l0; // dodaæ przycisk
	private double fiT0; // dodaæ przycisk
	private double roS1;
	private double m0Ed;
	private double n0Ed;
	
	public void setM0Ed(double m0Ed) {
		this.m0Ed = m0Ed;
	}

	public void setN0Ed(double n0Ed) {
		this.n0Ed = n0Ed;
	}

	public void setRoS1(double roS1) {
		this.roS1 = roS1;
	}
	

	public double getL0() {
		return l0;
	}

	public void setL0(double l0) {
		System.out.println("Ustawiam lo " + l0);
		this.l0 = l0;
	}

	public double getFiT0() {
		return fiT0;
	}

	public void setFiT0(double fiT0) {
		System.out.println("Ustawiam fit0 " + fiT0);
		this.fiT0 = fiT0;
	}

	
	public void CountNominalStiffness(Steel steel, Concrete concrete, InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Double mEd, Double nEd) {

		/// podstawowe jednostki zadania : kN, kNm, Mpa, m !
		
		System.out.println("l0: "+ l0);
		System.out.println("fit0: " + fiT0 );


		double fCk = concrete.getFCk() * 1000; // [Gpa] -> [Mpa]
		double eCm = concrete.getECm() * 1000; // [Gpa] -> [Mpa]
		//double fCd = fCk / 1.40;
		double fCd = concrete.getFCd()*1000;
		double eCd = eCm / 1.2;
		double eS = steel.getES() * 1000; /// [Gpa] -> [Mpa]

		double i = Math.sqrt(dimensions.getIc() / dimensions.getAc()); // [m]
		double lambda = l0 / i;
		double n = nEd / (dimensions.getAc() * fCd);
		double k1 = Math.sqrt((fCk / 20000.0));
		double k2 = n * (lambda / 170.0);
		double fiEf = fiT0 * 0.7;
		double eCdEff = (eCd / (1 + fiEf)) * 1000;
		double beta = (Math.sqrt(Math.PI)) / 12; // c0 = 12 - przyjêta najbardziej niekorzystna wartoœæ normowa !
		
		double kS;
		double kC;
		
		
		if (roS1 >= 0.002) {
			if (roS1 >= 0.01) {
				kS = 0;
			} else {
				kS = 1;
			}
		} else {
			roS1 = 0.002;
			kS = 1;
		}

		if (roS1 >= 0.002) {
			if (roS1 >= 0.01) {
				kC = 0.3 / (1 + (0.5 * fiEf));
			} else {
				kC = (k1 * k2) / (1 + fiEf);
			}
		} else {
			roS1 = 0.002;
			kC = (k1 * k2) / (1 + fiEf);
		}

		double iS = roS1 * dimensions.getB() * dimensions.getH() * Math.pow((0.5 * dimensions.getH() - dimensions.getA1()), 2); // [m^4]
		double eI = kC * eCdEff * dimensions.getIc() + kS * eS * iS; // [kNm^2]
		
		System.out.println("eI " + eI);
		double nB = (Math.pow(Math.PI, 2) * eI) / (Math.pow(l0, 2));
		
		/// ta wartosæ momentu musi odpowiadaæ najwiêkszemu mimoœrodowi \|/
		
		//System.out.println("m0Ed " + m0Ed + " beta " + beta + " nB "+ nB + " n0Ed " + n0Ed);
		this.mEd = m0Ed * (1 + (beta / ((nB / n0Ed) - 1))); // finalna wartoœæ momentu do projektowania zbrojenia [kNm]
		System.out.println(mEd);
		//sprawdzenie
		
		}

	public double getmEd() {
		System.out.println("Med w klasie nominalstiffness " + this.mEd);
		return mEd;
	}

	public void setmEd(double mEd) {
		this.mEd = mEd;
	}
	
	
}
