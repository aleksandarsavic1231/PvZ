
public class RestartCommand extends Controller implements Executable {

	public RestartCommand(Model model) {
		super(model);
	}
	
	@Override
	public void execute() {
		getModel().restart();
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
