package it.unibs.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import it.unibs.mainApp.BaseModel;
import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.Player;

public class ClientKeyboard extends BaseModel implements KeyListener {

		private Player p;
	//	private Battlefield model;

		public ClientKeyboard(Player p) {
			this.p = p;
	//	this.model = model;
		} 
		
		public ArrayList<Integer> currentActiveControls = new ArrayList<>();
		@Override
		public void keyTyped(KeyEvent e) {
		}  

//		@Override
//		public void keyPressed(KeyEvent e) {
//			if(!currentActiveControls.contains(e.getKeyCode()))
//				currentActiveControls.add(e.getKeyCode());
//			this.fireValuesChange();
//		}
//
//		@Override
//		public void keyReleased(KeyEvent e) {
//			currentActiveControls.remove((Object)e.getKeyCode());
//			this.fireValuesChange();
//		}
		

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				
				case KeyEvent.VK_W:
		
					p.setYSpeed(-Player.DEFAULT_Y_SPEED);
					System.out.println(p.getYSpeed());
					break;
				case KeyEvent.VK_A:
					p.setXSpeed(-Player.DEFAULT_X_SPEED);
					break;
				case KeyEvent.VK_S:
		
					p.setYSpeed(Player.DEFAULT_Y_SPEED);
					break;
				case KeyEvent.VK_D:
					p.setXSpeed(Player.DEFAULT_X_SPEED);
		
					break;
//				case KeyEvent.VK_SPACE:
//					P.shoot();
//					break;
			}
			
			this.fireValuesChange();
		}
		

		@Override
		public void keyReleased(KeyEvent e) {
		
			switch(e.getKeyCode()) {
			
				case KeyEvent.VK_W:
				case KeyEvent.VK_S:
					
					p.setYSpeed(0);
					break;
		
				case KeyEvent.VK_A:
				case KeyEvent.VK_D:
				
					p.setXSpeed(0);
		
					break;
			}
			
			this.fireValuesChange();
		}
		
			
		public void applyControls() {
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
//					case KeyEvent.VK_I, KeyEvent.VK_UP: {
//		                try {
//							if(p.shoot()) {
//							    model.bullet.add(new Bullet(p, p.getGun()));
//							    System.out.println(p.getAmmoLeft());
//							}
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//		                break; 
//		            }
		            case KeyEvent.VK_J, KeyEvent.VK_LEFT: p.rotate(- p.getR_velocity()); break;
		            case KeyEvent.VK_L, KeyEvent.VK_RIGHT: p.rotate(p.getR_velocity()); break;
		            case KeyEvent.VK_K, KeyEvent.VK_DOWN: {
		                try {
							p.reloadAmmo();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
		                break;
		            }
				}
			}
			
		}

		
	}
