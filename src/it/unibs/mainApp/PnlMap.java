package it.unibs.mainApp;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

public class PnlMap extends JPanel {
	int[][] mapMatrix = MapMatrix.getMatrix();
	ArrayList<Tile> walls = new ArrayList<Tile>();
	//TODO aggiugnere array
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
	//TODO disegnare mappa
	private void printMap(Graphics2D g2, int tileDim) {
		for(int y = 0; y < MapMatrix.HEIGHT; y++) {
			for(int x = 0; x < MapMatrix.WIDTH; x++) {
				//TODO aggiungere costruttori, array, disegno
				//TODO contatore per spawn-colori
				switch(mapMatrix[y][x]) {
					case 0:
						buildPavement(y, x, tileDim);
						break;
					case 1:
						buildWall();
						break;
					case 2:
						builSpawn();
						break;
					default:
						break;
				}
			}
		}
	}
	
	private T_Pavement buildPavement(int y, int x, int tileDim) {
		
		return new T_Pavement(y * tileDim, x * tileDim, tileDim, true);
	}
	
	private T_Wall buildWall(int y, int x, int tileDim) {
		
		return new T_Wall(y * tileDim, x * tileDim, tileDim, true);
	}

	private T_Spawn builSpawn(int y, int x, int tileDim) {
		Color c = Color.BLACK;
		return new T_Spawn(y * tileDim, x * tileDim, tileDim, true, c);
	}
	
}
