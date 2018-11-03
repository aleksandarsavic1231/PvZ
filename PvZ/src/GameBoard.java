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
	 * The lower bound ASCII character for all GameBoard objects.
	 */
	public static final int ASCII_LOWER_BOUND = 65;
	
	/**
	 * Tiles making up the game board.
	 */
	private char[][] tiles;
	
	/**
	 * Constructor.
	 */
	public GameBoard() {
		tiles = new char [ROWS][COLUMNS];
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
		return tiles[j][i] == ' ';
	}
	
	@Override
	public void clear () {iterateBoard((i, j) -> tiles[i][j] = ' '); }

	@Override
	public void print () {
		for (int j = 0; j < COLUMNS; j++) 
			System.out.print(Character.toString((char) (j + ASCII_LOWER_BOUND)) + " ");
		System.out.println();
		iterateBoard((i, j) -> {
			System.out.print(Character.toString(tiles[i][j]));
		    System.out.print(" ");
		    if (j == COLUMNS - 1) System.out.println(i);
		});
	}
	
	@Override
	public void addEntity(Entity e) {
		int i = e.getX();
		int j = e.getY(); 
		if (j < ROWS && i < COLUMNS && isEmpty(i, j)) {
			char identifier = ' '; 
			if (e instanceof PeaShooter) identifier = PeaShooter.IDENTIFIER;
			else if (e instanceof Sunflower) identifier = Sunflower.IDENTIFIER;
			else if (e instanceof Bullet) identifier = Bullet.IDENTIFIER;
			else if (e instanceof Zombie) identifier = Zombie.IDENTIFIER;
			tiles[j][i] = identifier;
		}
	}

	@Override
	public Point isValidLocation(int x, int y) {
		//int x = input.charAt(0) - ASCII_LOWER_BOUND;
		//int y = Character.getNumericValue(input.charAt(1));
		// Ensure input location is within the domain and range of game board.
		if (!(0 <= x && x < GameBoard.COLUMNS && 0 <= y && y < GameBoard.ROWS)) {
			//System.out.println("Invalid spawn location.");
			return null;
		} 
		return new Point(x, y);
	}
	
}
