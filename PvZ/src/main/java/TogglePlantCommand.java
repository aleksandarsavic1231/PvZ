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
	
	/**
	 * 
	 * @param model The Model to this TogglePlantCommand Object.
	 * @param plant The currently toggled plant.
	 */
	public TogglePlantCommand(Plant plant) {
		this.plant = plant;
	}

	@Override
	public void execute() {
		Model model = Controller.getInstance().getModel();
		model.setToggledPlant(plant);
	}

}
