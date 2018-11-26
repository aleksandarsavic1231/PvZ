import java.util.LinkedList;
import java.util.Stack;

public class UndoManager {
	
	private LinkedList<Listener> listeners;

 	private Stack<Undoable> undoStack;
 	
	private Stack<Undoable> redoStack;
	
	public UndoManager() {
		listeners = new LinkedList<Listener>();
		undoStack = new Stack<Undoable>();
		redoStack = new Stack<Undoable>();
	}
	
	public void execute(Undoable command) {
		command.execute();
		undoStack.push(command);
		// Reset undo stack if execution causes game to end
		if(command instanceof NextCommand && !((NextCommand)command).getModel().getIsRunning()) undoStack.clear();
		// Reset redo stack on execution of new command
		redoStack.clear();
		notifyListeners();
	}
	
 	public void undo() {
		if (undoStack.isEmpty()) return;
		Undoable command = undoStack.pop();
		command.undo();
		redoStack.push(command);
		notifyListeners();
	}
	
	public void redo() {
		if (redoStack.isEmpty()) return;		
		Undoable command = redoStack.pop();
		command.redo();
		undoStack.push(command);
		notifyListeners();
	}
	
	public void clearUndoManager() {
		redoStack.clear();
		undoStack.clear();
		notifyListeners();
	}

 	public boolean isUndoAvailable() {
		return !undoStack.isEmpty();
	}
 	
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
