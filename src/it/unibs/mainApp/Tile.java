package it.unibs.mainApp;

import java.awt.geom.Area;
import java.awt.*;

public abstract class Tile extends DungeonObject{
	private int x;
	private int y;
	private boolean walkable;
	private int dimension;
	
	public Tile(int y, int x, int dim1, int dim2, boolean walkable) {
		super();
		this.walkable = walkable;
		this.dimension = dimension;
		this.shape = new Area(new Rectangle(x, y, dim2, dim1));
	}
	
	

}
