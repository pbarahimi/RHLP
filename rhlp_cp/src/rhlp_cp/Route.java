package rhlp_cp;

import rhlp_cp.Node;

public class Route {

	private final int i,j,k,m;
	private final int type;
	private final double cost;
//	private int level;
	
	public Route(Node i,Node j,Node k,Node m,double alpha, double[][] distance){
		this.i=i.getIndex();
		this.j=j.getIndex();
		this.k=k.getIndex();
		this.m=m.getIndex();
		this.type=getType(this.i,this.j,this.k,this.m);
		this.cost=getCost(this.i,this.j,this.k,this.m, alpha,distance);
	}
	
	private int getType(int i,int j,int k,int m){
		int type=0;
		
		if (i!=j){
			if (i==k && i==m)
				type=1;
			else if (j==k && j==m)
				type=2;
			else if (i==k && j==m)
				type=3;
			else if (i!=k && j!=k && j==m)
				type=4;
			else if (i!=m && j!=m && i==k)
				type=5;
			else if (k==m && i!=k && j!=k)
				type=6;
			else if (i!=k && i!=m && j!=k && j!=m)
				type=7;
		}		
		return type;		
	}
	
	private double getCost(int i,int j,int k,int m,double alpha, double[][] distance){
		double cost=0;
		if (i!=j){
			cost= distance[i][k]
					+((1-alpha)*distance[k][m]
							+distance[m][j]);
		}
		return cost;
	}
	
	public double getCost(){
		return this.cost;
	}
	
	public double getFailProb(double q){
		double p=q;
		if (this.type==7)
			p=2*q-Math.pow(q, 2);
		return p;			
	}
	
	public int getType(){
		return this.type;
	}
	
	public int getI(){
		return this.i;
	}
	
	public int getJ(){
		return this.j;
	}
	
	public int getK(){
		return this.k;
	}
	
	public int getM(){
		return this.m;
	}
}
