import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * ...
 * 
 * @author tashfiqakhand
 * @version 6 Dec 18
 */
public class Chomper extends Shooter{
	
	public static final int COST = 150;
	
	public static final int RECHARGE_TIME = 10;
	
	public static final int DAMAGE = 1000;
	
	public static final int SPAWN_COOLDOWN = 3;

	private static int nextDeployable;
	
	public static boolean lock;
	
	public static final ImageIcon READY = new ImageIcon("src/main/resources/chomperReady.png");
	public static final ImageIcon CHEW = new ImageIcon("src/main/resources/chomperChewing.png");


	public Chomper(Point position) {
		super(position, 10);
		this.lock = false;
		nextDeployable = 0;
		resetFireRate();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toXMLString() {
		return "<Chomper>" + 
				"<Point>" + 
					"<x>" + getPosition().x + "</x>" + 
					"<y>" + getPosition().y + "</y>" + 
				"</Point>" + 
				"<health>" + getHealth() + "</health>" +
				"<nextDeployable>" + getNextDeployable() + "</nextDeployable>" +
				"<fireRate>" + getFireRate() + "</fireRate>" +
			"</Chomper>";
	}

}