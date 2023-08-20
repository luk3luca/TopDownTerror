package it.unibs.bot;

import java.util.*;

import it.unibs.mainApp.Battlefield;

public class Node implements Comparable<Node> {
	// Id for readability of result purposes
	public int id;
	public int row;
	public int col;

	// Parent in the path
	public Node parent = null;

	public List<Edge> neighbors;

	// Evaluation functions		Double.MAX_VALUE
	public double f = 5000;
	public double g = 3000;
	// Hardcoded heuristic
	public double h = 0; 

	public Node(int id, int row, int col) {
	    this.id = id;
	    this.row = row;
	    this.col = col;
	    this.neighbors = new ArrayList<>();
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	@Override
	public int compareTo(Node n) {
		return Double.compare(this.f, n.f);
	}

	public class Edge {
		public double weight;
		public Node node;
		
		Edge(double weight, Node node){
			this.weight = weight;
			this.node = node;
		}
	}

	public void addBranch(double weight, Node node){
		Edge newEdge = new Edge(weight, node);
		neighbors.add(newEdge);
	}

	public double calculateHeuristic(Node target){
		return Math.sqrt(Math.pow(target.getRow() - this.getRow(), 2) + Math.pow(target.getCol() - this.getCol(), 2)) * Battlefield.BATTLEFIELD_TILEDIM;
	}




}
