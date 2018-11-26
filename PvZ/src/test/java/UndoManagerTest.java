import java.awt.Point;

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
		TileCommand tileAction = new TileCommand(new Model(), new Point(0, 0));
		undoManager.execute(tileAction);
		
		assertTrue(undoManager.isUndoAvailable());
		assertFalse(undoManager.isRedoAvailable());
	}
	
	@Test
	public void testUndo() { //TODO }
	
	@Test
	public void testRedo() { //TODO }
	
	@Test
	public void testClear() {
		undoManager.clearUndoManager();
		assertFalse(undoManager.isUndoAvailable());
		assertFalse(undoManager.isRedoAvailable());
	}

}
