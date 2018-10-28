import java.awt.Point;

/** Zombie
 * 
 * Zombie has attributes damage, velocity
 * Updates the position of the zombie for each turn
 * Version: Oct 28, 2018
 * Author: Kyle Horne
 */

public class Zombie extends Alive implements Moveable {
	
	/**
	 * Damaged cause by Zombie
	 */
	public static final int DAMAGE = 2;
	
	/**
	 * Velocity of Zombie
	 */
	public static final int VELOCITY = 1;
	
	/**
	 * Character representing the Zombie on the GameBoard
	 */
	public static final char IDENTIFIER = '<';
	
	private boolean locked;

	public Zombie(Point position) {
		super(position, 5);
		locked = false;
	}

	/**
	 * Overrides are explained in Moveable interface
	 */
	@Override
	public void updatePosition() {
		if (!locked) getPoint().setLocation(nextPosition());
		locked = true;
	}

	@Override
	public Point nextPosition() {
		return new Point(getX() - VELOCITY, getY());
	}	

	@Override
	public void unlock() {
		this.locked = false;
	}	

}


