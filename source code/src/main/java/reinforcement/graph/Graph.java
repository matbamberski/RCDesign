package reinforcement.graph;

import java.util.ArrayList;

import GUI.view.ReinforcementDiagnosisController;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
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
	private double nn;
	private double m;
	private double mm;

	private double krok; // <- dok�adno�� wykresu (krok zwi�kszania warto�ci x)
	private double dzielnik;

	private double fcH;
	private double fcX;
	private double aS1;
	private double aS2;

	private double n01;
	private double n11;
	private double n21;
	private double n31;
	private double n41;
	private double n51;
	private double n61;
	private double n71;

	private double n02;
	private double n12;
	private double n22;
	private double n32;
	private double n42;
	private double n52;
	private double n62;
	private double n72;

	private ArrayList<Double> pointsN = new ArrayList<>();
	private ArrayList<Double> points_N = new ArrayList<>();
	private ArrayList<Double> pointsM = new ArrayList<>();
	private ArrayList<Double> points_M = new ArrayList<>();

	private XYChart<Number, Number> newGraph;
	
	private CheckBox nominalCheckBox;

	public Graph() {
	};
/*
	public Graph(final XYChart<Number, Number> newGraph, Steel steel, DimensionsOfCrossSectionOfConcrete dimensions,
			Concrete concrete, Reinforcement reinforcement) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
		this.newGraph = newGraph;

	}
*/
	public Graph(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete,
			Reinforcement reinforcement, InternalForces forces, CheckBox nominalCheckBox) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
		this.forces = forces;
		this.nominalCheckBox = nominalCheckBox;
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
		aS1 = reinforcement.getDesignedSymmetricalAS1();
		aS2 = reinforcement.getDesignedSymmetricalAS2();
		System.out.println("As1: " + aS1);
		System.out.println("As2: " + aS2);

		
		n01 = (-steel.getFYd() * 1000) * (aS1 + aS2);
		n11 = ((-steel.getFYd() * 1000) * (aS1 + aS2))
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * xMinMinusYd);
		n21 = ((steel.getFYd() * 1000) * (aS2 - aS1))
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * xMinYd);
		n31 = ((steel.getFYd() * 1000) * (aS2 - aS1))
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * xLim);
		n41 = (((-ypsilonCu3) * ((dimensions.getD() - dimensions.getH()) / dimensions.getH()) * steel.getES() * 1000000
				* aS1) + (steel.getFYd() * 1000 * aS2)
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * dimensions.getH()));
		n51 = (((-ypsilonC3)
				* ((dimensions.getD() - (dimensions.getH() / LAMBDA)) / ((dimensions.getH() / LAMBDA) - x0))
				* steel.getES() * 1000000 * aS1) + (steel.getFYd() * 1000 * aS2)
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * dimensions.getH()));
		n61 = (((-ypsilonC3) * ((dimensions.getD() - xMaxYd) / (xMaxYd - x0)) * steel.getES() * 1000000 * aS1)
				+ (steel.getFYd() * 1000 * aS2) + ((concrete.getFCd() * 1000) * dimensions.getB() * dimensions.getH()));
		n71 = ypsilonC3 * (steel.getES() * 1000000) * (aS1 + aS2) + fcH;

	}

	public void prepareDataGraph(DimensionsOfCrossSectionOfConcrete dimensions, Steel steel, Concrete concrete,
			Reinforcement reinforcement) {

		pointsM.clear();
		pointsN.clear();
		points_M.clear();

		while (x < xMinMinusYd) { // przedzial 1
			krok = (xMinMinusYd) / dzielnik;
			System.out.println("Przedzial 1");
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

		while (x < xMinYd) { // przedzial 2
			krok = (xMinYd - xMinMinusYd) / dzielnik;
			System.out.println("Przedzial 2");
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

		while (x <= xLim) { // przedzial 3
			krok = (xLim - xMinYd) / dzielnik;
			System.out.println("Przedzial 3");
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

		while (x <= dimensions.getH()) { // przedzial 4
			krok = (dimensions.getH() - xLim) / dzielnik;
			System.out.println("Przedzial 4");
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

		while (x <= (dimensions.getH() / LAMBDA)) { // przedzial 5
			krok = ((dimensions.getH() / LAMBDA) - dimensions.getH()) / dzielnik;
			System.out.println("Przedzial 5");
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

		while (x <= xMaxYd) { // przedzial 6
			krok = (xMaxYd - (dimensions.getH() / LAMBDA)) / dzielnik;
			System.out.println("Przedzial 6");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = -(steel.getFYd() * 1000);
			sigmaS2 = (steel.getFYd() * 1000);
			n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
			m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2());
			
			pointsN.add(n);
			pointsM.add(m);
			x = x + krok;
			System.out.println("N = " + n);
		}

		double xN7 = ((ypsilonC3 * steel.getES() * 1000000 * ((aS1 * dimensions.getD()) + (aS2 * dimensions.getA2()))
				+ (x0 * ((concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH()) - (n71 - 0.0001))))
				/ ((ypsilonC3 * ((steel.getES() * 1000000 * aS1) + (steel.getES() * 1000000 * aS2))) - (n71 - 0.0001)
						+ (concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH())));
		System.err.println("XN7 = " + xN7);

		krok = Math.abs((xN7 - x) / dzielnik);

		if (n <= n71) {
			while (n <= n71 & (1 - (n / n71)) > 0.0001 & mm < m) { // przedzial 7
				System.out.println("Przedzial 7");
				System.out.println("Krok = " + krok);

				sigmaS1 = -(steel.getFYd() * 1000);
				sigmaS2 = (steel.getFYd() * 1000);
				
				n = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
				m = sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA1())
						+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA2());

				System.out.println("X = " + x);
				System.out.println("N7 = " + n71);
				System.out.println("N = " + n);

				pointsN.add(n);
				pointsM.add(m);

				x = x + krok;

			}
		

		} else {
			System.err.println("N wieksze niz N7");

		}

		
		
		
		
		
		//// funkcja ujemna
		aS1 = reinforcement.getDesignedSymmetricalAS2();
		aS2 = reinforcement.getDesignedSymmetricalAS1();
		System.out.println("As1: " + aS1);
		System.out.println("As2: " + aS2);
		setX0(concrete, steel, dimensions.getH());
		setXLim(concrete, steel, dimensions.getD());
		setXMaxYd(concrete, steel, dimensions.getA1());
		setXMinMinusYd(concrete, steel, dimensions.getA1());
		setXMinYd(concrete, steel, dimensions.getA1());

		n02 = (-steel.getFYd() * 1000) * (aS1 + aS2);
		n12 = ((-steel.getFYd() * 1000) * (aS1 + aS2))
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * xMinMinusYd);
		n22 = ((steel.getFYd() * 1000) * (aS2 - aS1))
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * xMinYd);
		n32 = ((steel.getFYd() * 1000) * (aS2 - aS1))
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * xLim);
		n42 = (((-ypsilonCu3) * ((dimensions.getD() - dimensions.getH()) / dimensions.getH()) * steel.getES() * 1000000
				* aS1) + (steel.getFYd() * 1000 * aS2)
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * LAMBDA * dimensions.getH()));
		n52 = (((-ypsilonC3)
				* ((dimensions.getD() - (dimensions.getH() / LAMBDA)) / ((dimensions.getH() / LAMBDA) - x0))
				* steel.getES() * 1000000 * aS1) + (steel.getFYd() * 1000 * aS2)
				+ ((concrete.getFCd() * 1000) * dimensions.getB() * dimensions.getH()));
		n62 = (((-ypsilonC3) * ((dimensions.getD() - xMaxYd) / (xMaxYd - x0)) * steel.getES() * 1000000 * aS1)
				+ (steel.getFYd() * 1000 * aS2) + ((concrete.getFCd() * 1000) * dimensions.getB() * dimensions.getH()));
		n72 = ypsilonC3 * (steel.getES() * 1000000) * (aS1 + aS2) + fcH;

		x = 0;
		nn = 0;
		mm = 0;
		krok = 0.0;
		dzielnik = 10;
		points_N.clear();
		points_M.clear();

		while (x < xMinMinusYd) { // przedzial 1
			krok = (xMinMinusYd) / dzielnik;
			System.out.println("Przedzial 1");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = -(steel.getFYd() * 1000);
			nn = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;

			mm = -(sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x));
		
			points_N.add(nn);
			points_M.add(mm);
			x = x + krok;

		}

		while (x < xMinYd) { // przedzial 2
			krok = (xMinYd - xMinMinusYd) / dzielnik;
			System.out.println("Przedzial 2");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = ypsilonCu3 * ((x - dimensions.getA1()) / x) * (steel.getES() * 1000000);
			nn = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			mm = -(sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x));

			points_N.add(nn);
			points_M.add(mm);
			x = x + krok;
		}

		while (x <= xLim) { // przedzial 3
			krok = (xLim - xMinYd) / dzielnik;
			System.out.println("Przedzial 3");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = (steel.getFYd() * 1000);
			nn = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			mm = -(sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x));

			points_N.add(nn);
			points_M.add(mm);
			x = x + krok;
		}

		while (x <= dimensions.getH()) { // przedzial 4
			krok = (dimensions.getH() - xLim) / dzielnik;
			System.out.println("Przedzial 4");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = (steel.getFYd() * 1000);
			nn = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			mm = -(sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x));

			points_N.add(nn);
			points_M.add(mm);
			x = x + krok;
		}

		while (x <= (dimensions.getH() / LAMBDA)) { // przedzial 5
			krok = ((dimensions.getH() / LAMBDA) - dimensions.getH()) / dzielnik;
			System.out.println("Przedzial 5");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = (steel.getFYd() * 1000);
			sigmaS2 = (steel.getFYd() * 1000);
			nn = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcX * x;
			mm = -(sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA1())
					+ fcX * x * 0.5 * (dimensions.getH() - LAMBDA * x));
	
			points_N.add(nn);
			points_M.add(mm);
			x = x + krok;

		}

		while (x <= xMaxYd) { // przedzial 6
			krok = (xMaxYd - (dimensions.getH() / LAMBDA)) / dzielnik;
			System.out.println("Przedzial 6");
			System.out.println("Krok = " + krok);
			System.out.println("X = " + x);

			sigmaS1 = -(steel.getFYd() * 1000);
			sigmaS2 = (steel.getFYd() * 1000);
			nn = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
			mm = -(sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA2())
					+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA1()));
			
			points_N.add(nn);
			points_M.add(mm);
			x = x + krok;
			System.out.println("N = " + n);
		}

		xN7 = ((ypsilonC3 * steel.getES() * 1000000 * ((aS1 * dimensions.getD()) + (aS2 * dimensions.getA1()))
				+ (x0 * ((concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH()) - (n71 - 0.0001))))
				/ ((ypsilonC3 * ((steel.getES() * 1000000 * aS1) + (steel.getES() * 1000000 * aS2))) - (n71 - 0.0001)
						+ (concrete.getFCd() * 1000 * dimensions.getB() * dimensions.getH())));
		System.err.println("XN7 = " + xN7);

		krok = Math.abs((xN7 - x) / dzielnik);

		if (nn <= n72) {
			while (n <= n72 & (1 - (n / n72)) > 0.0001 & mm < m) { // przedzial 7
				System.out.println("Przedzial 7");
				System.out.println("Krok = " + krok);

				sigmaS1 = -(steel.getFYd() * 1000);
				sigmaS2 = (steel.getFYd() * 1000);
				nn = -sigmaS1 * aS1 + sigmaS2 * aS2 + fcH;
				mm = -(sigmaS1 * aS1 * (0.5 * dimensions.getH() - dimensions.getA2())
						+ sigmaS2 * aS2 * (0.5 * dimensions.getH() - dimensions.getA1()));
			
				System.out.println("X = " + x);
				System.out.println("N7 = " + n71);
				System.out.println("N = " + n);

				points_N.add(nn);
				points_M.add(mm);

				x = x + krok;

			}

		} else {
			System.err.println("N wi�ksze ni� N7");

		}
		
			
		if (aS1 == aS2) {
			pointsM.add((mm + m)/2);
			points_M.add((mm + m)/2);
			pointsN.add(n);
			points_N.add(nn);
		} else if (nn > n) {
			pointsM.add(mm);
			pointsN.add(nn);
		} else if (nn < n) {
			points_M.add(m);
			points_N.add(n);
		} 

		System.out.println("dlugosc N =" + pointsN.size());
		System.out.println("dlugosc M =" + pointsM.size());
		System.out.println("dlugosc -N =" + points_N.size());
		System.out.println("dlugosc -M =" + points_M.size());

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
		
		if (forces.getMomentMmax() == 0 && forces.getNormalnaMmax() == 0 && forces.getMomentMmin() == 0
				&& forces.getNormalnaMmin() == 0 && forces.getMomentNmax() == 0 && forces.getNormalnaNmax() == 0
						&& forces.getMomentNmin() == 0 && forces.getNormalnaNmin() == 0) {
			if (forces.getmEd() != 0) {
				newGraph.getData().add(seriesMed);
			} else if (forces.getnEd() != 0) {
				newGraph.getData().add(seriesMed);
			}
		}
		

		if (forces.getMomentMmax() != 0) {
			newGraph.getData().add(seriesMmax);
		} else if (forces.getNormalnaMmax() != 0) {
			newGraph.getData().add(seriesMmax);
		}

		if (forces.getMomentMmin() != 0) {
			newGraph.getData().add(seriesMmin);
		} else if (forces.getNormalnaMmin() != 0) {
			newGraph.getData().add(seriesMmin);
		}

		if (forces.getMomentNmax() != 0) {
			newGraph.getData().add(seriesNmax);
		} else if (forces.getNormalnaNmax() != 0) {
			newGraph.getData().add(seriesNmax);
		}

		if (forces.getMomentNmin() != 0) {
			newGraph.getData().add(seriesNmin);
		} else if (forces.getNormalnaNmin() != 0) {
			newGraph.getData().add(seriesNmin);
		}

		System.out.println("ZAKONCZONO");

	}

	public void addSeries(final XYChart<Number, Number> graph) {
		this.newGraph = graph;
	}

	private void assignData(final XYChart.Series<Number, Number> series, final XYChart.Series<Number, Number> series2,
			final XYChart.Series<Number, Number> seriesMed, final XYChart.Series<Number, Number> seriesMmax,
			final XYChart.Series<Number, Number> seriesMmin, final XYChart.Series<Number, Number> seriesNmax,
			final XYChart.Series<Number, Number> seriesNmin) {
		for (int i = 0; i < pointsN.size(); i++) {
			series.getData().add(new XYChart.Data<Number, Number>(pointsN.get(i), pointsM.get(i)));
		}
		for (int i = 0; i < points_N.size(); i++) {
			series2.getData().add(new XYChart.Data<Number, Number>(points_N.get(i), points_M.get(i)));
		}

		series.setName("KNG");
		series2.setName("KNG");

		if (forces.getMomentMmax() == 0 && forces.getNormalnaMmax() == 0 && forces.getMomentMmin() == 0
				&& forces.getNormalnaMmin() == 0 && forces.getMomentNmax() == 0 && forces.getNormalnaNmax() == 0
						&& forces.getMomentNmin() == 0 && forces.getNormalnaNmin() == 0) {
			if (nominalCheckBox.isSelected()) {
				seriesMed.getData().add(new XYChart.Data<Number, Number>(forces.getnEd(), forces.getmEdStiff()));
			} else
				seriesMed.getData().add(new XYChart.Data<Number, Number>(forces.getnEd(), forces.getmEd()));
			seriesMed.setName("MEd");
		}
		
		if (nominalCheckBox.isSelected()) {
			seriesMmax.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaMmax(), forces.getMomentMmaxStiff()));
			seriesMmin.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaMmin(), forces.getMomentMminStiff()));
			seriesNmax.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaNmax(), forces.getMomentNmaxStiff()));
			seriesNmin.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaNmin(), forces.getMomentNminStiff()));
		} else {
			seriesMmax.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaMmax(), forces.getMomentMmax()));
			seriesMmin.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaMmin(), forces.getMomentMmin()));
			seriesNmax.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaNmax(), forces.getMomentNmax()));
			seriesNmin.getData().add(new XYChart.Data<Number, Number>(forces.getNormalnaNmin(), forces.getMomentNmin()));
		}
		seriesMmax.setName("Mmax");
		seriesMmin.setName("Mmin");
		seriesNmax.setName("Nmax");
		seriesNmin.setName("Nmin");
		
	}
	
	
	
}
