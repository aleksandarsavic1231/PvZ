import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author kylehorne
 * @version 6 Nov 18
 */
public class Controller implements ActionListener {

	/**
	 * 
	 */
	private Model model;
	
	/**
	 * 
	 */
	private Dispatch dispatch;
	
	/**
	 * 
	 * @param model
	 * @param disptach
	 */
	public Controller(Model model, Dispatch disptach) {
		this.model = model;
		this.dispatch = disptach;
	}
	
	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		model.reducer(dispatch);
    }
	
}
