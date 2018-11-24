import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextAction extends Controller implements ActionListener {
	
	private UndoManager undoManager;

	public NextAction(UndoManager undoManager, Model model) {
		super(model);
		this.undoManager = undoManager;
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		undoManager.execute(new NextCommand(getModel()));
	}
	
}
