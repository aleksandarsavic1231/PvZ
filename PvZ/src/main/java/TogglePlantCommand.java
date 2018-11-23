
public class TogglePlantCommand extends Controller implements Executable {

	private Plant plant;
	
	public TogglePlantCommand(Model model, Plant plant) {
		super(model);
		this.plant = plant;
	}

	@Override
	public void execute() {
		getModel().setTogglePlant(plant);
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
