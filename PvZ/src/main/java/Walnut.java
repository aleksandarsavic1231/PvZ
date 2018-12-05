import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Walnut is a Plant that exists to stop Zombies from moving across the board.
 * 
 * @author kajhemmingsen
 *@version Nov 25, 2018
 */
public class Walnut extends Alive {
	
	/**
	 * The initial health of Cherry Bomb objects.
	 */
	public static final int INITIAL_HEALTH = 20;
	
	/**
	 * Cooldown time required to spawn a new Walnut object.
	 */
	public static final int SPAWN_COOLDOWN = 4;
	
	/**
	 * Sun points required to buy a Walnut.
	 */
	public static final int COST = 50;
	
	/**
	 * Icon image of Walnut Objects.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/wallnut.png");
	
	/**
	 * Turns remaining until the next Walnut can be deployed.
	 */
	private static int nextDeployable;
	
	/**
	 * Constructor
	 *
	 * @param position The spawn location of this Walnut Object.
	 */
	public Walnut(Point position) {
		super(position, INITIAL_HEALTH);
	}
	
	/**
	 * Set the next deployable game iteration a new Walnut object can be spawned.
	 *
	 * @param gameCounter The current game iteration.
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Whether or not a Walnut object is deployable.
	 * 
	 * @param gameCounter The current game iteration.
	 * @return boolean True if a Walnut object is deployable.
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	/**
	 * Reset the next deployable time of a Walnut object.
	 */
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	
	/**
	 * Get the game iteration a new Walnut Object may be deployed.
	 * 
	 * @return int The next deployable game iteration a Walnut Object may be deployed.
	 */
	public static int getNextDeployable() {
		return nextDeployable;
	}
	
	/**
	 * Set a specific game iteration that a Walnut Object may be deployed.
	 * 
	 * @param newDeployable The specific game iteration a Walnut Object may be deployed.
	 */
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}

	@Override
	public String toXMLString() {
		return "<Walnut>" + 
					"<Point>" + 
						"<x>" + getPosition().x + "</x>" + 
						"<y>" + getPosition().y + "</y>" + 
					"</Point>" + 
					"<health>" + getHealth() + "</health>" +
					"<nextDeployable>" + getNextDeployable() + "</nextDeployable>" +
				"</Walnut>";
	}
	
}
