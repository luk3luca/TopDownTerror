package it.unibs.mainApp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

//OK
public class Player extends MovingObject implements Serializable{
	public static final double DEFAULT_X_SPEED = 2., DEFAULT_Y_SPEED = 2.;
	
	protected static final double M_VELOCITY = 2.;
	public static final double R_VELOCITY = 0.02;
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
	
	// bot reset path
	public boolean respawn = false;
	
	
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
	private boolean shoots = false;
	private long lastShotTime;
	private long lastReloadTime;
	private long startReloadTime;
	private long tempoDiSparo;

	protected double xSpeed = 0.;
	private double ySpeed = 0.;
	private double rotation = 0.;
	
	private ArrayList<Integer> controls;
	
	public void setControls(ArrayList<Integer> controls) {
		this.controls = controls;
	}
	
	public String getName() {
		return name;
	}
	
	public long getTempoDiSparo() {
		return tempoDiSparo;
	}

	public void setTempoDiSparo(long tempoDiSparo) {
		this.tempoDiSparo = tempoDiSparo;
	}

	public  void setXSpeed(double xSpeed) { this.xSpeed = xSpeed;}
	
	public  void setYSpeed(double ySpeed) { this.ySpeed = ySpeed;}
	
	public double getXSpeed() {return xSpeed;}
	
	public double getYSpeed() {return ySpeed;}
	
	public  void setRotation(double rotation) {this.rotation = rotation;}
	
	public double getRotation() {return rotation;}
	
	public void setGun(Gun gun) {this.gun = gun;}
	
	public int getMagMax() {
		return magMax;
	}
	public void setMagMax(int magMax) {
		this.magMax = magMax;
	}
	public void setAmmoLeft(int ammoLeft) {
		this.ammoLeft = ammoLeft;
	}
	
	public Player() {
	}

	public Player(String name, T_Spawn spawn, Color color) {
		super(M_VELOCITY, R_VELOCITY,color );
		this.name = name;
		this.spawn = spawn;
		this.angle = Math.PI/2;
		this.hp = HP;
		this.kills = 0;
		this.deaths = 0;

		try {
			this.gun = Gun.AR.clone();
		} catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
		
		this.magMax = gun.getMaxAmmo();
		this.ammoLeft = magMax;
		
		setPosX(spawn.getSpawnX() - Battlefield.BATTLEFIELD_TILEDIM/4 );
		setPosY(spawn.getSpawnY() - Battlefield.BATTLEFIELD_TILEDIM/4);
		
		Ellipse2D.Double playerArea = new Ellipse2D.Double(0.,
														   0.,
														   Battlefield.BATTLEFIELD_TILEDIM/2, 
														   Battlefield.BATTLEFIELD_TILEDIM/2);
		
		this.shape = playerArea;
		this.gun.setPlayerInfo(getPosX(), getPosY(), getAngle());
		
		this.startReloadTime = System.currentTimeMillis() - (long)(this.gun.getReload()*1000);
		this.lastShotTime = 0;
	}
	
	public void stepNext() {
		setPosX(getPosX() + xSpeed);
		setPosY(getPosY() + ySpeed);
		setAngle(getAngle()+rotation);
		
		try {
			applyControls(controls);
		}
		catch (Exception e) {
		}
		
	}

	
	public long getStartReloadTime() {
		return startReloadTime;
	}

	public void setStartReloadTime(long startReloadTime) {
		this.startReloadTime = startReloadTime;
	}

	public  boolean isShoot() {
		boolean res = shoots;
		
		shoots = false;
		return res;
	}
	
	public  void shooting() {
		shoots = true;
	}

	public  boolean shoot() throws InterruptedException {
	    long currentTime = System.currentTimeMillis();

	    if(isReloadingTime(currentTime)) {
	        return false;
	    }

	    if (ammoLeft == 0 || isReloading()) {
	        reloadAmmo();
	        return false;
	    }

	    if(currentTime - lastShotTime > (this.gun.getRate() * 1000)) {
	    	setTempoDiSparo(currentTime - lastShotTime);
	        lastShotTime = currentTime;
	        
	        removeAmmo();
	        return true;
	    }

	    return false;
	}
	
	public  void reloadAmmo() throws InterruptedException {
	    long currentTime = System.currentTimeMillis();

	    if(reloading == true || ammoLeft == magMax) {
	        return;
	    }

	    System.out.println(name + " Reloading");
	    startReloadTime = currentTime;
	    reloading = true;
	    int reloadTime = (int) (this.gun.getReload()*1000);
	    
	    Timer timer = new Timer(reloadTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ammoLeft = magMax;
                reloading = false;
            }
        });
        timer.setRepeats(false);
        timer.start();
	}	
	
	public boolean checkAmmo() {
		if (ammoLeft == 0)
			return false;
		else {
			return true;
		}	
	}
	
	public boolean isReloading() {
		return reloading;
	}

	public boolean isReloadingTime(long currentTime) {
		if(currentTime < (startReloadTime + (this.gun.getReload() * 1000))) {
			return true;
		}
		return false;
	}
			
	public void removeAmmo() {
		this.ammoLeft -= 1;
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
	
	public void hitted(Gun gun, Player shooter) {
		this.hp -= gun.getDmg();
		if(hp<=0) {			
			dead();
			deaths++;
			shooter.kills++;
			System.out.println(name + " kills: " + shooter.kills);
		}
	}
	
	//TODO EVENTUALMENTE METTERE TIMER PER RESPAWN 
	private void dead() {
		setHp(HP);
		setAmmoLeft(magMax);
		setPosX(spawn.getSpawnX() - Battlefield.BATTLEFIELD_TILEDIM/4);
		setPosY(spawn.getSpawnY() - Battlefield.BATTLEFIELD_TILEDIM/4);
		
		// resppawn controller for bot path generation
		respawn = true;
	}
		
	/*---GETTERS AND SETTERS---*/
	public Gun getGun() {return gun;}
	
	public int getHp() {return hp;}
	
	public void setHp(int hp) {this.hp=hp;}
	
	public void setReloading(boolean reloading) {this.reloading = reloading;	}
	
	public int getAmmoLeft() {return ammoLeft;}
	
	public double getCenterX() { return posX + Battlefield.BATTLEFIELD_TILEDIM/4; }

	public double getCenterY() { return posY + Battlefield.BATTLEFIELD_TILEDIM/4; }

	public boolean isTopCollision() {return topCollision;}
	public void setTopCollision(boolean topCollision) {this.topCollision = topCollision;}

	public boolean isBottomCollision() {return bottomCollision;}
	public void setBottomCollision(boolean bottomCollision) {this.bottomCollision = bottomCollision;}

	public boolean isLeftCollision() {return leftCollision;}
	public void setLeftCollision(boolean leftCollision) {this.leftCollision = leftCollision;}

	public boolean isRightCollision() {return rightCollision;}
	public void setRightCollision(boolean rightCollision) {this.rightCollision = rightCollision;}

	public boolean isTopLeftCollision() {return topLeftCollision;}
	public void setTopLeftCollision(boolean topLeftCollision) {this.topLeftCollision = topLeftCollision;}

	public boolean isTopRightCollision() {return topRightCollision;}
	public void setTopRightCollision(boolean topRightCollision) {this.topRightCollision = topRightCollision;}

	public boolean isBottomLeftCollision() {return bottomLeftCollision;}
	public void setBottomLeftCollision(boolean bottomLeftCollision) {this.bottomLeftCollision = bottomLeftCollision;}

	public boolean isBottomRightCollision() {return bottomRightCollision;}
	public void setBottomRightCollision(boolean bottomRightCollision) {this.bottomRightCollision = bottomRightCollision;}

	public T_Spawn getSpawn() {return spawn;}
	
	public void setRandomGun() {
		Random rand = new Random();
		int gunRand = rand.nextInt(Gun.nGuns);
		setPlayerGun(gunRand);
	}
	
	public void setPlayerGun(int n) {
		try {
			this.gun = Gun.guns[n].clone();
		} catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	private void resetSpeed() {
		setXSpeed(0);
		setYSpeed(0);
		setRotation(0);
	}
	
	public void applyControls(ArrayList<Integer> keyCode) {
		resetSpeed();
		for (Integer key : keyCode) {
			switch(key) {
				case KeyEvent.VK_W:
					this.setYSpeed(-Player.DEFAULT_Y_SPEED);
					break;
				case KeyEvent.VK_A:
					this.setXSpeed(-Player.DEFAULT_X_SPEED);
					break;
				case KeyEvent.VK_S:
					this.setYSpeed(Player.DEFAULT_Y_SPEED);
					break;
				case KeyEvent.VK_D:
					this.setXSpeed(Player.DEFAULT_X_SPEED);
					break;
				case KeyEvent.VK_I, KeyEvent.VK_UP:
			    	this.shooting();
					break; 
				case KeyEvent.VK_J, KeyEvent.VK_LEFT:
					this.setRotation(-Player.R_VELOCITY);
					break;
				case KeyEvent.VK_L, KeyEvent.VK_RIGHT:
					this.setRotation(Player.R_VELOCITY);
					break;
				case KeyEvent.VK_K, KeyEvent.VK_DOWN: {
					try {
						this.reloadAmmo();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
					break;
				}
			}
		}
	}

	
}
