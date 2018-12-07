/**
 * Command is a Object that has a UndoManager.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class Command {
	
	/**
	 * The UndoManager of this Command Object.
	 */
	private final UndoManager undoManager;
	
	/**
	 * Constructor.
	 * 
	 * @param undoManager The UndoManager of this Command Object.
	 */
	public Command(UndoManager undoManager) {
		this.undoManager = undoManager;
	}

	/**
	 * Get the UndoManager of this Command Object.
	 * 
	 * @return UndoManager The UndoManager of this Command Object.
	 */
	public UndoManager getUndoManager() {
		return undoManager;
	}

}