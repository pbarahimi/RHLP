package rhlp_cp;

import java.util.ArrayList;
import java.util.List;

public class Problem {
	public final int nVar;
	public final int nHub=5;
	public final double q=0.05;
	public final double[][] coordinate;
	public final double[][] flow;
	public final double[] fixedCharge;
	public final double[][] distance;
	public final double alpha;
	public final List<Node> nodes;
	public List<Pair> pairs=new ArrayList<Pair>();
	
	public Problem(String c, String f, String fC,double alpha){
		
		this.alpha=alpha;
		this.nodes=new ArrayList<Node>(); 
		this.coordinate=MyArray.read(c);
		this.flow=MyArray.read(f);
		this.fixedCharge=MyArray.read2(fC);
		this.distance=Distance.get(coordinate);
		this.nVar=coordinate.length;
		
		for (int i=0;i<coordinate.length;i++){
			nodes.add(new Node(i, coordinate[i][0], coordinate[i][1], false));
		}
		
		rndPhubAssign(nodes);
		
		for (Node o:nodes){
			for (Node d:nodes){
				if (o.getIndex()>d.getIndex()){
				this.pairs.add(new Pair(o,d,this));
				}
			}
		}
	}
	
	public Problem(Problem other){
		this.coordinate=other.coordinate;
		this.flow=other.flow;
		this.fixedCharge=other.fixedCharge;
		this.distance=other.distance;
		this.alpha=0.2;
		this.nVar=other.nVar;
		this.nodes=other.nodes;
		this.pairs=other.pairs;
	}

	public void rndPhubAssign(List<Node> nodes){
//		Collections.shuffle(nodes);
//		
//		for (int i=0;i<nHub;i++){
//			nodes.get(i).setHub();
//		}
//		
//		Collections.sort(nodes);
		nodes.get(1).setHub();
		nodes.get(3).setHub();
		nodes.get(5).setHub();
	}

	// Objective function lower bound
	public double objFunLB(){	
		double objFun=0;
		int counter;
		
		for (Pair p:pairs){
			counter=0;
			for (Route r:p.getRoutes()){
			objFun+=(1-r.getFailProb(q))*Math.pow(q, counter++);
			}
			objFun*=p.flow*p.getShortestPath().getCost();
			objFun+=this.nHub*this.fixedCharge[0];
		}
		return objFun;
	}
	
	// Objective function

	// Objective Function
	public double objFun(){	
		double objFun=0;
		int counter;
		for (Pair p:pairs){
			counter=0;
			for (Route r:p.getRoutes()){
				objFun=r.getCost()*(1-r.getFailProb(q))*Math.pow(q, counter++);
			}
			objFun*=p.flow;
		}
		objFun+=this.nHub*this.fixedCharge[0];
		return objFun;
	}
}
