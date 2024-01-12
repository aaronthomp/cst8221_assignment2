package cs22;
/*
 * Student Name: Aaron Thompson
 * Student Number: 041058950
 * Lab Professor: Daniel Cormier
 * Lab Section: 302
 * Date: 2023-11-05
 * 
 */
import java.util.Random;

/**
 * Model class in the MVC pattern
 */
public class GameModel {
	/**
	 * game model constructor
	 */
	/**
	 * sets the size of the 18 bit binary rule
	 */
	final private int RULE = 18;
	/**
	 * store the row size
	 */
	private int row = 50;
	/**
	 * store the column size
	 */
	private int col = 50;
	/**
	 * used for switch case
	 */
	final private int NORMAL = 0;
	/**
	 * used for switch case of top left corner case
	 */
	final private int TOP_LEFT = 1;
	/**
	 * used for switch case of top right corner case
	 */
	final private int TOP_RIGHT = 2;
	/**
	 * used for switch case of bottom left corner case
	 */
	final private int BOT_LEFT = 3;
	/**
	 * used for switch case of right corner case
	 */
	final private int BOT_RIGHT = 4;
	/**
	 * used for switch case of top edge case
	 */
	final private int TOP_EDGE = 5;
	/**
	 * used for left edge switch case
	 */
	final private int LEFT_EDGE = 6;
	/**
	 * used for bottom edge switch case
	 */
	final private int BOT_EDGE = 7;
	/**
	 * used for right edge switch case
	 */
	final private int RIGHT_EDGE = 8;
	/**
	 * board used for current board state
	 */
	boolean[][] board = new boolean[row][col];
	/**
	 * stores the updated board state
	 */
	boolean[][] temp = new boolean[row][col];
	/**
	 * stores an array of the rules of neighbours of a dead cell
	 */
	int deadNeighbours[] = new int[RULE/2];
	/**
	 * stores an array of the rules of neighbours for an alive cell
	 */
	int aliveNeighbours[] = new int[RULE/2];
	
	/**
	 * gets row
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * sets row
	 * @param row takes in the row 
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * gets the column
	 * @return col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * sets column
	 * @param col takes in the column
	 */
	public void setCol(int col) {
		this.col = col;
	}


	/**
	 * initalizes the board to all false
	 * @param row takes in the row
	 * @param col takes in the column
	 */
	public void initBoard(int row, int col) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// just setting every element to false so i should get a white grid if painted in theory
				board[i][j] = false;
			}
		}
	}
	
	/**
	 * checks to see if the current cell is alive
	 * @param row takes in the row
	 * @param col takes in the column
	 * @return true or false for board cell state
	 */
	public boolean isCellAlive(int row, int col) {
		// just showing if the cell is alive at position in grid
		return board[row][col];
	}
	
	/**
	 * sets a cell to the opposite 
	 * @param row takes in the row
	 * @param col takes in the column
	 */
	public void setCell(int row, int col) {
		// since its boolean just flip it to set the cell (true to false, false to true)
		board[row][col] = !board[row][col];
	}
	
	
	/**
	 * sets the game rules into the dead/alive neighbor arrays
	 * @param modelNum takes in the model number from the model text field
	 */
	public void gameRules(String modelNum) {
		int rule = 1;
		if(modelNum == null) {
			return;
		}// iterate 9 cells for rules
			for (int i = 0; i < RULE/2; i++) {
				// check cell rules at i of model number (from model text field)
				if (modelNum.charAt(i) == '1') {
					// just set the array at i to 1 to compare to later
					deadNeighbours[i] = rule;
				}
			} // same as above but check char at the latter portion of model text field
			for(int i = 0; i < RULE/2; i++) {
				// should be checking the second set of 9 bits 
				if (modelNum.charAt(i+(RULE/2)) == '1') {
					aliveNeighbours[i] = rule;
				}
			}
	}
	
	/**
	 * updates the board from the temp board to get 
	 * to start the next iteration
	 */
	public void updateBoard(){
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				// probably a more efficient way of doing this but works on time crunch
				// board copies value of temp, temp holds next gen
				board[i][j] = temp[i][j];
			}
		}
	}
	
	/** 
	 * counts the amount of neighbours of the current cell
	 * @param board takes in a boolean board
	 * @param x takes in an x coordinate
	 * @param y takes in a y coordinate
	 * @return oversight without time to fix
	 */
	public int countNeighbours(boolean board[][], int x, int y){
		// extremely inefficient method, works for implementation
		// noticed patterns that couldve capitalized on efficiency if had more time to refactor
		// believe it or not this was my 2nd iteration of this method and the previous was even less efficient
		int aliveCells = 0;
		// check where we are in the grid
		switch (isWithinBounds(x, y)) {
		// normal case is anything that is not a corner or edge case
		case NORMAL:
			aliveCells = 0;
			for(int i = x - 1; i <= x+1; i++) {
				for(int j = y - 1; j <= y+1; j++) {
					// if statement skips over current cell that we're checking neighbors for
					if (i == x && j == y) {
						continue;	
					}
					// if board is true at non current cell pos theres an alive neighbor
					else if (board[i][j]){
						aliveCells++;
					}
				}
			}
			// return the count of neighbors
			return aliveCells;
			
		// case for top left corner	
		case TOP_LEFT:
			// all these switch cases use the same logic just manipulate where the i and y start and end
			aliveCells = 0;
			for(int i = x; i <= x+1; i++) {
				for(int j = y; j <= y+1; j++) {
					if (i == x && j == y) {
						continue;	
					}
					else if (board[i][j]){
						aliveCells++;
					}
				}
			}
			return aliveCells;

			
			// top right corner case
		case TOP_RIGHT:
			aliveCells = 0;
			for(int i = x; i <= x+1; i++) {
				for(int j = y-1; j <= y; j++) {
					if (i == x && j == y) {
						continue;	
					}
					else if (board[i][j]){
						aliveCells++;
					}
				}
			}
			return aliveCells;
			
		// bottom left corner case	
		case BOT_LEFT:
			aliveCells = 0;
			for(int i = x-1; i <= x; i++) {
				for(int j = y; j <= y+1; j++) {
					if (i == x && j == y) {
						continue;	
					}
					else if (board[i][j]){
						aliveCells++;
					}
				}
			}
			return aliveCells;

			
		// bottom right corner case	
		case BOT_RIGHT:
			aliveCells = 0;
			for(int i = x-1; i <= x; i++) {
				for(int j = y-1; j <= y; j++) {
					if (i == x && j == y) {
						continue;	
					}
					else if (board[i][j]){
						aliveCells++;
					}
				}
			}
			return aliveCells;

			
		// top edge excluding corners 	
		case TOP_EDGE:
			aliveCells = 0;
			for(int i = x; i <= x+1; i++) {
				for(int j = y - 1; j <= y+1; j++) {
					if (i == x && j == y) {
						continue;
					}
					else if(board[i][j]) {
						aliveCells++;
					}
				}	
			}

			return aliveCells;

			
		// left edge excluding corners	
		case LEFT_EDGE:
			aliveCells = 0;
			for(int i = x - 1; i <= x+1; i++) {
				for(int j = y; j <= y+1; j++) {
					if (i == x && j == y) {
						continue;
					}
					else if(board[i][j]) {
						aliveCells++;
					}
				}	
			}

			return aliveCells;

			
		// bottom edge excluding corners	
		case BOT_EDGE:
			aliveCells = 0;
			for(int i = x - 1; i <= x; i++) {
				for(int j = y - 1; j <= y+1; j++) {
					if (i == x && j == y) {
						continue;
					}
					else if(board[i][j]) {
						aliveCells++;
					}
				}	
			}
			return aliveCells;

		// right edge excluding corners	
		case RIGHT_EDGE:
			aliveCells = 0;
			for(int i = x - 1; i <= x + 1; i++) {
				for(int j = y - 1; j <= y; j++) {
					if (i == x && j == y) {
						continue;
					}
					else if(board[i][j]) {
						aliveCells++;
					}
				}	
			}			
	
			return aliveCells;

		}

		return 10;
	}
	
	
	/**
	 * calculates the cell rules and determines whether or not the cell is alive
	 * @param neighbour takes in alive neighbour count
	 * @param cell takes in the state of a cell
	 * @param x takes in an x coordinate
	 * @param y takes in a y coordinate
	 */
	public void calcCellRules(int neighbour, boolean cell, int x, int y){
		// check if the cell is alive
		if(cell) {
			// comparison with array storing game rules for alive cells
			if(aliveNeighbours[neighbour] == 1) {
				temp[x][y] = true;
			}
		
			else {
				temp[x][y] = false;
			}	
		}
		else {
			// comparison with array storing game rules for dead cells
			if(deadNeighbours[neighbour] == 1) {
				temp[x][y] = true;
			}
			else {
				temp[x][y] = false;
			}
		}
		
	}
	
	
	/**
	 * checks to see if the button is within bounds
	 * @param x takes in x coordinate
	 * @param y takes in y coordinate
	 * @return number 1-10 to be used in a switch case
	 */
	public int isWithinBounds(int x, int y){
		// lotta bullshit in this method
		// this is just making sure its not on any edge of the board
		if ((x >=  1 && x < row - 1) && (y >= 1  && y < col - 1)) { 
			return NORMAL;
		}
		// top left corner
		else if(x == 0 & y == 0) {
			return TOP_LEFT;
		}
		// top right corner
		else if(x == 0 && y == col-1) {
			return TOP_RIGHT;
		}
		// bottom left corner
		else if(x == row - 1 && y == 0) {
			return BOT_LEFT;
		}
		// bottom right corner
		else if(x == row - 1 && y == col - 1) {
			return BOT_RIGHT;
		}
		//top edge 
		else if(x == 0) {
			return TOP_EDGE;
		}
		// left edge
		else if(y == 0) {
			return LEFT_EDGE;
		}
		// bottom edge
		else if(x == row - 1) {
			return BOT_EDGE;
		}
		// right edge
		return RIGHT_EDGE;
		
	}
	
	/**
	 * random initialization of board
	 */
	public void randomInit() {
		// using random object to create a random board
		Random rand = new Random();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				// iterate through whole grid and set the boolean randomly
				board[i][j] = rand.nextBoolean();
			}
		}
	}
}