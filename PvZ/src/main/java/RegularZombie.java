import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * A Regular Zombie in PvZ.
 * 
 * @author kajhemmingsen
 * @version 20 Nov 18
 */
public class RegularZombie extends Zombie {
	
	/**
	 * Default health of Regular Zombie objects.
	 */
	public static final int INITIAL_HEALTH = 5;
	
	/**
	 * Custom images of Regular Zombie objects.
	 */
	public static final ImageIcon HEALTHY_ZOMBIE = new ImageIcon("src/main/resources/healthyZombie.png");
	public static final ImageIcon HURT_ZOMBIE = new ImageIcon("src/main/resources/hurtZombie.png");

	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public RegularZombie(Point position) {
		super(position, INITIAL_HEALTH);
	}

}
