package rhlp_cp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yen_alg.DijkstraShortestPathAlg;
import yen_alg.Graph;
import yen_alg.Path;
import yen_alg.VariableGraph;
import yen_alg.YenTopKShortestPathsAlg;
import yen_alg.tmpShortestPath;

public class Pair {
	public final Node origin;
	public final Node destination;
	public final double flow;
	private Path shortestPath;
	private List<Path> routes = new ArrayList<Path>();

	public Pair(Node i, Node j, Problem prob) {
		this.origin = i;
		this.destination = j;
		this.flow = prob.flow[origin.getIndex()][destination.getIndex()];
		this.shortestPath = shortestPath(prob);
	}

	private Path shortestPath(Problem prob) {
		Graph graph = new VariableGraph(this.origin, this.destination, prob);
		DijkstraShortestPathAlg alg = new DijkstraShortestPathAlg(graph);
		Path path=alg.getShortestPath(graph.getVertex(origin.getIndex()), graph.getVertex(destination.getIndex()));
		
		return path;
/*		*//** Casting path to route*//*
		Node i = prob.nodes.get(path.getVertexList().get(0).getId());
		Node j = prob.nodes.get(path.getVertexList()
				.get(path.getVertexList().size() - 1).getId());
		Node k;
		Node m;

		if (path.getVertexList().size() == 2) {
			if (origin.isHub() && destination.isHub()) {
				k = prob.nodes.get(path.getVertexList().get(0).getId());
				m = prob.nodes.get(path.getVertexList().get(1).getId());
			} else if (origin.isHub() && !destination.isHub()) {
				k = prob.nodes.get(path.getVertexList().get(0).getId());
				m = prob.nodes.get(path.getVertexList().get(0).getId());
			} else {
				k = prob.nodes.get(path.getVertexList().get(1).getId());
				m = prob.nodes.get(path.getVertexList().get(1).getId());
			}
		} else if (path.getVertexList().size() == 3) {
			if (!origin.isHub() && !destination.isHub()) {
				k = prob.nodes.get(path.getVertexList().get(1).getId());
				m = prob.nodes.get(path.getVertexList().get(1).getId());
			} else if (origin.isHub() && !destination.isHub()) {
				k = prob.nodes.get(path.getVertexList().get(0).getId());
				m = prob.nodes.get(path.getVertexList().get(1).getId());
			} else {
				k = prob.nodes.get(path.getVertexList().get(1).getId());
				m = prob.nodes.get(path.getVertexList().get(2).getId());
			}
		} else if (path.getVertexList().size() == 4) {
			k = prob.nodes.get(path.getVertexList().get(1).getId());
			m = prob.nodes.get(path.getVertexList().get(2).getId());
		} else {
			System.out
					.println("The shortest path from "+origin+" to "+destination+" is inconsistent, the route is: "
							+ path.toString());
			return null;
		}
		Route route=new Route(i, j, k, m, prob.alpha, prob.distance);
//		System.out.println("The shortest path of Dijkstra: "+route+" "+route.getCost());
		
		return route;*/
		
	}

	private List<Path> routes(Problem prob) {
//		List<Route> result = new ArrayList<Route>();
		List<Path> shortestPathsList = new ArrayList<Path>();
		Set<Integer> failedHubs=new HashSet<Integer>();		
		boolean hasNext=true;
		
		while (failedHubs.size()<prob.nHub
				&& hasNext){
			Graph graph = new VariableGraph(this.origin, this.destination, prob, failedHubs);
		
	//		System.out.println("The nodes adjacent to " + graph.getVertex(3) + ": "
	//				+ graph.getAdjacentVertices(graph.getVertex(3)).toString());
			YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
			tmpShortestPath tmp_shortest_paths_list = yenAlg.getShortestPaths(
					graph.getVertex(this.origin.getIndex()),
					graph.getVertex(this.destination.getIndex()));
			shortestPathsList.addAll(tmp_shortest_paths_list.getShortestPaths());
			failedHubs.addAll(tmp_shortest_paths_list.getFailedHubs());
			hasNext=tmp_shortest_paths_list.hasNext();			
		}
		return shortestPathsList;
/*
		*//** Cast vertices to nodes and return the route. *//*
		// System.out.println("The shortest path from "+this.origin+" to "+this.destination+" is: "+
		// shortestPath.toString());
		for (Path path : shortestPathsList) {
			Node i = prob.nodes.get(path.getVertexList().get(0).getId());
			Node j = prob.nodes.get(path.getVertexList()
					.get(path.getVertexList().size() - 1).getId());
			Node k;
			Node m;

			if (path.getVertexList().size() == 2) {
				if (origin.isHub() && destination.isHub()) {
					k = prob.nodes.get(path.getVertexList().get(0).getId());
					m = prob.nodes.get(path.getVertexList().get(1).getId());
				} else if (origin.isHub() && !destination.isHub()) {
					k = prob.nodes.get(path.getVertexList().get(0).getId());
					m = prob.nodes.get(path.getVertexList().get(0).getId());
				} else {
					k = prob.nodes.get(path.getVertexList().get(1).getId());
					m = prob.nodes.get(path.getVertexList().get(1).getId());
				}
			} else if (path.getVertexList().size() == 3) {
				if (!origin.isHub() && !destination.isHub()) {
					k = prob.nodes.get(path.getVertexList().get(1).getId());
					m = prob.nodes.get(path.getVertexList().get(1).getId());
				} else if (origin.isHub() && !destination.isHub()) {
					k = prob.nodes.get(path.getVertexList().get(0).getId());
					m = prob.nodes.get(path.getVertexList().get(1).getId());
				} else {
					k = prob.nodes.get(path.getVertexList().get(1).getId());
					m = prob.nodes.get(path.getVertexList().get(2).getId());
				}
			} else if (path.getVertexList().size() == 4) {
				k = prob.nodes.get(path.getVertexList().get(1).getId());
				m = prob.nodes.get(path.getVertexList().get(2).getId());
			} else {
				System.out
						.println("The shortest path from "+origin+" to "+destination+" is inconsistent, the route is: "
								+ path.toString());
				return null;
			}
			Route route=new Route(i, j, k, m, prob.alpha, prob.distance);
			result.add(route);
			System.out
			.println("The shortest path from "+origin+" to "+destination+" is: "
					+ route.toString()+" and the cost is: "+ route.getCost());
		}
		return result;*/
	}

	public Path getShortestRoute() {
		return this.shortestPath;
	}

	public List<Path> getRoutes(Problem prob) {
		this.routes=routes(prob);
		return this.routes;
	}
	
	public String toString(){
		String str=new String();
		str="["+this.origin.getIndex()+","+this.destination.getIndex()+"]";
		return str;
	}

}