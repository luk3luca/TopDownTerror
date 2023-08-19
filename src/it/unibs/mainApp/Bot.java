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
	private double magnitude;
	private double normalizedDx;
	private double normalizedDy;
	
	private int directionalX;
	private int directionalY;
	private double pAngle;
	
	private double oldDesiredDistance;
	private boolean playerInRange = false;
	private Player closerPlayer;
	
	
	public Bot(Player p, Player[] players, int pId) {
		this.p = p;
		this.pId = pId;
		this.player = players;
		this.targetX = CENTER_WIDTH;
		this.targetY = CENTER_HEIGHT;
		
		// TEST GUN RANGE
		this.oldDesiredDistance = 0;
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
		checkPlayerInRange();
		calculateDirection();
		setSpeed();
		setRotation();
		shootTarget();
	}
	
	/*
	 * TODO
	 * IN QUESTO MOMENTO IL BOT NON SALTELLA PIU QWUANDO SEGUE UN PLAYER
	 * FIXARE GUN RANGE
	 * SISTEMARE IL PRIMO IF SETTANDO SOLO TARGETX-Y E TIRARE FUORI DX-DY
	 * 
	 */
	
	
	// calculate direction to point to
	public void calculateDirection() {
		if(playerInRange) {
			//keepAtGunRange();
			//sum distance * normalized
	        this.dx = closerPlayer.getPosX() - p.getPosX();
	        this.dy = closerPlayer.getPosY() - p.getPosY();
		}
		else {
			targetX = CENTER_WIDTH;
			targetY = CENTER_HEIGHT;
			// TODO if player is in the middle, choose a random direction
			
			this.dx = targetX - p.getPosX();
	        this.dy = targetY - p.getPosY();
		}
		
		if(playerInRange) {
			keepAtGunRange();
		}
		
//		System.out.println("target: (" + targetX + ", " + targetY + ")");
//		System.out.println("position: (" + p.getPosX() + ", " + p.getPosY() + ")");
		        
//        this.dx = targetX - p.getPosX();
//        this.dy = targetY - p.getPosY();
//        System.out.println("dx: " + dx);
//        System.out.println("dy: " + dy);
        
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
		
		double deadzone = 4.0;
		
		//if(Math.abs(desiredDistance - oldDesiredDistance) > deadzone) {
		if(true) {
	        // current and desired distances	        
	        if (directionalX == 1) {
		        targetX -= desiredDistance;
		    } else if (directionalX == -1) {
		        targetX += desiredDistance;
		    }
		    
		    if (directionalY == 1) {
		        targetY -= desiredDistance;
		    } else if (directionalY == -1) {
		        targetY += desiredDistance;
		    }    
//		    targetX += desiredDistance * normalizedDx;
//		    targetY += desiredDistance * normalizedDy;
//		    
//		    System.out.println("x offset: " + targetX);

		    //System.out.println("target gr: (" + targetX + ", " + targetY + ")");

		}
		//System.out.println(gunRange);
		//System.out.println(gunRange*rangeCut);
		oldDesiredDistance = desiredDistance;
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
	public void setRotation() {
		int rotationDirection = calculateRotationDirection(pAngle, targetAngle);
        
		System.out.println("norm p pointer: " + pAngle);
        System.out.println("norm t pointer: " + targetAngle);
        
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
	
	private void shootTarget() {
		checkPlayerInGunRange();
		pointerOnTarget();
		
		if(playerInRange && checkPlayerInGunRange() && pointerOnTarget()) {
			p.shooting();
			System.out.println("shoot");
		}
			
	}
	
	private boolean pointerOnTarget() {
		double angleSum = Math.abs(pAngle) + Math.abs(targetAngle);
		double tolerance = 0.03;
		
		System.out.println("angle sum: " + angleSum);
		
		if(angleSum > (Math.PI - tolerance) && angleSum < (Math.PI + tolerance))
			return true;
		
		return false;
	}
	
	private boolean checkPlayerInGunRange() {
		if(!playerInRange)
			return false;
		
		//System.out.println("distance: " + magnitude);
		//System.out.println("gun range: " + p.getGun().getRange());
		
		if(magnitude <= (p.getGun().getRange() * Battlefield.BATTLEFIELD_TILEDIM)) {
			System.out.println("in gun range");
			return true;
		}
					
		return false;
	}

	// checks ammo to reload
	private void checkAmmo() throws InterruptedException {
		if(p.checkAmmo())
			p.reloadAmmo();
	}

}
