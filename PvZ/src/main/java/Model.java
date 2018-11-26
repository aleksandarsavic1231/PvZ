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
	public static final int N_PYLON_ZOMBIES = 0;

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
		plantToggled = null;
		notifyListeners(Action.RESTART_GAME);
	}

	/**
	 * Whether a position is currently occupied by another Entity excluding Bullet objects.
	 * Bullets are excluded since our implementation allows you to spawn a Plant on top of a Bullet.
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

	// TODO: Make lambda function
	private void spawnRegularZombies(int n) {
		for (int i = 0; i < n; i ++) {
			// Spawn further than columns so player has time to increase balance
			Entity zombie = new RegularZombie(new Point(new Random().nextInt(NOICE) + LOWER_BOUND , new Random().nextInt(Board.ROWS)));
			entities.add(zombie);
			notifyOfSpawn(zombie);
		}
	}
	
	// TODO: Make lambda function
	private void spawnPylonZombies(int n) {
		for (int i = 0; i < n; i ++) {
			// Spawn further than columns so player has time to increase balance
			Entity zombie = new PylonZombie(new Point(new Random().nextInt(NOICE) + LOWER_BOUND , new Random().nextInt(Board.ROWS)));
			entities.add(zombie);
			notifyOfSpawn(zombie);
		}
	}
	
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
		}
		return false;
	}
	
	public void spawnPlant(Point location) {
		// Check default conditions to execute
		if (!isRunning || plantToggled == null || isOccupied(location)) return;
		boolean hasPurchased = false;
		// Ensure toggled plant is purchasable
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
			balance -= Walnut.COST;
			entities.add(new Walnut(location));
			Walnut.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (plantToggled == Plant.REPEATER && isRepeaterPurchasable()){
			balance -= Repeater.COST;
			entities.add(new Repeater(location));
			Repeater.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if(plantToggled == Plant.BOMB && isBombPurchasable()) {
			balance -= Bomb.COST;
			entities.add(new Bomb(location));
			Bomb.setNextDeployable(gameCounter);
			hasPurchased = true;
		}
		// If successful purchase spawn plant and update new balance
		if (hasPurchased) {
			notifyOfSpawn(entities.getLast());
			notifyOfBalance();
			plantToggled = null;
		}
	}
	
	private Entity getEntity(int i, int j) {
		for(Entity e : entities) {
			if (e.getPosition().x == i && e.getPosition().y == j) return e;
		}
		return null;
	}
	
	private void explodeBomb(Point location) {
		for(int i = location.x - 1; i <= location.x + 1; i++) {
			for(int j = location.y - 1; j <= location.y+1; j++) {
				Entity entity = getEntity(i, j);
				if(Board.isValidLocation(j, i) && entity != null && entity instanceof Zombie) 
					((Zombie) entity).takeDamage((Bomb.DAMAGE));
			}
		}
	}

	public void updateShooters() {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity: entities) {
			if (entity instanceof Shooter && ((Shooter) entity).canShoot())  {
				// If PeaShooter can fire add new bullet at PeaShooter location
				if (entity instanceof PeaShooter) tempEntities.add(new Bullet(new Point(entity.getPosition().x, entity.getPosition().y), PeaShooter.DAMAGE));
				// If Sunflower can fire spawn Sun randomly on board
				else if (entity instanceof Sunflower) tempEntities.add(new Sun(new Point(new Random().nextInt(Board.COLUMNS), new Random().nextInt(Board.ROWS))));
				else if (entity instanceof Bomb) {
					explodeBomb(entity.getPosition());					
					((Bomb) entity).selfDestruct(); // Bomb explodes 
				} else if (entity instanceof Repeater) tempEntities.add(new Bullet(new Point(entity.getPosition().x, entity.getPosition().y), Repeater.DAMAGE));
			}
		}
		entities.addAll(tempEntities);
	}
	
	public void updateMoveables() {
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
	
	public void checkForDead() {
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext();) {	
			Entity entity = iter.next();
			if (entity instanceof Alive && ((Alive) entity).getHealth() <= 0) iter.remove(); // Remove dead
		}
	}

	public void notifyOfBalance() {
		notifyListeners(Action.UPDATE_BALANCE);
		// Purchasable plants may changed on new balance.
		updatePurchasablePlants();
	}
	
	public void updatePurchasablePlants() {
		notifyListeners(Action.TOGGLE_PEASHOOTER);
		notifyListeners(Action.TOGGLE_SUNFLOWER);
		notifyListeners(Action.TOGGLE_WALLNUT);
		notifyListeners(Action.TOGGLE_REPEATER);
		notifyListeners(Action.TOGGLE_BOMB);
	}

	public boolean isSunflowerPurchasable() {
		return Sunflower.COST <= balance && Sunflower.isDeployable(gameCounter);
	}

	public boolean isPeaShooterPurchasable() {
		return PeaShooter.COST <= balance && PeaShooter.isDeployable(gameCounter);
	}
	
	public boolean isWallnutPurchasable() {
		return Walnut.COST <= balance && Walnut.isDeployable(gameCounter);
	}
	
	public boolean isRepeaterPurchasable() {
		return Repeater.COST <= balance && Repeater.isDeployable(gameCounter);
	}
	
	public boolean isBombPurchasable() {
		return Bomb.COST <= balance && Bomb.isDeployable(gameCounter);
	}
	
	private boolean isGameOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getPosition().x == 0) {
				notifyListeners(Action.GAME_OVER);
				return true;
			}
		}
		return false;
	}

	private boolean isRoundOver() {
		for(Entity e : entities) if (e instanceof Zombie) return false;
		notifyListeners(Action.ROUND_OVER);
		return true;
	}
	
	public void updateRunnable() { if (isGameOver() || isRoundOver()) isRunning = false; }
	
	public boolean isRunning() { return isRunning; }

	public void spawnEntities() { for(Entity entity: entities) notifyOfSpawn(entity); }
	
	private void notifyOfSpawn(Entity entity) { notifyListeners(Action.SPAWN_ENTITY, entity); }
	
	private void notifyOfRemove(Entity entity) { notifyListeners(Action.REMOVE_ENTITY, entity); } 
	
	public void clearBoard() { for(Entity entity: entities) notifyOfRemove(entity); }
	
	public LinkedList<Entity> getEntities() { return entities; }

	public void setEntities(LinkedList<Entity> entities) { 
		clearBoard();
		this.entities = entities; 	
		spawnEntities();
	}
	
	public void addEntity(Entity entity) { 
		entities.add(entity); 
		notifyOfSpawn(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
		notifyOfRemove(entity);
	}

	public void removeEntities(LinkedList<Entity> entities) { 
		this.entities.removeAll(entities); 
		clearBoard();
		spawnEntities();
	}
	
	public void setTogglePlant(Plant plant) { plantToggled = plant; }
	
	public Plant getTogglePlant() { return plantToggled; }

	public int getBalance() { return balance; }
	
	public void setBalance(int balance) { 
		this.balance = balance; 
		notifyOfBalance();
	}
	
	public void increaseBalance(int delta) { setBalance(balance + delta); }
	
	public int getGameCounter() { return gameCounter; }
	
	public void decrementGameCounter() { gameCounter--; }
	
	public void incrementGameCounter()  { gameCounter++; } 
	
	public void notifyListeners(Action type) {
		for(Listener listener : listeners) listener.handleEvent(new Event(type));
	}
	
	public void notifyListeners(Action type, Entity entity) {
		for(Listener listener : listeners) listener.handleEvent(new EntityEvent(type, entity));
	}

	public void addActionListener(Listener listener) {
		listeners.add(listener);
		notifyOfBalance(); // Notify listener of initial balance
	}
	
}