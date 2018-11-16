/**
 * An Event has a Action and payload. 
 * 
 * @author kylehorne
 * @version 7 Nov 18
 */
public class Event {

	/**
	 * The Action of this.
	 */
	private final Action type;
	
	/**
	 * The payload of this.
	 */
	private final Object payload;
	
	/**
	 * Constructor.
	 * 
	 * @param type The type of Action for this.
	 * @param payload The payload coupled to this Event.
	 */
	public Event(Action type, Object payload) {
		this.type = type;
		this.payload = payload;
	}
	
	/**
	 * Get the type of Action of this.
	 * 
	 * @return Action This action.
	 */
	public Action getType() {
		return type;
	}
	
	/**
	 * Get the payload of this.
	 * 
	 * @return Object Payload of this.
	 */
	public Object getPayload() {
		return payload;
	}
	
}
