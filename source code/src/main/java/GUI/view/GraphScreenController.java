package GUI.view;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import GUI.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;
import reinforcement.graph.Graph;

public class GraphScreenController {

	private LinkedList<Double> pointsN = new LinkedList<>();
	private LinkedList<Double> pointsM = new LinkedList<>();
	Main main;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button backButton;

	@FXML
	private LineChart<Number, Number> lineChart;

	@FXML
	private Axis<Number> xAxis;

	@FXML
	private NumberAxis yAxis;

	public void setPointsN(LinkedList<Double> pointsN) {
		this.pointsN = pointsN;
	}

	public void setPointsM(LinkedList<Double> pointsM) {
		this.pointsM = pointsM;
	}

	@FXML
	void initialize() {
		
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Krzywe noœnoœci granicznej");

		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName("noœnoœci");

		for (int i = 0; i < pointsN.size(); i++) {
			System.out.println("N" + i + ": " + pointsN.get(i));
			System.out.println("M" + i + ": " + pointsM.get(i));
			series.getData().add(new Data<Number, Number>(pointsN.get(i), pointsM.get(i)));
			}
		lineChart.getData().add(series);
		
	}
	

	public void giveReferences(Main main) {
		this.main = main;

	}

	@FXML
	void switchBackToDiagnosisScene(ActionEvent event) {
		main.switchBackToDiagnosisScene();
	}

}
