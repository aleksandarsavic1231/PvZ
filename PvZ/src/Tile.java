/**
 * A functional interface.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
@FunctionalInterface
public interface Tile {
	
	/**
	 * The number of rows on a GameBoard.
	 */
	public static final int ROWS = 5;
	
	/**
	 * The number of columns on a GameBoard.
	 */
	public static final int COLUMNS = 10;
	
	/**
	 * Apply an update on tile.
	 * 
	 * @param i The i coordinate of the tile.
	 * @param j The j coordinate of the tile.
	 */
	public void update(int x, int y);
	
	/**
	 * Iterate over GameBoard and apply update.
	 * 
	 * @param t The tile.
	 */
	public static void iterateBoard(Tile t) {
		for (int i = 0 ; i < ROWS ; i++){
			for (int j = 0 ; j < COLUMNS ; j++){
				t.update(i, j);
			}
		}	 
	}
}

