import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class RepeaterTest extends TestCase {
	
	private Repeater repeater;

	@Before
	public void setUp() throws Exception {
		repeater = new Repeater(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		repeater = null;
	}
	
	@Test
	public void testDeployable() {		
		// Test reset deployable
		Repeater.resetNextDeployable();
		assertTrue(Repeater.isDeployable(0));
		
		// Test whether Repeater is deployable after being spawned
		Repeater.setNextDeployable(0);
		assertFalse(Repeater.isDeployable(0));
		
		// Test whether Repeater is deployable after waiting cool down period
		assertTrue(Repeater.isDeployable(Repeater.SPAWN_COOLDOWN));
		
		// Test hard set of deployable
		Repeater.hardSetNextDeployable(5);
		assertEquals(Repeater.getNextDeployable(), 5);
		
		// Test for broken code and negative field
		Repeater.hardSetNextDeployable(-1);
		assertNotEquals(Repeater.getNextDeployable(), 5);
		assertEquals(Repeater.getNextDeployable(), -1);
	}
	
	@Test 
	public void testSetFireRate() {
		int n = 4;
		// Set fire rate and test can shoot
		repeater.setFireRate(n);
		for(int i = 0; i < n; i++)  assertFalse(repeater.canShoot());
		assertTrue(repeater.canShoot()); // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(repeater.canShoot()); 
	}
	
	@Test 
	public void testResetFireRate() {
		// Reset fire rate and test can shoot
		repeater.resetFireRate();
		for(int i = 0; i < Repeater.RECHARGE_TIME; i++) assertFalse(repeater.canShoot());
		assertTrue(repeater.canShoot());  // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(repeater.canShoot()); 
	}

}
