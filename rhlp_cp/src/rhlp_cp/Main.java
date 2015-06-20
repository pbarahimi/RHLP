package rhlp_cp;

import java.util.ArrayList;
import java.util.List;


public class Main {
	
	public static void main(String[] args){
		Problem prob=new Problem("coordinates.txt", "w.txt", "fixedcharge.txt",0.2);
//		for (int i=0;i<Problem.distance[0].length;i++){
//			for (int j=0;j<Problem.distance.length;j++){
//			System.out.format("%-5f\t",Problem.distance[i][j]);
//			}
//			System.out.println();
//		}
		
//		for (Pair p:prob.pairs){
//			System.out.println(p.origin+" - "+p.destiation+" - "+p.flow);
//		}
		
		for (Node n:prob.nodes){
			System.out.println(n.isHub()+" - "+n.getIndex());
		}
		List<Vertex> hubs=new ArrayList<Vertex>();
		
		for (Node n:prob.nodes){
			if (n.isHub())
				hubs.add(new Vertex(n));
		}
//		Dijkstra.shortestPath(new Vertex(prob.nodes.get(0)), new Vertex(prob.nodes.get(9)), hubs, prob.distance, prob.alpha);
	}
}
