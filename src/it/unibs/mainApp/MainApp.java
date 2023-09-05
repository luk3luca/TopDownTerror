package it.unibs.mainApp;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import it.unibs.client.ClientController;
import it.unibs.server.BaseServer;
import it.unibs.view.CommandImg;
import it.unibs.view.LogoImg;

public class MainApp {
	private ClientController clientController;
	private BaseServer serverController;
	public JLabel lblDescription;
	
	private Battlefield model;
	private JFrame frame;
	private JButton btnJoinGame;
	private JTextField txtIP;
	private JTextField txtPlayerName;
	private JComboBox<Integer> comboBox;
	private JButton btnHostGame;
	private JLabel lblNewLabel;
	private JLabel lblName;
	
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
		frame.setBounds(100, 100, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(Color.GRAY);
		frame.getContentPane().add(menuPanel, BorderLayout.CENTER);
		menuPanel.setBackground(new Color(43, 43, 43));
		menuPanel.setLayout(null);
		
		
		LogoImg logoImg = new LogoImg();
		logoImg.setBounds(405, 20, 1623/4,566/4);
		logoImg.setBackground(new Color(43, 43, 43));
		menuPanel.add(logoImg);
		
//		JLabel lblTitle = new JLabel("Top Down Terror");
//		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
//		lblTitle.setForeground(new Color(178, 178, 178));
//		lblTitle.setFont(new Font("Arial Black", Font.PLAIN, 70));
//		lblTitle.setBounds(249, 16, 673, 153);
//		menuPanel.add(lblTitle);
		
		btnHostGame = new JButton("SERVER");
		btnHostGame.addActionListener(this::hostGame);
		btnHostGame.setFont(new Font("Tahoma", Font.BOLD, 60));
		btnHostGame.setBounds(194, 185, 364, 96);
		menuPanel.add(btnHostGame);
		
		btnJoinGame = new JButton("JOIN");
		btnJoinGame.addActionListener(this::joinGame);
		btnJoinGame.setFont(new Font("Tahoma", Font.BOLD, 60));
		btnJoinGame.setBounds(705, 185, 364, 96);
		menuPanel.add(btnJoinGame);
	
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 22));
		comboBox.addItem(1);
		comboBox.addItem(2);
		comboBox.addItem(3);
		comboBox.addItem(4);
		comboBox.addItem(5);
		comboBox.addItem(6);
		comboBox.setBounds(123, 185, 68, 96);
		menuPanel.add(comboBox); 
		
		txtIP = new JTextField();
		txtIP.setFont(new Font("Tahoma", Font.PLAIN, 36));
		txtIP.setBounds(194, 297, 364, 88);
		menuPanel.add(txtIP);
		frame.getContentPane().add(menuPanel);
		
		CommandImg commandImg = new CommandImg();
		commandImg.setBounds(83, 423, 1006, 371);
		menuPanel.add(commandImg);
		
		txtPlayerName = new JTextField();
		txtPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 36));
		txtPlayerName.setBounds(705, 297, 364, 88);
		menuPanel.add(txtPlayerName);
		
		lblNewLabel = new JLabel("IP: ");
		lblNewLabel.setForeground(new Color(178, 178, 178));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblNewLabel.setBounds(123, 297, 58, 88);
		menuPanel.add(lblNewLabel);
		
		lblName = new JLabel("NAME: ");
		lblName.setForeground(new Color(178, 178, 178));
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblName.setBounds(592, 297, 117, 88);
		menuPanel.add(lblName);
	}
	
	private void hostGame(ActionEvent e) {
		serverController = new BaseServer(frame,(int) comboBox.getSelectedItem());
		serverController.startServer();
	}
	
	private void joinGame(ActionEvent e) {
		String ip = txtIP.getText();
		String name = txtPlayerName.getText();
		clientController = new ClientController(frame, ip, name );
		clientController.connectToServer();
	}
}
