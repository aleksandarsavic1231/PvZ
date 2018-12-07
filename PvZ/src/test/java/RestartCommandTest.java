import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class RestartCommandTest extends TestCase {
	
	private RestartCommand restartCommand;
	
	@Before
	public void setUp() throws Exception {
		restartCommand = new RestartCommand(new UndoManager());
	}

	@After
	public void tearDown() throws Exception {
		restartCommand = null;
	}
	
	@Test
	public void testRestartCommand() {
		Model model = Controller.getInstance().getModel();
		// Modify state of Model
		model.addEntity(new RegularZombie(new Point(0, 0)));
		model.setBalance(Model.INITIAL_BALANCE * 10);
		model.incrementGameCounter();
		model.setToggledPlant(Plant.PEA_SHOOTER);
		PeaShooter.setNextDeployable(10);
		// Execute restart
		// Model should reset to default state
		restartCommand.execute();
		
		// Check for successful reset of Model state
		assertEquals(model.getGameCounter(), 0);
		assertEquals(model.getBalance(), Model.INITIAL_BALANCE);
		assertTrue(model.getIsRunning());
		assertNull(model.getToggledPlant());
		
		// Check for successfully spawned Zombies
		int nRegularZombies = 0;
		int nPylonZombies = 0;
		for(Entity entity: model.getEntities()) {
			if (entity instanceof RegularZombie) nRegularZombies++;
			else if (entity instanceof PylonZombie) nPylonZombies++;
		}
		assertEquals(Level.ONE.getNRegularZombies(), nRegularZombies);
		assertEquals(Level.ONE.getNPylonZombies(), nPylonZombies);
	}
}