import java.awt.Point;

/**
 * Entity is a thing within the game that has a position. 
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public abstract class Entity {
	
	/**
	 * The position of the Entity.
	 */
	private Point position;
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn position of the Entity.
	 */
	public Entity(Point position) {
		this.position = position;
	}

	/**
	 * Get the x coordinate of the Entity.
	 * 
	 * @return int The x coordinate of the Entity.
	 */
	public int getX() {
		return position.x;
	}
	
	/**
	 * Get the y coordinate of the Entity.
	 * 
	 * @return int The y coordinate of the Entity.
	 */
	public int getY() {
		return position.y;
	}
	
	/**
	 * Get the location of this Entity.
	 * 
	 * @return Point The position of this Entity.
	 */
	public Point getPoint() {
		return position;
	}

}
