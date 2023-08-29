package it.unibs.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unibs.mainApp.BaseModel;
import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.Bullet;
import it.unibs.mainApp.Player;

public class ClientKeyboard extends BaseModel implements KeyListener {

	private Player p;
	private boolean isShooting = false;
	private ExecutorService ex;

	public ClientKeyboard(Player p, ExecutorService ex) {
		this.p = p;
		this.ex = ex;
	} 


	@Override
	public void keyTyped(KeyEvent e) {
	}


	@Override
	public void keyPressed(KeyEvent e) {
				
		ex.execute(() -> {
					
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
					p.setYSpeed(-Player.DEFAULT_Y_SPEED);
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
			}
		});
					
	ex.execute(() -> {

		switch(e.getKeyCode()) {
			case KeyEvent.VK_I, KeyEvent.VK_UP:
				isShooting = true;
            // thread dedicato per lo sparo
            new Thread(() -> {
                while (isShooting) {
                    synchronized (p) {
                        p.shooting();
                    }
                    try {
                        Thread.sleep(p.getTempoDiSparo());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
				p.shooting();
				break;
			case KeyEvent.VK_J, KeyEvent.VK_LEFT:
				p.setRotation(-Player.R_VELOCITY);
				break;
			case KeyEvent.VK_L, KeyEvent.VK_RIGHT:
				p.setRotation(Player.R_VELOCITY);
				break;
			case KeyEvent.VK_K, KeyEvent.VK_DOWN:
				try {
					p.reloadAmmo();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			break;
		}
	});
				
			
	 this.fireValuesChange();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
			ex.execute(() -> {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_S:
					p.setYSpeed(0);
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_D:
					p.setXSpeed(0);
					break;
				case KeyEvent.VK_J:
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_L:
				case KeyEvent.VK_RIGHT:
					p.setRotation(0);
					break;
				case KeyEvent.VK_I:
				case KeyEvent.VK_UP:
					isShooting = false;
					
			}
	
				this.fireValuesChange();
		});
	}
	
//	private void closed() {
//		ex.shutdown();
//	}
	
	//public ArrayList<Integer> currentActiveControls = new ArrayList<>();
	
//	@Override
//	public void keyPressed(KeyEvent e) {
//		if(!currentActiveControls.contains(e.getKeyCode())) {
//			currentActiveControls.add(e.getKeyCode());
//		}
//		this.applyControls();
//		this.fireValuesChange();
//	}

//	@Override
//	public void keyReleased(KeyEvent e) {
//		
//		switch(e.getKeyCode()) {
//		case KeyEvent.VK_W:
//		case KeyEvent.VK_S:
//			p.setYSpeed(0);
//			break;
//		case KeyEvent.VK_A:
//		case KeyEvent.VK_D:
//			p.setXSpeed(0);
//			break;
//		case KeyEvent.VK_J:
//		case KeyEvent.VK_LEFT:
//		case KeyEvent.VK_L:
//		case KeyEvent.VK_RIGHT:
//			p.setRotation(0);
//			break;
//	}
//		currentActiveControls.remove((Object)e.getKeyCode());
//		this.fireValuesChange();
//	}

	
//	public void applyControls() {
//		for (Integer keycode: currentActiveControls) {
//			switch(keycode) {
//				case KeyEvent.VK_W: {
//						p.setYSpeed(-Player.DEFAULT_Y_SPEED);
//					break;
//				}
//				case KeyEvent.VK_A: {
//						p.setXSpeed(-Player.DEFAULT_X_SPEED);
//					break;
//				}
//				case KeyEvent.VK_S: {
//						p.setYSpeed(Player.DEFAULT_Y_SPEED);
//					break;
//				} 
//				case KeyEvent.VK_D: {
//						p.setXSpeed(Player.DEFAULT_X_SPEED);
//					break;
//				}
//				case KeyEvent.VK_I, KeyEvent.VK_UP: {
//			    	p.shooting();
//					break; 
//				}
//				case KeyEvent.VK_J, KeyEvent.VK_LEFT:
//					p.setRotation(-Player.R_VELOCITY);
//					break;
//				case KeyEvent.VK_L, KeyEvent.VK_RIGHT:
//					p.setRotation(Player.R_VELOCITY);
//					break;
//				case KeyEvent.VK_K, KeyEvent.VK_DOWN: {
//					try {
//						p.reloadAmmo();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					} 
//					break;
//				}
//			}
//		}
//	
//	}

	


}
