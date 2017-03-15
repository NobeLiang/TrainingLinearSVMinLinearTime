package com.test;

import com.SVMs.StructuralSVMs;
import com.tools.Avector;
import com.tools.LoadData;

public class Test {
	public static void main(String[] args) throws Exception{
		LoadData loader = new LoadData();
		String filename = "F:\\Java‘¥≥Ã–Ú\\SVMSMO\\circle1.txt";
//		String filename = "twocircle.txt";
//		String filename = "D:\\Course\\AI\\Experiment\\train\\scaleData01.txt";
		double[][] data = loader.readFile(filename);
		double[][] trainSample = loader.getAttribute(data);
		int[] itrainLabel = loader.getLabel(data);
		double[] trainLabel = Avector.intToDouble(itrainLabel);
		trainSample = Avector.addtionCols(trainSample, 1);
		
		StructuralSVMs ss = new StructuralSVMs(trainSample, trainLabel, 10000, 0.001);
		long start = System.currentTimeMillis();
		ss.train();
		long end = System.currentTimeMillis();
		System.out.println("time cost = " + (end - start));
	}
}
