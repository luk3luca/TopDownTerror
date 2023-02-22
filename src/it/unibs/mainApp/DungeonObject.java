package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class DungeonObject {
	
	
	protected double[] position = {0, 0, 0};
	protected Shape shape;
	private Color color;
	
	public DungeonObject(double[] position, Shape shape, Color color) {
		this.position = position;
		this.shape = shape;
		this.color = color;
	}

	public double getX() {
		return position[0];
	}
	public double getY() {
		return position[1];
	}
	public double getR() {
		return position[2];
	}
	
	public void setX(double p) {
		position[0] = p;
	}
	public void setY(double p) {
		position[1] = p;
	}
	public void setR(double p) {
		position[2] = p;
	}

	public Shape getShape() {
		AffineTransform t = new AffineTransform();
		t.translate(position[0], position[1]);
		t.rotate(position[2]);
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
