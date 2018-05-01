package GUI;

import java.io.IOException;

import GUI.view.ReinforcementDesignController;
import GUI.view.ReinforcementDiagnosisController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane design;
	private AnchorPane diagnosis;
	private ReinforcementDesignController designController;
	private ReinforcementDiagnosisController diagnosisController;
	private BorderPane rootLayout;
	private HBox hbox;
	private static final String colorBackground = "#e8eaed;";
	

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(
				"RCdesign v3.0 - Projektowanie                                                                                                             autor:Tadeusz Miesi¹c pod kierunkiem JŒ & MD ");
		initRootLayout();
		loadDesignScene();
		loadDiagnosisScene();
		//rootLayout.setCenter(design);
		hbox.getChildren().add(design);
		designController.giveReferences(diagnosisController);
		designController.bindPropertiesWithDiagnosisScene();
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Root.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootLayout.setStyle("-fx-background-color: " + colorBackground);
			
			hbox = new HBox();
			hbox.maxWidth(895);
			
			rootLayout.setCenter(hbox);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
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

	public void switchToDiagnosisScene() {
		hbox.getChildren().remove(design);
		hbox.getChildren().add(diagnosis);
		
		rootLayout.setStyle("-fx-background-color: #d1d1d1;");
		primaryStage.setTitle(
				"RCdesign v3.0 - Diagnostyka                                                                                                               autor:Tadeusz Miesi¹c pod kierunkiem JŒ & MD");

	}

	public void switchToDesignScene() {
		hbox.getChildren().remove(diagnosis);
		hbox.getChildren().add(design);
		
		rootLayout.setStyle("-fx-background-color: " + colorBackground);
		//rootLayout.setTop(design);
		primaryStage.setTitle(
				"RCdesign v3.0 - Projektowanie                                                                                                             autor:Tadeusz Miesi¹c pod kierunkiem JŒ & MD ");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
