
/**
 * Contains all player information
 * @author Avery Swank
 */
public class Player {

	private String name;
	private String symbol;
	private String type;
	private int winCount;
	
	public Player(String _name, String _symbol, String _type) {
		
		if(_symbol.equals("") || _symbol.equals("e") || _symbol.equals("_")) throw new Error("Player cannot have an empty '', 'e', '_' symbol");
		if(_symbol.length() > 1) throw new Error("Player cannot have a large symbol");
		
		name = _name;
		symbol = _symbol;
		type = _type;
		winCount = 0;
	}
	
	/**
	 * Return Player name
	 */
	public void resetWinCount(){
		winCount = 0;
	}
	
	/**
	 * Return Player name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Return Player symbol
	 */
	public String getSymbol(){
		return symbol;
	}
	
	/**
	 * Return the Player type
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Add a win to the Player's win count
	 */
	public void giveWin(){
		winCount++;
	}
	
	/**
	 * Return Player's number of wins
	 */
	public int getWinCount(){
		return winCount;
	}
	
	public String toString(){
		String s = "Player: " + name + '\n';
		s += "Symbol: " + symbol + '\n';
		s += "Wins: " + winCount + '\n';
		
		return s;
	}
}
