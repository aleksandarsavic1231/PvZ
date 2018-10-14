import java.awt.Point;

public abstract class Shooter extends Alive {
	
	private int fireRate;
	
	public Shooter(Point position, char label, int health) {
		super(position, label, health);
		this.fireRate = 0;	
	}
	
	public int Cooldown() {
		return this.fireRate;
	}
	
	public int setFireRate(int fireRate) {
		return this.fireRate = fireRate;
	}
	
	public boolean isReady() {
		if (fireRate == 0) {
			resetFireRate();
			return true;
		} 
		this.fireRate--;
		return false;
	}
	
	abstract public void resetFireRate();

}
