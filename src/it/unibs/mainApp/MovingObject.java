package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;

public class MovingObject extends DungeonObject {
	protected double[] position = {0,0,0};
	protected double speed = 0;
	protected boolean isAlive = true;
	
	public MovingObject(double speed) {
		this.speed = speed;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	public void rotate (double r) {
		position[2] += r;
	}
	
	
	public void stepNext() {
		position[0] = position[0] + 1;
		position[1] = position[1];
		position[2] = position[2];
	}
	
	//TODO metodi per le collisioni 
	
	
	/*GETTERS AND SETTERS*/
	public void setPosition(double x,double y, double r) {
		this.position[0] = x;
		this.position[1] = y;
		this.position[2] = r;
	}
}
