/**
 * A functional interface representing the PvZ Board.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
@FunctionalInterface
public interface Board {
	
	/**
	 * Number of rows on the PvZ Board.
	 */
	public static final int ROWS = 5;
	
	/**
	 * Number of columns on the PvZ Board.
	 */
	public static final int COLUMNS = 10;
	
	/**
	 * Apply an update to the PvZ Board at tile (i, j).
	 * 
	 * @param i The i coordinate of the Board.
	 * @param j The j coordinate of the Board.
	 */
	public void update(int i, int j);
	
	/**
	 * Iterate over the PvZ Board and apply an update.
	 * 
	 * @param board Implementation of lambda.
	 */
	public static void iterate(Board board) {
		for (int i = 0 ; i < ROWS ; i++){
			for (int j = 0 ; j < COLUMNS ; j++){
				board.update(i, j);
			}
		}	 
	}
	
	/**
	 * Whether a location is within the domain and range of the PvZ Board.
	 * 
	 * @param i The i coordinate.
	 * @param j The j coordinate.
	 * @return boolean True if it is a valid location.
	 */
	public static boolean isValidLocation(int i, int j) {
		return (0 <= j && j < COLUMNS && 0 <= i && i < ROWS);
	}
}

