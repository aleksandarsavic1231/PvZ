import java.awt.Point;

/**
 * Moveable interface.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public interface Moveable {
	
	/**
	 * Update current position.
	 */
	public void updatePosition();
	
	/**
	 * Get the next position of Moveable.
	 * 
	 * @return Point The next location of Moveable.
	 */
	public Point nextPosition();
	
	/**
	 * Unlock Moveable to allow call on update position.
	 */
	public void unlock();

}
