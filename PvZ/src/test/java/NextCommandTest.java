import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class NextCommandTest extends TestCase {
	
	private NextCommand nextCommand;
	
	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		nextCommand = new NextCommand(model);
	}

	@After
	public void tearDown() throws Exception {
		nextCommand = null;
		model = null;
	}
	
	@Test
	public void testNextCommand() {
		Zombie zombie = new RegularZombie(new Point(3, 0));
		PeaShooter peaShooter = new PeaShooter(new Point(0, 0));
		model.addEntity(zombie);
		model.addEntity(peaShooter);
		
		nextCommand.execute();
		// Test Bullet spawn 
		boolean bulletSpawned = false;
		for(Entity entity: model.getEntities()) {
			if (entity instanceof Bullet) bulletSpawned = true;
		}
		assertTrue(bulletSpawned);
		// Game counter should increase
		assertEquals(model.getGameCounter(), 1);
		// Test game is still running
		assertTrue(model.getIsRunning());
		
		nextCommand.undo();
		// Test Bullet spawn 
		bulletSpawned = false;
		for(Entity entity: model.getEntities()) {
			if (entity instanceof Bullet) bulletSpawned = true;
		}
		assertFalse(bulletSpawned);
		// Game counter should increase
		assertEquals(model.getGameCounter(), 0);
		// Test game is still running
		assertTrue(model.getIsRunning());

		nextCommand.redo();
		// Test Bullet spawn 
		bulletSpawned = false;
		for(Entity entity: model.getEntities()) {
			if (entity instanceof Bullet) bulletSpawned = true;
		}
		assertTrue(bulletSpawned);
		// Game counter should increase
		assertEquals(model.getGameCounter(), 1);
		// Test game is still running
		assertTrue(model.getIsRunning());
	}

}
