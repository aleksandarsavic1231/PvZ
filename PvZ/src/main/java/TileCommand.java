import java.awt.Point;
import java.util.LinkedList;

/**
 * Tile command causes Sun to be collected or a Plant to spawn.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class TileCommand implements Undoable {
	
	/**
	 * The location of the tile selected.
	 */
	private Point tile;
	
	/**
	 * The last state of Model balance.
	 */
	private int lastBalance;

	/**
	 * The last state of toggled plant.
	 */
	private Plant lastToggledPlant;
	
	/**
	 * The last deployable time of toggled plant.
	 */
	private int lastDeployable;
	
	/**
	 * Whether sun was found.
	 */
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
	
	/**
	 * Constructor.
	 * 
	 * @param model The Model to this NextCommand Object.
	 * @param tile The location of the tile selected.
	 */
	public TileCommand(Point tile) {
		this.tile = tile;
		lastEntities = new LinkedList<Entity>();
	}

	@Override
	public void execute() {	
		Model model = Controller.getInstance().getModel();
		// Only spawn plant if tile contains no sun.
		foundSun = false;
		for(Entity entity: model.getEntities()) {
			Point position = entity.getPosition();
			if (entity instanceof Sun && position.x == tile.x && position.y == tile.y) {
				foundSun = true;
				lastToggledPlant = model.getToggledPlant();
				lastBalance = model.getBalance();
				model.removeEntity(entity);
				model.increaseBalance(Sun.REWARD);
			}
		}
		if (!foundSun) {
			executeSpawnPlant();
		} else {
			model.setToggledPlant(lastToggledPlant);
			for(Entity entity: model.getEntities())
				try {
					lastEntities.add(EntityFactory.clone(entity));
				} catch (UnimplementedEntity e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		}
		
	}

	@Override
	public void undo() {
		Model model = Controller.getInstance().getModel();
		if (foundSun) {
			lastEntities.add(new Sun(tile));
			model.setBalance(lastBalance);
			model.setEntities(lastEntities);
			model.setToggledPlant(lastToggledPlant);

		} else {
			undoSpawnPlant();	
		}
	}

	@Override
	public void redo() {
		lastEntities = new LinkedList<Entity>();
		execute();
	}
	
	private void executeSpawnPlant() {
		Model model = Controller.getInstance().getModel();
		// Set last balance and game counter
		lastToggledPlant = model.getToggledPlant();
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
		case CHOMPER:
			lastDeployable = Chomper.getNextDeployable();
			break;
		}
		
		for(Entity entity: model.getEntities())
			try {
				lastEntities.add(EntityFactory.clone(entity));
			} catch (UnimplementedEntity e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		lastBalance = model.getBalance();	
		model.spawnPlant(tile);
	}
	
	private void undoSpawnPlant() {
		Model model = Controller.getInstance().getModel();
		model.setEntities(lastEntities);
		model.setBalance(lastBalance);
		model.setToggledPlant(lastToggledPlant);
		// Switch on plant was spawned and set next deployable to last deployable state.
		if (lastToggledPlant == Plant.CHERRY_BOMB) CherryBomb.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.PEA_SHOOTER) PeaShooter.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.SUNFLOWER) Sunflower.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.WALNUT) Walnut.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.REPEATER) Repeater.hardSetNextDeployable(lastDeployable);
		else if (lastToggledPlant == Plant.CHOMPER) Chomper.hardSetNextDeployable(lastDeployable);
		// Update purchasable plants because next deployable has changed
		model.updatePurchasablePlants();
	}

	@Override
	public String toXMLString() {
		String XMLEncoding = 
				"<TileCommand>"
					+ "<Point>"
						+ "<x>" + tile.x + "</x>" 
						+ "<y>" + tile.y + "</y>"
					+ "</Point>" 
					+ "<lastBalance>" + lastBalance + "</lastBalance>"
					+ "<lastToggledPlant>" + lastToggledPlant + "</lastToggledPlant>"
					+ "<lastDeployable>" + lastDeployable + "</lastDeployable>"
					+ "<foundSun>" + foundSun + "</foundSun>"
					+ "<lastEntities>";
		for(Entity entity: lastEntities) XMLEncoding += entity.toXMLString();
		return XMLEncoding += "</lastEntities></TileCommand>";
	}
	
	public void setLastBalance(int lastBalance) { this.lastBalance = lastBalance; } 
	
	public void setFoundSun(boolean foundSun) { this.foundSun = foundSun; }
	
	public void setLastDeployable(int lastDeployable) { this.lastDeployable = lastDeployable; }
	
	public void setLastToggledPlant(Plant lastToggledPlant) { this.lastToggledPlant = lastToggledPlant; }
	
	public void setLastEntities(LinkedList<Entity> lastEntities) { this.lastEntities = lastEntities; }

}
