import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartAction extends Command implements ActionListener {

	public RestartAction(UndoManager undoManager) {
		super(undoManager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new RestartCommand(getUndoManager()).execute();
	}

}
