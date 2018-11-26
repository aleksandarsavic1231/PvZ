import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class EventTest extends TestCase {
	
	Event event;
	
	@Before
	public void setUp() throws Exception {
		event = new Event(Action.GAME_OVER);
	}

	@After
	public void tearDown() throws Exception {
		event = null;
	}
	
	@Test 
	public void testGetType() {
		// Test for expected result 
		assertEquals(event.getType(), Action.GAME_OVER);
		
		// Test for broken code
		assertNotEquals(event.getType(), Action.ROUND_OVER);
	}

}
