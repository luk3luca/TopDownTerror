package it.unibs.mainApp;

import java.awt.*;

public enum Color_Number {
	RED(1, Color.RED),
	YELLOW(2, Color.YELLOW),
	MAGENTA(3, Color.MAGENTA),
	GREEN(4, Color.GREEN),
	ORANGE(5, Color.ORANGE),
	CYAN(6, Color.CYAN);

	private int number;
    private Color color;

    Color_Number(int number, Color color) {
        this.number = number;
        this.color = color;
    }
	
	public Color getColor(int i) {
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
	
	public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public static Color_Number fromNumber(int number) {
        for (Color_Number cn : Color_Number.values()) {
            if (cn.number == number) {
                return cn;
            }
        }
        throw new IllegalArgumentException("Invalid color number: " + number);
    }

}
