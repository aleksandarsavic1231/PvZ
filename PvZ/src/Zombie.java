import java.awt.Point;

public class Zombie extends Alive implements Moveable {
	
	/**
	 * Damaged cause by Zombie
	 */
	public static final int DAMAGE = 2;
	
	/**
	 * Velocity of Zombie
	 */
	public static final int VELOCITY = 1;
	
	private boolean locked;

	public Zombie(Point position) {
		super(position, '<', 5);
		locked = false;
	}

	@Override
	public void updatePosition() {
		getPoint().setLocation(nextPosition());
		locked = true;
	}

	@Override
	public Point nextPosition() {
		return new Point(getX() - VELOCITY, getY());
	}	
	
	@Override
	public boolean isLocked() {
		return locked;
	}

	@Override
	public void unlock() {
		this.locked = false;
	}	

}


