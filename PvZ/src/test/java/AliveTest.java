import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class AliveTest extends TestCase {
	
	private Alive alive;

	@Before
	public void setUp() throws Exception {
		alive = new Alive(new Point(0, 0), 100);
	}

	@After
	public void tearDown() throws Exception {
		alive = null;
	}
	
	@Test
	public void testGetHealth() {
		// Test for expected result
		assertEquals(alive.getHealth(), 100);
		
		// Test for broken code
		assertNotEquals(alive.getHealth(), -1);
	}
	
	@Test
	public void testTakeDamage() {
		alive.takeDamage(20);
		// Test for expected result
		assertEquals(alive.getHealth(), 80);
		
		// Test for broken code
		assertNotEquals(alive.getHealth(), 20);
		
		// Test for negative damage
		alive.takeDamage(-5);
		assertEquals(alive.getHealth(), 85);
	}
	
	@Test
	public void testSelfDestruct() {
		alive.selfDestruct();
		
		// Test for expected result
		assertEquals(alive.getHealth(), 0);
		
		// Test for broken code
		assertNotEquals(alive.getHealth(), -1);
	}

}
