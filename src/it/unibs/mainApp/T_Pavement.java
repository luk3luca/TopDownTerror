package it.unibs.mainApp;

import java.awt.Color;
import java.io.Serializable;
//OK
public class T_Pavement extends Tile {
	
	private static String imagePath = "src/images/smoothStone.png";
	
	public T_Pavement(int y, int x, int dimension, boolean walkable) {
		//super(y, x, dimension, dimension, walkable,imagePath);
		super(y, x, dimension, dimension, walkable);
		this.setColor(Color.GRAY);
	}
	
	@Override
	public boolean checkCollision(MovingObject o) {
		return (o instanceof Player) ? 
				false: 
				super.checkCollision(o);
	}


}
