import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller is the communication channel from the View to Model.
 * 
 * @author kylehorne
 * @version 6 Nov 18
 */
public class Controller implements ActionListener {

	/**
	 * The Model to this.
	 */
	private Model model;
	
	/**
	 * The Event dispatched by this.
	 */
	private Event event;
	
	/**
	 * Constructor.
	 * 
	 * @param model The Model to this
	 * @param event The Event dispatched by this.
	 */
	public Controller(Model model, Event event) {
		this.model = model;
		this.event = event;
	}
	
	/**
	 * Execute the reducer with Event.
	 */
	public void actionPerformed(ActionEvent e) {
		model.reducer(event);
    }
	
}
