package it.unibs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import it.unibs.mainApp.T_Pavement;

public class ClientController {
	
	 public static void main(String[] args) throws IOException, ClassNotFoundException {
		 
		 try {
			 Socket socket = new Socket("localhost", 1234); // Indirizzo IP del server e porta
			 System.out.println("Server connesso");

		     ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		        
		     int [][] receivedMatrix = (int[][]) inputStream.readObject();
		  
		     for (int i = 0; i < receivedMatrix.length; i++) {
		            for (int j = 0; j < receivedMatrix[i].length; j++) {
		                System.out.print(receivedMatrix[i][j] + " ");
		            }
		            System.out.println();
		        }
		     socket.close();

			} catch (Exception e) {
				System.out.println("Errore");
			}
		       
	    }
	
}
