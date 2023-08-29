package it.unibs.mainApp;

import java.awt.*;
import java.io.Serializable;
//OK
public class T_Wall extends Tile implements Serializable{
	private static String imagePath = "src/images/brick.png";
	
	public T_Wall(int y, int x, int dimension, boolean walkable) {
		//super(y, x, dimension, dimension, walkable, imagePath);
		super(y, x, dimension, dimension, walkable);
		this.setColor(Color.BLACK);
	}
	
}
