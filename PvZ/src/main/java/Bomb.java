import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * A Bomb is a device that randomly blows up after three game iterations.
 * 
 * @author Aleksandar1231 
 * @version 24 Nov 18
 */
public class Bomb extends Shooter {
	
	/**
	 * Sun points required to buy this.
	 */
	public static final int COST = 150;
	
	/**
	 * Recharge time required by a Bomb objects to explode.
	 */
	public static final int RECHARGE_TIME = 3;
	
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
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/cherryBomb.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public Bomb(Point position) {
		super(position, 1);
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
		setFireRate(RECHARGE_TIME);
	}
	
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}
	
	public static int getNextDeployable() {
		return nextDeployable;
	}

}