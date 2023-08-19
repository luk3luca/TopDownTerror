package it.unibs.bot;

import java.util.*;

public class Node implements Comparable<Node> {
	// Id for readability of result purposes
	private static int idCounter = 0;
	public int id;
	public int x;
	public int y;

	// Parent in the path
	public Node parent = null;

	public List<Edge> neighbors;

	// Evaluation functions
	public double f = Double.MAX_VALUE;
	public double g = Double.MAX_VALUE;
	// Hardcoded heuristic
	public double h; 

	public Node(int x, int y){
		this.x = x;
		this.y = y;
		//this.h = h;
		this.id = idCounter++;
		this.neighbors = new ArrayList<>();
	}

	@Override
	public int compareTo(Node n) {
		return Double.compare(this.f, n.f);
	}

	public class Edge {
		public int weight;
		public Node node;
		
		Edge(int weight, Node node){
			this.weight = weight;
			this.node = node;
		}
	}

	public void addBranch(int weight, Node node){
		Edge newEdge = new Edge(weight, node);
		neighbors.add(newEdge);
	}

	public double calculateHeuristic(Node target){
		return this.h;
	}




}
