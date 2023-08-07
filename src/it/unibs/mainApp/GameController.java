package it.unibs.mainApp;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import it.unibs.view.*;

public class GameController {
	protected JFrame frame;
	protected JFrame frame2;
	protected Battlefield battlefield;
	protected ArrayList<Tile> tiles;
	
	public GameController(JFrame frame, ArrayList<Tile> t ) {
		this.tiles = t;
		this.frame = frame;
//		frame.setBackground(Color.black);
//		frame.setBounds(100, 100, 1200, 900);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 
//		GridLayout layout = new GridLayout(0,2,400, 0);
//
//		frame.setLayout(layout);
//		
//		PlayerViewport playerViewport = new PlayerViewport(model, model.player[0]);
//		frame.add(playerViewport);
//		 
//		MapViewport miniMap = new MapViewport(model);
//		frame.add(miniMap);

		frame.getContentPane().removeAll();
		frame.dispose();
		frame = new JFrame("CLIENT VIEW");
		
		frame.setBounds(100, 100, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		
		
		
		
		frame.getContentPane().setEnabled(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {900, 0, 400};
		gridBagLayout.rowHeights = new int[]{900, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		PlayerViewport playerViewport = new PlayerViewport(battlefield,tiles);
		GridBagConstraints gbc_playerViewport = new GridBagConstraints();
		gbc_playerViewport.fill = GridBagConstraints.BOTH;
		gbc_playerViewport.insets = new Insets(0, 0, 5, 5);
		gbc_playerViewport.gridx = 0;
		gbc_playerViewport.gridy = 0;
		frame.getContentPane().add(playerViewport, gbc_playerViewport);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new GridLayout(3, 1, 0, 10));
		
		MapViewport mapViewport = new MapViewport(tiles);
		panel.add(mapViewport);
		
		
		//2 PANNELLI DA AGGIUNGERE : PLAYERINFO --- GAMEINFO
		//MapViewport mapViewport_1 = new MapViewport(model);
		//panel.add(mapViewport_1);
		
		//MapViewport mapViewport_2 = new MapViewport(model);
		//panel.add(mapViewport_2);
		
		
		
		
//		//GESTIONE TASTIERA
//		MyKeyboard hostKeys = new MyKeyboard(model.player[0], model);
//		playerViewport.addKeyListener(hostKeys);
//		
//		Timer t = new Timer(10, e->{ // 10 MILLISECONDI
//			hostKeys.applyControls();
//			model.stepNext();
//			
//			playerViewport.repaint();
//			mapViewport.repaint();
//		});
//		t.start(); 	
		
	}
}
