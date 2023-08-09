package it.unibs.mainApp;

import java.awt.Color;
import java.io.Serializable; 
//OK
public class T_Spawn extends Tile implements Serializable {
	private static final String imagePath = null;
	
	private int spawnXCenter;
	private int spawnYCenter;
	private boolean isTop;
	
	public T_Spawn(int y, int x, int dimY, int dimX, boolean walkable, Color c) {
		//super(y, x, dimY, dimX, walkable, imagePath);
		super(y, x, dimY / MapMatrix.SPAWN_H, dimX / MapMatrix.SPAWN_W, walkable);
		this.setColor(c);
		this.spawnXCenter = dimX / 2 + x ;
		this.spawnYCenter = dimY / 2 + y ;
	}
	
	public T_Spawn(int y, int x, int dim, boolean walkable, Color c) {
		//super(y, x, dim, dim, walkable, imagePath);
		super(y, x, dim, dim, walkable);
		this.setColor(c);
		this.spawnXCenter = dim / 2 + x ;
		this.spawnYCenter = dim / 2 + y ;
	}
	
	/*---GETTERS AND SETTERS---*/
	public int getSpawnX() { return spawnXCenter; }
	public void setSpawnX(int spawnX) { this.spawnXCenter = spawnX; }

	public int getSpawnY() { return spawnYCenter; }
	public void setSpawnY(int spawnY) { this.spawnYCenter = spawnY; }
}
