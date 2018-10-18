import java.awt.Point;

public class Entity {
	
	private Point p;
	
	private char label;
	
	public Entity(Point p, char label) {
		this.p = p;
		this.label = label;
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
	
	public char getLabel() {
		return label;
	}

}
