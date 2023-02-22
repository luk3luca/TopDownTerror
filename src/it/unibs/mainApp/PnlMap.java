package it.unibs.mainApp;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

public class PnlMap extends JPanel {
	int[][] mapMatrix = MapMatrix.getMatrix();
	ArrayList<Tile> walls = new ArrayList<Tile>();
	
	public PnlMap() {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		 int width = getWidth();
		 int height = getHeight();
		 
		 int tileDim = Math.min(width / MapMatrix.WIDTH, height/ MapMatrix.HEIGHT) ;
		 g2.setColor(Color.GRAY);
		 
	}
	
	private void printMap(Graphics2D g2, int tileDim) {
		for(int i = 0; i < MapMatrix.HEIGHT; i++) {
			for(int j = 0; j < MapMatrix.WIDTH; j++) {
				
			}
			System.out.println();
		}

		
	}
	
}
