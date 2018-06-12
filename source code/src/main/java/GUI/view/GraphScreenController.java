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

	
	Main main;
	ReinforcementDiagnosisController diagnosis;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button backButton;

	@FXML
	private LineChart<Number, Number> lineChart;
/*
	@FXML
	private Axis<Number> xAxis;

	@FXML
	private NumberAxis yAxis;
	*/
	private static Graph graph = ReinforcementDesignController.graph;

	//private static XYChart.Series<Number, Number> series;
	

	@FXML
	void initialize() {
		lineChart.setCreateSymbols(false);
		lineChart.getStyleClass().add("thick-chart");
		System.out.println("\nODPALONO GRAPHCONTROLLER!\n");
		//lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		graph.addSeries(lineChart);
		//graph = new Graph(lineChart, diagnosis.getSteel(), diagnosis.getDimensionsOfCrossSectionOfConcrete(), 
		//		diagnosis.getConcrete(), diagnosis.getReinforcement());
		

		//series = new XYChart.Series<Number, Number>();
		//series.setName("noœnoœci");
		
	}
	
	public static void plot() {
		graph.plotGraph();
	}
	
	/*
	public static void newGraph(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions,
			Concrete concrete, Reinforcement reinforcement) {
		System.out.println("\nNowy wykres\n");
		//Graph graph = new Graph(steel, dimensions, concrete, reinforcement);
		//graph.prepareDataGraph(dimensions, steel, concrete, reinforcement);
		series.getData().clear();
		lineChart.getData().clear();
		//prepareGraph(graph.getPointsN(), graph.getPointsM());
		test();
	}
	
	private static void prepareGraph(LinkedList<Double> pointsN, LinkedList<Double> pointsM) {
		
		for (int i = 0; i < pointsN.size(); i++) {
			/*
			System.out.println("N" + i + ": " + pointsN.get(i));
			System.out.println("M" + i + ": " + pointsM.get(i));
			
			series.getData().add(new Data<Number, Number>(pointsN.get(i), pointsM.get(i)));
			}
			
		lineChart.getData().add(series);
	}
*/
	public void giveReferences(Main main) {
		this.main = main;
	}
	
	public void giveReferences(ReinforcementDiagnosisController diagnosis) {
		this.diagnosis = diagnosis;
	}
	/*
	private static void test() {
		for (double x = -10; x <= 10; x = x + 0.01) {
			series.getData().add(new XYChart.Data<Number, Number>(x, Math.pow(x, 2)));
        }
		lineChart.getData().add(series);
		
		System.out.println("Dodano dane do wykresu");
	}
*/
	@FXML
	void switchBackToDiagnosisScene(ActionEvent event) {
		main.switchBackToDiagnosisScene();
	}

	public LineChart<Number, Number> getLineChart() {
		return lineChart;
	}
	
	

}
