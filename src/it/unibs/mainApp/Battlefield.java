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
		
	}
	
	private boolean isCrossWalkable(Tile topTile, Tile bottomTile,Tile leftTile, Tile rightTile) {
		if(topTile.isWalkable() && bottomTile.isWalkable() && leftTile.isWalkable() && rightTile.isWalkable())
			return true;
		return false;
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
			
			if(!isCrossWalkable(topTile, bottomTile, leftTile, rightTile)) {
				crossCollision(player[i], topTile, bottomTile, leftTile, rightTile);
			}
			else {
				angleCollision(player[i],playerSquareX ,playerSquareY );
			}
			
				
		
			
			
		}
	}
	
	
	private void crossCollision(Player player,Tile topTile, Tile bottomTile,Tile leftTile, Tile rightTile) {
		if(topTile.isWalkable() == false) {
			player.setTopCollision(topTile.checkCollision(player));
			//System.out.println("top:" + topTile.getClass());
		}else {
			player.setTopCollision(false);
		}
		if(bottomTile.isWalkable() == false) {
			//System.out.println("bottom:" + bottomTile.getClass());
			player.setBottomCollision(bottomTile.checkCollision(player));
		}else {
			player.setBottomCollision(false);
		}
		if(leftTile.isWalkable() == false) {
			player.setLeftCollision(leftTile.checkCollision(player));
			//System.out.println("left:" + leftTile.getClass());
		}else {
			player.setLeftCollision(false);
		}
		if(rightTile.isWalkable() == false) {
			player.setRightCollision(rightTile.checkCollision(player));
			//System.out.println("right:" + rightTile.getClass());
		}else {
			player.setRightCollision(false);
		}
		
		
		if(player.isTopCollision() && player.isLeftCollision()) {
			player.setPosY(topTile.getY() + BATTLEFIELD_TILEDIM);
			player.setPosX(leftTile.getX() + BATTLEFIELD_TILEDIM);
			System.out.println("TL");
		}
		else if(player.isTopCollision() && player.isRightCollision()) {
			player.setPosY(topTile.getY() + BATTLEFIELD_TILEDIM);
			player.setPosX(rightTile.getX() - BATTLEFIELD_TILEDIM/2);
			System.out.println("TR");
		}
		else if(player.isBottomCollision() && player.isLeftCollision()) {
			player.setPosY(bottomTile.getY() - BATTLEFIELD_TILEDIM/2);
			player.setPosX(leftTile.getX() + BATTLEFIELD_TILEDIM);
			System.out.println("BL");
		}
		else if(player.isBottomCollision() && player.isRightCollision()) {
			player.setPosY(bottomTile.getY() - BATTLEFIELD_TILEDIM/2);
			player.setPosX(rightTile.getX() - BATTLEFIELD_TILEDIM/2);
			System.out.println("BL");
		}
		else if(player.isTopCollision()) {		//TODO FIX
			player.setPosY(topTile.getY() + BATTLEFIELD_TILEDIM);
			player.setPosX(player.getPosX());
			System.out.println("T");
		}
		else if(player.isBottomCollision()) {	//TODO FIX
			player.setPosY(bottomTile.getY() - BATTLEFIELD_TILEDIM/2);
			player.setPosX(player.getPosX());
			System.out.println("B");
		}
		else if(player.isLeftCollision()) {
			player.setPosY(player.getPosY());
			player.setPosX(leftTile.getX() + BATTLEFIELD_TILEDIM);
			System.out.println("L");
		}
		else if(player.isRightCollision()) {
			player.setPosY(player.getPosY());
			player.setPosX(rightTile.getX() - BATTLEFIELD_TILEDIM/2);
			System.out.println("R");
		}
	}
	
	private void angleCollision(Player player,int playerSquareX, int playerSquareY ) {
		
		
		int[] topLeftSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] topRightSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
		int[] bottomLeftSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] bottomRightSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
		
		Tile topLeftTile = tiles.get(topLeftSquare[0]*MapMatrix.WIDTH + topLeftSquare[1]);
		Tile topRightTile = tiles.get(topRightSquare[0]*MapMatrix.WIDTH + topRightSquare[1]);
		Tile bottomLeftTile = tiles.get(bottomLeftSquare[0]*MapMatrix.WIDTH + bottomLeftSquare[1]);
		Tile bottomRightTile = tiles.get(bottomRightSquare[0]*MapMatrix.WIDTH + bottomRightSquare[1]);		
		
		if(!topLeftTile.isWalkable()) {
			player.setTopLeftCollision(topLeftTile.checkCollision(player));
			//System.out.println("top:" + topTile.getClass());
		}else {
			player.setTopLeftCollision(false);
		}
		if(!topRightTile.isWalkable()) {
			//System.out.println("bottom:" + bottomTile.getClass());
			player.setTopRightCollision(topRightTile.checkCollision(player));
		}else {
			player.setTopRightCollision(false);
		}
		if(!bottomLeftTile.isWalkable()) {
			player.setBottomLeftCollision(bottomLeftTile.checkCollision(player));
			//System.out.println("left:" + leftTile.getClass());
		}else {
			player.setBottomLeftCollision(false);
		}
		if(!bottomRightTile.isWalkable()) {
			player.setBottomRightCollision(bottomRightTile.checkCollision(player));
			//System.out.println("right:" + rightTile.getClass());
		}else {
			player.setBottomRightCollision(false);
		}
		
		
		
//		if(player.isTopLeftCollision()) {		
//			player.setPosY();
//			player.setPosX();
//		}
//		else if(player.isTopRightCollision()) {
//			player.setPosY();
//			player.setPosX();
//		}
//		else if(player.isBottomLeftCollision()) {
//			player.setPosY();
//			player.setPosX();
//		}
//		else 
		
		if(player.isBottomRightCollision()) {
			player.setPosY(bottomRightTile.getY() - (BATTLEFIELD_TILEDIM * (Math.sqrt(2)+ 1)/4) );
			player.setPosX(bottomRightTile.getX() - (BATTLEFIELD_TILEDIM * (Math.sqrt(2)+ 1)/4) );
		}
	}
}
