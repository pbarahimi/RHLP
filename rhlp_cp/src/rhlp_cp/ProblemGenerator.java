package rhlp_cp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProblemGenerator {
	private List<HubsList> problems;
	private int nNode;
	private  int nHub;
	
	public ProblemGenerator(int nNode, int nHub){
		this.nHub=nHub;
		this.nNode=nNode;
		this.problems=new ArrayList<HubsList>();
		
		for (int i=0;i<nNode;i++){
			HubsList hubsList = new HubsList(nHub);
			int counter=1;
			hubsList.getList()[0]=i;
			fun(counter, hubsList);
		}
	}
	
	private void fun(int counter, HubsList hubsList){
		if (counter==this.nHub-1){
			for (int i=hubsList.getList()[counter-1]+1;i<this.nNode;i++){
				hubsList.getList()[counter]=i;
				HubsList temp=new HubsList(hubsList);
				this.problems.add(temp);
			}
		}else{
			for (int i=hubsList.getList()[counter-1]+1;i<this.nNode;i++){
				hubsList.getList()[counter]=i;
				int counter2=new Integer(counter);
				fun(++counter2, hubsList);
			}
//			counter++;
		}
	}	
	
	public List<HubsList> getProblems(){
		return this.problems;
	}
	
	public void printProblems(){
		for (HubsList l:this.problems){
			System.out.println(l);
		}
	}
}

class HubsList{
	private int[] list;
	
	public HubsList(int nHub){
		this.list=new int[nHub];
	}
	
	public HubsList(HubsList other){
		this.list=new int[other.list.length];
		for (int i=0;i<other.list.length;i++){
			this.list[i]=other.list[i];
		}
	}
	
	public void add(int e){
		list[e]=e;
	}
	
	public int[] getList(){
		return this.list;
	}

	
	public String toString(){
		String str=new String();
		for (int i:this.list){
			str+=i+",";
		}
		str="["+str+"]";
		str=str.replace(",]", "]");
		return str;
	}

}
