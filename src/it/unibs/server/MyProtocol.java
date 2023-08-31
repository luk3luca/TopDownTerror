package it.unibs.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.event.ChangeEvent;

import it.unibs.mainApp.*;

public class MyProtocol implements Runnable  {

	public static final int PORT_NUMBER = 1234;
	public static final int SERVER_TIIMEOUT_MILLISECONDS = 5000;
	
	private static ArrayList<MyProtocol> clientList = new ArrayList<>(); // lista in cui vengono registrati i client collegati al server 
	private Socket client;
	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
	private ExecutorService ex;
	private Battlefield model;
	private int playerIndex;
	String playerName ;
	
	public MyProtocol(Socket client, Battlefield model, int playerIndex) {
		this.model = model;
		this.playerIndex = playerIndex;
		this.client = client;
		clientList.add(this);
	}
	
	public Socket getClient() {
		return client;
	}

	public void run() {	
		// crea un socket server legato al port in argomento
		try {	
			objOutputStream = new ObjectOutputStream(client.getOutputStream());
			objInputStream = new ObjectInputStream(client.getInputStream());
			initializeGame();
		} catch (SocketTimeoutException e) {	
			System.err.println("Timeout scaduto, nessun client connnesso: " + e);
		} catch (IOException e) {
			System.err.println("Errore di comunicazione: " + e);
		}
	}
	
	public void close() {
		if(objOutputStream!=null) {
			try {
				objOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		clientList.remove(this);
	}
	
	public void initializeGame() {
		sendToClient(playerIndex);
		sendToClient(model.tiles);
//		try {
//			 playerName = (String) objInputStream.readObject();
//			
//		} catch (ClassNotFoundException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		model.addChangeListener(this::modelUpdated);
		
		ex = Executors.newFixedThreadPool(1);
		ex.execute(this::listenToClient);
		
		
		
//		model.player[playerIndex].setName(playerName);
		model.startGame();
	}
	
	private void listenToClient() {
		try {
			while(client.isClosed() == false) {
				// TODO: rimuovere tmpplayer
				Player tmpPlayer = (Player) objInputStream.readObject();
				ArrayList<Integer> keyCode = (ArrayList<Integer>) objInputStream.readObject();
				Player remotePlayer = model.player[playerIndex];

				remotePlayer.setControls(keyCode);
			}
		} catch (Exception e) {
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
		if(model.isGameOver()) {
			model.stopGame();
			ex.shutdown();
			gameOverWindow();	
		} else {			
			sendToClient(model.player);
			sendToClient(model.bullet);
		}
	}	
	
	private void gameOverWindow() {
		//
	}

	
}