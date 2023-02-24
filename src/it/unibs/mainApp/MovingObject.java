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
		this.angle += r;
	}
	
	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	//TODO velocita rotazione
	public void stepNext() {
		this.posX += this.velocity;		//TODO m_velocity
		this.posY += this.velocity;	
		this.angle += this.velocity;	//TODO a_velocity
	}
	
	//TODO metodi per le collisioni 
	
	
}
