package it.unibs.server;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;

import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.MapMatrix;
import it.unibs.mainApp.Player;
import it.unibs.mainApp.T_Pavement;

public class ServerController {
	
	public static final int PORT_NUMBER = 1234;
	
	
	public Battlefield model;
	private JFrame frame;
	private ExecutorService executor;
	
	public ServerController(JFrame frame) {
		this.frame = frame;
		
	}
	
	public void initializeGame() {
		
		
	}
	
	public static void main(String[] args) {
		
		Socket client;
		int[][] mapMatrix = MapMatrix.getMatrix();


		try (ServerSocket server = new ServerSocket(PORT_NUMBER)) { // crea un socket server legato al port in argomento
			
			System.out.println("In attesa di un client");
			client = server.accept();	

			System.out.println("Client connesso");
			
	        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
	         
	         oos.writeObject(mapMatrix);
	         client.close();
	         server.close();


		}  catch (IOException e) {
			
			System.err.println("Errore di comunicazione: " + e);
		}
	}
	
	private void listenToClient() {
	}
	
	private void sendToClient(Serializable obj) {
		
		
		
	}
	
	// (MODEL -----> CONTROLLER) -----> VIEW
	private void modelUpdated(ChangeEvent e) {
	}
	
}
