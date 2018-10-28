import java.awt.Point;

/**
 * 
 * The Alive class only applies for Entities that have a health attribute.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Alive extends Entity {

	/**
	 * The health of this Entity.
	 */
	private int health;
	
	/**
	 * Constructor.
	 * 
	 * @param position Location of entity.
	 * @param health Health of entity.
	 */
	public Alive(Point position, int health) {
		super(position);
		this.health = health;
	}
	
	/**
	 * Get health of this Entity.
	 * 
	 * @return int Health of this Entity.
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * Set Entity health.
	 * 
	 * @param health New health of Entity.
	 */
	public void setHealth(int health) {
		this.health -= health;
	}
	
}
