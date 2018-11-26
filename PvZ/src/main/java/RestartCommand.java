
public class RestartCommand extends Command implements Executable {
	
	public RestartCommand(Model model, UndoManager undoManager) {
		super(model, undoManager);
	}
	
	@Override
	public void execute() {
		Model model = getModel();
		// Reset undo/redo on restart
		getUndoManager().clearUndoManager();
		model.clearBoard();
		PeaShooter.resetNextDeployable();
		Sunflower.resetNextDeployable();
		Walnut.resetNextDeployable();
		CherryBomb.resetNextDeployable();
		Repeater.resetNextDeployable();
		model.init();
		model.notifyOfBalance();
	}

}
