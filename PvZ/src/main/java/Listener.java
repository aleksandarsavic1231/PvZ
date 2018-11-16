/**
 * Listener is a interface to handle Events from the Event source.
 * 
 * @author kylehorne
 * @version 6 Nov 18
 */
public interface Listener {
	
	/**
	 * Handle Event caused by Event source.
	 * 
	 * @param event The event.
	 */
	void handleEvent(Event event);

}
