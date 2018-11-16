import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Bullet is a Entity that can move and apply damage on impact with enemy forces.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Bullet extends Entity implements Moveable {
	
	/**
	 * The damage of this.
	 */
	private int damage;
	
	/**
	 * The velocity Bullet objects.
	 */
	public static final int VELOCITY = 1;
	
	/**
	 * Whether this is locked from moving.
	 */
	private boolean locked;
	
	/**
	 * Icon image of Bullet objects.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/custombullet.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position Location of this.
	 * @param damage Damage of this.
	 */
	public Bullet(Point position, int damage) {
		super(position);
		this.damage = damage;
		this.locked = false;
	}

	/**
	 * Get the damage of this.
	 * 
	 * @return int The damage of this.
	 */
	public int getDamage() {
		return damage;
	}

	@Override
	public void updatePosition() {
		if (!locked) getPosition().setLocation(nextPosition());
		locked = true;
	}

	@Override
	public Point nextPosition() {
		return new Point(getPosition().x + VELOCITY, getPosition().y);
	}

	@Override
	public void unlock() {
		this.locked = false;
	}

}
