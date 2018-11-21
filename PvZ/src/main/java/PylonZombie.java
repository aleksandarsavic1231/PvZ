import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * A Pylom Zombie is a Zombie with more health relative to a Regular Zombie.
 * 
 * @author kajhemmingsen
 * @version 20 Nov 18
 */
public class PylonZombie extends Zombie {
	
	/**
	 * Default health of Pylon Zombie objects.
	 */
	public static final int INITIAL_HEALTH = 10;
	
	/**
	 * Custom images of Pylon Zombie objects.
	 */
	public static final ImageIcon HEALTHY_PYLON = new ImageIcon("src/resources/healthyZombie.png");
	public static final ImageIcon DAMAGED_PYLON = new ImageIcon("src/resources/hurtZombie.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public PylonZombie(Point position) {
		super(position, INITIAL_HEALTH);
	}
	
}
