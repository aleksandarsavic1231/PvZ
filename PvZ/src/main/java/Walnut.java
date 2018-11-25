import java.awt.Point;

import javax.swing.ImageIcon;

public class Walnut extends Alive {
	
	public static final int INITIAL_HEALTH = 20;
	
	public static final int SPAWN_COOLDOWN = 4;
	
	public static final int COST = 50;
	
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/wallnut.png");
	
	private static int nextDeployable;
	
	public Walnut(Point position) {
		super(position, 20);
	}
	
	public static void setNextDeployable(int gameCounter) {
		nextDeployable = gameCounter + SPAWN_COOLDOWN;
	}
	
	public static boolean isDeployable(int gameCounter) {
		return (nextDeployable <= gameCounter);
	}
	
	public static void resetNextDeployable() {
		nextDeployable = 0;
	}
	
	public static int getNextDeployable() {
		return nextDeployable;
	}
	
	public static void hardSetNextDeployable(int newDeployable) {
		nextDeployable = newDeployable;
	}
	
}
