import java.awt.Point;

/**
 * The Alive class applies to Entities that have a health attribute.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public abstract class Alive extends Entity {

	/**
	 * The health of this Alive Object.
	 */
	private int health;
	
	/**
	 * Constructor.
	 * 
	 * @param position Location of this Alive Object.
	 * @param health Health of this Alive Object.
	 */
	public Alive(Point position, int health) {
		super(position);
		this.health = health;
	}
	
	/**
	 * Get the health of this Alive Object.
	 * 
	 * @return int Health of this Alive Object.
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Update the health of this Alive Object based on damaged received.
	 * 
	 * @param damage The damage received.
	 */
	public void takeDamage(int damage) {
		health -= damage;
	}
	
	/**
	 * Kill this Alive Object.
	 */
	public void selfDestruct() {
		health = 0;
	}
	
	/**
	 * Set the health of this Alive Object.
	 * 
	 * @param health The new health of this Alive Object.
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
}
