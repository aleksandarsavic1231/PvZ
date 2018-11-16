import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Zombie is a Moveable enemy that can do damage on impact with friendly objects.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Zombie extends Alive implements Moveable {
	
	/**
	 * Damaged caused by a Zombie object on impact with friendly objects.
	 */
	public static final int DAMAGE = 2;
	
	/**
	 * Velocity of Zombie objects.
	 */
	public static final int VELOCITY = 1;
	
	/**
	 * Image icon of Zombie objects.
	 */
	public ImageIcon IMAGE = new ImageIcon("src/main/resources/healthyzombie.png");
	
	/**
	 * Whether this is locked from moving.
	 */
	private boolean locked;

	/**
	 * Constructor.
	 * 
	 * @param position The spawn location of this.
	 */
	public Zombie(Point position) {
		super(position, 5);
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
	
	public void updateImage() {
		if (this.getHealth() < 3) {
			IMAGE = new ImageIcon("src/main/resources/brokenzombie.png");
		}
	}

}

