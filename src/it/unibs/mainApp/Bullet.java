 package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
//OK
public class Bullet extends MovingObject implements Serializable {
	private static final double M_VELOCITY = 20;

	protected Player player;
	protected Gun gun;
	
	private int fuel;
	
	public Bullet(Player p, Gun g) {
		super(M_VELOCITY, 0,Color.RED);

		this.player = p;
		this.gun = g;
		this.fuel = (int) (gun.getRange() * Battlefield.BATTLEFIELD_TILEDIM/M_VELOCITY);
		
		this.setPosX(p.getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4);
		this.setPosY(p.getPosY()+ Battlefield.BATTLEFIELD_TILEDIM/4);
		this.setAngle(p.getAngle());
		
		this.shape = new Polygon(
				new int[] {5,0,-5,-5,0},
				new int[] {0,-3,-3,3,3},
				5);
	}
	
	public Shape getShape() {
		AffineTransform t = new AffineTransform();
		t.translate(this.posX, this.posY);		
		t.rotate(this.angle);

		return t.createTransformedShape(shape);
	}

	public void stepNext() {
		this.accelerate();
		if(--fuel < 0) {
			isAlive = false;
		}
	}	
	
	private void accelerate() {
		this.setPosX(this.getPosX() + M_VELOCITY * Math.cos(this.angle));
		this.setPosY(this.getPosY() + M_VELOCITY * Math.sin(this.angle));
	}

	public Player getPlayer() {return player;}
	public Gun getGun() {return gun;}
}
