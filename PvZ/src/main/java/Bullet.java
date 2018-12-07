import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Bullet is a Entity that can move and apply damage on collisions with Zombies.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Bullet extends Entity implements Moveable {
	
	/**
	 * The damage of this Bullet Object.
	 * 
	 * Damage is dependent on who fired the Bullet Object (therefore not static).
	 */
	private int damage;
	
	/**
	 * The velocity of Bullet Objects.
	 */
	public static final int VELOCITY = 1;
	
	/**
	 * Whether this Bullet Object is locked from moving.
	 */
	private boolean locked;
	
	/**
	 * Image icon of Bullet Objects.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/customBullet.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position Location of this Bullet Object.
	 * @param damage Damage of this Bullet Object.
	 */
	public Bullet(Point position, int damage) {
		super(position);
		this.damage = damage;
		this.locked = false;
	}

	/**
	 * Get the damage of this Bullet Object.
	 * 
	 * @return int The damage of this Bullet Object.
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Lock this Bullet Object from moving.
	 * 
	 * @param locked Whether this is locked.
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
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

	@Override
	public String toXMLString() {
		return "<Bullet>" + 
					"<Point>" + 
						"<x>" + getPosition().x + "</x>" + 
						"<y>" + getPosition().y + "</y>" + 
					"</Point>" + 
					"<damage>" + damage + "</damage>" +
					"<locked>" + locked + "</locked>" +
				"</Bullet>";
	}

}
