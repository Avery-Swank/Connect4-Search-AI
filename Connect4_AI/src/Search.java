import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that contains all of the major search functions for a Connect4 AI:
 * 	 - Search for winning scenarios
 * 	 - Human player moves with human input
 * 	 - Randomized and partially randomized searches
 * 	 - Tree searches
 *	 - Minimax
 *	 - Multiple different heuristic functions 				
 * @author Avery Swank
 */
public class Search {

	/**
	 * Return true if placing a symbol at that column is a winning move. 
	 * Otherwise, false.
	 */
	public static boolean isWinningMove(Board board, Player player, Player opponent, int col){
		
		// Add the symbol and check if there is a Connect4
		board.addSymbol(col, player.getSymbol());
		String win = board.getConnect4(player, opponent);
		boolean isWin = win.equals(player.getSymbol());
		
		// Remove the symbol for consistency
		board.removeSymbol(col);
		
		return isWin;
	}
	
	/**
	 * Heuristic Function
	 * 
	 * Equation: heuristic = (player # of 4 in a rows * w4) +
	 * 						 (player # of 3 in a rows * w3) + 
	 * 						 (player # of 2 in a rows * w2) +
	 *						 (player # of 1 in a rows * w1) - 
	 * 						 (opponent # of 4 in a rows * w4) -
	 * 						 (opponent # of 3 in a rows * w3) - 
	 * 						 (opponent # of 2 in a rows * w2) -
	 *						 (opponent # of 1 in a rows * w1)
	 * 
	 * The goal of this is to award boards that build up better situations for a Connect 4
	 * Weighted towards higher straights
	 */
	public static int getHeuristic(Board board, Player player, Player opponent){

		// Weights
		int w4 = 10;
		int w3 = 5;
		int w2 = 3;
		int w1 = 1;
		
		int player4 = board.getNumStraights(player.getSymbol(), 4);
		int player3 = board.getNumStraights(player.getSymbol(), 3);
		int player2 = board.getNumStraights(player.getSymbol(), 2);
		int player1 = board.getNumStraights(player.getSymbol(), 1);
		int opponent4 = board.getNumStraights(opponent.getSymbol(), 4);
		int opponent3 = board.getNumStraights(opponent.getSymbol(), 3);
		int opponent2 = board.getNumStraights(opponent.getSymbol(), 2);
		int opponent1 = board.getNumStraights(opponent.getSymbol(), 1);

		int playerHeuristic = (player4 * w4) + (player3 * w3) + (player2 * w2) + (player1 * w1);
		int opponentHeuristic = (opponent4 * w4) + (opponent3 * w3) + (opponent2 * w2) + (opponent1 * w1);
		int heuristic = playerHeuristic - opponentHeuristic;
		return heuristic;
	}
	
	/**
	 * Simple Heuristic Function
	 * 
	 * Same concept as getHeuristic() but simplified to only straights of 2 and 3 for sake of increased speed
	 */
	public static int getSimpleHeuristic(Board board, Player player, Player opponent){

		// Weights
		int w3 = 5;
		int w2 = 3;
		
		int player3 = board.getNumStraights(player.getSymbol(), 3);
		int player2 = board.getNumStraights(player.getSymbol(), 2);
		int opponent3 = board.getNumStraights(opponent.getSymbol(), 3);
		int opponent2 = board.getNumStraights(opponent.getSymbol(), 2);

		int playerHeuristic = (player3 * w3) + (player2 * w2);
		int opponentHeuristic = (opponent3 * w3) + (opponent2 * w2);
		int heuristic = playerHeuristic - opponentHeuristic;
		return heuristic;
	}
	
	/**
	 * Play a move based on human player input
	 * Use scanf to pick up the human player's inputs
	 */
	@SuppressWarnings("resource")
	public static int humanMove(Board board, Player p){
		
		Scanner scan = new Scanner(System.in);
		int move = -2;
		
		// Print out the board for the human player to read
		//System.out.println(board);
		
		// Ensure the human input is a valid number [0,columnSize-1]
		// Will keep repeating until a valid number is entered
		while(move < 0 || board.getColumnSize() - 1 < move){
			System.out.println(p.getName() + " enter a column number [0," + (board.getColumnSize()-1) + "]: ");
			move = scan.nextInt();
		}
		
		return move;
	}
	
	/**
	 * Make a random move on an available Connect4 column
	 */
	public static int random(Board board){
		int randIndex = (int) Math.floor(Math.random() * board.getAvailableColumns().length);
		int availableCol[] = board.getAvailableColumns();
		return availableCol[randIndex];
	}
	
	/**
	 * If there is a winning move for the player, take it
	 * If there is a winning move for the opponent, take it
	 * Otherwise, make a random move
	 */
	public static int naive(Board board, Player player, Player opponent){
		
		int[] moves = board.getAvailableColumns();
		
		// if there is a winning move, take it
		for(int i = 0; i < moves.length; i++){
			int col = moves[i];
			
			if(isWinningMove(board, player, opponent, col)){
				return col;
			}
		}
		
		// if the opponent has a winning move, take it to prevent it
		for(int i = 0; i < moves.length; i++){
			int col = moves[i];
			
			if(isWinningMove(board, opponent, player, col)){
				return col;
			}
		}
		
		// Otherwise, random
		return random(board);
	}
	
	/**
	 * Same as naive
	 * Except instead of a random move, pick the immediate move with the highest heuristic 
	 */
	public static int simple(Board board, Player player, Player opponent){
		
		int[] moves = board.getAvailableColumns();
		
		// if there is a winning move, take it
		for(int i = 0; i < moves.length; i++){
			int col = moves[i];
			
			if(isWinningMove(board, player, opponent, col)){
				return col;
			}
		}
		
		// if the opponent has a winning move, take it
		for(int i = 0; i < moves.length; i++){
			int col = moves[i];
			
			if(isWinningMove(board, opponent, player, col)){
				return col;
			}
		}
		
		// Otherwise, pick colun with highest heuristic		
		int bestCol = nextBestMove(board, player, opponent);
		return bestCol;
	}
	
	/**
	 * Move purely based on our written heuristic function: getHeuristic()
	 * Move only based on the next set of immediate moves ie greedy best choice
	 */
	public static int nextBestMove(Board board, Player player, Player opponent){
		
		int[] moves = board.getAvailableColumns();

		int minHeuristic = -1000;
		int highestCol = moves[0];
		for(int i = 0; i < moves.length; i++){
			int col = moves[i];
			
			board.addSymbol(col, player.getSymbol());
			int currHeuristic = getHeuristic(board, player, opponent);
			board.removeSymbol(col);
			
			if(currHeuristic > minHeuristic){
				highestCol = col;
				minHeuristic = currHeuristic;
			}
		}
		
		return highestCol;
	}
	
	/**
	 * @function minimax
	 * @description Search for the optimal move using a minimax tree to maximum player heuristic and 
	 * 				minimize opponent heurisitc
	 * 
	 * 				Searches at a depth of 3. (TODO: Adjust for any number of depth)
	 */
	public static int minimax(Board board, Player player, Player opponent){
		
		int[] firstMoves = board.getAvailableColumns();
		int[] heuristics = new int[firstMoves.length];
		
		for(int i = 0; i < heuristics.length; i++){
			heuristics[i] = 0;
		}
		
		// Check if there is an immediate win
		for(int i = 0; i < firstMoves.length; i++){
			int col = firstMoves[i];
			
			if(isWinningMove(board, player, opponent, col)){
				return col;
			}
		}
		
		// Otherwise, look at a tree of all combinations of the next three moves: player, opponent, player
		// then pick the best first move that contains the best future situations (max heuristic of all combinations after the first move) for the player
		//
		// It is important to note if the opponent is rational, then they will block any winning move on the third turn in the second turn
		// This has to be best calcualted with a heuristic, not a 'is win' situation
		for(int i = 0; i < firstMoves.length; i++){
			
			int firstCol = firstMoves[i];
			board.addSymbol(firstCol, player.getSymbol());
			int[] secondMoves = board.getAvailableColumns();
			
			for(int j = 0; j < secondMoves.length; j++){
				
				int secondCol = secondMoves[j];
				board.addSymbol(secondCol, opponent.getSymbol());
				int[] thirdMoves = board.getAvailableColumns();
				
				for(int k = 0; k < thirdMoves.length; k++){
					
					int thirdCol = thirdMoves[k];
					board.addSymbol(thirdCol, player.getSymbol());
					heuristics[i] += getSimpleHeuristic(board, player, opponent);
					board.removeSymbol(thirdCol);
				}	
				board.removeSymbol(secondCol);
			}	
			board.removeSymbol(firstCol);
		}
		
		// Return the first move that leads to the situations with the highest combined heuristics
		int maxIndex = 0;
		int maxHeuristic = heuristics[0];
		for(int i = 0; i < heuristics.length; i++){
			if(heuristics[i] > maxHeuristic){
				maxIndex = i;
				maxHeuristic = heuristics[i];
			}
		}
		
		return firstMoves[maxIndex];
	}
	
	/**
	 * @function optimalMiniMaxMove
	 * @description Traverse the tree bottom-up to find the optimal heuristic the player could make
	 */
	private static int getOptimalHeuristic(int[] list, int index, int depth, int height, boolean isMax){
		
		if(depth == height)
			return list[index];
		
		if(isMax){
			return Math.max(getOptimalHeuristic(list, index*2, depth+1, height, false), 
							getOptimalHeuristic(list, index*2+1, depth+1, height, false));
		} else {
			return Math.min(getOptimalHeuristic(list, index*2, depth+1, height, true), 
							getOptimalHeuristic(list, index*2+1, depth+1, height, true));
		}
	}
}
