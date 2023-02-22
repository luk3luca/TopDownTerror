package it.unibs.mainApp;

import java.awt.Color;

public class T_Pavement extends Tile {

	public T_Pavement(boolean walkable, int dimension) {
		super(walkable, dimension);
		this.setColor(Color.GRAY);
	}

}
