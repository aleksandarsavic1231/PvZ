import java.awt.Point;

/**
 * Bullet applies to Entities that can shoot objects.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Bullet extends Entity implements Moveable {
	
	/**
	 * The damage of this Bullet.
	 */
	private int damage;
	
	/**
	 * The velocity of all Bullet objects.
	 */
	public static final int VELOCITY = 1;
	
	/**
	 * Is this bullet locked from moving.
	 */
	private boolean locked;
	
	/**
	 * The identifier for all Bullet objects.
	 */
	public static final char IDENTIFIER = '*';
	
	/**
	 * Constructor.
	 * 
	 * @param position Location of entity.
	 * @param damage Bullet damage on impact.
	 */
	public Bullet(Point position, int damage) {
		super(position);
		this.damage = damage;
		this.locked = false;
	}

	/**
	 * Get the damage of this bullet.
	 * 
	 * @return int The damage of this bullet.
	 */
	public int getDamage() {
		return damage;
	}

	@Override
	public void updatePosition() {
		if (!locked) getPoint().setLocation(nextPosition());
		locked = true;
	}

	@Override
	public Point nextPosition() {
		return new Point(getX() + VELOCITY, getY());
	}

	@Override
	public void unlock() {
		this.locked = false;
	}

}
