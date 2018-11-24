import java.awt.Point;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * A Bomb is a device that randomly blows up.
 * 
 * @author Aleksandar1231 
 * @version 24 Nov 18
 */
public class Bomb extends Shooter {
	
	/**
	 * Sun points required to buy this.
	 */
	public static final int COST = 100;
	
	/**
	 * Max turns for bomb to randomly blow up
	 */
	private static final int MAX = 10;
	
	/**
	 * Min turns for bomb to randomly blow up
	 */
	private static final int MIN = 10;
	
	/**
	 * Random turn for bomb to randomly blow up
	 */
	private static final int RANDOM_NUMBER = new Random().nextInt(MAX + 1 - MIN) + MIN;
	
	/**
	 * Recharge time required by a Bomb objects to explode.
	 */
	public static final int RECHARGE_TIME = RANDOM_NUMBER;
	
	/**
	 * Damaged caused from this.
	 */
	public static final int DAMAGE = 100;

	/**
	 * Cooldown time required to spawn a new Bomb object.
	 */
	public static final int SPAWN_COOLDOWN = 5;
	
	/**
	 * The next game iteration a Bomb can be deployed.
	 */
	private static int nextDeployable;
	
	/**
	 * Icon image of Bomb objects.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/potatomine.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public Bomb(Point position) {
		super(position, 2);
		nextDeployable = 0;
		resetFireRate();
	}
	
	/**
	 * Set the next deployable game iteration a new Bomb object can be spawned.
	 *
	 * @param gameCounter The current game iteration.
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Whether a Bomb object is deployable.
	 * 
	 * @param gameCounter The current game iteration.
	 * @return boolean True if a Bomb object is deployable.
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	/**
	 * Reset next deployable time of a Bomb object.
	 */
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	
	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}
	
}