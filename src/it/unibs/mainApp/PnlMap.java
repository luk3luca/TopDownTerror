package it.unibs.mainApp;

import java.awt.*;
import javax.swing.JPanel;

public class PnlMap extends JPanel  {
	private static final long serialVersionUID = 1L;
	
	public Battlefield model;
	CircleProgress circle;
	
	public PnlMap(Battlefield model) {
		this.model = model;
		circle = new CircleProgress(model.player[0]);
	
		//EVENTI DELLA TASTIERA GIRATI SUL PANNELLO
		this.setFocusable(true);	
		this.requestFocusInWindow();
		//this.addKeyListener(this);
	} 
	
	
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Graphics2D g2 = (Graphics2D)g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
//		
//	}
	 
	protected void printMap(Graphics2D g2) {
		
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
