
package it.unibs.view;

import java.awt.*;
import java.util.ArrayList;

import it.unibs.mainApp.*;

public class PlayerViewport extends PnlMap {
    private static final long serialVersionUID = 1L;

    private Player p;
    Battlefield model;
    public PlayerViewport(  ArrayList<Tile> tiles, Player[] p) {
    	super(tiles,p);
        this.p = p[0];
        this.setFocusable(true);	
		this.requestFocusInWindow();
		
		this.model = model;
				
    }
    
  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Calcola la posizione della viewport
	    int viewportSize = Battlefield.BATTLEFIELD_HEIGHT / 2; // dimensione della viewport quadrata
	    int viewportX = (int) (p.getPosX() - viewportSize / 2);
	    int viewportY = (int) (p.getPosY() - viewportSize / 2);
	    
	    //TRASLA LA VIEWPORT IN ALTO A SINISTRA
	    g2.translate(-viewportX, -viewportY);
	    
	    // Imposta il rettangolo di clipping sulla viewport
	    Rectangle viewport = new Rectangle(viewportX, viewportY, viewportSize, viewportSize);
	    g2.setClip(viewport);
	    
	    //RETTANGOLO NERO PER IL CONTORNO DELLA MAPPA
	    g2.setColor(Color.BLACK);
	    g2.fillRect(-Battlefield.BATTLEFIELD_HEIGHT/4,
	    			-Battlefield.BATTLEFIELD_HEIGHT/4,
	    			Battlefield.BATTLEFIELD_WIDTH + Battlefield.BATTLEFIELD_HEIGHT/2,
	    			3* Battlefield.BATTLEFIELD_HEIGHT/2);
	    
	    printMap(g2);
	    
	    // Ripristina il rettangolo di clipping
	    g2.setClip(null);
  }
    
    

//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//		Graphics2D g2 = (Graphics2D)g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		// Calcola la posizione della viewport
//	    int viewportSize = Battlefield.BATTLEFIELD_HEIGHT / 2; // dimensione della viewport quadrata
//	    int viewportX = (int) (p.getPosX() - viewportSize / 2);
//	    int viewportY = (int) (p.getPosY() - viewportSize / 2);
//	    
//	    //TRASLA LA VIEWPORT IN ALTO A SINISTRA
//	    g2.translate(-viewportX, -viewportY);
//	    
//	    // Imposta il rettangolo di clipping sulla viewport
//	    Rectangle viewport = new Rectangle(viewportX, viewportY, viewportSize, viewportSize);
//	    g2.setClip(viewport);
//	    
//	    //RETTANGOLO NERO PER IL CONTORNO DELLA MAPPA
//	    g2.setColor(Color.BLACK);
//	    g2.fillRect(-Battlefield.BATTLEFIELD_HEIGHT/4,
//	    			-Battlefield.BATTLEFIELD_HEIGHT/4,
//	    			Battlefield.BATTLEFIELD_WIDTH + Battlefield.BATTLEFIELD_HEIGHT/2,
//	    			3* Battlefield.BATTLEFIELD_HEIGHT/2);
//	    
//	    printMap(g2);
//	    
//	    // Ripristina il rettangolo di clipping
//	    g2.setClip(null);
//    }
}

