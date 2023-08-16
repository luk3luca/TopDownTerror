//package it.unibs.view;
//
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import java.awt.GridLayout;
//import it.unibs.mainApp.Battlefield;
//import it.unibs.mainApp.Player;
//import java.awt.GridBagLayout;
//import java.awt.GridBagConstraints;
//import java.awt.Insets;
//import javax.swing.JPanel;
//import java.awt.FlowLayout;
//
//public class test {
//
//	private JFrame frame;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					test window = new test();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the application.
//	 */
//	public test() {
//		initialize();
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//		frame = new JFrame();
//		frame.getContentPane().setEnabled(false);
//		frame.setBounds(100, 100, 450, 300);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		GridBagLayout gridBagLayout = new GridBagLayout();
//		gridBagLayout.columnWidths = new int[] {300, 0, 200};
//		gridBagLayout.rowHeights = new int[]{261, 0, 0};
//		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
//		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
//		frame.getContentPane().setLayout(gridBagLayout);
//		
//		PlayerViewport playerViewport = new PlayerViewport((Battlefield) null, (Player) null);
//		GridBagConstraints gbc_playerViewport = new GridBagConstraints();
//		gbc_playerViewport.fill = GridBagConstraints.BOTH;
//		gbc_playerViewport.insets = new Insets(0, 0, 5, 5);
//		gbc_playerViewport.gridx = 0;
//		gbc_playerViewport.gridy = 0;
//		frame.getContentPane().add(playerViewport, gbc_playerViewport);
//		
//		JPanel panel = new JPanel();
//		GridBagConstraints gbc_panel = new GridBagConstraints();
//		gbc_panel.insets = new Insets(0, 0, 5, 0);
//		gbc_panel.fill = GridBagConstraints.BOTH;
//		gbc_panel.gridx = 2;
//		gbc_panel.gridy = 0;
//		frame.getContentPane().add(panel, gbc_panel);
//		panel.setLayout(new GridLayout(3, 1, 0, 10));
//		
//		MapViewport mapViewport = new MapViewport((Battlefield) null);
//		panel.add(mapViewport);
//		
//		MapViewport mapViewport_1 = new MapViewport((Battlefield) null);
//		panel.add(mapViewport_1);
//		
//		MapViewport mapViewport_2 = new MapViewport((Battlefield) null);
//		panel.add(mapViewport_2);
//	}
//
//}
