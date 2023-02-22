package it.unibs.mainApp;

import java.awt.Color;

public class T_Pavement extends Tile {

	public T_Pavement(int y, int x, int dimension, boolean walkable) {
		super(y, x, dimension, walkable);
		this.setColor(Color.GRAY);
	}

}
