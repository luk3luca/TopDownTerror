package it.unibs.mainApp;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import it.unibs.view.*;

public class GameController {
	protected JFrame frame;
	protected JFrame frame2;
	protected Battlefield battlefield;
	
	public GameController(JFrame frame, Battlefield model) {
		this.frame = frame;
		frame.setBackground(Color.black);
		frame.setBounds(100, 100, 1200, 1400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MapViewport map = new MapViewport(model, model.player[0]);
		frame.getContentPane().add(map, BorderLayout.CENTER);
		
//		PnlMap pnlMap = new PnlMap(model);
//		frame.getContentPane().add(pnlMap, BorderLayout.EAST);		
		
		//GESTIONE TASTIERA
		MyKeyboard hostKeys = new MyKeyboard(model.player[0], model);
		map.addKeyListener(hostKeys);
		
		Timer t = new Timer(10, e->{ // 10 MILLISECONDI
			hostKeys.applyControls();
			model.stepNext();
			map.repaint();
		});
		t.start(); 
		
	}
}
