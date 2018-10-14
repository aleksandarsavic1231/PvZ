import java.awt.Point;

public abstract class Shooter extends Alive {
	
	private int cooldown;
	
	public Shooter(Point position, char label, int health) {
		super(position, label, health);
		this.cooldown = 0;	
	}
	
	public int Cooldown() {
		return this.cooldown;
	}
	
	public int setCooldown(int cooldown) {
		return this.cooldown = cooldown;
	}
	
	public boolean isReady() {
		if (cooldown == 0) {
			resetCooldown();
			return true;
		} 
		this.cooldown--;
		return false;
	}
	
	abstract public void resetCooldown();

}
