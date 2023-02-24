package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Player extends MovingObject{
	private static final double M_VELOCITY = 0.1;
	private static final double R_VELOCITY = 0.1;
	private static final int HP = 100;

	private String name;
	private Color color;
	private int hp;
	private Gun gun = Gun.PISTOL;
	private T_Spawn spawn;
	private int magMax;
	private int ammoLeft;
	
	private int spawnX;
	private int spawnY;

	private int kills;
	private int deaths;

	public Player(String name, T_Spawn spawn) {
		super(M_VELOCITY, R_VELOCITY);
		this.name = name;
		this.spawn = spawn;
		this.magMax = gun.getMaxAmmo();
		this.ammoLeft = magMax;
		this.hp = HP;
		this.kills = 0;
		this.deaths = 0;
		
		this.color = spawn.getColor();
		//this.spawnX = spawn.getSpawnX();
		//this.spawnY = spawn.getSpawnY();
		setPosX(spawn.getSpawnX());
		setPosX(spawn.getSpawnY());
		
		//Area shapeArea = new Area(new Ellipse2D.Double(spawnX, spawnY, 10., 10.));
		Area shapeArea = new Area(new Ellipse2D.Double(0, 0, 10., 10.));
		shapeArea.add(new Area(new Line2D.Double(5., 5., 5., 15.)));
		this.shape = shapeArea;
	}
	
	

}
