import java.awt.Point;

/**
 * Entity is a thing within the game that has a position. 
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Entity {
	
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
		this.setPosition(position);
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

}
