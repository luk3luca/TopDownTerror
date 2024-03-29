package it.unibs.client;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import it.unibs.mainApp.*;
import it.unibs.server.MyProtocol;
import it.unibs.view.GameInfo;
import it.unibs.view.MapViewport;
import it.unibs.view.PlayerInfo;
import it.unibs.view.PlayerViewport;


public class ClientController {
	private static final String LOCALHOST = "127.0.0.1";

	Socket clientSocket;
 
	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
	
//	private GameView view;
	private ExecutorService executor;
	private JFrame frame;
	private ArrayList<Tile> tiles = new ArrayList<>();
	private Player[] players = new Player[6];
	private Player localPlayer = new Player();
	private ArrayList<Bullet> bullet = new ArrayList<>();
	private ArrayList<Integer> keyCode;
	private String ipAddress ;
	private String playerName ;
	
	public PlayerViewport playerViewport;
	public MapViewport mapViewport ;
	public PlayerInfo playerInfo ;
	public GameInfo gameInfo;
	int playerIndex;
	ClientKeyboard kb;
	
	public ClientController(JFrame frame, String ip, String playerName) {
		this.frame = frame;
		if(ip!= "") {
			this.ipAddress = ip;
		}
		else {
			this.ipAddress = LOCALHOST;
		}
		if (playerName.equalsIgnoreCase(""))
			this.playerName = "Player" + playerIndex;
		else
			this.playerName = playerName;
		
		
	}
	
	public void initializeGame() {
		int x = frame.getX(); 
		int y = frame.getY();
		frame.getContentPane().removeAll();
		frame.dispose();
		frame = new JFrame("CLIENT VIEW");
		frame.setBounds(x, y, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {900, 0, 400};
		gridBagLayout.rowHeights = new int[]{900, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		playerViewport = new PlayerViewport();
		GridBagConstraints gbc_playerViewport = new GridBagConstraints();
		gbc_playerViewport.fill = GridBagConstraints.BOTH;
		gbc_playerViewport.insets = new Insets(0, 0, 0, 0);
		gbc_playerViewport.gridx = 0;
		gbc_playerViewport.gridy = 0;
		frame.getContentPane().add(playerViewport, gbc_playerViewport);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 30, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		mapViewport = new MapViewport();
		panel.add(mapViewport);
		
		playerInfo = new PlayerInfo();
		panel.add(playerInfo);
		gameInfo = new GameInfo();
		panel.add(gameInfo);
		
		
		try {
			playerIndex = (int) objInputStream.readObject();
			tiles = (ArrayList<Tile>) objInputStream.readObject();

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		executor = Executors.newFixedThreadPool(2);
		executor.execute(this::listenToServer);
		
		sendNameToServer();
		
		
		kb = new ClientKeyboard(localPlayer);
		keyCode = kb.getCurrentActiveControls();
		kb.addChangeListener(this::sendToServer);
		playerViewport.addKeyListener(kb);
		
	}
	
	public void connectToServer() {
		try {	
			clientSocket = new Socket(ipAddress, MyProtocol.PORT_NUMBER);
			
			objInputStream = new ObjectInputStream(clientSocket.getInputStream());
			objOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			
			initializeGame();
		} catch(UnknownHostException e) {	
			System.err.println("IP address of the host could not be determined : " + e.toString());	
		} catch(IOException e) {	
			System.err.println("Error in creating socket: " + e.toString());	
		}
	}

	private void listenToServer() {	
		try {
			while(clientSocket.isClosed() == false) {
				Player[] players = (Player[]) objInputStream.readObject();
				
				ArrayList<Bullet> bull = (ArrayList<Bullet>)objInputStream.readObject();
				bullet.clear();
				bullet.addAll(bull);    

				
				
			    playerViewport.setObjects(tiles, players,playerIndex,bull);
				playerViewport.revalidate();
				playerViewport.repaint();
				
				mapViewport.setObjects(tiles, players,bull);
				mapViewport.revalidate();
				mapViewport.repaint();
				
				
				playerInfo.setObjects(players[playerIndex]);
				

				gameInfo.setObjects(players);
			
				
				Boolean gameOver = objInputStream.readBoolean();
				
				if (gameOver) 
					gameOverWindow();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void sendToServer(ChangeEvent c) {	
		try {
			objOutputStream.writeUnshared(localPlayer);
			objOutputStream.writeUnshared(keyCode);
			objOutputStream.flush();
			localPlayer.isShoot();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendNameToServer() {	
		try {
			objOutputStream.writeUnshared(playerName);
			objOutputStream.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void gameOverWindow() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 1200, 900);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		frame.setResizable(false);
//		
//		JPanel menuPanel = new JPanel();
//		menuPanel.setBackground(Color.GRAY);
//		frame.getContentPane().add(menuPanel, BorderLayout.CENTER);
//		menuPanel.setLayout(null);
//		
//		JLabel lblGameOver = new JLabel("Game Over");
//		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
//		lblGameOver.setFont(new Font("Arial Black", Font.PLAIN, 70));
//		lblGameOver.setBounds(291, 47, 673, 153);
//		menuPanel.add(lblGameOver);
		
		
		int x = frame.getX(), y= frame.getY();
		
		frame.dispose();
		frame = new JFrame();
		frame.setBounds(x, y,1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setBackground(Color.red);
		
		
		Player winner = gameInfo.getWinner();
		
		JLabel lblGameOver = new JLabel("WINNER: " + winner.getName() );
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setFont(new Font("Arial Black", Font.PLAIN, 60));
		lblGameOver.setBounds(140, 0, 900, 300);
		frame.add(lblGameOver);
		
		gameInfo.setBounds(425, 300, 900, 300);
		frame.add(gameInfo);
		
		
		
	}
		
}