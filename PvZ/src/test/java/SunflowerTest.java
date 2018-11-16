import org.junit.Test;

import junit.framework.TestCase;

public class SunflowerTest extends TestCase {

	@Test
	public void test() {
		// Test whether Sunflower is deployable when game begins
		assertTrue(Sunflower.isDeployable(0));
		
		// Test whether Sunflower is deployable after being spawned
		Sunflower.setNextDeployable(0);
		assertFalse(Sunflower.isDeployable(0));
		
		// Test whether Sunflower is deployable after waiting cooldown period
		assertTrue(Sunflower.isDeployable(Sunflower.SPAWN_COOLDOWN));
		
		// Test reset deployable
		Sunflower.resetNextDeployable();
		assertTrue(Sunflower.isDeployable(0));
		
	}
	

}
