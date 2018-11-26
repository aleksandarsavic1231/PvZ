import static org.junit.Assert.assertNotEquals;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class SunflowerTest extends TestCase {
	
	private Sunflower sunflower;

	@Before
	public void setUp() throws Exception {
		sunflower = new Sunflower(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		sunflower = null;
	}

	@Test
	public void testDeployable() {
		// Test reset deployable
		Sunflower.resetNextDeployable();
		assertTrue(Sunflower.isDeployable(0));
		
		// Test whether Sunflower is deployable after being spawned
		Sunflower.setNextDeployable(0);
		assertFalse(Sunflower.isDeployable(0));
		
		// Test whether Sunflower is deployable after waiting cooldown period
		assertTrue(Sunflower.isDeployable(Sunflower.SPAWN_COOLDOWN));
				
		// Test hard set of deployable
		Sunflower.hardSetNextDeployable(5);
		assertEquals(Sunflower.getNextDeployable(), 5);
				
		// Test for broken code and negative field
		Sunflower.hardSetNextDeployable(-1);
		assertNotEquals(Sunflower.getNextDeployable(), 5);
		assertEquals(Sunflower.getNextDeployable(), -1);
	}
	
	@Test 
	public void testSetFireRate() {
		int n = 4;
		// Set fire rate and test can shoot
		sunflower.setFireRate(n);
		for(int i = 0; i < n; i++)  assertFalse(sunflower.canShoot());
		assertTrue(sunflower.canShoot()); // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(sunflower.canShoot()); 
	}
	
	@Test 
	public void testResetFireRate() {
		// Reset fire rate and test can shoot
		sunflower.resetFireRate();
		for(int i = 0; i < Sunflower.RECHARGE_TIME; i++) assertFalse(sunflower.canShoot());
		assertTrue(sunflower.canShoot());  // Can now shoot
		
		// Test for broken code (must wait cool down period)
		assertFalse(sunflower.canShoot()); 
	}

}
