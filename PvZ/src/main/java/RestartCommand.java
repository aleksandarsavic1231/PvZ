/**
 * Restart Command will reset the Model to its initial state.
 * 
 * @author kylehorne
 *
 */
public class RestartCommand extends Command implements Executable {
	
	/**
	 * Constructor.
	 * 
	 * @param undoManager The undoManager to this.
	 */
	public RestartCommand(UndoManager undoManager) {
		super(undoManager);
	}
	
	@Override
	public void execute() {
		Model model = Controller.getInstance().getModel();
		// Set UndoManager and Model to initial state
		getUndoManager().clearUndoManager();
		model.clearBoard();
		PeaShooter.resetNextDeployable();
		Sunflower.resetNextDeployable();
		Walnut.resetNextDeployable();
		Repeater.resetNextDeployable();
		CherryBomb.resetNextDeployable();
		Repeater.resetNextDeployable();
		Chomper.resetNextDeployable();
		model.restartGame();
		model.notifyOfBalance();
	}

}
