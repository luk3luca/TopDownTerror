package it.unibs.mainApp;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;



public class Player extends MovingObject{
	protected static final double M_VELOCITY = 1.;
	private static final double R_VELOCITY = 0.02;
	private static final int HP = 100;

	private String name;
	private Color color;
	private int hp;
	// gun logic
	private Gun gun;
	private T_Spawn spawn;
	private int magMax;
	private int ammoLeft;
	
	private int spawnX;
	private int spawnY;

	private int kills;
	private int deaths;
	
	// collsion logic
	private boolean topCollision = false;
	private boolean bottomCollision = false;
	private boolean leftCollision = false;
	private boolean rightCollision = false;
	
	private boolean topLeftCollision = false;
	private boolean topRightCollision = false;
	private boolean bottomLeftCollision = false;
	private boolean bottomRightCollision = false; 
	
	// shooting logic
	private boolean reloading = false;
	private long lastShotTime;
	private long lastReloadTime;
	private long reloadStartTime;


	public Player(String name, T_Spawn spawn, Color color) {
		super(M_VELOCITY, R_VELOCITY,color );
		this.name = name;
		this.spawn = spawn;
		this.angle = Math.PI/2;

		try {
			this.gun = Gun.PISTOL.clone();
		} catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
		
		this.magMax = gun.getMaxAmmo();
		this.ammoLeft = magMax;
		this.hp = HP;
		this.kills = 0;
		this.deaths = 0;
		
		setPosX(spawn.getSpawnX() - Battlefield.BATTLEFIELD_TILEDIM/4 );
		setPosY(spawn.getSpawnY() - Battlefield.BATTLEFIELD_TILEDIM/4);
		Area playerArea = new Area(new Ellipse2D.Double(0.,
														0.,
														Battlefield.BATTLEFIELD_TILEDIM/2, 
														Battlefield.BATTLEFIELD_TILEDIM/2));
		
		this.shape = playerArea;
		//System.out.println(getPosX() + " " + getPosY() + " " + getAngle());
		this.gun.setPlayerInfo(getPosX(), getPosY(), getAngle());
		
		this.lastReloadTime = System.currentTimeMillis() - (int)(this.gun.getReload()*1000);
		this.lastShotTime = 0;
	}
	
	// TODO controllo su time in cui viene avviato reload
	public boolean shoot() {
		if(isReloading())
			return false;
		
		long currentTime = System.currentTimeMillis();		
	
		if(checkAmmo()) {
			if(currentTime - lastShotTime >= (this.gun.getRate() * 1000)) {
				lastShotTime = currentTime;
				removeAmmo();
				return true;
			}
		} else {
	        //reloadAmmo();
	        return false;
		}
		return false;
	}
	
	public void reloadAmmo() {
		if (reloading || ammoLeft == magMax) {
	        return;
	    }
	    
	    reloading = true;
	    reloadStartTime = System.currentTimeMillis();
	    
	    // schedule reload completion after reloadTime
	    new java.util.Timer().schedule(
	        new java.util.TimerTask() {
	            @Override
	            public void run() {
	                // reload completed, reset ammo and reloading flag
	                ammoLeft = magMax;
	                reloading = false;
	            }
	        },
	        (int)(this.gun.getReload() * 1000)
	    );
	}
	
	public boolean checkAmmo() {
		if (ammoLeft == 0)
			return false;
		else {
			return true;
		}
				
	}
			
	public void removeAmmo() {
		this.ammoLeft -= 1;
	}
	
	
	
	
	public Gun getGun() {
		return gun;
	}
	
	public boolean isReloading() {
		return reloading;
	}

	public void setReloading(boolean reloading) {
		this.reloading = reloading;
	}

	public int getAmmoLeft() {
		return ammoLeft;
	}

	public boolean isTopCollision() {
		return topCollision;
	}

	public void setTopCollision(boolean topCollision) {
		this.topCollision = topCollision;
	}

	public boolean isBottomCollision() {
		return bottomCollision;
	}

	public void setBottomCollision(boolean bottomCollision) {
		this.bottomCollision = bottomCollision;
	}

	public boolean isLeftCollision() {
		return leftCollision;
	}

	public void setLeftCollision(boolean leftCollision) {
		this.leftCollision = leftCollision;
	}

	public boolean isRightCollision() {
		return rightCollision;
	}

	public void setRightCollision(boolean rightCollision) {
		this.rightCollision = rightCollision;
	}


	public boolean isTopLeftCollision() {
		return topLeftCollision;
	}

	public void setTopLeftCollision(boolean topLeftCollision) {
		this.topLeftCollision = topLeftCollision;
	}

	public boolean isTopRightCollision() {
		return topRightCollision;
	}

	public void setTopRightCollision(boolean topRightCollision) {
		this.topRightCollision = topRightCollision;
	}

	public boolean isBottomLeftCollision() {
		return bottomLeftCollision;
	}

	public void setBottomLeftCollision(boolean bottomLeftCollision) {
		this.bottomLeftCollision = bottomLeftCollision;
	}

	public boolean isBottomRightCollision() {
		return bottomRightCollision;
	}

	public void setBottomRightCollision(boolean bottomRightCollision) {
		this.bottomRightCollision = bottomRightCollision;
	}

	public void resetCollision() {
		this.topCollision = false;
		this.bottomCollision = false;
		this.leftCollision = false;
		this.rightCollision = false;
		this.topLeftCollision = false;
		this.topRightCollision = false;
		this.bottomLeftCollision = false;
		this.bottomRightCollision = false; 
	}
	

	public void resetVelocity(){
		this.setM_velocity(M_VELOCITY);
	}
	
	
}
