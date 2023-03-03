package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Area;


public class Bullet extends MovingObject {
	private static final double M_VELOCITY = 5;

	protected Player player;
	protected Gun gun;

	private int fuel;
		
	public Bullet(Player p, Gun g) {
		super(M_VELOCITY, 0,Color.RED);

		this.player = p;
		this.gun = g;
		
		this.shape = new Area (new Polygon(
				new int[] {-5,0,5,0,-5},
				new int[] {3,3,0,-3,-3},
				5						 
				));
		
	}
	
	//TODO morte del proiettile in base a distanza percorsa
	@Override
	public void stepNext() {
		super.stepNext();
		if(--fuel <=0) {
			isAlive = false;
		}
		
	}	

	
}
