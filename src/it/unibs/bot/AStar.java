package it.unibs.bot;

import java.util.*;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.MapMatrix;

public class AStar {
	private static HashMap<Integer, Node> nodes = MapMatrix.getNodes();
	
	private static Stack<Node> nodePath;

	public static Stack<Node> generatePath(int startX, int startY, int targetX, int targetY) {
		// Avoid target being on a wall
		try {
			Node start = nodes.get(calculateKey(startX, startY));
			Node target = nodes.get(calculateKey(targetX, targetY));
			
			aStarPath(start, target);
			//printPath(target);
			nodePath = createNodePath(target);
			
			start = null;
			target = null;
		} catch (Exception e) {
			//System.out.println(e);
		}
		
		clearNodes();
		return nodePath;
	}
	
	private static void clearNodes() {
		for(Node n: nodes.values()) {
			n.resetNode();
		}
	}

	private static int calculateKey(int x, int y) {
		return (y * MapMatrix.WIDTH) + x;
	}

	public static Node aStarPath(Node start, Node target){
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

	// TODO: delete
	public static ArrayList<Integer> createPath(Node target){
		Node n = target;
		if(n == null)
			return null;
		//Costo ugale a "f"
		//setCost(n.f);
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

	public static Stack<Node> createNodePath(Node target){
		Node n = target;
		if(n == null)
			return null;

		Stack<Node> path = new Stack<>();

		while(n.parent != null){
			path.push(nodes.get(n.id));
			n = n.parent;
		}

		return path;
	}

	public static void printPath(Node target){
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
