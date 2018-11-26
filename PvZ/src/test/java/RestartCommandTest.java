import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class RestartCommandTest extends TestCase {
	
	private RestartCommand restartCommand;
	
	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		restartCommand = new RestartCommand(model, new UndoManager());
	}

	@After
	public void tearDown() throws Exception {
		restartCommand = null;
		model = null;
	}
	
	@Test
	public void testRestartCommand() {
		// Modify state of Model
		model.addEntity(new RegularZombie(new Point(0, 0)));
		model.updateIsRunning();
		model.setBalance(Model.INITIAL_BALANCE * 10);
		model.incrementGameCounter();
		model.setTogglePlant(Plant.PEA_SHOOTER);
		PeaShooter.setNextDeployable(10);
		// Execute restart
		// Model should reset to default state
		restartCommand.execute();
		
		// Check for successful reset of Model state
		assertEquals(model.getGameCounter(), 0);
		assertEquals(model.getBalance(), Model.INITIAL_BALANCE);
		assertTrue(model.getIsRunning());
		assertNull(model.getTogglePlant());
		
		// Check for successfully spawned Zombies
		int nRegularZombies = 0;
		int nPylonZombies = 0;
		for(Entity entity: model.getEntities()) {
			if (entity instanceof RegularZombie) nRegularZombies++;
			else if (entity instanceof PylonZombie) nPylonZombies++;
		}
		assertEquals(Model.N_REGULAR_ZOMBIES, nRegularZombies);
		assertEquals(Model.N_PYLON_ZOMBIES, nPylonZombies);
	}
}