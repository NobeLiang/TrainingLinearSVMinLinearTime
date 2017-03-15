package com.SVMs;

import java.util.ArrayList;

import com.tools.Avector;
import com.tools.QuadOpt;

public class StructuralSVMs {
	private double[][] trainSample;
	private double[] trainLabel;
	private double C;
	private double e;
	private ArrayList<double[]> workSet;
	private double[] w;
	
	public StructuralSVMs(double[][] trainSample, double[] trainLabel, double C, double e) {
		this.trainSample = trainSample;
		this.trainLabel = trainLabel;
		this.C = C;
		this.e = e;
		
		workSet = new ArrayList<double[]>();
		
		if(trainSample != null) {
			if(trainSample[0] != null) {
				this.w = new double[trainSample[0].length];
			}
		}
	}
	
	public void train() throws Exception {
		boolean flag = true;
		double eps = 0;
		int counter = 0;
		while(flag) {
			double[] c = getViolets(this.w);
			flag = false;
			for(int i = 0; i < c.length; i++) {
				if(c[i] > 0) {
					flag = true;
					break;
				}
			}
			
			counter = counter + 1;
			if(counter > 200) {
				break;
			}
			workSet.add(c);
			int n = this.trainSample.length;
			double lastEps = getError(c);
			
			double[][] Q = getQ(workSet);
			double[] P = getP(workSet);
			double[] result = QuadOpt.solveQuadraticProgramming(Q, P, this.C);
			this.w = updateW(result, workSet);
			
			double currentEps = getEps(workSet);
System.out.println("lastEps = " + lastEps);
System.out.println("currentEps = " + currentEps);
			if(lastEps < eps + this.e) {
				break;
			}
			eps = currentEps;
System.out.println("maxEps = " + eps);
			for(int k = 0; k < w.length; k++) {
				System.out.print(w[k] + " ");
			}
			System.out.println();
			System.out.println("=========================================");
		}
	}
	
	public double getError(double[] c) {
		double addc = 0;
		double[] t = new double[this.trainSample[0].length];
		for(int j = 0; j < c.length; j++) {
			addc += c[j];
			double cy = c[j] * this.trainLabel[j];
			t = Avector.vecAddVec(t, Avector.scaleProdVec(cy, this.trainSample[j]));
		}
		double eps = (1.0 / this.trainLabel.length) * (addc - Avector.vecProdVec(this.w, t));
		return eps;
	}
	
	public double getEps(ArrayList<double[]> ws) {
		int s = ws.size();
		double[] tempC;
		double[] esp = new double[s];
		for(int i = 0; i < s; i++) {
			tempC = ws.get(i);
			esp[i] = getError(tempC);
		}
		
		double maxeps = -Double.MAX_VALUE;
		for(int k = 0; k < esp.length; k++) {
			if(esp[k] > maxeps) {
				maxeps = esp[k];
			}
		}
		return maxeps;
	}
	
	public double[] updateW(double[] alpha, ArrayList<double[]> ws) {
		int n = this.trainSample.length;
		double[] tempw = new double[this.w.length];
		double[] t;
		for(int i = 0; i < ws.size(); i++) {
			t = ws.get(i);
			double[] tt = new double[this.w.length];
			for(int j = 0; j < n; j++) {
				double cy = t[j] * this.trainLabel[j];
				tt = Avector.vecAddVec(tt, Avector.scaleProdVec(cy, this.trainSample[j]));
			}
			double coff = alpha[i] / n;
			tempw = Avector.vecAddVec(tempw, Avector.scaleProdVec(coff, tt));
		}
		return tempw;
	}
	
	public double[] getP(ArrayList<double[]> ws) {
		int s = ws.size();
		double[] p = new double[s];
		int n = this.trainSample.length;
		for(int i = 0; i < s; i++) {
			double[] tv = ws.get(i);
			double counter = 0;
			for(int j = 0; j < n; j++) {
				if(tv[j] > 0) {
					counter++;
				}
			}
			p[i] = -(counter / n);
		}
		return p;
	}
	
	public double[][] getQ(ArrayList<double[]> ws) {
		int s = ws.size();
		double[][] q = new double[s][s];
		double[] tempC;
		
		int cols = this.trainSample[0].length;
		double[][] xc = new double[s][cols];
		
		double[] tempVec;
		double[] t;
		
		for(int i = 0; i < s; i++) {
			tempVec = ws.get(i);
			t = new double[cols];
			for(int j = 0; j < tempVec.length; j++) {
				if(tempVec[j] > 0) {
					double cy = tempVec[j] * this.trainLabel[j];
					t = Avector.vecAddVec(t, Avector.scaleProdVec(cy, this.trainSample[j]));
				}
			}
			
			int n = this.trainSample.length;
			double divn = 1.0 / n;
			t = Avector.scaleProdVec(divn, t);
			xc[i] = t;
		}
		
		for(int i = 0; i < s; i++) {
			double[] v1 = xc[i];
			for(int j = 0; j < s; j++) {
				double[] v2 = xc[j];
				double r = Avector.vecProdVec(v1, v2);
				q[i][j] = r;
			}
		}
		
		return q;
	}
	
	private double[] getViolets(double[] weight) {
		int n = this.trainSample.length;
		double[] c = new double[n];
		
		double label;
		double[] tempVec;
		double tempResult;
		for(int i = 0; i < n; i++) {
			tempVec = this.trainSample[i];
			label = this.trainLabel[i];
			tempResult = label * Avector.vecProdVec(weight, tempVec);
			
			if(tempResult < 1) {
				c[i] = 1;
			} else {
				c[i] = 0;
			}
		}
		return c;
	}
}
