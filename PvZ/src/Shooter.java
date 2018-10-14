import java.awt.Point;

public abstract class Shooter extends Alive {
	
	private int fireRate;

	
	public Shooter(Point position, char label, int health) {
		super(position, label, health);
		this.fireRate = 0;	
	}
	
	public int setFireRate(int fireRate) {
		return this.fireRate = fireRate;
	}
	
//	public int setPlantCooldown(int plantCooldown) {
//		return this.plantCooldown = plantCooldown;
//	}
	
	public boolean canShoot() {
		if (fireRate == 0) {
			resetFireRate();
			return true;
		} 
		this.fireRate--;
		return false;
	}
	
//	public boolean canPlant() {
//		if (plantCooldown == 0) {
//			resetPlantCooldown();
//			return true;
//		}
//		this.plantCooldown--;
//		return false;
//	}
	abstract public void resetFireRate();

}
