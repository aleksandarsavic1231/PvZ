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
	 * Can new Sunflower be deployed
	 */
	public static boolean isDeployable;

	
	public Sunflower(Point position) {
		super(position, '$', 8);
		isDeployable = false;
	}

	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}	
	
	public static boolean isDeployable(int gameCounter) {
		if (gameCounter % SPAWN_COOLDOWN == 0) {
			isDeployable = true;
		} 
		return isDeployable;
	}

}
