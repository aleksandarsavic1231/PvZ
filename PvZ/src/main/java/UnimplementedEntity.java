/**
 * UnimplementedEntity is a Exception thrown if a PvZ Entity is not properly implemented.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class UnimplementedEntity extends Exception {
	
	/**
	 * The default serial version for UnimplementedEntity Objects.
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message The message code causing the Exception to be thrown.
	 */
	public UnimplementedEntity(String message) {
		super(message);
	}

}
