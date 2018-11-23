import java.util.LinkedList;

public class NextCommand extends Controller implements Executable {
	
	private LinkedList<Entity> lastState;
  	 
	public NextCommand(Model model) {
		super(model);	
	}

	@Override
	public void execute() {
		lastState = getModel().getEntities();
		for(Entity e: lastState) System.out.println(e.getClass().getName() + ": "+ e.getPosition().x + ", " + e.getPosition().y);
		getModel().nextIteration();
	}

	@Override
	public void undo() {
		System.out.println("Undo Next Iteration:");
		for(Entity e: lastState) System.out.println(e.getClass().getName() + ": "+ e.getPosition().x + ", " + e.getPosition().y);
		
		getModel().setEntities(lastState);
		getModel().lastIteration();  
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
