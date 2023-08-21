package it.unibs.bot;

import java.util.Stack;

import it.unibs.mainApp.MapMatrix;

public class Path {
	private int startX;
	private int startY;
	private int targetX;
	private int targetY;

	private AStar astar;
	
	private Stack<Node> path;

	public Path(int startX, int startY, int targetX, int targetY) {
		this.startX = startX;
		this.startY = startY;
		this.targetX = targetX;
		this.targetY = targetY;
		
		this.astar = new AStar(startX, startY, targetX, targetY);
	}
	
	public Stack<Node> getPath() {
		return path;
	}
	
	public void generatePath() {
		path = astar.generatePath();
	}

	
	public void setAstar(int startX, int startY, int targetX, int targetY) {
		astar.setStartX(startX);
		astar.setStartY(startY);
		astar.setTargetX(targetX);
		astar.setTargetY(targetY);
	}

}
