package rhlp_cp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yen_alg.BaseVertex;
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
		if (prob.flow[this.origin.getIndex()][this.destination.getIndex()]==0){
			BaseVertex o=graph.getVertex(origin.getIndex());
			BaseVertex d=graph.getVertex(destination.getIndex());
			List<BaseVertex> tmp=new ArrayList<BaseVertex>();
			tmp.add(o); tmp.add( d);
			return new Path(tmp, 0);
		}else{
			DijkstraShortestPathAlg alg = new DijkstraShortestPathAlg(graph);
			Path path=alg.getShortestPath(graph.getVertex(origin.getIndex()), graph.getVertex(destination.getIndex()));
			return path;
		}
	}

	private List<Path> routes(Problem prob) {
		List<Path> shortestPathsList = new ArrayList<Path>();
		Graph graph = new VariableGraph(this.origin, this.destination, prob);
		if (prob.flow[this.origin.getIndex()][this.destination.getIndex()]==0){
			
			BaseVertex o=graph.getVertex(origin.getIndex());
			BaseVertex d=graph.getVertex(destination.getIndex());
			List<BaseVertex> tmp=new ArrayList<BaseVertex>();
			tmp.add(o); tmp.add( d);
			Path shortestPath=new Path(tmp, 0);
			shortestPathsList.add(shortestPath);
			return shortestPathsList;
		}else{			
			Set<Integer> failedHubs=new HashSet<Integer>();		
			boolean hasNext=true;
			
			while (failedHubs.size()<prob.nHub
					&& hasNext){
				graph = new VariableGraph(this.origin, this.destination, prob, failedHubs);
			
				YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
				tmpShortestPath tmp_shortest_paths_list = yenAlg.getShortestPaths(
						graph.getVertex(this.origin.getIndex()),
						graph.getVertex(this.destination.getIndex()));
				shortestPathsList.addAll(tmp_shortest_paths_list.getShortestPaths());
				failedHubs.addAll(tmp_shortest_paths_list.getFailedHubs());
				hasNext=tmp_shortest_paths_list.hasNext();			
			}
			return shortestPathsList;
		}
	}
	
	public List<Path> routes2(Problem prob) {
		List<Path> shortestPathsList = new ArrayList<Path>();
		Graph graph = new VariableGraph(this.origin, this.destination, prob);
		if (prob.flow[this.origin.getIndex()][this.destination.getIndex()]==0){
			
			BaseVertex o=graph.getVertex(origin.getIndex());
			BaseVertex d=graph.getVertex(destination.getIndex());
			List<BaseVertex> tmp=new ArrayList<BaseVertex>();
			tmp.add(o); tmp.add( d);
			Path shortestPath=new Path(tmp, 0);
			shortestPathsList.add(shortestPath);
			return shortestPathsList;
		}else{			
			Set<Integer> failedHubs=new HashSet<Integer>();		
			boolean hasNext=true;
			int counter=0;
			
			while (failedHubs.size()<prob.nHub
					&& hasNext && counter++<5){
				graph = new VariableGraph(this.origin, this.destination, prob, failedHubs);
			
				YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
				tmpShortestPath tmp_shortest_paths_list = yenAlg.getShortestPaths(
						graph.getVertex(this.origin.getIndex()),
						graph.getVertex(this.destination.getIndex()));
				shortestPathsList.addAll(tmp_shortest_paths_list.getShortestPaths());
				failedHubs.addAll(tmp_shortest_paths_list.getFailedHubs());
				hasNext=tmp_shortest_paths_list.hasNext();			
			}
			return shortestPathsList;
		}
	}

	public Path getShortestRoute() {
		return this.shortestPath;
	}

	public List<Path> getRoutes(Problem prob) {
		this.routes=routes(prob);
		return this.routes;
	}
	
	public double getDirectDistance (double[][] distance, double alpha){
		double result = distance[this.origin.getIndex()][this.destination.getIndex()]*(1-alpha);		
		return result;
	}
	
	public String toString(){
		String str=new String();
		str="["+this.origin.getIndex()+","+this.destination.getIndex()+"]";
		return str;
	}

}