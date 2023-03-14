package it.unibs.mainApp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import it.unibs.view.MapViewport;


public class PnlMap extends JPanel  {
	private static final long serialVersionUID = 1L;
	
	//
	public Battlefield model;
	CircleProgress circle;
	
	public PnlMap(Battlefield model) {
		this.model = model;
		circle = new CircleProgress(model.player[0]);
//		this.add(circle);
		
		Timer t = new Timer(10, e->{ // 10 MILLISECONDI
			model.stepNext();
			repaint(); 
		});
		t.start();
	
		//EVENTI DELLA TASTIERA GIRATI SUL PANNELLO
		this.setFocusable(true);	
		this.requestFocusInWindow();
		//this.addKeyListener(this);
	} 
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		g2.setBackground(Color.black);
//		printMap(g2);
		
		
		
		//TENTATIVO 
		
		// Calcola la posizione della viewport
	    int viewportSize = Battlefield.BATTLEFIELD_HEIGHT / 2; // dimensione della viewport quadrata
	    int viewportX = (int) (model.player[0].getPosX() - viewportSize / 2);
	    int viewportY = (int) (model.player[0].getPosY() - viewportSize / 2);
	   
	    g2.setColor(Color.BLACK);
	    //RETTANGOLO NERO PER IL CONTORNO DELLA MAPPA
	    g2.fillRect(-Battlefield.BATTLEFIELD_HEIGHT/4,
	    			-Battlefield.BATTLEFIELD_HEIGHT/4,
	    			Battlefield.BATTLEFIELD_WIDTH + Battlefield.BATTLEFIELD_HEIGHT/2,
	    			3* Battlefield.BATTLEFIELD_HEIGHT/2);
	    
	    //TRASLA LA MAPPA IN ALTO A SINISTRA
	    g2.translate(-viewportX, -viewportY);
	    
	    // Imposta il rettangolo di clipping sulla viewport
	    Rectangle viewport = new Rectangle(viewportX, viewportY, viewportSize, viewportSize);
	    g2.setClip(viewport);
	    
	    // Disegna la mappa
	    printMap(g2);
	    
	    // Ripristina il rettangolo di clipping
	    g2.setClip(null);
	}
	 
	private void printMap(Graphics2D g2) {
		for(Tile t: model.tiles) {
			g2.setColor(t.getColor());
			g2.fill(t.getShape());
			
			if(t.getImage() != null) {
				g2.drawImage(t.getImage(),(int)t.getX(),(int)t.getY(),Battlefield.BATTLEFIELD_TILEDIM,Battlefield.BATTLEFIELD_TILEDIM,null);
			} 
		}
		
		for(int i=0; i < model.player.length; i++) {
			Player p = model.player[i];
			p.getGun().setPlayerInfo(p.getPosX(), p.getPosY(), p.getAngle());
			g2.setColor(p.getGun().getColor());
			g2.fill(p.getGun().getShape());
			
			g2.setColor(p.getColor());
			g2.fill(p.getShape());
		}
		
		for (int i = 0; i < model.bullet.size(); i++) {
			g2.setColor(model.bullet.get(i).getColor());
			g2.fill(model.bullet.get(i).getShape());
		}
		
		circle.setBar();
	}	

}
