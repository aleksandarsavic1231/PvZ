import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Sunflower is a object which spawns Sun. Sun can be interpreted as a Shooter (has fire rate).
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Sunflower extends Shooter {
	
	/**
	 * Sun points required to buy a Sunflower object.
	 */
	public static final int COST = 50;
	
	/**
	 * Recharge time required by a Sunflower object to spawn a new Sun object.
	 */
	public static final int RECHARGE_TIME = 3;

	/**
	 * Cooldown time required to spawn a new Sunflower object.
	 */
	public static final int SPAWN_COOLDOWN = 2;
	
	/**
	 * The next game iteration a Sunflower object can be deployed.
	 */
	private static int nextDeployable;
	
	/**
	 * Image icon of a Sunflower object.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/Sunflower.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public Sunflower(Point position) {
		super(position, 8);
		nextDeployable = 0;
	}
	
	/**
	 * Set the next deployable game iteration a new Sunflower object can be spawned.
	 *
	 * @param gameCounter The current game iteration.
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Whether a Sunflower object is deployable.
	 * 
	 * @param gameCounter The current game iteration.
	 * @return boolean True if a Sunflower object is deployable.
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