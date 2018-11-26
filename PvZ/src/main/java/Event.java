/**
 * Event is a Object that has a Action.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class Event {

	/**
	 * The Action triggering this Event Object.
	 */
	private final Action type;
	
	/**
	 * Constructor.
	 * 
	 * @param type The type of Action triggering this Event Object.
	 */
	public Event(Action type) {
		this.type = type;
	}

	/**
	 * Get the type of Action triggering this Event Object.
	 * 
	 * @return Action The Action triggering this Event Object.
	 */
	public Action getType() {
		return type;
	}

}
