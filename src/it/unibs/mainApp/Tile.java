package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;

public abstract class Tile extends DungeonObject{
	private boolean walkable;
	private int dimension;
	
	public Tile(double[] position, Shape shape, Color color) {
		super(position, shape, color);
		
	}
	
	

}
