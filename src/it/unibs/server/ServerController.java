package it.unibs.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;

import it.unibs.mainApp.Battlefield;
import it.unibs.pajc.baseGame.Player;

public class ServerController {
	//private GameView view
	private Battlefield battlefield;
	private JFrame frame;
	private ExecutorService executor;
	
	public ServerController(JFrame frame) {
		this.frame = frame;
		
	}
	
	public void initializeGame() {
	}
	
	public void startServer() {
	}
	
	private void listenToClient() {
	}
	
	private void sendToClient(Serializable obj) {
	}
	
	// (MODEL -----> CONTROLLER) -----> VIEW
	private void modelUpdated(ChangeEvent e) {
	}
	
}
