import java.awt.Point;

/** Alive
 * 
 * The Alive class only applies for entity that are still considered alive in the game.
 * It has the health attribute to keep track of each entities health
 *
 */

public class Alive extends Entity {

	private int health;
	
	public Alive(Point position, int health) {
		super(position);
		this.health = health;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		this.health -= health;
	}
	
}
