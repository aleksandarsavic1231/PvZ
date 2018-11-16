import java.awt.Point;

/**
 * Shooter is object that can fire.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public abstract class Shooter extends Alive {
	
	/**
	 * The fire rate of this.
	 */
	private int fireRate;

	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 * @param health The initial health of this. 
	 */
	public Shooter(Point position, int health) {
		super(position, health);
		this.fireRate = 0;	
	}
	
	/**
	 * Set the fire rate of this.
	 * 
	 * @param fireRate The new fire rate.
	 */
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	/**
	 * Whether this can fire.
	 * 
	 * @return boolean True if the Shooter can shoot.
	 */
	public boolean canShoot() {
		if (--fireRate == 0) {
			resetFireRate();
			return true;
		} 
		return false;
	}
	
	abstract public void resetFireRate();

}