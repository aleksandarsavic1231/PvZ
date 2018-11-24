public class Event {

	private final Action type;
	
	public Event(Action type) {
		this.type = type;
	}

	public Action getType() {
		return type;
	}

}
