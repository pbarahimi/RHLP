package rhlp_cp;

import java.util.ArrayList;
import java.util.List;

import yen_alg.Graph;
import yen_alg.Path;
import yen_alg.VariableGraph;
import yen_alg.YenTopKShortestPathsAlg;

public class Pair {
	public final Node origin;
	public final Node destination;
	public final double flow;
	private Route shortestPath;
	private List<Route> routes = new ArrayList<Route>();

	public Pair(Node i, Node j, Problem prob) {
		this.origin = i;
		this.destination = j;
		this.flow = prob.flow[origin.getIndex()][destination.getIndex()]
				+ prob.flow[destination.getIndex()][origin.getIndex()];
		this.routes = routes(prob);
		this.shortestPath = this.routes.get(0);
	}

	private Route shortestPath(Problem prob) {
		List<Vertex> vertices = new ArrayList<Vertex>();
		for (Node n : prob.nodes) {
			if (!n.equals(origin) && !n.equals(destination) && n.isHub())
				vertices.add(new Vertex(n));
		}

		List<Vertex> shortestPath = Dijkstra.shortestPath(new Vertex(
				this.origin), new Vertex(this.destination), vertices,
				prob.distance, prob.alpha);

		/** Cast vertices to nodes and return the route. */
		// System.out.println("The shortest path from "+this.origin+" to "+this.destination+" is: "+
		// shortestPath.toString());
		Node i = prob.nodes.get(shortestPath.get(0).index);
		Node j = prob.nodes
				.get(shortestPath.get(shortestPath.size() - 1).index);
		Node k;
		Node m;

		if (shortestPath.size() == 2) {
			if (origin.isHub() && destination.isHub()) {
				k = prob.nodes.get(shortestPath.get(0).index);
				m = prob.nodes.get(shortestPath.get(1).index);
			} else if (origin.isHub() && !destination.isHub()) {
				k = prob.nodes.get(shortestPath.get(0).index);
				m = prob.nodes.get(shortestPath.get(0).index);
			} else {
				k = prob.nodes.get(shortestPath.get(1).index);
				m = prob.nodes.get(shortestPath.get(1).index);
			}
		} else if (shortestPath.size() == 3) {
			if (!origin.isHub() && !destination.isHub()) {
				k = prob.nodes.get(shortestPath.get(1).index);
				m = prob.nodes.get(shortestPath.get(1).index);
			} else if (origin.isHub() && !destination.isHub()) {
				k = prob.nodes.get(shortestPath.get(0).index);
				m = prob.nodes.get(shortestPath.get(1).index);
			} else {
				k = prob.nodes.get(shortestPath.get(1).index);
				m = prob.nodes.get(shortestPath.get(2).index);
			}
		} else if (shortestPath.size() == 4) {
			k = prob.nodes.get(shortestPath.get(1).index);
			m = prob.nodes.get(shortestPath.get(2).index);
		} else {
			System.out
					.println("The shortest path is inconsistent, the route is: "
							+ shortestPath.toString());
			return null;
		}
		Route result = new Route(i, j, k, m, prob.alpha, prob.distance);
		return result;
	}

	public List<Route> routes(Problem prob) {
		List<Route> result = new ArrayList<Route>();
		// Implement the Yen's algorithm here.
		Graph graph = new VariableGraph(this.origin, this.destination, prob);
//		System.out.println("The nodes adjacent to " + graph.getVertex(3) + ": "
//				+ graph.getAdjacentVertices(graph.getVertex(3)).toString());
		YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
		List<Path> shortest_paths_list = yenAlg.getShortestPaths(
				graph.getVertex(this.origin.getIndex()),
				graph.getVertex(this.destination.getIndex()), 5);

		/** Cast vertices to nodes and return the route. */
		// System.out.println("The shortest path from "+this.origin+" to "+this.destination+" is: "+
		// shortestPath.toString());
		for (Path path : shortest_paths_list) {
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
		return result;
	}

	public Route getShortestPath() {
		return this.shortestPath;
	}

	public List<Route> getRoutes() {
		return this.routes;
	}
}