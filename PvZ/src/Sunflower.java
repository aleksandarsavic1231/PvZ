import java.awt.Point;

public class Sunflower extends Shooter implements Plant{

	public static final int COST = 50;
	
	public static final int RECHARGE_TIME = 3;

	public static final int SPAWN_COOLDOWN = 2;
	
	public Sunflower(Point position) {
		super(position, '$', 100);
	}

	@Override
	public void resetFireRate() {
		this.setFireRate(RECHARGE_TIME);
	}
	
	
	
	
//	@Override
//	public void resetPlantCooldown() {
//		this.setPlantCooldown(SPAWN_COOLDOWN);
//	}

}
