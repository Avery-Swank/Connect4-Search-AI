import java.util.ArrayList;

/**
 * Contains all of the logic for executing multiple games within a match between two players
 * @author Avery Swank
 */
public class Match {

	private Game game;
	private int numGames;
	private int numTies;
	private ArrayList<Integer> p1Moves;
	private ArrayList<Integer> p2Moves;
	
	public Match(Game _game, int _numGames){
		game = _game;
		numGames = _numGames;
		numTies = 0;
		
		p1Moves = new ArrayList<Integer>();
		p2Moves = new ArrayList<Integer>();
	}
	
	/**
	 * Play 'numGames' between two players
	 */
	public void play(){
		
		try{
			for(int g = 1; g <= numGames; g++){

				Player winner = game.play();
				int totalMoves = game.getBoard().getNumMoves();
				
				// Update Game Counters
				if(winner != null){
					//System.out.println(winner.getName() + " won in " + totalMoves + " moves!");
					winner.giveWin();
				} else {
					//System.out.println("Tie Game. No Points Awarded");
					numTies++;
				}
				
				// Update Move Counters
				if(winner == game.getPlayer1()){
					p1Moves.add(game.getBoard().getNumMoves());
				}
				
				if(winner == game.getPlayer2()){
					p2Moves.add(game.getBoard().getNumMoves());
				}
				
				game.getBoard().emptyBoard();
			}
		} catch (Error e) {
			System.out.println("Error Playing: " + game.getPlayer1().getName() + " vs. " + game.getPlayer2().getName());
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Return average integer of an ArrayList
	 */
	private double average(ArrayList<Integer> arr){
		int ave = 0;
		for(int i = 0;i < arr.size(); i++){
			ave += arr.get(i);
		}
		
		return (double) ave / arr.size();
	}
	
	/**
	 * Return Game playing
	 */
	public Game getGame(){
		return game;
	}
	
	/**
	 * Return number of games to play
	 */
	public int getNumGames(){
		return numGames;
	}
	
	/**
	 * Return number of ties in the match
	 */
	public int getNumTies(){
		return numTies;
	}
	
	public String toString(){
		
		double p1Average = average(p1Moves);
		double p2Average = average(p2Moves);
		
		String s = "------Match Results------\n";
		s += "Number of Games: " + numGames + "\n";
		s += "Number of Ties: " + numTies + "\n\n";
		s += game.getPlayer1();
		s += "Average Number of Moves to Win: " + p1Average + "\n\n";
		s += game.getPlayer2();
		s += "Average Number of Moves to Win: " + p2Average;

		return s;
	}
}
