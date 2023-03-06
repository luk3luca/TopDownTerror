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
	
	
	 public CircleProgress() {
	        super();
	        setPreferredSize(new Dimension(100, 100));
	        setUI(new CustomProgressBarUI());
	    }

	    private class CustomProgressBarUI extends BasicProgressBarUI {

	        @Override
	        public void paint(Graphics g, JComponent c) {
	        	
	            Graphics2D g2d = (Graphics2D) g;
	            
	            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            int arcAngle = (int) (getPercentComplete() * 360 * 1); // calculate the angle of the arc
	            g2d.setColor(Color.RED);
	            g2d.fillArc(0, 0, c.getWidth(), c.getHeight(), 90, -arcAngle); // draw the arc
	            
	        	/*
	        	g2.translate(80, 80);
	            g2.rotate(Math.toRadians(270));
	            Arc2D arc = new Arc2D.Float(Arc2D.PIE);
	            arc.setFrameFromCenter(new Point(0, 0), new Point(80, 80));
	            arc.setAngleStart(1);
	            arc.setAngleExtent(-100*3.6);
	            g2.setColor(Color.red);
	            g2.draw(arc);
	            g2.fill(arc);
	            */
	        }
	    }
	    
	    
	
	
	
	

}
