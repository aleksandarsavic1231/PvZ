import java.util.LinkedList;
import java.util.Stack;

/**Handles incoming commands from game events and places them on a stack so that a user can undo an action, or redo a previously performed action.
 * 
 * @author kylehorne
 *@version Nov 25, 2018
 */
public class UndoManager {
	
	private LinkedList<Listener> listeners;

 	private Stack<Undoable> undoStack;
 	
	private Stack<Undoable> redoStack;

	/**
	 * Constructor
	 */
	public UndoManager() {
		listeners = new LinkedList<Listener>();
		undoStack = new Stack<Undoable>();
		redoStack = new Stack<Undoable>();
	}
	/**
	 * Passes a performed action onto a stack so that it may be accessed at a later time
	 * @param command Action performed by the user
	 */
	public void execute(Undoable command) {
		command.execute();
		undoStack.push(command);
		// Reset undo stack if execution causes game to end
		if(command instanceof NextCommand && !((NextCommand)command).getModel().getIsRunning()) undoStack.clear();
		// Reset redo stack on execution of new command
		redoStack.clear();
		notifyListeners();
	}
	/**
	 * Pops the latest performed action off of the stack
	 */
 	public void undo() {
		if (undoStack.isEmpty()) return;
		Undoable command = undoStack.pop();
		command.undo();
		redoStack.push(command);
		notifyListeners();
	}
	/**
	 * Re-does any amount of previously performed actions, until a user provides a new one
	 */
	public void redo() {
		if (redoStack.isEmpty()) return;		
		Undoable command = redoStack.pop();
		command.redo();
		undoStack.push(command);
		notifyListeners();
	}
	/**
	 * Empties the action stack
	 */
	public void clearUndoManager() {
		redoStack.clear();
		undoStack.clear();
		notifyListeners();
	}

	/**
	 * 
	 * @return True when there are actions that can be undone
	 */
 	public boolean isUndoAvailable() {
		return !undoStack.isEmpty();
	}
 	/**
 	 * 
 	 * @return True if there are actions in the redo stack
 	 */
	public boolean isRedoAvailable() {
		return !redoStack.isEmpty();
	}
	
	public void addActionListener(Listener listener) {
		listeners.add(listener);
		// Notify listeners of undo/redo state on subscription
		notifyListeners();
	}
	
	public void notifyListeners() {
		for(Listener listener : listeners) {
			listener.handleEvent(new Event(Action.UNDO));
			listener.handleEvent(new Event(Action.REDO));
		}
	}
	
 }
