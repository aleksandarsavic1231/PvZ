import static org.junit.Assert.assertNotEquals;

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
		// Test for expected result
		assertEquals(entity.getPosition(), new Point(0, 0));
		
		// Test for broken code
		assertNotEquals(entity.getPosition(), new Point(-1, -1));
	}
	
	@Test
	public void testSetPosition() {
		// Test for expected result
		entity.setPosition(new Point(1, 0));
		assertEquals(entity.getPosition(), new Point(1, 0));
		
		// Test set of negative position
		entity.setPosition(new Point(-1, -1));
		assertEquals(entity.getPosition(), new Point(-1, -1));
	}
	
	@Test
	public void testClone() throws UnimplementedEntity {
		// Test for expected result
		entity = new PeaShooter(new Point(0, 0));
		assertNotEquals(Entity.clone(entity), entity);
		
		// Test for null
		assertEquals(Entity.clone(null), null);
	}

}
