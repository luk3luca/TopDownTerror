package it.unibs.mainApp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class PnlMap extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	Battlefield model;
	
	public PnlMap(Battlefield model) {
		this.model = model;
		
		Timer t = new Timer(10, e->{ //10 milli secondi 
			applyControls();
			model.stepNext();
			repaint(); 
		});
		
		t.start();
		
		//eventi della tastiera girati al pannello 
		this.setFocusable(true);	
		this.requestFocusInWindow();
		this.addKeyListener(this);
	} 
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		
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
			g2.fill(model.player[i].getGun().getShape(model.player[i].getPosX(), model.player[i].getPosY(), 0));
		}
	}
	
	
	//GESTIONE EVENTI TASTIERA
	private ArrayList<Integer> currentActiveControls = new ArrayList<>();
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!currentActiveControls.contains(e.getKeyCode()))
				currentActiveControls.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentActiveControls.remove((Object)e.getKeyCode());
	}
	
	private void applyControls() {
		
		Player p = model.player[0];
		if(p == null)
			return;
		
		
		for(Integer keycode: currentActiveControls) {
			switch(keycode) {
			case KeyEvent.VK_W: p.setPosY(p.getPosY() - p.getM_velocity()); break;
			case KeyEvent.VK_A: p.setPosX(p.getPosX() - p.getM_velocity()); break;
			case KeyEvent.VK_S: p.setPosY(p.getPosY() + p.getM_velocity()); break;
			case KeyEvent.VK_D: p.setPosX(p.getPosX() + p.getM_velocity()); break;
			case KeyEvent.VK_I:  break;
			case KeyEvent.VK_J: p.rotate(- p.getR_velocity()); break;
			case KeyEvent.VK_L: p.rotate(p.getR_velocity()); break;
			
			}
		}
	}
	
}
