package it.unibs.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

import it.unibs.mainApp.CircleProgress;
import it.unibs.mainApp.Player;



public class PlayerInfo extends JPanel{
	private JProgressBar progressBar;
	private CircleProgress circle;
	
	public PlayerInfo() {
		
		circle = new CircleProgress();
		inizializeLifeBar();
		
		setLayout(null);
		
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
//		progressBar.setBounds(this.getHeight()-10, 120,200, 30);
//		add(progressBar);
	}
	
	public void setObjects(Player p) {
		progressBar.setValue(p.getHp());
		circle.setBar(p);
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
		
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
	}

}
