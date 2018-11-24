import java.awt.Point;

public class TileCommand extends Controller implements Undoable {
	
	private Point tile;
	
	private Entity lastEntity;
	
	private int lastBalance;
	
	private int lastGameCounter;
	
	private Plant lastPlantToggled;
	
	private boolean foundSun;
	
	public TileCommand(Model model, Point tile) {
		super(model);
		this.tile = tile;
	}

	@Override
	public void execute() {	
		Model model = getModel();
		// Set last balance and game counter
		lastBalance = model.getBalance();
		lastGameCounter = model.getGameCounter();	
		// Spawn plant only if the tile clicked contains no Sun.
		foundSun = false;
		for(Entity entity: model.getEntities()) {
			Point position = entity.getPosition();
			if (entity instanceof Sun && position.x == tile.x && position.y == tile.y) {
				lastEntity = Entity.clone(entity);
				model.removeEntity(entity);
				model.increaseBalance(Sun.REWARD);
				model.spawnEntities();
				foundSun = true;
				break;
			}
		}
		// Spawn plant only if no sun was found.
		if (!foundSun) {
			lastPlantToggled = model.getTogglePlant();
			lastEntity = Entity.clone(model.spawnPlant(tile));
		}

	}

	@Override
	public void undo() {
		Model model = getModel();
		// Last execution was collecting Sun reward
		if (foundSun) model.addEntity(lastEntity);
		// Last execution was spawning a plant
		else model.removeEntity(lastEntity);
		model.setGameCounter(lastGameCounter);
		model.setBalance(lastBalance);
		model.setTogglePlant(lastPlantToggled);
	}

	@Override
	public void redo() {
		execute();
	}

}
