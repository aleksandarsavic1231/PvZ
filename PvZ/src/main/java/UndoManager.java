import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;

public class UndoManager {

	private Stack<Executable> undoStack = new Stack<Executable>();
	private Stack<Executable> redoStack = new Stack<Executable>();
	
	private class OldState {
		private boolean undoAvailable = isUndoAvailable();
		private boolean redoAvailable = isRedoAvailable();
	}
	
	public void execute(Executable command) {
		try {
			OldState oldState = new OldState();
			command.execute();
			if (isUndoAvailable() && undoStack.peek().isCollapsible(command)) {
				undoStack.peek().collapse(command);
			} else {
				undoStack.push(command);
			}
			redoStack.clear();
			// fireChanges(oldState);
		} catch (IllegalStateException e) {
			// report and log
		}
	}

	public void undo() {
		if (!undoStack.isEmpty()) {
			try {
				OldState oldState = new OldState();
				Executable command = undoStack.pop();
				command.undo();
				redoStack.push(command);
				System.out.println("Undo stack size: "+ undoStack.size());
				// fireChanges(oldState);
			} catch (IllegalStateException e) {
				// report and log
			}
		}
	}
	
	public void redo() {
		if (!redoStack.isEmpty()) {
			try {
				OldState oldState = new OldState();
				Executable command = redoStack.pop();
				command.redo();
				undoStack.push(command);
				// fireChanges(oldState);
			} catch (IllegalStateException e) {
				// report and log
			}
		}
	}
	

	public boolean isUndoAvailable() {
		return !undoStack.isEmpty();
	}
	public boolean isRedoAvailable() {
		return !redoStack.isEmpty();
	}
	
}