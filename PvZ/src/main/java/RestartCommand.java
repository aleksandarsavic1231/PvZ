
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

	@Override
	public boolean isCollapsible(Executable command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collapse(Executable command) {
		// TODO Auto-generated method stub
		
	}

}
