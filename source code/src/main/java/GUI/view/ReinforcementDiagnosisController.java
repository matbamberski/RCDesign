package GUI.view;

import GUI.Main;
import GUI.ReinforcementDesignLibraryControllers.ADistanceTextFieldsController;
import GUI.ReinforcementDesignLibraryControllers.AdditionalVariablesController;
import GUI.ReinforcementDesignLibraryControllers.ConcreteChoiceBoxController;
import GUI.ReinforcementDesignLibraryControllers.CrossSectionTypeController;
import GUI.ReinforcementDesignLibraryControllers.DiagnosisButtonController;
import GUI.ReinforcementDesignLibraryControllers.GraphButtonController;
import GUI.ReinforcementDesignLibraryControllers.InternalForcesController;
import GUI.ReinforcementDesignLibraryControllers.NumberOfRodsController;
import GUI.ReinforcementDesignLibraryControllers.PdfController;
import GUI.ReinforcementDesignLibraryControllers.ResultsPaneControllerDiagnosis;
import GUI.ReinforcementDesignLibraryControllers.SaveFileButtonController;
import GUI.ReinforcementDesignLibraryControllers.ShearingReinforcementController;
import GUI.ReinforcementDesignLibraryControllers.SteelParametersController;
import GUI.ReinforcementDesignLibraryControllers.UnicodeForLabels;
import GUI.ReinforcementDesignLibraryControllers.UsersDesignedReinforcementController;
import SLS.Sls;
import SLS.creepCoeficent.CreepCoeficent;
import diagnosis.DiagnosisMainAlgorithm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import mainalgorithm.InternalForces;
import mainalgorithm.NominalStiffness;
import mainalgorithm.Reinforcement;
import mainalgorithm.RequiredReinforcement;
import materials.Cement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.graph.Graph;

public class ReinforcementDiagnosisController {

	ScreensController myController;
	ReinforcementDesignController design;
	ReinforcementDesignController x;
	ADistanceTextFieldsController adistance;
	GraphScreenController graphController;
	Main main;

	/*
	 * //dodane coœ psuje Graph graph; DimensionsOfCrossSectionOfConcrete
	 * dimensions;
	 * 
	 * public ReinforcementDiagnosisController(Graph graph,
	 * DimensionsOfCrossSectionOfConcrete dimensions, GraphScreenController
	 * graphValues) { super(); this.graph = graph; this.dimensions = dimensions; }
	 * //
	 */
	@FXML
	private TextField aS1SymmetricalNumberOfRodsTextField;
	@FXML
	private TextField aS2SymmetricalNumberOfRodsTextField;
	@FXML
	private TextField aS1UnsymmetricalNumberOfRodsTextField;
	@FXML
	private TextField aS2UnsymmetricalNumberOfRodsTextField;

	@FXML
	private Button switchScenesButton;
	@FXML
	private ChoiceBox<String> concreteChoiceBox;
	@FXML
	private ChoiceBox<String> crossSectionTypeChoiceBox;
	@FXML
	private Button countButton;
	@FXML
	private TextField a1DimensionTextField;
	@FXML
	private TextField a2DimensionTextField;
	@FXML
	private TextField cNomTextField;
	@FXML
	private TextField bTextField;
	@FXML
	private TextField hTextField;
	@FXML
	private TextField bEffTextField;
	@FXML
	private TextField tWTextField;
	@FXML
	private TextField mEdObliczenioweTextField;
	@FXML
	private TextField mEdCharCalkTextField;
	@FXML
	private TextField mEdCharDlugTextField;
	@FXML
	private TextField nEdTextField;
	@FXML
	private TextField vEdTextField;
	@FXML
	private TextField aS1TextField;
	@FXML
	private TextField aS2TextField;
	@FXML
	private Label a1Label;
	@FXML
	private Label a2Label;
	@FXML
	private Label mEdLabel;
	@FXML
	private Label nEdLabel;
	@FXML
	private Label vEdLabel;
	@FXML
	private Label aS1Label;
	@FXML
	private Label aS2Label;
	@FXML
	private Label bLabel;
	@FXML
	private Label hLabel;
	@FXML
	private Label bEffLabel;
	@FXML
	private Label bEffLowerrLabel;
	@FXML
	private Label tWLabel;
	@FXML
	private Label tWLowerrLabel;

	// ** RESULTS ULS

	@FXML
	private Label stanGranicznyNosnosciNequal0Label;
	@FXML
	private Line leftSGNNequal0Line;
	@FXML
	private Line rightSGNNequal0Line;
	@FXML
	private Label zbrojeniePodluzneNequal0Label;
	@FXML
	private Line leftZbrojeniePodluzneNequal0Line;
	@FXML
	private Line rightZbrojeniePodluzneNequal0Line;

	@FXML
	private Label zbrojeniePoprzeczneNequal0Label;
	@FXML
	private Line leftZbrojeniePoprzeczneNequal0Line;
	@FXML
	private Line rightZbrojeniePoprzeczneNequal0Line;

	@FXML
	private Label stanGranicznyUzytkowalnosciNequal0Label1;
	@FXML
	private Line leftSGUNequal0Line1;
	@FXML
	private Line rightSGUNequal0Line1;

	@FXML
	private Label stanGranicznyUzytkowalnosciNequal0Label2;
	@FXML
	private Line leftSGUNequal0Line2;
	@FXML
	private Line rightSGUNequal0Line2;

	@FXML
	private Label zbrojeniePodluzneSymetryczneLabel;
	@FXML
	private Line leftZbrojeniePodluzneSymetryczneLine;
	@FXML
	private Line rightZbrojeniePodluzneSymetryczneLine;

	@FXML
	private Label zbrojeniePodluzneNiesymetryczneLabel;
	@FXML
	private Line leftZbrojeniePodluzneNiesymetryczneLine;
	@FXML
	private Line rightZbrojeniePodluzneNiesymetryczneLine;

	@FXML
	private Label zbrojeniePoprzeczneNNotequal0Label;
	@FXML
	private Line leftZbrojeniePoprzeczneNNotequal0Line;
	@FXML
	private Line rightZbrojeniePoprzeczneNNotequal0Line;

	@FXML
	private Label gridLabel00;
	@FXML
	private Label gridLabel01;
	@FXML
	private Label gridLabel02;
	@FXML
	private Label gridLabel03;
	@FXML
	private Label gridLabel04;
	@FXML
	private Label gridLabel05;
	@FXML
	private Label gridLabel06;
	@FXML
	private Label gridLabel07;
	@FXML
	private Label gridLabel08;
	@FXML
	private Label gridLabel09;
	@FXML
	private Label gridLabel010;
	@FXML
	private Label gridLabel011;
	@FXML
	private Label gridLabel012;
	@FXML
	private Label gridLabel013;
	@FXML
	private Label gridLabel014;
	@FXML
	private Label gridLabel015;
	@FXML
	private Label gridLabel016;
	@FXML
	private Label gridLabel017;
	@FXML
	private Label gridLabel018;
	@FXML
	private Label gridLabel019;
	@FXML
	private Label gridLabel020;
	@FXML
	private Label gridLabel021;
	@FXML
	private Label gridLabel022;
	@FXML
	private Label gridLabel023;
	@FXML
	private Label gridLabel024;

	@FXML
	private Label gridLabel10;
	@FXML
	private Label gridLabel11;
	@FXML
	private Label gridLabel12;
	@FXML
	private Label gridLabel13;
	@FXML
	private Label gridLabel14;
	@FXML
	private Label gridLabel15;
	@FXML
	private Label gridLabel16;
	@FXML
	private Label gridLabel17;
	@FXML
	private Label gridLabel18;
	@FXML
	private Label gridLabel19;
	@FXML
	private Label gridLabel110;
	@FXML
	private Label gridLabel111;
	@FXML
	private Label gridLabel112;
	@FXML
	private Label gridLabel113;
	@FXML
	private Label gridLabel114;
	@FXML
	private Label gridLabel115;
	@FXML
	private Label gridLabel116;
	@FXML
	private Label gridLabel117;
	@FXML
	private Label gridLabel118;
	@FXML
	private Label gridLabel119;
	@FXML
	private Label gridLabel120;
	@FXML
	private Label gridLabel121;
	@FXML
	private Label gridLabel122;
	@FXML
	private Label gridLabel123;
	@FXML
	private Label gridLabel124;

	@FXML
	private Label gridLabel20;
	@FXML
	private Label gridLabel21;
	@FXML
	private Label gridLabel22;
	@FXML
	private Label gridLabel23;
	@FXML
	private Label gridLabel24;
	@FXML
	private Label gridLabel25;
	@FXML
	private Label gridLabel26;
	@FXML
	private Label gridLabel27;
	@FXML
	private Label gridLabel28;
	@FXML
	private Label gridLabel29;
	@FXML
	private Label gridLabel210;
	@FXML
	private Label gridLabel211;
	@FXML
	private Label gridLabel212;
	@FXML
	private Label gridLabel213;
	@FXML
	private Label gridLabel214;
	@FXML
	private Label gridLabel215;
	@FXML
	private Label gridLabel216;
	@FXML
	private Label gridLabel217;
	@FXML
	private Label gridLabel218;
	@FXML
	private Label gridLabel219;
	@FXML
	private Label gridLabel220;
	@FXML
	private Label gridLabel221;
	@FXML
	private Label gridLabel222;
	@FXML
	private Label gridLabel223;
	@FXML
	private Label gridLabel224;

	@FXML
	private Label gridLabel30;
	@FXML
	private Label gridLabel31;
	@FXML
	private Label gridLabel32;
	@FXML
	private Label gridLabel33;
	@FXML
	private Label gridLabel34;
	@FXML
	private Label gridLabel35;
	@FXML
	private Label gridLabel36;
	@FXML
	private Label gridLabel37;
	@FXML
	private Label gridLabel38;
	@FXML
	private Label gridLabel39;
	@FXML
	private Label gridLabel310;
	@FXML
	private Label gridLabel311;
	@FXML
	private Label gridLabel312;
	@FXML
	private Label gridLabel313;
	@FXML
	private Label gridLabel314;
	@FXML
	private Label gridLabel315;
	@FXML
	private Label gridLabel316;
	@FXML
	private Label gridLabel317;
	@FXML
	private Label gridLabel318;
	@FXML
	private Label gridLabel319;
	@FXML
	private Label gridLabel320;
	@FXML
	private Label gridLabel321;
	@FXML
	private Label gridLabel322;
	@FXML
	private Label gridLabel323;
	@FXML
	private Label gridLabel324;
	@FXML
	private Label lowerTitle;
	// additional variables

	@FXML
	private TextField lEffTextField;
	@FXML
	private TextField alfaMTextField;
	@FXML
	private TextField t0TextField;
	@FXML
	private TextField rHTextField;
	@FXML
	private ChoiceBox<String> cementClassChoiceBox;
	@FXML
	private ChoiceBox<String> typeOfLoadChoiceBox;
	@FXML
	private TextField wLimTextField;
	@FXML
	private TextField aLimTextField;

	// Shearing Pane
	@FXML
	private Label phiSt;
	@FXML
	private TextField aSW1TextField;
	@FXML
	private TextField aSW2TextFIeld;
	@FXML
	private TextField phiStTextField;
	@FXML
	private TextField nS1TextField;
	@FXML
	private TextField nS2TextField;
	@FXML
	private Label nS1Label;
	@FXML
	private Label nS2Label;
	@FXML
	private TextField s1TextField;
	@FXML
	private TextField s2TextField;
	@FXML
	private TextField thetaTextField;
	@FXML
	private TextField alfaTextField;

	/// Kombinacje obciazen do slupow
	@FXML
	private HBox columsCasesHBox;

	@FXML
	private VBox firstColumnVBox;
	@FXML
	private Label emptyLabel;
	@FXML
	private Label mLabel;
	@FXML
	private Label nLabel;

	@FXML
	private VBox mMaxColumnVBox;
	@FXML
	private Label mMaxLabel;
	@FXML
	private TextField momentMmax;
	@FXML
	private TextField normalnaMmax;

	@FXML
	private VBox mMinColumnVBox;
	@FXML
	private Label mMinLabel;
	@FXML
	private TextField momentMmin;
	@FXML
	private TextField normalnaMmin;

	@FXML
	private VBox nMaxColumnVBox;
	@FXML
	private Label nMaxLabel;
	@FXML
	private TextField momentNmax;
	@FXML
	private TextField normalnaNmax;

	@FXML
	private VBox nMinColumnVBox;
	@FXML
	private Label nMinLabel;
	@FXML
	private TextField momentNmin;
	@FXML
	private TextField normalnaNmin;

	@FXML
	private VBox labelsColumnVBox;
	@FXML
	private Label emptyLabelL0;
	@FXML
	private Label l0Label;
	@FXML
	private Label fit0Label;

	@FXML
	private VBox lastColumnVBox;
	@FXML
	private Label emptyLastColumnLabel;
	@FXML
	private TextField l0;
	@FXML
	private TextField fiT0;
	
	/////////////////////////

	// steel parameters
	@FXML
	private Label fYkLabel;
	@FXML
	private TextField fYkTextField;
	@FXML
	private Button diagnosisButton;

	@FXML
	private Label ctgThetaLabel;
	@FXML
	private Label alfaLabel;
	@FXML
	private Label alfaMLabel;
	@FXML
	private TextField pdfName;

	@FXML
	private Line lineBelowS;
	@FXML
	private Line lineBelowS2;
	@FXML
	private Label sgnLabel;
	@FXML
	private Label sguUperLabel;
	@FXML
	private Label sguLowerLabel;
	@FXML
	private Label zbrojenieNiesymetryczneLabel;
	@FXML
	private Label zbrojenieSymetryczneLabel;
	@FXML
	private Button saveToPdfButton;

	@FXML
	private Button graphButton;


	private DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete = ReinforcementDesignController.dimensionsOfCrossSectionOfConcrete;
	private InternalForces internalForces = ReinforcementDesignController.internalForces;
	private Reinforcement reinforcement = ReinforcementDesignController.reinforcement;
	private Concrete concrete = ReinforcementDesignController.concrete;
	private Steel steel = ReinforcementDesignController.steel;
	private RequiredReinforcement requiredReinforcementSeter = ReinforcementDesignController.requiredReinforcementSeter;
	protected Cement cement = ReinforcementDesignController.cement;
	protected Sls sls = ReinforcementDesignController.sls;
	protected CreepCoeficent creep = ReinforcementDesignController.creep;
	private ResultsPaneControllerDiagnosis resultsPaneControllerDiagnosis;
	private DiagnosisButtonController diagnosisButtonController;
	private NominalStiffness stiffness = ReinforcementDesignController.stiffness;
	private DiagnosisMainAlgorithm diagnosisMainAlgorithm = new DiagnosisMainAlgorithm();
	
	private Graph graph = ReinforcementDesignController.graph;
	


	@FXML
	void initialize() {

		PdfController.addPropertiesToPdfNameTextField(pdfName);
		UnicodeForLabels.addUnicodeForLabels(ctgThetaLabel, alfaLabel, alfaMLabel);

		ConcreteChoiceBoxController.addPropertiesToConcreteChoiceBox(concrete, concreteChoiceBox);
		SteelParametersController.addPropertiesToFYkTF(steel, fYkTextField);

		ADistanceTextFieldsController.addPropertiesToA1TextField(a1DimensionTextField,
				dimensionsOfCrossSectionOfConcrete);
		ADistanceTextFieldsController.addPropertiesToA2TextField(a2DimensionTextField,
				dimensionsOfCrossSectionOfConcrete);
		CrossSectionTypeController.addPorpertiesToCrossSectionTypeChoiceBox(crossSectionTypeChoiceBox, bEffTextField,
				tWTextField, bEffLabel, bEffLowerrLabel, tWLabel, tWLowerrLabel, dimensionsOfCrossSectionOfConcrete,
				columsCasesHBox);
		CrossSectionTypeController.addPropertiesToBEffTextField(bEffTextField, dimensionsOfCrossSectionOfConcrete);
		CrossSectionTypeController.addPropertiesToBTextField(bTextField, dimensionsOfCrossSectionOfConcrete);
		CrossSectionTypeController.addPropertiesToHTextField(hTextField, dimensionsOfCrossSectionOfConcrete);
		CrossSectionTypeController.addPropertiesToTWTextField(tWTextField, dimensionsOfCrossSectionOfConcrete);

		/// Metoda sprawdza poprawosc wprowadzonych danych
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaMmax, ReinforcementDesignController.InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaMmin, ReinforcementDesignController.InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaNmax, ReinforcementDesignController.InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaNmin, ReinforcementDesignController.InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentMmax, ReinforcementDesignController.InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentMmin, ReinforcementDesignController.InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentNmax, ReinforcementDesignController.InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentNmin, ReinforcementDesignController.InternalForces);
		
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, fiT0, ReinforcementDesignController.Nominalstiffness);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, l0, ReinforcementDesignController.Nominalstiffness);

		
		////////

		InternalForcesController.addPropertiesToMEdTextField(internalForces, mEdObliczenioweTextField,
				mEdCharCalkTextField, mEdCharDlugTextField);
		InternalForcesController.addPropertiesToNEdTextField(internalForces, nEdTextField, crossSectionTypeChoiceBox);
		InternalForcesController.addPropertiesToVEdTextField(internalForces, vEdTextField);
		InternalForcesController.addPropertiesToMEdCharCalk(internalForces, mEdObliczenioweTextField,
				mEdCharCalkTextField, mEdCharDlugTextField);
		InternalForcesController.addPropertiesToMEdCharDlug(internalForces, mEdObliczenioweTextField,
				mEdCharCalkTextField, mEdCharDlugTextField);

		UsersDesignedReinforcementController.addPropertiesToDesignedAs1TF(reinforcement, aS1TextField);
		UsersDesignedReinforcementController.addPropertiesToDesignedAs2TF(reinforcement, aS2TextField);

		ShearingReinforcementController.addPropertiesToNS1TF(reinforcement, nS1TextField);
		ShearingReinforcementController.addPropertiesToNS2TFDiagnosisScene(reinforcement, nS2TextField);
		ShearingReinforcementController.addPropertiesToAsw1TFDesignScene(reinforcement, aSW1TextField);
		ShearingReinforcementController.addPropertiesToAsw2TFDesignScene(reinforcement, aSW2TextFIeld);
		ShearingReinforcementController.addPropertiesToS2TFDiagnosisScene(reinforcement, s2TextField);
		ShearingReinforcementController.addPropertiesToThetaTFDesignScene(reinforcement, thetaTextField);
		ShearingReinforcementController.addPropertiesToAlfaTFDesignScene(reinforcement, alfaTextField);
		ShearingReinforcementController.addPropertiesToS1TFDiagnosisScene(reinforcement, s1TextField);

		AdditionalVariablesController.addPropertiesToAlfaMTF(internalForces, alfaMTextField);
		AdditionalVariablesController.addPropertiesToCementClass(cementClassChoiceBox, cement);
		AdditionalVariablesController.addPropertiesToLEffTF(dimensionsOfCrossSectionOfConcrete, lEffTextField);
		AdditionalVariablesController.addPropertiesToRHTF(concrete, rHTextField);
		AdditionalVariablesController.addPropertiesToT0TF(concrete, t0TextField);
		AdditionalVariablesController.addPropertiesToTypeOfLoad(internalForces, typeOfLoadChoiceBox);

		NumberOfRodsController.addPropertiesToAs1SymmetricalNumberOfRodsTF(aS1SymmetricalNumberOfRodsTextField,
				reinforcement);
		NumberOfRodsController.addPropertiesToAs2SymmetricalNumberOfRodsTF(aS2SymmetricalNumberOfRodsTextField,
				reinforcement);

		resultsPaneControllerDiagnosis = new ResultsPaneControllerDiagnosis(reinforcement, internalForces, sls,
				gridLabel00, gridLabel01, gridLabel02, gridLabel03, gridLabel04, gridLabel05, gridLabel06, gridLabel07,
				gridLabel08, gridLabel09, gridLabel010, gridLabel011, gridLabel012, gridLabel013, gridLabel014,
				gridLabel015, gridLabel016, gridLabel017, gridLabel018, gridLabel019, gridLabel020, gridLabel10,
				gridLabel11, gridLabel12, gridLabel13, gridLabel14, gridLabel15, gridLabel16, gridLabel17, gridLabel18,
				gridLabel19, gridLabel110, gridLabel111, gridLabel112, gridLabel113, gridLabel114, gridLabel115,
				gridLabel116, gridLabel117, gridLabel118, gridLabel119, gridLabel120, gridLabel20, gridLabel21,
				gridLabel22, gridLabel23, gridLabel24, gridLabel25, gridLabel26, gridLabel27, gridLabel28, gridLabel29,
				gridLabel210, gridLabel211, gridLabel212, gridLabel213, gridLabel214, gridLabel215, gridLabel216,
				gridLabel217, gridLabel218, gridLabel219, gridLabel220, gridLabel30, gridLabel31, gridLabel32,
				gridLabel33, gridLabel34, gridLabel35, gridLabel36, gridLabel37, gridLabel38, gridLabel39, gridLabel310,
				gridLabel311, gridLabel312, gridLabel313, gridLabel314, gridLabel315, gridLabel316, gridLabel317,
				gridLabel318, gridLabel319, gridLabel320, diagnosisMainAlgorithm, stanGranicznyNosnosciNequal0Label,
				leftSGNNequal0Line, rightSGNNequal0Line, zbrojeniePodluzneNequal0Label,
				leftZbrojeniePodluzneNequal0Line, rightZbrojeniePodluzneNequal0Line, zbrojeniePoprzeczneNequal0Label,
				leftZbrojeniePoprzeczneNequal0Line, rightZbrojeniePoprzeczneNequal0Line,
				stanGranicznyUzytkowalnosciNequal0Label1, leftSGUNequal0Line1, rightSGUNequal0Line1,
				stanGranicznyUzytkowalnosciNequal0Label2, leftSGUNequal0Line2, rightSGUNequal0Line2,

				zbrojeniePoprzeczneNNotequal0Label, leftZbrojeniePoprzeczneNNotequal0Line,
				rightZbrojeniePoprzeczneNNotequal0Line);
		DiagnosisButtonController.addPropertiesToDiagnosisButton(diagnosisButton, requiredReinforcementSeter, concrete,
				steel, internalForces, dimensionsOfCrossSectionOfConcrete, reinforcement,
				resultsPaneControllerDiagnosis, cement, sls, internalForces, creep, diagnosisMainAlgorithm, stiffness);

		SaveFileButtonController.addPropertiesToDiagnosisSceneSaveButton(saveToPdfButton, concrete, steel,
				reinforcement, internalForces, dimensionsOfCrossSectionOfConcrete, sls, diagnosisMainAlgorithm);
		
		GraphButtonController.addPropertiesToDesignButton(graphButton, this, graph);
		
	}

	public void giveReferences(Main main) {
		this.main = main;
	}
	
	public void giveReferences(GraphScreenController graphController) {
		this.graphController = graphController;
	}

	@FXML
	private void switchToDesignScene(ActionEvent event) {
		main.switchToDesignScene();
	}

	@FXML
	public void switchToGraphScene(ActionEvent event) {
		main.switchToGraphScene();
	}

	public ChoiceBox<String> getConcreteChoiceBox() {
		return concreteChoiceBox;
	}

	public ChoiceBox<String> getCrossSectionTypeChoiceBox() {
		return crossSectionTypeChoiceBox;
	}

	public TextField getA1DimensionTextField() {
		return a1DimensionTextField;
	}

	public TextField getA2DimensionTextField() {
		return a2DimensionTextField;
	}

	public TextField getcNomTextField() {
		return cNomTextField;
	}

	public TextField getbTextField() {
		return bTextField;
	}

	public TextField gethTextField() {
		return hTextField;
	}

	public TextField getbEffTextField() {
		return bEffTextField;
	}

	public TextField gettWTextField() {
		return tWTextField;
	}

	public TextField getmEdObliczenioweTextField() {
		return mEdObliczenioweTextField;
	}

	public TextField getmEdCharCalkTextField() {
		return mEdCharCalkTextField;
	}

	public TextField getmEdCharDlugTextField() {
		return mEdCharDlugTextField;
	}

	public TextField getnEdTextField() {
		return nEdTextField;
	}

	public TextField getvEdTextField() {
		return vEdTextField;
	}

	public TextField getaS1TextField() {
		return aS1TextField;
	}

	public TextField getaS2TextField() {
		return aS2TextField;
	}

	public TextField getlEffTextField() {
		return lEffTextField;
	}

	public TextField getAlfaMTextField() {
		return alfaMTextField;
	}

	public TextField getT0TextField() {
		return t0TextField;
	}

	public TextField getrHTextField() {
		return rHTextField;
	}

	public ChoiceBox<String> getCementClassChoiceBox() {
		return cementClassChoiceBox;
	}

	public ChoiceBox<String> getTypeOfLoadChoiceBox() {
		return typeOfLoadChoiceBox;
	}

	public TextField getwLimTextField() {
		return wLimTextField;
	}

	public TextField getaLimTextField() {
		return aLimTextField;
	}

	public TextField getaSW1TextField() {
		return aSW1TextField;
	}

	public TextField getaSW2TextFIeld() {
		return aSW2TextFIeld;
	}

	public TextField getnS1TextField() {
		return nS1TextField;
	}

	public TextField getnS2TextField() {
		return nS2TextField;
	}

	public TextField getS2TextField() {
		return s2TextField;
	}

	public TextField getThetaTextField() {
		return thetaTextField;
	}

	public TextField getAlfaTextField() {
		return alfaTextField;
	}

	public TextField getfYkTextField() {

		return fYkTextField;
	}

	public TextField getaS1SymmetricalNumberOfRodsTextField() {
		return aS1SymmetricalNumberOfRodsTextField;
	}

	public TextField getaS2SymmetricalNumberOfRodsTextField() {
		return aS2SymmetricalNumberOfRodsTextField;
	}

	public TextField getaS1UnsymmetricalNumberOfRodsTextField() {
		return aS1UnsymmetricalNumberOfRodsTextField;
	}

	public TextField getaS2UnsymmetricalNumberOfRodsTextField() {
		return aS2UnsymmetricalNumberOfRodsTextField;
	}

	public TextField gets1TextField() {
		return s1TextField;
	}

	public TextField getPdfNameTF() {
		return pdfName;
	}

	public TextField getMomentMmax() {
		return momentMmax;
	}

	public TextField getNormalnaMmax() {
		return normalnaMmax;
	}

	public TextField getMomentMmin() {
		return momentMmin;
	}

	public TextField getNormalnaMmin() {
		return normalnaMmin;
	}

	public TextField getMomentNmax() {
		return momentNmax;
	}

	public TextField getNormalnaNmax() {
		return normalnaNmax;
	}

	public TextField getMomentNmin() {
		return momentNmin;
	}

	public TextField getNormalnaNmin() {
		return normalnaNmin;
	}
	
	public TextField getFiT0() {
		return fiT0;
	}
	
	public TextField getL0() {
		return l0;
	}

	public DimensionsOfCrossSectionOfConcrete getDimensionsOfCrossSectionOfConcrete() {
		return dimensionsOfCrossSectionOfConcrete;
	}

	public InternalForces getInternalForces() {
		return internalForces;
	}

	public Concrete getConcrete() {
		return concrete;
	}

	public Steel getSteel() {
		return steel;
	}

	public Reinforcement getReinforcement() {
		return reinforcement;
	}

	
	
	

	public DimensionsOfCrossSectionOfConcrete getDimensionsOfCrossSectionOfConcrete() {
		return dimensionsOfCrossSectionOfConcrete;
	}

	public InternalForces getInternalForces() {
		return internalForces;
	}

	public Concrete getConcrete() {
		return concrete;
	}

	public Steel getSteel() {
		return steel;
	}

	public Reinforcement getReinforcement() {
		return reinforcement;
	}

	public TextField getL0() {
		return l0;
	}

	public TextField getFiT0() {
		return fiT0;
	}
	
	

}
