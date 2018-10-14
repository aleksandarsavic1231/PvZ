import java.awt.Point;

public class PeaShooter extends Shooter {
	
	public static final int COST = 100;
	
	public static final int RECHARGE_TIME = 2;
	
	public static final int DAMAGE = 2;

	public PeaShooter(Point position) {
		super(position, '^', 100);
	}

	@Override
	public void resetCooldown() {
		this.setCooldown(RECHARGE_TIME);
	}

}
