package rhlp_cp;

import org.apache.commons.math3.util.*;

public class ProblemGenerator {
	private HubsList[] problems;
	double test;
	private int nNode;
	private  int nHub;
	private int ctr=0;
	
	@SuppressWarnings("deprecation")
	public ProblemGenerator(int nNode, int nHub){
		this.nHub=nHub;
		this.nNode=nNode;
		this.problems=new HubsList[(int) ArithmeticUtils.binomialCoefficientDouble(nNode, nHub)];
				
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
				this.problems[this.ctr++]=temp;
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
	
	public HubsList[] getProblems(){
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
