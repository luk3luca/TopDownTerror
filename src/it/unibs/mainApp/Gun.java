package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;

public class Gun {
	public final static Gun SNIPER = new Gun("Sniper rifle", 6, 3., 7, 6., 80, );
	public final static Gun AR = new Gun("AK-47", 4, 0.5, 25, 4., 20, );
	public final static Gun SHOTGUN = new Gun("Il pompa del nonno", 2, 0.8, 5, 3., 50, );
	public final static Gun PISTOL = new Gun("Tac-45", 4, 0.8, 12, 3., 15, );
	public final static Gun SMG = new Gun("MP7", 3, 0.25, 30, 4., 12, );
	public final static Gun BOW = new Gun("Sniper rifle", 6, 3., 7, 6., 100, );

	
	//TODO COSTRUIRE GUN
	private String name;
	private int range;
	private double rate;
	private int maxAmmo;
	private double reload;
	private int dmg;
	private Color color;
	
	public Gun(String name, int range, double rate, int maxAmmo, double reload, int dmg, Color color) {
		this.name = name;
		this.range = range;
		this.rate = rate;
		this.maxAmmo = maxAmmo;
		this.dmg = dmg;
		this.color = color;
	}
	
	

}
