package it.unibs.mainApp;

import java.awt.geom.Area;
import java.io.Serializable;

import javax.swing.ImageIcon;

import java.awt.*;
//OK
public class Tile implements Serializable {
	private int posX;
	private int posY;
	private int dimY;
	private int dimX;
	private boolean walkable;
	
	private Shape shape;
	private Color color;
	private String imagePath;
	  
	//Si danno 2 dimensioni, per spawn si puo creare rettangolo
	public Tile(int y, int x, int dimY, int dimX, boolean walkable, String imagePath) {
		this.posX = x;
		this.posY = y;
		this.dimY = dimY;
		this.dimX = dimX;
		this.walkable = walkable;
		this.shape = new Rectangle(x, y, this.dimX, this.dimY);
		this.imagePath = imagePath;
	}
	
	public Tile(int y, int x, int dimY, int dimX, boolean walkable) {
		this.posX = x;
		this.posY = y;
		this.dimY = dimY;
		this.dimX = dimX;
		this.walkable = walkable;
		this.shape = new Rectangle(x, y, this.dimX, this.dimY);
	}
	
	//COLLISION DETECTION
	public boolean checkCollision(MovingObject o) {
		Area a = new Area(this.getShape());
		a.intersect(new Area(o.getShape()));
		
		return !a.isEmpty();
	}
		
	/*---GETTERS AND SETTERS---*/
	public Image getImage(){return  new ImageIcon(imagePath).getImage();}
	
	public Shape getShape() {return this.shape;}
	public void setShape(Shape shape) {this.shape = shape;}
	
	public boolean isWalkable() {return walkable;}
	public void setWalkable(boolean walkable) {this.walkable = walkable;}
	
	public double getX() {return posX;}
	public double getY() {return posY;}
	
	public Color getColor() {return color;}
	public void setColor(Color color) {this.color = color;}
	
}
