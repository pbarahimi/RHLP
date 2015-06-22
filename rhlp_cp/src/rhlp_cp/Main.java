package rhlp_cp;

import java.util.ArrayList;
import java.util.List;

import yen_alg.DijkstraShortestPathAlg;
import yen_alg.Graph;
import yen_alg.Path;
import yen_alg.VariableGraph;
import yen_alg.YenTopKShortestPathsAlg;


public class Main {
	
	public static void main(String[] args){
		 long startTime = System.currentTimeMillis();
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
		for (Pair p:prob.pairs){
			System.out.println("The shortest distance from node "+p.origin.getIndex()+" to "+p.destination.getIndex()+" is : "+ p.getShortestPath()+" with type "/*+p.getShortestPath().getCost()*/);
		}
		
//		Graph graph=new VariableGraph("test_5.txt");
/*		Graph graph=new VariableGraph(prob.nodes.get(3), prob.nodes.get(9), prob);
		System.out.println("The nodes adjacent to "+graph.getVertex(3)+": "+graph.getAdjacentVertices(graph.getVertex(3)).toString());
		YenTopKShortestPathsAlg yenAlg=new YenTopKShortestPathsAlg(graph);
		List<Path> shortest_paths_list = yenAlg.getShortestPaths(
                graph.getVertex(3), graph.getVertex(9), 5);
		System.out.println(":"+shortest_paths_list);
		System.out.println(yenAlg.getResultList().size());*/
		
		long stopTime = System.currentTimeMillis();
	      long elapsedTime = stopTime - startTime;
	      System.out.println(elapsedTime);
	      
	      MyArray.print(prob.distance);
	}
}
