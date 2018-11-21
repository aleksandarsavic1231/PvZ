import java.awt.Point;

import javax.swing.ImageIcon;

public class PylonZombie extends Alive implements Moveable{

	public static final int DAMAGE = 2;
	
	public static final int VELOCITY = 1;
	
	public static final ImageIcon HEALTHY_PYLON = new ImageIcon("src/resources/Zombie.png");
	public static final ImageIcon DAMAGED_PYLON = new ImageIcon("src/resources/Zombie.png");
	public static final ImageIcon HEALTHY_ZOMBIE = new ImageIcon("src/main/resources/healthyZombie.png");
	public static final ImageIcon HURT_ZOMBIE = new ImageIcon("src/main/resources/hurtZombie.png");
	
	public static final int INITIAL_HEALTH = 10;
	
	private boolean locked;
	
	public PylonZombie(Point position) {
		super(position,INITIAL_HEALTH);
		locked = false;
	}
	
	public void updatePosition() {
		if(!locked) getPosition().setLocation(nextPosition());
		locked = true;
	}
	
	public Point nextPosition() {
		return new Point(getPosition().x - VELOCITY, getPosition().y);
	}
	
	public void unlock() {
		this.locked = false;
	}
	
}
