import java.awt.Point;

public interface Board {
		
	public void clear();

	public void print();
	
	public Point isValidLocation(String input);
			
	public void addEntity(Entity e);
	
	public void removeEntity(Entity e);

}
