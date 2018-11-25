import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * PeaShooter is a Plant that can fire bullets.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class PeaShooter extends Shooter {
	
	/**
	 * Sun points required to buy this.
	 */
	public static final int COST = 100;
	
	/**
	 * Recharge time required by a PeaShooter objects to fire a new bullet.
	 */
	public static final int RECHARGE_TIME = 2;
	
	/**
	 * Damaged caused from fired Bullets from this.
	 */
	public static final int DAMAGE = 2;

	/**
	 * Cooldown time required to spawn a new PeaShooter object.
	 */
	public static final int SPAWN_COOLDOWN = 3;
	
	/**
	 * The next game iteration a PeaShooter can be deployed.
	 */
	private static int nextDeployable;
	
	/**
	 * Icon image of PeaShooter objects.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/customPeaShooter.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public PeaShooter(Point position) {
		super(position, 5);
	}
	
	/**
	 * Set the next deployable game iteration a new PeaShooter object can be spawned.
	 *
	 * @param gameCounter The current game iteration.
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}
	
	public static int getNextDeployable() {
		return nextDeployable;
	}
	
	/**
	 * Whether a PeaShooter object is deployable.
	 * 
	 * @param gameCounter The current game iteration.
	 * @return boolean True if a PeaShooter object is deployable.
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	/**
	 * Reset next deployable time of a PeaShooter object.
	 */
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	
	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}	

}