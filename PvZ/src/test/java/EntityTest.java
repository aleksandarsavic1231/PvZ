import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class EntityTest extends TestCase {
	
	private Entity entity;

	@Before
	public void setUp() throws Exception {
		entity = new Entity(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		entity = null;
	}

	@Test
	public void testGetPosition() {
		assertEquals(entity.getPosition(), new Point(0, 0));
	}
	
	@Test
	public void testSetPosition() {
		entity.setPosition(new Point(1, 0));
		assertEquals(entity.getPosition(), new Point(1, 0));
		
		// Test set of negative position
		entity.setPosition(new Point(-1, -1));
		assertEquals(entity.getPosition(), new Point(-1, -1));
	}

}
