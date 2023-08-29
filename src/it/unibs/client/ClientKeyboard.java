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
	public ArrayList<Integer> currentActiveControls = new ArrayList<>();

	public ClientKeyboard(Player p) {
		this.p = p;
	} 
	
	public ArrayList<Integer> getCurrentActiveControls() {
		return currentActiveControls;
	}

	public void setCurrentActiveControls(ArrayList<Integer> currentActiveControls) {
		this.currentActiveControls = currentActiveControls;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!currentActiveControls.contains(e.getKeyCode())) {
			currentActiveControls.add(e.getKeyCode());
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
		case KeyEvent.VK_J:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_L:
		case KeyEvent.VK_RIGHT:
			p.setRotation(0);
			break;
	}
		currentActiveControls.remove((Object)e.getKeyCode());
		this.fireValuesChange();
	}


	


}
