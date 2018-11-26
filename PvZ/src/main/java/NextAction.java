import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextAction extends Command implements ActionListener {
	
	public NextAction(Model model, UndoManager undoManager) {
		super(model, undoManager);
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Only execute if game is running.
		if (!getModel().getIsRunning()) return; 
		getUndoManager().execute(new NextCommand(getModel()));
	}
	
}
