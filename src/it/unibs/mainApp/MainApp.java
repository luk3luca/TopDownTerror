package it.unibs.mainApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class MainApp {
	
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

}
