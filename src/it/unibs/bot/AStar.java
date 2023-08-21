package it.unibs.bot;

import java.nio.file.spi.FileSystemProvider;
import java.util.*;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.MapMatrix;

public class AStar {
	private double cost;

	private HashMap<Integer, Node> nodes;

	private int startX;
	private int startY;
	private int targetX;
	private int targetY;

	private Node start;
	private Node target;
	
	private Stack<Node> nodePath;

	public AStar(int startX, int startY, int targetX, int targetY) {
		this.nodes = MapMatrix.getNodes();
		this.startX = startX;
		this.startY = startY;
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public void setStartX(int startX) {this.startX = startX;}

	public void setStartY(int startY) {this.startY = startY;}

	public void setTargetX(int targetX) {this.targetX = targetX;}

	public void setTargetY(int targetY) {this.targetY = targetY;}

	public Stack<Node> generatePath() {

		start = nodes.get(calculateKey(startX, startY));
		target = nodes.get(calculateKey(targetX, targetY));
		
		aStarPath(start, target);
		printPath(target);
		nodePath = createNodePath(target);
		
		start = null;
		target = null;
		
		return nodePath;
	}

	private int calculateKey(int x, int y) {
		return (y * MapMatrix.WIDTH) + x;
	}

	public Node aStarPath(Node start, Node target){
		PriorityQueue<Node> closedList = new PriorityQueue<>();
		PriorityQueue<Node> openList = new PriorityQueue<>();

		start.f = start.g + start.calculateHeuristic(target);
		openList.add(start);

		while(!openList.isEmpty()){
			Node n = openList.peek();
			if(n == target){
				return n;
			}

			for(Node.Edge edge : n.neighbors){
				Node m = edge.node;
				double totalWeight = n.g + edge.weight;

				if(!openList.contains(m) && !closedList.contains(m)){
					m.parent = n;
					m.g = totalWeight;
					m.f = m.g + m.calculateHeuristic(target);
					openList.add(m);
				} else {
					if(totalWeight < m.g){
						m.parent = n;
						m.g = totalWeight;
						m.f = m.g + m.calculateHeuristic(target);

						if(closedList.contains(m)){
							closedList.remove(m);
							openList.add(m);
						}
					}
				}
			}

			openList.remove(n);
			closedList.add(n);
		}
		return null;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	// CHANGE TO STACK FOR LIFO STRUCTURE
	public ArrayList<Integer> createPath(Node target){
		Node n = target;
		if(n == null)
			return null;
		//Costo ugale a "f"
		setCost(n.f);
		ArrayList<Integer> path = new ArrayList<>();

		while(n.parent != null){
			path.add(n.id);
			n = n.parent;
		}
		//starting position
		//path.add(n.id);

		Collections.reverse(path);
		
		return path;
	}

	public Stack<Node> createNodePath(Node target){
		Node n = target;
		if(n == null)
			return null;
		//Costo ugale a "f"
		setCost(n.f);
		Stack<Node> path = new Stack<>();

		while(n.parent != null){
			path.push(nodes.get(n.id));
			n = n.parent;
		}
		//starting position
		//path.add(n.id);
		//Collections.reverse(path);

		return path;
	}

	public void printPath(Node target){
		Node n = target;

		if(n==null)
			return;

		List<Integer> ids = new ArrayList<>();

		while(n.parent != null){
			ids.add(n.id);
			n = n.parent;
		}
		//starting position
		//ids.add(n.id);

		Collections.reverse(ids);

		System.out.println("Path:");
		for(int id : ids){
			//System.out.print(id + " ");
			System.out.println(id + " (" + nodes.get(id).getCol() + ", " + nodes.get(id).getRow() + ")");
		}
		System.out.println("");
	}
}
