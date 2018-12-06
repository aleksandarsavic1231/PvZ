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
	public NextAction(UndoManager undoManager) {
		super(undoManager);
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		getUndoManager().execute(new NextCommand());
	}
	
}
