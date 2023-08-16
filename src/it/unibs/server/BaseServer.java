package it.unibs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import it.unibs.mainApp.Battlefield;




public class BaseServer {
	private Battlefield model;
	private JFrame frame;
	
	public BaseServer(JFrame frame) {
		this.frame = frame;
	}
	
	
	public void startServer() {
		model = new Battlefield();
		
		System.out.println("Server in avvio...");
		
		try (
				ServerSocket server = new ServerSocket(1234);
		) {
//			server.setSoTimeout(1000);
			int id = 0;
			long tempoScadenza = System.currentTimeMillis() + 2000; // 10 secondi in millisecondi

	        while (!(id==2)) {
				
				Socket client = server.accept();
				MyProtocol clientProtocol = new MyProtocol(client, model, id++);
				
				Executor clientThread  = Executors.newFixedThreadPool(6);
				clientThread.execute(clientProtocol);
			}
			
		} catch(IOException e) {
			System.err.printf("\nErrore di comunicazione: %s\n", e);
		}
		
		System.out.println("Server terminato...");
	
	}

}
