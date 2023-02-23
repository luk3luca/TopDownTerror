package it.unibs.mainApp;

import java.awt.Color;

public class T_Spawn extends Tile {
	//TODO aggiungere controllo per non sparare/prendere danni
	
	private static String imagePath = null;
	
	public T_Spawn(int y, int x, int dimY, int dimX, boolean walkable, Color c) {
		super(y, x, dimY, dimX, walkable,imagePath);
		this.setColor(c);
	}

}
