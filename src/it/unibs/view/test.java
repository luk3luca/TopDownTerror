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

public class test {

	private JFrame frame;
	private JTable table;

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
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBorder(null);
		table.setCellSelectionEnabled(true);
		table.setFont(new Font("Tahoma", Font.BOLD, 16));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"name ", "kills", "deaths"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(36);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.setBounds(51, 59, 291, 103);
		panel.add(table);
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
