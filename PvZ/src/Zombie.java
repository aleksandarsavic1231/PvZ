import java.awt.Point;

public class Zombie extends Alive implements Moveable {
	
	public static final int DAMAGE = 2;
	
	public static final int VELOCITY = 1;

	public Zombie(Point position) {
		super(position, '<', 100);
	}

	@Override
	public void updatePosition() {
		getPoint().move(getX() - VELOCITY, getY());
	}

}
