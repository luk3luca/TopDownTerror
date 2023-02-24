package it.unibs.mainApp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PnlMap extends JPanel {
	private static final long serialVersionUID = 1L;

	int[][] mapMatrix = MapMatrix.getMatrix();
	
	//TODO aggiugnere array
	ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public PnlMap() {
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		int tileDim = getCellSize();
		g2.setColor(Color.GRAY); 
		printMap(g2, tileDim);
	}
	
	//TODO disegnare mappa
	private void printMap(Graphics2D g2, int tileDim) {
		int spawnCounter = 1;
		for(int y = 0; y < MapMatrix.HEIGHT; y++) {
			for(int x = 0; x < MapMatrix.WIDTH; x++) {
				//TODO aggiungere costruttori, array, disegno
				//TODO contatore per spawn-colori
				switch(mapMatrix[y][x]) {
					case 0:
						tiles.add(buildPavement(y, x, tileDim));
						break;
					case 1:
						tiles.add(buildWall(y,x, tileDim));
						break;
					case 2:
						tiles.add(buildSpawn(y,x, tileDim, spawnCounter));
						spawnCounter++;
						break;
					default:
						break;
				}
			}
		}
		
		for(Tile t: tiles) {
			
			g2.setColor(t.getColor());
			g2.fill(t.getShape());
			
			
			if(t.getImage() != null) {
				g2.drawImage(t.getImage(),(int)t.getX(),(int)t.getY(),tileDim,tileDim,null);
			}
			
		}
	}
	
	private T_Pavement buildPavement(int y, int x, int tileDim) {
		return new T_Pavement(y * tileDim, x * tileDim, tileDim, true);
	}
	
	private T_Wall buildWall(int y, int x, int tileDim) {
		return new T_Wall(y * tileDim, x * tileDim, tileDim, false);
	}

	private T_Spawn buildSpawn(int y, int x, int tileDim, int spawnCounter) {
		Color c = TeamColors.getColorAlpha(spawnCounter);
		//Color c = Color.CYAN;
		return new T_Spawn(y * tileDim, x * tileDim, tileDim * MapMatrix.SPAWN_H, tileDim * MapMatrix.SPAWN_W, true, c);
	}
	
	private int getCellSize() {
		return Math.min(getWidth() / MapMatrix.WIDTH, getHeight() / MapMatrix.HEIGHT); 
	}
}
