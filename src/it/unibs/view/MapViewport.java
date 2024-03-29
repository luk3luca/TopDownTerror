package it.unibs.view;

import java.awt.*;
import java.util.ArrayList;

import it.unibs.mainApp.*;

public class MapViewport extends PnlMap {
    private static final long serialVersionUID = 1L;

    public MapViewport() {
    }
    public void setObjects(ArrayList<Tile> tiles, Player[] p,ArrayList<Bullet> bullet) {
        super.setObjects(tiles, p,bullet);
    }
    
    public MapViewport(ArrayList<Tile> tiles,Player[] players,ArrayList<Bullet> bullet) {
    	super(tiles,players,bullet);
        this.setFocusable(true);	
		this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.scale(1/6., 1/6.);
		printMap(g2);
		g2.scale(6., 6.);
    }
}