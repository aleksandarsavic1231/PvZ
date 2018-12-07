/**
 * Controller is a Singleton Object that instantiates a Model.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class Controller {

	/**
	 * The Singleton instance of Controller.
	 */
	private static Controller singleton;
	
	/**
	 * The Model to be instantiated.
	 */
	private Model model;
	
	/**
	 * Constructor.
	 */
	private Controller() {
		model = new Model();
	}

	/**
	 * Get the instantiated instance of Controller.
	 * 
	 * @return Controller The Contoller Controller.
	 */
	public static Controller getInstance() {
		// Instantiate the Controller if not already done
		if (singleton == null) singleton = new Controller();
		return singleton;
	}
	
	/**
	 * Get the Model of this Controller.
	 * 
	 * @return Model The Model to this Controller.
	 */
	public Model getModel() {
		return model;
	}
	
}

