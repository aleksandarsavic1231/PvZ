import java.awt.Point;

/** Entity
 * 
 * Entity applies to all entities in the game
 * It keeps track of the location of all entities
 */

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
