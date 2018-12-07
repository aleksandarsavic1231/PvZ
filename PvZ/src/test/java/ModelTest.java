import static org.junit.Assert.assertNotEquals;

import java.awt.Point;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ModelTest extends TestCase {
	
	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		model.clearEntities();
	}

	@After
	public void tearDown() throws Exception {
		model = null;
	}
	
	@Test
	public void testIsCollision() {
		// Test collision between Zombie and Bullet when sharing the same location 
		Bullet bullet = new Bullet(new Point(1, 0), 5);
		Zombie zombie = new RegularZombie(new Point(1, 0));
		model.addEntity(zombie);		
		assertTrue(model.isCollision(bullet));
		
		// Test that Zombie takes damage on collision with Bullet
		assertEquals(zombie.getHealth(), RegularZombie.INITIAL_HEALTH - 5);
		
		// Test collision between Zombie and Bullet when spaced one apart in domain 
		bullet.setPosition(new Point(0, 0));
		assertTrue(model.isCollision(bullet));
		
		// Test broken code 
		// When Bullet is in front of Zombie
		bullet.setPosition(new Point(2, 0));
		assertFalse(model.isCollision(bullet));
		
		// Test collision in negative domain and range
		model.clearEntities();
		zombie = new RegularZombie(new Point(-1, -1));
		model.addEntity(zombie);
		bullet.setPosition(new Point(-1, -1));
		assertTrue(model.isCollision(bullet));
		
		// Test collision between Zombie and Plant
		model.clearEntities();
		PeaShooter peaShooter = new PeaShooter(new Point(0, 0));
		model.addEntity(peaShooter);
		zombie.setPosition(new Point(1, 0));
		assertTrue(model.isCollision(zombie));
		
		// Test that Plant takes damage on collision with Zombie 
		assertEquals(peaShooter.getHealth(), PeaShooter.INITIAL_HEALTH - RegularZombie.DAMAGE);
		
		// Test broken code 
		// Zombie is on top of bullet
		// This should never occur (will be tested in this class)
		zombie.setPosition(new Point(0, 0));
		assertFalse(model.isCollision(zombie));
	}
	
	@Test
	public void testSpawnPlant() {
		// Test successful spawn
		Walnut.resetNextDeployable();
		model.setToggledPlant(Plant.WALNUT);
		model.spawnPlant(new Point(0, 0));
		assertNull(model.getToggledPlant());
		assertEquals(model.getEntities().size(), 1);
		
		// Test balance on successful spawn
		int newBalance = Model.INITIAL_BALANCE - Walnut.COST;
		assertEquals(model.getBalance(), newBalance);
		
		// Test broken code
		// No plant is toggled
		model.spawnPlant(new Point(0, 0));
		assertEquals(model.getEntities().size(), 1);
		
		// Test balance on unsuccessful spawn
		assertEquals(model.getBalance(), newBalance);
	}
	
	@Test 
	public void getUpdateShooters() {
		// Test fire of PeaShooter 
		PeaShooter peaShooter = new PeaShooter(new Point(0, 0));
		assertTrue(peaShooter.canShoot());
		model.addEntity(peaShooter);
		model.updateShooters();
		for (Entity entity: model.getEntities()) {
			if (entity instanceof Bullet) {
				assertTrue(true); // Successful spawn of Bullet Object
				
				// Bullet spawns at location of PeaShooter 
				assertEquals(entity.getPosition(), new Point(0, 0));
				
				// Broken code
				// Bullet spawns at wrong location
				assertNotEquals(entity.getPosition(), new Point(1, 0));
			}
		}
		assertFalse(peaShooter.canShoot());
		model.clearEntities();
		
		// Test fire of Sunflower 
		Sunflower sunflower = new Sunflower(new Point(0, 0));
		assertTrue(sunflower.canShoot());
		model.addEntity(sunflower);
		model.updateShooters();
		for (Entity entity: model.getEntities()) {
			// Successful spawn of Sun Object at random location
			if (entity instanceof Sun) assertTrue(true); 
		}
		assertFalse(sunflower.canShoot());
		model.clearEntities();
		
		// Test fire of Cherry Bomb 
		CherryBomb cherryBomb = new CherryBomb(new Point(0, 0));
		for(int i = 0; i < CherryBomb.DETONATION_TIME - 1; i++) assertFalse(cherryBomb.canShoot());
		assertTrue(cherryBomb.canShoot());
		model.addEntity(cherryBomb);
		model.updateShooters();
		for (Entity entity: model.getEntities()) {
			if (entity instanceof CherryBomb) assertTrue(true); // Successful self destruct of Cherry Bomb
		}
		model.clearEntities();
		
		// Test fire of Repeater
		Repeater repeater = new Repeater(new Point(0, 0));
		assertTrue(repeater.canShoot());
		model.addEntity(repeater);
		model.updateShooters();
		for (Entity entity: model.getEntities()) {
			if (entity instanceof Bullet) {
				assertTrue(true); // Successful spawn of Bullet Object
				
				// Bullet spawns at location of PeaShooter 
				assertEquals(entity.getPosition(), new Point(0, 0));
				
				// Broken code
				// Bullet spawns at wrong location
				assertNotEquals(entity.getPosition(), new Point(1, 0));
			}
		}
		assertFalse(repeater.canShoot());
	}
	
	@Test 
	public void testUpdateMoveables() {
		Zombie zombie = new RegularZombie(new Point(5, 0));
		model.addEntity(zombie);
		
		// Test successful move
		model.updateMoveables();
		assertEquals(zombie.getPosition(), new Point(4, 0));
		
		// Broken code
		// Zombie moves forward twice
		assertNotEquals(zombie.getPosition(), new Point(3, 0));
		
		// Test unsuccessful because of collision
		model.addEntity(new PeaShooter(new Point(3, 0)));
		model.updateMoveables();
		assertEquals(zombie.getPosition(), new Point(4, 0));
		
		// Test Bullet is removed on impact with Zombie
		model.addEntity(new Bullet(new Point(3, 0), 0));
		model.updateMoveables();
		for(Entity entity: model.getEntities()) {
			// There should be no Bullet
			if (entity instanceof Bullet) assertTrue(false);
		}
		
		// Bullet is removed if outside Board domain
		model.clearEntities();
		model.addEntity(new Bullet(new Point(Board.COLUMNS + 1, Board.ROWS), 0));
		model.updateMoveables();
		for(Entity entity: model.getEntities()) {
			// There should be no Bullet
			if (entity instanceof Bullet) assertTrue(false);
		}	
	}
	
	@Test
	public void testCheckForDead() {
		Repeater repeater = new Repeater(new Point(0, 0));
		repeater.selfDestruct();
		model.addEntity(repeater);
		
		// Test successful deletion of dead Entity
		model.checkForDead();
		assertTrue(model.getEntities().isEmpty());
		
		// Test broken code
		// Entity should not be deleted if still healthy
		model.addEntity(new Sunflower(new Point(0, 0)));
		model.checkForDead();
		assertFalse(model.getEntities().isEmpty());
	}
	
	@Test 
	public void testIsSunflowerPurchasable() {
		assertTrue(model.isSunflowerPurchasable());
		
		// Test broken code
		// Insufficient balance
		model.setBalance(0);
		assertFalse(model.isSunflowerPurchasable());
		
		// Test broken code
		// Not deployable
		model.setBalance(Model.INITIAL_BALANCE);
		Sunflower.setNextDeployable(4);
		assertFalse(model.isSunflowerPurchasable());
	}
	
	@Test 
	public void testIsPeaShooterPurchasable() {
		assertTrue(model.isPeaShooterPurchasable());
		
		// Test broken code
		// Insufficient balance
		model.setBalance(0);
		assertFalse(model.isPeaShooterPurchasable());
		
		// Test broken code
		// Not deployable
		model.setBalance(Model.INITIAL_BALANCE);
		PeaShooter.setNextDeployable(4);
		assertFalse(model.isPeaShooterPurchasable());
	}
	
	@Test 
	public void testIsWalnutPurchasable() {
		assertTrue(model.isWalnutPurchasable());
		
		// Test broken code
		// Insufficient balance
		model.setBalance(0);
		assertFalse(model.isWalnutPurchasable());
		
		// Test broken code
		// Not deployable
		model.setBalance(Model.INITIAL_BALANCE);
		Walnut.setNextDeployable(4);
		assertFalse(model.isWalnutPurchasable());
	}
	
	@Test 
	public void testIsRepeaterPurchasable() {
		assertTrue(model.isRepeaterPurchasable());
		
		// Test broken code
		// Insufficient balance
		model.setBalance(0);
		assertFalse(model.isRepeaterPurchasable());
		
		// Test broken code
		// Not deployable
		model.setBalance(Model.INITIAL_BALANCE);
		Repeater.setNextDeployable(4);
		assertFalse(model.isRepeaterPurchasable());
	}
	
	@Test 
	public void testIsCherryBombPurchasable() {
		assertTrue(model.isCherryBombPurchasable());
		
		// Test broken code
		// Insufficient balance
		model.setBalance(0);
		assertFalse(model.isCherryBombPurchasable());
		
		// Test broken code
		// Not deployable
		model.setBalance(Model.INITIAL_BALANCE);
		CherryBomb.setNextDeployable(4);
		assertFalse(model.isCherryBombPurchasable());
	}
	
	@Test
	public void testIsRunning() {
		// Game is running
		assertTrue(model.getIsRunning());
		
		// Test when game is over
		Entity entity = new RegularZombie(new Point(0, 0));
		model.addEntity(entity);
		model.checkGameOver();
		model.checkRoundOver();
		assertFalse(model.getIsRunning());
		
		// Test when round is over
		model.clearEntities();
		model.checkRoundOver();
		assertTrue(model.getIsRunning());
	}
	
	@Test 
	public void testGetIsRunning() {
		assertTrue(model.getIsRunning());
	}
	
	@Test 
	public void testGetEntities() {
		// Test when Entity list is empty
		LinkedList<Entity> entities = new LinkedList<Entity>();
		assertEquals(model.getEntities(), entities);
		assertTrue(model.getEntities().isEmpty());
		
		// Test when Entity list contains items
		Entity entity = new PeaShooter(new Point(0, 0));
		model.addEntity(entity);
		entities.add(entity);
		assertEquals(model.getEntities(), entities);
		
		// Test broken code 
		 entities = new LinkedList<Entity>();
		assertNotEquals(model.getEntities(), entities);
	}
	
	@Test 
	public void testSetEntities() {
		// Test set of empty Entity list
		LinkedList<Entity> entities = new LinkedList<Entity>();
		model.setEntities(entities);
		assertEquals(model.getEntities(), entities);
		
		// Test set Entity list that contains items
		entities.add(new PeaShooter(new Point(0, 0)));
		model.setEntities(entities);
		assertEquals(model.getEntities(), entities);
		
		// Test broken code
		assertNotEquals(model.getEntities(), new PeaShooter(new Point(0, 0)));
	}
	
	
	@Test 
	public void testAddEntity() {
		// Test successful add
		Entity entity = new PeaShooter(new Point(0, 0));
		model.addEntity(entity);
		LinkedList<Entity> entities = new LinkedList<Entity>();
		entities.add(entity);
		assertEquals(model.getEntities(), entities);
	}
	
	@Test 
	public void testRemoveEntity() {
		// Test successful remove
		Entity entity = new PeaShooter(new Point(0, 0));
		model.addEntity(entity);
		model.removeEntity(entity);
		LinkedList<Entity> entities = new LinkedList<Entity>();
		assertEquals(model.getEntities(), entities);
	}
	
	@Test 
	public void testRemoveEntities() {
		Entity entity = new PeaShooter(new Point(0, 0));
		model.addEntity(entity);
		LinkedList<Entity> entities = new LinkedList<Entity>();
		entities.add(entity);
		model.removeEntities(entities);
		
		assertNotEquals(model.getEntities(), entities); // Test broken code 
		
		entities.clear();
		assertEquals(model.getEntities(), entities); // Test successful remove
	}
	
	@Test
	public void testToggledPlant() {
		model.setToggledPlant(Plant.PEA_SHOOTER);
		assertEquals(model.getToggledPlant(), Plant.PEA_SHOOTER);
		
		// Test broken code
		assertNotEquals(model.getToggledPlant(), Plant.CHERRY_BOMB);
	}
	
	@Test
	public void testBalance() {
		// Test initial balance
		assertEquals(model.getBalance(), Model.INITIAL_BALANCE);
		
		// Test set and get balance
		model.setBalance(100);
		assertEquals(model.getBalance(), 100);
		
		// Test broken code on set and get balance
		assertNotEquals(model.getBalance(), 0);
		
		// Test increment balance
		model.increaseBalance(20);
		assertEquals(model.getBalance(), 120);
	}
	
	@Test
	public void testGameCounter() {
		// Test initial game counter
		assertEquals(model.getGameCounter(), 0);
		
		// Test increment game counter
		model.incrementGameCounter();
		assertEquals(model.getGameCounter(), 1);
		assertNotEquals(model.getGameCounter(), 0);
		
		// Test increment game counter
		model.decrementGameCounter();
		assertEquals(model.getGameCounter(), 0);
		assertNotEquals(model.getGameCounter(), 1);
	}
	
	@Test 
	public void testClearEntities() {
		Entity entity = new PeaShooter(new Point(0, 0));
		model.addEntity(entity);
		model.clearEntities();
		assertTrue(model.getEntities().isEmpty());
		
	}
	
}
