/**
 * Undoable is a interface that is Executable and allows undo/redo.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public interface Undoable extends Executable {
	
	/**
	 * The undo method.
	 */
	public void undo();
	
	/**
	 * The redo method.
	 */
	public void redo();

}
