import java.awt.Point;

public class Repeater extends Shooter{

	public static final int COST = 200;
	
	public static final int DAMAGE = 4;
	
	public static final int RECHARGE_TIME = 3;
	
	public static final int SPAWN_COOLDOWN = 4;
	
	private static int nextDeployable;
	
	//public static final ImageIcon IMAGE = new ImageIcon("");
	
	public Repeater(Point position) {
		super(position, 5);
	}
	
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}

	public static boolean isDeployable(int gameCounter) {
		return nextDeployable <= gameCounter;
	}
	
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}
}
