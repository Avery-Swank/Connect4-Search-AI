import java.util.ArrayList;

/**
 * Contains all game board information as well as functions to interact with the game board
 * @author Avery Swank
 */
public class Board {

	private int rowSize;
	private int columnSize;
	
	private String[][] board;
	private int[] heights;
	
	// Default Game Board is 6 rows * 7 columns
	public Board(){
		this(6, 7);
	}
	
	public Board(int _rowSize, int _columnSize){
		
		if(_rowSize < 6) throw new Error("Invalid rowSize parameter");
		if(_columnSize < 7) throw new Error("Invalid columnSize parameter");
		
		rowSize = _rowSize;
		columnSize = _columnSize;
		
		board = new String[rowSize][columnSize];
		heights = new int[columnSize];
		
		emptyBoard();
	}
	
	/**
	 * Set all spaces in the board to empty ie "e"
	 */
	public void emptyBoard(){
		
		// Set board to empty
		for(int i = 0; i < rowSize; i++){
			for(int j = 0; j < columnSize; j++){
				board[i][j] = "e";
			}
		}
		
		// Set heights to zero
		for(int i = 0; i < columnSize; i++){
			heights[i] = 0;
		}
	}
	
	/**
	 * Return the symbol at position (row, column)
	 */
	public String getPosition(int row, int column){
		
		if(row < 0 || row > rowSize - 1) throw new Error("Invalid row position");
		if(column < 0 || column > columnSize - 1) throw new Error("Invalid column position");
		return board[row][column];
	}
	
	/**
	 * Return the number of moves played on the board by just adding the heights
	 */
	public int getNumMoves(){
		int moves = 0;
		for(int i = 0; i < heights.length; i++){
			moves += heights[i];
		}
		return moves;
	}
	
	/**
	 * Get a list of integers of available columns that you can add to.
	 * Prevents adding symbols to a full column
	 */
	public int[] getAvailableColumns(){
		
		int[] available;
		int size = 0;
		for(int i = 0; i < columnSize; i++){
			if(!isFull(i)){
				size++;
			}
		}
		available = new int[size];
		
		int j = 0;
		for(int i = 0; i < columnSize; i++){
			if(!isFull(i)){
				available[j] = i;
				j++;
			}
		}
		return available;
	}
	
	/**
	 * Return true if the column is empty of symbols
	 * Otherwise, false
	 */
	public boolean isEmpty(int column){
		if(column < 0 || column > columnSize - 1) throw new Error("Invalid column position");
		return heights[column] == 0;
	}
	
	/**
	 * Return true if the column is full of symbols
	 * Otherwise, false
	 */
	public boolean isFull(int column){
		if(column < 0 || column > columnSize - 1) throw new Error("Invalid column position");
		return heights[column] == rowSize;
	}
	
	/**
	 * Return true if the entire board is full, check for tie games
	 */
	public boolean isBoardFull(){
		boolean full = true;
		for(int i = 0; i < columnSize; i++){
			if(!isFull(i)){
				full = false;
				break;
			}
		}
		return full;
	}
	
	/**
	 * Play a piece on a column, add the piece at the bottom of the first available space in that column like Connect 4
	 */
	public void addSymbol(int column, String _symbol){
		
		if(isFull(column)) throw new Error("Cannot add a symbol to a full column");
		if(column < 0 || column > columnSize - 1) throw new Error("Invalid column position");
		
		// Add the first symbol on the bottom of the column ie gravity pulls the piece to the bottom
		for(int i = rowSize-1; i >= 0; i--){
			if(board[i][column].equals("e")){
				board[i][column] = _symbol;
				heights[column]++;
				return;
			}
		}
	}
	
	/**
	 * Remove the top piece of a column. Necessary for search algorithms when creating multiple boards
	 */
	public void removeSymbol(int column){
		
		if(column < 0 || column > columnSize - 1) throw new Error("Invalid column position");
		if(heights[column] == 0) throw new Error("Cannot Remove a Symbol from an empty column");
		
		// Add the first symbol on the bottom of the column ie gravity pulls the piece to the bottom
		for(int i = 0; i < rowSize; i++){
			if(!board[i][column].equals("e")){
				board[i][column] = "e";
				heights[column]--;
				return;
			}
		}
	}
	
	/**
	 * Return the symbol that has a connect 4
	 * Otherwise return "" indicating that no player has a Connect 4
	 */
	public String getConnect4(Player p1, Player p2){
		
		String s = "";
		
		String p1Sequence = "";
		String p2Sequence = "";
		for(int i = 0; i < 4; i++){
			p1Sequence += p1.getSymbol();
			p2Sequence += p2.getSymbol();
		}
		
		// Check a Connect 4 in all rows
		for(int i = 0; i < rowSize; i++){
			s = getRow(i);
			
			if(s.contains(p1Sequence)){
				return p1.getSymbol();
			}
			
			if(s.contains(p2Sequence)){
				return p2.getSymbol();
			}
					
			s = "";
		}
		
		// Check a Connect 4 in all columns
		for(int i = 0; i < columnSize; i++){
			s = getColumn(i);
			
			if(s.contains(p1Sequence)){
				return p1.getSymbol();
			}
			
			if(s.contains(p2Sequence)){
				return p2.getSymbol();
			}
			
			s = "";
		}

		// Check a Connect 4 in all top-left to down-right straights
		for(int i = 0; i < rowSize-3; i++){
			for(int j = 0; j < columnSize-3; j++){
				for(int k = 0; k < 4; k++){
					s += getPosition(i+k, j+k);
				}
				
				if(s.contains(p1Sequence)){
					return p1.getSymbol();
				}
				
				if(s.contains(p2Sequence)){
					return p2.getSymbol();
				}
				
				s = "";
			}
		}
		
		// Check a Connect 4 in all bottom-left to top-right straights
		for(int i = rowSize-1; i >= rowSize-3; i--){
			for(int j = 0; j < columnSize-3; j++){
				for(int k = 0; k < 4; k++){
					s += getPosition(i-k, j+k);
				}
				
				if(s.contains(p1Sequence)){
					return p1.getSymbol();
				}
				
				if(s.contains(p2Sequence)){
					return p2.getSymbol();
				}
				
				s = "";
			}
		}
		
		return "";
	}

	/**
	 * Return the number of straights that contain symbol 'symbol' of length 'length+1'
	 * These are straights that CAN BE EXTENDED (ie there are empty symbols adjacent) in the:
	 * row direction, column direction, top-left to bottom-right, bottom-left to top-right
	 */
	public int getNumStraights(String symbol, int length){
		
		String s = "";
		int count = 0;
		
		ArrayList<String> combos = new ArrayList<String>();
		switch(length){
			case 1:
				combos.add("e" + symbol + "e");
				break;
			case 2:
				combos.add("e" + symbol + symbol + "e");
				combos.add("e" + symbol + symbol);
				combos.add(symbol + "e" + symbol);
				combos.add(symbol + symbol + "e");
				break;
			case 3:
				combos.add("e" + symbol + symbol + symbol + "e");
				combos.add("e" + symbol + symbol + symbol);
				combos.add(symbol + "e" + symbol + symbol);
				combos.add(symbol + symbol + "e" + symbol);
				combos.add(symbol + symbol + symbol + "e");
				break;
			case 4:
				combos.add(symbol + symbol + symbol + symbol);
				break;
			default:
				throw new Error("Invalid length");
		}
		
		// Check a Connect 4 in all rows
		for(int i = 0; i < rowSize; i++){
			s = getRow(i);
			
			for(int z = 0; z < combos.size(); z++){
				String comboString = combos.get(z);
				
				if(s.contains(comboString)){
					count++;
				}
			}
					
			s = "";
		}
		
		// Check a Connect 4 in all columns
		for(int i = 0; i < columnSize; i++){
			s = getColumn(i);
			
			for(int z = 0; z < combos.size(); z++){
				String comboString = combos.get(z);
				
				if(s.contains(comboString)){
					count++;
				}
			}
			
			s = "";
		}

		// Check a Connect 4 in all top-left to down-right straights
		for(int i = 0; i < rowSize-3; i++){
			for(int j = 0; j < columnSize-3; j++){
				for(int k = 0; k < 4; k++){
					s += getPosition(i+k, j+k);
				}
				
				for(int z = 0; z < combos.size(); z++){
					String comboString = combos.get(z);
					
					if(s.contains(comboString)){
						count++;
					}
				}
				
				s = "";
			}
		}
		
		// Check a Connect 4 in all bottom-left to top-right straights
		for(int i = rowSize-1; i >= rowSize-3; i--){
			for(int j = 0; j < columnSize-3; j++){
				for(int k = 0; k < 4; k++){
					s += getPosition(i-k, j+k);
				}
				
				for(int z = 0; z < combos.size(); z++){
					String comboString = combos.get(z);
					
					if(s.contains(comboString)){
						count++;
					}
				}
				
				s = "";
			}
		}
		
		return count;
	}
	
	/**
	 * Return a row's contents as a string
	 */
	public String getRow(int row){
		
		if(row < 0 || row >= rowSize) throw new Error("Invalid getRow index");
		
		String s = "";
		for(int i = 0; i < columnSize; i++){
			s += getPosition(row, i);
		}
		
		return s;
	}
	
	/**
	 * Return a column's contents as a string
	 */
	public String getColumn(int col){
		
		if(col < 0 || col >= columnSize) throw new Error("Invalid getColumn index");
		
		String s = "";
		for(int i = 0; i < rowSize; i++){
			s += getPosition(i, col);
		}
		
		return s;
	}
	
	/**
	 * Return row size
	 */
	public int getRowSize(){
		return rowSize;
	}
	
	/**
	 * Return column size
	 */
	public int getColumnSize(){
		return columnSize;
	}
	
	/**
	 * Return board
	 */
	public String[][] getBoard(){
		return board;
	}
	
	/**
	 * Return column heights
	 */
	public int[] getHeights(){
		return heights;
	}
	
	/**
	 * Print the board and heights
	 */
	public String toString(){
		String s = "";
		
		// Add board symbols
		for(int i = 0; i < rowSize; i++){
			for(int j = 0; j < columnSize; j++){
				s += board[i][j] + " ";
			}
			s += '\n';
		}
		
		s += "Heights: [";
		for(int i = 0; i < columnSize; i++){
			s += heights[i] + " ";
		}
		s += "]\n";
		
		return s;
	}
		
}
