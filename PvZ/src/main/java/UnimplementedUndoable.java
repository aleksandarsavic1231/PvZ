/**
 * UnimplementedUndoable is a Exception thrown if a Undoable Command is not properly implemented.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public class UnimplementedUndoable extends Exception {
	
	/**
	 * The default serial version for UnimplementedUndoable Objects.
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message The message code causing the Exception to be thrown.
	 */
	public UnimplementedUndoable(String message) {
		super(message);
	}

}
