import java.awt.Point;
import java.util.LinkedList;

public class NextCommand extends Controller implements Executable {
	
	private LinkedList<Entity> lastEntities;
	
	private int lastBalance;
  	 
	public NextCommand(Model model) {
		super(model);	
		lastEntities = new LinkedList<Entity>();
	}

	@Override
	public void execute() {
		System.out.println("Execute Called (next):");
		
		lastBalance = getModel().getBalance();
		System.out.println("Last Balance: " + lastBalance);
		lastEntities.addAll(getModel().getEntities());
		for(Entity e: lastEntities) System.out.println("Last Entities: (" + e.getPosition().x + ", " + e.getPosition().y + ")");
		
		getModel().nextIteration();
		System.out.println("Updated Balance: " + getModel().getBalance());
		for(Entity e: getModel().getEntities()) System.out.println("New Entities: (" + e.getPosition().x + ", " + e.getPosition().y + ")");
		for(Entity e: lastEntities) System.out.println("Last Entities: (" + e.getPosition().x + ", " + e.getPosition().y + ")");
	}

	@Override
	public void undo() {
		System.out.println("Undo Called (next):");
		
		getModel().setEntities(lastEntities);
		getModel().setBalance(lastBalance);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public boolean isCollapsible(Executable command) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collapse(Executable command) {
		// TODO Auto-generated method stub
		
	}

}
