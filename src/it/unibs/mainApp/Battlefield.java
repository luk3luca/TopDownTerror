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

	protected ArrayList<Bullet> bullet = new ArrayList<>();
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
			if(i > 2)
				player[i].setAngle(-Math.PI/2);
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
	
//	public void checkBorder() {
//		for (int i = 0; i < player.length; i++) {
//			if (player[i].posX >= BATTLEFIELD_WIDTH - BATTLEFIELD_TILEDIM/2  || player[i].posY >= BATTLEFIELD_HEIGHT - BATTLEFIELD_TILEDIM/2 ) {
//				player[i].setPosX(player[i].getPosX() - Player.M_VELOCITY);
//				player[i].setPosY(player[i].getPosY() - Player.M_VELOCITY);
//			}else if (player[i].posX <= 0 || player[i].posY <= 0) {
//				player[i].setPosX(player[i].getPosX() + Player.M_VELOCITY);
//				player[i].setPosY(player[i].getPosY() + Player.M_VELOCITY);
//			}
//		}
//	}

	public void stepNext() {
		//checkBorder();
		for(Bullet b:bullet) {
			b.stepNext();
		}
		checkCollision();
	}
	
	
	/*----------------GESTIONE COLLISIONI----------------*/

	// TODO COLLISIONI MOVING OBJECT <--> MOVING OBJECT 

	//COLLISIONI MOVING OBJECT <--> TILES
	private void detectCollision() {
		int nObjs = walls.size();
		if(nObjs < 2)
			return;
		
		Tile[] objs = new Tile[nObjs];
		walls.toArray(objs);
		
	}
	
	private void checkCollision() {
		for(int i=0; i<player.length ; i++) {
			// Riquadro in cui si trova il centro del player
			int playerSquareX = (int)((player[i].getPosX() + BATTLEFIELD_TILEDIM/4) / BATTLEFIELD_TILEDIM);
			int playerSquareY = (int)((player[i].getPosY() + BATTLEFIELD_TILEDIM/4 )/ BATTLEFIELD_TILEDIM);

			player[i].resetCollision();
			crossCollision(player[i], playerSquareX ,playerSquareY);
			angleCollision(player[i],playerSquareX ,playerSquareY);			
		}
	}
	
	// Controllo delle collisioni sui muri supra, sotto, destra, sisnistra del player
	private void crossCollision(Player player,int playerSquareX, int playerSquareY) {
		// Coordinate delle Tile da controllare per collisioni, con controllo per out of bounds
		int[] topSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX};
		int[] bottomSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX};
		int[] leftSquare = {playerSquareY, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] rightSquare = {playerSquareY, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
		
		Tile topTile = tiles.get(topSquare[0]*MapMatrix.WIDTH + topSquare[1]);
		Tile bottomTile = tiles.get(bottomSquare[0]*MapMatrix.WIDTH + bottomSquare[1]);
		Tile leftTile = tiles.get(leftSquare[0]*MapMatrix.WIDTH + leftSquare[1]);
		Tile rightTile = tiles.get(rightSquare[0]*MapMatrix.WIDTH + rightSquare[1]);
		
		//Controllo collisioni per ogni Tile
		//TopTile
		if(!topTile.isWalkable())
			player.setTopCollision(topTile.checkCollision(player));
		
		//BottomTile
		if(!bottomTile.isWalkable())
			player.setBottomCollision(bottomTile.checkCollision(player));
		
		//LeftTile
		if(!leftTile.isWalkable())
			player.setLeftCollision(leftTile.checkCollision(player));
		
		//RightTile
		if(!rightTile.isWalkable())
			player.setRightCollision(rightTile.checkCollision(player));
		
		//Gestione delle collisioni, 4 angoli e 4 pareti
		if(player.isTopCollision() && player.isLeftCollision()) {
			player.setPosY(player.getPosY() + player.getM_velocity());
			player.setPosX(player.getPosX() + player.getM_velocity());
		}
		else if(player.isTopCollision() && player.isRightCollision()) {
			player.setPosY(player.getPosY() + player.getM_velocity());
			player.setPosX(player.getPosX() - player.getM_velocity());
		}
		else if(player.isBottomCollision() && player.isLeftCollision()) {
			player.setPosY(player.getPosY() - player.getM_velocity());
			player.setPosX(player.getPosX() + player.getM_velocity());
		}
		else if(player.isBottomCollision() && player.isRightCollision()) {
			player.setPosY(player.getPosY() - player.getM_velocity());
			player.setPosX(player.getPosX() - player.getM_velocity());
		}
		else if(player.isTopCollision()) {
			player.setPosY(player.getPosY() + player.getM_velocity());
			//player.setPosX(player.getPosX());
		}
		else if(player.isBottomCollision()) {
			player.setPosY(player.getPosY() - player.getM_velocity());
			//player.setPosX(player.getPosX());
		}
		else if(player.isLeftCollision()) {
			//player.setPosY(player.getPosY());
			player.setPosX(player.getPosX() + player.getM_velocity());
		}
		else if(player.isRightCollision()) {
			//player.setPosY(player.getPosY());
			player.setPosX(player.getPosX() - player.getM_velocity());
		}
	}
	
	// Controllo e gestione delle collisioni con gli spigoli
	private void angleCollision(Player player,int playerSquareX, int playerSquareY ) {
		// COordinate spipgoli
		int[] topLeftSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] topRightSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
		int[] bottomLeftSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] bottomRightSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
		
		// Spigoli con cui puo' collidere
		Tile topLeftTile = tiles.get(topLeftSquare[0]*MapMatrix.WIDTH + topLeftSquare[1]);
		Tile topRightTile = tiles.get(topRightSquare[0]*MapMatrix.WIDTH + topRightSquare[1]);
		Tile bottomLeftTile = tiles.get(bottomLeftSquare[0]*MapMatrix.WIDTH + bottomLeftSquare[1]);
		Tile bottomRightTile = tiles.get(bottomRightSquare[0]*MapMatrix.WIDTH + bottomRightSquare[1]);		
		
		// Controllo collisione con spigoli
		if(!topLeftTile.isWalkable())
			player.setTopLeftCollision(topLeftTile.checkCollision(player));

		if(!topRightTile.isWalkable())
			player.setTopRightCollision(topRightTile.checkCollision(player));
		
		if(!bottomLeftTile.isWalkable())
			player.setBottomLeftCollision(bottomLeftTile.checkCollision(player));
		
		if(!bottomRightTile.isWalkable())
			player.setBottomRightCollision(bottomRightTile.checkCollision(player));
		
		// Gestione collisioni con spigoli
		if(player.isTopLeftCollision()) {		
			player.setPosY(player.getPosY() + player.getM_velocity());
			player.setPosX(player.getPosX() + player.getM_velocity());
		}
		if(player.isTopRightCollision()) {
			player.setPosY(player.getPosY() + player.getM_velocity());
			player.setPosX(player.getPosX() - player.getM_velocity());

		}
		if(player.isBottomLeftCollision()) {
			player.setPosY(player.getPosY() - player.getM_velocity());
			player.setPosX(player.getPosX() + player.getM_velocity());

		}
		if(player.isBottomRightCollision()) {
			player.setPosY(player.getPosY() - player.getM_velocity());
			player.setPosX(player.getPosX() - player.getM_velocity());
		}
	}

	
}
