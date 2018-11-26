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
		
	}
	
	@Test
	public void testUndo() {
		
	}
	
	@Test
	public void TestRedo() {
		
	}
	
	@Test
	public void TestClear() {
		
	}
	
	@Test
	public void testIsUndoAvailable() {
		
	}
	
	@Test
	public void testIsRedoAvailable() {
		
	}
}
