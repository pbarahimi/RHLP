package rhlp_cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem {
	public final int nVar;
	public int nHub;
	public final double q;
	public final double[][] coordinate;
	public final double[][] flow;
	public final double[] fixedCharge;
	public final double[][] distance;
	public final double alpha;
	public final List<Node> nodes;
	public List<Pair> pairs=new ArrayList<Pair>();
	
	public Problem(String c, String f, String fC,double alpha, double q){
		
		this.q=q;
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
		int tempNHub=0;
		for (Node n:this.nodes){
			if (n.isHub())
				tempNHub++;
		}
		this.nHub=tempNHub;
		
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
		this.alpha=other.alpha;
		this.nVar=other.nVar;
		this.nHub=other.nHub;
		this.nodes=other.nodes;
		this.pairs=other.pairs;
		this.q=other.q;
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
		nodes.get(7).setHub();
		nodes.get(9).setHub();
	}

	/** Objective function lower bound
	 * 
	 * @return
	 */
	public double objFunLB(){	
		double objFun=0;
		int counter;
		double temp;
		for (Pair pair:this.pairs){
			temp=0;
			counter=0;
			for (int i=0;i<pair.getRoutes().size();i++){
				temp+=(1-pair.getShortestRoute().getFailProb(q))*Math.pow(q, counter++);
			}
			temp*=pair.flow*pair.getShortestRoute().getCost();
			objFun+=temp;
		}
		objFun+=this.nHub*this.fixedCharge[0];
		return objFun;
	}

	/** Objective Function
	 * 
	 * @return
	 */
	public double objFun(){	
		double objFun=0;
		int counter;
		double temp;
		for (Pair pair:this.pairs){
			temp=0;
			counter=0;
			for (Route route:pair.getRoutes()){
				temp+=route.getCost()*(1-route.getFailProb(q))*Math.pow(q, counter++);
			}
			temp*=pair.flow;
			objFun+=temp;
		}
		
		objFun+=this.nHub*this.fixedCharge[0];
		return objFun;
	}
	
	public void printHubs(){
		String str=new String();
		System.out.print("The hubs are: ");
		for (Node n:this.nodes){
			if (n.isHub())
				str=str+","+n;
		}
		str=str.substring(1);
		System.out.println("("+str+")");
	}
	
}
