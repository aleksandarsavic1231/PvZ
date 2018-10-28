import java.awt.Point;

/**
 * Entity is a location of a entity in the game.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public abstract class Entity {
	
	/**
	 * The location of the Entity.
	 */
	private Point p;
	
	/**
	 * Constructor.
	 * 
	 * @param p The location of the Entity.
	 */
	public Entity(Point p) {
		this.p = p;
	}

	/**
	 * Get the x coordinate of the Entity.
	 * 
	 * @return int The x coordinate of the Entity.
	 */
	public int getX() {
		return p.x;
	}
	
	/**
	 * Get the y coordinate of the Entity.
	 * 
	 * @return int The y coordinate of the Entity.
	 */
	public int getY() {
		return p.y;
	}
	
	/**
	 * Get the location of this Entity.
	 * 
	 * @return Point The location of this Entity.
	 */
	public Point getPoint() {
		return p;
	}

}
