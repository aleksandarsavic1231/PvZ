import java.awt.Point;

public interface Moveable {
	
	public void updatePosition();
	
	public Point nextPosition();
	
	public void unlock();

}
