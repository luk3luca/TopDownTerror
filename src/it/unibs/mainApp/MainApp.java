package it.unibs.mainApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainApp {

//	public static final int WIDTH = 1080;
//	public static final int HEIGHT = 800;
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public MainApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PnlMap pnlMap = new PnlMap();
//		pnlMap.setBounds(100, 100, WIDTH, HEIGHT);
//		frame.setResizable(false);
		frame.getContentPane().add(pnlMap, BorderLayout.CENTER);
	}

}
