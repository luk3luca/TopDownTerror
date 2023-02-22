package it.unibs.mainApp;

import java.awt.Color;

public class T_Spawn extends Tile {
	//TODO aggiungere controllo per non sparare/prendere danni
	public T_Spawn(int y, int x, int dim1, int dim2, boolean walkable, Color c) {
		super(y, x, dim1, dim2, walkable);
		this.setColor(c);
	}

}
