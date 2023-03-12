package it.unibs.mainApp;

import java.awt.BorderLayout;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameController {
	protected JFrame frame;
	protected Battlefield battlefield;
	
	public GameController(JFrame frame, Battlefield model) {
		this.frame = frame;
		
		frame.setBounds(100, 100, Battlefield.BATTLEFIELD_WIDTH, Battlefield.BATTLEFIELD_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PnlMap pnlMap = new PnlMap(model);
		frame.getContentPane().add(pnlMap, BorderLayout.CENTER);
		
		MyKeyboard hostKeys = new MyKeyboard(model.player[2], model);
		pnlMap.addKeyListener(hostKeys);
		
		Timer t = new Timer(10, e->{ // 10 MILLISECONDI
			hostKeys.applyControls();
		});
		
		t.start();
		}
}
