package it.unibs.client;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;

import it.unibs.mainApp.GameController;
import it.unibs.mainApp.Player;
import it.unibs.mainApp.PnlMap;
import it.unibs.mainApp.Tile;
import it.unibs.pajc.client.ClientKeyboard;
import it.unibs.server.ServerController;

public class ClientController {
	
	Socket clientSocket;

	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
	private ExecutorService executor;
	private JFrame frame;
	
	private ArrayList<Tile> tiles;
	private Player[] players;
	private GameController controller;
	
	public ClientController(JFrame frame) {
		
		this.frame = frame;
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

	public void initializeGame() {
//		executor = Executors.newFixedThreadPool(1); // crea un pool thread
//		executor.execute(this::listenToServer);
		
		listenToServer();
		controller = new GameController(frame, tiles,players);
		frame.getContentPane().removeAll();

		
		
		ClientKeyboard kb = new ClientKeyboard(players[0]);
		kb.addChangeListener(this::sendToServer);
		controller.playerView.addKeyListener(kb);
	
		
	}
	
	private void sendToServer(ChangeEvent e) {

		try {

			players = objOutputStream.writeUnshared( players[0]);
			objOutputStream.flush();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	private void listenToServer() {
		
		try {
			
			tiles =(ArrayList<Tile>) objInputStream.readObject();
		    players = (Player[]) objInputStream.readObject();
			
			
//			 		 
//				for(Player p: players)
//				{
//					System.out.println(p.getPosX());
//					System.out.println(p.getPosY());
//					System.out.println(p.getAngle()+"AAAAAAAA");
//				}
			
			
			System.out.println("Data received");

		} catch (IOException e) {

			System.out.println(e.toString());
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
