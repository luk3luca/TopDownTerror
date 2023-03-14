package it.unibs.mainApp;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.Timer;
import it.unibs.view.MapViewport;

public class GameController {
	protected JFrame frame;
	protected JFrame frame2;
	protected Battlefield battlefield;
	
	public GameController(JFrame frame, Battlefield model) {
		this.frame = frame;
		frame.setBackground(Color.black);
		frame.setBounds(100, 100, Battlefield.BATTLEFIELD_WIDTH, Battlefield.BATTLEFIELD_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PnlMap pnlMap = new PnlMap(model);
		frame.getContentPane().add(pnlMap, BorderLayout.CENTER);		
		
//		MapViewport map = new MapViewport(model, model.player[0]);
//		frame.getContentPane().add(map, BorderLayout.CENTER);
//		frame.setBounds(100, 100, Battlefield.BATTLEFIELD_HEIGHT, Battlefield.BATTLEFIELD_HEIGHT);
//		
		
		//GESTIONE TASTIERA 
		MyKeyboard hostKeys = new MyKeyboard(model.player[0], model);
		pnlMap.addKeyListener(hostKeys);
		Timer t = new Timer(10, e->{ // 10 MILLISECONDI
			hostKeys.applyControls();
		});
		t.start(); 
		
	}
}
