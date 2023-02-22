package it.unibs.mainApp;

import java.awt.Polygon;
import java.awt.geom.Area;

public class Bullet extends MovingObject {

	protected Player player;
	protected Gun gun;

	private int fuel;
		
	public Bullet(Player p, Gun g) {

		this.player = p;
		this.gun = g;
		//TODO velocità fissa in base alla velocità del player
		
		this.shape = new Area (new Polygon(
				new int[] {-5,0,5,0,-5},
				new int[] {3,3,0,-3,-3},
				5						 
				));
		
	}
	
	
	@Override
	public void stepNext() {
		super.stepNext();
		
		if(--fuel <=0) {
			isAlive = false;
			//TODO settare velocità = 0
		}
	}	
}
