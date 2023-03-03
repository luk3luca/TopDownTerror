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
					case 3:
						tiles.add(buildPavement(y, x, BATTLEFIELD_TILEDIM));
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
		checkCollision();
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
		/*
		for(int i=0; i<player.length ; i++) {
			boolean x = true;
			for(int j=0; j<nObjs ; j++) {
				
				
				if(objs[j].checkCollision(player[i])){
					//TODO  METTERE IL PLAYER NELLA POSIZIONE PRECEDENTE A QUANDO HA COLPITO IL MURO / SETTARE VELOCITA' A ZERO MA --> CAMBIA METODO PER SPOSTAMENTO 
					
					
					if (player[i].getPosX() < objs[j].getX()) {
		                player[i].setPosX(player[i].getPosX() - Player.M_VELOCITY);
		            }else if (player[i].getPosX() + BATTLEFIELD_TILEDIM/2  > objs[j].getX() + BATTLEFIELD_TILEDIM) {
		                player[i].setPosX(player[i].getPosX() + Player.M_VELOCITY);
		            }
					if (player[i].getPosY() < objs[j].getY()) {
		            	player[i].setPosY(player[i].getPosY() - Player.M_VELOCITY);
		            }else if (player[i].getPosY() + BATTLEFIELD_TILEDIM/2 > objs[j].getY() + BATTLEFIELD_TILEDIM) {
		            	player[i].setPosY(player[i].getPosY() + Player.M_VELOCITY);
		            }
		            
					/*
					if(player[i].getPosX() + BATTLEFIELD_TILEDIM/2 > objs[j].getX()) {
						player[i].setPosX(objs[j].getX() - BATTLEFIELD_TILEDIM/2);
						System.out.println("left collision");
					}
					else if(player[i].getPosX() < objs[j].getX()+ BATTLEFIELD_TILEDIM) {
						player[i].setPosX(objs[j].getX() + BATTLEFIELD_TILEDIM);
						System.out.println("right collision");
					}
					if(player[i].getPosY() < objs[j].getY() + BATTLEFIELD_TILEDIM) {
						player[i].setPosY(objs[j].getY());
						System.out.println("bottom collision");
					}					
					else if(player[i].getPosY() + BATTLEFIELD_TILEDIM/2 > objs[j].getY()) {
						player[i].setPosY(objs[j].getY()- BATTLEFIELD_TILEDIM/2);
						System.out.println("top collision");
					}
					
				} 
				*/
			}
	
	
	private void checkCollision() {
		for(int i=0; i<player.length ; i++) {
			//Player p = player[i];
			int playerSquareX = (int)((player[i].getPosX() + BATTLEFIELD_TILEDIM/4) / BATTLEFIELD_TILEDIM);
			int playerSquareY = (int)((player[i].getPosY() + BATTLEFIELD_TILEDIM/4 )/ BATTLEFIELD_TILEDIM);
			//System.out.println(playerSquareY + " " + playerSquareX);
			
			int[] topSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX};
			int[] bottomSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX};
			int[] leftSquare = {playerSquareY, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
			int[] rightSquare = {playerSquareY, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
			
			Tile topTile = tiles.get(topSquare[0]*MapMatrix.WIDTH + topSquare[1]);
			Tile bottomTile = tiles.get(bottomSquare[0]*MapMatrix.WIDTH + bottomSquare[1]);
			Tile leftTile = tiles.get(leftSquare[0]*MapMatrix.WIDTH + leftSquare[1]);
			Tile rightTile = tiles.get(rightSquare[0]*MapMatrix.WIDTH + rightSquare[1]);			
			
			if(topTile.isWalkable() == false) {
				player[i].setTopCollision(topTile.checkCollision(player[i]));
				//System.out.println("top:" + topTile.getClass());
			}else {
				player[i].setTopCollision(false);
			}
			if(bottomTile.isWalkable() == false) {
				//System.out.println("bottom:" + bottomTile.getClass());
				player[i].setBottomCollision(bottomTile.checkCollision(player[i]));
			}else {
				player[i].setBottomCollision(false);
			}
			if(leftTile.isWalkable() == false) {
				player[i].setLeftCollision(leftTile.checkCollision(player[i]));
				//System.out.println("left:" + leftTile.getClass());
			}else {
				player[i].setLeftCollision(false);
			}
			if(rightTile.isWalkable() == false) {
				player[i].setRightCollision(rightTile.checkCollision(player[i]));
				//System.out.println("right:" + rightTile.getClass());
			}else {
				player[i].setRightCollision(false);
			}
								
			/*
			System.out.println("top:" + player[i].isTopCollision());
			System.out.println("bottom:" + player[i].isBottomCollision());
			System.out.println("left:" + player[i].isLeftCollision());
			System.out.println("right:" + player[i].isRightCollision());
			*/
			
			
			if(player[i].isTopCollision() && player[i].isLeftCollision()) {
				player[i].setPosY(topTile.getY() + BATTLEFIELD_TILEDIM);
				player[i].setPosX(leftTile.getX() + BATTLEFIELD_TILEDIM);
				System.out.println("TL");
			}
			else if(player[i].isTopCollision() && player[i].isRightCollision()) {
				player[i].setPosY(topTile.getY() + BATTLEFIELD_TILEDIM);
				player[i].setPosX(rightTile.getX() - BATTLEFIELD_TILEDIM/2);
				System.out.println("TR");
			}
			else if(player[i].isBottomCollision() && player[i].isLeftCollision()) {
				player[i].setPosY(bottomTile.getY() - BATTLEFIELD_TILEDIM/2);
				player[i].setPosX(leftTile.getX() + BATTLEFIELD_TILEDIM);
				System.out.println("BL");
			}
			else if(player[i].isBottomCollision() && player[i].isRightCollision()) {
				player[i].setPosY(bottomTile.getY() - BATTLEFIELD_TILEDIM/2);
				player[i].setPosX(rightTile.getX() - BATTLEFIELD_TILEDIM/2);
				System.out.println("BL");
			}
			else if(player[i].isTopCollision()) {		//TODO FIX
				player[i].setPosY(topTile.getY() + BATTLEFIELD_TILEDIM);
				player[i].setPosX(player[i].getPosX());
				System.out.println("T");
			}
			else if(player[i].isBottomCollision()) {	//TODO FIX
				player[i].setPosY(bottomTile.getY() - BATTLEFIELD_TILEDIM/2);
				player[i].setPosX(player[i].getPosX());
				System.out.println("B");
			}
			else if(player[i].isLeftCollision()) {
				player[i].setPosY(player[i].getPosY());
				player[i].setPosX(leftTile.getX() + BATTLEFIELD_TILEDIM);
				System.out.println("L");
			}
			else if(player[i].isRightCollision()) {
				player[i].setPosY(player[i].getPosY());
				player[i].setPosX(rightTile.getX() - BATTLEFIELD_TILEDIM/2);
				System.out.println("R");
			}
			
			//player[i].resetCollision();
			/*
			if(player[i].isTopCollision() && player[i].isLeftCollision()) {
				System.out.println("TL");
			}
			else if(player[i].isTopCollision() && player[i].isRightCollision()) {
				System.out.println("TR");
			}
			else if(player[i].isBottomCollision() && player[i].isLeftCollision()) {
				System.out.println("BL");
			}
			else if(player[i].isBottomCollision() && player[i].isRightCollision()) {
				System.out.println("BL");
			}
			else if(player[i].isTopCollision()) {
				System.out.println("T");
			}
			else if(player[i].isBottomCollision()) {
				System.out.println("B");
			}
			else if(player[i].isLeftCollision()) {
				System.out.println("L");
			}
			else if(player[i].isRightCollision()) {
				System.out.println("R");
			}
			*/
			
		}
	}
	
	
}
