import java.awt.Point;

public class Bullet extends Entity implements Moveable {
	
	private int damage;
	
	public static final int VELOCITY = 1;
	
	private boolean locked;
	
	public Bullet(Point position, int damage) {
		super(position, '>');
		this.damage = damage;
		this.locked = false;
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public void updatePosition() {
		getPoint().setLocation(nextPosition());
		locked = true;
	}

	@Override
	public Point nextPosition() {
		return new Point(getX() + VELOCITY, getY());
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
