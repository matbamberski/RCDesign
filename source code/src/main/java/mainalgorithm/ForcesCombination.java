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
	private boolean medNegativ;
	private double aS1req;
	private double aS2req;
	private double aS1reqUnsymmetrical;
	private double aS2reqUnsymmetrical;
	private double asSymmetricalReq;
	private double asSymmetricalProv;
	private double aS1prov;
	private double aS2prov;
	private double nRd_;
	private double mRd_;
	
	private SimpleStringProperty Medtab;
	private SimpleStringProperty Mrdtab;
	private SimpleStringProperty Nedtab;
	private SimpleStringProperty Nrdtab;
	
	public ForcesCombination(double M, double N) {
		this.M = M;
		this.N = N;
		countE();
		setMedNegativ(false);
	}
	
	public void countE() {
		if (this.N!=0) {
			this.e = Math.abs(M/N);
		} else this.e = 0;
	}

	public double getE() {
		countE();
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
		this.Nedtab = new SimpleStringProperty(String.format("%.3f", N));
		this.Nrdtab = new SimpleStringProperty(String.format("%.3f", nRd));
		this.Medtab = new SimpleStringProperty(String.format("%.3f", mStiff));
		if (isMedNegativ()) {
			this.Mrdtab = new SimpleStringProperty(String.format("%.3f", -mRd));
		} else {
			this.Mrdtab = new SimpleStringProperty(String.format("%.3f", mRd));
		}
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

	public boolean isMedNegativ() {
		return medNegativ;
	}

	public void setMedNegativ(boolean medNegativ) {
		this.medNegativ = medNegativ;
	}
	
	public void swapAs1WithAs2() {
		double tmpAs = aS1reqUnsymmetrical;
		setaS1reqUnsymmetrical(aS2reqUnsymmetrical);
		setaS2reqUnsymmetrical(tmpAs);
	}

	public double getaS1reqUnsymmetrical() {
		return aS1reqUnsymmetrical;
	}

	public void setaS1reqUnsymmetrical(double aS1reqUnsymmetrical) {
		this.aS1reqUnsymmetrical = aS1reqUnsymmetrical;
	}

	public double getaS2reqUnsymmetrical() {
		return aS2reqUnsymmetrical;
	}

	public void setaS2reqUnsymmetrical(double aS2reqUnsymmetrical) {
		this.aS2reqUnsymmetrical = aS2reqUnsymmetrical;
	}

	public double getaS1prov() {
		return aS1prov;
	}

	public void setaS1prov(double aS1prov) {
		this.aS1prov = aS1prov;
	}

	public double getaS2prov() {
		return aS2prov;
	}

	public void setaS2prov(double aS2prov) {
		this.aS2prov = aS2prov;
	}

	public double getaS1req() {
		return aS1req;
	}

	public void setaS1req(double aS1req) {
		this.aS1req = aS1req;
	}

	public double getaS2req() {
		return aS2req;
	}

	public void setaS2req(double aS2req) {
		this.aS2req = aS2req;
	}

	public double getAsSymmetricalReq() {
		return asSymmetricalReq;
	}

	public void setAsSymmetricalReq(double asSymmetricalReq) {
		this.asSymmetricalReq = asSymmetricalReq;
	}

	public double getAsSymmetricalProv() {
		return asSymmetricalProv;
	}

	public void setAsSymmetricalProv(double asSymmetricalProv) {
		this.asSymmetricalProv = asSymmetricalProv;
	}

	public double getnRd_() {
		return nRd_;
	}

	public void setnRd_(double nRd_) {
		this.nRd_ = nRd_;
	}

	public double getmRd_() {
		return mRd_;
	}

	public void setmRd_(double mRd_) {
		this.mRd_ = mRd_;
	}

	public void setM(double m) {
		M = m;
	}

	public void setN(double n) {
		N = n;
	}

	
	
	
	
}
