package materials;

public class Cement {
	private static int cementType; // 1 == cement klasy S , 2 == cement klasy N
									// , 3==
	// cement klasy R

	private static final double[] alfaDS1 = { 3.0, 4.0, 6.0 };
	private static final double[] alfaDS2 = { 0.13, 0.12, 0.11 };
	private static final double[] alfa = { -1, 0, 1 };

	public Cement(int i) {
		cementType = i;
		dispCementTypeInConsole(i);
	}

	private void dispCementTypeInConsole(int i) {
		if (i == 0) {
			System.out.println("cement klasy s ");
		}
		if (i == 1) {
			System.out.println("cement klasy n ");
		}
		if (i == 2) {
			System.out.println("cement klasy R ");
		}
	}

	public int getCementType() {
		return cementType;
	}

	public double getAlfaDS1() {
		return alfaDS1[cementType];
	}

	public double getAlfaDS2() {
		return alfaDS2[cementType];
	}

	public double getAlfa() {
		return alfa[cementType];
	}
	
	public String getCementTypeName(int i) {
		String name = "";
		if (i == 0) {
			name= "S";
		}
		else if (i == 1) {
			name = "N";
		}
		else if (i == 2) {
			name = "R";
		}
		return name;
	}
}
