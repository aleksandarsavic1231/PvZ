import java.awt.Point;

/**
 * Entity is a thing within the game that has a position. 
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Entity implements Cloneable {
	
	/**
	 * The position of this.
	 */
	private Point position;
	
	/**
	 * Constructor.
	 * 
	 * @param position The position of this.
	 */
	public Entity(Point position) {
		setPosition(position);
	}

	/**
	 * Get this position.
	 * 
	 * @return Point The current position of this.
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Set the position of this.
	 * 
	 * @param position The new position of this.
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	
	/**
	 * Deep clone Entity Object.
	 * 
	 * @param entity The Entity to be cloned.
	 * @return Entity The cloned Entity.
	 * @throws UnimplementedEntity.
	 */
	public static Entity clone(Entity entity) throws UnimplementedEntity {
		if (entity != null) {
			Point spawnLocation = new Point(entity.getPosition());
			if (entity instanceof RegularZombie) return new RegularZombie(spawnLocation);
			else if (entity instanceof PylonZombie) return new PylonZombie(spawnLocation);
			else if (entity instanceof PeaShooter) return new PeaShooter(spawnLocation);
			else if (entity instanceof Walnut) return new Walnut(spawnLocation);
			else if (entity instanceof Sunflower) return new Sunflower(spawnLocation);
			else if (entity instanceof Bullet) return new Bullet(spawnLocation, ((Bullet) entity).getDamage());
			else if (entity instanceof Sun) return new Sun(spawnLocation);
			else if (entity instanceof CherryBomb) return new CherryBomb(spawnLocation);
			else if (entity instanceof Repeater) return new Repeater(spawnLocation);
			else if (entity instanceof Chomper) return new Chomper(spawnLocation);
			else throw new UnimplementedEntity(entity.getClass() + " not cloneable");
		} return null;
	}

}
