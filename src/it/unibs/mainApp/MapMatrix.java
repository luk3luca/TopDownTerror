package it.unibs.mainApp;

import java.util.*;

import it.unibs.bot.*;

public class MapMatrix {
	public static final int HEIGHT = 22;
	public static final int WIDTH = 32;
	public static final int N_SPAWNS = 6;
	public static final int SPAWN_H = 2;
	public static final int SPAWN_W = 2;
	public static final double WALL_PROBABILITY = 0.17;
	
	private static final int WALL = 1;
	private static final int PAVEMENT = 0;
	private static final int SPAWN = 2;
	private static final int SPAWN_ZONE = 3;
	
	private static final int CX = 15;
	private static final int CY = 11;
	
	private static HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
	
//	public static int[][] matrix = {
//		    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//		    {1,2,3,0,0,0,0,0,0,0,0,0,0,0,0,2,3,1,0,1,0,0,0,0,0,1,1,1,1,2,3,1},
//		    {1,3,3,1,1,1,1,1,1,1,1,1,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,1,3,3,1},
//		    {1,1,1,1,0,0,0,0,0,1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1},
//		    {1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
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

	public static int[][] matrix = new int[HEIGHT][WIDTH];
	
	private static int[][] spawn = {{1,1}, 
									{1, WIDTH/2 - SPAWN_W/2},
									{1, WIDTH - SPAWN_W - 1},
									{HEIGHT - SPAWN_H - 1,1},
									{HEIGHT - SPAWN_H - 1, WIDTH/2 - SPAWN_W/2},
									{HEIGHT - SPAWN_H - 1, WIDTH - SPAWN_W - 1}};
	
//	public static void main(String[] args) {
//		//fillTheMap();
//		
//		addSpawn();
//		clearCenter();
//		buildNodeMap();
//		System.out.println(checkMap());
//		//matrix = getMatrix();
//				
//		buildNodeMap();
//		print();
//		//Node n = nodes.get((3*WIDTH + 16));
//		//n.printEdge();
//		
//		int sx = 16;
//		int sy = 3;
//		int tx = 15;
//		int ty = 11;
//		
//		//System.out.println(matrix[sy][sx]);
//		//System.out.println(nodes.toString());
//		//printNode();
//		
//		AStar.generatePath(sx, sy, tx, ty);
//		
//		//Node n = nodes.get((ty*WIDTH + tx));
//		//n.printEdge();
//		
//		//AStar.generatePath(sx, sy, tx, ty);
//		AStar.generatePath(tx, ty, 7, 3);
//		
//		//print();
//	}
	 
	public static int[][] getMatrix() {
		do {
			fillTheMap();
			addSpawn();
			clearCenter();
			
			buildNodeMap();
		} while(!checkMap());	
		
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
		for(int[] s: spawn) {
			fillSpawn(s[0], s[1]);
		}
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
	
	private static void clearCenter() {
		matrix[HEIGHT/2 - 1][WIDTH/2 - 1] = 0;
		matrix[HEIGHT/2 - 1][WIDTH/2] = 0;
		matrix[HEIGHT/2][WIDTH/2 - 1] = 0;
		matrix[HEIGHT/2][WIDTH/2] = 0;
	}
	
	private static void print() {
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	private static void buildNodeMap() {
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				if(matrix[i][j] != 1) {
					int key = (i * WIDTH) + j;
					nodes.put(key, new Node(key, i, j));
				}
			}
		}
		
		
		for(int i = 1; i < HEIGHT - 1; i++) {
			for(int j = 1; j < WIDTH - 1; j++) {
				if(matrix[i][j] != 1) {
					int key = (i * WIDTH) + j;
					Node n = nodes.get(key);
					
					int li = i - 1;
					int ti = i + 1;
					int lj = j - 1;
					int tj = j + 1;
					
					for(int h = li; h <= ti; h++) {
						for(int k = lj; k <= tj; k++) {
							if(h == i && k == j)
								continue;
							
							if(matrix[h][k] != 1) {

								if(h == li && k == lj && matrix[h+1][k] == 1 && matrix[h][k+1] == 1)
									continue;
								if(h == li && k == tj && matrix[h][k-1] == 1 && matrix[h+1][k] == 1)
									continue;
								if(h == ti && k == lj && matrix[h-1][k] == 1 && matrix[h][k+1] == 1)
									continue;
								if(h == ti && k == tj && matrix[h-1][k] == 1 && matrix[h][k-1] == 1)
									continue;
								
								int bKey = (h * WIDTH) + k;
								Node branch = nodes.get(bKey);
								
								// weight is tiledim, diagonal is tiledim * rad2
								if(h != i && k != j)
									n.addBranch(Math.sqrt(2) * Battlefield.BATTLEFIELD_TILEDIM, branch);
								else
									n.addBranch(Battlefield.BATTLEFIELD_TILEDIM, branch);
							}
						}
					}
				}
			}
		}
	}
	
	public static HashMap<Integer, Node> getNodes() {
		return nodes;
	}
	
	public static void printNode() {
		for(HashMap.Entry<Integer, Node> n: nodes.entrySet()) {
			System.out.println("key: " + n.getKey() + ", (" + n.getValue().getCol() + ", " + n.getValue().getRow() + ")");
		}
	}
	
	public static boolean isPavement(int x, int y) {
		if(matrix[y][x] == 0)
			return true;
		
		return false;
	}
	
	public static boolean isWall(int x, int y) {
		if(matrix[y][x] == 1)
			return true;
		
		return false;
	}
	
	private static boolean checkMap() {
		int i = 1;
		for(int[] s: spawn) {
			if(!isReached(s[0], s[1]))
				return false;
		}
		
		return true;
	}
	
	private static Stack<Node> n = new Stack<>();

	private static boolean isReached(int x, int y) {
		Path p = new Path(x, y, CX, CY);
		return p.targetReached();
	}
}
