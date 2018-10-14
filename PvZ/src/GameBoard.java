public class GameBoard implements Board {
	
	public static final int ROWS = 5;
	
	public static final int COLUMNS = 10;
	
	private char[][] tiles;
	
	public GameBoard() {
		tiles = new char [ROWS][COLUMNS];
		clear();
	}
	
	private void iterateBoard(Tile tile) {
		for (int j = 0 ; j < ROWS ; j++){
			for (int i = 0 ; i < COLUMNS ; i++){
				tile.update(i, j);
			}
		}	 
	}
	
	@Override
	public void clear () {iterateBoard((i, j) -> tiles[j][i] = ' '); }

	@Override
	public void print () {
		for (int i = 0; i < COLUMNS; i++) 
			System.out.print(Character.toString((char) (i + 65)) + " ");
		System.out.println();
		
		iterateBoard((i, j) -> {
			System.out.print(Character.toString(tiles[j][i]));
		    System.out.print(" ");
		    if (i == COLUMNS - 1) System.out.println(j);
		});
	}
	
	@Override
	public void addEntity(int i, int j, char c) {
		if (i < ROWS && j < COLUMNS) tiles[i][j] = c;
	}
	
}