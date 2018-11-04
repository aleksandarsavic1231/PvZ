import java.awt.Point;

/**
 * GameBoard consisting of multiple tiles. 
 * This class implements the Board interface.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class GameBoard implements Board {
	
	/**
	 * The number of rows on a GameBoard.
	 */
	public static final int ROWS = 5;
	
	/**
	 * The number of columns on a GameBoard.
	 */
	public static final int COLUMNS = 10;
	
	/**
	 * Tiles making up the game board.
	 */
	private Entity[][] tiles;
	
	/**
	 * Constructor.
	 */
	public GameBoard() {
		tiles = new Entity [ROWS][COLUMNS];
		clear();
	}
	
	/**
	 * Iterate over GameBoard and apply update.
	 * 
	 * @param t The tile.
	 */
	private void iterateBoard(Tile t) {
		for (int i = 0 ; i < ROWS ; i++){
			for (int j = 0 ; j < COLUMNS ; j++){
				t.update(i, j);
			}
		}	 
	}
	
	/**
	 * Check if tile is currently unoccupied.
	 * 
	 * @param i The location i of tile on game board.
	 * @param j The location i of tile on game board.
	 * @return boolean True if the location is unoccupied.
	 */
	private boolean isEmpty(int i, int j) {
		return tiles[j][i] == null;
	}
	
	@Override
	public void clear () { iterateBoard((i, j) -> tiles[i][j] = null); }
	
	@Override
	public void addEntity(Entity e) {
		int i = e.getX();
		int j = e.getY(); 
		if (j < ROWS && i < COLUMNS && isEmpty(i, j)) tiles[j][i] = e;
	}

	@Override
	public Point isValidLocation(int i, int j) {
		if (!(0 <= i && j < GameBoard.COLUMNS && 0 <= i && j < GameBoard.ROWS)) {
			return null;
		} 
		return new Point(i, j);
	}
	
}
