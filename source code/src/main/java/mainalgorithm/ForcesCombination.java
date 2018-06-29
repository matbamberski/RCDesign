package mainalgorithm;

import javafx.beans.property.SimpleStringProperty;

public class ForcesCombination {
	private SimpleStringProperty name;
	private double M;
	private double N;
	private double mStiff;
	private double e;
	private double mRd;
	private double nRd;
	
	private SimpleStringProperty Medtab;
	private SimpleStringProperty Mrdtab;
	private SimpleStringProperty Nedtab;
	private SimpleStringProperty Nrdtab;
	
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

	public double getmStiff() {
		return mStiff;
	}

	public void setmStiff(double mStiff) {
		this.mStiff = mStiff;
	}

	public double getmRd() {
		return mRd;
	}

	public void setmRd(double mRd) {
		this.mRd = mRd;
	}

	public double getnRd() {
		return nRd;
	}

	public void setnRd(double nRd) {
		this.nRd = nRd;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String newname) {
		name.set(newname);
	}
	
	public void newName(String newname) {
		this.name = new SimpleStringProperty(newname);
	}
	
	public void setTableValues() {
		this.Medtab = new SimpleStringProperty(String.format("%.3f", mStiff));
		this.Nedtab = new SimpleStringProperty(String.format("%.3f", N));
		this.Mrdtab = new SimpleStringProperty(String.format("%.3f", mRd));
		this.Nrdtab = new SimpleStringProperty(String.format("%.3f", nRd));
	}

	public String getMedtab() {
		return Medtab.get();
	}

	public void setMedtab(String medtab) {
		Medtab.set(medtab);
	}

	public String getMrdtab() {
		return Mrdtab.get();
	}

	public void setMrdtab(String mrdtab) {
		Mrdtab.set(mrdtab);
	}

	public String getNedtab() {
		return Nedtab.get();
	}

	public void setNedtab(String nedtab) {
		Nedtab.set(nedtab);
	}

	public String getNrdtab() {
		return Nrdtab.get();
	}

	public void setNrdtab(String nrdtab) {
		Nrdtab.set(nrdtab);
	}
	
	
	
}
