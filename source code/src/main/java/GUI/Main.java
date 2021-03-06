package GUI;

import java.io.IOException;

import GUI.view.GraphScreenController;
import GUI.view.ReinforcementDesignController;
import GUI.view.ReinforcementDiagnosisController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane design;
	private AnchorPane diagnosis;
	private AnchorPane graph;
	private ReinforcementDesignController designController;
	private ReinforcementDiagnosisController diagnosisController;
	private GraphScreenController graphController;
	private BorderPane rootLayout;
	private HBox hbox;
	private static final String colorBackground = "#e8eaed;";
	

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.getIcons().add(new Image("/GUI/icon.png"));
		this.primaryStage.setTitle(
				"RCdesign v5.0 - Projektowanie                          autorzy:Tadeusz Miesi�c, Konrad Markowski, Mateusz Bamberski pod kierunkiem J� & MD ");
		initRootLayout();
		loadDesignScene();
		loadDiagnosisScene();
		loadGraphScene();
		//rootLayout.setCenter(design);
		hbox.getChildren().add(design);
		designController.giveReferences(diagnosisController);
		designController.giveReferences(graphController);
		diagnosisController.giveReferences(graphController);
		diagnosisController.giveReferences(designController);
		designController.bindPropertiesWithDiagnosisScene();
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Root.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootLayout.setStyle("-fx-background-color: #6897bb;");
			
			hbox = new HBox();
			//hbox.maxWidth(895);
			
			rootLayout.setCenter(hbox);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			//primaryStage.getIcons().add(new Image("icon.jpg"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadDesignScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ReinforcementDesign.fxml"));
			design = (AnchorPane) loader.load();
			designController = loader.getController();
			designController.giveReferences(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadDiagnosisScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ReinforcementDiagnosis.fxml"));
			diagnosis = (AnchorPane) loader.load();
			diagnosisController = loader.getController();
			diagnosisController.giveReferences(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadGraphScene() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/GraphScreen.fxml"));
			graph = (AnchorPane) loader.load();
			graphController = loader.getController();
			graphController.giveReferences(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void switchToDiagnosisScene() {
		hbox.getChildren().remove(design);
		hbox.getChildren().add(diagnosis);
		
		rootLayout.setStyle("-fx-background-color: #91bfa0;");
		primaryStage.setTitle(
				"RCdesign v5.0 - Diagnostyka                             autorzy:Tadeusz Miesi�c, Konrad Markowski, Mateusz Bamberski pod kierunkiem J� & MD ");

	}
	
	public void switchToGraphScene() {
		hbox.getChildren().remove(diagnosis);
		hbox.getChildren().add(graph);
		
		rootLayout.setStyle("-fx-background-color: #d1d1d1;");
		primaryStage.setTitle(
				"RCdesign v5.0 - Krzywe no�no�ci granicznej     autorzy:Tadeusz Miesi�c, Konrad Markowski, Mateusz Bamberski pod kierunkiem J� & MD ");

	}
	
	public void switchBackToDiagnosisScene() {
		hbox.getChildren().remove(graph);
		hbox.getChildren().add(diagnosis);
		
		rootLayout.setStyle("-fx-background-color: #91bfa0;");
		primaryStage.setTitle(
				"RCdesign v5.0 - Diagnostyka                             autorzy:Tadeusz Miesi�c, Konrad Markowski, Mateusz Bamberski pod kierunkiem J� & MD ");

	}

	public void switchToDesignScene() {
		hbox.getChildren().remove(diagnosis);
		hbox.getChildren().add(design);
		
		rootLayout.setStyle("-fx-background-color: #6897bb;");
		//rootLayout.setTop(design);
		primaryStage.setTitle(
				"RCdesign v5.0 - Projektowanie                          autorzy:Tadeusz Miesi�c, Konrad Markowski, Mateusz Bamberski pod kierunkiem J� & MD ");
	}
	

	public ReinforcementDiagnosisController getDiagnosisController() {
		return diagnosisController;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
