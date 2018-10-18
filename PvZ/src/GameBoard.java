public class GameBoard implements Board {
	
	public static final int ROWS = 5;
	
	public static final int COLUMNS = 10;
	
	private char[][] tiles;
	
	public GameBoard() {
		tiles = new char [ROWS][COLUMNS];
		clear();
	}
	
	private void iterateBoard(Tile t) {
		for (int i = 0 ; i < ROWS ; i++){
			for (int j = 0 ; j < COLUMNS ; j++){
				t.update(i, j);
			}
		}	 
	}
	
	@Override
	public void clear () {iterateBoard((i, j) -> tiles[i][j] = ' '); }

	@Override
	public void print () {
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
	public void addEntity(Entity e) {
		// TODO: Throw exception if out of bounds
		// TODO: Loosen coupling 
		int i = e.getX();
		int j = e.getY(); 
		char c = tiles[j][i];
		if (j < ROWS && i < COLUMNS) {
			if (50 <= c && c <= 57) tiles[j][i]++;
			else if ((e instanceof Zombie) && c != ' ') tiles[j][i] = 50;
			else tiles[j][i] = e.getLabel();
		}
	}

	@Override
	public void removeEntity(Entity e) {
		 tiles[e.getY()][e.getX()] = ' ';
	}
	
}