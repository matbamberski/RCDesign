package reinforcement.graph;

import java.util.HashMap;
import java.util.LinkedList;

import mainalgorithm.InternalForces;
import mainalgorithm.Reinforcement;
import materials.Concrete;
import materials.DimensionsOfCrossSectionOfConcrete;
import materials.Steel;

public class Graph {

	private Steel steel;
	private DimensionsOfCrossSectionOfConcrete dimensions;
	private InternalForces forces;
	private Concrete concrete;
	private Reinforcement reinforcement;

	public Graph(Steel steel, DimensionsOfCrossSectionOfConcrete dimensions, InternalForces forces, Concrete concrete,
			Reinforcement reinforcement) {
		this.steel = steel;
		this.dimensions = dimensions;
		this.forces = forces;
		this.concrete = concrete;
		this.reinforcement = reinforcement;
	}

	private final double ypsilonCu3 = 0.0035;
	private final double ypsilonC3 = 0.00157;
	private final double LAMBDA = 0.8;
	double sigmaS1 = 0.0;
	double sigmaS2 = 0.0;
	double x = 0.0;
	double n = 0.0;
	double m = 0.0;
	
	double krok = 0.001; // <- dok³adnoœæ wykresu (krok zwiêkszania wartoœci x)

	double fcH = concrete.getFCd() * dimensions.getB() * dimensions.getH();
	double fcX = concrete.getFCd() * dimensions.getB() * LAMBDA;
	double aS1 = 0.0;
	double aS2 = 0.0;

	{
		// uzale¿niæ aS1 i aS2 od tego czy symetryczne czy nie
		if (reinforcement.getDesignedUnsymmetricalAS2() == 0) {
			aS1 = reinforcement.getDesignedSymmetricalAS1();
			aS2 = reinforcement.getDesignedSymmetricalAS2();
		} else {
			aS1 = reinforcement.getDesingedUnsymmetricalAS1();
			aS2 = reinforcement.getDesignedUnsymmetricalAS2();
		}
	}
	double n7 = ypsilonC3*steel.getES()*(aS1+aS2)+fcH;
	
	// sprawdziæ, czy poni¿sze wymiary a2, d, h s¹ w [m]
	private double x_YdMin = (ypsilonCu3 / (ypsilonCu3 + (steel.getFYd() / steel.getES()))) * dimensions.getA2();
	private double xYdMin = (ypsilonCu3 / (ypsilonCu3 - (steel.getFYd() / steel.getES()))) * dimensions.getA2();
	private double xLim = (ypsilonCu3 / (ypsilonCu3 + (steel.getFYd() / steel.getES()))) * dimensions.getD();
	private double x0 = ((ypsilonCu3 - ypsilonC3) / ypsilonCu3) * dimensions.getH();
	private double xYdMax = ((steel.getFYd() * x0)
			- (ypsilonC3 * dimensions.getA2() * steel.getES()) / (steel.getFYd() - (ypsilonC3 * steel.getES())));

	public void plotGhraph(DimensionsOfCrossSectionOfConcrete dimensions, Steel steel, Concrete concrete,
			Reinforcement reinforcement) {
		/*
		 * 0<x<= x-ydmin, Fs1 = fydAs1, Fs2 = -fydAs2, Fc = nfcdblambdax
		 */

		LinkedList<Double> pointsN = new LinkedList<>();
		LinkedList<Double> pointsM = new LinkedList<>();
		
		

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
		
		while (x < (dimensions.getH()/LAMBDA)) { // przedzia³ 5
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
			
			if ((ypsilonC3 * ((x - dimensions.getA2()) / (x - x0)) * steel.getES()) <  steel.getFYd()) {
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

	}

}
