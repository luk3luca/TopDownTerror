package it.unibs.mainApp;

<<<<<<< Updated upstream
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainApp {
	
=======
import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import it.unibs.client.ClientController;
import it.unibs.server.ServerController;

//aggiunta di controller
public class MainApp {
	
	GameController controller;
	ClientController clientController;
	ServerController serverController;
	public JLabel lblDescription;
	
>>>>>>> Stashed changes
	private JFrame frame;

	public static void main(String[] args) {
		System.out.println("start");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
<<<<<<< Updated upstream
	} 
	Battlefield model;
	public MainApp() {
		model = new Battlefield();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, Battlefield.BATTLEFIELD_WIDTH, Battlefield.BATTLEFIELD_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		
		PnlMap pnlMap = new PnlMap(model);
		frame.getContentPane().add(pnlMap, BorderLayout.CENTER);
	}
=======
	}
	
	public MainApp() {
//		frame = new JFrame();
//		model = buildBattlefield();
//		controller = new GameController(frame, model.tiles);
		
		startGameMenu();
		
	}

	private Battlefield buildBattlefield() {
		return new Battlefield();
	}

	//-----------------------------------
	private void startGameMenu() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 985, 452);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.requestFocus();
		frame.setResizable(false);
		
		
		
		
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(Color.GRAY);
		frame.getContentPane().add(menuPanel, BorderLayout.CENTER);
		menuPanel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Top Down Terror");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial Black", Font.PLAIN, 70));
		lblTitle.setBounds(164, 16, 673, 153);
		menuPanel.add(lblTitle);
		
		JButton btnSinglePlayer = new JButton("SINGLE PLAYER");
		btnSinglePlayer.setFont(new Font("Arial Black", Font.PLAIN, 40));
		btnSinglePlayer.setBounds(137, 203, 728, 96);
		menuPanel.add(btnSinglePlayer);
//		btnSinglePlayer.addActionListener(this::startLocalGame);
		
		JButton btnHostGame = new JButton("SERVER");
		btnHostGame.addActionListener(this::hostGame);
		btnHostGame.setFont(new Font("Arial Black", Font.PLAIN, 40));
		btnHostGame.setBounds(137, 301, 364, 96);
		menuPanel.add(btnHostGame);
		
		JButton btnJoinGame = new JButton("JOIN GAME");
		btnJoinGame.addActionListener(this::joinGame);
		btnJoinGame.setFont(new Font("Arial Black", Font.PLAIN, 40));
		btnJoinGame.setBounds(501, 301, 364, 96);
		menuPanel.add(btnJoinGame);
		
		lblDescription = new JLabel("");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDescription.setBounds(236, 457, 728, 30);
		menuPanel.add(lblDescription);
	}
	
	
	

	
	private void hostGame(ActionEvent e) {
		
		serverController = new ServerController();
		
		lblDescription.setText("Waiting for a client to connect...");
		frame.revalidate();
		frame.repaint();

		if(serverController.startServer() == false) {
			
			lblDescription.setText("Timeout scaduto: nessun client si è connesso");
		} else {
			
			lblDescription.setText("Client connesso");
		}
	}
	
	
	private void joinGame(ActionEvent e) {

		clientController = new ClientController(frame);
		
		lblDescription.setText("Looking for a host to connect to...");
		
		frame.revalidate();
		frame.repaint();
		
		if(clientController.connectToServer() == false) {
			lblDescription.setText("Couldn't connect to server");

		} else {
	
			lblDescription.setText("Connected to server");
		}
		
	}

>>>>>>> Stashed changes

}
