import java.awt.Point;
import java.util.LinkedList;

public class TileCommand extends Controller implements Undoable {
	
	private Point tile;
	
	private int lastBalance;

	private Plant lastToggledPlant;
	
	private int lastDeployable;
	
	private boolean foundSun;
	
	/**
	 * The last state of Entities before spawning a new Plant.
	 * 
	 * We store all Entities instead of just the newly spawned Plant because 
	 * NextCommand stores a deep clone of all Entities. Therefore if we tried 
	 * to remove only the newly spawned Plant it would fail since the deep clone
	 * has a different memory address. Thus, we need to store the state of all 
	 * Entities prior to spawning the plant so we can revert back to the previous 
	 * state.
	 */
	private LinkedList<Entity> lastEntities;
	
	public TileCommand(Model model, Point tile) {
		super(model);
		this.tile = tile;
		lastEntities = new LinkedList<Entity>();
	}

	@Override
	public void execute() {	
		Model model = getModel();
		// Only spawn plant if tile contains no sun.
		foundSun = false;
		for(Entity entity: getModel().getEntities()) {
			Point position = entity.getPosition();
			if (entity instanceof Sun && position.x == tile.x && position.y == tile.y) {
				foundSun = true;
				lastToggledPlant = model.getTogglePlant();
				lastBalance = model.getBalance();
				model.removeEntity(entity);
				model.increaseBalance(Sun.REWARD);
			}
		}
		if (!foundSun) {
			executeSpawnPlant(model);
		} else {
			model.setTogglePlant(lastToggledPlant);
			for(Entity entity: getModel().getEntities())
				try {
					lastEntities.add(Entity.clone(entity));
				} catch (UnimplementedEntity e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		}
		
	}

	@Override
	public void undo() {
		Model model = getModel();
		if (foundSun) {
			lastEntities.add(new Sun(tile));
			model.setBalance(lastBalance);
			model.setEntities(lastEntities);
			model.setTogglePlant(lastToggledPlant);

		} else {
			undoSpawnPlant(model);	
		}
	}

	@Override
	public void redo() {
		lastEntities = new LinkedList<Entity>();
		execute();
	}
	
	private void executeSpawnPlant(Model model) {
		// Set last balance and game counter
		lastToggledPlant = model.getTogglePlant();
		if (lastToggledPlant == null) return; // No plant selected 
		switch(lastToggledPlant) {
		case CHERRY_BOMB:
			lastDeployable = CherryBomb.getNextDeployable();
			break;
		case PEA_SHOOTER:
			lastDeployable = PeaShooter.getNextDeployable();
			break;
		case SUNFLOWER:
			lastDeployable = Sunflower.getNextDeployable();
			break;
		case WALNUT:
			lastDeployable = Walnut.getNextDeployable();
			break;
		case REPEATER:
			lastDeployable = Repeater.getNextDeployable();
			break;
		}
		for(Entity entity: model.getEntities())
			try {
				lastEntities.add(Entity.clone(entity));
			} catch (UnimplementedEntity e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		lastBalance = model.getBalance();	
		model.spawnPlant(tile);
	}
	
	private void undoSpawnPlant(Model model) {
		model.setEntities(lastEntities);
		model.setBalance(lastBalance);
		model.setTogglePlant(lastToggledPlant);

		// Switch on plant was spawned and set next deployable to last deployable state.
		if (lastToggledPlant == Plant.CHERRY_BOMB) CherryBomb.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.PEA_SHOOTER) PeaShooter.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.SUNFLOWER) Sunflower.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.WALNUT) Walnut.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.REPEATER) Repeater.hardSetNextDeployable(lastDeployable);
		// Update purchasable plants because next deployable has changed
		model.updatePurchasablePlants();
	}

}
