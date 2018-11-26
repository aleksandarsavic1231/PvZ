import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import junit.framework.TestCase;

public class WalnutTest extends TestCase {
	
	@Test
	public void testDeployable() {
		// Test reset deployable
		Walnut.resetNextDeployable();
		assertTrue(Walnut.isDeployable(0));
		
		// Test whether Walnut is deployable after being spawned
		Walnut.setNextDeployable(0);
		assertFalse(Walnut.isDeployable(0));
		
		// Test whether Walnut is deployable after waiting cooldown period
		assertTrue(Walnut.isDeployable(Walnut.SPAWN_COOLDOWN));
		
		// Test hard set of deployable
		Walnut.hardSetNextDeployable(5);
		assertEquals(Walnut.getNextDeployable(), 5);
		
		// Test for broken code and negative field
		Walnut.hardSetNextDeployable(-1);
		assertNotEquals(Walnut.getNextDeployable(), 5);
		assertEquals(Walnut.getNextDeployable(), -1);
	}
	
}
