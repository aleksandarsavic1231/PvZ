/**
 * Command is a Object that has a Model and UndoManager.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class Command extends Controller {
	
	/**
	 * The UndoManager of this Command Object.
	 */
	private UndoManager undoManager;
	
	/**
	 * Constructor.
	 * 
	 * @param model The Model of this Command Object.
	 * @param undoManager The UndoManager of this Command Object.
	 */
	public Command(Model model, UndoManager undoManager) {
		super(model);
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
