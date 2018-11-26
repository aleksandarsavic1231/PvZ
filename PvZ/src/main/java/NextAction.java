import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Next Action is Command that executes NextCommand.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class NextAction extends Command implements ActionListener {
	
	/**
	 * Constructor.
	 * 
	 * @param model The Model to this NextAction Object.
	 * @param undoManager The UndoManager to this NextAction Object.
	 */
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
