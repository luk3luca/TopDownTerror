package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;

public class Gun {
	public final static Gun SNIPER = new Gun("Sniper rifle", 6, 3., 7, 6., 80, new Color(0,0,102)); 				// #000066
	public final static Gun AR = new Gun("AK-47", 4, 0.5, 25, 4., 20, new Color(128, 0, 32));						// #800020
	public final static Gun SHOTGUN = new Gun("Il pompa del nonno", 2, 0.8, 5, 3., 50, new Color(135, 38, 87));	// #872657
	public final static Gun PISTOL = new Gun("Tac-45", 4, 0.8, 12, 3., 15, new Color(0, 191, 255));				// #00BFFF
	public final static Gun SMG = new Gun("MP7", 3, 0.25, 30, 4., 12, new Color(130, 38, 176));					// #8226b0
	public final static Gun BOW = new Gun("Sniper rifle", 6, 6., 1, 1., 100, new Color(255,255,255));				// #FFFFFF
	
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

	public static Gun getSniper() {
		return SNIPER;
	}

	public static Gun getAr() {
		return AR;
	}

	public static Gun getShotgun() {
		return SHOTGUN;
	}

	public static Gun getPistol() {
		return PISTOL;
	}

	public static Gun getSmg() {
		return SMG;
	}

	public static Gun getBow() {
		return BOW;
	}

	public String getName() {
		return name;
	}

	public int getRange() {
		return range;
	}

	public double getRate() {
		return rate;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public double getReload() {
		return reload;
	}

	public int getDmg() {
		return dmg;
	}

	public Color getColor() {
		return color;
	}

	
}
