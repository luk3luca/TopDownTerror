package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;

public class MovementObject extends DungeonObject {
	
	public MovementObject() {
		
	}

	private static final double VELOCITY = 100.;
	
	protected double[] position = {0,0,0};
	protected double velocity = VELOCITY;
	protected boolean isAlive = true;

	public boolean isAlive() {
		return isAlive;
	}
	
	public void rotate (double r) {
		position[2] += r;
	}
	
	
	public void stepNext() {
		position[0] = position[0] + VELOCITY;
		position[1] = position[1] + VELOCITY;
		position[2] = position[2] + VELOCITY;
	}
	
	//TODO metodi per le collisioni 
	
	
	/*GETTERS AND SETTERS*/
	public void setPosition(double x,double y, double r) {
		this.position[0] = x;
		this.position[1] = y;
		this.position[2] = r;
	}
}
