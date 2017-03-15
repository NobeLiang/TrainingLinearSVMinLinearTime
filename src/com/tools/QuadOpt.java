package com.tools;

import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.functions.PDQuadraticMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;

public class QuadOpt {
	public static double[] solveQuadraticProgramming(double[][] Q, double[] P, double c) throws Exception {
		PDQuadraticMultivariateRealFunction objFunction = 
				new PDQuadraticMultivariateRealFunction(Q, P, 0);
		int xn = P.length;
		ConvexMultivariateRealFunction[] inequalities = new ConvexMultivariateRealFunction[xn + 1];
		double[] i0 = new double[xn];
		for(int j = 0; j < i0.length; j++) {
			i0[j] = 1;
		}
		inequalities[0] = new LinearMultivariateRealFunction(i0, -c);
		for(int i = 1; i < inequalities.length; i++) {
			double[] t = new double[xn];
			t[i-1] = -1;
			inequalities[i] = new LinearMultivariateRealFunction(t, 0);
		}
		
		OptimizationRequest or = new OptimizationRequest();
		or.setF0(objFunction);
		or.setFi(inequalities);
		or.setTolerance(1.E-5);
		or.setToleranceFeas(1.E-5);
		
		JOptimizer opt = new JOptimizer();
		opt.setOptimizationRequest(or);
		int returnCode = opt.optimize();
		
		double[] result = opt.getOptimizationResponse().getSolution();
		return result;
	}
}
