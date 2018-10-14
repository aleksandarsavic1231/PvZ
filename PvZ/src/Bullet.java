import java.awt.Point;

public class Bullet extends Entity implements Moveable {
	
	private int damage;
	
	public static final int VELOCITY = 1;
	
	public Bullet(Point position, int damage) {
		super(position, '>');
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public void updatePosition() {
		getPoint().move(getX() + VELOCITY, getY());
	}	

}
