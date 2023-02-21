package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;

public class DungeonObject {
	private double[] position = {0, 0, 0};
	private Shape shape;
	private Color color;
	
	public DungeonObject(double[] position, Shape shape, Color color) {
		this.position = position;
		this.shape = shape;
		this.color = color;
	}
	
	
	
}
