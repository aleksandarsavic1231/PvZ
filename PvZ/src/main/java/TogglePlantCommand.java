/**
 * TogglePlantCommand causes a plant to be toggled in the Model.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class TogglePlantCommand extends Controller implements Executable {

	/**
	 * The currently toggled plant.
	 */
	private Plant plant;
	
	/**
	 * 
	 * @param model The Model to this TogglePlantCommand Object.
	 * @param plant The currently toggled plant.
	 */
	public TogglePlantCommand(Model model, Plant plant) {
		super(model);
		this.plant = plant;
	}

	@Override
	public void execute() {
		getModel().setTogglePlant(plant);
	}

}
