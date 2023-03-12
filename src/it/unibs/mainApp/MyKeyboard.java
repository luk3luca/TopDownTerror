package it.unibs.mainApp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MyKeyboard implements KeyListener{
	private Player p;
	private Battlefield model;

	public MyKeyboard(Player p,Battlefield model) {
		this.p = p;
		this.model = model;
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

//		switch(e.getKeyCode()) {
//		case KeyEvent.VK_W: {
//			System.out.println("ww");
//			if(currentActiveControls.contains(KeyEvent.VK_A) || currentActiveControls.contains(KeyEvent.VK_D))
//				p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
//
//			p.setPosY(p.getPosY() - p.getM_velocity()); 
//			break;
//		}
//		case KeyEvent.VK_A: {
//			if(currentActiveControls.contains(KeyEvent.VK_W) || currentActiveControls.contains(KeyEvent.VK_S)) 
//				p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
//
//			p.setPosX(p.getPosX() - p.getM_velocity());
//			break;
//		}
//		case KeyEvent.VK_S: {
//			if(currentActiveControls.contains(KeyEvent.VK_A) || currentActiveControls.contains(KeyEvent.VK_D))
//				p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
//
//			p.setPosY(p.getPosY() + p.getM_velocity());
//			break;
//		} 
//		case KeyEvent.VK_D: {
//			if(currentActiveControls.contains(KeyEvent.VK_W) || currentActiveControls.contains(KeyEvent.VK_S))
//				p.setM_velocity(p.getM_velocity() / Math.sqrt(2));
//
//			p.setPosX(p.getPosX() + p.getM_velocity()); 
//			break;
//		}
//		case KeyEvent.VK_I, KeyEvent.VK_UP: {
//			try {
//				if(p.shoot()) {
//					model.bullet.add(new Bullet(p, p.getGun()));
//					System.out.println(p.getAmmoLeft());
//				}
//			} catch (InterruptedException e1) {		
//				e1.printStackTrace();
//			}
//			break;
//		}
//		case KeyEvent.VK_J, KeyEvent.VK_LEFT: p.rotate(- p.getR_velocity()); break;
//		case KeyEvent.VK_L, KeyEvent.VK_RIGHT: p.rotate(p.getR_velocity()); break;
//		case KeyEvent.VK_K, KeyEvent.VK_DOWN: {
//			try {
//				p.reloadAmmo();
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			} 
//			break;
//		}
//		}
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W: {
				p.setPosY(p.getPosY() - p.getM_velocity()); 
				break;
			}
			case KeyEvent.VK_A: {
				p.setPosX(p.getPosX() - p.getM_velocity());
				break;
			}
			case KeyEvent.VK_S: {
				p.setPosY(p.getPosY() + p.getM_velocity());
				break;
			} 
			case KeyEvent.VK_D: {
				p.setPosX(p.getPosX() + p.getM_velocity()); 
				break;
			}
			case KeyEvent.VK_I, KeyEvent.VK_UP: {
				try {
					if(p.shoot()) {
						model.bullet.add(new Bullet(p, p.getGun()));
						System.out.println(p.getAmmoLeft());
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			case KeyEvent.VK_J, KeyEvent.VK_LEFT: p.rotate(- p.getR_velocity()); break;
			case KeyEvent.VK_L, KeyEvent.VK_RIGHT: p.rotate(p.getR_velocity()); break;
			case KeyEvent.VK_K, KeyEvent.VK_DOWN: {
				try {
					p.reloadAmmo();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentActiveControls.remove((Object)e.getKeyCode());

					switch(e.getKeyCode()) {
					case KeyEvent.VK_W,KeyEvent.VK_A,KeyEvent.VK_S,KeyEvent.VK_D: {
						p.resetVelocity();
						break;
					}
		}


	}

	//		private void applyControls() throws InterruptedException {
	//			
	//			for(Integer keycode: currentActiveControls) {
	//				p.resetVelocity();
	//				
	//			}
	//		}
	//		


}
