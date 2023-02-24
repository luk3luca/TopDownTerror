package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;

public class Player extends MovingObject{
	private static final double M_VELOCITY = 0.1;
	private static final int HP = 100;

	private String name;
	private Color color;
	private int hp;
	private Gun gun = Gun.PISTOL;
	private T_Spawn spawn;
	private int magMax;
	private int ammoLeft;

	private int kills;
	private int deaths;

	// TODO: costruire player
	public Player() {
		super(M_VELOCITY);
		//this.shape = new Area()

	}

	public Player(String name, Color color, int hp, Gun gun, T_Spawn spawn, int magMax, int ammoLeft) {
		super(M_VELOCITY);
		this.name = name;
		this.spawn = spawn;
		this.color = spawn.getColor();
		this.hp = HP;
		this.magMax = gun.getMaxAmmo();
		this.ammoLeft = magMax;
		this.kills = 0;
		this.deaths = 0;
		
		//this.shape = new Area(new Ellipse2D.Double());
	}
	
	

}
