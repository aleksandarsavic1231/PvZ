/**
 * UnimplementedLevel is a Exception thrown if a Level is not properly implemented.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public class UnimplementedLevel extends Exception {
	
	/**
	 * The default serial version for UnimplementedLevel Objects.
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message The message code causing the Exception to be thrown.
	 */
	public UnimplementedLevel(String message) {
		super(message);
	}

}
