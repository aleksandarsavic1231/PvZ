import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TileAction extends Controller implements ActionListener {
	
	private Point tileClicked;
	
	public TileAction(Model model, Point tileClicked) {
		super(model);
		this.tileClicked = tileClicked;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new TileCommand(getModel(), tileClicked).execute();
	}

}
