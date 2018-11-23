import java.awt.Point;

import javax.swing.ImageIcon;

public class BucketZombie extends Zombie{

	public static final int INITIAL_HEALTH = 20;
	
	/*public static final ImageIcon HEALTHY_BUCKET;
	public static final ImageIcon DAMAGED_BUCKET;
	public static final ImageIcon HEALTHY_ZOMBIE;
	public static final ImageIcon DAMAGED_ZOMBIE;*/
	
	public BucketZombie(Point position) {
		super(position, INITIAL_HEALTH);
	}

}
