import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tile Action is Command that executes TileCommand on ActionEvent.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class TileAction extends Command implements ActionListener {
	
	/**
	 * The location of the tile clicked.
	 */
	private Point tileClicked;
	
	/**
	 * Constructor.
	 * 
	 * @param model The Model to this TileAction Object.
	 * @param undoManager The UndoManager to this TileAction Object.
	 * @param tileClicked The location of the tile clicked.
	 */
	public TileAction(Model model, UndoManager undoManager, Point tileClicked) {
		super(model, undoManager);
		this.tileClicked = tileClicked;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Only execute if game is running.
		if (!getModel().getIsRunning()) return; 
		getUndoManager().execute(new TileCommand(getModel(), tileClicked));
	}

}
