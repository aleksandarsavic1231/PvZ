import java.awt.Point;

/**
 * Shooter is object that can fire sequentially.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public abstract class Shooter extends Alive {
	
	/**
	 * The fire rate of this Shooter.
	 */
	private int fireRate;

	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this Shooter.
	 * @param health The initial health of this Shooter. 
	 */
	public Shooter(Point position, int health) {
		super(position, health);
		this.fireRate = 0;	
	}
	
	/**
	 * Set the fire rate of this shooter.
	 * 
	 * @param fireRate
	 */
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	/**
	 * Whether the Shooter can fire.
	 * 
	 * @return
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