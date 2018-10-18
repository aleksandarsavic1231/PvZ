import java.awt.Point;

public interface Moveable {
	
	public Point nextPosition();
	
	public void updatePosition();
	
	public void unlock();

}
