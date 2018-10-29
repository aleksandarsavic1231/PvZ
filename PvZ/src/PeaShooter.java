import java.awt.Point;

/**
 * PeaShooter is a Plant that can fire bullets.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class PeaShooter extends Shooter {
	
	/**
	 * Sun points required to buy a PeaShooter.
	 */
	public static final int COST = 100;
	
	/**
	 * Recharge time required by a PeaShooter to fire a new bullet.
	 */
	public static final int RECHARGE_TIME = 2;
	
	/**
	 * Damaged cause by a PeaShooter bullet.
	 */
	public static final int DAMAGE = 2;

	/**
	 * Cooldown time required to spawn a new PeaShooter.
	 */
	public static final int SPAWN_COOLDOWN = 3;
	
	/**
	 * The next game iteration a PeaShooter can be deployed.
	 */
	public static int nextDeployable;
	
	/**
	 * Character representation of a PeaShooter.
	 */
	public static final char IDENTIFIER = 'P';
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this PeaShooter.
	 */
	public PeaShooter(Point position) {
		super(position, 5);
		nextDeployable = 0;
	}

	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}	
	
	/**
	 * Set the next deployable game iteration a new PeaShooter can be spawned.
	 *
	 * @param gameCounter The current game iteration.
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Whether a PeaShooter is deployable.
	 * 
	 * @param gameCounter The current game iteration.
	 * @return boolean True if PeaShooter is deployable.
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}

}