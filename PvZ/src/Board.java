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
	 * Check whether a input location is within the domain and range of the board.
	 * Return is null if location is outside the scope of the domain and range.
	 * 
	 * @param input The input location.
	 * @return Point Point object of a valid input String. 
	 */
	public Point isValidLocation(int x, int y);
			
	/**
	 * Add Entity object to board.
	 * 
	 * @param e Entity object.
	 */
	public void addEntity(Entity e);


}
