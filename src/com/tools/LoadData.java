package com.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadData {
	
	public double[][] readFile(String filename) throws IOException {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(new FileInputStream(filename)));
		String line = null;
		ArrayList<double[]> container = new ArrayList<double[]>();
		while((line = in.readLine()) != null) {
			String[] strs = line.split(" ");
			double[] temp = new double[strs.length];
			for(int i = 0; i < temp.length; i++) {
				temp[i] = Double.parseDouble(strs[i]);
			}
			container.add(temp);
		}
		in.close();
		
		double[][] result = new double[container.size()][container.get(0).length];
		for(int i = 0; i < result.length; i++) {
			double[] t = container.get(i);
			for(int j = 0; j < result[0].length; j++) {
				result[i][j] = t[j];
			}
		}
		return result;
	}

	public double[][] getAttribute(double[][] data) {
		if(data != null) {
			if(data[0] != null) {
				int row = data.length;
				int colum = data[0].length;
				
				double[][] result = new double[row][colum - 1];
				for(int i = 0; i < row; i++) {
					for(int j = 0; j < colum - 1; j++) {
						result[i][j] = data[i][j];
					}
				}
				return result;
			}
		}
		return null;
	}

	public int[] getLabel(double[][] data) {
		int[] result = new int[data.length];
		int colum = data[0].length;
		int index = colum - 1;
		for(int i = 0; i < result.length; i++) {
			result[i] = (int)data[i][index];
		}
		return result;
	}
}
