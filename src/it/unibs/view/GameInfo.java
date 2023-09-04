package it.unibs.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import it.unibs.mainApp.Player;

public class GameInfo extends JPanel{
	private DefaultTableModel model;
	private Player[] players;
	private Player[] p;
	
	
	
	public Player getWinner() {
//		 if (p != null  && p.length >= 6) {
//		        return p[0];
//		    } else {
//		        // Gestire il caso in cui non ci sono abbastanza elementi in p
//		    	return null;
//		    }
		return p[5];
	}


	public GameInfo() {
		setLayout(null);
		
		model = new DefaultTableModel();
		
        model.addColumn("Name");
        model.addColumn("Kills");
        model.addColumn("Deaths");
        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD,24));
        table.setFont(new Font("Tahoma", Font.CENTER_BASELINE,20));
        table.setRowHeight(27);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Aligns "Kills" column
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        for(int i=0; i<3;i++) {
        	table.getColumnModel().getColumn(i).setPreferredWidth(20);
            table.getColumnModel().getColumn(i).setResizable(false);
        }
       
        table.setBorder(null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 342, 198);
        
        add(scrollPane);
	}
	
	
	public void setObjects(Player[] players){
		if(players.length == 6) {
			this.players = players;
			this.p = selectionSort(this.players);
			model.setRowCount(0);//mette il numero di righe=0, quindi le toglie
			for(int i=5; i >= 0; i--) {
				
				model.addRow(new Object[]{p[i].getName(),p[i].getKills(),p[i].getDeaths()});
			}
		}
	}
	
	public  Player[] selectionSort(Player[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            
            for (int j = i + 1; j < n; j++) {
                if (arr[j].getKills() < arr[indiceMinimo].getKills()) {
                    indiceMinimo = j;
                }
            }
            
            // Scambia l'elemento corrente con il minimo
            Player temp = arr[i];
            arr[i] = arr[indiceMinimo];
            arr[indiceMinimo] = temp;
        }
        return arr;
    }
	
//	public Player[] reverseArray(Player[] arr) {
//	    int n = arr.length;
//	    Player[] reversedArray = new Player[n];
//
//	    for (int i = 0; i < n; i++) {
//	        reversedArray[i] = arr[n - i - 1];
//	    }
//
//	    return reversedArray;
//	}
	
}
