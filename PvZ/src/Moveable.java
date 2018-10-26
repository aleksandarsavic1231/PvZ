import java.awt.Point;

/** Moveable
 * 
 * Moveable interface consists of methods updatePosition, nextPosition, unlock
 */

public interface Moveable {
	
	public void updatePosition();
	
	public Point nextPosition();
	
	public void unlock();

}
