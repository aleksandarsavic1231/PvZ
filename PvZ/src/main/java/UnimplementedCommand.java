/**
 * UnimplementedCommand is a Exception thrown if a Command is not properly implemented.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public class UnimplementedCommand extends Exception {
	
	/**
	 * The default serial version for UnimplementedCommand Objects.
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message The message code causing the Exception to be thrown.
	 */
	public UnimplementedCommand(String message) {
		super(message);
	}

}
