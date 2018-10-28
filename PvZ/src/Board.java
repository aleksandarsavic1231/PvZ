import java.awt.Point;

/**
 * Board interface.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public interface Board {
		
	/**
	 * Clear board.
	 */
	public void clear();

	/**
	 * Print board.
	 */
	public void print();
	
	/**
	 * Check whether a input String is a valid location on the board.
	 * 
	 * @param input The input location.
	 * @return Point Point object of a valid input String.
	 */
	public Point isValidLocation(String input);
			
	/**
	 * Add Entity object to board.
	 * 
	 * @param e Entity object.
	 */
	public void addEntity(Entity e);

}
