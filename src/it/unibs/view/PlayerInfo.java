package it.unibs.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibs.mainApp.CircleProgress;
import it.unibs.mainApp.Player;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;


public class PlayerInfo extends JPanel{
	Player p;
	JProgressBar progressBar;
	CircleProgress circle;
	
	public PlayerInfo(Player p) {
		this.p = p;
		setLayout(null);
		circle = new CircleProgress();
		inizializeLifeBar();
		
		
		JLabel lblAmmo = new JLabel("AMMO: " );
		lblAmmo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAmmo.setBounds(0, circle.getHeight()/2, 100, 100);
		add(lblAmmo);
		
		circle.setBounds(80, 0, 100, 100);
		add(circle);
		
		JLabel lblLife = new JLabel("HP: ");
		lblLife.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLife.setBounds(0, 85 , 100, 100);
		add(lblLife);
		
		progressBar.setBounds(80, 120,200, 30);
		add(progressBar);
	
//        addProgressBar(this,circle,"AMMO: ");
//	    addProgressBar(this,progressBar, "Barra 2");
	   
//		
//		add(circle);
//		add(progressBar);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
	}
	
	
	
	 private static void addProgressBar(JPanel panel,JProgressBar bar, String label) {
	        JPanel subPanel = new JPanel(new BorderLayout());
	        JLabel titleLabel = new JLabel(label);

	        subPanel.add(titleLabel, BorderLayout.WEST);
	        subPanel.add(bar, BorderLayout.CENTER);
	        panel.add(subPanel);
	    }
	
	
	
	
	
	
	
	
	
	public void setObjects(Player p) {
		this.p = p;
		progressBar.setValue(p.getHp());
		circle.setBar(p);
	}
	
	public void setCircle() {
		if(!p.isReloading()) {
			circle.setMinimum(0);
			circle.setMaximum(p.getGun().getMaxAmmo());
			circle.setValue(p.getAmmoLeft());
		}
		else {
			circle.setMinimum((int) p.getStartReloadTime());
			circle.setMaximum((int) p.getStartReloadTime() + (int)(p.getGun().getReload()*1000));
			circle.setValue((int) System.currentTimeMillis()); 	
		}	
	}
	
	private void inizializeLifeBar() {
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(true);
		progressBar.setBackground(Color.WHITE);
		progressBar.setFont(new Font("Tahoma", Font.BOLD, 40));
		progressBar.setPreferredSize(new Dimension(200, 30));
		progressBar.setBorderPainted(false);
		progressBar.setUI(new BasicProgressBarUI() {
            @Override
            protected void paintString(Graphics g, int x, int y, int width, int height, int amountFull, Insets b) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    			
                g2.setColor(Color.BLACK); // Cambia il colore del testo
                g2.setFont(new Font("Tahoma", Font.BOLD, 20));
                String progressText = String.format("%d%%", (int) (progressBar.getValue()));
                FontMetrics fontMetrics = g2.getFontMetrics();
                int textWidth = fontMetrics.stringWidth(progressText);
                int textHeight = fontMetrics.getHeight();
                g2.drawString(progressText, (width - textWidth) / 2, (height + textHeight) / 2);
                g2.dispose();
            }
        });
		
		
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		if(circle!=null) {
//			circle.setBar();
//			
//			circle.repaint();
//		}
		
    }
		
	
}
