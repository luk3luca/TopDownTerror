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
	
	private static HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
	//public static int[][] matrix = new int[HEIGHT][WIDTH];
	
//	public static int[][] matrix = {
//		    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//		    {1,2,3,0,0,0,0,0,0,0,0,0,0,0,0,2,3,1,0,1,0,0,0,0,0,1,1,1,0,2,3,1},
//		    {1,3,3,0,1,1,1,1,1,1,1,1,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,3,3,1},
//		    {1,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1},
//		    {1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
//		    {1,0,0,1,1,0,0,0,0,1,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
//		    {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,1,1,1,1},
//		    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1},
//		    {1,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1},
//		    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
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
	
	
	public static int[][] matrix = {
		    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		    {1,2,3,0,0,0,0,0,0,0,0,0,0,0,0,2,3,1,0,1,0,0,0,0,0,1,1,1,0,2,3,1},
		    {1,3,3,0,1,1,1,1,1,1,1,1,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,3,3,1},
		    {1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,1},
		    {1,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1},
		    {1,0,0,1,1,0,0,0,0,1,0,1,1,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,1},
		    {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,1,1,1,1},
		    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1},
		    {1,0,1,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1},
		    {1,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1},
		    {1,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
		    {1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,1,1,0,0,1,0,0,0,0,1,0,0,0,0,1},
		    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		    {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,1,0,1},
		    {1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,1,0,0,1},
		    {1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
		    {1,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
		    {1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
		    {1,2,3,0,0,0,0,0,1,0,0,1,0,1,0,2,3,0,0,0,0,0,0,0,0,0,0,0,0,2,3,1},
		    {1,3,3,1,1,0,1,1,0,0,0,0,0,0,0,3,3,0,0,1,0,1,0,0,0,0,0,0,0,3,3,1},
		    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};

//	public static int[][] matrix = new int[HEIGHT][WIDTH];
	
	public static void main(String[] args) {
		//matrix = getMatrix();
		clearCenter();
		buildNodeMap();
		
		//Node n = nodes.get((3*WIDTH + 16));
		//n.printEdge();
		int sx = 16;
		int sy = 3;
		int tx = 16;
		int ty = 11;
		
		//System.out.println(matrix[sy][sx]);
		//System.out.println(nodes.toString());
		//printNode();
		
		
		AStar a = new AStar(sx, sy, tx, ty);
		a.generatePath();
		
		Node n = nodes.get((ty*WIDTH + tx));
		n.printEdge();
		
		AStar a2 = new AStar(17, 12, 3, 3);
		a2.generatePath();
		//print();
	}
	 
	public static int[][] getMatrix() {
		//fillTheMap();
		addSpawn();
		clearCenter();
		
		buildNodeMap();
		
		//if(!checkMap())
			//getMatrix();
		
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

//	private static boolean checkMap() {
//	    // Find all spawn points
//	    List<int[]> spawnPoints = new ArrayList<>();
//	    for (int row = 0; row < HEIGHT; row++) {
//	        for (int col = 0; col < WIDTH; col++) {
//	            if (matrix[row][col] == SPAWN) {
//	                spawnPoints.add(new int[] {row, col});
//	            }
//	        }
//	    }
//	    System.out.println(spawnPoints.size());
//
//	    // Check if all spawn points are connected
//	    Set<int[]> visited = new HashSet<>();
//	    Queue<int[]> queue = new ArrayDeque<>();
//	    queue.offer(spawnPoints.get(0));
//	    visited.add(spawnPoints.get(0));
//	    int count = 1;
//
//	    while (!queue.isEmpty()) {
//	        int[] curr = queue.poll();
//	        int row = curr[0];
//	        int col = curr[1];
//
//	        // Check neighbors
//	        int[][] neighbors = {{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};
//	        for (int[] neighbor : neighbors) {
//	            int neighborRow = neighbor[0];
//	            int neighborCol = neighbor[1];
//
//	            if (neighborRow >= 0 && neighborRow < HEIGHT && neighborCol >= 0 && neighborCol < WIDTH) {
//	                if (matrix[neighborRow][neighborCol] == SPAWN && !visited.contains(neighbor)) {
//	                    queue.offer(neighbor);
//	                    visited.add(neighbor);
//	                    count++;
//	                } else if ((matrix[neighborRow][neighborCol] == SPAWN_ZONE || matrix[neighborRow][neighborCol] == PAVEMENT) && !visited.contains(neighbor)) {
//	                    visited.add(neighbor);
//	                }
//	            }
//	        }
//	    }
//
//	    return count == N_SPAWNS;
//	}

	

//	----------------------------------------------------------
//	private static boolean checkMap() {
//		for(int i = 1; i < HEIGHT - 1; i++) {
//			for(int j = 0; j < WIDTH; j++) {
//				if(matrix[i][j] == SPAWN) {
//					if(!canReachAnotherSpawn(i, j)) {
//						return false;
//					}
//				}
//			}
//		}
//		return true;
//	}
//	
//	//Breadth-first search (BFS) algorithm
//	private static boolean canReachAnotherSpawn(int startCol, int startRow) {
//	    // Check if the starting position is a spawn cell
//	    if (matrix[startCol][startRow] != SPAWN) {
//	        return false;
//	    }
//
//	    // Set up the BFS
//	    Queue<int[]> queue = new ArrayDeque<>();
//	    Set<String> visited = new HashSet<>();
//	    queue.offer(new int[] { startCol, startRow });
//	    visited.add(startCol + "," + startRow);
//
//	    // Start BFS
//	    while (!queue.isEmpty()) {
//	        int[] current = queue.poll();
//	        int row = current[0];
//	        int col = current[1];
//
//	        // Check if we have reached another spawn cell
//	        if (matrix[row][col] == SPAWN && (row != startCol || col != startRow)) {
//	            return true;
//	        }
//
//	        // Add neighboring cells to the queue
//	        if (row > 0 && (matrix[row-1][col] == SPAWN || matrix[row-1][col] == SPAWN_ZONE || matrix[row-1][col] == PAVEMENT) && !visited.contains((row-1) + "," + col)) {
//	            queue.offer(new int[] { row-1, col });
//	            visited.add((row-1) + "," + col);
//	        }
//	        if (row < HEIGHT-1 && (matrix[row+1][col] == SPAWN || matrix[row+1][col] == SPAWN_ZONE || matrix[row+1][col] == PAVEMENT) && !visited.contains((row+1) + "," + col)) {
//	            queue.offer(new int[] { row+1, col });
//	            visited.add((row+1) + "," + col);
//	        }
//	        if (col > 0 && (matrix[row][col-1] == SPAWN || matrix[row][col-1] == SPAWN_ZONE || matrix[row][col-1] == PAVEMENT) && !visited.contains(row + "," + (col-1))) {
//	            queue.offer(new int[] { row, col-1 });
//	            visited.add(row + "," + (col-1));
//	        }
//	        if (col < WIDTH-1 && (matrix[row][col+1] == SPAWN || matrix[row][col+1] == SPAWN_ZONE || matrix[row][col+1] == PAVEMENT) && !visited.contains(row + "," + (col+1))) {
//	            queue.offer(new int[] { row, col+1 });
//	            visited.add(row + "," + (col+1));
//	        }
//	    }
//
//	    // No other spawn cells were found
//	    return false;
//	}
	
	
//	-------------------------------------------------------
//	private static boolean allSpawnsConnected() {
//	    // Initialize variables
//	    boolean[] visited = new boolean[N_SPAWNS];
//	    Queue<Integer> queue = new LinkedList<>();
//
//	    // Perform BFS from first spawn
//	    queue.add(0);
//	    visited[0] = true;
//
//	    // Continue BFS from each unvisited spawn
//	    while (!queue.isEmpty()) {
//	        int spawn = queue.remove();
//
//	        // Check if all spawns have been visited
//	        boolean allVisited = true;
//	        for (int i = 0; i < N_SPAWNS; i++) {
//	            if (!visited[i]) {
//	                allVisited = false;
//	                break;
//	            }
//	        }
//	        if (allVisited) {
//	            return true;
//	        }
//
//	        // Add neighboring spawns to queue
//	        for (int i = 0; i < N_SPAWNS; i++) {
//	            if (i != spawn && isConnected(spawn, i) && !visited[i]) {
//	                queue.add(i);
//	                visited[i] = true;
//	            }
//	        }
//	    }
//
//	    return false;  // Not all spawns are connected
//	}
//
//	private static boolean isConnected(int spawn1, int spawn2) {
//	    // Perform BFS from spawn1 to check if it reaches spawn2
//	    Queue<Integer> queue = new LinkedList<>();
//	    Set<Integer> visited = new HashSet<>();
//
//	    queue.add(spawn1);
//	    visited.add(spawn1);
//
//	    while (!queue.isEmpty()) {
//	        int currentSpawn = queue.remove();
//
//	        if (currentSpawn == spawn2) {
//	            return true;
//	        }
//
//	        for (int i = 0; i < N_SPAWNS; i++) {
//	            if (i != currentSpawn && matrix[currentSpawn][i] == SPAWN && !visited.contains(i)) {
//	                queue.add(i);
//	                visited.add(i);
//	            }
//	        }
//	    }
//
//	    return false;  // spawn1 does not connect to spawn2
//	}
	
	
}
