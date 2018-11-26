/**
 * A functional interface to a Listener Object.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
@FunctionalInterface
public interface Listener {

	/**
	 * Handle an Event.
	 * 
	 * @param event The Event to be handled.
	 */
	public void handleEvent(Event event);

}
