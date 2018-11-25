import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TogglePlantAction extends Controller implements ActionListener {

	private Plant plant;
	
	public TogglePlantAction(Model model, Plant plant) {
		super(model);
		this.plant = plant;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Only execute if game is running.
		if (!getModel().isRunning()) return; 
		new TogglePlantCommand(getModel(), plant).execute();
	}

}
