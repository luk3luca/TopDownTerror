package it.unibs.view;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.Bullet;
import it.unibs.mainApp.Player;
import it.unibs.mainApp.Tile;

public class PnlMap extends JPanel implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Tile> tiles;
	private Player[] players;
	private ArrayList<Bullet> bullet;
	CircleProgress circle;
	
	public PnlMap() {
		this.setFocusable(true);	
		this.requestFocusInWindow();
	}
	public void setObjects(ArrayList<Tile> t, Player[] p,ArrayList<Bullet> b) {
		this.tiles = t;
		this.players = p;
		this.bullet = b;
		
		
	}
	
	public PnlMap(ArrayList<Tile> t,Player[] players,ArrayList<Bullet> b) {
//		circle = new CircleProgress(players[0]);
		this.tiles = t;
		this.players = players;
		this.bullet = b;
		//EVENTI DELLA TASTIERA GIRATI SUL PANNELLO
		this.setFocusable(true);	
		this.requestFocusInWindow();
	} 
	
	
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Graphics2D g2 = (Graphics2D)g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
//		
//	}
	 
	protected void printMap(Graphics2D g2) {
		if(tiles!=null) {
			for(Tile t: tiles) {
				g2.setColor(t.getColor());
				g2.fill(t.getShape());
				
				if(t.getImage() != null) {
					g2.drawImage(t.getImage(),(int)t.getX(),(int)t.getY(),Battlefield.BATTLEFIELD_TILEDIM,Battlefield.BATTLEFIELD_TILEDIM,null);
				} 
			}
		}
		
			for(int i=0; i < players.length; i++) {
				Player p = players[i];
				if(p!=null) {
					p.getGun().setPlayerInfo(p.getPosX(), p.getPosY(), p.getAngle());
					g2.setColor(p.getGun().getColor());
					g2.fill(p.getGun().getShape());
					
					g2.setColor(p.getColor());
					g2.fill(p.getShape());
				}
				
			
		}
		
		for (int i = 0; i < bullet.size(); i++) {
			g2.setColor(bullet.get(i).getColor());
			g2.fill(bullet.get(i).getShape());
		}
		
		
	}	

}
