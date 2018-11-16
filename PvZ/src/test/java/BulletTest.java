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
		assertEquals(bullet.getDamage(), 5);
	}
	
	@Test
	public void testUpdatePosition() {
		// Test update position when unlocked
		bullet.unlock();
		bullet.updatePosition();
		assertEquals(bullet.getPosition(), new Point(1, 0));
		
		// Test update position when locked
		bullet.updatePosition();
		assertEquals(bullet.getPosition(), new Point(1, 0));
	}
	
	@Test
	public void testNextPosition() {
		assertEquals(bullet.nextPosition(), new Point(1, 0));
	}

}
