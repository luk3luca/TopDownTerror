package it.unibs.mainApp;

import java.awt.Color;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.Timer;

import it.unibs.bot.Bot;

//MODEL
public class Battlefield extends BaseModel {                                
	public static final int BATTLEFIELD_TILEDIM = 32 * 2 ;
	public static final int BATTLEFIELD_WIDTH = BATTLEFIELD_TILEDIM * (MapMatrix.WIDTH + 1);
	public static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_TILEDIM * (MapMatrix.HEIGHT + 2);
		
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public ArrayList<Bullet> bullet = new ArrayList<>();
	public ArrayList<Tile> wallsAndSpawn = new ArrayList<>();
	public T_Spawn[] spawns = new T_Spawn[6];
	public Player[] player = new Player[6];
	private int[][] mapMatrix = MapMatrix.getMatrix();
	private Timer gameTimer;
	private int realPlayer;
	
	// TEST BOT
	private int nBot;
	private Bot bot1;
	private ArrayList<Bot> bot = new ArrayList<>();
	
	private boolean gameOver = false;
	public boolean isGameOver() {return gameOver;}
	public void stopGame() {gameTimer.stop();}
	
	public Battlefield(int realPlayer) {
		this.realPlayer = realPlayer;
		nBot = player.length - realPlayer;
		
		buildMap();
		buildPlayer();
		buildBot();
		gameTimer = new Timer(20, e ->{
				try {
					stepNext();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		});
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
						int nC = getSpawnColor(y, x);						
						T_Spawn s2 = buildTransparentSpawn(y, x, BATTLEFIELD_TILEDIM, nC);
						tiles.add(s2);
						wallsAndSpawn.add(s2);
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
	
	// TEST BOT BUILD
	private void buildBot() {
		int id = player.length - nBot;
		//bot1 = new Bot(player[id], player, id, tiles);
		
		for(int i = id; i < player.length; i++) {
			bot.add(new Bot(player[i], player, i, tiles));
			player[i].setName("BOT" + (i+1));
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
	
	private T_Spawn buildTransparentSpawn(int y, int x, int tileDim, int spawnCounter) {
		Color c = TeamColors.getColorAlpha(spawnCounter);
		return new T_Spawn(y * tileDim, x * tileDim, tileDim, true, c);
	}
	
	private int getSpawnColor(int y, int x) {
		if(y < MapMatrix.SPAWN_H + 1) {
			if(x < MapMatrix.SPAWN_W + 1)
				return 1;
			else if(x > MapMatrix.WIDTH/2 - 2 && x < MapMatrix.WIDTH/2 + 1)
				return 2;
			else if(x > MapMatrix.WIDTH - MapMatrix.SPAWN_W - 2)
				return 3;
		} else if(y > MapMatrix.SPAWN_H - 2) {
			if(x < MapMatrix.SPAWN_W + 1)
				return 4;
			else if(x > MapMatrix.WIDTH/2 - 2 && x < MapMatrix.WIDTH/2 + 1)
				return 5;
			else if(x > MapMatrix.WIDTH - MapMatrix.SPAWN_W - 2)
				return 6;
		}
		return 1;
	}
	
	private void addShot(Player p) throws InterruptedException{
		if(p.isShoot() && p.shoot()) {
			System.out.println(p.getName() + " shot - " + p.getAmmoLeft());
			bullet.add(new Bullet(p, p.getGun()));
		}
	}
	
	/*----------------GESTIONE COLLISIONI----------------*/
	
	public void startGame() {
		gameTimer.start();
	}

	
	public void stepNext() throws InterruptedException {
		//TEST BOT
		//bot1.stepNext();
		for(Bot b: bot)
			b.stepNext();

		for(Player p: player) {
			p.stepNext();
			addShot(p);
		}

		bullet.forEach((b)->b.stepNext());

		checkCollision();
		checkWin();

		this.fireValuesChange();
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
		for(int i = 0; i < player.length; i++) {
			// Riquadro in cui si trova il centro del player
			int playerSquareX = (int)((player[i].getPosX() + BATTLEFIELD_TILEDIM/4) / BATTLEFIELD_TILEDIM);
			int playerSquareY = (int)((player[i].getPosY() + BATTLEFIELD_TILEDIM/4) / BATTLEFIELD_TILEDIM);
			//System.out.println("square BTF: (" + playerSquareX + ", " + playerSquareY + ")");
			player[i].resetCollision();
			
			bulletPlayerCollision();
			bullletWallsCollision();
	        removeDust();
	        
	        crossCollision(player[i], playerSquareX, playerSquareY);
			angleCollision(player[i],playerSquareX, playerSquareY);		
//			checkGunRangeCollision(player[i], playerSquareX, playerSquareY, i);
			checkPlayerCollision(player[i]);	
		}
	}
    
    private void checkPlayerCollision(Player p1) {
    	for (Player p2 : player) {
    		if (p1 != p2) {
    			if(p1.checkCollision(p2)) {
    				double dx = p1.getCenterX() - p2.getCenterX();
    				double dy = p1.getCenterY() - p2.getCenterY();
    				double tetha = Math.atan2(dy,dx) + Math.PI; // p1<-->p2 tetha = 0, con p1 sopra p2 tetha = 90

    				p1.setPosX(p1.getPosX() - Player.M_VELOCITY *Math.cos(tetha));
    				p1.setPosY(p1.getPosY() - Player.M_VELOCITY * Math.sin(tetha));	
    			}
    		}
    	}
    }
    
    // Controllo collisione bullet-muro
    private void bullletWallsCollision() {
    	for(Tile w:wallsAndSpawn) {
    		bullet.forEach(o -> {
    			if (w.checkCollision(o)) {
    				o.collided();
    			}             
    		});
    	}
    }
    
    // Controllo collisione bullet-player, con damage
    private void bulletPlayerCollision() {
    	for (Player p: player) {
    		bullet.forEach(o ->{
    			// p == player che ha sparato, o.getPlayer() == player colpito. 
    			if(o.getPlayer()!= p && p.checkCollision(o)) { 
    				o.collided();
    				p.hitted(o.getGun(), o.getPlayer());
    			}
    		});
    		removeDust();
		}
    }
    
	/*-------PLAYER <--> WALLS-------*/
	// Controllo delle collisioni sui muri sopra, sotto, destra, sinistra del player
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
			//player.setPosX(player.getPosX());�
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
	private void checkGunRangeCollision(Player player, int playerSquareX, int playerSquareY, int n) {
		int range = (int)Math.ceil(player.getGun().getRange());
		int lowerY = Math.max(0, playerSquareY - range - 1);
		int lowerX = Math.max(0, playerSquareX - range - 1);
		int upperY = Math.min(MapMatrix.HEIGHT, playerSquareY + range + 1);
		int upperX = Math.min(MapMatrix.WIDTH, playerSquareX + range + 1);
		
		Point2D playerP = new Point2D.Double(player.getCenterX(), player.getCenterY());
		ArrayList<Point2D> collisionsP = new ArrayList<>();

		for(int i = lowerY; i < upperY; i++) {
			for(int j = lowerX; j < upperX; j++) {
				Tile t = tiles.get(i*MapMatrix.WIDTH + j);

				if(!t.isWalkable()) {
					if(t.checkCollision(player.getGun())) {
						Area area1 = new Area(player.getGun().getShape());
						Area area2 = new Area(t.getShape());
						area1.intersect(area2);

						PathIterator path = area1.getPathIterator(null);
						while (!path.isDone()) {
							double[] coords = new double[6];
							int type = path.currentSegment(coords);
							if (type == PathIterator.SEG_LINETO) {
								Point2D point = new Point2D.Double(coords[0], coords[1]);
								collisionsP.add(point);
							}
							path.next();
						}
					}
					else {
					    player.getGun().resetRange();
					}
				}
			}
		}

		if(collisionsP.size() > 0) {
		    Point2D closestPoint = findClosestPoint(collisionsP, playerP);
		    double newRange = closestPoint.distance(playerP);
		    player.getGun().setRange(newRange / BATTLEFIELD_TILEDIM);
		}
	}

	private Point2D findClosestPoint(ArrayList<Point2D> collisionsP, Point2D targetPoint) {
	    Point2D closestPoint = null;
	    double closestDistance = Double.MAX_VALUE;

	    for (Point2D point : collisionsP) {
	        double distance = targetPoint.distance(point);
	        
	        if (distance < closestDistance) {
	            closestDistance = distance;
	            closestPoint = point;
	        }
	    }

	    return closestPoint;
	}
	
	
////////////// CHECK WIN   ///////////////
	
	private void checkWin() {
		for (Player py : player) {
			if (py.getKills() == 20) {
				gameOver = true;
				stopGame();
			}
		}
	}
	
	
	
	
	
		
}
