import java.awt.Point;

public class Sunflower extends Shooter {

	public static final int COST = 50;
	
	public static final int RECHARGE_TIME = 3;

	public Sunflower(Point position) {
		super(position, '$', 100);
	}

	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}

}
