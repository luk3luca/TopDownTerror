package it.unibs.mainApp;

import java.awt.Color;
import java.util.ArrayList;

public class Battlefield {

	protected static final int BATTLEFIELD_TILEDIM = 40;
	protected static final int BATTLEFIELD_WIDTH = BATTLEFIELD_TILEDIM * (MapMatrix.WIDTH + 1);
	protected static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_TILEDIM * (MapMatrix.HEIGHT + 1);
	 
	protected ArrayList<Tile> tiles = new ArrayList<Tile>();
	protected T_Spawn[] spawns = new T_Spawn[6];
	protected Player[] player = new Player[6];
	
	private int[][] mapMatrix = MapMatrix.getMatrix();
	
	public Battlefield() {
		buildMap();
		buildPlayer();
	}
	
	
	private void buildMap() {
		int spawnCounter = 1;
		for(int y = 0; y < MapMatrix.HEIGHT; y++) {
			for(int x = 0; x < MapMatrix.WIDTH; x++) {
				switch(mapMatrix[y][x]) {
					case 0:
						tiles.add(buildPavement(y, x, BATTLEFIELD_TILEDIM));
						break;
					case 1:
						tiles.add(buildWall(y,x, BATTLEFIELD_TILEDIM));
						break;
					case 2:
						T_Spawn s = buildSpawn(y,x, BATTLEFIELD_TILEDIM, spawnCounter);
						tiles.add(s);
						spawns[spawnCounter - 1] = s;
						spawnCounter++;
						break;
					default:
						break;
				}
			}
		}
	}
	
	private void buildPlayer() {
		for(int i=0; i < player.length; i++) {
			player[i] = new Player("player " + i , spawns[i], TeamColors.getColor(i + 1));
		}
	}
	
	 
	
	
	private T_Pavement buildPavement(int y, int x, int tileDim) {
		return new T_Pavement(y * tileDim, x * tileDim, tileDim, true);
	}
	
	private T_Wall buildWall(int y, int x, int tileDim) {
		return new T_Wall(y * tileDim, x * tileDim, tileDim, false);
	}

	private T_Spawn buildSpawn(int y, int x, int tileDim, int spawnCounter) {
		Color c = TeamColors.getColorAlpha(spawnCounter);
		return new T_Spawn(y * tileDim, x * tileDim, tileDim * MapMatrix.SPAWN_H, tileDim * MapMatrix.SPAWN_W, true, c);
	}

	public void stepNext() {
		
	}
	
}
