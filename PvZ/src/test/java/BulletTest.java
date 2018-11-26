import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class BulletTest extends TestCase {
	
	private Bullet bullet;

	@Before
	public void setUp() throws Exception {
		bullet = new Bullet(new Point(0, 0), 5);
	}

	@After
	public void tearDown() throws Exception {
		bullet = null;
	}

	@Test
	public void testGetDamage() {
		// Test for expected result
		assertEquals(bullet.getDamage(), 5);
		
		// Test for broken code
		assertNotEquals(bullet.getDamage(), -1);
	}
	
	@Test
	public void testUpdatePosition() {
		// Test update position when unlocked
		bullet.unlock();
		bullet.updatePosition();
		assertEquals(bullet.getPosition(), new Point(1, 0));
		
		// Test for broken code
		// Update position when locked
		bullet.updatePosition();
		assertNotEquals(bullet.getPosition(), new Point(2, 0));
	}
	
	@Test
	public void testNextPosition() {
		// Test for expected result
		assertEquals(bullet.nextPosition(), new Point(1, 0));
		
		// Test for broken code 
		assertNotEquals(bullet.nextPosition(), new Point(0, 0));
	}

}
