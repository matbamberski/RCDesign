package materials;

public class DimensionsOfCrossSectionOfConcrete {

	private double a1;
	private double a2;
	private double h;
	private double d = h - a1;
	private double b;
	private double bEff;
	private double tW;
	private boolean isColumn;
	private boolean isBeamRectangular;
	private double lEff;
	private double aC;
	private double u;
	private double h0;
	private double sC;
	private double xC;
	private double iC;
	private double wC;
	private double s1;
	private double aC1;
	private double x1;
	private double i1;
	private double x2;
	private double i2;
	private double s2;
	private double cNom;
	private double wLim;
	private double aLim;

	public double getwLim() {
		return wLim;
	}

	public double getaLim() {
		return aLim;
	}

	public void setwLim(double wLim) {
		this.wLim = wLim;
	}

	public void setaLim(double aLim) {
		this.aLim = aLim;
	}

	public double getcNom() {
		return cNom;
	}

	public void setcNom(double cNom) {
		this.cNom = cNom / 1000;
		System.out.println("Cnom " + cNom);
	}

	public void claculateS2(double aS1) {
		s2 = aS1 * (d - x2);
	}

	public double getS2() {
		return s2;
	}

	public void calculateI2(double alfaE, double aS1, double aS2) {
		if (tW != 0) {
			if (x1 > tW) {
				i2 = (bEff - b) * x2 * x2 * x2 / 12 + (bEff - b) * x2 * x2 * x2 / 4 + b * x2 * x2 * x2 / 12 + b * x2 * x2 * x2 / 4 + alfaE * (aS1 * (d - x2) * (d - x2) + aS2 * (x2 - a2) * (x2 - a2));
			} else {
				i2 = bEff * x2 * x2 * x2 / 12 + bEff * x2 * x2 * x2 / 2 / 2 + alfaE * (aS1 * (d - x2) * (d - x2) + aS2 * (x2 - a2) * (x2 - a2));
			}
		}
		if (bEff == 0) {
			i2 = b * x2 * x2 * x2 / 12 + b * x2 * x2 * x2 / 2 / 2 + alfaE * (aS1 * (d - x2) * (d - x2) + aS2 * (x2 - a2) * (x2 - a2));
		} else {
			i2 = bEff * x2 * x2 * x2 / 12 + bEff * x2 * x2 * x2 / 2 / 2 + alfaE * (aS1 * (d - x2) * (d - x2) + aS2 * (x2 - a2) * (x2 - a2));
		}

	}

	public double getI2() {
		return i2;
	}

	public void calculateX2(double alfaE, double aS1, double aS2) {
		if (tW != 0) {
			if (x1 > tW) {
				double a = -((bEff - b) * tW + alfaE * (aS1 + aS2)) / b;
				double c = 2 * ((bEff - b) * tW + alfaE * (aS1 + aS2)) / b;
				double e = 4 * ((bEff - b) * tW * tW + 2 * alfaE * (aS1 * d + aS2 * a2)) / b;
				x2 = a + Math.sqrt(c * c + e) / 2;
				System.out.println("Ac " + aC);

			} else {
				double part = alfaE * (aS1 + aS2) / bEff;
				x2 = -alfaE * (aS1 + aS2) / bEff + Math.sqrt(part * part + 2 * alfaE / bEff * (aS1 * d + aS2 * a2));
			}
		} else {
			if (bEff == 0) {
				double part = 2 * alfaE * (aS1 + aS2) / b;
				x2 = -alfaE * (aS1 + aS2) / b + (Math.sqrt(part * part + 8 * alfaE / b * (aS1 * d + aS2 * a2))) / 2;
			} else {
				double part = 2 * alfaE * (aS1 + aS2) / bEff;
				x2 = -alfaE * (aS1 + aS2) / bEff + (Math.sqrt(part * part + 8 * alfaE / bEff * (aS1 * d + aS2 * a2))) / 2;
			}
		}
	}

	public double getX2() {
		return x2;
	}

	public void calculateS1(double aS1) {
		s1 = aS1 * (d - x1);
	}

	public void calculateI1(double alfaE, double aS1, double aS2) {
		i1 = b * h * h * h / 12 + b * h * (0.5 * h - x1) * (0.5 * h - x1) + (bEff - b) * tW * tW * tW / 12 + (bEff - b) * tW * (x1 - 0.5 * tW) * (x1 - 0.5 * tW)
				+ alfaE * (aS1 * (d - x1) * (d - x1) + aS2 * (x1 - a2) * (x1 - a2));
		;
	}

	public double getI1() {
		return i1;
	}

	public void calculateX1() {
		x1 = s1 / aC1;
	}

	public void calculateAC1(double alfaE, double aS1, double aS2) {
		aC1 = b * h + (bEff - b) * tW + alfaE * (aS1 + aS2);
	}

	public double getAC1() {
		return aC1;
	}

	public void calculateInitialS1(double alfaE, double aS1, double aS2) {
		s1 = b * h * h / 2 + (bEff - b) * tW * tW / 2 + alfaE * (aS1 * d + aS2 * a2);
		;
	}

	public double getS1() {
		return s1;
	}

	public void calculateWc() {
		wC = iC / (h - xC);
	}

	public double getWc() {
		return wC;
	}

	public void calculateIc() {
		iC = b * h * h * h / 12 + b * h * (0.5 * h - xC) * (0.5 * h - xC) + (bEff - b) * (bEff - b) * tW * tW * tW / 12 + (bEff - b) * tW * (xC - 0.5 * tW) * (xC - 0.5 * tW);
	}

	public double getIc() {
		return iC;
	}

	public void calculateXc() {
		xC = sC / aC;
	}

	public double returnXc() {
		return xC;
	}

	public void calculateSc() {
		sC = 0.5 * b * h * h + 0.5 * (bEff - b) * tW * tW;
	}

	public double getSc() {
		return sC;
	}

	public void calculateAc() {
		aC = bEff * tW + b * (h - tW);
		System.out.println("Ac " + aC);
	}

	public double getAc() {
		return aC;
	}

	public void calculateU() {
		double bf = bEff;
		if (bEff == 0) {
			bf = b;
		}
		u = 2 * bf + 2 * (h - tW);
		System.out.println("u " + u);
	}

	public double getU() {
		return u;
	}

	public void calculateh0() {
		calculateAc();
		calculateU();
		h0 = 2 * aC / u;
	}

	public double getH0() {
		return h0;
	}

	public double getlEff() {
		return lEff;
	}

	public void setlEff(double lEff) {
		this.lEff = lEff;
		System.out.println("lEff " + lEff);
	}

	public void setisBeamRectangular(boolean isBeamRectangular) {
		this.isBeamRectangular = isBeamRectangular;
		System.out.println("isBeamRectangular " + isBeamRectangular);
	}

	public boolean getisBeamRectangular() {
		return isBeamRectangular;
	}

	public void setIsColumn(boolean isColumn) {
		this.isColumn = isColumn;
		System.out.println("isColumn" + isColumn);
	}
	
	public boolean getIsColumn() {
		return isColumn;
	}

	public double getB() {
		return b;
	}

	public double getbEff() {
		return bEff;
	}

	public double gettW() {
		return tW;
	}

	public void setB(double b) {
		this.b = b;
		System.out.println("b " + b);
	}

	public void calculateD() {
		d = h - a1;
		System.out.println("d " + d);
	}

	public void setbEff(double bEff) {
		this.bEff = bEff;
		System.out.println("bEff " + bEff);
	}

	public void settW(double tW) {
		this.tW = tW;
		System.out.println("tW " + tW);
	}

	public double getA1() {
		return a1;
	}

	public double getA2() {
		return a2;
	}

	public double getD() {
		return d;
	}

	public double getH() {
		return h;
	}

	public void setA1(double a1) {
		this.a1 = a1;
		System.out.println("a1 " + a1);
	}

	public void setA2(double a2) {
		this.a2 = a2;
		System.out.println("a2 " + a2);
	}

	public void setD(double d) {
		this.d = d;
		System.out.println("d " + d);
	}

	public void setH(double h) {
		this.h = h;
		System.out.println("h " + h);
		calculateD();
	}

}
