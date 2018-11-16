/**
 * A functional interface representing the PvZ Board.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
@FunctionalInterface
public interface Board {
	
	/**
	 * Number of rows on a Board.
	 */
	public static final int ROWS = 5;
	
	/**
	 * Number of columns on a Board.
	 */
	public static final int COLUMNS = 10;
	
	/**
	 * Apply an update to Board location.
	 * 
	 * @param i The i coordinate of the tile.
	 * @param j The j coordinate of the tile.
	 */
	public void update(int i, int j);
	
	/**
	 * Iterate over Board and apply update.
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
}

