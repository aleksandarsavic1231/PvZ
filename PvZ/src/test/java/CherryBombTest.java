import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class CherryBombTest extends TestCase {
	
	private CherryBomb cherryBomb;

	@Before
	public void setUp() throws Exception {
		cherryBomb = new CherryBomb(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		cherryBomb = null;
	}
	
	@Test
	public void testDeployable() {
		// Test for unexpected result when Bomb is deployed after being spawned
		CherryBomb.setNextDeployable(0);
		assertFalse(CherryBomb.isDeployable(0));
		
		// Test whether Bomb is deployable after waiting cooldown period
		assertTrue(CherryBomb.isDeployable(CherryBomb.SPAWN_COOLDOWN));
		
		// Test reset deployable
		CherryBomb.resetNextDeployable();
		assertTrue(CherryBomb.isDeployable(0));
		
		// Test hard set of deployable
		CherryBomb.hardSetNextDeployable(5);
		assertEquals(CherryBomb.getNextDeployable(), 5);
		
		// Test for broken code and negative field
		CherryBomb.hardSetNextDeployable(-1);
		assertNotEquals(CherryBomb.getNextDeployable(), 5);
		assertEquals(CherryBomb.getNextDeployable(), -1);
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
		for(int i = 0; i < CherryBomb.DEONATION_TIME; i++) assertFalse(cherryBomb.canShoot());
		assertTrue(cherryBomb.canShoot());  // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(cherryBomb.canShoot()); 
	}

}
