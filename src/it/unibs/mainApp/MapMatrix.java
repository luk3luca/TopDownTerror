package it.unibs.mainApp;

import java.util.Arrays;
import java.util.Random;

public class MapMatrix {
	public static final int HEIGHT = 22;
	public static final int WIDTH = 32;
	public static final int SPAWN_H = 2;
	public static final int SPAWN_W = 2;
	public static final double WALL_PROBABILITY = 0.17;
	
	public static int[][] matrix = new int[HEIGHT][WIDTH];
	
	/*
	public static void main(String[] args) {
		//fillWithZeros();
		fillTheMap();
		addSpawn();
		print();
	}
	*/
	 
	public static int[][] getMatrix() {
		fillTheMap();
		addSpawn();
		return matrix;
	}

	private static void fillTheMap() {
		Arrays.fill(matrix[0], 1);
		Arrays.fill(matrix[HEIGHT - 1], 1);
		for(int i = 1; i < HEIGHT - 1; i++) {
			for(int j = 0; j < WIDTH; j++) {
				if(j == 0 || j == WIDTH - 1)
					matrix[i][j] = 1;
				else
					matrix[i][j] = WallOrNot();
			}
		}
	}
	
	private static int WallOrNot() {
		return new Random().nextFloat() > WALL_PROBABILITY ? 0 : 1;
	}
	
	private static void addSpawn() {
		fillSpawn(1, 1);
		fillSpawn(1, WIDTH/2 - SPAWN_W/2);
		fillSpawn(1, WIDTH - SPAWN_W - 1);
		
		fillSpawn(HEIGHT - SPAWN_H - 1,1);
		fillSpawn(HEIGHT - SPAWN_H - 1, WIDTH/2 - SPAWN_W/2);
		fillSpawn(HEIGHT - SPAWN_H - 1, WIDTH - SPAWN_W - 1);
	}
	
	private static void fillSpawn(int y, int x) {
		boolean first = true;
		for(int i = y; i < y + SPAWN_H; i++) {
			for (int j = x; j < x + SPAWN_W; j++)
				if(first) {
					matrix[i][j] = 2;
					first = false;
				}
				else
					matrix[i][j] = 3;
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
}
