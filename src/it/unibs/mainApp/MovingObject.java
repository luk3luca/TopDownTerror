package it.unibs.mainApp;


public class MovingObject extends DungeonObject {
	
	protected double velocity = 0;
	protected boolean isAlive = true;
	
	public MovingObject(double speed) {
		this.velocity = speed;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	public void rotate (double r) {
		position[2] += r;
	}
	
	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public void stepNext() {
		position[0] = position[0] + this.velocity;
		position[1] = position[1] + this.velocity;
		position[2] = position[2];
	}
	
	//TODO metodi per le collisioni 
	
	
}
