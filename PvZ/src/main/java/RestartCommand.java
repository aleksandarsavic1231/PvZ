
public class RestartCommand extends Controller implements Executable {

	public RestartCommand(Model model) {
		super(model);
	}
	
	@Override
	public void execute() {
		Model model = getModel();
		model.clearBoard();
		PeaShooter.resetNextDeployable();
		Sunflower.resetNextDeployable();
		Walnut.resetNextDeployable();
		model.init();
		model.notifyOfBalance();
	}

}
