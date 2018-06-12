package reinforcement.graph;

import java.util.ArrayList;

import GUI.view.ReinforcementDiagnosisController;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class Graph extends reinforcement.axisload.SymmetricalTensilingBeamReinforcement {

	ReinforcementDiagnosisController diagnosis;

	private Steel steel;
	private DimensionsOfCrossSectionOfConcrete dimensions;
	private Concrete concrete;
	private Reinforcement reinforcement;
	private InternalForces forces;
	// private GraphScreenController graphCont;
	private double ypsilonCu3;
	private double ypsilonC3;
	private double LAMBDA;
	private double sigmaS1;
	private double sigmaS2;
	private double x;
	private double n;
	private double m;

	private double krok; // <- dok³adnoœæ wykresu (krok zwiêkszania wartoœci x)
	private double dzielnik;

	private double fcH;
	private double fcX;
	private double aS1;
	private double aS2;

	private double n7;

	private ArrayList<Double> pointsN = new ArrayList<>();
	private ArrayList<Double> pointsM = new ArrayList<>();
	private ArrayList<Double> points_M = new ArrayList<>();

	private XYChart<Number, Number> newGraph;

	public Graph() {
	};

	public Graph(final XYChart<Number, Number> newGraph, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions,
			Concrete concrete, Reinforcement reinforcement) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
		this.newGraph = newGraph;

	}

	public Graph(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete,
			Reinforcement reinforcement, InternalForces forces) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
		this.forces = forces;
	}

	private void init() {
		ypsilonCu3 = concrete.getEpsilonCU3();
		ypsilonC3 = concrete.getEpsilonC3();
		LAMBDA = 0.8;
		sigmaS1 = 0.0;
		sigmaS2 = 0.0;
		setX0(concrete, steel, dimensions.getH());
		setXLim(concrete, steel, dimensions.getD());
		setXMaxYd(concrete, steel, dimensions.getA2());
		setXMinMinusYd(concrete, steel, dimensions.getA2());
		setXMinYd(concrete, steel, dimensions.getA2());
		x = 0;
		n = 0;
		m = 0;
		krok = 0.0;
		dzielnik = 10;
		fcH = concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH();
		fcX = concrete.getFCd() * 1000 * dimensions.getB() * LAMBDA;

		// uzale¿niæ aS1 i aS2 od tego czy symetryczne czy nie -- znaleŸæ warunek
		// symetrycznego

		aS1 = reinforcement.getDesignedSymmetricalAS1();
		aS2 = reinforcement.getDesignedSymmetricalAS2();
		System.out.println("As1: " + aS1);
		System.out.println("As2: " + aS2);

		/// sprawdzic jednostki czy [m] (te wartosci sa juz policzone, trzeba polaczyc z
		/// obiektem liczonego zbrojenia)
		n7 = ypsilonC3 * (steel.getES() * 1000000) * (aS1 + aS2) + fcH;
	}

	public void prepareDataGraph(DimensionsOfCrossSectionOfConcrete dimensions, Steel steel, Concrete concrete,
			Reinforcement reinforcement) {

		pointsM.clear();
		pointsN.clear();
		points_M.clear();

		while (x < xMinMinusYd) { // przedzia³ 1
			krok = (xMinMinusYd) / dzielnik;
			System.out.println("Przedzia³1");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = -(steel.getFYd() * 1000);
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;

		}

		while (x < xMinYd) { // przedzia³ 2
			krok = (xMinYd - xMinMinusYd) / dzielnik;
			System.out.println("Przedzia³2");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = ypsilonCu3 * ((x - dimensions.getA2()) / x) * (steel.getES() * 1000000);
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < xLim) { // przedzia³ 3
			krok = (xLim - xMinYd) / dzielnik;
			System.out.println("Przedzia³3");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = (steel.getFYd() * 1000);
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < dimensions.getH()) { // przedzia³ 4
			krok = (dimensions.getH() - xLim) / dzielnik;
			System.out.println("Przedzia³4");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			if ((ypsilonCu3 * ((dimensions.getD() - x) / (x)) * (steel.getES() * 1000000)) < (steel.getFYd() * 1000)) {
				sigmaS1 = ypsilonCu3 * ((dimensions.getD() - x) / (x)) * (steel.getES() * 1000000);
			} else {
				sigmaS1 = (steel.getFYd() * 1000);
			}

			sigmaS2 = (steel.getFYd() * 1000);
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < (dimensions.getH() / LAMBDA)) { // przedzia³ 5
			krok = ((dimensions.getH() / LAMBDA) - dimensions.getH()) / dzielnik;
			System.out.println("Przedzia³5");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			if ((ypsilonC3 * ((dimensions.getD() - x) / (x - x0))
					* (steel.getES() * 1000000)) > -(steel.getFYd() * 1000)) {
				sigmaS1 = ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * (steel.getES() * 1000000);
			} else {
				sigmaS1 = -(steel.getFYd() * 1000);
			}

			sigmaS2 = (steel.getFYd() * 1000);
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x);
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		while (x < xMaxYd) { // przedzia³ 6
			krok = (xMaxYd - (dimensions.getH() / LAMBDA)) / dzielnik;
			System.out.println("Przedzia³6");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			if ((ypsilonC3 * ((dimensions.getD() - x) / (x - x0))
					* (steel.getES() * 1000000)) > -(steel.getFYd() * 1000)) {
				sigmaS1 = ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * (steel.getES() * 1000000);
			} else {
				sigmaS1 = -(steel.getFYd() * 1000);
			}

			sigmaS2 = (steel.getFYd() * 1000);
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2());
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
		}

		System.out.println("N =" + n);
		System.out.println("N7 = " + n7);
		krok = (n7 - n) / dzielnik;
		if (n < n7) {
			while (n < n7) { // przedzia³ 7
				System.out.println("Przedzia³7");
				System.out.println("Krok = " + krok);
				System.out.println("X = " + x);

				if ((ypsilonC3 * ((dimensions.getD() - x) / (x - x0))
						* (steel.getES() * 1000000)) > -(steel.getFYd() * 1000)) {
					sigmaS1 = ypsilonC3 * ((dimensions.getD() - x) / (x - x0)) * (steel.getES() * 1000000);
				} else {
					sigmaS1 = -(steel.getFYd() * 1000);
				}

				if ((ypsilonC3 * ((x - dimensions.getA2()) / (x - x0)) * (steel.getES() * 1000000)) < (steel.getFYd()
						* 1000)) {
					sigmaS2 = ypsilonC3 * ((x - dimensions.getA2()) / (x - x0)) * (steel.getES() * 1000000);
				} else {
					sigmaS2 = (steel.getFYd() * 1000);
				}

				n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
				m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
						+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2());
				pointsN.add(n);
				pointsM.add(m);
				x = x + krok;
			}
		} else {
			System.err.println("N wiêksze ni¿ N7");
		}

		for (int i = 0; i < pointsN.size(); i++) {
			points_M.add(-pointsM.get(i));
		}
		pointsM.add((double) 0);
		points_M.add((double) 0);
		pointsN.add(n7);
		
		/*
		 * setPointsM(pointsM); setPoints_M(points_M); setPointsN(pointsN);
		 */
	}

	private void test() {
		for (double x = 0; x < 10; x = x + 0.01) {
			pointsM.add(x);
			pointsN.add(Math.pow(x, 2));
		}
		// setPointsM(pointsM);
		// setPointsN(pointsN);
	}

	public void plotGraph() {
		init();
		System.out.println("PO INICJACJI");
		prepareDataGraph(dimensions, steel, concrete, reinforcement);
		// test();
		System.out.println("DATA PRZYGOTOWANA");
		final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		final XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
		final XYChart.Series<Number, Number> seriesMed = new XYChart.Series<Number, Number>();
		final XYChart.Series<Number, Number> seriesMmax = new XYChart.Series<Number, Number>();
		final XYChart.Series<Number, Number> seriesMmin = new XYChart.Series<Number, Number>();
		final XYChart.Series<Number, Number> seriesNmax = new XYChart.Series<Number, Number>();
		final XYChart.Series<Number, Number> seriesNmin = new XYChart.Series<Number, Number>();
		System.out.println("DODANO SERIES");
		assignData(series, series2, seriesMed, seriesMmax, seriesMmin, seriesNmax, seriesNmin);
		System.out.println("PRZYPISANO DANE");
		newGraph.getData().clear();
		newGraph.getData().add(series);
		newGraph.getData().add(series2);
		if(forces.getmEd() !=0) {
			newGraph.getData().add(seriesMed);
		}else if(forces.getnEd() !=0) {
			newGraph.getData().add(seriesMed);
		}
		
		if(forces.getMomentMmax() !=0) {
			newGraph.getData().add(seriesMmax);
		}else if(forces.getNormalnaMmax() !=0) {
			newGraph.getData().add(seriesMmax);
		}
		
		if(forces.getMomentMmin() !=0) {
			newGraph.getData().add(seriesMmin);
		}else if(forces.getNormalnaMmin() !=0) {
			newGraph.getData().add(seriesMmin);
		}

		if(forces.getMomentNmax() !=0) {
			newGraph.getData().add(seriesNmax);
		}else if(forces.getNormalnaNmax() !=0) {
			newGraph.getData().add(seriesNmax);
		}

		if(forces.getMomentNmin() !=0) {
			newGraph.getData().add(seriesNmin);
		}else if(forces.getNormalnaNmin() !=0) {
			newGraph.getData().add(seriesNmin);
		}

		System.out.println("ZAKONCZONO");

	}

	public void addSeries(final XYChart<Number, Number> graph) {
		this.newGraph = graph;
	}

	private void assignData(final XYChart.Series<Number, Number> series, final XYChart.Series<Number, Number> series2,
			final XYChart.Series<Number, Number> seriesMed,
			final XYChart.Series<Number, Number> seriesMmax, final XYChart.Series<Number, Number> seriesMmin,
			final XYChart.Series<Number, Number> seriesNmax, final XYChart.Series<Number, Number> seriesNmin) {
		for (int i = 0; i < pointsN.size(); i++) {
			series.getData().add(new XYChart.Data<Number, Number>(pointsN.get(i), pointsM.get(i)));
			series2.getData().add(new XYChart.Data<Number, Number>(pointsN.get(i), points_M.get(i)));
		}

		series.setName("KNG");
		series2.setName("KNG");

		seriesMed.getData().add(new XYChart.Data<Number, Number>(forces.getnEd(),
		forces.getmEd()));
		seriesMed.setName("MEd");
		seriesMmax.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaMmax(), forces.getMomentMmax()));
		seriesMmax.setName("Mmax");
		seriesMmin.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaMmin(), forces.getMomentMmin()));
		seriesMmin.setName("Mmin");
		seriesNmax.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaNmax(), forces.getMomentNmax()));
		seriesNmax.setName("Nmax");
		seriesNmin.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaNmin(), forces.getMomentNmin()));
		seriesNmin.setName("Nmin");

	}
	/*
	 * public LinkedList<Double> getPointsN() { return pointsN; }
	 * 
	 * public LinkedList<Double> getPointsM() { return pointsM; }
	 * 
	 * public void setPointsN(LinkedList<Double> pointsN) { this.pointsN = pointsN;
	 * }
	 * 
	 * public void setPointsM(LinkedList<Double> pointsM) { this.pointsM = pointsM;
	 * }
	 * 
	 * public void setPoints_M(LinkedList<Double> points_M) { this.points_M =
	 * points_M; }
	 * 
	 */
}
