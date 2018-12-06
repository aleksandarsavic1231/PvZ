
public class RestartCommand extends Command implements Executable {
	
	public RestartCommand(UndoManager undoManager) {
		super(undoManager);
	}
	
	@Override
	public void execute() {
		Model model = Controller.getInstance().getModel();
		// Reset undo/redo on restart
		getUndoManager().clearUndoManager();
		model.clearBoard();
		PeaShooter.resetNextDeployable();
		Sunflower.resetNextDeployable();
		Walnut.resetNextDeployable();
		Repeater.resetNextDeployable();
		CherryBomb.resetNextDeployable();
		Repeater.resetNextDeployable();
		model.init();
		model.notifyOfBalance();
	}

}
