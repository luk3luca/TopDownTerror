package it.unibs.mainApp;

import java.util.Arrays;
import java.util.Random;

public class MapMatrix {
	private static final int HEIGHT = 64;
	private static final int WIDTH = 80;
	private static final double WALL_PROBABILITY = 0.2;
	
	public static int[][] matrix = new int[HEIGHT][WIDTH];
	
	public static void main(String[] args) {
		//fillWithZeros();
		fillTheMap();
		print();
	}
	
	private static void fillWithZeros() {
		for(int[] a: matrix)
			Arrays.fill(a, 0);
	}
	
	private static void fillTheMap() {
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				matrix[i][j] = WallOrNot();
			}
		}
	}

	
	private static void print() {
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	private static int WallOrNot() {
		return new Random().nextFloat() > WALL_PROBABILITY ? 0 : 1;
	}
	
	
	
	
}
