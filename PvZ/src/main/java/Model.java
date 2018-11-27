import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/** 
 * The Model contains the game state and logic of PvZ.
 * 
 * @author kylehorne
 * @version 24 Nov 18
 */
public class Model {
	
	/**
	 * The currently toggled plant from View.
	 */
	private Plant toggledPlant;
	
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
	 * The player sun point balance.
	 */
	private int balance;
	
	/**
	 * The current game iteration.
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
	public static final int INITIAL_BALANCE = 400;
	
	/**
	 * The lower bound a Zombie can spawn.
	 */
	public static final int LOWER_BOUND = 5 + Board.COLUMNS;
	
	/**
	 * The randomness between Zombie separation. 
	 */
	public static final int NOICE = 8;
	
	/**
	 * The number of Regular Zombies to spawn.
	 */
	public static final int N_REGULAR_ZOMBIES = 3;
	
	/**
	 * The number of Pylon Zombies to spawn.
	 */
	public static final int N_PYLON_ZOMBIES = 1;

	/**
	 * Constructor.
	 */
	public Model() {
		listeners = new LinkedList<Listener>();
		init();
	}	
	
	/**
	 * Initialize fields to default game state.
	 */
	public void init() {
		isRunning = true;
		entities = new LinkedList<Entity>();
		balance = INITIAL_BALANCE; 
		gameCounter = 0;
		spawnRegularZombies(N_REGULAR_ZOMBIES);
		spawnPylonZombies(N_PYLON_ZOMBIES);
		toggledPlant = null;
		notifyListeners(Action.RESTART_GAME);
	}

	/**
	 * Whether a position is currently occupied by another Entity excluding Bullet objects.
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
	 * Spawn n Regular Zombies.
	 * 
	 * @param n The number of Regular Zombies to spawn.
	 */
	
	private void spawnRegularZombies(int n) {
		// TODO: Make lambda function
		for (int i = 0; i < n; i ++) {
			// Spawn further than columns so player has time to increase balance
			Entity zombie = new RegularZombie(new Point(new Random().nextInt(NOICE) + LOWER_BOUND , new Random().nextInt(Board.ROWS)));
			entities.add(zombie);
			notifyOfSpawn(zombie);
		}
	}
	
	
	/**
	 * Spawn n Pylon Zombies.
	 * 
	 * @param n The number of Regular Zombies to spawn.
	 */
	private void spawnPylonZombies(int n) {
		// TODO: Make lambda function
		for (int i = 0; i < n; i ++) {
			// Spawn further than columns so player has time to increase balance
			Entity zombie = new PylonZombie(new Point(new Random().nextInt(NOICE) + LOWER_BOUND , new Random().nextInt(Board.ROWS)));
			entities.add(zombie);
			notifyOfSpawn(zombie);
		}
	}
	
	/**
	 * Check for collision between Entity and Moveable.
	 * 
	 * @param m The Moveable Object to check for collision.
	 * @return boolean True if there is a collision.
	 */
	public boolean isCollision(Moveable m) {
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
			if ((e instanceof PeaShooter || e instanceof Sunflower || e instanceof Walnut || e instanceof Repeater) && m instanceof Zombie && willCollide) {
				((Alive) e).takeDamage(Zombie.DAMAGE);		
				return true;
			}
			if((e instanceof Chomper) && m instanceof Zombie && (willCollide)) {
				if (((Chomper)e).canShoot() == false) {
					((Zombie) m).takeDamage(Chomper.DAMAGE);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Spawn a Plant Object at location.
	 * 
	 * @param location The location to spawn a plant.
	 */
	public void spawnPlant(Point location) {
		// Check default conditions to execute
		if (!isRunning || toggledPlant == null || isOccupied(location)) return;
		boolean hasPurchased = false;
		// Ensure toggled plant is purchasable
		if (toggledPlant == Plant.PEA_SHOOTER && isPeaShooterPurchasable()) {
			balance -= PeaShooter.COST;
			entities.add(new PeaShooter(location));
			PeaShooter.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (toggledPlant == Plant.SUNFLOWER && isSunflowerPurchasable()) {
			balance -= Sunflower.COST;
			entities.add(new Sunflower(location));
			Sunflower.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (toggledPlant == Plant.WALNUT && isWalnutPurchasable()) {
			balance -= Walnut.COST;
			entities.add(new Walnut(location));
			Walnut.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (toggledPlant == Plant.REPEATER && isRepeaterPurchasable()){
			balance -= Repeater.COST;
			entities.add(new Repeater(location));
			Repeater.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if(toggledPlant == Plant.CHERRY_BOMB && isCherryBombPurchasable()) {
			balance -= CherryBomb.COST;
			entities.add(new CherryBomb(location));
			CherryBomb.setNextDeployable(gameCounter);
			hasPurchased = true;
		}else if(toggledPlant == Plant.CHOMPER && isChomperPurchasable()) {
			balance -= Chomper.COST;
			entities.add(new Chomper(location));
			Chomper.setNextDeployable(gameCounter);
			hasPurchased = true;
		}
		// If successful purchase spawn plant and update new balance
		if (hasPurchased) {
			notifyOfSpawn(entities.getLast());
			notifyOfBalance();
			toggledPlant = null;
		}
	}
	
	/**
	 * Get Entities at location (i, j).
	 * 
	 * @param i The i coordinate.
	 * @param j The j coordinate.
	 * @return Entity The Entity at location
	 */
	private LinkedList<Entity> getEntities(int i, int j) {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity : entities) {
			Point location = entity.getPosition();
			if (location.x == i && location.y == j) tempEntities.add(entity);
		}
		return tempEntities;
	}
	
	/**
	 * Explode Bomb at location with an area of 3x3.
	 * 
	 * @param location The location to explode the Bomb.
	 */
	private void explodeBomb(Point location) {
		// Iterate over 3x3 area
		for(int i = location.x - 1; i <= location.x + 1; i++) {
			for(int j = location.y - 1; j <= location.y+1; j++) {
				// Get all Entities at this tile
				LinkedList<Entity> tempEntities = getEntities(i, j);
				for(Entity entity: tempEntities) {
					// If the Entities at this tile are instances of Zombie they take damage
					if(Board.isValidLocation(j, i) && entity != null && entity instanceof Zombie) 
						((Zombie) entity).takeDamage((CherryBomb.DAMAGE));
				}
			}
		}
	}

	/**
	 * Update all Shooter Objects.
	 */
	public void updateShooters() {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity: entities) {
			if (entity instanceof Shooter && ((Shooter) entity).canShoot())  {
				// If PeaShooter can fire add new bullet at PeaShooter location PeaShooter damage
				if (entity instanceof PeaShooter) tempEntities.add(new Bullet(new Point(entity.getPosition().x, entity.getPosition().y), PeaShooter.DAMAGE));
				// If Sunflower can fire spawn Sun randomly on board
				else if (entity instanceof Sunflower) tempEntities.add(new Sun(new Point(new Random().nextInt(Board.COLUMNS), new Random().nextInt(Board.ROWS))));
				else if (entity instanceof CherryBomb) {
					// Explode CherryBomb if it is ready to detonate
					explodeBomb(entity.getPosition());					
					((CherryBomb) entity).selfDestruct(); // CherryBomb explodes itself
				// If Repeater can fire spawn new bullet at Repeater location with Repeater damage
				} else if (entity instanceof Repeater) tempEntities.add(new Bullet(new Point(entity.getPosition().x, entity.getPosition().y), Repeater.DAMAGE));
			}
			else if (entity instanceof Chomper) tempEntities.add(new Chomper(new Point(entity.getPosition().x, entity.getPosition().y)));
		}
		// Add newly spawned Objects to Entities list
		entities.addAll(tempEntities);
	}
	
	/**
	 * Update all Moveable Objects.
	 */
	public void updateMoveables() {
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
			Entity entity = iter.next();
			// Ensure Entity is Moveable and is not waiting to be deleted
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
	 * Check for dead Entities that are instances of Alive (health is >= 0).
	 */
	public void checkForDead() {
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext();) {	
			Entity entity = iter.next();
			if (entity instanceof Alive && ((Alive) entity).getHealth() <= 0) iter.remove(); // Remove dead
		}
	}

	/**
	 * Notify listeners of balance.
	 */
	public void notifyOfBalance() {
		notifyListeners(Action.UPDATE_BALANCE);
		// Purchasable plants may changed on new balance.
		updatePurchasablePlants();
	}
	
	/**
	 * Update purchasable plants.
	 */
	public void updatePurchasablePlants() {
		notifyListeners(Action.TOGGLE_PEASHOOTER);
		notifyListeners(Action.TOGGLE_SUNFLOWER);
		notifyListeners(Action.TOGGLE_WALLNUT);
		notifyListeners(Action.TOGGLE_REPEATER);
		notifyListeners(Action.TOGGLE_CHERRY_BOMB);
		notifyListeners(Action.TOGGLE_CHOMPER);
	}

	/**
	 * Check if a a Sunflower is purchasable.
	 * 
	 * @return boolean True if a Sunflower is purchasable.
	 */
	public boolean isSunflowerPurchasable() {
		return Sunflower.COST <= balance && Sunflower.isDeployable(gameCounter);
	}

	/**
	 * Check if a a PeaShooter is purchasable.
	 * 
	 * @return boolean True if a PeaShooter is purchasable.
	 */
	public boolean isPeaShooterPurchasable() {
		return PeaShooter.COST <= balance && PeaShooter.isDeployable(gameCounter);
	}
	
	/**
	 * Check if a a Walnut is purchasable.
	 * 
	 * @return boolean True if a Walnut is purchasable.
	 */
	public boolean isWalnutPurchasable() {
		return Walnut.COST <= balance && Walnut.isDeployable(gameCounter);
	}
	
	/**
	 * Check if a a Repeater is purchasable.
	 * 
	 * @return boolean True if a Repeater is purchasable.
	 */
	public boolean isRepeaterPurchasable() {
		return Repeater.COST <= balance && Repeater.isDeployable(gameCounter);
	}
	
	/**
	 * Check if a a CherryBomb is purchasable.
	 * 
	 * @return boolean True if a CherryBomb is purchasable.
	 */
	public boolean isCherryBombPurchasable() {
		return CherryBomb.COST <= balance && CherryBomb.isDeployable(gameCounter);
	}
	
	public boolean isChomperPurchasable() {
		return Chomper.COST <= balance && Chomper.isDeployable(gameCounter);
	}
	
	/**
	 * Check if the game is over.
	 * 
	 * @return boolean True if the game is over.
	 */
	private boolean isGameOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getPosition().x == 0) {
				notifyListeners(Action.GAME_OVER);
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the round is over.
	 * 
	 * @return boolean True if the round  is over.
	 */
	private boolean isRoundOver() {
		for(Entity e : entities) if (e instanceof Zombie) return false;
		notifyListeners(Action.ROUND_OVER);
		return true;
	}
	
	/**
	 * Update isRunning field.
	 */
	public void updateIsRunning() { if (isGameOver() || isRoundOver()) isRunning = false; }
	
	/**
	 * Check if the game is still running.
	 * 
	 * @return boolean True if the game is still running.
	 */
	public boolean getIsRunning() { return isRunning; }

	/**
	 * Notify listeners to spawn all Entities.
	 */
	public void spawnEntities() { for(Entity entity: entities) notifyOfSpawn(entity); }
	
	/**
	 * Notify listeners to spawn Entity.
	 *  
	 * @param entity The Entity to spawn.
	 */
	private void notifyOfSpawn(Entity entity) { notifyListeners(Action.SPAWN_ENTITY, entity); }
	
	/**
	 * Notify listeners to remove Entity.
	 *  
	 * @param entity The Entity to remove.
	 */
	private void notifyOfRemove(Entity entity) { notifyListeners(Action.REMOVE_ENTITY, entity); } 
	
	/**
	 * Notify listeners to remove all Entities.
	 */
	public void clearBoard() { for(Entity entity: entities) notifyOfRemove(entity); }
	
	/**
	 * Get Entities.
	 * 
	 * @return LinkedList<Entity> The list of Entities.
	 */
	public LinkedList<Entity> getEntities() { return entities; }

	/**
	 * Set Entities.
	 * 
	 * @param entities The Entities to set.
	 */
	public void setEntities(LinkedList<Entity> entities) { 
		clearBoard();
		this.entities = entities; 	
		spawnEntities();
	}
	
	/**
	 * Add Entity.
	 * 
	 * @param entity The Entity to be added.
	 */
	public void addEntity(Entity entity) { 
		entities.add(entity); 
		notifyOfSpawn(entity);
	}
	
	/**
	 * Remove Entity.
	 * 
	 * @param entity The Entity to be removed.
	 */
	public void removeEntity(Entity entity) {
		entities.remove(entity);
		notifyOfRemove(entity);
	}

	/**
	 * Remove Entities.
	 * 
	 * @param entities The Entities to be removed. 
	 */
	public void removeEntities(LinkedList<Entity> entities) { 
		this.entities.removeAll(entities); 
		clearBoard();
		spawnEntities();
	}
	
	/**
	 * Set currently toggled plant.
	 * 
	 * @param plant The toggled Plant to be set.
	 */
	public void setTogglePlant(Plant plant) { toggledPlant = plant; }
	
	/**
	 * Get currently toggled Plant.
	 * 
	 * @return Plant The currently toggled Plant.
	 */
	public Plant getTogglePlant() { return toggledPlant; }

	/**
	 * Get balance of player.
	 * 
	 * @return int The Sun point balance.
	 */
	public int getBalance() { return balance; }
	
	/**
	 * Set the Sun point balance.
	 * 
	 * @param balance The new balance.
	 */
	public void setBalance(int balance) { 
		this.balance = balance; 
		notifyOfBalance();
	}
	
	/**
	 * Increase balance by delta.
	 * 
	 * @param delta The change in balance.
	 */
	public void increaseBalance(int delta) { setBalance(balance + delta); }
	
	/**
	 * Get the current game iteration.
	 * 
	 * @return int The current game iteration.
	 */
	public int getGameCounter() { return gameCounter; }
	
	/**
	 * Decrement game counter.
	 */
	public void decrementGameCounter() { gameCounter--; }
	
	/**
	 * Increment game counter.
	 */
	public void incrementGameCounter()  { gameCounter++; } 
	
	/**
	 * Notify all listeners of Event.
	 * 
	 * @param type The type of Action caused by Event.
	 */
	public void notifyListeners(Action type) {
		for(Listener listener : listeners) listener.handleEvent(new Event(type));
	}
	
	/**
	 *  Notify all listeners of Entity Event.
	 * 
	 * @param type The type of Action caused by Event.
	 * @param entity The Entity triggering the Event.
	 */
	public void notifyListeners(Action type, Entity entity) {
		for(Listener listener : listeners) listener.handleEvent(new EntityEvent(type, entity));
	}

	/**
	 * Add listener to this Model Object.
	 * 
	 * @param listener The listener to add.
	 */
	public void addActionListener(Listener listener) {
		listeners.add(listener);
		notifyOfBalance(); // Notify listener of initial balance
	}
	
	/**
	 * Clear Entity list.
	 */
	public void clearEntities() {
		entities.clear();
	}
	
}