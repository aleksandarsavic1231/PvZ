
public interface Executable {

	public void execute();
	
	public void undo();
	
	public void redo();
	
	public boolean isCollapsible(Executable command);
	
	public void collapse(Executable command);

}
