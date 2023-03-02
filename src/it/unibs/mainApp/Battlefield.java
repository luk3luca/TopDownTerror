package it.unibs.mainApp;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Battlefield {                                
	protected static final int BATTLEFIELD_TILEDIM = 32;
	protected static final int BATTLEFIELD_WIDTH = BATTLEFIELD_TILEDIM * (MapMatrix.WIDTH + 1);
	protected static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_TILEDIM * (MapMatrix.HEIGHT + 2);
	 
	protected ArrayList<Tile> tiles = new ArrayList<Tile>();
	protected T_Spawn[] spawns = new T_Spawn[6];
	protected Player[] player = new Player[6];
	protected ArrayList<T_Wall> walls = new ArrayList<>();
	//protected Rectangle2D.Double borders = new Rectangle2D.Double(0.,0.,BATTLEFIELD_WIDTH,BATTLEFIELD_HEIGHT); //bordi logici dell'universo 
	
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
						walls.add(buildWall(y,x, BATTLEFIELD_TILEDIM));
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
	
	public void checkBorder() {
		for (int i = 0; i < player.length; i++) {
			if (player[i].posX >= BATTLEFIELD_WIDTH - BATTLEFIELD_TILEDIM/2  || player[i].posY >= BATTLEFIELD_HEIGHT - BATTLEFIELD_TILEDIM/2 ) {
				player[i].setPosX(player[i].getPosX() - Player.M_VELOCITY);
				player[i].setPosY(player[i].getPosY() - Player.M_VELOCITY);
			}else if (player[i].posX <= 0 || player[i].posY <= 0) {
				player[i].setPosX(player[i].getPosX() + Player.M_VELOCITY);
				player[i].setPosY(player[i].getPosY() + Player.M_VELOCITY);
			}
		}
	}

	public void stepNext() {
		checkBorder();
		detectCollision();
	}
	
	
	/*----------------GESTIONE COLLISIONI----------------*/

	//COLLISIONI MOVING OBJECT <--> MOVING OBJECT 

	//COLLISIONI MOVING OBJECT <--> TILES
	//CREARE ARRAYLIST DI MURI E FARE IL CONTROLLO SOLO SU QUELLO, NON SERVE CONSIDERARE IL CAMMINABILE
	private void detectCollision() {
		int nObjs = walls.size();
		if(nObjs < 2)
			return;
		
		Tile[] objs = new Tile[nObjs];
		walls.toArray(objs);
		
		for(int i=0; i<player.length ; i++) {
			boolean x = true;
			for(int j=0; j<nObjs ; j++) {
				if(objs[j].checkCollision(player[i])){
					//TODO  METTERE IL PLAYER NELLA POSIZIONE PRECEDENTE A QUANDO HA COLPITO IL MURO / SETTARE VELOCITA' A ZERO MA --> CAMBIA METODO PER SPOSTAMENTO 
					
					if (player[i].getPosX() < objs[j].getX()) {
		                player[i].setPosX(player[i].getPosX() - Player.M_VELOCITY);
		            }else if (player[i].getPosX() + BATTLEFIELD_TILEDIM/2  > objs[j].getX() + BATTLEFIELD_TILEDIM) {
		                player[i].setPosX(player[i].getPosX() + Player.M_VELOCITY);
		            }else if (player[i].getPosY() < objs[j].getY()) {
		            	player[i].setPosY(player[i].getPosY() - Player.M_VELOCITY);
		            }else if (player[i].getPosY() + BATTLEFIELD_TILEDIM/2 > objs[j].getY() + BATTLEFIELD_TILEDIM) {
		            	player[i].setPosY(player[i].getPosY() + Player.M_VELOCITY);
		            }
					
					
				} 
				
			}
		}
	}
	
	
	
}
