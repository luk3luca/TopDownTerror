package it.unibs.mainApp;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
//OK
import java.io.Serializable;
public class Gun extends MovingObject implements Cloneable, Serializable {
	public final static double GUN_WIDTH = 4.;
	
	public final static Gun AR = new Gun("AK-47", 4, 0.4, 25, 4., 20, new Color(128, 0, 32));					// #800020
	public final static Gun PISTOL = new Gun("Tac-45", 4, 0.4, 12, 3., 15, new Color(0, 191, 255));				// #00BFFF
	public final static Gun SMG = new Gun("MP7", 3, 0.2, 30, 4., 12, new Color(130, 38, 176));					// #8226b0
	public final static Gun SNIPER = new Gun("Ballista", 6, 3., 7, 6., 80, new Color(0,0,102)); 				// #000066
	public final static Gun BOW = new Gun("Bow", 6, 1, 1, 5., 100, new Color(255,255,255));					// #FFFFFF
	public final static Gun SHOTGUN = new Gun("Il pompa del nonno", 2, 0.8, 5, 3., 50, new Color(135, 38, 87));	// #872657
	
	public static Gun[] guns = {AR, PISTOL, SMG, SNIPER, BOW, SHOTGUN};
	public static int nGuns = guns.length;
	
	private String name;
	private double maxRange;
	private double range;
	private double rate;
	private double reload;
	private int maxAmmo;
	private int dmg;
	protected Shape shape;
	
	public Gun(String name, double maxRange, double rate, int maxAmmo, double reload, int dmg, Color color) {
		super();
		this.name = name;
		this.maxRange = maxRange;
		this.range = maxRange;
		this.rate = rate;
		this.maxAmmo = maxAmmo;
		this.dmg = dmg;
		this.color = color;
		this.reload = reload;
	}
	
	public void setPlayerInfo(double posX, double posY, double angle) {
		this.posX = posX;
		this.posY = posY;
		this.angle = angle;
	}
	
	@Override
	public Shape getShape() {
		Rectangle2D.Double gunArea = new Rectangle2D.Double(Battlefield.BATTLEFIELD_TILEDIM/4, 
				   Battlefield.BATTLEFIELD_TILEDIM/4 - GUN_WIDTH/2, 
				   Battlefield.BATTLEFIELD_TILEDIM * this.range,
				   GUN_WIDTH);
		
		this.shape = gunArea;
		AffineTransform t = new AffineTransform();
		
		t.translate(posX, posY);
		t.rotate(angle,Battlefield.BATTLEFIELD_TILEDIM/4, 
				Battlefield.BATTLEFIELD_TILEDIM/4);
		
		return t.createTransformedShape(shape);
	}
	
	@Override
    public Gun clone() throws CloneNotSupportedException {
        try {
            Gun cloned = (Gun) super.clone();
            //cloned.shape = (Shape) this.shape.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
	
	public void resetRange() {
		this.range = this.maxRange;
	}
	
	/*---GETTERS AND SETTERS---*/
	public static Gun getSniper() {return SNIPER;}
	public static Gun getAr() {return AR;}
	public static Gun getShotgun() {return SHOTGUN;}
	public static Gun getPistol() {return PISTOL;}
	public static Gun getSmg() {return SMG;}
	public static Gun getBow() {return BOW;}
	
	public void setName(String name) {this.name = name;}
	public void setMaxRange(double maxRange) {this.maxRange = maxRange;}
	public void setRate(double rate) {this.rate = rate;}
	public void setReload(double reload) {this.reload = reload;}
	public void setMaxAmmo(int maxAmmo) {this.maxAmmo = maxAmmo;}
	public void setDmg(int dmg) {this.dmg = dmg;}

	public double getMaxRange() {return maxRange;}
	public double getRange() {return range;}
	public void setRange(double range) {this.range = range;}
	public String getName() {return name;}	
	public double getRate() {return rate;}
	public int getMaxAmmo() {return maxAmmo;}
	public double getReload() {return reload;}
	public int getDmg() {return dmg;}
	public Color getColor() {return color;}

}
