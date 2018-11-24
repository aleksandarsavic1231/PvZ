
public class Command extends Controller {
	
	private UndoManager undoManager;
	
	public Command(Model model, UndoManager undoManager) {
		super(model);
		this.undoManager = undoManager;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

}
