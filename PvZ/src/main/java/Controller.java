/**
 * Controller is a Object that has a Model.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class Controller {

	private static Controller singleton;
	
	private Model model;
	
	private Controller() {
		model = new Model();
	}

	public static Controller getInstance() {
		if (singleton == null) singleton = new Controller();
		return singleton;
	}
	
	public Model getModel() {
		return model;
	}
	
}

