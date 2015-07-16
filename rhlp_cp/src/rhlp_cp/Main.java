package rhlp_cp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.math3.util.ArithmeticUtils;

import yen_alg.BaseVertex;
import yen_alg.Graph;
import yen_alg.Path;

public class Main {
	private static double bestObjFun = Double.POSITIVE_INFINITY;
	private static Problem optimalSetting=new Problem();
	private static PrintWriter out;
	
	public static void main(String[] args) throws FileNotFoundException {
		Main.out = new PrintWriter(new File("Results.txt"));
		Main.out.println("Hubs\t\t\tLower bound1\t\tLower bound2\t\tCurrent_obj_fun\t\tBest_obj_fun");
		Scanner sc = new Scanner(System.in);

		long startTime = System.currentTimeMillis();
		ProblemGenerator pg = new ProblemGenerator(25, 5);

		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		out.append("elapsed time: " + elapsedTime + "ms");
		System.out.println("elapsed time: " + elapsedTime + "ms");
		System.out.println("optimal obj fun: "+bestObjFun+"\n");
		optimalSetting.printHubs();
//		MyArray.print(Distance.get(MyArray.read("coordinates.txt")));
		sc.close();
		out.close();
	
		
		/*int[] results = new int[prob.fixedCharge.length];
		
		for (Pair pair:prob.pairs){
			Problem tmpProb = new Problem("coordinates.txt", "w.txt",
					"fixedcharge.txt", 0.2, 0.05, pair);
			Pair tmppair = new Pair(tmpProb.nodes.get(pair.origin.getIndex()), tmpProb.nodes.get(pair.destination.getIndex()), tmpProb);
			List<Path> pathList = tmppair.routes2(tmpProb);
			if (pair.flow==0)
				System.out.println("\nThere's no flow from "+pair.origin+" to "+pair.destination);
			else{
				System.out.println("\nShortest paths from "+pair.origin+" to "+pair.destination+":");
				System.out.println(pathList);
				for (Path path: pathList){
				path.getVertexList().remove(0);
				path.getVertexList().remove(path.getVertexList().size()-1);
				for (BaseVertex bv:path.getVertexList()){
					results[bv.getId()]+=tmppair.flow;
				}
			}
			}
			
			
		}
		MyArray.print(prob.distance);
		for (int i:results){
			System.out.println(i);
		}*/
	}
	
	public static void run(HubsList tempHubsList){
		Problem prob = new Problem("distances.txt", "w.txt",
				"fixedcharge.txt", tempHubsList.getList(), 0.2, 0.05);
/*		
		double hubsDistances=0;
		for (int i=0; i<tempHubsList.getList().length ;i++){
			for (int j=i+1;j<tempHubsList.getList().length;j++){
				hubsDistances+=prob.distance[tempHubsList.getList()[i]][tempHubsList.getList()[j]];
				System.out.println("from "+tempHubsList.getList()[i]+" to "+tempHubsList.getList()[j]+": "+prob.distance[tempHubsList.getList()[i]][tempHubsList.getList()[j]]);
			}
		}
		
		System.out.println("Total hubs distances for "+tempHubsList+" is: "+hubsDistances+"\n");*/

		double lB = prob.objFunLB();
		if (lB < bestObjFun) {
			double objFun = prob.objFun();
			if (objFun < bestObjFun) {
				Main.optimalSetting = prob;
				Main.bestObjFun = objFun;
				Main.out.printf("%-15s" + "\t\t%-8.3f" + "\t\t%-15s" + "\t\t"
						+ "%-8.3f" + "\t\t" + "%-8.3f" + "\n", tempHubsList, lB, "-----",
						objFun, bestObjFun);
			}
		} else {
			Main.out.printf("%-15s" + "\t\t%-8.3f"+ "\t\t%-15s" + "\t\tNaN\t\t\t\t"
					+ "%-8.3f" + "\n", tempHubsList,  lB, "-----", bestObjFun);
		}
	}
}
