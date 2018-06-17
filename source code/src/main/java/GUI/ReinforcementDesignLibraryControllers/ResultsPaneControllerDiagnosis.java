package GUI.ReinforcementDesignLibraryControllers;

import SLS.Sls;
import diagnosis.DiagnosisMainAlgorithm;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import util.OutputFormatter;

public class ResultsPaneControllerDiagnosis {
	private Reinforcement reinforcement;
	private InternalForces internalForces;
	private Sls sls;
	
	private double mRd1D;
	private double mRd2D;
	private double mRd3D;
	private double mRd4D;
	private double nRd1D;
	private double nRd2D;
	private double nRd3D;
	private double nRd4D;
	private double mRd1R;
	private double mRd2R;
	private double mRd3R;
	private double mRd4R;
	private double nRd1R;
	private double nRd2R;
	private double nRd3R;
	private double nRd4R;

	public void setmRd1D(double mRd1D) {
		this.mRd1D = mRd1D;
	}

	public void setmRd2D(double mRd2D) {
		this.mRd2D = mRd2D;
	}

	public void setmRd3D(double mRd3D) {
		this.mRd3D = mRd3D;
	}

	public void setmRd4D(double mRd4D) {
		this.mRd4D = mRd4D;
	}

	public void setnRd1D(double nRd1D) {
		this.nRd1D = nRd1D;
	}

	public void setnRd2D(double nRd2D) {
		this.nRd2D = nRd2D;
	}

	public void setnRd3D(double nRd3D) {
		this.nRd3D = nRd3D;
	}

	public void setnRd4D(double nRd4D) {
		this.nRd4D = nRd4D;
	}

	public void setmRd1R(double mRd1R) {
		this.mRd1R = mRd1R;
	}

	public void setmRd2R(double mRd2R) {
		this.mRd2R = mRd2R;
	}

	public void setmRd3R(double mRd3R) {
		this.mRd3R = mRd3R;
	}

	public void setmRd4R(double mRd4R) {
		this.mRd4R = mRd4R;
	}

	public void setnRd1R(double nRd1R) {
		this.nRd1R = nRd1R;
	}

	public void setnRd2R(double nRd2R) {
		this.nRd2R = nRd2R;
	}

	public void setnRd3R(double nRd3R) {
		this.nRd3R = nRd3R;
	}

	public void setnRd4R(double nRd4R) {
		this.nRd4R = nRd4R;
	}

	private Label gridLabel00;
	private Label gridLabel01;
	private Label gridLabel02;
	private Label gridLabel03;
	private Label gridLabel04;
	private Label gridLabel05;
	private Label gridLabel06;
	private Label gridLabel07;
	private Label gridLabel08;
	private Label gridLabel09;
	private Label gridLabel010;
	private Label gridLabel011;
	private Label gridLabel012;
	private Label gridLabel013;
	private Label gridLabel014;
	private Label gridLabel015;
	private Label gridLabel016;
	private Label gridLabel017;
	private Label gridLabel018;
	private Label gridLabel019;
	private Label gridLabel020;

	private Label gridLabel10;
	private Label gridLabel11;
	private Label gridLabel12;
	private Label gridLabel13;
	private Label gridLabel14;
	private Label gridLabel15;
	private Label gridLabel16;
	private Label gridLabel17;
	private Label gridLabel18;
	private Label gridLabel19;
	private Label gridLabel110;
	private Label gridLabel111;
	private Label gridLabel112;
	private Label gridLabel113;
	private Label gridLabel114;
	private Label gridLabel115;
	private Label gridLabel116;
	private Label gridLabel117;
	private Label gridLabel118;
	private Label gridLabel119;
	private Label gridLabel120;

	private Label gridLabel20;
	private Label gridLabel21;
	private Label gridLabel22;
	private Label gridLabel23;
	private Label gridLabel24;
	private Label gridLabel25;
	private Label gridLabel26;
	private Label gridLabel27;
	private Label gridLabel28;
	private Label gridLabel29;
	private Label gridLabel210;
	private Label gridLabel211;
	private Label gridLabel212;
	private Label gridLabel213;
	private Label gridLabel214;
	private Label gridLabel215;
	private Label gridLabel216;
	private Label gridLabel217;
	private Label gridLabel218;
	private Label gridLabel219;
	private Label gridLabel220;

	private Label gridLabel30;
	private Label gridLabel31;
	private Label gridLabel32;
	private Label gridLabel33;
	private Label gridLabel34;
	private Label gridLabel35;
	private Label gridLabel36;
	private Label gridLabel37;
	private Label gridLabel38;
	private Label gridLabel39;
	private Label gridLabel310;
	private Label gridLabel311;
	private Label gridLabel312;
	private Label gridLabel313;
	private Label gridLabel314;
	private Label gridLabel315;
	private Label gridLabel316;
	private Label gridLabel317;
	private Label gridLabel318;
	private Label gridLabel319;
	private Label gridLabel320;

	private Label stanGranicznyNosnosciNequal0Label;
	private Line leftSGNNequal0Line;
	private Line rightSGNNequal0Line;
	private Label zbrojeniePodluzneNequal0Label;
	private Line leftZbrojeniePodluzneNequal0Line;
	private Line rightZbrojeniePodluzneNequal0Line;
	private Label zbrojeniePoprzeczneNequal0Label;
	private Line leftZbrojeniePoprzeczneNequal0Line;
	private Line rightZbrojeniePoprzeczneNequal0Line;
	private Label stanGranicznyUzytkowalnosciNequal0Label1;
	private Line leftSGUNequal0Line1;
	private Line rightSGUNequal0Line1;
	private Label stanGranicznyUzytkowalnosciNequal0Label2;
	private Line leftSGUNequal0Line2;
	private Line rightSGUNequal0Line2;

	private Label zbrojeniePoprzeczneNNotequal0Label;
	private Line leftZbrojeniePoprzeczneNNotequal0Line;
	private Line rightZbrojeniePoprzeczneNNotequal0Line;

	private DiagnosisMainAlgorithm diagnosisMainAlgorithm;

	public ResultsPaneControllerDiagnosis(Reinforcement reinforcement, InternalForces internalForces, Sls sls, Label gridLabel00, Label gridLabel01, Label gridLabel02, Label gridLabel03,
			Label gridLabel04, Label gridLabel05, Label gridLabel06, Label gridLabel07, Label gridLabel08, Label gridLabel09, Label gridLabel010, Label gridLabel011, Label gridLabel012,
			Label gridLabel013, Label gridLabel014, Label gridLabel015, Label gridLabel016, Label gridLabel017, Label gridLabel018, Label gridLabel019, Label gridLabel020, Label gridLabel10,
			Label gridLabel11, Label gridLabel12, Label gridLabel13, Label gridLabel14, Label gridLabel15, Label gridLabel16, Label gridLabel17, Label gridLabel18, Label gridLabel19,
			Label gridLabel110, Label gridLabel111, Label gridLabel112, Label gridLabel113, Label gridLabel114, Label gridLabel115, Label gridLabel116, Label gridLabel117, Label gridLabel118,
			Label gridLabel119, Label gridLabel120, Label gridLabel20, Label gridLabel21, Label gridLabel22, Label gridLabel23, Label gridLabel24, Label gridLabel25, Label gridLabel26,
			Label gridLabel27, Label gridLabel28, Label gridLabel29, Label gridLabel210, Label gridLabel211, Label gridLabel212, Label gridLabel213, Label gridLabel214, Label gridLabel215,
			Label gridLabel216, Label gridLabel217, Label gridLabel218, Label gridLabel219, Label gridLabel220, Label gridLabel30, Label gridLabel31, Label gridLabel32, Label gridLabel33,
			Label gridLabel34, Label gridLabel35, Label gridLabel36, Label gridLabel37, Label gridLabel38, Label gridLabel39, Label gridLabel310, Label gridLabel311, Label gridLabel312,
			Label gridLabel313, Label gridLabel314, Label gridLabel315, Label gridLabel316, Label gridLabel317, Label gridLabel318, Label gridLabel319, Label gridLabel320,
			DiagnosisMainAlgorithm diagnosisMainAlgorithm, Label stanGranicznyNosnosciNequal0Label, Line leftSGNNequal0Line, Line rightSGNNequal0Line, Label zbrojeniePodluzneNequal0Label,
			Line leftZbrojeniePodluzneNequal0Line, Line rightZbrojeniePodluzneNequal0Line, Label zbrojeniePoprzeczneNequal0Label, Line leftZbrojeniePoprzeczneNequal0Line,
			Line rightZbrojeniePoprzeczneNequal0Line, Label stanGranicznyUzytkowalnosciNequal0Label1, Line leftSGUNequal0Line1, Line rightSGUNequal0Line1,
			Label stanGranicznyUzytkowalnosciNequal0Label2, Line leftSGUNequal0Line2, Line rightSGUNequal0Line2,

			Label zbrojeniePoprzeczneNNotequal0Label, Line leftZbrojeniePoprzeczneNNotequal0Line, Line rightZbrojeniePoprzeczneNNotequal0Line) {

		this.reinforcement = reinforcement;
		this.internalForces = internalForces;
		this.sls = sls;
		this.gridLabel00 = gridLabel00;
		this.gridLabel01 = gridLabel01;
		this.gridLabel02 = gridLabel02;
		this.gridLabel03 = gridLabel03;
		this.gridLabel04 = gridLabel04;
		this.gridLabel05 = gridLabel05;
		this.gridLabel06 = gridLabel06;
		this.gridLabel07 = gridLabel07;
		this.gridLabel08 = gridLabel08;
		this.gridLabel09 = gridLabel09;
		this.gridLabel010 = gridLabel010;
		this.gridLabel011 = gridLabel011;
		this.gridLabel012 = gridLabel012;
		this.gridLabel013 = gridLabel013;
		this.gridLabel014 = gridLabel014;
		this.gridLabel015 = gridLabel015;
		this.gridLabel016 = gridLabel016;
		this.gridLabel017 = gridLabel017;
		this.gridLabel018 = gridLabel018;
		this.gridLabel019 = gridLabel019;
		this.gridLabel020 = gridLabel020;

		this.gridLabel10 = gridLabel10;
		this.gridLabel11 = gridLabel11;
		this.gridLabel12 = gridLabel12;
		this.gridLabel13 = gridLabel13;
		this.gridLabel14 = gridLabel14;
		this.gridLabel15 = gridLabel15;
		this.gridLabel16 = gridLabel16;
		this.gridLabel17 = gridLabel17;
		this.gridLabel18 = gridLabel18;
		this.gridLabel19 = gridLabel19;
		this.gridLabel110 = gridLabel110;
		this.gridLabel111 = gridLabel111;
		this.gridLabel112 = gridLabel112;
		this.gridLabel113 = gridLabel113;
		this.gridLabel114 = gridLabel114;
		this.gridLabel115 = gridLabel115;
		this.gridLabel116 = gridLabel116;
		this.gridLabel117 = gridLabel117;
		this.gridLabel118 = gridLabel118;
		this.gridLabel119 = gridLabel119;
		this.gridLabel120 = gridLabel120;

		this.gridLabel20 = gridLabel20;
		this.gridLabel21 = gridLabel21;
		this.gridLabel22 = gridLabel22;
		this.gridLabel23 = gridLabel23;
		this.gridLabel24 = gridLabel24;
		this.gridLabel25 = gridLabel25;
		this.gridLabel26 = gridLabel26;
		this.gridLabel27 = gridLabel27;
		this.gridLabel28 = gridLabel28;
		this.gridLabel29 = gridLabel29;
		this.gridLabel210 = gridLabel210;
		this.gridLabel211 = gridLabel211;
		this.gridLabel212 = gridLabel212;
		this.gridLabel213 = gridLabel213;
		this.gridLabel214 = gridLabel214;
		this.gridLabel215 = gridLabel215;
		this.gridLabel216 = gridLabel216;
		this.gridLabel217 = gridLabel217;
		this.gridLabel218 = gridLabel218;
		this.gridLabel219 = gridLabel219;
		this.gridLabel220 = gridLabel220;

		this.gridLabel30 = gridLabel30;
		this.gridLabel31 = gridLabel31;
		this.gridLabel32 = gridLabel32;
		this.gridLabel33 = gridLabel33;
		this.gridLabel34 = gridLabel34;
		this.gridLabel35 = gridLabel35;
		this.gridLabel36 = gridLabel36;
		this.gridLabel37 = gridLabel37;
		this.gridLabel38 = gridLabel38;
		this.gridLabel39 = gridLabel39;
		this.gridLabel310 = gridLabel310;
		this.gridLabel311 = gridLabel311;
		this.gridLabel312 = gridLabel312;
		this.gridLabel313 = gridLabel313;
		this.gridLabel314 = gridLabel314;
		this.gridLabel315 = gridLabel315;
		this.gridLabel316 = gridLabel316;
		this.gridLabel317 = gridLabel317;
		this.gridLabel318 = gridLabel318;
		this.gridLabel319 = gridLabel319;
		this.gridLabel320 = gridLabel320;

		this.diagnosisMainAlgorithm = diagnosisMainAlgorithm;

		this.stanGranicznyNosnosciNequal0Label = stanGranicznyNosnosciNequal0Label;
		this.leftSGNNequal0Line = leftSGNNequal0Line;
		this.rightSGNNequal0Line = rightSGNNequal0Line;
		this.zbrojeniePodluzneNequal0Label = zbrojeniePodluzneNequal0Label;
		this.leftZbrojeniePodluzneNequal0Line = leftZbrojeniePodluzneNequal0Line;
		this.rightZbrojeniePodluzneNequal0Line = rightZbrojeniePodluzneNequal0Line;
		this.zbrojeniePoprzeczneNequal0Label = zbrojeniePoprzeczneNequal0Label;
		this.leftZbrojeniePoprzeczneNequal0Line = leftZbrojeniePoprzeczneNequal0Line;
		this.rightZbrojeniePoprzeczneNequal0Line = rightZbrojeniePoprzeczneNequal0Line;
		this.stanGranicznyUzytkowalnosciNequal0Label1 = stanGranicznyUzytkowalnosciNequal0Label1;
		this.leftSGUNequal0Line1 = leftSGUNequal0Line1;
		this.rightSGUNequal0Line1 = rightSGUNequal0Line1;
		this.stanGranicznyUzytkowalnosciNequal0Label2 = stanGranicznyUzytkowalnosciNequal0Label2;
		this.leftSGUNequal0Line2 = leftSGUNequal0Line2;
		this.rightSGUNequal0Line2 = rightSGUNequal0Line2;

		this.zbrojeniePoprzeczneNNotequal0Label = zbrojeniePoprzeczneNNotequal0Label;
		this.leftZbrojeniePoprzeczneNNotequal0Line = leftZbrojeniePoprzeczneNNotequal0Line;
		this.rightZbrojeniePoprzeczneNNotequal0Line = rightZbrojeniePoprzeczneNNotequal0Line;

		hideResults();
	}

	private void hideResults() {
		gridLabel00.setText("");
		gridLabel01.setText("");
		gridLabel02.setText("");
		gridLabel03.setText("");
		gridLabel04.setText("");
		gridLabel05.setText("");
		gridLabel06.setText("");
		gridLabel07.setText("");
		gridLabel08.setText("");
		gridLabel09.setText("");
		gridLabel010.setText("");
		gridLabel011.setText("");
		gridLabel012.setText("");
		gridLabel013.setText("");
		gridLabel014.setText("");
		gridLabel015.setText("");
		gridLabel016.setText("");
		gridLabel017.setText("");
		gridLabel018.setText("");
		gridLabel019.setText("");
		gridLabel020.setText("");

		gridLabel10.setText("");
		gridLabel11.setText("");
		gridLabel12.setText("");
		gridLabel13.setText("");
		gridLabel14.setText("");
		gridLabel15.setText("");
		gridLabel16.setText("");
		gridLabel17.setText("");
		gridLabel18.setText("");
		gridLabel19.setText("");
		gridLabel110.setText("");
		gridLabel111.setText("");
		gridLabel112.setText("");
		gridLabel113.setText("");
		gridLabel114.setText("");
		gridLabel115.setText("");
		gridLabel116.setText("");
		gridLabel117.setText("");
		gridLabel118.setText("");
		gridLabel119.setText("");
		gridLabel120.setText("");

		gridLabel20.setText("");
		gridLabel21.setText("");
		gridLabel22.setText("");
		gridLabel23.setText("");
		gridLabel24.setText("");
		gridLabel25.setText("");
		gridLabel26.setText("");
		gridLabel27.setText("");
		gridLabel28.setText("");
		gridLabel29.setText("");
		gridLabel210.setText("");
		gridLabel211.setText("");
		gridLabel212.setText("");
		gridLabel213.setText("");
		gridLabel214.setText("");
		gridLabel215.setText("");
		gridLabel216.setText("");
		gridLabel217.setText("");
		gridLabel218.setText("");
		gridLabel219.setText("");
		gridLabel220.setText("");

		gridLabel30.setText("");
		gridLabel31.setText("");
		gridLabel32.setText("");
		gridLabel33.setText("");
		gridLabel34.setText("");
		gridLabel35.setText("");
		gridLabel36.setText("");
		gridLabel37.setText("");
		gridLabel38.setText("");
		gridLabel39.setText("");
		gridLabel310.setText("");
		gridLabel311.setText("");
		gridLabel312.setText("");
		gridLabel313.setText("");
		gridLabel314.setText("");
		gridLabel315.setText("");
		gridLabel316.setText("");
		gridLabel317.setText("");
		gridLabel318.setText("");
		gridLabel319.setText("");
		gridLabel320.setText("");

		stanGranicznyNosnosciNequal0Label.setVisible(false);
		leftSGNNequal0Line.setVisible(false);
		rightSGNNequal0Line.setVisible(false);
		zbrojeniePodluzneNequal0Label.setVisible(false);
		leftZbrojeniePodluzneNequal0Line.setVisible(false);
		rightZbrojeniePodluzneNequal0Line.setVisible(false);
		zbrojeniePoprzeczneNequal0Label.setVisible(false);
		leftZbrojeniePoprzeczneNequal0Line.setVisible(false);
		rightZbrojeniePoprzeczneNequal0Line.setVisible(false);
		stanGranicznyUzytkowalnosciNequal0Label1.setVisible(false);
		leftSGUNequal0Line1.setVisible(false);
		rightSGUNequal0Line1.setVisible(false);
		stanGranicznyUzytkowalnosciNequal0Label2.setVisible(false);
		leftSGUNequal0Line2.setVisible(false);
		rightSGUNequal0Line2.setVisible(false);

		zbrojeniePoprzeczneNNotequal0Label.setVisible(false);
		leftZbrojeniePoprzeczneNNotequal0Line.setVisible(false);
		rightZbrojeniePoprzeczneNNotequal0Line.setVisible(false);
	}

	protected void dispResults() {
		checkIfNedIsEqualto0AndDispResults();
		checkIfNedIsNotEqualto0AndDispResults();
	}

	private void checkIfNedIsEqualto0AndDispResults() {
		if (internalForces.getnEd() == 0) {
			dispResultsWhenNedIsEqual0();
		}
	}

	private void checkIfNedIsNotEqualto0AndDispResults() {
		if (internalForces.getnEd() != 0) {
			dispResultsWhenNedIsNotEqual0();
		}
	}

	private void dispResultsWhenNedIsNotEqual0() {
		stanGranicznyNosnosciNequal0Label.setVisible(true);
		leftSGNNequal0Line.setVisible(true);
		rightSGNNequal0Line.setVisible(true);
		zbrojeniePodluzneNequal0Label.setVisible(true);
		leftZbrojeniePodluzneNequal0Line.setVisible(true);
		rightZbrojeniePodluzneNequal0Line.setVisible(true);
		zbrojeniePoprzeczneNequal0Label.setVisible(false);
		leftZbrojeniePoprzeczneNequal0Line.setVisible(false);
		rightZbrojeniePoprzeczneNequal0Line.setVisible(false);
		stanGranicznyUzytkowalnosciNequal0Label1.setVisible(false);
		leftSGUNequal0Line1.setVisible(false);
		rightSGUNequal0Line1.setVisible(false);
		stanGranicznyUzytkowalnosciNequal0Label2.setVisible(false);
		leftSGUNequal0Line2.setVisible(false);
		rightSGUNequal0Line2.setVisible(false);

		zbrojeniePoprzeczneNNotequal0Label.setVisible(true);
		leftZbrojeniePoprzeczneNNotequal0Line.setVisible(true);
		rightZbrojeniePoprzeczneNNotequal0Line.setVisible(true);

		gridLabel00.setText("");
		gridLabel01.setText("");
		gridLabel02.setText("");
		gridLabel03.setText("As1");
		gridLabel04.setText("As2");
		gridLabel05.setText("\u03C1");
		
		if (internalForces.getMomentMmax() != 0) {
			gridLabel06.setText("MRd1");
			gridLabel07.setText("NRd1");
		} else if (internalForces.getNormalnaMmax() !=0){
			gridLabel06.setText("MRd1");
			gridLabel07.setText("NRd1");
		} else {
			gridLabel06.setText("MRd");
			gridLabel07.setText("NRd");
		}
		
		if (internalForces.getMomentMmin() != 0) {
			gridLabel08.setText("MRd2");
			gridLabel09.setText("NRd2");
		} else if (internalForces.getNormalnaMmin() !=0){
			gridLabel08.setText("MRd2");
			gridLabel09.setText("NRd2");
		} else {
			gridLabel08.setText("");
			gridLabel09.setText("");
		}
		
		if (internalForces.getMomentNmax() != 0) {
			gridLabel010.setText("MRd3");
			gridLabel011.setText("NRd3");
		} else if (internalForces.getNormalnaNmax() !=0){
			gridLabel010.setText("MRd3");
			gridLabel011.setText("NRd3");
		} else {
			gridLabel010.setText("");
			gridLabel011.setText("");
		}
		
		if (internalForces.getMomentNmin() != 0) {
			gridLabel012.setText("MRd4");
			gridLabel013.setText("NRd4");
		} else if (internalForces.getNormalnaNmin() !=0){
			gridLabel012.setText("MRd4");
			gridLabel013.setText("NRd4");
		} else {
			gridLabel012.setText("");
			gridLabel013.setText("");
		}
			gridLabel014.setText("");
		
		// zbrojenie poprzeczne
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel015.setText("s1 ");
			gridLabel016.setText("s2 ");
			gridLabel017.setText("Vrd");
		} else {
			gridLabel015.setText("s");
			gridLabel016.setText("VRd");
			gridLabel017.setText("");
		}
		
		gridLabel018.setText("");
		gridLabel019.setText("");
		gridLabel020.setText("");
		

		gridLabel10.setText("");
		gridLabel11.setText("");
		gridLabel12.setText("Obliczeniowe");
		gridLabel13.setText((OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredSymmetricalAS1())));
		gridLabel14.setText((OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredSymmetricalAS2())));
		gridLabel15.setText(OutputFormatter.showPercentages(reinforcement.getDegreeOfComputedSymmetricalReinforcement()));
		
		if (internalForces.getMomentMmax() != 0) {
			gridLabel16.setText(OutputFormatter.diagnosisMed(mRd1R));//MRd1R
			gridLabel17.setText(OutputFormatter.diagnosisVedAndNed(nRd1R));//NRd1R
		} else if (internalForces.getNormalnaMmax() !=0){
			gridLabel16.setText(OutputFormatter.diagnosisMed(mRd1R));//MRd1R
			gridLabel17.setText(OutputFormatter.diagnosisVedAndNed(nRd1R));//NRd1R
		} else {
			gridLabel16.setText(OutputFormatter.diagnosisMed(diagnosisMainAlgorithm.getmRdRequiredSymmetrical()));
			gridLabel17.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getnRdRequiredSymmetrical()));
		}
		
		if (internalForces.getMomentMmin() != 0) {
			gridLabel18.setText(OutputFormatter.diagnosisMed(mRd2R));//MRd2R
			gridLabel19.setText(OutputFormatter.diagnosisVedAndNed(nRd2R));//NRd2R
		} else if (internalForces.getNormalnaMmin() !=0){
			gridLabel18.setText(OutputFormatter.diagnosisMed(mRd2R));//MRd2R
			gridLabel19.setText(OutputFormatter.diagnosisVedAndNed(nRd2R));//NRd2R
		} else {
			gridLabel18.setText("");
			gridLabel19.setText("");
		}
		
		if (internalForces.getMomentNmax() != 0) {
			gridLabel110.setText(OutputFormatter.diagnosisMed(mRd3R));//MRd3R
			gridLabel111.setText(OutputFormatter.diagnosisVedAndNed(nRd3R));//NRd3R
		} else if (internalForces.getNormalnaNmax() !=0){
			gridLabel110.setText(OutputFormatter.diagnosisMed(mRd3R));//MRd3R
			gridLabel111.setText(OutputFormatter.diagnosisVedAndNed(nRd3R));//NRd3R
		} else {
			gridLabel110.setText("");
			gridLabel111.setText("");
		}
		
		if (internalForces.getMomentNmin() != 0) {
			gridLabel112.setText(OutputFormatter.diagnosisMed(mRd4R));//MRd4R
			gridLabel113.setText(OutputFormatter.diagnosisVedAndNed(nRd4R));//NRd4R
		} else if (internalForces.getNormalnaNmin() !=0){
			gridLabel112.setText(OutputFormatter.diagnosisMed(mRd4R));//MRd4R
			gridLabel113.setText(OutputFormatter.diagnosisVedAndNed(nRd4R));//NRd4R
		} else {
			gridLabel112.setText("");
			gridLabel113.setText("");
		}
		gridLabel114.setText("");
		
		// zbrojenie poprzeczne
		
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel115.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel116.setText(OutputFormatter.s1s2(reinforcement.getS2Required()));
			gridLabel117.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdRequired()));
		} else {
			gridLabel115.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel116.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdRequired()));
			gridLabel117.setText("");
		}

		gridLabel118.setText("");
		gridLabel119.setText("");
		gridLabel120.setText("");

		gridLabel20.setText("");
		gridLabel21.setText("");
		gridLabel22.setText("Zastosowane");
		gridLabel23.setText((OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS1())));
		gridLabel24.setText((OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS2())));
		gridLabel25.setText(OutputFormatter.showPercentages(reinforcement.getDegreeOfDesignedSymmetricalReinforcement()));

		
		
		if (internalForces.getMomentMmax() != 0) {
			gridLabel26.setText(OutputFormatter.diagnosisMed(mRd1D));//MRd1D
			gridLabel27.setText(OutputFormatter.diagnosisVedAndNed(nRd1D));//NRd1D
		} else if (internalForces.getNormalnaMmax() !=0){
			gridLabel26.setText(OutputFormatter.diagnosisMed(mRd1D));//MRd1D
			gridLabel27.setText(OutputFormatter.diagnosisVedAndNed(nRd1D));//NRd1D
		} else {
			gridLabel26.setText(OutputFormatter.diagnosisMed(diagnosisMainAlgorithm.getmRdDesignedSymmetrical()));
			gridLabel27.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getnRdDesignedSymmetrical()));
		}
		
		if (internalForces.getMomentMmin() != 0) {
			gridLabel28.setText(OutputFormatter.diagnosisMed(mRd2D));//MRd2D
			gridLabel29.setText(OutputFormatter.diagnosisVedAndNed(nRd2D));//NRd2D
		} else if (internalForces.getNormalnaMmin() !=0){
			gridLabel28.setText(OutputFormatter.diagnosisMed(mRd2D));//MRd2D
			gridLabel29.setText(OutputFormatter.diagnosisVedAndNed(nRd2D));//NRd2D
		} else {
			gridLabel28.setText("");
			gridLabel29.setText("");
		}
		
		if (internalForces.getMomentNmax() != 0) {
			gridLabel210.setText(OutputFormatter.diagnosisMed(mRd3D));//MRd3D
			gridLabel211.setText(OutputFormatter.diagnosisVedAndNed(nRd3D));//NRd3D
		} else if (internalForces.getNormalnaNmax() !=0){
			gridLabel210.setText(OutputFormatter.diagnosisMed(mRd3D));//MRd3D
			gridLabel211.setText(OutputFormatter.diagnosisVedAndNed(nRd3D));//NRd3D
		} else {
			gridLabel210.setText("");
			gridLabel211.setText("");
		}
		
		if (internalForces.getMomentNmin() != 0) {
			gridLabel212.setText(OutputFormatter.diagnosisMed(mRd4D));//MRd4D
			gridLabel213.setText(OutputFormatter.diagnosisVedAndNed(nRd4D));//NRd4D
		} else if (internalForces.getNormalnaNmin() !=0){
			gridLabel212.setText(OutputFormatter.diagnosisMed(mRd4D));//MRd4D
			gridLabel213.setText(OutputFormatter.diagnosisVedAndNed(nRd4D));//NRd4D
		} else {
			gridLabel212.setText("");
			gridLabel213.setText("");
		}
		
		gridLabel214.setText("");

		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel215.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel216.setText(OutputFormatter.s1s2(reinforcement.getS2Designed()));
			gridLabel217.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdDesigned()));
		} else {
			gridLabel215.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel216.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdDesigned()));
			gridLabel217.setText("");
		}
		
		gridLabel218.setText("");
		gridLabel219.setText("");
		gridLabel220.setText("");

		gridLabel30.setText("");
		gridLabel31.setText("");
		gridLabel32.setText("Liczba prêtów");
		gridLabel33.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS1()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS1()));
		gridLabel34.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS2()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS2()));
		gridLabel35.setText("");
		gridLabel36.setText("");
		gridLabel37.setText("");
		gridLabel38.setText("");
		gridLabel39.setText("");
		gridLabel310.setText("");
		gridLabel311.setText("");
		gridLabel312.setText("");
		gridLabel313.setText("");
		gridLabel314.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel315.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel316.setText(OutputFormatter.formatAs1As2(reinforcement.getnS2Designed()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getaSW2Diameter()));
		} else {
			gridLabel315.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel316.setText("");
		}
		
		gridLabel317.setText("");
		gridLabel318.setText("");
		gridLabel319.setText("");
		gridLabel320.setText("");

	}

	private void dispResultsWhenNedIsEqual0() {
		stanGranicznyNosnosciNequal0Label.setVisible(true);
		leftSGNNequal0Line.setVisible(true);
		rightSGNNequal0Line.setVisible(true);
		zbrojeniePodluzneNequal0Label.setVisible(true);
		leftZbrojeniePodluzneNequal0Line.setVisible(true);
		rightZbrojeniePodluzneNequal0Line.setVisible(true);
		zbrojeniePoprzeczneNequal0Label.setVisible(true);
		leftZbrojeniePoprzeczneNequal0Line.setVisible(true);
		rightZbrojeniePoprzeczneNequal0Line.setVisible(true);
		zbrojeniePoprzeczneNNotequal0Label.setVisible(false);
		leftZbrojeniePoprzeczneNNotequal0Line.setVisible(false);
		rightZbrojeniePoprzeczneNNotequal0Line.setVisible(false);

		gridLabel00.setText("");
		gridLabel01.setText("");
		gridLabel02.setText("");
		gridLabel03.setText("As1");
		gridLabel04.setText("As2");
		gridLabel05.setText("\u03C1");
		gridLabel06.setText("MRd");
		gridLabel07.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel08.setText("s1 ");
			gridLabel09.setText("s2 ");
			gridLabel010.setText("VRd");
			gridLabel011.setText("");
			gridLabel012.setText("");
			gridLabel013.setText("w");
			gridLabel014.setText("f");
			stanGranicznyUzytkowalnosciNequal0Label1.setVisible(false);
			leftSGUNequal0Line1.setVisible(false);
			rightSGUNequal0Line1.setVisible(false);
			stanGranicznyUzytkowalnosciNequal0Label2.setVisible(true);
			leftSGUNequal0Line2.setVisible(true);
			rightSGUNequal0Line2.setVisible(true);
		} else {
			gridLabel08.setText("s");
			gridLabel09.setText("VRd");
			gridLabel010.setText("");
			gridLabel011.setText("");
			gridLabel012.setText("w");
			gridLabel013.setText("f");
			gridLabel014.setText("");
			stanGranicznyUzytkowalnosciNequal0Label1.setVisible(true);
			leftSGUNequal0Line1.setVisible(true);
			rightSGUNequal0Line1.setVisible(true);
			stanGranicznyUzytkowalnosciNequal0Label2.setVisible(false);
			leftSGUNequal0Line2.setVisible(false);
			rightSGUNequal0Line2.setVisible(false);
		}
		
		gridLabel015.setText("");
		gridLabel016.setText("");
		gridLabel017.setText("");
		gridLabel018.setText("");
		gridLabel019.setText("");
		gridLabel020.setText("");
		

		gridLabel10.setText("");
		gridLabel11.setText("");
		gridLabel12.setText("Obliczeniowe");
		gridLabel13.setText(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredSymmetricalAS1()));
		gridLabel14.setText(OutputFormatter.metersToCentimetersForReinforcement((reinforcement.getRequiredSymmetricalAS2())));
		gridLabel15.setText(OutputFormatter.showPercentages((reinforcement.getDegreeOfComputedSymmetricalReinforcement())));
		gridLabel16.setText(OutputFormatter.diagnosisMed(diagnosisMainAlgorithm.getmRdRequiredSymmetrical()));
		gridLabel17.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel18.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel19.setText(OutputFormatter.s1s2(reinforcement.getS2Required()));
			gridLabel110.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdRequired()));
			gridLabel111.setText("");
			gridLabel112.setText("");
			gridLabel113.setText(OutputFormatter.wFormatter(sls.getwSymmetricalRequired()));
			gridLabel114.setText(OutputFormatter.fFormatter(sls.getfSymmetricalRequired()));
		} else {
			gridLabel18.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel19.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdRequired()));
			gridLabel110.setText("");
			gridLabel111.setText("");
			gridLabel112.setText(OutputFormatter.wFormatter(sls.getwSymmetricalRequired()));
			gridLabel113.setText(OutputFormatter.fFormatter(sls.getfSymmetricalRequired()));
			gridLabel114.setText("");
			
			
		}
		gridLabel115.setText("");
		gridLabel116.setText("");
		gridLabel117.setText("");
		gridLabel118.setText("");
		gridLabel119.setText("");
		gridLabel120.setText("");

		gridLabel20.setText("");
		gridLabel21.setText("");
		gridLabel22.setText("Zastosowane");
		gridLabel23.setText((OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS1())));
		gridLabel24.setText((OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS2())));
		gridLabel25.setText(OutputFormatter.showPercentages(reinforcement.getDegreeOfDesignedSymmetricalReinforcement()));
		gridLabel26.setText(OutputFormatter.diagnosisMed(diagnosisMainAlgorithm.getmRdDesignedSymmetrical()));
		gridLabel27.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel28.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel29.setText(OutputFormatter.s1s2(reinforcement.getS2Designed()));
			gridLabel210.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdDesigned()));
			gridLabel211.setText("");
			gridLabel212.setText("");
			gridLabel213.setText(OutputFormatter.wFormatter(sls.getwSymmetricalDesigned()));
			gridLabel214.setText(OutputFormatter.fFormatter(sls.getfSymmetricalDesigned()));
		} else {
			gridLabel28.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel29.setText(OutputFormatter.diagnosisVedAndNed(diagnosisMainAlgorithm.getvRdDesigned()));
			gridLabel210.setText("");
			gridLabel211.setText("");
			gridLabel212.setText((OutputFormatter.wFormatter(sls.getwSymmetricalDesigned())));
			gridLabel213.setText(OutputFormatter.fFormatter(sls.getfSymmetricalDesigned()));
			gridLabel214.setText("");
		}
		gridLabel215.setText("");
		gridLabel216.setText("");
		gridLabel217.setText("");
		gridLabel218.setText("");
		gridLabel219.setText("");
		gridLabel220.setText("");

		gridLabel30.setText("");
		gridLabel31.setText("");
		gridLabel32.setText("Liczba prêtów");
		gridLabel33.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS1()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS1()));
		gridLabel34.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS2()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS2()));
		gridLabel35.setText("");
		gridLabel36.setText("");
		gridLabel37.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel38.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel39.setText(OutputFormatter.formatAs1As2(reinforcement.getnS2Designed()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getaSW2Diameter()));
		} else {
			gridLabel38.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5" + OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel39.setText("");
		}
		gridLabel310.setText("");
		gridLabel311.setText("");
		gridLabel312.setText("");
		gridLabel313.setText("");
		gridLabel314.setText("");
		gridLabel315.setText("");
		gridLabel316.setText("");
		gridLabel317.setText("");
		gridLabel318.setText("");
		gridLabel319.setText("");
		gridLabel320.setText("");
	}
}
