package it.unibs.mainApp;

import java.awt.Color;

public class T_Spawn extends Tile {
	//TODO aggiungere controllo per non sparare/prendere danni
	public T_Spawn(int y, int x, int dimension, boolean walkable, Color c) {
		super(y, x, dimension, walkable);
		this.setColor(c);
	}

}