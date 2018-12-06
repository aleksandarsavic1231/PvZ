
public class RestartCommand extends Command implements Executable {
	
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
		model.restartGame();
		model.notifyOfBalance();
	}

}
