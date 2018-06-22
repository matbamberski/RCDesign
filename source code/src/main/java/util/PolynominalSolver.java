package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.exception.NoBracketingException;

public class PolynominalSolver {

	private static ArrayList<Double> solveCubicPolynominalABC(double a, double b, double c) {
		ArrayList<Double> rootsList = new ArrayList<Double>();
		// x^3+Ax^2+Bx+C=0
		//

		double coefficients[] = { c, b, a, 1 };

		PolynomialFunction f = new PolynomialFunction(coefficients);
		LaguerreSolver solver = new LaguerreSolver();

		double min = 0;
		double max = 0.001;
		double result;
		for (double i = 0; i <= 10; i = i + 0.001) {
			try {
				result = solver.solve(100, f, min, max);

				if (rootsList.contains(result) == false) {
					rootsList.add(result);
				}

				System.out.println(result);
			} catch (NoBracketingException ex) {
			}
			min = min + 0.001;
			max = max + 0.001;
		}
		System.out.println("array " + rootsList);
		return rootsList;
	}
	
	private static ArrayList<Double> solveSquaredPolynominalABC(double a, double b, double c) {
		ArrayList<Double> rootsList = new ArrayList<Double>();
		// Ax^2 + Bx + C = 0
		//
		double delta;

		delta = (b * b) - (4 * a * c);

		double x1 = (-b - Math.sqrt(delta)) / (2 * a);
		double x2 = (-b + Math.sqrt(delta)) / (2 * a);
		System.out.println("x1 " + x1);
		System.out.println("x2 " + x2);
		/*
		if (x1 == x2) {
			rootsList.add(x1);
		} else {
			rootsList.add(Math.max(x1, x2));
			rootsList.add(Math.min(x1, x2));
		}
		*/
		rootsList.add(x1);
		rootsList.add(x2);
		return rootsList;
	}

	private static ArrayList<Double> solveCubicPolynominalABCD(double a, double b, double c, double d) {
		ArrayList<Double> rootsList = new ArrayList<Double>();
		// Ax^3+Bx^2+Cx+D=0

		double coefficients[] = { d, c, b, a };

		PolynomialFunction f = new PolynomialFunction(coefficients);
		LaguerreSolver solver = new LaguerreSolver();

		double min = 0;
		double max = 0.001;
		double result;
		for (double i = 0; i <= 20; i = i + 0.001) {

			try {
				result = solver.solve(100, f, min, max);

				if (rootsList.contains(result) == false) {
					rootsList.add(result);
				}
			} catch (NoBracketingException ex) {
			}

			min = min + 0.001;
			max = max + 0.001;

		}
		System.out.println("array " + rootsList);
		return rootsList;
	}

	private static double getMaximumRoot(ArrayList<Double> rootsList) {
		double maxValue = 0;
		try {
			maxValue = Collections.max(rootsList);
			System.out.println("max root : " + maxValue);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return maxValue;
	}

	private static double getMinimumRoot(ArrayList<Double> rootsList) {
		double minValue = 0;
		try {
			minValue = Collections.min(rootsList);
			System.out.println("min root : " + minValue);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return minValue;
	}

	public static double getMaximumRootOfPolynominalABC(double a, double b, double c) {
		return getMaximumRoot(solveCubicPolynominalABC(a, b, c));
	}

	public static double getMaximumRootOfPolynominalABCD(double a, double b, double c, double d) {
		return getMaximumRoot(solveCubicPolynominalABCD(a, b, c, d));
	}

	public static double getMinimumRootOfPolynoiminalABC(double a, double b, double c) {
		return getMinimumRoot(solveCubicPolynominalABC(a, b, c));
	}
	
	public static double getMaximumRootofPolynominalSquare(double a, double b, double c) {
		return getMaximumRoot(solveSquaredPolynominalABC(a, b, c));
	}

}
