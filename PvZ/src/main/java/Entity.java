import java.awt.Point;

/**
 * Entity is a thing within the game that has a position. 
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public abstract class Entity implements Cloneable {
	
	/**
	 * The position of this.
	 */
	private Point position;
	
	/**
	 * Constructor.
	 * 
	 * @param position The position of this.
	 */
	public Entity(Point position) {
		setPosition(position);
	}

	/**
	 * Get this position.
	 * 
	 * @return Point The current position of this.
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Set the position of this.
	 * 
	 * @param position The new position of this.
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	
	/**
	 * Get the XML encoded representation of this.
	 * 
	 * @return String The Object encoded in XML.
	 */
	public abstract String toXMLString();

}
