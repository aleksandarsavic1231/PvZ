/**
 * UnimplementedPlant is a Exception thrown if a PvZ Plant is not properly implemented.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public class UnimplementedPlant extends Exception {
	
	/**
	 * The default serial version for UnimplementedPlant Objects.
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message The message code causing the Exception to be thrown.
	 */
	public UnimplementedPlant(String message) {
		super(message);
	}

}
