package it.unibs.mainApp;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import it.unibs.client.ClientController;
import it.unibs.server.BaseServer;
import it.unibs.server.MyProtocol;

//import it.unibs.client.ClientController;
//aggiunta di controller
public class MainApp {
	GameController controller;
	ClientController clientController;
	BaseServer serverController;
	public JLabel lblDescription;
	
	Battlefield model;
	private JFrame frame;
	private JButton btnJoinGame;
	private JTextArea textArea;
	private JComboBox<Integer> comboBox;
	private JButton btnHostGame;
	
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
	}
	
	public MainApp() {
		startGameMenu();
	}

	private void startGameMenu() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 500);
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
		
		btnHostGame = new JButton("SERVER");
		btnHostGame.addActionListener(this::hostGame);
		btnHostGame.setFont(new Font("Arial Black", Font.PLAIN, 40));
		btnHostGame.setBounds(120, 201, 364, 96);
		menuPanel.add(btnHostGame);
		
		btnJoinGame = new JButton("JOIN GAME");
		btnJoinGame.addActionListener(this::joinGame);
		btnJoinGame.setFont(new Font("Arial Black", Font.PLAIN, 40));
		btnJoinGame.setBounds(529, 291, 364, 96);
		menuPanel.add(btnJoinGame);
		
		lblDescription = new JLabel("");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDescription.setBounds(236, 457, 728, 30);
		menuPanel.add(lblDescription);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 22));
		comboBox.addItem(1);
		comboBox.addItem(2);
		comboBox.addItem(3);
		comboBox.addItem(4);
		comboBox.addItem(5);
		comboBox.addItem(6);
		comboBox.setBounds(52, 201, 68, 96);
		menuPanel.add(comboBox); 
		
		textArea = new JTextArea();
		textArea.setBounds(529, 201, 364, 88);
		menuPanel.add(textArea);
	}
	
	private void hostGame(ActionEvent e) {
		serverController = new BaseServer(frame,(int) comboBox.getSelectedItem());
		serverController.startServer();
	}
	
	private void joinGame(ActionEvent e) {
		String ipAddress ="127.0.0.1"; 	
		String ip = textArea.getText();
		
		clientController = new ClientController(frame, ipAddress);
		clientController.connectToServer();
	}

	
	private void startLocalGame(ActionEvent e) {
		frame = new JFrame();
		model = new Battlefield((int) comboBox.getSelectedItem());
		controller = new GameController(model,frame, model.tiles, model.player, 0, model.bullet);
	}
}
