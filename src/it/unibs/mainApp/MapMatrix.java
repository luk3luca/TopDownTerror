package it.unibs.mainApp;

import java.util.*;

public class MapMatrix {
	public static final int HEIGHT = 22;
	public static final int WIDTH = 32;
	public static final int SPAWN_H = 2;
	public static final int SPAWN_W = 2;
	public static final double WALL_PROBABILITY = 0.17;
	
	private static final int WALL = 1;
	private static final int PAVEMENT = 0;
	private static final int SPAWN = 2;
	private static final int SPAWN_ZONE = 3;
	
	public static int[][] matrix = new int[HEIGHT][WIDTH];
	
//	public static int[][] matrix = {
//		    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//		    {1,2,3,0,0,0,0,0,0,0,0,1,0,0,0,2,3,1,0,1,0,0,0,0,0,1,1,1,0,2,3,1},
//		    {1,3,3,0,1,1,1,1,1,1,1,1,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,3,3,1},
//		    {1,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1},
//		    {1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
//		    {1,0,0,1,1,0,0,0,0,1,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
//		    {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,1,1,1,1},
//		    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1},
//		    {1,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1},
//		    {1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1},
//		    {1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
//		    {1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,1},
//		    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//		    {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,1,0,1},
//		    {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//		    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,1,0,0,1},
//		    {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
//		    {1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
//		    {1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
//		    {1,2,3,0,0,0,0,0,1,0,0,1,0,1,0,2,3,0,0,0,0,0,0,0,0,0,0,0,0,2,3,1},
//		    {1,3,3,1,1,0,1,1,0,0,0,0,0,0,0,3,3,0,0,1,0,1,0,0,0,0,0,0,0,3,3,1},
//		    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
//	};
	
	
//	public static void main(String[] args) {
//		//fillTheMap();
//		//addSpawn();
//		//System.out.println(canReachAnotherSpawn(1, 15));
//		checkMap();
//		print();
//	}
	
	 
	public static int[][] getMatrix() {
		fillTheMap();
		addSpawn();
		if(!checkMap())
			getMatrix();
		
		return matrix;
	}

	private static void fillTheMap() {
		Arrays.fill(matrix[0], 1);
		Arrays.fill(matrix[HEIGHT - 1], 1);
		for(int i = 1; i < HEIGHT - 1; i++) {
			for(int j = 0; j < WIDTH; j++) {
				if(j == 0 || j == WIDTH - 1)
					matrix[i][j] = WALL;
				else
					matrix[i][j] = WallOrNot();
			}
		}
	}
	
	private static int WallOrNot() {
		return new Random().nextFloat() > WALL_PROBABILITY ? PAVEMENT : WALL;
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
					matrix[i][j] = SPAWN;
					first = false;
				}
				else
					matrix[i][j] = SPAWN_ZONE;
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
	
	private static boolean checkMap() {
		for(int i = 1; i < HEIGHT - 1; i++) {
			for(int j = 0; j < WIDTH; j++) {
				if(matrix[i][j] == SPAWN) {
					if(!canReachAnotherSpawn(i, j)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	//Breadth-first search (BFS) algorithm
	private static boolean canReachAnotherSpawn(int startCol, int startRow) {
	    // Check if the starting position is a spawn cell
	    if (matrix[startCol][startRow] != SPAWN) {
	        return false;
	    }

	    // Set up the BFS
	    Queue<int[]> queue = new ArrayDeque<>();
	    Set<String> visited = new HashSet<>();
	    queue.offer(new int[] { startCol, startRow });
	    visited.add(startCol + "," + startRow);

	    // Start BFS
	    while (!queue.isEmpty()) {
	        int[] current = queue.poll();
	        int row = current[0];
	        int col = current[1];

	        // Check if we have reached another spawn cell
	        if (matrix[row][col] == SPAWN && (row != startCol || col != startRow)) {
	            return true;
	        }

	        // Add neighboring cells to the queue
	        if (row > 0 && (matrix[row-1][col] == SPAWN || matrix[row-1][col] == SPAWN_ZONE || matrix[row-1][col] == PAVEMENT) && !visited.contains((row-1) + "," + col)) {
	            queue.offer(new int[] { row-1, col });
	            visited.add((row-1) + "," + col);
	        }
	        if (row < HEIGHT-1 && (matrix[row+1][col] == SPAWN || matrix[row+1][col] == SPAWN_ZONE || matrix[row+1][col] == PAVEMENT) && !visited.contains((row+1) + "," + col)) {
	            queue.offer(new int[] { row+1, col });
	            visited.add((row+1) + "," + col);
	        }
	        if (col > 0 && (matrix[row][col-1] == SPAWN || matrix[row][col-1] == SPAWN_ZONE || matrix[row][col-1] == PAVEMENT) && !visited.contains(row + "," + (col-1))) {
	            queue.offer(new int[] { row, col-1 });
	            visited.add(row + "," + (col-1));
	        }
	        if (col < WIDTH-1 && (matrix[row][col+1] == SPAWN || matrix[row][col+1] == SPAWN_ZONE || matrix[row][col+1] == PAVEMENT) && !visited.contains(row + "," + (col+1))) {
	            queue.offer(new int[] { row, col+1 });
	            visited.add(row + "," + (col+1));
	        }
	    }

	    // No other spawn cells were found
	    return false;
	}
	
	
}
