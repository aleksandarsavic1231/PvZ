import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class EntityEventTest extends TestCase {
	
	EntityEvent event;
	
	PeaShooter peaShooter;
	
	@Before
	public void setUp() throws Exception {
		peaShooter = new PeaShooter(new Point(0, 0));
		event = new EntityEvent(Action.SPAWN_ENTITY, peaShooter);
	}

	@After
	public void tearDown() throws Exception {
		event = null;
	}
	
	@Test 
	public void testGetType() {
		// Test for expected result 
		assertEquals(event.getEntity(), peaShooter);
		
		// Test for broken code
		peaShooter = new PeaShooter(new Point(0, 0));
		assertNotEquals(event.getType(), peaShooter);
	}

}