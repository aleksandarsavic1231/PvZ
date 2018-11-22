import java.awt.Point;

public class TileCommand extends Controller implements Executable {
	
	private Point tileClicked;
	
	public TileCommand(Model model, Point tileClicked) {
		super(model);
		this.tileClicked = tileClicked;
	}

	@Override
	public void execute() {
		if (!getModel().containsSun(tileClicked)) getModel().spawnPlant(tileClicked);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
