package it.unibs.mainApp;

import java.awt.event.KeyEvent;

public class Bot {
	private static final int CENTER_HEIGHT = Battlefield.BATTLEFIELD_HEIGHT / 2;
	private static final int CENTER_WIDTH = Battlefield.BATTLEFIELD_WIDTH / 2;
	
	public Player p;
	public int pId;
	public Player[] player = new Player[6];
	
	private double dx;
	private double dy;
	private double targetX;
	private double targetY;
	private double targetAngle;
	
	private int directionalX;
	private int directionalY;
	
	private boolean playerInRange = false;
	private Player closerPlayer;
	
	
	public Bot(Player p, Player[] players, int pId) {
		this.p = p;
		this.pId = pId;
		this.player = players;
		this.targetX = CENTER_WIDTH;
		this.targetY = CENTER_HEIGHT;
	}
	
		
	/*
	 * TODO:
	 * check players in range
	 * check direction
	 * 		check collisons
	 * set directional speed
	 * set angular speed,and rotation control
	 * check ammo: reload if no left (or after time) 
	 * check player in gun range
	 * 		shoot
	 * 		random movement with player in range
	 * */
	public void stepNext(){
		checkPlayerInRange();
		calculateDirection();
		setSpeed();
		setRotation();
	}
	
	// calculate direction to point to
	public void calculateDirection() {
		if(playerInRange) {
			targetX = closerPlayer.getPosX();
			targetY = closerPlayer.getPosY();
		}
		else {
			targetX = CENTER_WIDTH;
			targetY = CENTER_HEIGHT;
			// TODO if player is in the middle, choose a random direction
		}
		
		System.out.println("target: (" + targetX + ", " + targetY + ")");
		//System.out.println("position: (" + p.getPosX() + ", " + p.getPosY() + ")");
		
		if(playerInRange) {
			System.out.println("in range");
			keepAtGunRange();
		}
		
        this.dx = targetX - p.getPosX();
        this.dy = targetY - p.getPosY();

        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = dx / magnitude;
        double normalizedDy = dy / magnitude;

        // horizontal angle, needs to be inverted
        targetAngle = -Math.atan2(normalizedDy, normalizedDx);
        //double angleDegrees = Math.toDegrees(targetAngle);
        this.directionalX = mapDirection(dx);
        this.directionalY = mapDirection(dy);
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
	
	// check for player in range using matrix position in the visible grid of a player + 1
	private boolean checkPlayerInRange() {
		int playerSquareX = (int)((p.getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		int playerSquareY = (int)((p.getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
		
//		System.out.println("position: (" + p.getPosX() + ", " + p.getPosY() + ")");
//		System.out.println("square: (" + playerSquareX + ", " + playerSquareY + ")");
		
		int range = 7;
		int lowX = (playerSquareX - range > 0 ? playerSquareX - range : 0);
		int lowY = (playerSquareY - range > 0 ? playerSquareY - range : 0);
		int topX = (playerSquareX + range < MapMatrix.WIDTH ? playerSquareX + range : MapMatrix.WIDTH - 1);
		int topY = (playerSquareY + range < MapMatrix.HEIGHT ? playerSquareY + range : MapMatrix.HEIGHT - 1);
		
//		System.out.println("limits: (" + lowX + ", " + lowY + ")- (" + topX + ", " + topY + ")");
		
		boolean pInRange = false;
		
		for(int i = 0; i < 6; i++) {
			if(i == pId)
				continue;
			
			int psX = (int)((player[i].getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
			int psY = (int)((player[i].getPosY() + Battlefield.BATTLEFIELD_TILEDIM/4) / Battlefield.BATTLEFIELD_TILEDIM);
			
//			if(i == 4)
//				System.out.println("player1: (" + psX + ", " + psY + ")");
			
			double maxDistance = 500;
			
			if(psX > lowX && psX < topX) {
				System.out.println("in x: " + i);
				if(psY > lowY && psY < topY) {
					System.out.println("in y: " + i);
					double distanceX = p.getPosX() - player[i].getPosX();
					double distanceY = p.getPosY() - player[i].getPosY();
					double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
					System.out.println("distance:" + distance);
					
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
		
		System.out.println(gunRange);
		System.out.println(gunRange*rangeCut);
		
		if(directionalX == 1 && directionalY == 1) {
			targetX -= (gunRange * rangeCut);
			targetY -= (gunRange * rangeCut);
		}
		else if(directionalX == -1 && directionalY == 1) {
			targetX += (gunRange * rangeCut);
			targetY -= (gunRange * rangeCut);
		}
		else if(directionalX == -1 && directionalY == -1) {
			targetX += (gunRange * rangeCut);
			targetY += (gunRange * rangeCut);
		}
		else if(directionalX == 1 && directionalY == -1) {
			targetX -= (gunRange * rangeCut);
			targetY += (gunRange * rangeCut);
		}
		else if(directionalX == 0 && directionalY == 1) {
			targetY -= (gunRange * rangeCut);
		}
		else if(directionalX == 0 && directionalY == -1) {
			targetY += (gunRange * rangeCut);
		}
		else if(directionalX == 1 && directionalY == 0) {
			targetX -= (gunRange * rangeCut);
		}
		else if(directionalX == -1 && directionalY == 0) {
			targetX += (gunRange * rangeCut);
		}
	}
	
	// set directional speed
	private void setSpeed() {
		System.out.println("direction: (" + directionalX + ", " + directionalY + ")");
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
	public double splitCircleRotation() {
        double normalizedAngle = p.getAngle() % (2 * Math.PI);
        
        if (normalizedAngle <= Math.PI) {
            return normalizedAngle;
        } else {
            return normalizedAngle - 2 * Math.PI;
        }
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
	// TODO: controllare se invertire +1 e -1
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
	public void setRotation() {
		double pAngle = splitCircleRotation();
		int rotationDirection = calculateRotationDirection(pAngle, targetAngle);
		switch(rotationDirection) {
			case 1:
				p.setR_velocity(Player.R_VELOCITY);
				break;
			case 0:
				p.setR_velocity(0);
				break;
			case -1:
				p.setR_velocity(-Player.R_VELOCITY);
				break;
			default:
				break;
		}
	}

	
	// checks ammo to reload
	private void checkAmmo() throws InterruptedException {
		if(p.checkAmmo())
			p.reloadAmmo();
	}

}
