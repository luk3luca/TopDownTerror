package it.unibs.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;

import javax.swing.JFrame;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.Player;


public class ServerController {
	public static final int PORT_NUMBER = 1234;
	public static final int SERVER_TIIMEOUT_MILLISECONDS = 5000;

	private Socket client;
	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
	
	public Battlefield battlefield; // metti privato
	private JFrame frame;
	private ExecutorService executor;
	
	
	
	
public boolean startServer() {
		
		boolean isConnected = false;

		try (ServerSocket server = new ServerSocket(PORT_NUMBER)) { // crea un socket server legato al port in argomento
			
			server.setSoTimeout(SERVER_TIIMEOUT_MILLISECONDS);
			client = server.accept();	// mette in ascolto il server, blocca il thread fino all'arrivo di una richiesta, all'arrivo restituisce il socket del client

			objOutputStream = new ObjectOutputStream(client.getOutputStream());
			objInputStream = new ObjectInputStream(client.getInputStream());
			
			isConnected = true;
			initializeGame();
			
			 client.close();
             server.close();

		} catch (SocketTimeoutException e) {
			
			System.err.println("Timeout scaduto, nessun client connnesso: " + e);
			
		} catch (IOException e) {
			
			System.err.println("Errore di comunicazione: " + e);
		}
		
		return isConnected;
	}
	
	public void initializeGame() {
		battlefield = new Battlefield();
		
		sendToClient(battlefield.tiles);
		sendToClient(battlefield.player);
		
		
//		for(Player p:battlefield.player)
//		{
//			System.out.println(p.getColor());
//		}
		
//		sendToClient(battlefield.player);
		
	}
	
	private void sendToClient(Serializable obj) {
		
		try {
			
			objOutputStream.writeObject(obj);
			objOutputStream.reset();

		} catch (IOException e) {

			System.err.println("Error, data not sent: " + e.toString());
		}
	}
	
	
}
