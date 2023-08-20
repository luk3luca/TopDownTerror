package it.unibs.bot;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Stack;

import it.unibs.mainApp.*;

public class Bot {
	private static final int CENTER_HEIGHT = Battlefield.BATTLEFIELD_HEIGHT / 2;
	private static final int CENTER_WIDTH = Battlefield.BATTLEFIELD_WIDTH / 2;
	
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
	
	
	private int directionalX = 0;
	private int directionalY = 0;
	private double pAngle;
	
	
	private double oldDesiredDistance;
	
	
	private int playerSquareX;
	private int playerSquareY;
	private boolean playerInRange = false;
	private Player closerPlayer;
	
	
	
	private AStar astar;
	private Stack<Node> path;
	private int oldPlayerSquareX;
	private int oldPlayerSquareY;
	private int targetSquareX;
	private int targetSquareY;
	private int oldTargetSquareX;
	private int oldTargetSquareY;
	
	private Node nextNode;
	private int nextRow;
	private int nextCol;
	private double nextX;
	private double nextY;
	
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
		
		setSquareTarget();
	}
	
		
	/*
	 * TODO:
	 * check players in range 	+
	 * check direction			+
	 * 		check collisons
	 * set directional speed	
	 * set angular speed,and rotation control
	 * check ammo: reload if no left (or after time) 
	 * check player in gun range	+
	 * 		shoot
	 * 		random movement with player in range
	 * 		if player comes closer backup + random movement
	 * */
	public void stepNext(){
		//checkPlayerInRange();
		
		findTarget();
		
		calculateDirection();
		setSpeed();
		setRotation();
		
		//collision();
		//shootTarget();
	}
	
	/*
	 * TODO
	 * IN QUESTO MOMENTO IL BOT NON SALTELLA PIU QWUANDO SEGUE UN PLAYER
	 * FIXARE GUN RANGE
	 * SISTEMARE IL PRIMO IF SETTANDO SOLO TARGETX-Y E TIRARE FUORI DX-DY
	 * 
	 */
	
	private void setSquareTarget() {
		playerSquareX = (int)((p.getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		playerSquareY = (int)((p.getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		
		oldPlayerSquareX = playerSquareX;
		oldPlayerSquareY = playerSquareY;
		
		targetSquareX = (int)((targetX + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		targetSquareY = (int)((targetY + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		
		oldTargetSquareX = targetSquareX;
		oldTargetSquareY = targetSquareY;
		
		// Initialize first path
		astar = new AStar(playerSquareX, playerSquareY, targetSquareX, targetSquareY);
		path = astar.generatePath();
		nextNode();
	}
	
	// Change to next node to reach
	//TODO: add random coordinates to reach within the square
	private void nextNode() {
		nextNode = path.peek();
		nextCol = nextNode.getCol();
		nextRow = nextNode.getRow();
		
		nextX = nextCol * Battlefield.BATTLEFIELD_TILEDIM + Battlefield.BATTLEFIELD_TILEDIM/4;
		nextY = nextRow * Battlefield.BATTLEFIELD_TILEDIM + Battlefield.BATTLEFIELD_TILEDIM/4;
		
		System.out.println("next: (" + nextCol + ", " + nextRow + ")");
	}
	
	// Find best path to target
	//TODO: (?) use a different square than the one of the target
	private void findTarget() {
		playerSquareX = (int)((p.getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		playerSquareY = (int)((p.getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);

		if(playerInRange) {			
			targetX = closerPlayer.getPosX();
			targetY = closerPlayer.getPosY();		}
		else {
			targetX = CENTER_WIDTH;
			targetY = CENTER_HEIGHT;
		}

		targetSquareX = (int)((targetX + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		targetSquareY = (int)((targetY + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);

		//TODO: only change if target is different
		//if(playerSquareX != oldPlayerSquareX || playerSquareY != oldPlayerSquareY || targetSquareX != oldTargetSquareX || targetSquareY != oldTargetSquareY) {
		if(targetSquareX != oldTargetSquareX || targetSquareY != oldTargetSquareY) {
			astar = new AStar(playerSquareX, playerSquareY, targetSquareX, targetSquareY);
			path = astar.generatePath();
			nextNode();
		}
		
		if(playerSquareX == nextCol && playerSquareY == nextRow) {
			if(playerSquareX != targetSquareX && playerSquareY != targetSquareY)
				path.pop();
			nextNode();
		}

		oldPlayerSquareX = playerSquareX;
		oldPlayerSquareY = playerSquareY;
		oldTargetSquareX = targetSquareX;
		oldTargetSquareY = targetSquareY;
	}
	
	// calculate direction to point to
	private void calculateDirection() {			
		this.dx = nextX - p.getPosX();
		this.dy = nextY - p.getPosY();

		//			System.out.println("target: (" + targetX + ", " + targetY + ")");
		//			System.out.println("position: (" + p.getPosX() + ", " + p.getPosY() + ")");

		//			this.dx = targetX - p.getPosX();
		//			this.dy = targetY - p.getPosY();
		//			System.out.println("dx: " + dx);
		//			System.out.println("dy: " + dy);

		magnitude = Math.sqrt(dx * dx + dy * dy);
		normalizedDx = dx / magnitude;
		normalizedDy = dy / magnitude;

		// horizontal angle between bot x axis and the targeted player
		targetAngle = Math.atan2(normalizedDy, normalizedDx);
		// double angleDegrees = Math.toDegrees(targetAngle);
		pAngle = splitCircleRotation(p.getAngle());

		this.directionalX = mapDirection(dx);
		this.directionalY = mapDirection(dy);
	}
	
//	// calculate direction to point to
//	private void calculateDirection() {
//		if(playerInRange) {
//			//keepAtGunRange();
//			//sum distance * normalized
//			targetX = closerPlayer.getPosX();
//			targetY = closerPlayer.getPosY();
//		}
//		else {
//			targetX = CENTER_WIDTH;
//			targetY = CENTER_HEIGHT;
//			// TODO if player is in the middle, choose a random direction
//		}
//		
//		this.dx = targetX - p.getPosX();
//        this.dy = targetY - p.getPosY();
//		
////		System.out.println("target: (" + targetX + ", " + targetY + ")");
////		System.out.println("position: (" + p.getPosX() + ", " + p.getPosY() + ")");
//		        
////		this.dx = targetX - p.getPosX();
////		this.dy = targetY - p.getPosY();
////		System.out.println("dx: " + dx);
////		System.out.println("dy: " + dy);
//        
//        magnitude = Math.sqrt(dx * dx + dy * dy);
//        normalizedDx = dx / magnitude;
//        normalizedDy = dy / magnitude;
//
//        // horizontal angle between bot x axis and the targeted player
//        targetAngle = Math.atan2(normalizedDy, normalizedDx);
//        // double angleDegrees = Math.toDegrees(targetAngle);
//        pAngle = splitCircleRotation(p.getAngle());
//        
//        this.directionalX = mapDirection(dx);
//        this.directionalY = mapDirection(dy);
//	}
	
	// map directional value to -1, +1, 0
	private int mapDirection(double value) {
		if (value < 0)
			return -1;
		else if (value == 0)
			return 0;
		else
	    	return 1;
	}
	
	// check for player in range using matrix position in the visible grid of a player + 1
	private boolean checkPlayerInRange() {
		playerSquareX = (int)((p.getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		playerSquareY = (int)((p.getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		
//		System.out.println("position: (" + p.getPosX() + ", " + p.getPosY() + ")");
//		System.out.println("square: (" + playerSquareX + ", " + playerSquareY + ")");
		
		int range = 7;
		int lowX = (playerSquareX - range > 0 ? playerSquareX - range : 0);
		int lowY = (playerSquareY - range > 0 ? playerSquareY - range : 0);
		int topX = (playerSquareX + range < MapMatrix.WIDTH ? playerSquareX + range : MapMatrix.WIDTH - 1);
		int topY = (playerSquareY + range < MapMatrix.HEIGHT ? playerSquareY + range : MapMatrix.HEIGHT - 1);
				
		boolean pInRange = false;
		
		for(int i = 0; i < 6; i++) {
			if(i == pId)
				continue;
			
			int psX = (int)((player[i].getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
			int psY = (int)((player[i].getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
			
			double maxDistance = 500;
			
			if(psX > lowX && psX < topX) {
				//System.out.println("in x: " + i);
				if(psY > lowY && psY < topY) {
					//System.out.println("in y: " + i);
					double distanceX = p.getPosX() - player[i].getPosX();
					double distanceY = p.getPosY() - player[i].getPosY();
					double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
					//System.out.println("distance:" + distance);
					
					if(distance < maxDistance) {
						maxDistance = distance;
						closerPlayer = player[i];
						pInRange = true;
					}
				}
			}
		}
		
		playerInRange = pInRange;
		//System.out.println(playerInRange);
		return playerInRange;
	}
	
	private void keepAtGunRange() {
		double rangeCut = 0.8;
		double gunRange = p.getGun().getRange() * Battlefield.BATTLEFIELD_TILEDIM;

		double desiredDistance = (gunRange * rangeCut);
		//double deadzone = 4.0;
		
		targetX = targetX + desiredDistance * (-directionalX);
		targetY = targetY + desiredDistance * (-directionalY);
		//if(Math.abs(desiredDistance - oldDesiredDistance) > deadzone) {
//		if(true) {
//	        // current and desired distances	        
//	        if (directionalX == 1) {
//		        targetX -= desiredDistance;
//		    } else if (directionalX == -1) {
//		        targetX += desiredDistance;
//		    }
//		    
//		    if (directionalY == 1) {
//		        targetY -= desiredDistance;
//		    } else if (directionalY == -1) {
//		        targetY += desiredDistance;
//		    }    
////		    targetX += desiredDistance * normalizedDx;
////		    targetY += desiredDistance * normalizedDy;
////		    
////		    System.out.println("x offset: " + targetX);
//
//		    //System.out.println("target gr: (" + targetX + ", " + targetY + ")");
//
//		}
		//System.out.println(gunRange);
		//System.out.println(gunRange*rangeCut);
		//oldDesiredDistance = desiredDistance;
	}
	
	// set directional speed
	private void setSpeed() {
		//System.out.println("direction: (" + directionalX + ", " + directionalY + ")");
		switch(directionalX) {
			case 1:
				p.setXSpeed(Player.DEFAULT_X_SPEED);
				break;
			case -1:
				p.setXSpeed(-Player.DEFAULT_X_SPEED);
				break;
			case 0:
				p.setXSpeed(0);
				//p.setXSpeed(p.getXSpeed());
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
				//p.setXSpeed(p.getXSpeed());
				break;
			default:
				break;
		}
	}
	
	// set player rotation angle in [-pi,pi]
	public double splitCircleRotation(double angle) {
        double normalizedAngle = p.getAngle() % (2 * Math.PI);
        
        normalizedAngle -= Math.PI;

        if (normalizedAngle < -Math.PI) {
            normalizedAngle += 2 * Math.PI;
        }
        
        return normalizedAngle;
    }
	
	// check closer delta
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
	
    // set rotation velocity based on closer way to reach the target
    //TODO: change targetAngle value, needs to be fixed to the end target, now is on the next tile target
	public void setRotation() {
		int rotationDirection = calculateRotationDirection(pAngle, targetAngle);
        
//		System.out.println("norm p pointer: " + pAngle);
//		System.out.println("norm t pointer: " + targetAngle);
        
        double rotationThreshold = Math.toRadians(5); // 5 degrees
        
        if (Math.abs(targetAngle - pAngle) <= rotationThreshold) {
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
	 * target is player and is in range
	 * player distance is lower than gun range
	 * pointer is on the target
	 */
	private void shootTarget() {
		if(playerInRange && checkPlayerInGunRange() && pointerOnTarget()) {
			p.shooting();
		}
	}
	
	/*
	 * If the pointer is on the target the sum of the angles is 3.14
	 * Tolerance let bot shoot while the pointer is not in the middle
	 */
	private boolean pointerOnTarget() {
		double angleSum = Math.abs(pAngle) + Math.abs(targetAngle);
		double tolerance = 0.07;
		
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
		if(p.checkAmmo())
			p.reloadAmmo();
	}

	//useless with pathfinding
	private void checkWalls() {
		int[] topSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX};
		int[] bottomSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX};
		int[] leftSquare = {playerSquareY, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] rightSquare = {playerSquareY, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
		int[] topLeftSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] topRightSquare = {playerSquareY - 1 > 0 ? playerSquareY - 1 : 0, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
		int[] bottomLeftSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX - 1 > 0 ? playerSquareX - 1 : 0};
		int[] bottomRightSquare = {playerSquareY + 1 < MapMatrix.HEIGHT ? playerSquareY + 1 : MapMatrix.HEIGHT - 1, playerSquareX + 1 < MapMatrix.WIDTH ? playerSquareX + 1 : MapMatrix.WIDTH - 1};
	
		Tile topTile = tiles.get(topSquare[0]*MapMatrix.WIDTH + topSquare[1]);
		Tile bottomTile = tiles.get(bottomSquare[0]*MapMatrix.WIDTH + bottomSquare[1]);
		Tile leftTile = tiles.get(leftSquare[0]*MapMatrix.WIDTH + leftSquare[1]);
		Tile rightTile = tiles.get(rightSquare[0]*MapMatrix.WIDTH + rightSquare[1]);
		Tile topLeftTile = tiles.get(topLeftSquare[0]*MapMatrix.WIDTH + topLeftSquare[1]);
		Tile topRightTile = tiles.get(topRightSquare[0]*MapMatrix.WIDTH + topRightSquare[1]);
		Tile bottomLeftTile = tiles.get(bottomLeftSquare[0]*MapMatrix.WIDTH + bottomLeftSquare[1]);
		Tile bottomRightTile = tiles.get(bottomRightSquare[0]*MapMatrix.WIDTH + bottomRightSquare[1]);
		
		if(collision()) {
			if(directionalX == 0) {
				if(directionalY == 1) {		//bottom wall
					//check top
					//check left or right
					//check bottom left or bottom right
				}
				if(directionalY == -1) {	//top wall
					//check bottom
					//check left or right
					//check top left or top right
				}
			}
			if(directionalY == 0) {
				if(directionalX == 1) {
					//check right
					//check top or bottom
					//check top right or bottom right
				}
				if(directionalX == -1) {
					//check left
					//check top or bottom
					//check top left or bottom left				
				}
			}
			if(directionalX == 1) {
				if(directionalY == 1) {		//to br
					//check top or left
					//check top right or bottom left
				}
				if(directionalY == -1) {	//to tr
					//check bottom or right
					//check top left or bottom right
				}
			}
			if(directionalX == -1) {
				if(directionalY == 1) {		//to bl
					//check top or right
					//check top left or bottom right
				}
				if(directionalY == -1) {	//to tl
					//check bottom or right
					//check bottom left or top right
				}
			}
		}
	
	}
	
	private boolean collision() {
		
		if(oldPosX == p.getPosX() && oldPosY == p.getPosY()) {
			System.out.println("collison");
			
			return true;
		}
		
		oldPosX = p.getPosX();
		oldPosY = p.getPosY();
		
		return false;
	}
	
	private void pathFinding() {
		
	}
	
}
