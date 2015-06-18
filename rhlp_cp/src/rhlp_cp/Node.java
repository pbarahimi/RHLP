package rhlp_cp;

public class Node implements Comparable<Node>{
	private final int index;
	private final double x;
	private final double y;
	private boolean hub;

	public Node(int index, double x, double y, boolean hub) {
		this.index = index;
		this.x = x;
		this.y = y;
		this.hub=hub;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public boolean isHub(){
		return this.hub;
	}
	
	public void setHub(){
		this.hub=true;
	}

	@Override
	public int compareTo(Node j) {
		return this.index-j.getIndex();		
	}
}
