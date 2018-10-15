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

	public Zombie(Point position) {
		super(position, '<', 5);
	}

	@Override
	public void updatePosition() {
		getPoint().setLocation(nextPosition());
	}

	@Override
	public Point nextPosition() {
		return new Point(getX() - VELOCITY, getY());
	}	


}


