import java.awt.Point;

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
	
	public static final char IDENTIFIER = '^';
	
	/**
	 * Can new PeaShooter be deployed
	 */
	public static boolean isDeployable;
	
	public PeaShooter(Point position) {
		super(position, 5);
		isDeployable = false;
	}

	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}
	
	public static void isDeployable(int gameCounter) {
		if (gameCounter % SPAWN_COOLDOWN == 0) {
			isDeployable = true;
		} 
	}

}
