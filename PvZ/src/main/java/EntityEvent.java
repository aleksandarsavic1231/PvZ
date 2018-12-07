/**
 * EntityEvent is a Event that has a Entity attached as payload.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class EntityEvent extends Event {

	/**
	 * The Entity attached to this Event Object.
	 */
	private final Entity entity;
	
	/**
	 * Constructor.
	 * 
	 * @param type The type of Action triggering this EntityEvent Object.
	 * @param entity The Entity attached to this EntityEvent Object.
	 */
	public EntityEvent(Action type, Entity entity) {
		super(type);
		this.entity = entity;
	}
	
	/**
	 * Get the Entity attached to this EntityEvent Object.
	 * 
	 * @return Entity The Entity attached to this EntityEvent Object.
	 */
	public Entity getEntity() {
		return entity;
	}

}
