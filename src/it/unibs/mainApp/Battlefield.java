package it.unibs.mainApp;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Battlefield {                                
	public static final int BATTLEFIELD_TILEDIM = 64;
	public static final int BATTLEFIELD_WIDTH = BATTLEFIELD_TILEDIM * (MapMatrix.WIDTH + 1);
	public static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_TILEDIM * (MapMatrix.HEIGHT + 2);
	 
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	protected T_Spawn[] spawns = new T_Spawn[6];
	public Player[] player = new Player[6];
	protected ArrayList<Tile> wallsAndSpawn = new ArrayList<>();
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
						wallsAndSpawn.add(buildWall(y,x, BATTLEFIELD_TILEDIM));
						break;
					case 2:
						T_Spawn s = buildSpawn(y,x, BATTLEFIELD_TILEDIM, spawnCounter);
						tiles.add(s);
						wallsAndSpawn.add(s);
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
			Color c = TeamColors.getColorAlpha(i + 1);
			
			player[i] = new Player("player " + i , spawns[i], c);
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
	
	/*----------------GESTIONE COLLISIONI----------------*/
	public void stepNext() {
        //checkBorder();
        for (Bullet bullet : bullet) {
            bullet.stepNext();
        }
        checkCollision();
        bullletWallsCollision();
        removeDust();

    }
    private void removeDust() {
        ArrayList<Bullet> dust = new ArrayList<>();
 
        bullet.forEach(o -> {
            if (!o.isAlive()) {
                dust.add(o); 
            } 
        });
        
        dust.forEach(bullet::remove);

    } 
    
    private void checkCollision() {
		for(int i=0; i<player.length ; i++) {
			// Riquadro in cui si trova il centro del player
			int playerSquareX = (int)((player[i].getPosX() + BATTLEFIELD_TILEDIM/4) / BATTLEFIELD_TILEDIM);
			int playerSquareY = (int)((player[i].getPosY() + BATTLEFIELD_TILEDIM/4 )/ BATTLEFIELD_TILEDIM);

			player[i].resetCollision();
			crossCollision(player[i], playerSquareX, playerSquareY);
			angleCollision(player[i],playerSquareX, playerSquareY);		
			checkGunRangeCollision(player[i], playerSquareX, playerSquareY, i);
			
			
		}
	}
	// TODO COLLISIONI MOVING OBJECT <--> MOVING OBJECT 

    private void bullletWallsCollision() {
    	for(Tile w:wallsAndSpawn) {
    		bullet.forEach(o -> {
                if (w.checkCollision(o)) {
                	o.collided();
                } 
            });
    	}
    }
    
    
	/*-------PLAYER <--> WALLS-------*/
		
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
	
	//COLLISIONI GUN RANGE <--> WALLS
		// TODO fix controllo sull'angolo del player quando avviene collisione
		// TODO fix repaint top e left 
		private void checkGunRangeCollision(Player player, int playerSquareX, int playerSquareY, int n) {
			int range = (int)Math.ceil(player.getGun().getRange());
			int lowerY = Math.max(0, playerSquareY - range);
			int lowerX = Math.max(0, playerSquareX - range);
			int upperY = Math.min(MapMatrix.HEIGHT, playerSquareY + range + 1);
			int upperX = Math.min(MapMatrix.WIDTH, playerSquareX + range + 1);
			//System.out.println("(" + lowerX + "," + lowerY + ") ("+ upperX + "," + upperY + ")");
			Point2D playerP = new Point2D.Double(player.getPosX() + BATTLEFIELD_TILEDIM/4, player.getPosY() + BATTLEFIELD_TILEDIM/4);
			ArrayList<Point2D> collisionsP = new ArrayList<>();
			
			for(int i = lowerY; i < upperY; i++) {
				for(int j = lowerX; j < upperX; j++) {
					Tile t = tiles.get(i*MapMatrix.WIDTH + j);
					
					if(!t.isWalkable()) {
						player.getGun().setPlayerInfo(player.getPosX(), player.getPosY(), player.getAngle());
						if(t.checkCollision(player.getGun())) {
							Area area1 = new Area(player.getGun().getShape());
							Area area2 = new Area(t.getShape());
							area1.intersect(area2);
							//System.out.println(area1.isEmpty());
							
							PathIterator path = area1.getPathIterator(null);
					        while (!path.isDone()) {
					            double[] coords = new double[6];
					            int type = path.currentSegment(coords);
					            if (type == PathIterator.SEG_LINETO) {
					                Point2D point = new Point2D.Double(coords[0], coords[1]);
					                collisionsP.add(point);
					                //System.out.println("Intersection point: (" + (coords[0]) + ", " + (coords[1]) + ")");
					                //System.out.println("Intersection point: " + point);
					            }
					            path.next();
					        }
						}
					}
				}
			}
			
			if(collisionsP.size() > 0) {
				double newRange = findClosestPoint(collisionsP, playerP);
				player.getGun().setRange(newRange / BATTLEFIELD_TILEDIM);
			}
			else
				player.getGun().resetRange();
			//System.out.println(player.getGun().getRange());
		}
		
		private double findClosestPoint(ArrayList<Point2D> collisionsP, Point2D targetPoint) {
	        Point2D closestPoint = null;
	        double closestDistance = Double.MAX_VALUE;
	        
	        for (Point2D point : collisionsP) {
	            double distance = targetPoint.distance(point);
	            if (distance < closestDistance) {
	                closestDistance = distance;
	                closestPoint = point;
	            }
	        }
	        
	        return closestDistance;
	    }
	
}
