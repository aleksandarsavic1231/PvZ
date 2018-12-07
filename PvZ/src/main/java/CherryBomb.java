import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * A CherryBomb is a device that detonates after three game iterations.
 * 
 * @author Aleksandar1231 
 * @version 24 Nov 18
 */
public class CherryBomb extends Shooter {
	
	/**
	 * Sun points required to buy a CherryBomb.
	 */
	public static final int COST = 150;
	
	/**
	 * Time required by CherryBomb Objects to explode.
	 */
	public static final int DETONATION_TIME = 3;
	
	/**
	 * Damaged caused from a CherryBomb explosion.
	 */
	public static final int DAMAGE = 100;

	/**
	 * Cooldown time required to spawn a new CherryBomb Object.
	 */
	public static final int SPAWN_COOLDOWN = 5;
	
	/**
	 * The next game iteration a CherryBomb may be deployed.
	 */
	private static int nextDeployable;
	
	/**
	 * Image icon of CherryBomb Objects.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/cherryBomb.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn position of this CherryBomb Object.
	 */
	public CherryBomb(Point position) {
		super(position, 1);
		resetFireRate();
	}
	
	/**
	 * Set the next deployable game iteration a new CherryBomb Object may be spawned.
	 *
	 * @param gameCounter The current game iteration.
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Whether a CherryBomb object is deployable.
	 * 
	 * @param gameCounter The current game iteration.
	 * @return boolean True if a CherryBomb object is deployable.
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	/**
	 * Reset the next deployable time for a CherryBomb.
	 */
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	
	@Override
	public void resetFireRate() {
		setFireRate(DETONATION_TIME);
	}
	
	/**
	 * Set a specific game iteration that a CherryBomb Object may be deployed.
	 * 
	 * @param newDeployable The specific game iteration a CherryBomb Object may be deployed.
	 */
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}
	
	/**
	 * Get the game iteration a new CherryBomb Object may be deployed.
	 * 
	 * @return int The next deployable game iteration a CherryBomb Object may be deployed.
	 */
	public static int getNextDeployable() {
		return nextDeployable;
	}

	@Override
	public String toXMLString() {
		return "<CherryBomb>" + 
					"<Point>" + 
						"<x>" + getPosition().x + "</x>" + 
						"<y>" + getPosition().y + "</y>" + 
					"</Point>" + 
					"<health>" + getHealth() + "</health>" +
					"<nextDeployable>" + getNextDeployable() + "</nextDeployable>" +
					"<fireRate>" + getFireRate() + "</fireRate>" +
				"</CherryBomb>";
	}

}