import java.awt.Point;

public class PeaShooter extends Shooter {
	
	public static final int COST = 100;
	
	public static final int RECHARGE_TIME = 2;
	
	public static final int DAMAGE = 2;

	public static final int SPAWN_COOLDOWN = 3;
	
	public PeaShooter(Point position) {
		super(position, '^', 100);
	}
	//edited fireRate
	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}
	
	@Override
	public void resetPlantCooldown() {
		this.setPlantCooldown(SPAWN_COOLDOWN);
	}

}
