package it.unibs.mainApp;

import java.awt.Color;

public class T_Pavement extends Tile {
	
	private static String imagePath = "src/images/cobblestone.png";
	
	public T_Pavement(int y, int x, int dimension, boolean walkable) {
		super(y, x, dimension, dimension, walkable,imagePath);
		this.setColor(Color.GRAY);
	}

}
