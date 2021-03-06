import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TogglePlantAction is Command that executes TogglePlantCommand on ActionEvent.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class TogglePlantAction implements ActionListener {

	/**
	 * The toggled plant.
	 */
	private Plant plantToggled;
	
	/**
	 * Constructor.
	 * 
	 * @param model The Model to this RestartAction Object.
	 * @param plantToggled The toggled plant.
	 */
	public TogglePlantAction(Plant plantToggled) {
		this.plantToggled = plantToggled;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new TogglePlantCommand(plantToggled).execute();
	}

}
