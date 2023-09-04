package it.unibs.bot;

import java.awt.event.KeyEvent;
import java.nio.file.spi.FileSystemProvider;
import java.util.*;

import it.unibs.mainApp.*;

public class Bot {
	private static final int CENTER_HEIGHT = Battlefield.BATTLEFIELD_HEIGHT/2 - Battlefield.BATTLEFIELD_TILEDIM;
	private static final int CENTER_WIDTH = Battlefield.BATTLEFIELD_WIDTH/2 - Battlefield.BATTLEFIELD_TILEDIM;
	
	private Player p;
	private int pId;
	private Player[] player = new Player[6];
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	private double dx;
	private double dy;
	private double targetX;
	private double targetY;
	private double targetAngle;
	private double magnitude;
	private double normalizedDx;
	private double normalizedDy;
	
	// Player direction 
	private int directionalX = 0;
	private int directionalY = 0;
	private double pAngle;
	
	// POinter direction
	private double pointerX;
	private double pointerY;
	
	private double oldDesiredDistance;
	
	// Player position
	private int playerSquareX;
	private int playerSquareY;
	private boolean playerInRange = false;
	private Player closerPlayer;
	
	
	// Pathfinding
	private Stack<Node> path;
	private Path astarPath;
	
	private int oldPlayerSquareX;
	private int oldPlayerSquareY;
	private int targetSquareX;
	private int targetSquareY;
	private int oldTargetSquareX;
	private int oldTargetSquareY;
		
	private boolean resetPath = true;

	// next node in path
	private Node nextNode;
	private int nextRow;
	private int nextCol;
	private double nextX;
	private double nextY;
	private int offsetX = Battlefield.BATTLEFIELD_TILEDIM/4;
	private int offsetY = Battlefield.BATTLEFIELD_TILEDIM/4;

	
	private double oldPosX;
	private double oldPosY;
	
	public Bot(Player p, Player[] players, int pId, ArrayList<Tile> tiles) {
		this.p = p;
		this.pId = pId;
		this.player = players;
		this.targetX = CENTER_WIDTH;
		this.targetY = CENTER_HEIGHT;
		this.tiles = tiles;
		// TEST GUN RANGE
		this.oldDesiredDistance = 0;
		
		this.oldPosX = p.getPosX();
		this.oldPosY = p.getPosY();
		
		setRandomTarget(4, 3);
	}
		
	/*
	 * TODO:
	 * check players in range 	
	 * check direction			+
	 * 		check collisons
	 * set directional speed	+
	 * set angular speed,and rotation control	+
	 * check ammo: reload if no left (or after time) +
	 * check player in gun range	+
	 * 		shoot					+
	 * 		random movement with player in range
	 * 		if player comes closer backup + random movement	+
	 * 
	 * set random gun
	 * 		respawn reset gun
	 * */
	
	public void stepNext() throws InterruptedException{
		// set player and target square
		setPlayerSquare();
		setTargetSquare();
		
		// check to reset the path
		if(resetPath || p.respawn) {
			// avoid not resetting target if getting killed by non targeted player
			if(p.respawn && !resetPath)
				setRandomTarget(12, 6);
			
			setupPath();
			resetPath = false;
			p.respawn = false;
			p.setRandomGun();
		}
		
		checkPlayerInRange();
		findTarget();
		calculateDirection();
		calculatePointerDirection();
		setSpeed();
		setRotation();
		
		shootTarget();
		checkAmmo();
	}
	
	private void setPlayerSquare() {
		playerSquareX = (int)((p.getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		playerSquareY = (int)((p.getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
	}
	
	private void setTargetSquare() {
		targetSquareX = (int)((targetX + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		targetSquareY = (int)((targetY + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
	}
	
	// Initialize first path
	private void setupPath() {
		oldPlayerSquareX = playerSquareX;
		oldPlayerSquareY = playerSquareY;
		
		oldTargetSquareX = targetSquareX;
		oldTargetSquareY = targetSquareY;

		generateNewPath();
	}
	
	private void generateNewPath() {
		//System.out.println(pId + " new path generation");
		//System.out.println("target: (" + targetSquareX + ", " + targetSquareY + ")");
		astarPath = new Path(playerSquareX, playerSquareY, targetSquareX, targetSquareY);
		astarPath.generatePath();
		path = astarPath.getPath();
		try {
			nextNode();
		} catch (Exception e) {
		}
	}
	
	// Change to next node to reach
	private void nextNode() {
		try {
			nextNode = path.peek();
		} catch (Exception e) {
			//System.out.println(e);
		}
		
		nextCol = nextNode.getCol();
		nextRow = nextNode.getRow();
		
		if(nextCol != oldPlayerSquareX && nextRow != oldPlayerSquareY) {
			offsetX = getRandomOffset();
			offsetY = getRandomOffset();
		}
		
		nextX = nextCol * Battlefield.BATTLEFIELD_TILEDIM + offsetX;
		nextY = nextRow * Battlefield.BATTLEFIELD_TILEDIM + offsetY;	
	}
	
	// Find best path to target
	private void findTarget() {
		if(playerInRange) {			
			targetX = closerPlayer.getPosX();
			targetY = closerPlayer.getPosY();		
		}
				
		setTargetSquare();

		// If target changes it generates a new path
		if(targetSquareX != oldTargetSquareX || targetSquareY != oldTargetSquareY) {
			generateNewPath();
		}
		
		// Node gets popped if it's not the target node
		if(playerSquareX == nextCol && playerSquareY == nextRow) {
			if(!targetReached()) {
				/*
				 * a volte lo stack e' vuoto e da errore, cosi non si interrompe
				 * penso succeda perche' player e bot si trovano nella stessa cella
				 * e il getPath rimuove il nodo di parternza (coinciderebbe con arrivo)
				 * quindi stack resta vuoto
				 */
				try {
					path.pop();
				} catch (Exception e) {
					//System.out.println(e);
				}
			}
		}

		oldPlayerSquareX = playerSquareX;
		oldPlayerSquareY = playerSquareY;
		oldTargetSquareX = targetSquareX;
		oldTargetSquareY = targetSquareY;
		
		if(targetReached()) {
			if(!playerInRange) {
				setRandomTarget(12, 6);
				//generateNewPath();
			}
		}
		
		nextNode();
	}
			
	// random target around the center of the map
	private void setRandomTarget(int randX, int randY) {
		int centerX = 15;
		int centerY = 11;
		
		int sqX;
		int sqY;
		
		do {
			sqX = centerX + getRandomRange(randX);
			sqY = centerY + getRandomRange(randY);
		} while(!MapMatrix.isPavement(sqX, sqY));
		
		targetX = sqX * Battlefield.BATTLEFIELD_TILEDIM + getRandomOffset();
		targetY = sqY * Battlefield.BATTLEFIELD_TILEDIM + getRandomOffset();
	}
	
	// value between 0 and BATTLEFIELD_TILEDIM/2, random position inside the tile
	private int getRandomOffset() {
	    Random rand = new Random();
	    int lowerBound = 10;
	    int upperBound = Battlefield.BATTLEFIELD_TILEDIM / 2 - lowerBound;
	    return rand.nextInt(upperBound - lowerBound + 1) + lowerBound;
	}

	// random int between [-n;n]
	private int getRandomRange(int n) {
		Random random = new Random();
		
		return random.nextInt(2*n + 1) - n;
	}
	
	// Check if player reached target, avoid last path Node to get popped
	private boolean targetReached() {
		if(playerSquareX == targetSquareX && playerSquareY == targetSquareY) {
			return true;
		}
					
		return false;
	}
	
	// calculate direction to point to
	private void calculateDirection() {			
		this.dx = nextX - p.getPosX();
		this.dy = nextY - p.getPosY();

		int reverse = reverseSpeed();
		
		this.directionalX = reverse * mapDirection(dx);
		this.directionalY = reverse * mapDirection(dy);
	}
	
	// if enemy get closer bot reverse speed to escape
	private int reverseSpeed() {
		//double rangeCut = 0.25;
		//double gunRange = p.getGun().getRange() * Battlefield.BATTLEFIELD_TILEDIM;
		double rangeCut = 1;
		double gunRange = Battlefield.BATTLEFIELD_TILEDIM;

		double desiredDistance = (gunRange * rangeCut);
		
		if(checkPlayerInGunRange()) {	
			if(magnitude < desiredDistance)
				return -1;
		}
		
		return 1;
	}
	
	// calculate pointer direction to the target
	private void calculatePointerDirection() {
		this.pointerX = targetX - p.getPosX();
        this.pointerY = targetY - p.getPosY();

        magnitude = Math.sqrt(pointerX * pointerX + pointerY * pointerY);
        normalizedDx = pointerX / magnitude;
        normalizedDy = pointerY / magnitude;

        // horizontal angle between bot x axis and the targeted player
        targetAngle = Math.atan2(normalizedDy, normalizedDx);
        // double angleDegrees = Math.toDegrees(targetAngle);
        pAngle = splitCircleRotation(p.getAngle());
	}
	
	// map directional value to -1, +1, 0
	private int mapDirection(double value) {
		if (value < 0)
			return -1;
		else if (value == 0)
			return 0;
		else
	    	return 1;
	}
	
	// check for player in range using matrix position in the visible grid of a player + 1 (7 squares)
	private boolean checkPlayerInRange() {
		int range = 7;
		int lowX = (playerSquareX - range > 0 ? playerSquareX - range : 0);
		int lowY = (playerSquareY - range > 0 ? playerSquareY - range : 0);
		int topX = (playerSquareX + range < MapMatrix.WIDTH ? playerSquareX + range : MapMatrix.WIDTH - 1);
		int topY = (playerSquareY + range < MapMatrix.HEIGHT ? playerSquareY + range : MapMatrix.HEIGHT - 1);

		boolean pInRange = false;
		double maxDistance = 500;		//7*64=448
		
		// cycle all players and find the closer target
		for(int i = 0; i < 6; i++) {
			if(i == pId)
				continue;

			int psX = (int)((player[i].getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
			int psY = (int)((player[i].getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);

			// avoid target on player getting pushed over a wall or camping players in spawn
			if(!MapMatrix.isPavement(psX, psY))
				continue;
			
			if(inRange(lowX, lowY, topX, topY, psX, psY)) {
				double distanceX = p.getPosX() - player[i].getPosX();
				double distanceY = p.getPosY() - player[i].getPosY();
				double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

				if(distance < maxDistance) {
					maxDistance = distance;
					closerPlayer = player[i];
					pInRange = true;
				}
			}
		}

		playerInRange = pInRange;
		return playerInRange;
	}
	
	// check if the player is in range
	private boolean inRange(int lowX, int lowY, int topX, int topY, int psX, int psY) {
		if((psX > lowX && psX < topX) && (psY > lowY && psY < topY))
			return true;
		
		return false;
		
	}
		
	// set directional speed
	private void setSpeed() {
		switch(directionalX) {
			case 1:
				p.setXSpeed(Player.DEFAULT_X_SPEED);
				break;
			case -1:
				p.setXSpeed(-Player.DEFAULT_X_SPEED);
				break;
			case 0:
				p.setXSpeed(0);
				break;
			default:
				break;
        }
		
		switch(directionalY) {
			case 1:
				p.setYSpeed(Player.DEFAULT_Y_SPEED);
				break;
			case -1:
				p.setYSpeed(-Player.DEFAULT_Y_SPEED);
				break;
			case 0:
				p.setYSpeed(0);
				break;
			default:
				break;
		}
	}
		
	// set player rotation angle between [-pi,pi]
	public double splitCircleRotation(double angle) {
		double normalizedAngle = p.getAngle() % (2 * Math.PI);
		normalizedAngle -= Math.PI;

		if (normalizedAngle < -Math.PI) {
			normalizedAngle += 2 * Math.PI;
		}

		return normalizedAngle;
	}

	// check closer delta angle for faster rotation
	public double shortestAngularDistance(double source, double target) {
		double delta = target - source;
		if (delta > Math.PI) {
			delta -= 2 * Math.PI;
		} else if (delta < -Math.PI) {
			delta += 2 * Math.PI;
		}
		return delta;
	}

	// calculate rotation direction
	public int calculateRotationDirection(double source, double target) {
		double angularDistance = shortestAngularDistance(source, target);

		if (angularDistance == 0)
			return 0;
		else if(angularDistance > 0)
			return 1;
		else
			return -1;
	}
	
	// set rotation velocity based on the closer way to reach the target, with tolerance
	public void setRotation() {
		int rotationDirection = calculateRotationDirection(pAngle, targetAngle);
		double rotationTolerance = Math.toRadians(5); // 5 degrees

		if (Math.abs(targetAngle - pAngle) <= rotationTolerance) {
			p.setRotation(0);
		} else {
			switch(rotationDirection) {
				case 1:
					p.setRotation(-Player.R_VELOCITY);
					break;
				case -1:
					p.setRotation(Player.R_VELOCITY);
					break;
				case 0:
					p.setRotation(0);
				default:
					break;
			}
		}
	}
	
	/*
	 * Player shooting conditions:
	 * 		target is player and is in range
	 * 		player distance is lower than gun range
	 * 		pointer is on the target
	 * 		player is not on a spawn tile
	 * 		not shooting at players inside spawn, player can have a little out of spawn and be hittable
	 * 		fix slow shooting
	 */
	private void shootTarget() {
		if(playerInRange && checkPlayerInGunRange() && pointerOnTarget() && MapMatrix.isPavement(playerSquareX, playerSquareY)) {
			p.shooting();
			//System.out.println("shoot");
		}
	}
	
	/*
	 * Check if pointer is on target with a 0.15 rad tolerance
	 * 		If the pointer is on the target the sum of the absolute value of the angles is 3.14
	 * 		Tolerance let bot shoot while the pointer is not in the middle
	 */
	private boolean pointerOnTarget() {
		double angleSum = Math.abs(pAngle) + Math.abs(targetAngle);
		double tolerance = 0.15;
		
		if(angleSum > (Math.PI - tolerance) && angleSum < (Math.PI + tolerance))
			return true;
		
		return false;
	}
	
	// If player distance is less than the gun range it can shoot
	private boolean checkPlayerInGunRange() {
		if(!playerInRange)
			return false;

		if(magnitude <= (p.getGun().getRange() * Battlefield.BATTLEFIELD_TILEDIM)) {
			return true;
		}
					
		return false;
	}

	// checks ammo to reload
	private void checkAmmo() throws InterruptedException {
		int ammoLeft = p.getAmmoLeft();
		int maxAmmo = p.getGun().getMaxAmmo();
		
		if(!p.checkAmmo()) {
			p.reloadAmmo();
			return;
		}
		
		if(!playerInRange) {
			int minAmmo = (maxAmmo/3) * 2;
			
			if(ammoLeft < minAmmo)
				p.reloadAmmo();
		}	
	}
	
}
