package it.unibs.bot;

import java.util.*;

import it.unibs.mainApp.Battlefield;

public class Node implements Comparable<Node> {
	// Id
	public int id;
	public int row;
	public int col;

	// Parent in the path
	public Node parent = null;
	public List<Edge> neighbors;

	// Evaluation functions
	public double f = 10000;
	public double g = 10000;
	// Hardcoded heuristic
	public double h = 0; 

	public Node(int id, int row, int col) {
	    this.id = id;
	    this.row = row;
	    this.col = col;
	    this.neighbors = new ArrayList<>();
	}
	
	public void resetNode() {
		h = 0;
		parent = null;
	}
	
	public int getRow() {return row;}
	public int getCol() {return col;}
	public int getId() {return id;}

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

	public void printEdge() {
		for(Edge n: neighbors) {
			System.out.println(n.node.getId() + ": (" + n.node.getCol() + ", " + n.node.getRow() + ")");
		}
	}


}
