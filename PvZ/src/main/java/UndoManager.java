import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The UndoManager manages the order of commands and provides and interface to perform execution/redo/undo.
 * 
 * @author kylehorne
 * @version 25 Nov 18
 */
public class UndoManager implements XMLEncoderDecoder {
	
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
		if(!(Controller.getInstance().getModel().getIsRunning())) undoStack.clear();
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
	
	public Stack<Undoable> getUndoStack() { return undoStack; }
	
	public Stack<Undoable> getRedoStack() { return redoStack; }
	
	public void setUndoStack(Stack<Undoable> undoStack) { this.undoStack = undoStack; }
	
	public void setRedoStack(Stack<Undoable> redoStack) { this.redoStack = redoStack; }

	@Override
	public void save() 
	throws IOException {
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><UndoManager>");
		buffer.append("<UndoStack>");
		for(Undoable undoItem : undoStack) buffer.append(undoItem.toXMLString());
		buffer.append("</UndoStack>");
		buffer.append("<RedoStack>");
		for(Undoable redoItem : redoStack) buffer.append(redoItem.toXMLString());
		buffer.append("</RedoStack>");
		buffer.append("</UndoManager>");
		BufferedWriter stream = new BufferedWriter(new FileWriter("./" + getClass().getName() + ".xml"));
		stream.write(buffer.toString());
		stream.close();
	}

	@Override
	public void load() 
	throws 
	IOException, 
	SAXException, 
	ParserConfigurationException, 
	UnimplementedPlant, 
	UnimplementedEntity, 
	UnimplementedLevel, 
	UnimplementedCommand {
		File file = new File("./" + getClass().getName() + ".xml");
		if (!file.exists()) return;
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream("./" + getClass().getName() + ".xml"));
		NodeList undoList = document.getElementsByTagName("UndoStack").item(0).getChildNodes();
		Stack<Undoable> tempUndoStack = new Stack<Undoable>();
		for(int i = 0; i < undoList.getLength(); i++) tempUndoStack.add(CommandFactory.create(undoList.item(i)));
		setUndoStack(tempUndoStack);
		NodeList redoList = document.getElementsByTagName("RedoStack").item(0).getChildNodes();
		Stack<Undoable> tempRedoStack = new Stack<Undoable>();
		for(int i = 0; i < redoList.getLength(); i++) tempRedoStack.add(CommandFactory.create(redoList.item(i)));
		setRedoStack(tempRedoStack);
		notifyListeners();
	}
	
 }
