package rhlp_cp;

import java.util.ArrayList;
import java.util.List;

public class Pair {
	public final int origin;
	public final int destination;
	public final double flow;
	public List<Route> routes=new ArrayList<Route>();
	
	public Pair(Node i, Node j, double[][] flows){
		this.origin=i.getIndex();
		this.destination=j.getIndex();
		this.flow=flows[origin][destination]+flows[destination][origin];
	}

}
