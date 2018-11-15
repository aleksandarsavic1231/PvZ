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
	 * The damage of this Bullet.
	 * Each bullet may have different damage depending on who fired.
	 */
	private int damage;
	
	/**
	 * The velocity of a Bullet.
	 */
	public static final int VELOCITY = 1;
	
	/**
	 * Is this bullet locked from moving.
	 */
	private boolean locked;
	
	/**
	 * Character representation of a Bullet.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("./resources/Bullet.png");
	/**
	 * Constructor.
	 * 
	 * @param position Location of this Bullet.
	 * @param damage This bullet damage on impact.
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
