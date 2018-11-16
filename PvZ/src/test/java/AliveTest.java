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
		assertEquals(alive.getHealth(), 100);
	}
	
	@Test
	public void testTakeDamage() {
		alive.takeDamage(20);
		assertEquals(alive.getHealth(), 80);
	}

}
