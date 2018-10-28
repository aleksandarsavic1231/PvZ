import java.awt.Point;

/**
 * Moveable interface.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public interface Moveable {
	
	/**
	 * Update the position of Moveable.
	 */
	public void updatePosition();
	
	/**
	 * Get the next position of Moveable.
	 * 
	 * @return
	 */
	public Point nextPosition();
	
	/**
	 * Unlock Moveable to allow update position.
	 */
	public void unlock();

}
