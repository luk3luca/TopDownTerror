package it.unibs.client;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.Player;
import it.unibs.mainApp.Tile;
import it.unibs.server.ServerController;
import it.unibs.view.MapViewport;
import it.unibs.view.PlayerViewport;


public class ClientController {

	Socket clientSocket;

	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
	
//	private GameView view;
	private ExecutorService executor;
	private JFrame frame;
	private ArrayList<Tile> tiles = new ArrayList<>();
	private Player[] players = new Player[6];
	private Player localPlayer = new Player();
	public PlayerViewport playerViewport;
	public MapViewport mapViewport ;
	int playerIndex=0;
	ClientKeyboard kb;
	public ClientController(JFrame frame) {
		
		this.frame = frame;
	}
	
	
	public void initializeGame() {

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
		
//		mapViewport = new MapViewport(tiles, players);
//		panel.add(mapViewport);
		
		playerViewport.setObjects(tiles, players,0);
		

		
		 try {
			tiles = (ArrayList<Tile>) objInputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executor = Executors.newFixedThreadPool(1); // crea un pool thread
		executor.execute(this::listenToServer);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		kb = new ClientKeyboard(localPlayer);
		
		kb.addChangeListener(this::sendToServer);
		playerViewport.addKeyListener(kb);
		
		
	}
	
	public boolean connectToServer() {
		
		boolean isConnected = false;
		
		try {
			
			clientSocket = new Socket("127.0.0.1", ServerController.PORT_NUMBER);
			
			objInputStream = new ObjectInputStream(clientSocket.getInputStream());
			objOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			
			isConnected = true;
			
			initializeGame();

		} catch(UnknownHostException e) {
			
			System.err.println("IP address of the host could not be determined : " + e.toString());
			
		} catch(IOException e) {
			
			System.err.println("Error in creating socket: " + e.toString());
			
		}
		
		return isConnected;
	}


	private void listenToServer() {
			
		try {
			
			while(clientSocket.isClosed() == false) {
				
				Player[] tmpPlayers = (Player[]) objInputStream.readObject();
			    
			    players = new Player[tmpPlayers.length];
			    System.arraycopy(tmpPlayers, 0, players, 0, tmpPlayers.length);
				
			  
			    playerViewport.setObjects(tiles, players,0);
				playerViewport.revalidate();
				playerViewport.repaint();
			}
//				System.out.println("Data received");
			// immettere l'oggetto nel model
			
			

		} catch (IOException e) {

			System.out.println(e.toString());
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void sendToServer(ChangeEvent c) {
		
		try {
			objOutputStream.writeUnshared(localPlayer);
			objOutputStream.flush();
			System.out.println(localPlayer.getXSpeed());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void viewUpdated(PropertyChangeEvent e) {
		
		// quando la view viene aggiornata potrebbe essere il momento migliore per lanciare l'aggiornamento del background
		//controller.update(e);
	}
}


































//package it.unibs.client;
//
//
//import java.io.*;
//import java.net.*;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import javax.swing.JFrame;
//import javax.swing.Timer;
//import javax.swing.event.ChangeEvent;
//
//import it.unibs.mainApp.Bullet;
//import it.unibs.mainApp.GameController;
//import it.unibs.mainApp.Player;
//import it.unibs.mainApp.PnlMap;
//import it.unibs.mainApp.Tile;
//import it.unibs.server.ServerController;
//
//public class ClientController {
//	
//	Socket clientSocket;
//
//	private ObjectInputStream objInputStream;
//	private ObjectOutputStream objOutputStream;
//	private ExecutorService executor;
//	private JFrame frame;
//	
//	private ArrayList<Tile> tiles;
//	private Player[] players;
//	private GameController controller;
//	ClientKeyboard kb;
//	
//	public ClientController(JFrame frame) {
//		
//		this.frame = frame;
//	}
//	
//	public boolean connectToServer() {
//		
//		boolean isConnected = false;
//		
//		try {
//			
//			clientSocket = new Socket("127.0.0.1", ServerController.PORT_NUMBER);
//			
//			objInputStream = new ObjectInputStream(clientSocket.getInputStream());
//			objOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//			
//			isConnected = true;
//			initializeGame();
//
//		} catch(UnknownHostException e) {
//			
//			System.err.println("IP address of the host could not be determined : " + e.toString());
//			
//		} catch(IOException e) {
//			
//			System.err.println("Error in creating socket: " + e.toString());
//			
//		}
//		
//		return isConnected;
//	}
//
//	public void initializeGame() {
//	
//		listenToServer();
//			
//		int playerIndex = 0;
//		controller = new GameController(frame, tiles,players, playerIndex);
//		kb = new ClientKeyboard(players[playerIndex]);	
//		kb.addChangeListener(this::sendToServer);
//		controller.playerViewport.addKeyListener(kb);
//		
//
////		 Timer gameTimer;
////		
////			gameTimer = new Timer(30, e -> {
////				kb.applyControls();
////				controller.playerViewport.revalidate();
////		    controller.mapViewport.revalidate();
////		    controller.playerViewport.repaint();
////		    controller.mapViewport.repaint();
////		    });
////			
////			gameTimer.start();
//		
//	}
//	
//	private void sendToServer(ChangeEvent e) {
//
//		try {
//
//			objOutputStream.writeUnshared(kb.currentActiveControls);
//			objOutputStream.flush();
//			
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//	}
//	
//	
//	
//	private void listenToServer() {
//		
//		try {
//			
//				tiles =(ArrayList<Tile>) objInputStream.readObject();
//				Player[] tmpPlayers = (Player[]) objInputStream.readObject();
//			    
//			    players = new Player[tmpPlayers.length];
//			    System.arraycopy(tmpPlayers, 0, players, 0, tmpPlayers.length);
//			    
//			    if(this.controller!=null) {
//			    	controller.playerViewport.revalidate();
//				    controller.mapViewport.revalidate();
//				    controller.playerViewport.repaint();
//				    controller.mapViewport.repaint();
//			    
//			    
//			}
//			
//
//		} catch (IOException e) {
//
//			System.out.println(e.toString());
//		
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}