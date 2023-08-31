
package it.unibs.view;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.mainApp.*;

public class PlayerViewport extends PnlMap implements Serializable {
    private static final long serialVersionUID = 1L;

    private Player p;
    private Gun[] guns = Gun.guns;

    
    //
    public PlayerViewport() {
    	super();
    }

	public void setObjects(ArrayList<Tile> tiles, Player[] p, int playerIndex,ArrayList<Bullet> bullet) {
        this.p = p[playerIndex];
        super.setObjects(tiles, p,bullet);
	}
    
    
    
    public PlayerViewport(  ArrayList<Tile> tiles, Player[] p, int playerIndex, ArrayList<Bullet> bullet) {
    	super(tiles,p,bullet);
        this.p = p[playerIndex];
        this.setFocusable(true);	
		this.requestFocusInWindow();
    }
    
    
    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(p!=null) {
			// Calcola la posizione della viewport
		    int viewportSize = Battlefield.BATTLEFIELD_HEIGHT / 2; // dimensione della viewport quadrata
		    int viewportX = (int) (p.getPosX() - viewportSize / 2);
		    int viewportY = (int) (p.getPosY() - viewportSize / 2);
		    
		    //TRASLA LA VIEWPORT IN ALTO A SINISTRA
		    g2.translate(-viewportX, -viewportY);
		    
		    // Imposta il rettangolo di clipping sulla viewport
		    Rectangle viewport = new Rectangle(viewportX, viewportY, viewportSize, viewportSize);
		    g2.setClip(viewport);
		    
		    //RETTANGOLO NERO PER IL CONTORNO DELLA MAPPA
		    g2.setColor(Color.BLACK);
		    g2.fillRect(-Battlefield.BATTLEFIELD_HEIGHT/4,
		    			-Battlefield.BATTLEFIELD_HEIGHT/4,
		    			Battlefield.BATTLEFIELD_WIDTH + Battlefield.BATTLEFIELD_HEIGHT/2,
		    			3* Battlefield.BATTLEFIELD_HEIGHT/2);
		    
		    printMap(g2);
		    
		    // Ripristina il rettangolo di clipping
		    g2.setClip(null);
		    
//		    int offset = 0;
//
//            for(int i=0;i<guns.length;i++) {
//                g2.setColor(Color.BLUE);
//
//                if(guns[i].getName().equals(p.getGun().getName())) {
//                    g2.setColor(Color.RED);
//                }
//
//                g2.setFont(new Font("Tahoma", Font.BOLD, 20));
//                g2.drawString((i+1) + ":"+ guns[i].getName() + "    " , -240 + i*140, 540);
//
//                g2.setColor(Color.BLUE);
//            }

		}
		
    }
}

