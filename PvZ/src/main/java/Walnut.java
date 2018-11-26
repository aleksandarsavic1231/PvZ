import java.awt.Point;

import javax.swing.ImageIcon;
/**
 * Walnut is a plant that exists to stop zombies from moving across the board
 * 
 * @author kajhemmingsen
 *@version Nov 25, 2018
 */
public class Walnut extends Alive {
	/**
	 * initial health for the Walnut
	 */
	public static final int INITIAL_HEALTH = 20;
	
	/**
	 * The number of turns required before another Walnut can be placed
	 */
	public static final int SPAWN_COOLDOWN = 4;
	
	/**
	 * Cost in Sun for one Walnut plant
	 */
	public static final int COST = 50;
	
	/**
	 * Custom image file for the Walnut
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/wallnut.png");
	
	/**
	 * Turns remaining until the next Walnut can be deployed
	 */
	private static int nextDeployable;
	
	/**
	 * Constructor
	 *
	 * @param position Where the Walnut will be placed
	 */
	public Walnut(Point position) {
		super(position, 20);
	}
	
	/**
	 * Sets when the next Walnut can be deployed
	 * 
	 * @param gameCounter Current game iteration
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Whether or not a Walnut can be placed
	 * @param gameCounter Current game iteration
	 * @return True if the gameCounter is larger than nextDeployable
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	/**
	 * Resets the counter to iteration 0 when the game is restarted
	 */
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	
	/**
	 * Returns the iteration the Walnut can be deployed again
	 * @return The game iteration Walnut can be placed
	 */
	public static int getNextDeployable() {
		return nextDeployable;
	}
	
	/**
	 * Manually input turn that Walnut can be placed
	 * @param newDeployable
	 */
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}
	
}
