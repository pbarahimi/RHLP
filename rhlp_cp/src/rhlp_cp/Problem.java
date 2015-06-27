package rhlp_cp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yen_alg.Path;

public class Problem {
	public final int nVar;
	public final int nHub;
	public final double q;
	public final double[][] coordinate;
	public final double[][] flow;
	public final double[] fixedCharge;
	public final double[][] distance;
	public final double alpha;
	public final List<Node> nodes;
	public final Set<Pair> pairs;
	public final Set<Node> hubsList;

	/**
	 * Constructor 1
	 * 
	 * @param c
	 *            coordinates filename
	 * @param f
	 *            flows filename
	 * @param fC
	 *            fixed charge filename
	 * @param hubsList
	 * @param alpha
	 *            discount factor
	 * @param q
	 *            failure probability of a node
	 */
	public Problem(String c, String f, String fC, int[] hubsList, double alpha,
			double q) {
		this.q = q;
		this.alpha = alpha;
		this.nodes = new ArrayList<Node>();
		this.hubsList = new HashSet<Node>();
		this.pairs = new HashSet<Pair>();
		this.coordinate = MyArray.read(c);
		this.fixedCharge = MyArray.read2(fC);
		this.distance = Distance.get(coordinate);
		this.nVar = coordinate.length;
		this.flow = new double[nVar][nVar];
		

		/** instantiating nodes */
		for (int i = 0; i < coordinate.length; i++) {
			nodes.add(new Node(i, coordinate[i][0], coordinate[i][1], false));
		}
		
		/** assigning hubs */
		for (int i : hubsList) {
			this.nodes.get(i).setHub();
		}
		
		/** filling  the hubs list*/
		for (int i:hubsList){
			this.hubsList.add(this.nodes.get(i));
		}

		/** instantiating flows matrix */
		double[][] tempFlows = MyArray.read(f);
		for (int i = 0; i < nVar; i++) {
			for (int j = 0; j < nVar; j++) {
				this.flow[i][j] = tempFlows[i][j] + tempFlows[j][i];
			}
		}


		/** setting number of hubs */
		this.nHub = hubsList.length;

		/** instantiating pairs */
		for (Node o : nodes) {
			for (Node d : nodes) {
				if (o.getIndex() > d.getIndex()) {
					this.pairs.add(new Pair(o, d, this));
				}
			}
		}

	}

	/** Constructor 2
	 * 
	 * @param c
	 * @param f
	 * @param fC
	 * @param alpha
	 * @param q
	 */
	public Problem(String c, String f, String fC, double alpha, double q) {

		this.q = q;
		this.alpha = alpha;
		this.nodes = new ArrayList<Node>();
		this.pairs = new HashSet<Pair>();
		this.hubsList = new HashSet<Node>();
		this.coordinate = MyArray.read(c);
		this.fixedCharge = MyArray.read2(fC);
		this.distance = Distance.get(coordinate);
		this.nVar = coordinate.length;
		this.flow = MyArray.read(f);

		/** instantiating flows matrix */
		double[][] tempFlows = MyArray.read(f);
		for (int i = 0; i < nVar; i++) {
			for (int j = 0; j < nVar; j++) {
				this.flow[i][j] = tempFlows[i][j] + tempFlows[j][i];
			}
		}

		/** instantiating nodes */
		for (int i = 0; i < coordinate.length; i++) {
			nodes.add(new Node(i, coordinate[i][0], coordinate[i][1], false));
		}

		/** assigning hubs */
		pHubAssign(nodes);
		
		/** filling the hubs list*/
		for (Node n:this.nodes){
			if (n.isHub())
				this.hubsList.add(n);
		}

		/** setting number of hubs */
		int tempNHub = 0;
		for (Node n : this.nodes) {
			if (n.isHub())
				tempNHub++;
		}
		this.nHub = tempNHub;

		/** instantiating pairs */
		for (Node o : nodes) {
			for (Node d : nodes) {
				if (o.getIndex() > d.getIndex()) {
					this.pairs.add(new Pair(o, d, this));
				}
			}
		}

	}
	
	/** Constructor 3
	 * 
	 * @param other
	 */

	public Problem(Problem other) {
		this.coordinate = other.coordinate;
		this.flow = other.flow;
		this.fixedCharge = other.fixedCharge;
		this.distance = other.distance;
		this.alpha = other.alpha;
		this.nVar = other.nVar;
		this.nHub = other.nHub;
		this.nodes = other.nodes;
		this.pairs = other.pairs;
		this.hubsList = other.hubsList;
		this.q = other.q;
	}

	/** Constructor 4
	 * 
	 * @param nodes
	 */
	public Problem(){
		this.q = 0;
		this.alpha = 0;
		this.nodes = new ArrayList<Node>();
		this.pairs = new HashSet<Pair>();
		this.hubsList = new HashSet<Node>();
		this.coordinate = new double[0][0];
		this.fixedCharge = new double[0];
		this.distance = Distance.get(coordinate);
		this.nVar = coordinate.length;
		this.nHub=0;
		this.flow = new double[nVar][nVar];
	}
	public void pHubAssign(List<Node> nodes) {
		nodes.get(0).setHub();
		nodes.get(1).setHub();
		nodes.get(2).setHub();
		nodes.get(9).setHub();
		// nodes.get(9).setHub();
		// nodes.get(14).setHub();
		// nodes.get(15).setHub();
	}

	/**
	 * Objective function lower bound
	 * 
	 * @return The lower bound of the objective function with the shortest path
	 *         serving as both primary and backup routes.
	 */
	public double objFunLB() {
		double objFun = 0;
		int counter;
		double temp;
		for (Pair pair : this.pairs) {
			temp = 0;
			counter = 0;
			for (int i = 0; i < 1; i++) {
				temp += (1 - pair.getShortestRoute().getFailProb(q))
						* Math.pow(q, counter++);
			}
			temp *= pair.flow * pair.getShortestRoute().getCost(this);
			objFun += temp;
		}
		objFun += this.nHub * this.fixedCharge[0];
		return objFun;
	}

	/**
	 * Objective Function
	 * 
	 * @return
	 */
	public double objFun() {
		double objFun = 0;
		int counter;
		double temp;
		for (Pair pair : this.pairs) {
			temp = 0;
			counter = 0;
			for (Path path: pair.getRoutes(this)) {
				temp += path.getCost(this) * (1 - path.getFailProb(q))
						* Math.pow(q, counter++);
			}
			temp *= pair.flow;
			objFun += temp;
		}

		objFun += this.nHub * this.fixedCharge[0];
		return objFun;
	}

	public void printHubs() {
		String str = new String();
		System.out.print("The hubs are: ");
		for (Node n : this.nodes) {
			if (n.isHub())
				str = str + "," + n;
		}
		str = str.substring(1);
		System.out.println("(" + str + ")");
	}

}
