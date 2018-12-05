/**
 * Undoable is a interface that is Executable, allows undo/redo, and Encodable.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public interface Undoable extends Executable, Encodable {
	
	/**
	 * The undo method.
	 */
	public void undo();
	
	/**
	 * The redo method.
	 */
	public void redo();

}
