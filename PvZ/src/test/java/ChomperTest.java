import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ChomperTest extends TestCase {
	
	private Chomper chomper;

	@Before
	public void setUp() throws Exception {
		chomper = new Chomper(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		chomper = null;
	}
	
	@Test
	public void testDeployable() {
		// Test for unexpected result when Bomb is deployed after being spawned
		Chomper.setNextDeployable(0);
		assertFalse(Chomper.isDeployable(0));
				
		// Test whether Bomb is deployable after waiting cooldown period
		assertTrue(Chomper.isDeployable(Chomper.SPAWN_COOLDOWN));
				
		// Test reset deployable
		Chomper.resetNextDeployable();
		assertTrue(Chomper.isDeployable(0));
				
		// Test hard set of deployable
		Chomper.hardSetNextDeployable(5);
		assertEquals(Chomper.getNextDeployable(), 5);
				
		// Test for broken code and negative field
		Chomper.hardSetNextDeployable(-1);
		assertNotEquals(Chomper.getNextDeployable(), 5);
		assertEquals(Chomper.getNextDeployable(), -1);
	}
	
	@Test 
	public void testSetFireRate() {
		int n = 10;
		// Set fire rate and test can shoot
		chomper.setFireRate(n);
		for(int i = 0; i < n; i++)  assertFalse(chomper.canShoot());
		assertTrue(chomper.canShoot()); // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(chomper.canShoot()); 
	}
	
	@Test 
	public void testResetFireRate() {
		// Reset fire rate and test can shoot
		chomper.resetFireRate();
		for(int i = 0; i < Chomper.RECHARGE_TIME; i++) assertFalse(chomper.canShoot());
		assertTrue(chomper.canShoot());  // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(chomper.canShoot()); 
	}

}

