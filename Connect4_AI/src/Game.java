
/**
 * All of the logic to play a game of Connect4 between 2 Players
 * @author Avery Swank
 */
public class Game {
	
	private Board board;
	private Player p1;
	private Player p2;
	
	public Game(Board _board, Player _p1, Player _p2){
		
		if(_p1.getSymbol().equals(_p2.getSymbol())) throw new Error("Both Players cannot have the same symbol");
		
		board = _board;
		p1 = _p1;
		p2 = _p2;
	}
	
	/**
	 * Play a game of Connect 4 on a game board 'board' with two players 'p1' and 'p2'
	 * Each game is played alternating moves until a player wins or the board is full
	 * @return Returns the winning player
	 */
	public Player play(){
		
		Player currPlayer;
		Player currOpponent;
		
		int i = 0;
		while(!board.isBoardFull()){
			
			// Alternate between p1 and p2 for who is playing and who is the opponent for Search.java
			// Starting with p1
			int r = i % 2;
			currPlayer = (r == 0) ? p1 : p2;
			currOpponent = (r == 0) ? p2 : p1;
			i++;
			
			// Handles searching and picking a column to add a symbol
			// provided it can add to a column that is not full
			int col = move(currPlayer, currOpponent);
				
			// Make currPlayer's move
			board.addSymbol(col, currPlayer.getSymbol());
				
			// Check if there is winner
			String win = board.getConnect4(p1, p2);
			Player winner = getWinner(win);
			if(winner != null){
				return winner;
			}
		}
		
		return null;
	}
	
	/**
	 * Play a move based on which type of Player is playing
	 */
	public int move(Player player, Player opponent){
		
		int col = -1;
		
		switch(player.getType()){
			case "human":
				col = Search.humanMove(board, player);
				break;
			case "random":
				col = Search.random(board);
				break;
			case "naive":
				col = Search.naive(board, player, opponent);
				break;
			case "simple":
				col = Search.simple(board, player, opponent);
				break;
			case "heuristic":
				col = Search.nextBestMove(board, player, opponent);
				break;
			case "minimax":
				col = Search.minimax(board, player, opponent);
				break;
			default:
				throw new Error("Invalid Player Type");
		}
		
		return col;
	}
	
	/**
	 * Return the Player cooresponding to the winning symbol
	 * Otherwise, return null
	 */
	private Player getWinner(String winningSymbol){

		if(winningSymbol.equals(p1.getSymbol())){
			return p1;
		}
		
		if(winningSymbol.equals(p2.getSymbol())){
			return p2;
		}
		
		return null;
	}
	
	/**
	 * Return active Game Board
	 */
	public Board getBoard(){
		return board;
	}
	
	/**
	 * Return Player 1
	 */
	public Player getPlayer1(){
		return p1;
	}
	
	/**
	 * Return Player 2
	 */
	public Player getPlayer2(){
		return p2;
	}

}
