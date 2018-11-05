/**
 * 
 * @author 
 * @version 5 Nov 18
 */
public class Application {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		View view = new View("Plants vs. Zombies");
		Model model = new Model();
		new Controller(view, model);
	}

}
