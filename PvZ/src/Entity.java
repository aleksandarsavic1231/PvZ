import java.awt.Point;

public class Entity {
	
	private Point position;
	
	private char label;
	
	public Entity(Point position, char label) {
		this.position = position;
		this.label = label;
	}

	public int getX() {
		return (int) position.getX();
	}
	
	public int getY() {
		return (int) position.getY();
	}
	
	public Point getPoint() {
		return position;
	}
	
	public char getLabel() {
		return label;
	}

}
