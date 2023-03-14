package it.unibs.mainApp;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import it.unibs.view.*;

public class GameController {
	protected JFrame frame;
	protected JFrame frame2;
	protected Battlefield battlefield;
	
	public GameController(JFrame frame, Battlefield model) {
		this.frame = frame;
		frame.setBackground(Color.black);
		frame.setBounds(100, 100, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		PlayerViewport playerViewport = new PlayerViewport(model, model.player[0]);
		frame.add(playerViewport, BorderLayout.CENTER);
		 
//		MapViewport miniMap = new MapViewport(model);
//		frame.add(miniMap, BorderLayout.CENTER);

		//GESTIONE TASTIERA
		MyKeyboard hostKeys = new MyKeyboard(model.player[0], model);
		playerViewport.addKeyListener(hostKeys);
		
		Timer t = new Timer(10, e->{ // 10 MILLISECONDI
			hostKeys.applyControls();
			model.stepNext();
			
			playerViewport.repaint();
//			miniMap.repaint();
		});
		t.start(); 
		
	}
}
