package it.unibs.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import it.unibs.mainApp.Player;

public class GameInfo extends JPanel{
	private DefaultTableModel model;
	private Player[] players;
	
	public GameInfo() {
		setLayout(null);
		
		model = new DefaultTableModel();
		
        model.addColumn("Name");
        model.addColumn("Kills");
        model.addColumn("Deaths");
        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD,18));
        table.setFont(new Font("Tahoma", Font.CENTER_BASELINE,15));
        
        table.setRowHeight(22);
        for(int i=0; i<3;i++) {
        	table.getColumnModel().getColumn(i).setPreferredWidth(15);
            table.getColumnModel().getColumn(i).setResizable(false);
            
        }
        
        table.setBorder(null);
        
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 300, 161);
        scrollPane.setPreferredSize(new Dimension(200,120));
        
        add(scrollPane);
	}
	
	
	public void setObjects(Player[] players){
		this.players = players;
		model.setRowCount(0);//mette il numero di righe=0, quindi le toglie
		for(Player p: players) {
			model.addRow(new Object[]{p.getName(),p.getKills(),p.getDeaths()});
		}
	}
	
}
