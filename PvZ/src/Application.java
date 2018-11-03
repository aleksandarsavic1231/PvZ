
public class Application {

	public static void main(String[] args) {
		View v = new View("PvZ");
		PvZModel model = new PvZModel();
		Controller control = new Controller(v, model);
		control.initController();
	}

}
