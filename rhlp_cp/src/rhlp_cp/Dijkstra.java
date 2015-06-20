package rhlp_cp;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import rhlp_cp.Node;

class Vertex implements Comparable<Vertex> {
	public final String name;
	public List<Edge> adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	public int index;

	public Vertex(Node n){
		this.name=n.getIndex()+"";
		this.index=n.getIndex();
	}
	
	public Vertex(String argName) {
		name = argName;
	}

	public String toString() {
		return name;
	}

	public int compareTo(Vertex other) {
		return Double.compare(minDistance, other.minDistance);
	}
}

class Edge {
	public final Vertex target;
	public final double weight;

	public Edge(Vertex argTarget, double argWeight) {
		target = argTarget;
		weight = argWeight;
	}
	
	public String toString(){
		return this.target+"";
	}	
}

public class Dijkstra {
	public static void computePaths(Vertex source) {
		source.minDistance = 0;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

			// Visit each edge exiting u
			for (Edge e : u.adjacencies) {
				Vertex v = e.target;
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}

	public static List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}

	public static List<Vertex> shortestPath(Vertex o, Vertex d, List<Vertex> vertices, double[][] distance, double alpha) {
		o.adjacencies=new ArrayList<Edge>();
		d.adjacencies=new ArrayList<Edge>();
		for (Vertex v:vertices){
			o.adjacencies.add(new Edge(v, distance[o.index][v.index]));
//			d.adjacencies.add(new Edge(v, distance[d.index][v.index]));
		}
		
		for (Vertex v1:vertices){
			v1.adjacencies=new ArrayList<Edge>();
			for (Vertex v2:vertices){
				if (!v1.equals(v2)){
					v1.adjacencies.add(new Edge(v2, (1-alpha)*distance[v1.index][v2.index]));
				}
			}
			v1.adjacencies.add(new Edge(d, distance[v1.index][d.index]));
		}
		
		/*
		 * System.out.println("is "+o.index+"adjacent to "+d.index+"? "+o.
		 * adjacencies.contains(d)); computePaths(o);
		 * System.out.println("Distance from " + o.toString()+ " to " +
		 * d.toString() + ": " + d.minDistance);
		 */
		List<Vertex> path = getShortestPathTo(d);
//		System.out.println("The shortest path is: "+path.toString());
		return path;
	}
}