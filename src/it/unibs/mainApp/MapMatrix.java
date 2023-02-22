package it.unibs.mainApp;

import java.util.Arrays;
import java.util.Random;

public class MapMatrix {
	public static final int HEIGHT = 64;
	public static final int WIDTH = 80;
	public static final double WALL_PROBABILITY = 0.2;
	
	public static final int SPAWN_H = 4;
	public static final int SPAWN_W = 6;
	
	public static int[][] matrix = new int[HEIGHT][WIDTH];
	
	public static void main(String[] args) {
		//fillWithZeros();
		fillTheMap();
		addSpawn();
		print();
	}
	public static int[][] getMatrix() {
		return matrix;
	}
	
	/*
	private static void fillWithZeros() {
		for(int[] a: matrix)
			Arrays.fill(a, 0);
	}
	*/
	
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
	
	/*
	private static void addSpawn() {
		matrix[0][0] = 2;
		matrix[0][WIDTH/2 - SPAWN_W/2 -1] = 2;
		matrix[0][WIDTH - SPAWN_W] = 2;
		
		matrix[HEIGHT - SPAWN_H - 1][0] = 2;
		matrix[HEIGHT - SPAWN_H - 1][WIDTH/2 - SPAWN_W/2 - 1] = 2;
		matrix[HEIGHT - SPAWN_H - 1][WIDTH - SPAWN_W] = 2;
	}
	*/
	
	private static void addSpawn() {
		fillSpawn(0, 0);
		fillSpawn(0, WIDTH/2 - SPAWN_W/2);
		fillSpawn(0, WIDTH - SPAWN_W);
		
		fillSpawn(HEIGHT - SPAWN_H,0);
		fillSpawn(HEIGHT - SPAWN_H, WIDTH/2 - SPAWN_W/2);
		fillSpawn(HEIGHT - SPAWN_H, WIDTH - SPAWN_W);
	}
	
	private static void fillSpawn(int y, int x) {
		for(int i = y; i < y + SPAWN_H; i++) {
			for (int j = x; j < x + SPAWN_W; j++)
				matrix[i][j] = 2;
		}
	}
	
	
	
}