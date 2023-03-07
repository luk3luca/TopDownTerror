package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CircleProgress extends JProgressBar {
	
	private Player p;
	public CircleProgress(Player p) {
		super();
		this.p = p;
		setPreferredSize(new Dimension(60, 60));
		setUI(new CustomProgressBarUI());
	}

	private class CustomProgressBarUI extends BasicProgressBarUI {
		@Override
		public void paint(Graphics g, JComponent c) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int arcAngle = (int) (getPercentComplete() * 360 * 1); // calculate the angle of the arc
			g2.setColor(Color.RED);
			g2.fillArc(0, 0, c.getWidth(), c.getHeight(), 0, -arcAngle);
			
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(p.getAmmoLeft()), c.getWidth()/2 - 5 ,c.getHeight()/2 + 5);
		}
	}
	
	
}
