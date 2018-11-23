import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/** 
 * The Model contains the game state and logic.
 * 
 * @author kylehorne
 * @version 29 Oct 18
 */
public class Model {
	
	/**
	 * The currently toggled plant from View.
	 */
	private Plant plantToggled;
	
	/**
	 * List of Model listeners.
	 */
	private LinkedList<Listener> listeners;

	/**
	 * Spawned entities.
	 */
	private LinkedList<Entity> entities;
  	
	/**
	 * Whether the game is running.
	 */
	private boolean isRunning;
	
	/**
	 * Sun point balance.
	 */
	private int balance;
	
	/**
	 * Current game iteration.
	 */
	private int gameCounter;

	/**
	 * The period which sun points are automatically rewarded to balance (welfare).
	 */
	public static final int PAYMENT_PERIOD = 4;
	
	/**
	 * The sun points automatically deposited every payment period.
	 */
	public static final int WELFARE = 25;
	
	/**
	 * The initial sun point balance.
	 */
	public static final int INITIAL_BALANCE = 100;
	
	/**
	 * The lower bound a Zombie can spawn.
	 */
	public static final int LOWER_BOUND = 5 + Board.COLUMNS;
	
	/**
	 * The randomness between Zombie separation. 
	 */
	public static final int NOICE = 8;
	
	/**
	 * The number of Zombies to spawn.
	 */
	public static final int NUM_ZOMBIES = 3;
	
	/**
	 * Constructor.
	 */
	public Model() {
		listeners = new LinkedList<Listener>();
		init();
	}	
	
	/**
	 * Initialize fields to default state.
	 */
	public void init() {
		isRunning = true;
		entities = new LinkedList<Entity>();
		balance = INITIAL_BALANCE; 
		gameCounter = 0;
		spawnZombies(NUM_ZOMBIES);
		plantToggled = null;
	}

	/**
	 * Whether a position is currently occupied by another Entity excluding Bullets.
	 * 
	 * @param location The location to check.
	 * @return boolean True if location is occupied.
	 */
	private boolean isOccupied(Point location) {
		for(Entity e : entities) {
			if (!(e instanceof Bullet) && e.getPosition().x == location.x && e.getPosition().y == location.y) {
				return true;
			} 
		}
		return false;
	}

	/**
	 * Check whether the game is over. The game is over when a Zombie has traversed the board (0, y).
	 * 
	 * @return boolean True if the game is over.
	 */
	private boolean isGameOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getPosition().x == 0) {
				isRunning = false; 
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the round is over. The round is over when no zombies remain.
	 * 
	 * @return boolean True if the round is over.
	 */
	private boolean isRoundOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie) return false;
		}
		isRunning = false;
		return true;
	}

	/**
	 * Spawn n Zombies at a random position greater than the board domain and within board range.
	 *  
	 * @param n The number of Zombies to spawn.
	 */
	private void spawnZombies(int n) {
		// The number of Regular Zombies to spawn.
		int numRegular = n - new Random().nextInt(n) + 1;
		for (int i = 0; i < n; i ++) {
			Entity zombie;
			// Spawn further than columns so player has time to increase balance
			Point spawnLocation = new Point(new Random().nextInt(NOICE) + LOWER_BOUND , new Random().nextInt(Board.ROWS));
			if (i < numRegular) zombie = new RegularZombie(spawnLocation);
			else zombie = new PylonZombie(spawnLocation);
			entities.add(zombie);
			notifyOfSpawn(zombie);
		}
	}
	
	/**
	 * Check if there is a collision between a Moveable object and another Entity.
	 * 
	 * @param m The Moveable object to check for collision.
	 * @return boolean True if there is a collision.
	 */
	private boolean isCollision(Moveable m) {
		for(Entity e: entities) {
			// A collision will occur if the next position of Moveable is currently occupied.
			boolean willCollide = e.getPosition().x == m.nextPosition().x && e.getPosition().y == m.nextPosition().y;
			// A collision occurred if two entities are on top of each other.
			boolean hasCollided = e.getPosition().x == ((Entity) m).getPosition().x && e.getPosition().y == ((Entity) m).getPosition().y;
			// Zombie hit by bullet
			if (e instanceof Zombie && m instanceof Bullet && (hasCollided || willCollide)) {
				((Zombie) e).takeDamage(((Bullet) m).getDamage());
				return true;
			}
			// Zombie collided with plant 
			if ((e instanceof PeaShooter || e instanceof Sunflower || e instanceof Wallnut) && m instanceof Zombie && willCollide) {
				((Alive) e).takeDamage(Zombie.DAMAGE);		
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the Sunflower is purchasable.
	 * 
	 * @return boolean True if the Sunflower is purchasable.
	 */
	public boolean isSunflowerPurchasable() {
		// Sunflower is purchasable if player has sufficient balance and Sunflower is deployable.
		return Sunflower.COST <= balance && Sunflower.isDeployable(gameCounter);
	}
	
	/**
	 * Check if the PeaShooter is purchasable.
	 * 
	 * @return boolean True if the PeaShooter is purchasable.
	 */
	public boolean isPeaShooterPurchasable() {
		// PeaShooter is purchasable if player has sufficient balance and Sunflower is deployable.
		return PeaShooter.COST <= balance && PeaShooter.isDeployable(gameCounter);
	}
	
	public boolean isWallnutPurchasable() {
		return Wallnut.COST <= balance && Wallnut.isDeployable(gameCounter);
	}
	
	/**
	 * Spawn plant at location based on toggled button.
	 * 
	 * @param location The location to spawn a plant.
	 */
	public void spawnPlant(Point location) {
		// Check default conditions to execute
		if (!isRunning || plantToggled == null || isOccupied(location)) return;
		// Ensure toggled plant is purchasable
		boolean hasPurchased = false;
		if (plantToggled == Plant.PEA_SHOOTER && isPeaShooterPurchasable()) {
			balance -= PeaShooter.COST;
			entities.add(new PeaShooter(location));
			PeaShooter.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (plantToggled == Plant.SUNFLOWER && isSunflowerPurchasable()) {
			balance -= Sunflower.COST;
			entities.add(new Sunflower(location));
			Sunflower.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (plantToggled == Plant.WALNUT && isWallnutPurchasable()) {
			balance -= Wallnut.COST;
			entities.add(new Wallnut(location));
			Wallnut.setNextDeployable(gameCounter);
			hasPurchased = true;
		}
		// If successful purchase spawn plant and update new balance
		if (hasPurchased) {
			notifyOfSpawn(entities.getLast());
			notifyOfBalance();
			plantToggled = null;
		}
	}
	
	/**
	 * Check if a Shooter object can shoot.
	 */
	private void updateShooters() {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity: entities) {
			if (entity instanceof Shooter && ((Shooter) entity).canShoot())  {
				// If PeaShooter can fire add new bullet at PeaShooter location
				if (entity instanceof PeaShooter) tempEntities.add(new Bullet(new Point(entity.getPosition().x, entity.getPosition().y), PeaShooter.DAMAGE));
				// If Sunflower can fire spawn Sun randomly on board
				else if (entity instanceof Sunflower) tempEntities.add(new Sun(new Point(new Random().nextInt(Board.COLUMNS), new Random().nextInt(Board.ROWS))));
			}
		}
		entities.addAll(tempEntities);
	}
	
	/**
	 * Update all Moveable objects.
	 */
	private void updateMoveables() {
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
			Entity entity = iter.next();
			// Ensure Entity is Moveable and is not waiting to be delete
			if (entity instanceof Moveable) {
				Moveable m = ((Moveable) entity);
				boolean isBullet = m instanceof Bullet;
				m.unlock(); // Unlock to allow update position on this game iteration
				if (!isCollision(m)) { 
					m.updatePosition(); // Update position if there is no collision
					// Remove bullet if location is greater than board domain
					if (isBullet && Board.COLUMNS < entity.getPosition().x) iter.remove();
				} else if (isBullet) iter.remove(); // Remove bullet on impact
			}
		}
	}
	
	/**
	 * Check for dead Entities.
	 */
	private void checkForDead() {
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext();) {	
			Entity entity = iter.next();
			if (entity instanceof Alive && ((Alive) entity).getHealth() <= 0) iter.remove(); // Remove dead
		}
	}
	
	/**
	 * Update game state.
	 */
	public void nextIteration() { 
		if (!isRunning) return; // Check if game is running
		clearBoard();		
		updateShooters();
		updateMoveables();
		checkForDead();
		spawnEntities();
		gameCounter++;
		// Add automatic welfare if payment period has elapsed 
		if (gameCounter % Model.PAYMENT_PERIOD == 0) balance += Model.WELFARE;	
		notifyOfBalance();
		// Check if game is still runnable
		if (isRoundOver()) notifyListeners(Action.ROUND_OVER);
		if (isGameOver()) notifyListeners(Action.GAME_OVER);
	}
	
	public void lastIteration() {
		clearBoard();		
		spawnEntities();
	}
	 
	/**
	 * Notify listeners of balance.
	 */
	private void notifyOfBalance() {
		notifyListeners(Action.UPDATE_BALANCE);
		// Purchasable plants may changed on new balance.
		notifyListeners(Action.TOGGLE_PEASHOOTER);
		notifyListeners(Action.TOGGLE_SUNFLOWER);
		notifyListeners(Action.TOGGLE_WALLNUT);
	}
	
	/**
	 * Notify listeners of newly spawned Entity.
	 * 
	 * @param e The Entity spawned.
	 */
	private void notifyOfSpawn(Entity e) {
		notifyListeners(Action.SPAWN_ENTITY, e);
	}
	
	/**
	 * Spawn all Entities.
	 */
	private void spawnEntities() {
		for(Entity e: entities) notifyOfSpawn(e);
	}
	
	/**
	 * Notify listeners of deleted Entity.
	 * 
	 * @param e The Entity to be removed.
	 */
	private void removeEntity(Entity e) {
		 notifyListeners(Action.REMOVE_ENTITY, e);
	} 
	
	/**
	 * Notify listeners to clear board.
	 */
	private void clearBoard() {
		for(Entity e: entities) removeEntity(e);
	}
	
	/**
	 * Check if location contains Sun.
	 * 
	 * @param location The location to check.
	 * @return boolean True if the location contains Sun.
	 */
	public boolean containsSun(Point location) {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		boolean foundSun = false;
		for(Entity e: entities) {
			if (e instanceof Sun && e.getPosition().x == location.x && e.getPosition().y == location.y) {
				removeEntity(e);
				balance += Sun.REWARD;
				notifyOfBalance();
				foundSun = true;
				tempEntities.add(e);
			}
		}
		entities.removeAll(tempEntities);
		if (foundSun) spawnEntities(); // If Sun was clicked, there may be an Entity underneath that we need to spawn.
		return foundSun;
	}
	
	public void restart() {
		clearBoard();
		PeaShooter.resetNextDeployable();
		Sunflower.resetNextDeployable();
		Wallnut.resetNextDeployable();
		init();
		notifyOfBalance();
	}
	
	public int getBalance() {
		return balance;
	}
	
	public LinkedList<Entity> getEntities() {
		return entities;
	}
	
	public void setEntities(LinkedList<Entity> entities) {
		this.entities = entities;
	}
  	
	
	public void setTogglePlant(Plant plant) {
		plantToggled = plant;
	}
	
	/**
	 * Notify listeners of Event.
	 * 
	 * @param type The Action type.
	 * @param payload The payload coupled to action.
	 */
	private void notifyListeners(Action type) {
		for(Listener listener : listeners) listener.handleEvent(new Event(type));
	}
	
	private void notifyListeners(Action type, Entity entity) {
		for(Listener listener : listeners) listener.handleEvent(new EntityEvent(type, entity));
	}

	/**
	 * Add listener to this.
	 *  
	 * @param listener The listener to add.
	 */
	public void addActionListener(Listener listener) {
		listeners.add(listener);
		notifyOfBalance(); // Notify listener of initial balance
	}
	
}