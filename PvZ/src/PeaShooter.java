import java.awt.Point;

/** PeaShooter
 * 
 * PeaShooter has attributes cost, recharge_time, damage
 * Determines when the peashooter can shoot and be deployed
 */

public class PeaShooter extends Shooter {
	
	/**
	 * Sun points required to buy PeaShooter
	 */
	public static final int COST = 100;
	
	/**
	 * Recharge time required to fire new bullet.
	 */
	public static final int RECHARGE_TIME = 2;
	
	/**
	 * Damaged cause by PeaShooter bullet
	 */
	public static final int DAMAGE = 2;

	/**
	 * Cooldown time required to spawn new PeaShooter
	 */
	public static final int SPAWN_COOLDOWN = 3;
	
	/**
	 * Next round the Sunflower can be deployed.
	 */
	public static int nextDeployable;
	
	public static final char IDENTIFIER = 'P';
	
	public PeaShooter(Point position) {
		super(position, 5);
		nextDeployable = 0;
	}

	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}	
	
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}

}