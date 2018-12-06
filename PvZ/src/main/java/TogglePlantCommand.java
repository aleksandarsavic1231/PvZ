/**
 * TogglePlantCommand causes a plant to be toggled in the Model.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class TogglePlantCommand implements Executable {

	/**
	 * The currently toggled plant.
	 */
	private Plant plant;
	
	private Model model;
	
	/**
	 * 
	 * @param model The Model to this TogglePlantCommand Object.
	 * @param plant The currently toggled plant.
	 */
	public TogglePlantCommand(Plant plant) {
		model = Controller.getInstance().getModel();
		this.plant = plant;
	}

	@Override
	public void execute() {
		model.setToggledPlant(plant);
	}

}
