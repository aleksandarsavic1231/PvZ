import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class CommandTest extends TestCase {
	
	private Command command;
	
	private UndoManager undoManager;

	@Before
	public void setUp() throws Exception {
		undoManager = new UndoManager();
		command = new Command(new Model(), undoManager);
	}

	@After
	public void tearDown() throws Exception {
		command = null;
		undoManager = null;
	}
	
	@Test 
	public void testGetUndoManager() {
		// Test for expected result 
		assertEquals(command.getUndoManager(), undoManager);
		
		// Test for broken code
		undoManager = new UndoManager();
		assertNotEquals(command.getUndoManager(), undoManager);
	}

}
