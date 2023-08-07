package it.unibs.mainApp;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
<<<<<<< Updated upstream
import java.util.Timer;
import java.util.TimerTask;



public class Player extends MovingObject{
	protected static final double M_VELOCITY = 1.;
=======
import java.io.Serializable;

import javax.swing.Timer;

//OK
public class Player extends MovingObject implements Serializable{
	protected static final double M_VELOCITY = 2.;
>>>>>>> Stashed changes
	private static final double R_VELOCITY = 0.02;
	private static final int HP = 100;

	private String name;
	private Color color;
	private int hp;
	private Gun gun;
	private T_Spawn spawn;
	private int magMax;
	private int ammoLeft;
	
	private int spawnX;
	private int spawnY;

	private int kills;
	private int deaths;
	private boolean reload = false;
	
	private boolean topCollision = false;
	private boolean bottomCollision = false;
	private boolean leftCollision = false;
	private boolean rightCollision = false;
	
	private boolean topLeftCollision = false;
	private boolean topRightCollision = false;
	private boolean bottomLeftCollision = false;
	private boolean bottomRightCollision = false; 

	public Player(String name, T_Spawn spawn, Color color) {
		super(M_VELOCITY, R_VELOCITY,color );
		this.name = name;
		this.spawn = spawn;
		this.angle = Math.PI/2;
		//Clona Gun in modo che si modifichi la costante
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
<<<<<<< Updated upstream
		Area playerArea = new Area(new Ellipse2D.Double(0.,
=======
		
		Ellipse2D.Double playerArea = new Ellipse2D.Double(0.,
>>>>>>> Stashed changes
														0.,
														Battlefield.BATTLEFIELD_TILEDIM/2, 
														Battlefield.BATTLEFIELD_TILEDIM/2);
		
		this.shape = playerArea;
		System.out.println(getPosX() + " " + getPosY() + " " + getAngle());
		this.gun.setPlayerInfo(getPosX(), getPosY(), getAngle());
	}

	public Gun getGun() {
		return gun;
	}
	
	
	public boolean isReload() {
		return reload;
	}

	public void setReload(boolean reload) {
		this.reload = reload;
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
	
	public void ammo() {
		if (ammoLeft>0) {
			this.ammoLeft += -1;
		}else if(ammoLeft == 0){
			reload = true;
			Timer timer = new Timer();
			TimerTask tt = new TimerTask() {
				
				@Override
				public void run() {
					ammoLeft = magMax;
					reload = false;
					
				}
			};
			timer.schedule(tt, (int)gun.getReload());
			
		}
	}
/*
	public void fire() {
		Bullet b = new Bullet(this, gun);
	}
*/
	
	  
 
}
