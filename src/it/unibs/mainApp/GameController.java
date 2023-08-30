package it.unibs.mainApp;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

import it.unibs.view.*;

public class GameController {
	protected JFrame frame;
	protected ArrayList<Tile> tiles;
	protected ArrayList<Bullet> bullet;
	public PlayerViewport playerViewport;
	public MapViewport mapViewport ;
	public Battlefield model;
	
	public GameController(Battlefield model,JFrame frame, ArrayList<Tile> t, Player[] players , int playerIndex,ArrayList<Bullet> b) {
		this.tiles = t;
		this.frame = frame;
		this.bullet = b;
		this.model = model;
		
		frame.getContentPane().removeAll();
		frame.dispose();
		frame = new JFrame("CLIENT VIEW");
		frame.setBounds(100, 100, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {900, 0, 400};
		gridBagLayout.rowHeights = new int[]{900, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		playerViewport = new PlayerViewport();
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
		
		mapViewport = new MapViewport();
		panel.add(mapViewport);
		
		playerViewport.setObjects(tiles, players,playerIndex,bullet);
		mapViewport.setObjects(tiles, players,bullet);
		
		
		//2 PANNELLI DA AGGIUNGERE : PLAYERINFO --- GAMEINFO
		//MapViewport mapViewport_1 = new MapViewport(model);
		//panel.add(mapViewport_1);
		
		//MapViewport mapViewport_2 = new MapViewport(model);
		//panel.add(mapViewport_2);
			
		model.addChangeListener(this::modelUpdated);
		KeyListener keys = new MyKeyboard(model.player[playerIndex]);
		playerViewport.addKeyListener(keys);
		model.startGame();
		
	}
	
	private void modelUpdated(ChangeEvent e) {
		mapViewport.repaint();
		playerViewport.repaint();
	}
}