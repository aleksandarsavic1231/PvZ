import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ZombieTest extends TestCase  {
	
	private Zombie zombie;

	@Before
	public void setUp() throws Exception {
		this.zombie = new RegularZombie(new Point(1, 0));
	}

	@After
	public void tearDown() throws Exception {
		this.zombie = null;
	}

	@Test
	public void testUpdatePosition() {
		// Test update position when unlocked
		zombie.unlock();
		zombie.updatePosition();
		assertEquals(zombie.getPosition(), new Point(0, 0));
		
		// Test for broken code
		// Update position when locked
		zombie.updatePosition();
		assertNotEquals(zombie.getPosition(), new Point(-1, 0));
	}
	
	@Test
	public void testNextPosition() {
		// Test for expected result
		assertEquals(zombie.nextPosition(), new Point(0, 0));
		
		// Test for broken code 
		assertNotEquals(zombie.nextPosition(), new Point(-1, 0));
	}

}
