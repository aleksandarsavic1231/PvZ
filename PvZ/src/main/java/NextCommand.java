
public class NextCommand extends Controller implements Executable {

	public NextCommand(Model model) {
		super(model);
	}

	@Override
	public void execute() {
		getModel().nextIteration();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
