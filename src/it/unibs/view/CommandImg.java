package it.unibs.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import it.unibs.mainApp.Battlefield;

public class CommandImg extends JPanel {

	ImageIcon imageIcon = new ImageIcon("src/images/keyboard.png");
	public CommandImg() {
			
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.drawImage(imageIcon.getImage(),0,0,1006,371,null);
		
	}}
