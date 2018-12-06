import java.util.LinkedList;

/**
 * Next Command causes PvZ to move to its next state.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class NextCommand implements Undoable {
	
	/**
	 * The last state of Model Entities.
	 */
	private LinkedList<Entity> lastEntities;
	
	/**
	 * The last state of Model balance.
	 */
	private int lastBalance;
	
	private Model model;

	/**
	 * Constructor.
	 * 
	 * @param model The Model to this NextCommand Object.
	 */
	public NextCommand() {	
		model = Controller.getInstance().getModel();
		lastEntities = new LinkedList<Entity>();
	}

	@Override
	public void execute() {
		// Save state of Model.
		lastBalance = model.getBalance();
		for(Entity entity: model.getEntities()) {
			try {
				lastEntities.add(EntityFactory.clone(entity));
			} catch (UnimplementedEntity e) {
				e.printStackTrace();
			} 
		}
		// Update to next game iteration.
		model.clearBoard();		
		model.updateShooters();
		model.updateMoveables();
		model.checkForDead();
		model.spawnEntities();
		model.incrementGameCounter();
		model.updatePurchasablePlants();
		// Add automatic welfare if payment period has elapsed 
		if (model.getGameCounter() % Model.PAYMENT_PERIOD == 0) model.increaseBalance(Model.WELFARE);	
		// Check if game is still runnable
		model.updateIsRunning();
	}

	@Override
	public void undo() {
		// Set Model to last game state.
		model.setEntities(lastEntities);
		model.setBalance(lastBalance);
		model.decrementGameCounter();
		model.updatePurchasablePlants();
	}

	@Override
	public void redo() {
		lastEntities = new LinkedList<Entity>();
		execute();
	}

	@Override
	public String toXMLString() {
		String XMLEncoding = 
				"<NextCommand>"
						+ "<lastBalance>" + lastBalance + "</lastBalance>"
						+ "<lastEntities>";
		for(Entity entity: lastEntities) XMLEncoding += entity.toXMLString();
		return XMLEncoding += "</lastEntities></NextCommand>";
	}
	
	public void setLastBalance(int lastBalance) { this.lastBalance = lastBalance; } 
	
	public void setLastEntities(LinkedList<Entity> lastEntities) { this.lastEntities = lastEntities; }

}
