package GUI.ReinforcementDesignLibraryControllers;

import SLS.Sls;
import SLS.creepCoeficent.CreepCoeficent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import mainalgorithm.ForcesCombination;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import util.OutputFormatter;

public class ResultsPaneControllerULS {

	private Reinforcement reinforcement;
	private InternalForces internalForces;
	private Sls sls;
	private CreepCoeficent creepCoeficent;
	private NominalStiffness stiffness;

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
	private Label gridLabel019;
	private Label gridLabel119;
	private Label gridLabel219;
	private Label gridLabel120;
	private Label gridLabel220;
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
	private Label gridLabel319;
	private Label gridLabel320;
	private CheckBox nominalCheckbox;

	//////// boolean na zwiekszenie przekroju
	private Label warningLabel;
	private Label degreeLabel;
	private Label abortLabel1;
	private Label abortLabel2;
	private Label abortLabel3;

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

	private Label zbrojeniePodluzneSymetryczneLabel;
	private Line leftZbrojeniePodluzneSymetryczneLine;
	private Line rightZbrojeniePodluzneSymetryczneLine;
	private Label zbrojeniePodluzneNiesymetryczneLabel;
	private Line leftZbrojeniePodluzneNiesymetryczneLine;
	private Line rightZbrojeniePodluzneNiesymetryczneLine;
	private Label zbrojeniePoprzeczneNNotequal0Label;
	private Line leftZbrojeniePoprzeczneNNotequal0Line;
	private Line rightZbrojeniePoprzeczneNNotequal0Line;

	public ResultsPaneControllerULS(Reinforcement reinforcement, InternalForces internalForces, Sls sls,
			Label gridLabel00, Label gridLabel01, Label gridLabel02, Label gridLabel03, Label gridLabel04,
			Label gridLabel05, Label gridLabel06, Label gridLabel07, Label gridLabel08, Label gridLabel09,
			Label gridLabel010, Label gridLabel011, Label gridLabel012, Label gridLabel013, Label gridLabel014,
			Label gridLabel015, Label gridLabel10, Label gridLabel11, Label gridLabel12, Label gridLabel13,
			Label gridLabel14, Label gridLabel15, Label gridLabel16, Label gridLabel17, Label gridLabel18,
			Label gridLabel19, Label gridLabel110, Label gridLabel111, Label gridLabel112, Label gridLabel113,
			Label gridLabel114, Label gridLabel115, Label gridLabel20, Label gridLabel21, Label gridLabel22,
			Label gridLabel23, Label gridLabel24, Label gridLabel25, Label gridLabel26, Label gridLabel27,
			Label gridLabel28, Label gridLabel29, Label gridLabel210, Label gridLabel211, Label gridLabel212,
			Label gridLabel213, Label gridLabel214, Label gridLabel215, Label gridLabel30, Label gridLabel31,
			Label gridLabel32, Label gridLabel33, Label gridLabel34, Label gridLabel35, Label gridLabel36,
			Label gridLabel37, Label gridLabel38, Label gridLabel39, Label gridLabel310, Label gridLabel311,
			Label gridLabel312, Label gridLabel313, Label gridLabel314, Label gridLabel315,
			Label stanGranicznyNosnosciNequal0Label, Line leftSGNNequal0Line, Line rightSGNNequal0Line,
			Label zbrojeniePodluzneNequal0Label, Line leftZbrojeniePodluzneNequal0Line,
			Line rightZbrojeniePodluzneNequal0Line, Label zbrojeniePoprzeczneNequal0Label,
			Line leftZbrojeniePoprzeczneNequal0Line, Line rightZbrojeniePoprzeczneNequal0Line,
			Label stanGranicznyUzytkowalnosciNequal0Label1, Line leftSGUNequal0Line1, Line rightSGUNequal0Line1,
			Label stanGranicznyUzytkowalnosciNequal0Label2, Line leftSGUNequal0Line2, Line rightSGUNequal0Line2,
			Label zbrojeniePodluzneSymetryczneLabel, Line leftZbrojeniePodluzneSymetryczneLine,
			Line rightZbrojeniePodluzneSymetryczneLine, Label zbrojeniePodluzneNiesymetryczneLabel,
			Line leftZbrojeniePodluzneNiesymetryczneLine, Line rightZbrojeniePodluzneNiesymetryczneLine,
			Label zbrojeniePoprzeczneNNotequal0Label, Line leftZbrojeniePoprzeczneNNotequal0Line,
			Line rightZbrojeniePoprzeczneNNotequal0Line, Label gridLabel019, Label gridLabel119, Label gridLabel219,
			Label gridLabel120, Label gridLabel220, Label gridLabel020,
			/// NOWE!
			NominalStiffness stiffness, Label warningLabel, Label degreeLabel, Label abortLabel1, Label abortLabel2,
			Label abortLabel3, CreepCoeficent creepCoeficent, Label gridLabel319, Label gridLabel320,
			CheckBox nominalCheckbox) {

		this.reinforcement = reinforcement;
		this.internalForces = internalForces;
		this.sls = sls;
		this.stiffness = stiffness;
		this.creepCoeficent = creepCoeficent;
		this.warningLabel = warningLabel;
		this.degreeLabel = degreeLabel;
		this.abortLabel1 = abortLabel1;
		this.abortLabel2 = abortLabel2;
		this.abortLabel3 = abortLabel3;
		this.nominalCheckbox = nominalCheckbox;

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

		this.gridLabel319 = gridLabel319;
		this.gridLabel320 = gridLabel320;
		this.gridLabel019 = gridLabel019;
		this.gridLabel119 = gridLabel119;
		this.gridLabel219 = gridLabel219;
		this.gridLabel120 = gridLabel120;
		this.gridLabel220 = gridLabel220;
		this.gridLabel020 = gridLabel020;

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

		this.zbrojeniePodluzneSymetryczneLabel = zbrojeniePodluzneSymetryczneLabel;
		this.leftZbrojeniePodluzneSymetryczneLine = leftZbrojeniePodluzneSymetryczneLine;
		this.rightZbrojeniePodluzneSymetryczneLine = rightZbrojeniePodluzneSymetryczneLine;
		this.zbrojeniePodluzneNiesymetryczneLabel = zbrojeniePodluzneNiesymetryczneLabel;
		this.leftZbrojeniePodluzneNiesymetryczneLine = leftZbrojeniePodluzneNiesymetryczneLine;
		this.rightZbrojeniePodluzneNiesymetryczneLine = rightZbrojeniePodluzneNiesymetryczneLine;
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
		gridLabel019.setText("");
		gridLabel119.setText("");
		gridLabel219.setText("");
		gridLabel120.setText("");
		gridLabel220.setText("");
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

		zbrojeniePodluzneSymetryczneLabel.setVisible(false);
		leftZbrojeniePodluzneSymetryczneLine.setVisible(false);
		rightZbrojeniePodluzneSymetryczneLine.setVisible(false);
		zbrojeniePodluzneNiesymetryczneLabel.setVisible(false);
		leftZbrojeniePodluzneNiesymetryczneLine.setVisible(false);
		rightZbrojeniePodluzneNiesymetryczneLine.setVisible(false);
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

		zbrojeniePodluzneSymetryczneLabel.setVisible(true);
		leftZbrojeniePodluzneSymetryczneLine.setVisible(true);
		rightZbrojeniePodluzneSymetryczneLine.setVisible(true);
		zbrojeniePodluzneNiesymetryczneLabel.setVisible(true);
		leftZbrojeniePodluzneNiesymetryczneLine.setVisible(true);
		rightZbrojeniePodluzneNiesymetryczneLine.setVisible(true);
		zbrojeniePoprzeczneNNotequal0Label.setVisible(true);
		leftZbrojeniePoprzeczneNNotequal0Line.setVisible(true);
		rightZbrojeniePoprzeczneNNotequal0Line.setVisible(true);

		gridLabel019.setText("");
		gridLabel119.setText("");
		gridLabel219.setText("");
		gridLabel120.setText("");
		gridLabel220.setText("");
		gridLabel020.setText("");

		warningLabel.setVisible(false);
		abortLabel1.setVisible(false);
		abortLabel2.setVisible(false);
		abortLabel3.setVisible(false);
		degreeLabel.setVisible(false);

		if (reinforcement.getDegreeOfDesignedSymmetricalReinforcement() > 0.04
				|| reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement() > 0.04) {
			reinforcement.setDegreeExceeded(true);
		}

		if (reinforcement.isDegreeExceeded()) {
			warningLabel.setVisible(true);
			degreeLabel.setVisible(true);
		}

		if (reinforcement.isDegreeExceededSymmetrical() || reinforcement.isDegreeExceededUnsymmetrical()) {
			warningLabel.setVisible(true);
			abortLabel1.setVisible(true);
			abortLabel2.setVisible(true);
		}

		if (stiffness.isnBExceeded()) {
			warningLabel.setVisible(true);
			abortLabel1.setVisible(true);
			abortLabel2.setVisible(false);
			abortLabel3.setVisible(true);

		} else if (stiffness.isAborted()) {
			warningLabel.setVisible(true);
			abortLabel1.setVisible(true);
			abortLabel2.setVisible(true);
			abortLabel3.setVisible(false);
		}

		stiffness.setAborted(false);
		stiffness.setnBExceeded(false);

		if (internalForces.getMomentMmax() != 0 || internalForces.getNormalnaMmax() != 0
				|| internalForces.getMomentMmin() != 0 || internalForces.getNormalnaMmin() != 0
				|| internalForces.getMomentNmax() != 0 || internalForces.getNormalnaNmax() != 0
				|| internalForces.getMomentNmin() != 0 || internalForces.getNormalnaNmin() != 0) {

			gridLabel019.setText("max E:");
			gridLabel119.setText("MEd:");
			gridLabel219.setText("NEd:");

			ForcesCombination combination = internalForces.getMaxECombination();
			gridLabel020
					.setText(String.format("%.2f", Math.abs((combination.getmStiff()) / (combination.getN()))) + " m");
			gridLabel120.setText(OutputFormatter.diagnosisMed(combination.getmStiff()));
			gridLabel220.setText(OutputFormatter.diagnosisVedAndNed(combination.getN()));

		} else {
			gridLabel019.setText("");
			gridLabel119.setText("");
			gridLabel219.setText("");
			gridLabel120.setText("");
			gridLabel220.setText("");
			gridLabel020.setText("");
		}

		gridLabel00.setText("");
		gridLabel01.setText("");
		gridLabel02.setText("");
		gridLabel03.setText("As1");
		gridLabel04.setText("As2");
		gridLabel05.setText("\u03C1");
		gridLabel06.setText("");
		gridLabel07.setText("");
		gridLabel08.setText("As1");
		gridLabel09.setText("As2");
		gridLabel010.setText("\u03C1");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel011.setText(" ");
			gridLabel012.setText("s1 ");
			gridLabel013.setText("s2");
		} else {
			gridLabel011.setText("");
			gridLabel012.setText("s");
			gridLabel013.setText("");
		}

		gridLabel014.setText("");
		gridLabel015.setText("");

		gridLabel10.setText("");
		gridLabel11.setText("");
		gridLabel12.setText("Obliczeniowe");
		gridLabel13.setText(
				OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredSymmetricalAS1()));
		gridLabel14.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredSymmetricalAS2())));
		gridLabel15
				.setText(OutputFormatter.showPercentages(reinforcement.getDegreeOfComputedSymmetricalReinforcement()));
		gridLabel16.setText("");
		gridLabel17.setText("Obliczeniowe");
		gridLabel18.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredUnsymmetricalAS1())));
		gridLabel19.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredUnsymmetricalAS2())));
		gridLabel110.setText(
				OutputFormatter.showPercentages(reinforcement.getDegreeOfComputedUnsymmetricalReinforcement()));

		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel111.setText("");
			gridLabel112.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel113.setText(OutputFormatter.s1s2(reinforcement.getS2Required()));
			gridLabel211.setText("");
			gridLabel212.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel213.setText(OutputFormatter.s1s2(reinforcement.getS2Designed()));

		} else {
			gridLabel111.setText("");
			gridLabel112.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel113.setText("");
			gridLabel211.setText("");
			gridLabel212.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel213.setText("");

		}
		gridLabel114.setText("");
		gridLabel115.setText("");

		gridLabel20.setText("");
		gridLabel21.setText("");
		gridLabel22.setText("Zastosowane");
		gridLabel23.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS1())));
		gridLabel24.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS2())));
		gridLabel25
				.setText(OutputFormatter.showPercentages(reinforcement.getDegreeOfDesignedSymmetricalReinforcement()));
		gridLabel26.setText("");
		gridLabel27.setText("Zastosowane");
		gridLabel28.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesingedUnsymmetricalAS1())));
		gridLabel29.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedUnsymmetricalAS2())));
		gridLabel210.setText(
				OutputFormatter.showPercentages(reinforcement.getDegreeOfDesignedUnsymmetricalReinforcement()));

		gridLabel214.setText("");
		gridLabel215.setText("");

		gridLabel30.setText("");
		gridLabel31.setText("");
		gridLabel32.setText("Liczba prêtów");
		gridLabel33.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS1()) + "\u03D5"
				+ OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS1()));
		gridLabel34.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS2()) + "\u03D5"
				+ OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS2()));
		gridLabel35.setText("");
		gridLabel36.setText("");
		gridLabel37.setText("Liczba prêtów");
		gridLabel38.setText(Integer.toString(reinforcement.getRequiredNumberOfUnsymmetricalRodsAS1()) + "\u03D5"
				+ OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS1()));
		gridLabel39.setText(Integer.toString(reinforcement.getRequiredNumberOfUnsymmetricalRodsAS2()) + "\u03D5"
				+ OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS2()));
		gridLabel310.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel311.setText(" ");
			gridLabel312.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5"
					+ OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel313.setText(OutputFormatter.formatAs1As2(reinforcement.getnS2Designed()) + "\u03D5"
					+ OutputFormatter.formatAs1As2(reinforcement.getaSW2Diameter()));
		} else {
			gridLabel311.setText("");
			gridLabel312.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5"
					+ OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel313.setText("");
		}
		gridLabel314.setText("");
		gridLabel315.setText("");
		if (nominalCheckbox.isSelected()) {
			gridLabel319.setText("\u03C6" + "ef");
			gridLabel320.setText(String.format("%.2f", creepCoeficent.getCreepCoeficent()));
		} else {
			gridLabel319.setText("");
			gridLabel320.setText("");
		}
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

		zbrojeniePodluzneSymetryczneLabel.setVisible(false);
		leftZbrojeniePodluzneSymetryczneLine.setVisible(false);
		rightZbrojeniePodluzneSymetryczneLine.setVisible(false);
		zbrojeniePodluzneNiesymetryczneLabel.setVisible(false);
		leftZbrojeniePodluzneNiesymetryczneLine.setVisible(false);
		rightZbrojeniePodluzneNiesymetryczneLine.setVisible(false);
		zbrojeniePoprzeczneNNotequal0Label.setVisible(false);
		leftZbrojeniePoprzeczneNNotequal0Line.setVisible(false);
		rightZbrojeniePoprzeczneNNotequal0Line.setVisible(false);

		gridLabel00.setText("");
		gridLabel01.setText("");
		gridLabel02.setText("");
		gridLabel03.setText("As1");
		gridLabel04.setText("As2");
		gridLabel05.setText("\u03C1");
		gridLabel06.setText("");

		gridLabel019.setText("");
		gridLabel119.setText("");
		gridLabel219.setText("");
		gridLabel120.setText("");
		gridLabel220.setText("");
		gridLabel020.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel07.setText("s1 ");
			gridLabel08.setText("s2 ");

			gridLabel09.setText("");
			gridLabel010.setText("");
			gridLabel011.setText("w");
			gridLabel012.setText("f");
			stanGranicznyUzytkowalnosciNequal0Label1.setVisible(false);
			leftSGUNequal0Line1.setVisible(false);
			rightSGUNequal0Line1.setVisible(false);
			stanGranicznyUzytkowalnosciNequal0Label2.setVisible(true);
			leftSGUNequal0Line2.setVisible(true);
			rightSGUNequal0Line2.setVisible(true);

		} else {
			gridLabel07.setText("s ");

			gridLabel08.setText("");
			gridLabel09.setText("");
			gridLabel010.setText("w");
			gridLabel011.setText("f");
			gridLabel012.setText("");
			stanGranicznyUzytkowalnosciNequal0Label1.setVisible(true);
			leftSGUNequal0Line1.setVisible(true);
			rightSGUNequal0Line1.setVisible(true);
			stanGranicznyUzytkowalnosciNequal0Label2.setVisible(false);
			leftSGUNequal0Line2.setVisible(false);
			rightSGUNequal0Line2.setVisible(false);
		}

		gridLabel013.setText("");
		gridLabel014.setText("");
		gridLabel015.setText("");

		gridLabel10.setText("");
		gridLabel11.setText("");
		gridLabel12.setText("Obliczeniowe");
		gridLabel13.setText(
				OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredSymmetricalAS1()));
		gridLabel14.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getRequiredSymmetricalAS2())));
		gridLabel15.setText(
				OutputFormatter.showPercentages((reinforcement.getDegreeOfComputedSymmetricalReinforcement())));
		gridLabel16.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel17.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel18.setText(OutputFormatter.s1s2(reinforcement.getS2Required()));
			gridLabel19.setText("");
			gridLabel110.setText("");
			if (internalForces.isLoadSustained()) {
				gridLabel111.setText(OutputFormatter.wFormatter(sls.getwSymmetricalRequired()));
				gridLabel112.setText(OutputFormatter.fFormatter(sls.getfSymmetricalRequiredWithoutShrinkage(), sls.getfSymmetricalRequired()));
			} else {
				gridLabel111.setText(OutputFormatter.wFormatterSingle(sls.getwSymmetricalRequired()));
				gridLabel112.setText(OutputFormatter.fFormatterSingle(sls.getfSymmetricalRequired()));
			}

		} else {
			gridLabel17.setText(OutputFormatter.s1s2(reinforcement.getS1Required()));
			gridLabel18.setText("");
			gridLabel19.setText("");
			if (internalForces.isLoadSustained()) {
				gridLabel110.setText(OutputFormatter.wFormatter(sls.getwSymmetricalRequired()));
				gridLabel111.setText(OutputFormatter.fFormatter(sls.getfSymmetricalRequiredWithoutShrinkage(), sls.getfSymmetricalRequired()));
			} else {
				gridLabel110.setText(OutputFormatter.wFormatterSingle(sls.getwSymmetricalRequired()));
				gridLabel111.setText(OutputFormatter.fFormatterSingle(sls.getfSymmetricalRequired()));
			}
			gridLabel112.setText("");
		}
		gridLabel113.setText("");
		gridLabel114.setText("");
		gridLabel115.setText("");

		gridLabel20.setText("");
		gridLabel21.setText("");
		gridLabel22.setText("Zastosowane");
		gridLabel23.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS1())));
		gridLabel24.setText(
				(OutputFormatter.metersToCentimetersForReinforcement(reinforcement.getDesignedSymmetricalAS2())));
		gridLabel25
				.setText(OutputFormatter.showPercentages(reinforcement.getDegreeOfDesignedSymmetricalReinforcement()));
		gridLabel26.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel27.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel28.setText(OutputFormatter.s1s2(reinforcement.getS2Designed()));
			gridLabel29.setText("");
			gridLabel210.setText("");
			if (internalForces.isLoadSustained()) {
			gridLabel211.setText(OutputFormatter.wFormatter(sls.getwSymmetricalDesigned()));
			gridLabel212.setText(OutputFormatter.fFormatter(sls.getfSymmetricalDesignedWithoutShrinkage(), sls.getfSymmetricalDesigned()));
			} else {
				gridLabel211.setText(OutputFormatter.wFormatterSingle(sls.getwSymmetricalDesigned()));
				gridLabel212.setText(OutputFormatter.fFormatterSingle(sls.getfSymmetricalDesigned()));
			}

		} else {
			gridLabel27.setText(OutputFormatter.s1s2(reinforcement.getS1Designed()));
			gridLabel28.setText("");
			gridLabel29.setText("");
			if (internalForces.isLoadSustained()) {
			gridLabel210.setText(OutputFormatter.wFormatter(sls.getwSymmetricalDesigned()));
			gridLabel211.setText(OutputFormatter.fFormatter(sls.getfSymmetricalDesignedWithoutShrinkage(), sls.getfSymmetricalDesigned()));
			} else {
				gridLabel210.setText(OutputFormatter.wFormatterSingle(sls.getwSymmetricalDesigned()));
				gridLabel211.setText(OutputFormatter.fFormatterSingle(sls.getfSymmetricalDesigned()));
			}
				
			gridLabel212.setText("");
		}
		gridLabel213.setText("");
		gridLabel214.setText("");
		gridLabel215.setText("");

		gridLabel30.setText("");
		gridLabel31.setText("");
		gridLabel32.setText("Liczba prêtów");
		gridLabel33.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS1()) + "\u03D5"
				+ OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS1()));
		gridLabel34.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS2()) + "\u03D5"
				+ OutputFormatter.formatAs1As2(reinforcement.getDesignedDiameterSymmetricalAS2()));
		gridLabel35.setText("");
		gridLabel36.setText("");
		if (reinforcement.getnS2Required() != 0 && reinforcement.getS2Designed() != 0) {
			gridLabel37.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5"
					+ OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel38.setText(OutputFormatter.formatAs1As2(reinforcement.getnS2Designed()) + "\u03D5"
					+ OutputFormatter.formatAs1As2(reinforcement.getaSW2Diameter()));
		} else {
			gridLabel37.setText(OutputFormatter.formatAs1As2(reinforcement.getnS1()) + "\u03D5"
					+ OutputFormatter.formatAs1As2(reinforcement.getaSW1Diameter()));
			gridLabel38.setText("");
		}
		gridLabel39.setText("");
		gridLabel310.setText("");
		gridLabel311.setText("");
		gridLabel312.setText("");
		gridLabel313.setText("");
		gridLabel314.setText("");
		gridLabel315.setText("");
		gridLabel319.setText("");
		gridLabel320.setText("");

		if (internalForces.getMomentMmax() != 0 || internalForces.getMomentMmin() != 0
				|| internalForces.getMomentNmax() != 0 || internalForces.getMomentNmin() != 0) {
			stanGranicznyUzytkowalnosciNequal0Label1.setVisible(false);
			leftSGUNequal0Line1.setVisible(false);
			rightSGUNequal0Line1.setVisible(false);
			stanGranicznyUzytkowalnosciNequal0Label2.setVisible(false);
			leftSGUNequal0Line2.setVisible(false);
			rightSGUNequal0Line2.setVisible(false);

			gridLabel010.setText("");
			gridLabel011.setText("");
			gridLabel110.setText("");
			gridLabel111.setText("");
			gridLabel210.setText("");
			gridLabel211.setText("");
			gridLabel310.setText("");
			gridLabel311.setText("");
			gridLabel319.setText("");
			gridLabel320.setText("");
		}

	}

}
