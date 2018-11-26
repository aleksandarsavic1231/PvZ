import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ControllerTest extends TestCase {
	
	private Controller controller;
	
	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		controller = new Controller(model);
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
	}
	
	@Test 
	public void testGetUndoManager() {
		// Test for expected result 
		assertEquals(controller.getModel(), model);
		
		// Test for broken code
		model = new Model();
		assertNotEquals(controller.getModel(), model);
	}

}
