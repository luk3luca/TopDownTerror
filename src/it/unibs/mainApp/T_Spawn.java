package it.unibs.mainApp;

import java.awt.Color;


public class T_Spawn extends Tile {
	private static final String imagePath = null;
	
	private int spawnXCenter;
	private int spawnYCenter;
	private boolean isTop;
	
	/*
	public T_Spawn(int y, int x, int dimY, int dimX, boolean walkable, boolean isTop, Color c) {
		super(y, x, dimY, dimX, walkable, imagePath);
		this.isTop = isTop;
		this.setColor(c);
		
		int tileDim = (dimX / MapMatrix.SPAWN_W);
		this.spawnXCenter = dimX / 2 + x * tileDim;
		this.spawnYCenter = dimY / 2 + y * tileDim;
	}
	*/
	
	public T_Spawn(int y, int x, int dimY, int dimX, boolean walkable, Color c) {
		super(y, x, dimY, dimX, walkable, imagePath);
		
		this.setColor(c);
		
		//int tileDim = (dimX / MapMatrix.SPAWN_W);
		this.spawnXCenter = dimX / 2 + x ;
		this.spawnYCenter = dimY / 2 + y ;
		
	} 


	public int getSpawnX() { return spawnXCenter; }
	public void setSpawnX(int spawnX) { this.spawnXCenter = spawnX; }

	public int getSpawnY() { return spawnYCenter; }
	public void setSpawnY(int spawnY) { this.spawnYCenter = spawnY; }
	
	
	
	//TODO aggiungere controllo per non sparare/prendere danni

	@Override
	public boolean checkCollision(MovingObject o) {
		return (o instanceof Player) ? 
				false: 
				super.checkCollision(o);
	}

}
