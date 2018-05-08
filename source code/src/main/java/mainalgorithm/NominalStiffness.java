package mainalgorithm;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class NominalStiffness {
	
	private double mEd;
	private double l0; // dodaæ przycisk
	private double fiT0; // dodaæ przycisk
	private double roS1;
	
	public void setRoS1(Reinforcement reinforcement) {
		this.roS1 = reinforcement.getReinforcementRatio();
	}
	
	public void setl0(double l0) {
		this.l0 = l0;
	}
	
	public void setfiT0(double fiT0) {
		this.fiT0 = fiT0;
	}

	public void CountNominalStiffness(Steel steel, Concrete concrete, InternalForces internalForces, DimensionsOfCrossSectionOfConcrete dimensions, Double mEd) {

		/// podstawowe jednostki zadania : kN, kNm, Mpa, m !

		int fCk = concrete.getFCk() * 1000; // [Gpa] -> [Mpa]
		int eCm = concrete.getECm() * 1000; // [Gpa] -> [Mpa]
		double fCd = fCk / 1.40;
		double eCd = eCm / 1.2;
		double eS = steel.getES() * 1000; /// [Gpa] -> [Mpa]

		//////////////////// Dzia³ania matematyczne

		//aC = h * b;
		//iC = (b * Math.pow((h), 3)) / 12; // [m^4]
		double i = Math.sqrt(dimensions.getIc() / dimensions.getAc()); // [m]
		double lambda = l0 / i;
		double n = internalForces.getnEd() / (dimensions.getAc() * fCd);
		double k1 = Math.sqrt((fCk / 20000.0));
		double k2 = n * (lambda / 170.0);
		double fiEf = fiT0 * 0.7;
		double eCdEff = (eCd / (1 + fiEf)) * 1000;
		double beta = (Math.sqrt(Math.PI)) / 12; // c0 = 12 - przyjêta najbardziej niekorzystna wartoœæ normowa !
		
		int kS;
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
		

		double nB = (Math.pow(Math.PI, 2) * eI) / (Math.pow(l0, 2));
		
		/// ta wartosæ momentu musi odpowiadaæ najwiêkszemu mimoœrodowi \|/
		mEd = internalForces.getmEd() * (1 + (beta / ((nB / internalForces.getnEd()) - 1))); // finalna wartoœæ momentu do projektowania zbrojenia [kNm]
		
		//sprawdzenie
		
		}
	public double getMEd() {
		return mEd;

	}
}
