import java.awt.Point;

public interface Moveable {
	
	public Point nextPosition();
	
	public void updatePosition();
	
	public boolean isLocked();
	
	public void unlock();

}
