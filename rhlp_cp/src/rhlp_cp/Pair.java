package rhlp_cp;

import java.util.ArrayList;
import java.util.List;

public class Pair {
	public final Node origin;
	public final Node destination;
	public final double flow;
	private final Route shortestPath;
	private List<Route> routes=new ArrayList<Route>();
	
	public Pair(Node i, Node j, Problem prob){
		this.origin=i;
		this.destination=j;
		this.flow=prob.flow[origin.getIndex()][destination.getIndex()]+prob.flow[destination.getIndex()][origin.getIndex()];
		this.shortestPath=shortestPath(prob);
	}
	
	public Route shortestPath(Problem prob){
		List<Vertex> vertices=new ArrayList<Vertex>();
		for (Node n:prob.nodes){
			if (!n.equals(origin) && !n.equals(destination))
			vertices.add(new Vertex(n));
		}
		System.out.println("The Size of the vertices matrix is: "+ vertices.size());
		List<Vertex> shortestPath=Dijkstra.shortestPath(new Vertex(this.origin), new Vertex(this.destination), vertices, prob.distance, prob.alpha);
		
		// Cast vertices to nodes and return the route.
		if (shortestPath.size()==3 || shortestPath.size()==4){
			Node i=prob.nodes.get(shortestPath.get(0).index);
			Node k=prob.nodes.get(shortestPath.get(1).index);
			Node m;
			if (shortestPath.size()==3)
				m=prob.nodes.get(shortestPath.get(1).index);
			else
				m=prob.nodes.get(shortestPath.get(2).index);
			Node j=prob.nodes.get(shortestPath.get(shortestPath.size()-1).index);
			Route result=new Route(i, j, k, m, prob.alpha, prob.distance);
			return result;
		} else{
			System.out.println("The shortest path is inconsistent, the route is: "+
								shortestPath.toString());
			return null;
		}
	}

	public List<Route> routes(){
		List<Route> result=new ArrayList<Route>();
		//Implement the Yen's algorithm here.
		return result;
	}
	
	public Route getShortestPath(){
		return this.shortestPath;
	}
	
	public List<Route> getRoutes(){
		return this.routes;
	}
}