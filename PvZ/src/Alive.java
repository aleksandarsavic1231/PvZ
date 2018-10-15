import java.awt.Point;

public class Alive extends Entity {

	private int health;
	
	public Alive(Point position, char label, int health) {
		super(position, label);
		this.health = health;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		this.health -= health;
	}
	
}
