import java.awt.Point;

/**
 * The Alive class applies to Entities that have a health attribute.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Alive extends Entity {

	/**
	 * The health of this.
	 */
	private int health;
	
	/**
	 * Constructor.
	 * 
	 * @param position Location of this.
	 * @param health Health of this.
	 */
	public Alive(Point position, int health) {
		super(position);
		this.health = health;
	}
	
	/**
	 * Get health of this.
	 * 
	 * @return int Health of this.
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * Set health after taking damage.
	 * 
	 * @param damage The damage received.
	 */
	public void takeDamage(int damage) {
		this.health -= damage;
	}
	
}
