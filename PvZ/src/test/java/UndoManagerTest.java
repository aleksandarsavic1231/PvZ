import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class UndoManagerTest extends TestCase {

	private UndoManager undoManager;
	
	@Before
	public void setUp() throws Exception {
		undoManager = new UndoManager();
	}
	
	@After
	public void tearDown() throws Exception {
		undoManager = null;
	}
	
	@Test
	public void testExecute() {
		Undoable command = null;
		undoManager.execute(command);
		assertTrue(undoManager.isUndoAvailable());
	}
	
	@Test
	public void testUndo() {
		
	}
	
	@Test
	public void testRedo() {
		Undoable command = null;
		undoManager.execute(command);
		undoManager.undo();
	}
	
	@Test
	public void testClear() {
		undoManager.clearUndoManager();
		assertFalse(undoManager.isRedoAvailable() && undoManager.isRedoAvailable());
	}
	
	@Test
	public void testIsUndoAvailable() {
		Undoable command = null;
		undoManager.execute(command);
		assertTrue(undoManager.isUndoAvailable());
	}
	
	@Test
	public void testIsRedoAvailable() {
		
	}
}
