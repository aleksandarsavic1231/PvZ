public class GameBoard implements Board {
	
	public static final int ROWS = 5;
	
	public static final int COLUMNS = 10;
	
	private char[][] tiles;
	
	public GameBoard() {
		tiles = new char [ROWS][COLUMNS];
		clear();
	}
	
	private void iterateBoard(Tile tile) {
		for (int i = 0 ; i < ROWS ; i++){
			for (int j = 0 ; j < COLUMNS ; j++){
				tile.update(i, j);
			}
		}	 
	}
	
	@Override
	public void clear () {iterateBoard((i, j) -> tiles[i][j] = ' '); }

	@Override
	public void print () {
		/* TODO: If more than one zombie is standing on tile   
	    replace char with # indicating the # of zombies on tile */
		for (int j = 0; j < COLUMNS; j++) 
			System.out.print(Character.toString((char) (j + 65)) + " ");
		System.out.println();
		iterateBoard((i, j) -> {
			System.out.print(Character.toString(tiles[i][j]));
		    System.out.print(" ");
		    if (j == COLUMNS - 1) System.out.println(i);
		});
	}
	
	@Override
	public void addEntity(int i, int j, char c) {
		if (j < ROWS && i < COLUMNS) tiles[j][i] = c;
	}
	
}