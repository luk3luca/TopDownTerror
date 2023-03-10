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
	private int countRate;
	
	
	public PnlMap(Battlefield model) {
		this.model = model;
		
		Timer t = new Timer(10, e->{ // 10 MILLISECONDI
			applyControls();
			model.stepNext();
			countRate-=10;
			repaint(); 
		});
		
		t.start();
		
		//EVENTI DELLA TASTIERA GIRATI SUL PANNELLO
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
		
		//TODO capire come mai quando crea player lo spawn associato � vuoto 
		
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
			p.resetVelocity();
			switch(keycode) {
			case KeyEvent.VK_W: {
				if(currentActiveControls.contains(KeyEvent.VK_A) || currentActiveControls.contains(KeyEvent.VK_D))
					p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
					
					p.setPosY(p.getPosY() - p.getM_velocity()); 
				break;
			}
			case KeyEvent.VK_A: {
				if(currentActiveControls.contains(KeyEvent.VK_W) || currentActiveControls.contains(KeyEvent.VK_S)) 
					p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
				
				p.setPosX(p.getPosX() - p.getM_velocity());
				break;
			}
			case KeyEvent.VK_S: {
				if(currentActiveControls.contains(KeyEvent.VK_A) || currentActiveControls.contains(KeyEvent.VK_D))
					p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
				
				p.setPosY(p.getPosY() + p.getM_velocity());
				break;
			} 
			case KeyEvent.VK_D: {
				if(currentActiveControls.contains(KeyEvent.VK_W) || currentActiveControls.contains(KeyEvent.VK_S))
					p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
					
				p.setPosX(p.getPosX() + p.getM_velocity()); 
				break;
			}
			case KeyEvent.VK_I, KeyEvent.VK_UP: 
				if(countRate<=0) {
					if (!p.isReload()) {
						model.bullet.add(new Bullet(p, p.getGun()));
						System.out.println(p.getAmmoLeft());
						p.ammo();
					}
					countRate = (int) (p.getGun().getRate()*1000);	
				}
				break;
			case KeyEvent.VK_J, KeyEvent.VK_LEFT: p.rotate(- p.getR_velocity()); break;
			case KeyEvent.VK_L, KeyEvent.VK_RIGHT: p.rotate(p.getR_velocity()); break;
			case KeyEvent.VK_K, KeyEvent.VK_DOWN: // reload gun			
			
			 
			}
		}
	}
	
}
