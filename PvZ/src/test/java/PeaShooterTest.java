import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class PeaShooterTest extends TestCase {
	
	private PeaShooter peaShooter;

	@Before
	public void setUp() throws Exception {
		peaShooter = new PeaShooter(new Point(0, 0));
	}

	@After
	public void tearDown() throws Exception {
		peaShooter = null;
	}
	
	@Test
	public void testDeployable() {
		// Test whether PeaShooter is deployable when game begins
		assertTrue(PeaShooter.isDeployable(0));
		
		// Test whether PeaShooter is deployable after being spawned
		PeaShooter.setNextDeployable(0);
		assertFalse(PeaShooter.isDeployable(0));
		
		// Test whether PeaShooter is deployable after waiting cooldown period
		assertTrue(PeaShooter.isDeployable(PeaShooter.SPAWN_COOLDOWN));
		
		// Test reset deployable
		PeaShooter.resetNextDeployable();
		assertTrue(PeaShooter.isDeployable(0));
	}
	
	@Test 
	public void testSetFireRate() {
		int n = 4;
		// Set fire rate and test can shoot
		peaShooter.setFireRate(n);
		for(int i = 0; i < n - 1; i++)  assertFalse(peaShooter.canShoot());
		assertTrue(peaShooter.canShoot()); // Can now shoot
		//assertFalse(peaShooter.canShoot());
	}
	
	@Test 
	public void testResetFireRate() {
		// Reset fire rate and test can shoot
		peaShooter.resetFireRate();
		for(int i = 0; i < PeaShooter.RECHARGE_TIME - 1; i++) assertFalse(peaShooter.canShoot());
		assertTrue(peaShooter.canShoot()); 
		assertFalse(peaShooter.canShoot());
	}

}
