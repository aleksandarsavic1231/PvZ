import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ZombieTest extends TestCase  {
	
	private Zombie zombie;

	@Before
	public void setUp() throws Exception {
		this.zombie = new Zombie(new Point(0, 0));
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
		assertEquals(zombie.getPosition(), new Point(-1, 0));
		
		// Test update position when locked
		zombie.updatePosition();
		assertEquals(zombie.getPosition(), new Point(-1, 0));
	}
	
	@Test
	public void testNextPosition() {
		assertEquals(zombie.nextPosition(), new Point(-1, 0));
	}

}
