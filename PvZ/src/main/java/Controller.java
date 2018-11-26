/**
 * Command is a Object that has a Model.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class Controller {

	/**
	 * The Model of this Controller Object.
	 */
	private Model model;
	
	/**
	 * Constructor.
	 * 
	 * @param model The Model of this Controller Object.
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	/**
	 * Get the Model of this Controller Object.
	 * 
	 * @return Model The Model of this Controller Object.
	 */
	public Model getModel() {
		return model;
	}
	
}
