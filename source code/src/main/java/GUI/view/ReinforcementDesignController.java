
package GUI.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import GUI.Main;
import GUI.ReinforcementDesignLibraryControllers.ADistanceTextFieldsController;
import GUI.ReinforcementDesignLibraryControllers.AdditionalVariablesController;
import GUI.ReinforcementDesignLibraryControllers.ConcreteChoiceBoxController;
import GUI.ReinforcementDesignLibraryControllers.CrossSectionTypeController;
import GUI.ReinforcementDesignLibraryControllers.InternalForcesController;
import GUI.ReinforcementDesignLibraryControllers.PdfController;
import GUI.ReinforcementDesignLibraryControllers.ReinforcementDesignButtonController;
import GUI.ReinforcementDesignLibraryControllers.ResultsPaneControllerULS;
import GUI.ReinforcementDesignLibraryControllers.SaveFileButtonController;
import GUI.ReinforcementDesignLibraryControllers.ShearingReinforcementController;
import GUI.ReinforcementDesignLibraryControllers.SteelParametersController;
import GUI.ReinforcementDesignLibraryControllers.UnicodeForLabels;
import GUI.ReinforcementDesignLibraryControllers.UsersDesignedReinforcementController;
import SLS.Sls;
import SLS.creepCoeficent.CreepCoeficent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

public class ReinforcementDesignController {

	ScreensController myController;
	ReinforcementDiagnosisController diagnosis;
	GraphScreenController graphController;
	Main main;

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
	private Label bEffLowerLabel;
	@FXML
	private Label tWLabel;
	@FXML
	private Label tWLowerLabel;

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

	/*
	@FXML
	private VBox lastColumnVBox;
	@FXML
	private Label emptyLastColumnLabel;
	@FXML
	private TextField l0;
	@FXML
	private TextField fiT0;
*/
	@FXML
	private AnchorPane anchorPaneMLine;
	
	@FXML
	private HBox NEdHBoxLine;
	
	///////
	@FXML
	private TextField VEdRedTextField;
	
	@FXML
	private TextField befftTextField;
	
	@FXML
	private TextField hftTextField;
	
	@FXML
	private VBox vBoxBeffHf;
	
	@FXML
	private CheckBox nominalCheckBox;
	
	@FXML
	private CheckBox columnCheckBox;
	
	
	/////
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
	private TextField s2TextField;
	@FXML
	private TextField thetaTextField;
	@FXML
	private TextField alfaTextField;
	// steel parameters
	@FXML
	private Label fYkLabel;

	@FXML
	private TextField fYkTextField;
	private ResultsPaneControllerULS resultsPaneControllerULS;
	// private ResultsPaneControllerSLS resultsPaneControllerSLS;
	// uncode labels
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
	private boolean wasResultsGenerated = false;

	protected static DimensionsOfCrossSectionOfConcrete dimensionsOfCrossSectionOfConcrete;
	protected static InternalForces internalForces;
	protected static Reinforcement reinforcement;
	protected static Concrete concrete;
	protected static Steel steel;
	protected static RequiredReinforcement requiredReinforcementSeter;
	protected static Cement cement;
	protected static Sls sls;
	protected static CreepCoeficent creep;
	protected static NominalStiffness stiffness;
	
	protected static Graph graph;
	
	protected static ArrayList<TextField> list1;
	protected static ArrayList<TextField> list2;
	protected static ArrayList<TextField> list3;
	

	public static final String Nominalstiffness = "mainalgorithm.NominalStiffness";
	public static final String InternalForces = "mainalgorithm.InternalForces";

	@FXML
	void initialize() {
		dimensionsOfCrossSectionOfConcrete = new DimensionsOfCrossSectionOfConcrete();
		internalForces = new InternalForces();
		concrete = new Concrete(4);
		reinforcement = new Reinforcement();
		steel = new Steel();
		requiredReinforcementSeter = new RequiredReinforcement();
		sls = new Sls();
		cement = new Cement(0);
		creep = new CreepCoeficent();
		stiffness = new NominalStiffness();
		graph = new Graph(steel, dimensionsOfCrossSectionOfConcrete, concrete, reinforcement, internalForces);
		
		UnicodeForLabels.addUnicodeForLabels(ctgThetaLabel, alfaLabel, alfaMLabel);

		PdfController.addPropertiesToPdfNameTextField(pdfName);

		ConcreteChoiceBoxController.addPropertiesToConcreteChoiceBox(concrete, concreteChoiceBox);

		SteelParametersController.addPropertiesToFYkTF(steel, fYkTextField);

		ADistanceTextFieldsController.addPropertiesToA1TextField(a1DimensionTextField,
				dimensionsOfCrossSectionOfConcrete);
		ADistanceTextFieldsController.addPropertiesToA2TextField(a2DimensionTextField,
				dimensionsOfCrossSectionOfConcrete);
		
		list1 = new ArrayList<>(Arrays.asList(momentMmax, momentMmin, momentNmax, momentNmax, momentNmin,
				normalnaMmax, normalnaMmin, normalnaNmax, normalnaNmin, hftTextField, tWTextField));
		
		list2 = new ArrayList<>(Arrays.asList(momentMmax, momentMmin, momentNmax, momentNmax, momentNmin,
				normalnaMmax, normalnaMmin, normalnaNmax, normalnaNmin, nEdTextField));
		
		list3 = new ArrayList<>(Arrays.asList(hftTextField, tWTextField,
				mEdObliczenioweTextField, nEdTextField, mEdCharCalkTextField, mEdCharDlugTextField));
		

		CrossSectionTypeController.addPorpertiesToCrossSectionTypeChoiceBox(crossSectionTypeChoiceBox, bEffTextField,
				tWTextField, bEffLabel, bEffLowerLabel, tWLabel, tWLowerLabel, dimensionsOfCrossSectionOfConcrete,
				columsCasesHBox, vBoxBeffHf, anchorPaneMLine, NEdHBoxLine, list1, list2, list3, befftTextField, columnCheckBox,
				internalForces, momentMmax, normalnaMmax);
		
		CrossSectionTypeController.addPropertiesToBEffTextField(bEffTextField, bTextField, dimensionsOfCrossSectionOfConcrete);
		
		
		
		///////////////////////
		CrossSectionTypeController.addPropertiesToBEfftTextField(befftTextField, bTextField, dimensionsOfCrossSectionOfConcrete);
		CrossSectionTypeController.addPropertiesTohftTextField(hftTextField, dimensionsOfCrossSectionOfConcrete);
		/////////////////////
		
		
		
		CrossSectionTypeController.addPropertiesToBTextField(bTextField, bEffTextField, befftTextField, dimensionsOfCrossSectionOfConcrete);
		CrossSectionTypeController.addPropertiesToHTextField(hTextField, dimensionsOfCrossSectionOfConcrete);
		CrossSectionTypeController.addPropertiesToTWTextField(tWTextField, dimensionsOfCrossSectionOfConcrete);

		InternalForcesController.addPropertiesToMEdTextField(internalForces, mEdObliczenioweTextField,
				mEdCharCalkTextField, mEdCharDlugTextField);
		InternalForcesController.addPropertiesToNEdTextField(internalForces, nEdTextField, crossSectionTypeChoiceBox);
		InternalForcesController.addPropertiesToVEdTextField(internalForces, vEdTextField);
		InternalForcesController.addPropertiesToVEdRedTextField(internalForces, VEdRedTextField);
		
		//////////////
		AdditionalVariablesController.addPropertiesTocNomTextField(dimensionsOfCrossSectionOfConcrete, cNomTextField);
		

		/// Metoda sprawdza poprawosc wprowadzonych danych i wpisuje w pola sily
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaMmax, InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaMmin, InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaNmax, InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, normalnaNmin, InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentMmax, InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentMmin, InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentNmax, InternalForces);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, momentNmin, InternalForces);
/*
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, l0, Nominalstiffness);
		InternalForcesController.addPropertiesToTextField(internalForces, stiffness, fiT0, Nominalstiffness);
*/
		/////////

		InternalForcesController.addPropertiesToMEdCharCalk(internalForces, mEdObliczenioweTextField,
				mEdCharCalkTextField, mEdCharDlugTextField);
		InternalForcesController.addPropertiesToMEdCharDlug(internalForces, mEdObliczenioweTextField,
				mEdCharCalkTextField, mEdCharDlugTextField);

		UsersDesignedReinforcementController.addPropertiesToDesignedAs1TF(reinforcement, aS1TextField);
		UsersDesignedReinforcementController.addPropertiesToDesignedAs2TF(reinforcement, aS2TextField);

		ShearingReinforcementController.addPropertiesToNS1TF(reinforcement, nS1TextField);
		ShearingReinforcementController.addPropertiesToNS2TFDesignScene(reinforcement, nS2TextField);
		ShearingReinforcementController.addPropertiesToAsw1TFDesignScene(reinforcement, aSW1TextField);
		ShearingReinforcementController.addPropertiesToAsw2TFDesignScene(reinforcement, aSW2TextFIeld);
		ShearingReinforcementController.addPropertiesToS2TFDesignScene(reinforcement, s2TextField);
		ShearingReinforcementController.addPropertiesToThetaTFDesignScene(reinforcement, thetaTextField);
		ShearingReinforcementController.addPropertiesToAlfaTFDesignScene(reinforcement, alfaTextField);

		AdditionalVariablesController.addPropertiesToAlfaMTF(internalForces, alfaMTextField);
		AdditionalVariablesController.addPropertiesToCementClass(cementClassChoiceBox, cement);
		AdditionalVariablesController.addPropertiesToLEffTF(dimensionsOfCrossSectionOfConcrete, lEffTextField);
		AdditionalVariablesController.addPropertiesToRHTF(concrete, rHTextField);
		AdditionalVariablesController.addPropertiesToT0TF(concrete, t0TextField);
		AdditionalVariablesController.addPropertiesToTypeOfLoad(internalForces, typeOfLoadChoiceBox);

		resultsPaneControllerULS = new ResultsPaneControllerULS(reinforcement, internalForces, sls, gridLabel00,
				gridLabel01, gridLabel02, gridLabel03, gridLabel04, gridLabel05, gridLabel06, gridLabel07, gridLabel08,
				gridLabel09, gridLabel010, gridLabel011, gridLabel012, gridLabel013, gridLabel014, gridLabel015,
				gridLabel10, gridLabel11, gridLabel12, gridLabel13, gridLabel14, gridLabel15, gridLabel16, gridLabel17,
				gridLabel18, gridLabel19, gridLabel110, gridLabel111, gridLabel112, gridLabel113, gridLabel114,
				gridLabel115, gridLabel20, gridLabel21, gridLabel22, gridLabel23, gridLabel24, gridLabel25, gridLabel26,
				gridLabel27, gridLabel28, gridLabel29, gridLabel210, gridLabel211, gridLabel212, gridLabel213,
				gridLabel214, gridLabel215, gridLabel30, gridLabel31, gridLabel32, gridLabel33, gridLabel34,
				gridLabel35, gridLabel36, gridLabel37, gridLabel38, gridLabel39, gridLabel310, gridLabel311,
				gridLabel312, gridLabel313, gridLabel314, gridLabel315, stanGranicznyNosnosciNequal0Label,
				leftSGNNequal0Line, rightSGNNequal0Line, zbrojeniePodluzneNequal0Label,
				leftZbrojeniePodluzneNequal0Line, rightZbrojeniePodluzneNequal0Line, zbrojeniePoprzeczneNequal0Label,
				leftZbrojeniePoprzeczneNequal0Line, rightZbrojeniePoprzeczneNequal0Line,
				stanGranicznyUzytkowalnosciNequal0Label1, leftSGUNequal0Line1, rightSGUNequal0Line1,
				stanGranicznyUzytkowalnosciNequal0Label2, leftSGUNequal0Line2, rightSGUNequal0Line2,
				zbrojeniePodluzneSymetryczneLabel, leftZbrojeniePodluzneSymetryczneLine,
				rightZbrojeniePodluzneSymetryczneLine, zbrojeniePodluzneNiesymetryczneLabel,
				leftZbrojeniePodluzneNiesymetryczneLine, rightZbrojeniePodluzneNiesymetryczneLine,
				zbrojeniePoprzeczneNNotequal0Label, leftZbrojeniePoprzeczneNNotequal0Line,
				rightZbrojeniePoprzeczneNNotequal0Line);

		ReinforcementDesignButtonController.addPropertiesToDesignButton(countButton, requiredReinforcementSeter,
				concrete, steel, internalForces, dimensionsOfCrossSectionOfConcrete, reinforcement,
				resultsPaneControllerULS, cement, sls, internalForces, creep, wasResultsGenerated, stiffness, nominalCheckBox);

		// CountButtonController.addPropertiesToDesignButton(countButton,
		// requiredReinforcementSeter, concrete, steel, internalForces,
		// dimensionsOfCrossSectionOfConcrete, reinforcement,
		// resultsPaneControllerULS, cement, sls, internalForces, creep);

		SaveFileButtonController.addPropertiesToDesignSceneSaveButton(saveToPdfButton, concrete, steel, reinforcement,
				internalForces, dimensionsOfCrossSectionOfConcrete, sls);
		
		

	}

	public void giveReferences(Main main) {
		this.main = main;
	}

	public void giveReferences(ReinforcementDiagnosisController diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	public void giveReferences(GraphScreenController graphController) {
		this.graphController = graphController;
	}
	
	public void setTypeSection(String value) {
		crossSectionTypeChoiceBox.setValue(value);
	}
	

	@FXML
	private void switchToDiagnosisScene(ActionEvent event) {
		diagnosis.setTypeCrossSectionChoice(crossSectionTypeChoiceBox.getValue());
		main.switchToDiagnosisScene();
		pushNumberOfRodsToDiagnosisScene();
		pushS1ValueToDiagnosisScene();
	}

	public void bindPropertiesWithDiagnosisScene() {
		bindBidirectionalA1DimensionsTFs();
		bindBidirectionalA2DimensionsTFs();
		;
		bindBidirectionalBTFs();
		bindBidirectionalHTFs();
		bindBidirectionalBEffTFs();
		bindBidirectionalTwTFs();
		bindBidirectionalmEdObliczenioweTFs();
		bindBidirectionalmEdCharCalkTFs();
		bindBidirectionalmEdCharDlugTFs();
		bindBidirectionalnEdTFs();
		bindBidirectionalvEdTFs();
		bindBidirectionalaS1TFs();
		bindBidirectionalaS2TFs();
		bindBidirectionalalEffTFs();
		bindBidirectionalaAlfaMTFs();
		bindBidirectionalaT0TFs();
		bindBidirectionalarHTFs();
		bindBidirectionalCementClassCBs();
		bindBidirectionalTypeOfLoadCBs();
		// bindBidirectionalarwLimTFs();
		// bindBidirectionalaraLimTFs();
		bindBidirectionalaraSW1TFs();
		bindBidirectionalaraSW2TFs();
		bindBidirectionalarnS1TFs();
		bindBidirectionalarnS2TFs();
		bindDiagnosisS2TFtoDesignS2TF();
		bindBidirectionalarThetaTFs();
		bindBidirectionalarAlfaTFs();
		bindBidirectionalarfYkTFs();
		//bindBidirectionalCrossSectionTypeCBs();
		bindBidirectionalConcreteCBs();
		bindBidirectionalPdfName();

		bindBidirectionalarMomentMmax();
		bindBidirectionalarMomentMmin();
		bindBidirectionalarMomentNmax();
		bindBidirectionalarMomentNmin();

		bindBidirectionalarNormalnaMmax();
		bindBidirectionalarNormalnaMmin();
		bindBidirectionalarNormalnaNmax();
		bindBidirectionalarNormalnaNmin();
/*
		bindBidirectionalarL0();
		bindBidirectionalarFiT0();
*/
		
		bindBidirectionalbefft();
		bindBidirectionalhft();
		bindBidirectionalcNom();

	}

	private void bindBidirectionalA1DimensionsTFs() {
		a1DimensionTextField.textProperty().bindBidirectional(diagnosis.getA1DimensionTextField().textProperty());
	}

	private void bindBidirectionalA2DimensionsTFs() {
		a2DimensionTextField.textProperty().bindBidirectional(diagnosis.getA2DimensionTextField().textProperty());
	}

	private void bindBidirectionalCnomTFs() {
		cNomTextField.textProperty().bindBidirectional(diagnosis.getcNomTextField().textProperty());
	}

	private void bindBidirectionalBTFs() {
		bTextField.textProperty().bindBidirectional(diagnosis.getbTextField().textProperty());
	}

	private void bindBidirectionalHTFs() {
		hTextField.textProperty().bindBidirectional(diagnosis.gethTextField().textProperty());
	}

	private void bindBidirectionalBEffTFs() {
		bEffTextField.textProperty().bindBidirectional(diagnosis.getbEffTextField().textProperty());
	}

	private void bindBidirectionalTwTFs() {
		tWTextField.textProperty().bindBidirectional(diagnosis.gettWTextField().textProperty());
	}

	private void bindBidirectionalmEdObliczenioweTFs() {
		mEdObliczenioweTextField.textProperty()
				.bindBidirectional(diagnosis.getmEdObliczenioweTextField().textProperty());
	}

	private void bindBidirectionalmEdCharCalkTFs() {
		mEdCharCalkTextField.textProperty().bindBidirectional(diagnosis.getmEdCharCalkTextField().textProperty());
	}

	private void bindBidirectionalmEdCharDlugTFs() {
		mEdCharDlugTextField.textProperty().bindBidirectional(diagnosis.getmEdCharDlugTextField().textProperty());
	}

	private void bindBidirectionalnEdTFs() {
		nEdTextField.textProperty().bindBidirectional(diagnosis.getnEdTextField().textProperty());
	}

	private void bindBidirectionalvEdTFs() {
		vEdTextField.textProperty().bindBidirectional(diagnosis.getvEdTextField().textProperty());
	}

	private void bindBidirectionalaS1TFs() {
		aS1TextField.textProperty().bindBidirectional(diagnosis.getaS1TextField().textProperty());
	}

	private void bindBidirectionalaS2TFs() {
		aS2TextField.textProperty().bindBidirectional(diagnosis.getaS2TextField().textProperty());
	}

	private void bindBidirectionalalEffTFs() {
		lEffTextField.textProperty().bindBidirectional(diagnosis.getlEffTextField().textProperty());
	}

	private void bindBidirectionalaAlfaMTFs() {
		alfaMTextField.textProperty().bindBidirectional(diagnosis.getAlfaMTextField().textProperty());
	}

	private void bindBidirectionalaT0TFs() {
		t0TextField.textProperty().bindBidirectional(diagnosis.getT0TextField().textProperty());
	}

	private void bindBidirectionalarHTFs() {
		rHTextField.textProperty().bindBidirectional(diagnosis.getrHTextField().textProperty());
	}

	private void bindBidirectionalCementClassCBs() {
		cementClassChoiceBox.selectionModelProperty()
				.bindBidirectional(diagnosis.getCementClassChoiceBox().selectionModelProperty());
	}

	private void bindBidirectionalTypeOfLoadCBs() {
		typeOfLoadChoiceBox.selectionModelProperty()
				.bindBidirectional(diagnosis.getTypeOfLoadChoiceBox().selectionModelProperty());
	}

	private void bindBidirectionalarwLimTFs() {
		wLimTextField.textProperty().bindBidirectional(diagnosis.getwLimTextField().textProperty());
	}

	private void bindBidirectionalaraLimTFs() {
		aLimTextField.textProperty().bindBidirectional(diagnosis.getaLimTextField().textProperty());
	}

	private void bindBidirectionalaraSW1TFs() {
		aSW1TextField.textProperty().bindBidirectional(diagnosis.getaSW1TextField().textProperty());
	}

	private void bindBidirectionalaraSW2TFs() {
		aSW2TextFIeld.textProperty().bindBidirectional(diagnosis.getaSW2TextFIeld().textProperty());
	}

	private void bindBidirectionalarnS1TFs() {
		nS1TextField.textProperty().bindBidirectional(diagnosis.getnS1TextField().textProperty());
	}

	private void bindBidirectionalarnS2TFs() {
		nS2TextField.textProperty().bindBidirectional(diagnosis.getnS2TextField().textProperty());
	}

	private void bindDiagnosisS2TFtoDesignS2TF() {
		s2TextField.textProperty().bindBidirectional(diagnosis.getS2TextField().textProperty());
	}

	private void bindBidirectionalarThetaTFs() {
		thetaTextField.textProperty().bindBidirectional(diagnosis.getThetaTextField().textProperty());
	}

	private void bindBidirectionalarAlfaTFs() {
		alfaTextField.textProperty().bindBidirectional(diagnosis.getAlfaTextField().textProperty());
	}

	private void bindBidirectionalarfYkTFs() {
		fYkTextField.textProperty().bindBidirectional(diagnosis.getfYkTextField().textProperty());
	}

	private void bindBidirectionalarMomentMmax() {
		momentMmax.textProperty().bindBidirectional(diagnosis.getMomentMmax().textProperty());
	}

	private void bindBidirectionalarMomentMmin() {
		momentMmin.textProperty().bindBidirectional(diagnosis.getMomentMmin().textProperty());
	}

	private void bindBidirectionalarMomentNmax() {
		momentNmax.textProperty().bindBidirectional(diagnosis.getMomentNmax().textProperty());
	}

	private void bindBidirectionalarMomentNmin() {
		momentNmin.textProperty().bindBidirectional(diagnosis.getMomentNmin().textProperty());
	}

	private void bindBidirectionalarNormalnaNmin() {
		normalnaNmin.textProperty().bindBidirectional(diagnosis.getNormalnaNmin().textProperty());
	}

	private void bindBidirectionalarNormalnaNmax() {
		normalnaNmax.textProperty().bindBidirectional(diagnosis.getNormalnaNmax().textProperty());
	}

	private void bindBidirectionalarNormalnaMmax() {
		normalnaMmax.textProperty().bindBidirectional(diagnosis.getNormalnaMmax().textProperty());
	}

	private void bindBidirectionalarNormalnaMmin() {
		normalnaMmin.textProperty().bindBidirectional(diagnosis.getNormalnaMmin().textProperty());
	}
/*
	private void bindBidirectionalarL0() {
		l0.textProperty().bindBidirectional(diagnosis.getL0().textProperty());
	}

	private void bindBidirectionalarFiT0() {
		fiT0.textProperty().bindBidirectional(diagnosis.getFiT0().textProperty());
	}
	*/
	private void bindBidirectionalCrossSectionTypeCBs() {
		crossSectionTypeChoiceBox.selectionModelProperty()
				.bindBidirectional(diagnosis.getCrossSectionTypeChoiceBox().selectionModelProperty());
	}

	private void bindBidirectionalConcreteCBs() {
		concreteChoiceBox.selectionModelProperty()
				.bindBidirectional(diagnosis.getConcreteChoiceBox().selectionModelProperty());
	}
	
	private void bindBidirectionalbefft() {
		befftTextField.textProperty().bindBidirectional(diagnosis.getBefftTextField().textProperty());
	}
	
	private void bindBidirectionalhft() {
		hftTextField.textProperty().bindBidirectional(diagnosis.getHftTextField().textProperty());
	}
	
	private void bindBidirectionalcNom() {
		cNomTextField.textProperty().bindBidirectional(diagnosis.getcNomTextField().textProperty());
	}

	private void pushNumberOfRodsToDiagnosisScene() {
		pushNumberOfRodsAs1Symmetrical();
		pushNumberOfRodsAs2Symmetrical();
		// pushNumberOfRodsAs1Unsymmetrical();
		// pushNumberOfRodsAs2Unsymmetrical();
	}

	private void pushNumberOfRodsAs1Symmetrical() {
		diagnosis.getaS1SymmetricalNumberOfRodsTextField()
				.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS1()));
	}

	private void pushNumberOfRodsAs2Symmetrical() {
		diagnosis.getaS2SymmetricalNumberOfRodsTextField()
				.setText(Integer.toString(reinforcement.getRequiredNumberOfSymmetricalRodsAS2()));
	}

	private void pushNumberOfRodsAs1Unsymmetrical() {
		diagnosis.getaS1UnsymmetricalNumberOfRodsTextField()
				.setText(Integer.toString(reinforcement.getRequiredNumberOfUnsymmetricalRodsAS1()));
	}

	private void pushNumberOfRodsAs2Unsymmetrical() {
		diagnosis.getaS2UnsymmetricalNumberOfRodsTextField()
				.setText(Integer.toString(reinforcement.getRequiredNumberOfUnsymmetricalRodsAS2()));
	}

	private void pushS1ValueToDiagnosisScene() {
		diagnosis.gets1TextField().setText(Double.toString(reinforcement.getS1Designed()));
	}

	private void bindBidirectionalPdfName() {
		pdfName.textProperty().bindBidirectional(diagnosis.getPdfNameTF().textProperty());
	}
}
