import java.awt.Point;

/**
 * Zombie is a Moveable enemy that can do damage on impact with friendly objects.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public abstract class Zombie extends Alive implements Moveable {
	
	/**
	 * Velocity of Zombie objects.
	 */
	public static final int VELOCITY = 1;
	
	/**
	 * Damaged caused by a Zombie object on impact with friendly objects.
	 */
	public static final int DAMAGE = 2;
	
	/**
	 * Whether this is locked from moving.
	 */
	private boolean locked;

	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public Zombie(Point position, int health) {
		super(position, health);
		locked = false;
	}

	@Override
	public void updatePosition() {
		if (!locked) getPosition().setLocation(nextPosition());
		locked = true;
	}

	@Override
	public Point nextPosition() {
		return new Point(getPosition().x - VELOCITY, getPosition().y);
	}

	@Override
	public void unlock() {
		this.locked = false;
	}	
	
}
