package it.unibs.view;

import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.MapMatrix;
import it.unibs.mainApp.Player;
import it.unibs.mainApp.PnlMap;

public class PlayerViewport extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final int SIDE = Battlefield.BATTLEFIELD_TILEDIM * MapMatrix.HEIGHT/2;
	
	Player p;
	PnlMap pnlMap;
	private Rectangle2D.Double viewport = new Rectangle2D.Double(
												p.getCenterX() - SIDE/2, 
												p.getCenterY() - SIDE/2, 
												SIDE, 
												SIDE);
	
	public void setViewport(Rectangle2D.Double newViewport) {
		this.viewport = newViewport;
		repaint();
	}
	
	public PlayerViewport(Player p) {
		this.p = p;
	}

}
