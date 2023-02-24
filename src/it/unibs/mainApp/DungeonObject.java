package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class DungeonObject {
	protected double posX;
	protected double posY;
	protected double angle = 0;
	protected Shape shape;
	private Color color;
	
	public DungeonObject() {
	}

	public double getPosX() {
		return posX;
	}
	
	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Shape getShape() {
		AffineTransform t = new AffineTransform();
		t.translate(this.posX, this.posY);
		t.rotate(this.angle);
		return t.createTransformedShape(shape);
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
		
	
	
	
	
	
	
	
	
	
}
