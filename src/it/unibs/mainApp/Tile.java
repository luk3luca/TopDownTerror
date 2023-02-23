package it.unibs.mainApp;

import java.awt.geom.Area;
import java.awt.*;

public abstract class Tile extends DungeonObject{
	private int x;
	private int y;
	private boolean walkable;
	private int dimY;
	private int dimX;
	
	//Si danno 2 dimensioni, per spawn si puo creare rettangolo
	public Tile(int y, int x, int dimY, int dimX, boolean walkable) {
		super();
		this.walkable = walkable;
		this.dimY = dimY;
		this.dimX = dimX;
		this.shape = new Area(new Rectangle(x, y, this.dimX, this.dimY));
	}
	
	

}
