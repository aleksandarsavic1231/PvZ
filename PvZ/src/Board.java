import java.awt.Point;

public interface Board {
		
	public void clear();

	public void print();
	
	public boolean isTaken(Point p);
		
	public void addEntity(Entity e);

}
