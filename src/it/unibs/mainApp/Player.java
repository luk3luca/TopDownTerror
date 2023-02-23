package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;

public class Player extends MovingObject{
	private static final double VELOCITY = 0.1;

	private String name;
	private Color color;
	private int hp;
	private Gun gun;
	private T_Spawn spawn;
	private int magMax;
	private int ammoLeft;

	private int kills;
	private int deaths;

	// TODO: costruire player
	public Player() {
		super(VELOCITY);
		//this.shape = new Area()

	}

}
