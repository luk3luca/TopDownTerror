package it.unibs.mainApp;

import java.awt.*;

public enum TeamColors {
	RED(1, Color.RED, new Color(255,0,0,64)),
	YELLOW(2, Color.YELLOW, new Color(255,255,0,64)),
	MAGENTA(3, Color.MAGENTA, new Color(255,0,255,64)),
	GREEN(4, Color.GREEN, new Color(0,255,0,64)),
	ORANGE(5, Color.ORANGE, new Color(255,200,0,64)),
	CYAN(6, Color.CYAN, new Color(0,255,255,64));

	private int number;
    private Color color;
    private Color alpha;

    TeamColors(int number, Color color, Color alpha) {
        this.number = number;
        this.color = color;
        this.alpha = alpha;
    }
	
	public static Color getColor(int i) {
	    switch (i) {
	        case 1:
	            return Color.RED;
	        case 2:
	            return Color.YELLOW;
	        case 3:
	            return Color.MAGENTA;
	        case 4:
	            return Color.GREEN;
	        case 5:
	            return Color.ORANGE;
	        case 6:
	            return Color.CYAN;
	        default:
	            throw new IllegalArgumentException("Invalid color number: " + i);
	    }
	}
	
	public static Color getColorAlpha(int i) {
	    switch (i) {
	        case 1:
	            return new Color(255,100,100);
	        case 2:
	            return new Color(255,255,100);
	        case 3:
	            return new Color(255,100,255);
	        case 4:
	            return new Color(100,255,100);
	        case 5:
	            return new Color(255,200,100);
	        case 6:
	            return new Color(100,255,255);
	        default:
	            throw new IllegalArgumentException("Invalid color number: " + i);
	    }
	}
	
	public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public static TeamColors fromNumber(int number) {
        for (TeamColors cn : TeamColors.values()) {
            if (cn.number == number) {
                return cn;
            }
        }
        throw new IllegalArgumentException("Invalid color number: " + number);
    }

}
