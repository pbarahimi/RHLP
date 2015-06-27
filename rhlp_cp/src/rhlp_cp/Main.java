package rhlp_cp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.math3.util.ArithmeticUtils;

public class Main {
	private static double bestObjFun = Double.POSITIVE_INFINITY;
	private static Problem optimalSetting=new Problem();
	private static PrintWriter out;
	
	public static void main(String[] args) throws FileNotFoundException {
		Main.out = new PrintWriter(new File("Results.txt"));
		Main.out.println("Hubs\t\t\tLower bound\t\tCurrent_obj_fun\t\tBest_obj_fun");
		Scanner sc = new Scanner(System.in);

		long startTime = System.currentTimeMillis();
		ProblemGenerator pg = new ProblemGenerator(20, 5);

		/*for (HubsList l : pg.getProblems()) {
			Problem prob = new Problem("coordinates.txt", "w.txt",
					"fixedcharge.txt", l.getList(), 0.2, 0.05);

			double lB = prob.objFunLB();
			if (lB < Main.bestObjFun) {
				double objFun = prob.objFun();
				if (objFun < bestObjFun) {
					Main.optimalSetting = prob;
					Main.bestObjFun = objFun;
					Main.out.printf("%-15s" + "\t\t" + "%-8.3f" + "\t\t"
							+ "%-8.3f" + "\t\t" + "%-8.3f" + "\n", l, lB,
							objFun, bestObjFun);
				}
			} else {
				out.printf("%-15s" + "\t\t" + "%-8.3f" + "\t\tNaN\t\t\t\t"
						+ "%-8.3f" + "\n", l, lB, bestObjFun);
			}
		}*/
		optimalSetting.printHubs();
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		out.append("elapsed time: " + elapsedTime + "ms");
		System.out.println("elapsed time: " + elapsedTime + "ms");
		System.out.println("optimal obj fun: "+bestObjFun);
		sc.close();
		out.close();

		
		 /*Problem prob = new Problem("coordinates.txt", "w.txt", "fixedcharge.txt", 0.2, 0.05); 
		 double lB = prob.objFunLB();
		 double objFun = prob.objFun();
		 System.out.println("LB: "+lB+"\tobjective function: "+objFun);
		 for (Pair p:prob.pairs){
			 System.out.println(p.getRoutes(prob));
		 }
		 MyArray.print(prob.distance);

		 int[] hubs={0,1,2,9};
		 int o=3;
		 int d=6;
		
		 
		 for (int i:hubs){
			 for (int j:hubs){
					 double cost=prob.distance[o][i]+(1-prob.alpha)*prob.distance[i][j]+prob.distance[j][d];
					 System.out.println(o+"-"+i+"-"+j+"-"+d+" : "+ cost);
			 }
		 }*/
	/*	System.out.println(ArithmeticUtils.binomialCoefficientDouble(40, 10));
		ProblemGenerator pg = new ProblemGenerator(40, 10);
		pg.printProblems();*/
	}
	
	public static void run(HubsList tempHubsList){
		Problem prob = new Problem("coordinates.txt", "w.txt",
				"fixedcharge.txt", tempHubsList.getList(), 0.2, 0.05);

		double lB = prob.objFunLB();
		if (lB < bestObjFun) {
			double objFun = prob.objFun();
			if (objFun < bestObjFun) {
				Main.optimalSetting = prob;
				Main.bestObjFun = objFun;
				Main.out.printf("%-15s" + "\t\t" + "%-8.3f" + "\t\t"
						+ "%-8.3f" + "\t\t" + "%-8.3f" + "\n", tempHubsList, lB,
						objFun, bestObjFun);
			}
		} else {
			Main.out.printf("%-15s" + "\t\t" + "%-8.3f" + "\t\tNaN\t\t\t\t"
					+ "%-8.3f" + "\n", tempHubsList, lB, bestObjFun);
		}
	}
}
