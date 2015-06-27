/*
 *
 * Copyright (c) 2004-2008 Arizona State University.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ARIZONA STATE UNIVERSITY ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL ARIZONA STATE UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package yen_alg;

import java.util.List;
import java.util.Vector;

import rhlp_cp.Problem;
import yen_alg.BaseElementWithWeight;
import yen_alg.BaseVertex;

/**
 * The class defines a path in graph.
 * 
 * @author yqi
 */
public class Path implements BaseElementWithWeight {
	
	private List<BaseVertex> vertexList = new Vector<BaseVertex>();
	private double weight = -1;
	
	public Path() {
		
	}
	
	public Path(List<BaseVertex> vertexList, double weight) {
		this.vertexList = vertexList;
		this.weight = weight;
		
	}

	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public List<BaseVertex> getVertexList() {
		return vertexList;
	}
	
	@SuppressWarnings("null")
	public int getType(Problem prob){
		int type=0;
		if (this.vertexList.size()==2){
			if (prob.nodes.get(this.vertexList.get(0).getId()).isHub()
					&& prob.nodes.get(this.vertexList.get(1).getId()).isHub())
				type = 3;
			else if(prob.nodes.get(this.vertexList.get(0).getId()).isHub())
				type = 1;
			else
				type = 2;			
		}else if(this.vertexList.size()==3){
			if(!prob.nodes.get(this.vertexList.get(0).getId()).isHub()
					&& !(prob.nodes.get(this.vertexList.get(1).getId()).isHub()))
				type = 6;
			else if (!prob.nodes.get(this.vertexList.get(0).getId()).isHub())
				type = 5;
			else
				type = 4;
		}else if (this.vertexList.size()==4){
			type = 7;
		}else{
			System.out.println("the path is inconsistent: "+this);
			return (Integer) null;
		}
		return type;
	}
	
	public double getCost(Problem prob){
		double cost=0;
		double alpha;
		for (int i=0; i<this.getVertexList().size()-1;i++){
			if (prob.hubsList.contains(prob.nodes.get(i))
					&& prob.hubsList.contains(prob.nodes.get(i+1)))
				alpha=prob.alpha;
			else
				alpha=0;
			cost+=(1-alpha)*prob.distance[this.getVertexList().get(i).getId()][this.getVertexList().get(i+1).getId()];
		}
		return cost;
	}
	
	public double getFailProb(double q){
		double p=q;
		if (this.getVertexList().size()==4)
			p = 2 * q - Math.pow(q, 2);
		return p;
	}
	
	@Override
	public boolean equals(Object right) {
		
		if (right instanceof Path) {
			Path rPath = (Path) right;
			return vertexList.equals(rPath.vertexList);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return vertexList.hashCode();
	}
	
	public String toString() {
		return vertexList.toString() + ":" + weight;
	}
}
