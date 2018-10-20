import java.awt.Point;

public class Sunflower extends Shooter {
	
	/**
	 * Sun points required to buy Sunflower
	 */
	public static final int COST = 50;
	
	/**
	 * Recharge time required to fire new bullet.
	 */
	public static final int RECHARGE_TIME = 3;

	/**
	 * Cooldown time required to spawn new PeaShooter
	 */
	public static final int SPAWN_COOLDOWN = 2;
	
	/**
	 * Next round the Sunflower can be deployed.
	 */
	public static int nextDeployable;
	
	public static final char IDENTIFIER = '$';
	
	public Sunflower(Point position) {
		super(position, 8);
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