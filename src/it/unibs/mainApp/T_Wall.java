package it.unibs.mainApp;

import java.awt.*;

public class T_Wall extends Tile{

	public T_Wall(int y, int x, int dimension, boolean walkable) {
		super(y, x, dimension, walkable);
		this.setColor(Color.BLACK);
	}
	
}
