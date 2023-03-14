package it.unibs.view;

import java.awt.*;

import it.unibs.mainApp.*;

public class MapViewport extends PnlMap {
    private static final long serialVersionUID = 1L;

    public MapViewport(Battlefield model) {
    	super(model);
        this.setFocusable(true);	
		this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.scale(1/6., 1/6.);
		printMap(g2);
		g2.scale(6., 6.);
    }
}