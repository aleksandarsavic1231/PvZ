import java.awt.Point;

/** Board
 * 
 * Board interface consists of methods clear, print, isValidLocation, addEntity, removeEntity
 */

public interface Board {
		
	public void clear();

	public void print();
	
	public Point isValidLocation(String input);
			
	public void addEntity(Entity e);
	
	public void removeEntity(Entity e);

}
