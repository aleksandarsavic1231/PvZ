import java.awt.Point;

/** Shooter
 * 
 * Shooter applies to all entities that can shoot objects
 * Controls the fire rate for all entities
 */

public abstract class Shooter extends Alive {
	
	private int fireRate;

	public Shooter(Point position, int health) {
		super(position, health);
		this.fireRate = 0;	
	}
	
	/**
	 * Returns the firerate of a specific shooter
	 * @param fireRate
	 * @return int
	 */
	
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
	
	/**
	 * Determines whether a shooter can shoot or if it is still cooling down
	 * 
	 * @return boolean
	 */

	public boolean canShoot() {
		if (fireRate == 0) {
			resetFireRate();
			return true;
		} 
		this.fireRate--;
		return false;
	}
	
	abstract public void resetFireRate();

}