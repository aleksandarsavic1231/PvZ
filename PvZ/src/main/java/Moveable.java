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
	 * Get the next position this.
	 * 
	 * @return Point The next location of this.
	 */
	public Point nextPosition();
	
	/**
	 * Unlock this to allow call on update position.
	 */
	public void unlock();

}
