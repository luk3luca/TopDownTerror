package it.unibs.server;

import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.Player;

public class ServerController  {

	public static final int PORT_NUMBER = 1234;
	public static final int SERVER_TIIMEOUT_MILLISECONDS = 5000;

	private Socket client;
	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
	
	private Battlefield battlefield;
	private JFrame frame;
	private ExecutorService executor;
	ArrayList<Integer> currentActiveControls;
	public ServerController() {

	}
	
	
	public void initializeGame() {
		
		battlefield = new Battlefield();
		sendToClient(battlefield.tiles);
		battlefield.addChangeListener(this::modelUpdated);
		
		executor = Executors.newFixedThreadPool(1);
		executor.execute(this::listenToClient);
		
		battlefield.startGame();
	}
	
	
	public boolean startServer() {
		
		boolean isConnected = false;

		try (ServerSocket server = new ServerSocket(PORT_NUMBER)) { // crea un socket server legato al port in argomento
			
			server.setSoTimeout(SERVER_TIIMEOUT_MILLISECONDS);
			client = server.accept();	// mette in ascolto il server, blocca il thread fino all'arrivo di una richiesta, all'arrivo restituisce il socket del client

			objOutputStream = new ObjectOutputStream(client.getOutputStream());
			objInputStream = new ObjectInputStream(client.getInputStream());
			
			isConnected = true;
			initializeGame();

		} catch (SocketTimeoutException e) {
			
			System.err.println("Timeout scaduto, nessun client connnesso: " + e);
			
		} catch (IOException e) {
			
			System.err.println("Errore di comunicazione: " + e);
		}

		return isConnected;
	}
	
	
	private void listenToClient() {
	
		try {

			while(client.isClosed() == false) {
				Player tmpPlayer = (Player) objInputStream.readObject();
				Player remotePlayer = battlefield.player[0];
				
				remotePlayer.setXSpeed(tmpPlayer.getXSpeed());
				remotePlayer.setYSpeed(tmpPlayer.getYSpeed());
				
				System.out.println(tmpPlayer.getXSpeed());
				
			
				
//				System.out.println("xSpeed: " + battlefield.getRemotePlayer().getXSpeed() + ", ySpeed: " + battlefield.getRemotePlayer().getYSpeed());
			}

		} catch (IOException e) {

			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void sendToClient(Serializable obj) {
		
		try {
			
			objOutputStream.writeObject(obj);
			objOutputStream.reset();
			

		} catch (IOException e) {

			System.err.println("Error, data not sent: " + e.toString());
		}
	}
	
	
	// (MODEL -----> CONTROLLER) -----> VIEW
	private void modelUpdated(ChangeEvent e) {
		
		
		if(battlefield.isGameOver()) {
			
			battlefield.stopGame();
			executor.shutdown();
			
			gameOverWindow();
			
		} else {			
			sendToClient(battlefield.player);
		}
	}
	
	
	
	private void gameOverWindow() {
	
		//
	}
	
	
	private void viewUpdated(PropertyChangeEvent e) {
		
		// quando la view viene aggiornata potrebbe essere il momento migliore per lanciare l'aggiornamento del background
		//controller.update(e);
	}
	
	

	
}




	












//package it.unibs.server;
//
//import java.io.*;
//import java.net.*;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import javax.swing.JFrame;
//import javax.swing.event.ChangeEvent;
//
//import it.unibs.mainApp.Battlefield;
//import it.unibs.mainApp.Player;
//
//
//public class ServerController {
//	public static final int PORT_NUMBER = 1234;
//	public static final int SERVER_TIIMEOUT_MILLISECONDS = 5000;
//
//	private Socket client;
//	private ObjectInputStream objInputStream;
//	private ObjectOutputStream objOutputStream;
//	
//	private Battlefield model; // metti privato
//	private JFrame frame;
//	private ExecutorService executor;
//	
//	public ServerController() {
//		model = new Battlefield();
//	}
//	
//	
//public boolean startServer() {
//		
//		boolean isConnected = false;
//
//		try (ServerSocket server = new ServerSocket(PORT_NUMBER)) { // crea un socket server legato al port in argomento
//			
//			server.setSoTimeout(SERVER_TIIMEOUT_MILLISECONDS);
//			client = server.accept();	// mette in ascolto il server, blocca il thread fino all'arrivo di una richiesta, all'arrivo restituisce il socket del client
//
//			objOutputStream = new ObjectOutputStream(client.getOutputStream());
//			objInputStream = new ObjectInputStream(client.getInputStream());
//			
//			isConnected = true;
//			initializeGame();
//			
//			 client.close();
//             server.close();
//
//		} catch (SocketTimeoutException e) {
//			
//			System.err.println("Timeout scaduto, nessun client connnesso: " + e);
//			
//		} catch (IOException e) {
//			
//			System.err.println("Errore di comunicazione: " + e);
//		}
//		
//		return isConnected;
//	}
//	
//	public void initializeGame() {
//		sendToClient(model.tiles);
//		sendToClient(model.player);
//		
////		model.addChangeListener(this::modelUpdated);
//		
//		
//		
//		listenToClient();
//		
//		System.out.println("ciao");
//		
//		
////		
////		 
////		for(Player p: model.player)
////		{
////			System.out.println(p.getPosX());
////			System.out.println(p.getPosY());
////			System.out.println(p.getAngle()+"AAAAAAAA");
////		}
//		
//		
////		sendToClient(battlefield.player);
//		
//	}
//	private void modelUpdated(ChangeEvent e) {
//		sendToClient(model.player);
//	}
//	
//	private void sendToClient(Serializable obj) {
//		
//		try {
//			
//			objOutputStream.writeObject(obj);
//			objOutputStream.reset();
//
//		} catch (IOException e) {
//
//			System.err.println("Error, data not sent: " + e.toString());
//		}
//	}
//	
//	private void listenToClient() {
//		
//		try {
//
//			while(client.isClosed() == false) {
//				
//				
//				
//				ArrayList<Integer> currentActiveControls = (ArrayList<Integer>) objInputStream.readObject();
//				model.addControls(currentActiveControls);
//				
//				System.out.println(currentActiveControls);
//				model.stepNext();
//				
//				
//				
////				Player tmpPlayers = (Player) objInputStream.readObject();
//				
//				
////				Player tmpPlayer = (Player) objInputStream.readObject();
////				Player remotePlayer = model.getRemotePlayer();
////				
////				remotePlayer.setXSpeed(tmpPlayer.getXSpeed());
////				remotePlayer.setYSpeed(tmpPlayer.getYSpeed());
////				
////				if(tmpPlayer.isShooting())
////					remotePlayer.shoot();
////				
////				System.out.println("xSpeed: " + battlefield.getRemotePlayer().getXSpeed() + ", ySpeed: " + battlefield.getRemotePlayer().getYSpeed());
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	
//	
//}