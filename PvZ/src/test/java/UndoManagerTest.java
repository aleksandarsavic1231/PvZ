import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UndoManagerTest extends TestCase {

	private UndoManager undoManager;
	private Model model;
	
	@Before
	public void setUp() throws Exception {
		model = new Model();
		undoManager = new UndoManager();
	}
	
	@After
	public void tearDown() throws Exception {
		undoManager = null;
		model = null;
	}
	
	@Test
	public void testExecute() {
		NextCommand command = new NextCommand(model);
		undoManager.execute(command);
		assertTrue(undoManager.isUndoAvailable());
	}
	
	@Test
	public void testUndo() {
		NextCommand command = new NextCommand(model);
		undoManager.execute(command);
		undoManager.undo();
		assertFalse(undoManager.isUndoAvailable());
	}
	
	@Test
	public void testRedo() {
		NextCommand command = new NextCommand(model);
		undoManager.execute(command);
		undoManager.undo();
		assertTrue(undoManager.isRedoAvailable());
	}
	
	@Test
	public void testClear() {
		undoManager.clearUndoManager();
		assertFalse(undoManager.isRedoAvailable() && undoManager.isRedoAvailable());
	}
	
	@Test
	public void testIsUndoAvailable() {
		NextCommand command = new NextCommand(model);
		undoManager.execute(command);
		assertTrue(undoManager.isUndoAvailable());
	}
	
	@Test
	public void testIsRedoAvailable() {
		NextCommand command = new NextCommand(model);
		undoManager.execute(command);
		undoManager.undo();
		assertTrue(undoManager.isRedoAvailable());
		
	}
}
