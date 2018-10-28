import java.awt.Point;

/** Sunflower
 * 
 * Sunflower has attributes cost, recharge_time, damage
 * Determines when the sunflower can be deployed
 * Version: Oct 28, 2018
 * Author: Kyle Horne
 */

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
	/**
	 * Character representation of Sunflower on GameBoard
	 */
	public static final char IDENTIFIER = '$';
	
	public Sunflower(Point position) {
		super(position, 8);
		nextDeployable = 0;
	}

	/** 
	 * All Overrides are found in Shooter class
	 */
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