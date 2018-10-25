import java.awt.Point;

public class Bullet extends Entity implements Moveable {
	
	private int damage;
	
	public static final int VELOCITY = 1;
	
	private boolean locked;
	
	public static final char IDENTIFIER = '*';
	
	public Bullet(Point position, int damage) {
		super(position);
		this.damage = damage;
		this.locked = false;
	}

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
