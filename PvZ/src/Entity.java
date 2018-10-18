import java.awt.Point;

public abstract class Entity {
	
	private Point p;
	
	public Entity(Point p) {
		this.p = p;
	}

	public int getX() {
		return p.x;
	}
	
	public int getY() {
		return p.y;
	}
	
	public Point getPoint() {
		return p;
	}

}
