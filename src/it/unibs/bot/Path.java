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

	public void setStartX(int startX) {this.startX = startX;}
	public void setStartY(int startY) {this.startY = startY;}
	public void setTargetX(int targetX) {this.targetX = targetX;}
	public void setTargetY(int targetY) {this.targetY = targetY;}
	
	public Path(int startX, int startY, int targetX, int targetY) {
		this.startX = startX;
		this.startY = startY;
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public Stack<Node> getPath() {
		return path;
	}
	
	public void generatePath() {
		path = AStar.generatePath(startX, startY, targetX, targetY);
	}

	public void setAstar(int startX, int startY, int targetX, int targetY) {
		setStartX(startX);
		setStartY(startY);
		setTargetX(targetX);
		setTargetY(targetY);
	}
	
	public boolean targetReached() {
		return AStar.targetNotReached(startX, startY, targetX, targetY);
	}

}
