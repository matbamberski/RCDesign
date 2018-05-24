package reinforcement.graph;

import java.util.LinkedList;

import GUI.view.GraphScreenController;
import GUI.view.ReinforcementDiagnosisController;
import javafx.scene.chart.XYChart;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class Graph {

	ReinforcementDiagnosisController diagnosis;
	
	private Steel steel;
	private DimensionsOfCrossSectionOfConcrete dimensions;
	private Concrete concrete;
	private Reinforcement reinforcement;
	//private GraphScreenController graphCont;
	private double ypsilonCu3;
	private double ypsilonC3;
	private double LAMBDA;
	private double sigmaS1;
	private double sigmaS2;
	private double x;
	private double n;
	private double m;

	private double krok; // <- dok³adnoœæ wykresu (krok zwiêkszania wartoœci x)

	private double fcH;
	private double fcX;
	private double aS1;
	private double aS2;

	private double n7;

	// sprawdziæ, czy poni¿sze wymiary a2, d, h s¹ w [m]
	private double x_YdMin;
	private double xYdMin;
	private double xLim;
	private double x0;
	private double xYdMax;
	
	private LinkedList<Double> pointsN = new LinkedList<>();
	private LinkedList<Double> pointsM = new LinkedList<>();

	
	private XYChart<Number, Number> newGraph;
	
	public Graph() {};
	
	public Graph(final XYChart<Number, Number> newGraph, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete,
			Reinforcement reinforcement) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
		this.newGraph = newGraph;
	}
	
	public Graph(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete,
			Reinforcement reinforcement) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
	}

	private void init() {
		ypsilonCu3 = concrete.getEpsilonCU3();
		ypsilonC3 = concrete.getEpsilonC3();
		LAMBDA = 0.8;
		sigmaS1 = 0.0;
		sigmaS2 = 0.0;
		x = 0;
		n = 0;
		m = 0;
		krok = 0.01;
		fcH = concrete.getFCd() * dimensions.getB() * dimensions.getH();
		fcX = concrete.getFCd() * dimensions.getB() * LAMBDA;
		
		// uzale¿niæ aS1 i aS2 od tego czy symetryczne czy nie -- znaleŸæ warunek
				// symetrycznego
		if (reinforcement.getDesignedUnsymmetricalAS2() == 0) {
			aS1 = reinforcement.getDesignedSymmetricalAS1();
			aS2 = reinforcement.getDesignedSymmetricalAS2();
		} else {
			aS1 = reinforcement.getDesingedUnsymmetricalAS1();
			aS2 = reinforcement.getDesignedUnsymmetricalAS2();
		}

		///sprawdzic jednostki czy [m] (te wartosci sa juz policzone, trzeba polaczyc z obiektem liczonego zbrojenia)
		 n7 = ypsilonC3 * steel.getES() * (aS1 + aS2) + fcH;
		 x_YdMin = (ypsilonCu3 / (ypsilonCu3 + (steel.getFYd() / steel.getES()))) * dimensions.getA2();
		 xYdMin = (ypsilonCu3 / (ypsilonCu3 - (steel.getFYd() / steel.getES()))) * dimensions.getA2();
		 xLim = (ypsilonCu3 / (ypsilonCu3 + (steel.getFYd() / steel.getES()))) * dimensions.getD();
		 x0 = ((ypsilonCu3 - ypsilonC3) / ypsilonCu3) * dimensions.getH();
		 xYdMax = ((steel.getFYd() * x0)
					- (ypsilonC3 * dimensions.getA2() * steel.getES()) / (steel.getFYd() - (ypsilonC3 * steel.getES())));
		 
	}



	
	

	public void prepareDataGraph(DimensionsOfCrossSectionOfConcrete dimensions, Steel steel, Concrete concrete,
			Reinforcement reinforcement) {
		
		pointsM.clear();
		pointsN.clear();

		while (x < x_YdMin) { // przedzia³ 1
			sigmaS1 = steel.getFYd();
			sigmaS2 = -steel.getFYd();
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;

		}

		while (x < xYdMin) { // przedzia³ 2
			sigmaS1 = steel.getFYd();
			sigmaS2 = ypsilonCu3 * ((x - dimensions.getA2()) / x) * steel.getES();
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < xLim) { // przedzia³ 3
			sigmaS1 = steel.getFYd();
			sigmaS2 = steel.getFYd();
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < dimensions.getH()) { // przedzia³ 4
			if ((ypsilonCu3 * ((dimensions.getD() - x) / (x)) * steel.getES()) < steel.getFYd()) {
				sigmaS1 = ypsilonCu3 * ((dimensions.getD() - x) / (x)) * steel.getES();
			} else {
				sigmaS1 = steel.getFYd();
			}

			sigmaS2 = steel.getFYd();
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < (dimensions.getH() / LAMBDA)) { // przedzia³ 5
			if ((ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * steel.getES()) > -steel.getFYd()) {
				sigmaS1 = ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * steel.getES();
			} else {
				sigmaS1 = -steel.getFYd();
			}

			sigmaS2 = steel.getFYd();
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < xYdMax) { // przedzia³ 6
			if ((ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * steel.getES()) > -steel.getFYd()) {
				sigmaS1 = ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * steel.getES();
			} else {
				sigmaS1 = -steel.getFYd();
			}

			sigmaS2 = steel.getFYd();
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2());
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (n <= n7) { // przedzia³ 7

			if ((ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * steel.getES()) > -steel.getFYd()) {
				sigmaS1 = ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * steel.getES();
			} else {
				sigmaS1 = -steel.getFYd();
			}

			if ((ypsilonC3 * ((x - dimensions.getA2()) / (x - x0)) * steel.getES()) < steel.getFYd()) {
				sigmaS2 = ypsilonC3 * ((x - dimensions.getA2()) / (x - x0)) * steel.getES();
			} else {
				sigmaS2 = steel.getFYd();
			}

			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2());
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}
		/*
		for (int i = 0; i < pointsN.size(); i++) {
			System.out.println("N" + i + ": " + pointsN.get(i));
			System.out.println("M" + i + ": " + pointsM.get(i));

		}
		
		
		*/
		setPointsM(pointsM);
		setPointsN(pointsN);
		
	}
	
	private void test() {
		for (double x = 0; x<10; x=x+0.01) {
			pointsM.add(x);
			pointsN.add(Math.pow(x, 2));
		}
		setPointsM(pointsM);
		setPointsN(pointsN);
	}
	
	public void plotGraph() {
		init();
		System.out.println("PO INICJACJI");
		prepareDataGraph(dimensions, steel, concrete, reinforcement);
		//test();
		System.out.println("DATA PRZYGOTOWANA");
		final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		System.out.println("DODANO SERIES");
		assignData(series);
		System.out.println("PRZYPISANO DANE");
		newGraph.getData().clear();
		newGraph.getData().add(series);
		System.out.println("ZAKONCZONO");
		
	}
	
	public void addSeries(final XYChart<Number, Number> graph) {
		this.newGraph = graph;
	}
	
	private void assignData(final XYChart.Series<Number, Number> series) {
		for (int i = 0; i < pointsN.size(); i++) {
			series.getData().add(new XYChart.Data<Number, Number>(pointsN.get(i), pointsM.get(i)));
			}
	}
	
	public LinkedList<Double> getPointsN() {
		return pointsN;
	}

	public LinkedList<Double> getPointsM() {
		return pointsM;
	}

	public void setPointsN(LinkedList<Double> pointsN) {
		this.pointsN = pointsN;
	}

	public void setPointsM(LinkedList<Double> pointsM) {
		this.pointsM = pointsM;
	}

	
}
