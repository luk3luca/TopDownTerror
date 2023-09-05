package it.unibs.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import it.unibs.mainApp.Battlefield;
import it.unibs.mainApp.Player;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class test {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
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
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {30, 100, 0, 2};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnNewButton_1 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 0;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton = new JButton("New button");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 2;
		panel.add(btnNewButton, gbc_btnNewButton);
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
		
		
		
		
	}
}
