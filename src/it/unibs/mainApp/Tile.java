package it.unibs.mainApp;

import java.awt.geom.Area;

import javax.swing.ImageIcon;

import java.awt.*;

public abstract class Tile extends DungeonObject{
	private int x;
	private int y;
	private boolean walkable;
	private int dimY;
	private int dimX;
	
	private String imagePath;
	
	//Si danno 2 dimensioni, per spawn si puo creare rettangolo
	public Tile(int y, int x, int dimY, int dimX, boolean walkable, String imagePath) {
		super();
		this.walkable = walkable;
		this.dimY = dimY;
		this.dimX = dimX;
		this.shape = new Area(new Rectangle(x, y, this.dimX, this.dimY));
		this.imagePath = imagePath;
		
		this.x = x;
		this.y = y;
	}
	
	
	public Image getImage(){
		return  new ImageIcon(imagePath).getImage();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	

}
