import java.awt.Point;

/**
 * Shooter is object that can fire.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public abstract class Shooter extends Alive {
	
	/**
	 * The fire rate of this Shooter Object.
	 */
	private int fireRate;

	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this Shooter Object.
	 * @param health The initial health of this Shooter Object. 
	 */
	public Shooter(Point position, int health) {
		super(position, health);
		fireRate = 0;	
	}
	
	/**
	 * Set the fire rate of this Shooter Object.
	 * 
	 * @param fireRate The new fire rate of this Shooter Object.
	 */
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	/**
	 * Whether this Shooter Object can fire.
	 * 
	 * @return boolean True if this Shooter Object can shoot.
	 */
	public boolean canShoot() {
		if (fireRate-- == 0) {
			resetFireRate();
			return true;
		} 
		return false;
	}
	
	public abstract void resetFireRate();

}