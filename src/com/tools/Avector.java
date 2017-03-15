package com.tools;

public class Avector {

	public static double[] scaleProdVec(double s, double[] v) {
		double[] result;
		if(v != null) {
			result = new double[v.length];
			for(int i = 0; i < v.length; i++) {
				result[i] = s * v[i];
			}
			return result;
		} else {
			return null;
		}
	}
	
	public static double vecProdVec(double[] v, double[] s) {
		double result = 0;
		if(v != null && s != null) {
			int vl = v.length;
			int sl = s.length;
			if(vl == sl) {
				for(int i = 0; i < vl; i++) {
					result += v[i] * s[i];
				}
				return result;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public static double[][] addtionCols(double[][] data, double a) {
		double[][] result;
		if(data != null) {
			if(data[0] != null) {
				int row = data.length;
				int col = data[0].length;
				result = new double[row][col + 1];
				for(int i = 0; i < row; i++) {
					int j;
					for(j = 0; j < col; j++) {
						result[i][j] = data[i][j];
					}
					result[i][j] = a;
				}
				
				return result;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static double[] vecAddVec(double[] v, double[] s) {
		if(v == null || s == null) {
			return null;
		}
		
		int vl = v.length;
		int sl = s.length;
		if(vl != sl) {
			return null;
		}
		
		double[] result = new double[vl];
		for(int i = 0; i < vl; i++) {
			result[i] = v[i] + s[i];
		}
		return result;
	}
	
	public static double[] intToDouble(int[] v) {
		double[] result = new double[v.length];
		for(int i = 0; i < v.length; i++) {
			result[i] = (double)v[i];
		}
		return result;
	}
}
