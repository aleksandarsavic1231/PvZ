import java.util.Stack;

public class UndoManager {

 	private Stack<Undoable> undoStack;
	private Stack<Undoable> redoStack;
	
	public UndoManager() {
		undoStack = new Stack<Undoable>();
		redoStack = new Stack<Undoable>();
	}
	
	public void execute(Undoable command) {
		command.execute();
		undoStack.push(command);
		// Reset redo stack on execution of new command
		redoStack.clear();
	}
	
 	public void undo() {
		if (undoStack.isEmpty()) return;

		Undoable command = undoStack.pop();
		command.undo();
		redoStack.push(command);
	}
	
	public void redo() {
		if (redoStack.isEmpty()) return;
		
		Undoable command = redoStack.pop();
		command.redo();
		undoStack.push(command);
	}

 	public boolean isUndoAvailable() {
		return !undoStack.isEmpty();
	}
 	
	public boolean isRedoAvailable() {
		return !redoStack.isEmpty();
	}
	
 }
