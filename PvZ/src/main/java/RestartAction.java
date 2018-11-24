import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartAction extends Command implements ActionListener {

	public RestartAction(Model model, UndoManager undoManager) {
		super(model, undoManager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new RestartCommand(getModel(), getUndoManager()).execute();
	}

}
