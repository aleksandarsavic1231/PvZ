import java.util.LinkedList;
import java.util.Stack;

/**
 * The UndoManager manages the order of commands and provides and interface to perform execution/redo/undo.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class UndoManager {
	
	/**
	 * The listeners to this UndoManager Object.
	 */
	private LinkedList<Listener> listeners;

	/**
	 * The undoStack to this UndoManager Object.
	 */
 	private Stack<Undoable> undoStack;
 	
	/**
	 * The redoStack to this UndoManager Object.
	 */
	private Stack<Undoable> redoStack;
	
	/**
	 * Constructor.
	 */
	public UndoManager() {
		listeners = new LinkedList<Listener>();
		undoStack = new Stack<Undoable>();
		redoStack = new Stack<Undoable>();
	}
	
	/**
	 * Execute the command.
	 * 
	 * @param command The command to execute.
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
	 * Undo the command.
	 */
 	public void undo() {
		if (undoStack.isEmpty()) return;
		Undoable command = undoStack.pop();
		command.undo();
		redoStack.push(command);
		notifyListeners();
	}
	
 	/**
 	 * Redo the command.
 	 */
	public void redo() {
		if (redoStack.isEmpty()) return;		
		Undoable command = redoStack.pop();
		command.redo();
		undoStack.push(command);
		notifyListeners();
	}
	
	/**
	 * Clear the undo and redo stack.
	 */
	public void clearUndoManager() {
		redoStack.clear();
		undoStack.clear();
		notifyListeners();
	}

	/**
	 * Is a undo available.
	 * 
	 * @return boolean True if an undo is available.
	 */
 	public boolean isUndoAvailable() {
		return !undoStack.isEmpty();
	}
 	
 	/**
 	 * Is a redo available.
 	 * 
 	 * @return boolean True if a redo is available.
 	 */
	public boolean isRedoAvailable() {
		return !redoStack.isEmpty();
	}
	
	/**
	 * Add a action listener to this UndoManager Object.
	 * 
	 * @param listener The listener to add.
	 */
	public void addActionListener(Listener listener) {
		listeners.add(listener);
		// Notify listeners of undo/redo state on subscription
		notifyListeners();
	}
	
	/**
	 * Notify all listener of a Event.
	 */
	public void notifyListeners() {
		for(Listener listener : listeners) {
			listener.handleEvent(new Event(Action.UNDO));
			listener.handleEvent(new Event(Action.REDO));
		}
	}
	
 }
