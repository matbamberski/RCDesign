package reinforcement.graph;

import java.util.LinkedList;

import GUI.view.GraphScreenController;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class Graph {

	private Steel steel;
	private DimensionsOfCrossSectionOfConcrete dimensions;
	private Concrete concrete;
	private Reinforcement reinforcement;
	private GraphScreenController graphCont;
	

	public Graph(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, Concrete concrete,
			Reinforcement reinforcement, GraphScreenController graphCont) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
		this.graphCont = graphCont;
	}

	private final double ypsilonCu3 = 0.0035;
	private final double ypsilonC3 = 0.00157;
	private final double LAMBDA = 0.8;
	private double sigmaS1 = 0.0;
	private double sigmaS2 = 0.0;
	private double x = 0.0;
	private double n = 0.0;
	private double m = 0.0;

	private double krok = 0.001; // <- dok³adnoœæ wykresu (krok zwiêkszania wartoœci x)

	private double fcH = concrete.getFCd() * dimensions.getB() * dimensions.getH();
	private double fcX = concrete.getFCd() * dimensions.getB() * LAMBDA;
	private double aS1 = 0.0;
	private double aS2 = 0.0;

	{
		// uzale¿niæ aS1 i aS2 od tego czy symetryczne czy nie -- znaleŸæ warunek
		// symetrycznego
		if (reinforcement.getDesignedUnsymmetricalAS2() == 0) {
			aS1 = reinforcement.getDesignedSymmetricalAS1();
			aS2 = reinforcement.getDesignedSymmetricalAS2();
		} else {
			aS1 = reinforcement.getDesingedUnsymmetricalAS1();
			aS2 = reinforcement.getDesignedUnsymmetricalAS2();
		}
	}
	private double n7 = ypsilonC3 * steel.getES() * (aS1 + aS2) + fcH;

	// sprawdziæ, czy poni¿sze wymiary a2, d, h s¹ w [m]
	private double x_YdMin = (ypsilonCu3 / (ypsilonCu3 + (steel.getFYd() / steel.getES()))) * dimensions.getA2();
	private double xYdMin = (ypsilonCu3 / (ypsilonCu3 - (steel.getFYd() / steel.getES()))) * dimensions.getA2();
	private double xLim = (ypsilonCu3 / (ypsilonCu3 + (steel.getFYd() / steel.getES()))) * dimensions.getD();
	private double x0 = ((ypsilonCu3 - ypsilonC3) / ypsilonCu3) * dimensions.getH();
	private double xYdMax = ((steel.getFYd() * x0)
			- (ypsilonC3 * dimensions.getA2() * steel.getES()) / (steel.getFYd() - (ypsilonC3 * steel.getES())));

	private LinkedList<Double> pointsN = new LinkedList<>();
	private LinkedList<Double> pointsM = new LinkedList<>();
	
	

	public void plotGhraph(DimensionsOfCrossSectionOfConcrete dimensions, Steel steel, Concrete concrete,
			Reinforcement reinforcement) {

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

		for (int i = 0; i < pointsN.size(); i++) {
			System.out.println("N" + i + ": " + pointsN.get(i));
			System.out.println("M" + i + ": " + pointsM.get(i));

		}
		
		graphCont.setPointsM(pointsM);
		graphCont.setPointsN(pointsN);

	}
	
	public LinkedList<Double> getPointsN() {
		return pointsN;
	}

	public LinkedList<Double> getPointsM() {
		return pointsM;
	}

}
