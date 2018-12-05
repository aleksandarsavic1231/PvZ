import java.awt.Point;

import javax.swing.ImageIcon;


/**
 * Repeater is a plant that functions similarly to a peashooter but with increased damage and cost
 * 
 * @author kajhemmingsen
 *@version Nov 25, 2018
 */
public class Repeater extends Shooter {

	/**
	 * The cost in sun to buy
	 */
	public static final int COST = 200;
	
	/**
	 * The number of turns it takes to fire another shot
	 */
	public static final int RECHARGE_TIME = 2;
	
	/**
	 * The Bullet's damage
	 */
	public static final int DAMAGE = 4;
	
	/**
	 * The minimum amount of turns between placing a Repeater
	 */
	public static final int SPAWN_COOLDOWN = 5;
	
	/**
	 * Which game iteration a Repeater may be placed
	 */
	private static int nextDeployable;
	
	/**
	 * Icon for Repeater
	 */
	public static final ImageIcon IMAGE = new ImageIcon ("src/main/resources/repeater.png");
	
	/**
	 * Constructor
	 * @param position Where the Repeater is to be placed
	 */
	public Repeater(Point position) {
		super(position, 5);
	}
	
	/**
	 * Sets the next game iteration Repeater can be placed
	 * @param gameCounter Current game iteration
	 */
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	/**
	 * Manually enter the turn Repeater can be placed
	 * @param newDeployable Desired game iteration
	 */
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}

	public static int getNextDeployable() {
		return nextDeployable;
	}
	/**
	 * 
	 * @param gameCounter Game's current iteration
	 * @return True if game's current iteration is greater than nextDeployable 
	 */
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	/**
	 * Sets nextDeployable to 0
	 */
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);

	}

	@Override
	public String toXMLString() {
		return "<Repeater>" + 
					"<Point>" + 
						"<x>" + getPosition().x + "</x>" + 
						"<y>" + getPosition().y + "</y>" + 
					"</Point>" + 
					"<health>" + getHealth() + "</health>" +
					"<nextDeployable>" + getNextDeployable() + "</nextDeployable>" +
					"<fireRate>" + getFireRate() + "</fireRate>" +
				"</Repeater>";
	}

}
