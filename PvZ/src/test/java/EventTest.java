import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class EventTest extends TestCase  {
	
	private Event event;

	@Before
	public void setUp() throws Exception {
		event = new Event(Action.TILE_CLICKED, new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		event = null;
	}
	
	@Test
	public void testGetType() {
		assertEquals(event.getType(), Action.TILE_CLICKED);
	}
	
	@Test
	public void testGetPayload() {
		assertEquals(event.getPayload(), new Point(0, 0));
	}

}
