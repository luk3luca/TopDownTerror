package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Area;

public class Bullet extends MovingObject {
	private static final double M_VELOCITY = 15;
	//private static final double R_VELOCITY = 0;

	protected Player player;
	protected Gun gun;

	private int fuel;
		
	public Bullet(Player p, Gun g) {
		super(M_VELOCITY, 0,Color.RED);

		this.player = p;
		this.gun = g;
		//TODO velocitA fissa in base alla velocita del player
		
		this.setPosX(p.getPosX() + Battlefield.BATTLEFIELD_TILEDIM/4);
		this.setPosY(p.getPosY()+ Battlefield.BATTLEFIELD_TILEDIM/4);
		
		this.setAngle(p.getAngle());
		
		
		
		this.shape = new Area (new Polygon(
				new int[] {5,0,-5,-5,0},
				new int[] {0,-3,-3,3,3},
				5						 
				));
	}
	
	//TODO morte del proiettile in base a distanza percorsa
	@Override
	public void stepNext() {
		this.accelerate();
		super.stepNext();
		if(--fuel <=0) {
			isAlive = false;
		}
	}	
	
	private void accelerate() {
		this.setPosX(this.getPosX() + M_VELOCITY * Math.cos(this.angle));
		this.setPosY(this.getPosY() + M_VELOCITY * Math.sin(this.angle));
	}
}
