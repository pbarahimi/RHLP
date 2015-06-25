package rhlp_cp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import yen_alg.BaseVertex;
import yen_alg.DijkstraShortestPathAlg;
import yen_alg.Graph;
import yen_alg.Path;
import yen_alg.VariableGraph;
import yen_alg.YenTopKShortestPathsAlg;


public class Main {
	
	public static void main(String[] args){
		 long startTime = System.currentTimeMillis();
		 
		/* try {
			PrintWriter out=new PrintWriter(new File("Results.txt"));
			out.println("Hubs\t\tLower bound\t\tBest objective function"); 
			 Problem optimalSetting;
			 double bestObjFun=Double.POSITIVE_INFINITY;
			 ProblemGenerator pg=new ProblemGenerator(10,4);
			 pg.printProblems();
			 Scanner sc=new Scanner(System.in);
			 sc.nextLine();
			 
			 
			 for (HubsList l:pg.getProblems()){
				 Problem prob=new Problem("coordinates.txt", "w.txt", "fixedcharge.txt", l.getList(),0.2, 0.05);
				 double lB = prob.objFunLB();
				 if (lB<bestObjFun){
					 double objFun=prob.objFun();
					 if (objFun<bestObjFun){
						 optimalSetting=prob;
						 bestObjFun=objFun;
					 }
				 }
				 out.append(l+"\t\t"+lB+"\t\t"+bestObjFun+"\n");
				 
			 }
			 sc.close();
			 out.close();
		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		Problem prob=new Problem("coordinates.txt", "w.txt", "fixedcharge.txt",0.2,0.05);
//		for (int i=0;i<Problem.distance[0].length;i++){
//			for (int j=0;j<Problem.distance.length;j++){
//			System.out.format("%-5f\t",Problem.distance[i][j]);
//			}
//			System.out.println();
//		}
		
//		for (Pair p:prob.pairs){
//			System.out.println(p.origin+" - "+p.destiation+" - "+p.flow);
//		}
		
		/*for (Node n:prob.nodes){
			System.out.println(n.isHub()+" - "+n.getIndex());
		}
		List<Vertex> hubs=new ArrayList<Vertex>();
		
		for (Node n:prob.nodes){
			if (n.isHub())
				hubs.add(new Vertex(n));
		}*/
//		Dijkstra.shortestPath(new Vertex(prob.nodes.get(0)), new Vertex(prob.nodes.get(9)), hubs, prob.distance, prob.alpha);
		
		/*for (Pair p:prob.pairs){	
			if (p.getShortestPath()==null){
				System.out.println("The shortest path for "+p.origin.getIndex()+" to "+p.destination.getIndex()+" is: null");
			}
			else
				System.out.println("The shortest path for "+p.origin.getIndex()+" to "+p.destination.getIndex()+" is: "+p.getShortestPath());
		}*/
		/*for (Pair p:prob.pairs){
			System.out.println("The shortest distance from node "+p.origin.getIndex()+" to "+p.destination.getIndex()+" is : "+ p.getShortestPath()+" with type "+p.getShortestPath().getCost());
		}*/
		
//		Graph graph=new VariableGraph("test_5.txt");
		/*
		for (BaseVertex bv:graph.getVertexList()){
			System.out.println("The nodes adjacent to "+bv+": "+graph.getAdjacentVertices(bv).toString());
		}
		YenTopKShortestPathsAlg yenAlg=new YenTopKShortestPathsAlg(graph);
		List<Path> shortest_paths_list = yenAlg.getShortestPaths(
                graph.getVertex(0), graph.getVertex(6), 5);
		System.out.println(":"+shortest_paths_list);
		System.out.println(yenAlg.getResultList().size());*/
		/*Graph graph=new VariableGraph(prob.nodes.get(13), prob.nodes.get(19), prob);
		System.out.println(graph.getAdjacentVertices(graph.getVertexList().get(1)));
		prob.pairs.get(prob.pairs.size()-1).getRoutes(prob);*/
		double temp=prob.objFunLB();
		double temp2=prob.objFun();
	
		System.out.println("the obj fun: "+temp2);
		System.out.println("the lower bound: "+temp);
		
//		pg.printProblems();
		
		long stopTime = System.currentTimeMillis();
	      long elapsedTime = stopTime - startTime;
	      System.out.println("elapsed time: "+elapsedTime+"ms");
//		System.out.println("The lower bound: "+prob.objFunLB());
//		System.out.println("The objective function value: "+prob.objFun());
//	      prob.printHubs();
	      
//	      for (Pair p:prob.pairs){
//	    	  System.out.println("the shortest route from "+p.origin+" to "+p.destination+": "+p.getShortestRoute());
//	      }
	}
}
