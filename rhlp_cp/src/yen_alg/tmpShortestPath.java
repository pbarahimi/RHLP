package yen_alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class tmpShortestPath {
	private Set<Integer> failedHubs=new HashSet<Integer>();
	private List<Path> shortestPaths=new ArrayList<Path>();
	private boolean hasNext = true;
	
	public void updateFailedHubs(Set<Integer> s){
		failedHubs.addAll(s);
	}
	
	public void updateShortestPaths(List<Path> l){
		shortestPaths.addAll(l);
	}
	
	public void updateHasNext(boolean b){
		this.hasNext=b;
	}
	
	public Set<Integer> getFailedHubs(){
		return this.failedHubs;
	}
	
	public List<Path> getShortestPaths(){
		return this.shortestPaths;
	}
	
	public boolean hasNext(){
		return this.hasNext;
	}

}
