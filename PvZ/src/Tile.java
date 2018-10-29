/**
 * A functional interface.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
@FunctionalInterface
public interface Tile {
	
	/**
	 * Apply an update on tile.
	 * 
	 * @param i The i coordinate of the tile.
	 * @param j The j coordinate of the tile.
	 */
	public void update(int i, int j);
	
}

