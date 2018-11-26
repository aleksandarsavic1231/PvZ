import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class BombTest extends TestCase {
	
	private Bomb cherryBomb;

	@Before
	public void setUp() throws Exception {
		cherryBomb = new Bomb(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		cherryBomb = null;
	}
	
	@Test
	public void testDeployable() {
		// Test whether Bomb is deployable when game begins
		assertTrue(Bomb.isDeployable(0));
		
		// Test for unexpected result when Bomb is deployed after being spawned
		Bomb.setNextDeployable(0);
		assertFalse(Bomb.isDeployable(0));
		
		// Test whether Bomb is deployable after waiting cooldown period
		assertTrue(Bomb.isDeployable(Bomb.SPAWN_COOLDOWN));
		
		// Test reset deployable
		Bomb.resetNextDeployable();
		assertTrue(Bomb.isDeployable(0));
		
		// Test hard set of deployable
		Bomb.hardSetNextDeployable(5);
		assertEquals(Bomb.getNextDeployable(), 5);
		
		// Test for broken code and negative field
		Bomb.hardSetNextDeployable(-1);
		assertNotEquals(Bomb.getNextDeployable(), 5);
		assertEquals(Bomb.getNextDeployable(), -1);
	}
	
	@Test 
	public void testSetFireRate() {
		int n = 4;
		// Set fire rate and test can shoot
		cherryBomb.setFireRate(n);
		for(int i = 0; i < n; i++)  assertFalse(cherryBomb.canShoot());
		assertTrue(cherryBomb.canShoot()); // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(cherryBomb.canShoot()); 
	}
	
	@Test 
	public void testResetFireRate() {
		// Reset fire rate and test can shoot
		cherryBomb.resetFireRate();
		for(int i = 0; i < Bomb.DEONATION_TIME; i++) assertFalse(cherryBomb.canShoot());
		assertTrue(cherryBomb.canShoot());  // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(cherryBomb.canShoot()); 
	}

}
