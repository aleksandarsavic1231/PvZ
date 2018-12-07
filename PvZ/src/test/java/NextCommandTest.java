import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class NextCommandTest extends TestCase {
	
	private NextCommand nextCommand;

	@Before
	public void setUp() throws Exception {
		nextCommand = new NextCommand();
	}

	@After
	public void tearDown() throws Exception {
		nextCommand = null;
	}
	
	@Test
	public void testNextCommand() {
		Model model = Controller.getInstance().getModel();
		Zombie zombie = new RegularZombie(new Point(5, 0));
		PeaShooter peaShooter = new PeaShooter(new Point(0, 0));
		model.addEntity(zombie);
		model.addEntity(peaShooter);
		
		nextCommand.execute();
		boolean bulletSpawned = false; // Test Bullet spawn 
		// Test correct location of PeaShooter
		boolean isCorrectPeaShooterLocation = false;  
		// Test correct location of PeaShooter
		boolean isCorrectZombieLocation = false; 
		for(Entity entity: model.getEntities()) {
			Point location = entity.getPosition();
			if (entity instanceof Bullet && location.x == 1 && location.y == 0) bulletSpawned = true;
			if (entity instanceof PeaShooter && location.x == 0 && location.y == 0) isCorrectPeaShooterLocation = true;
			if (entity instanceof Zombie && location.x == 4 && location.y == 0) isCorrectZombieLocation = true;
		}
		assertTrue(bulletSpawned);
		assertTrue(isCorrectPeaShooterLocation);
		assertTrue(isCorrectZombieLocation);
		// Game counter should increase
		assertEquals(model.getGameCounter(), 1);
		// Test game is still running
		assertTrue(model.getIsRunning());
		// Asert Zombie and PeaShooter health is default
		assertEquals(zombie.getHealth(), RegularZombie.INITIAL_HEALTH);
		assertEquals(peaShooter.getHealth(), PeaShooter.INITIAL_HEALTH);

		nextCommand.undo();
		// Test undo of Bullet spawn 
		boolean bulletRemoved = true;
		// Test correct location of PeaShooter
		isCorrectPeaShooterLocation = false;  
		// Test correct location of PeaShooter
		isCorrectZombieLocation = false; 
		for(Entity entity: model.getEntities()) {
			Point location = entity.getPosition();
			if (entity instanceof Bullet) bulletRemoved = false;
			if (entity instanceof PeaShooter && location.x == 0 && location.y == 0) isCorrectPeaShooterLocation = true;
			if (entity instanceof Zombie && location.x == 5 && location.y == 0) isCorrectZombieLocation = true;
		}
		assertTrue(bulletRemoved);
		assertTrue(isCorrectPeaShooterLocation);
		assertTrue(isCorrectZombieLocation);
		// Game counter should decrease
		assertEquals(model.getGameCounter(), 0);
		// Test game is still running
		assertTrue(model.getIsRunning());
		// Asert Zombie and PeaShooter health is default
		assertEquals(zombie.getHealth(), RegularZombie.INITIAL_HEALTH);
		assertEquals(peaShooter.getHealth(), PeaShooter.INITIAL_HEALTH);

		nextCommand.redo();
		// Test redo of Bullet spawn 
		bulletSpawned = false;
		// Test correct location of PeaShooter
		isCorrectPeaShooterLocation = false;  
		// Test correct location of PeaShooter
		isCorrectZombieLocation = false; 
		for(Entity entity: model.getEntities()) {
			Point location = entity.getPosition();
			if (entity instanceof Bullet && location.x == 1 && location.y == 0) bulletSpawned = true;
			if (entity instanceof PeaShooter && location.x == 0 && location.y == 0) isCorrectPeaShooterLocation = true;
			if (entity instanceof Zombie && location.x == 4 && location.y == 0) isCorrectZombieLocation = true;
		}
		assertTrue(bulletSpawned);
		assertTrue(isCorrectPeaShooterLocation);
		assertTrue(isCorrectZombieLocation);
		// Game counter should increase
		assertEquals(model.getGameCounter(), 1);
		// Test game is still running
		assertTrue(model.getIsRunning());
	}

}
