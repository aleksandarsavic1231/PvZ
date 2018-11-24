
public class EntityEvent extends Event {

	private final Entity entity;
	
	public EntityEvent(Action type, Entity entity) {
		super(type);
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}

}
