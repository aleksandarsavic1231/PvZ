import org.junit.Test;

import junit.framework.TestCase;

public class PeaShooterTest extends TestCase {
	
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

}
