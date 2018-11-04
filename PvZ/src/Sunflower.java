import java.awt.Point;

import javax.swing.ImageIcon;

/** Sunflower
 * 
 * Sunflower has attributes cost, recharge_time, damage
 * Determines when the sunflower can be deployed
 * Version: Oct 28, 2018
 * Author: Kyle Horne
 */

/**
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Sunflower extends Shooter {
	
	/**
	 * Sun points required to buy a Sunflower.
	 */
	public static final int COST = 50;
	
	/**
	 * Recharge time required by a Sunflower to fire a new bullet.
	 */
	public static final int RECHARGE_TIME = 3;

	/**
	 * Cooldown time required to spawn a new Sunflower.
	 */
	public static final int SPAWN_COOLDOWN = 2;
	
	/**
	 * The next game iteration a Sunflower can be deployed.
	 */
	public static int nextDeployable;
	
	/**
	 * Character representation of a Sunflower.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("./resources/Sunflower_Scaled.png");
	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this Sunflower.
	 */
	public Sunflower(Point position) {
		super(position, 8);
		nextDeployable = 0;
	}

	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}	
	
	/**
	 * Set the next deployable game iteration a new Sunflower can be spawned.
	 *
	 * @param gameCounter The current game iteration.
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Whether a Sunflower is deployable.
	 * 
	 * @param gameCounter The current game iteration.
	 * @return boolean True if Sunflower is deployable.
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}

}