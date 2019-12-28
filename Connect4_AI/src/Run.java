
/**
 * Main to run everything
 * @author Avery Swank
 */
public class Run {

	public static void main(String[] args) {
		
		// Connect 4 AI setup
		int boardRowSize = 6;
		int boardColumnSize = 7;
		int gamesPerMatch = 100;
		
		// All the different types of players
		// Can play against other players or their opponent for head-to-head matches
		Player pHuman = new Player("Human Player", "h", "human");

		Player pRandom = new Player("Random Player", "a", "random");
		Player oRandom = new Player("Random Opponent", "b", "random");

		Player pNaive = new Player("Naive Player", "c", "naive");
		Player oNaive = new Player("Naive Opponent", "d", "naive");

		Player pSimple = new Player("Simple Player", "f", "simple");
		Player oSimple = new Player("Simple Opponent", "g", "simple");

		Player pHeuristic = new Player("Heuristic Player", "h", "heuristic");
		Player oHeuristic = new Player("Heuristic Opponent", "i", "heuristic");

		Player pMinimax = new Player("Minimax Player", "j", "minimax");
		Player oMinimax = new Player("Minimax Opponent", "k", "minimax");

		Player[] players = {pRandom, pNaive, pSimple, pHeuristic, pMinimax};
		Player[] oppoennts = {oRandom, oNaive, oSimple, oHeuristic, oMinimax};
		
		// Set the board
		Board connect4Board = new Board(boardRowSize, boardColumnSize);
		
		Game game;
		Match match;

		// Play a single match between two players
		/*game = new Game(connect4Board, pHuman, pMinimax);
		match = new Match(game, gamesPerMatch);
		match.play();
		System.out.println(match);

		pSimple.resetWinCount();
		pHeuristic.resetWinCount();*/

		// Play each type of player against each type of player
		for(int i = 0; i < players.length; i++){
			for(int j = i+1; j < players.length; j++){
				Player player = players[i];
				Player opponent = players[j];

				game = new Game(connect4Board, player, opponent);
				match = new Match(game, gamesPerMatch);
				match.play();
				System.out.println(match);

				player.resetWinCount();
				opponent.resetWinCount();
				game.getBoard().emptyBoard();
			}
		}

		 // Play each type of player against a copy of itself
		for(int i = 0; i < players.length; i++){
			Player player = players[i];
			Player opponent = oppoennts[i];

			game = new Game(connect4Board, player, opponent);
			match = new Match(game, gamesPerMatch);
			match.play();
			System.out.println(match);

			player.resetWinCount();
			opponent.resetWinCount();
			game.getBoard().emptyBoard();
		}
		
		System.out.println("Completed All Matches");
	}

}
