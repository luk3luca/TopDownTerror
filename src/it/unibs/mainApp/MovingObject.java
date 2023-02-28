package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class MovingObject {
	protected double posX;
	protected double posY;
	protected double angle = 0;
	protected double m_velocity = 0;
	protected double r_velocity = 0;
	
	protected boolean isAlive = true;
	
	protected Shape shape;
	private Color color;
	
	public MovingObject(double m_velocity, double r_velocity, Color color) {
		this.m_velocity = m_velocity;
		this.r_velocity = r_velocity;
		this.color = color;
	}
	
	public double getPosX() { return posX; }
	public void setPosX(double posX) { this.posX = posX; }

	public double getPosY() { return posY; }
	public void setPosY(double posY) { this.posY = posY; }

	public double getAngle() { return angle; }
	public void setAngle(double angle) { this.angle = angle; }
	
	public double getM_velocity() { return m_velocity; }
	public void setM_velocity(double m_velocity) { this.m_velocity = m_velocity; }

	public double getR_velocity() { return r_velocity; }
	public void setR_velocity(double r_velocity) { this.r_velocity = r_velocity; }

	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }
	
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void rotate (double r) {
		this.angle += r;
	}
	
	public Shape getShape() {
		AffineTransform t = new AffineTransform();
		t.translate(this.posX, this.posY);
		//t.rotate(this.angle);
		
		t.rotate(this.angle, 
				Battlefield.BATTLEFIELD_TILEDIM/4, 
				Battlefield.BATTLEFIELD_TILEDIM/4);
				
		/*
		t.rotate(this.angle, 
				this.getPosX(), 
				this.getPosY());
				*/
		return t.createTransformedShape(shape);
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	
	//TODO velocita rotazione
	public void stepNext() {
//		this.posX += this.m_velocity;
//		this.posY += this.m_velocity;	
//		rotate(r_velocity);
	}
	
	//TODO metodi per le collisioni 
	
	 
}
