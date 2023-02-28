package it.unibs.mainApp;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PnlMap extends JPanel {
	private static final long serialVersionUID = 1L;
	
	Battlefield model;
	
	public PnlMap(Battlefield model) {
		this.model = model;
		
		Timer t = new Timer(10, e->{ //10 milli secondi 
			repaint();
		});
		
		t.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		printMap(g2);
	}
	 
	private void printMap(Graphics2D g2) {
		
		for(Tile t: model.tiles) {
			
			g2.setColor(t.getColor());
			g2.fill(t.getShape());
			
			if(t.getImage() != null) {
				g2.drawImage(t.getImage(),(int)t.getX(),(int)t.getY(),Battlefield.BATTLEFIELD_TILEDIM,Battlefield.BATTLEFIELD_TILEDIM,null);
			}
			
		}
		
		//TODO capire come mai quando crea player lo spawn associato è vuoto 
		
		for(int i=0; i < model.player.length; i++) {
			g2.setColor(model.player[i].getColor());
			g2.fill(model.player[i].getShape());
		}
	}
	
}
