package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;

public abstract class Tile extends DungeonObject{
	private boolean walkable;
	private int dimension;
	
	public Tile(boolean walkable, int dimension) {
		super();
		this.walkable = walkable;
		this.dimension = dimension;
	}
	
	

}
