import java.util.LinkedList;

public class NextCommand extends Controller implements Undoable {
	
	private LinkedList<Entity> lastEntities;
	
	private int lastBalance;
	
	private int lastGameCounter;

	public NextCommand(Model model) {
		super(model);	
		lastEntities = new LinkedList<Entity>();
	}

	@Override
	public void execute() {
		Model model = getModel();
		// Only execute if game is running.
		if (!model.isRunning()) return;
		// Save last state of current Model.
		lastBalance = model.getBalance();
		lastGameCounter = model.getGameCounter();
		for(Entity entity: model.getEntities()) {
			try { lastEntities.add(Entity.clone(entity)); } 
			catch (UnimplementedCloneableEntity e) { e.printStackTrace(); }
		}
		// Update to next game iteration.
		model.clearBoard();		
		model.updateShooters();
		model.updateMoveables();
		model.checkForDead();
		model.spawnEntities();
		model.incrementGameCounter();
		// Add automatic welfare if payment period has elapsed 
		if (model.getGameCounter() % Model.PAYMENT_PERIOD == 0) model.increaseBalance(Model.WELFARE);	
		// Check if game is still runnable
		model.updateRunnable();
	}

	@Override
	public void undo() {
		Model model = getModel();
		// Only undo if game is running.
		if (!model.isRunning()) return;
		// Set Model to last game state.
		model.setEntities(lastEntities);
		model.setBalance(lastBalance);
		model.setGameCounter(lastGameCounter);
	}

	@Override
	public void redo() {
		lastEntities = new LinkedList<Entity>();
		execute();
	}

}
