import java.awt.Point;

/**
 * Interface to Moveable Objects.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public interface Moveable {
	
	/**
	 * Update the current position of this.
	 */
	public void updatePosition();
	
	/**
	 * Get the next position this.
	 * 
	 * @return Point The next location of this.
	 */
	public Point nextPosition();
	
	/**
	 * Unlock this to allow call on update position
	 * 
	 * Locking the Object after moving will prevent the Object 
	 * from updating position twice on one game iteration.
	 */
	public void unlock();

}
