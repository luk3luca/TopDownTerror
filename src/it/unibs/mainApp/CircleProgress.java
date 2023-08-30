package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;
//OK
public class CircleProgress extends JProgressBar implements Serializable {
	
	
	public CircleProgress() {
		super();
		setPreferredSize(new Dimension(60, 60));
		setBorderPainted(false);
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
//			g2.drawString(Integer.toString(p.getAmmoLeft()), c.getWidth()/2 - 5 ,c.getHeight()/2 + 5);
		}
	}
	
	public void setBar(Player p) {
		
		if(!p.isReloading()) {
			setMinimum(0);
			setMaximum(p.getGun().getMaxAmmo());
			setValue(p.getAmmoLeft());
		}
		else {
			setMinimum((int) p.getStartReloadTime());
			setMaximum((int) p.getStartReloadTime() + (int)(p.getGun().getReload()*1000));
			setValue((int) System.currentTimeMillis()); 	
		}	
	}

	
	
}
