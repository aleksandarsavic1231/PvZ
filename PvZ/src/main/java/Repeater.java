import java.awt.Point;

import javax.swing.ImageIcon;

public class Repeater extends Shooter {

	public static final int COST = 200;
	
	public static final int RECHARGE_TIME = 2;
	
	public static final int DAMAGE = 4;
	
	public static final int SPAWN_COOLDOWN = 5;
	
	private static int nextDeployable;
	
	public static final ImageIcon IMAGE = new ImageIcon ("src/main/resources/repeater.png");
	
	public Repeater(Point position) {
		super(position, 5);
	}
	
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}
	
	public static int getNextDeployable() {
		return nextDeployable;
	}
	
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);

	}

}
