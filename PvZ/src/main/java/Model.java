import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/** 
 * The Model contains the game state and logic.
 * 
 * @author kylehorne
 * @version 29 Oct 18
 * @param <R>
 */
public class Model {
	
	/**
	 * 
	 */
	private Action plantType;
	
	/**
	 * 
	 */
	private LinkedList<Listener> listeners;

	/**
	 * Spawned entities. 
	 */
	private LinkedList<Entity> entities;
  	
	private boolean isGameOver;
	
	/**
	 * Game balance.
	 */
	private int sunPoints;
	
	/**
	 * Current game iteration.
	 */
	private int gameCounter;

	/**
	 * The period which sun points are automatically rewarded to balance (welfare)
	 */
	public static final int PAYMENT_PERIOD = 4;
	
	/**
	 * The sun points automatically deposited every payment period
	 */
	public static final int WELFARE = 25;
	
	/**
	 * The initial sun point balance.
	 */
	public static final int INITIAL_BALANCE = 100;
	
	/**
	 * Constructor.
	 */
	public Model() {
		listeners =  new LinkedList<Listener>();
		isGameOver = false;
		entities = new LinkedList<Entity>();
		sunPoints = INITIAL_BALANCE; 
		gameCounter = 0;
		spawnZombies(1);
		plantType = null;
	}	
	
	public void initialize() {
		

		
		isGameOver = false;
		entities = new LinkedList<Entity>();
		sunPoints = INITIAL_BALANCE; 
		gameCounter = 0;
		spawnZombies(1);
		plantType = null;
		
		PeaShooter.resetNextDeployable();
		Sunflower.resetNextDeployable();
		
		boolean isSunflowerPurchasable = sunPoints >= Sunflower.COST && Sunflower.isDeployable(gameCounter);
		boolean isPeaShooterPurchasable = sunPoints >= PeaShooter.COST && PeaShooter.isDeployable(gameCounter);
		
		notifyListeners(Action.TOGGLE_PEASHOOTER, isPeaShooterPurchasable);
		notifyListeners(Action.TOGGLE_SUNFLOWER, isSunflowerPurchasable);
		
		notifyListeners(Action.UPDATE_SUN_POINTS, INITIAL_BALANCE); 
	}

	/**
	 * Check whether a position is occupied by another Entity excluding Bullet.
	 * 
	 * @param location The location to check.
	 * @return boolean True if position is currently occupied by another Entity excluding Bullet.
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
	 * Check if game is over.
	 * Game is over if and only if a Zombie has traversed the board.
	 * 
	 * @return boolean True if game is over.
	 */
	private boolean isGameOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getPosition().x == 0) {
				isGameOver =  true;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the current round is over.
	 * The round is over if all instances of Zombie have died.
	 * 
	 * @return boolean True if the round is over.
	 */
	private boolean isRoundOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie) return false;
		}
		isGameOver = true;
		return true;
	}
	
	/**
	 * Spawn n Zombies at a random location.
	 * 
	 * @param n The number of zombies to spawn.
	 */
	private void spawnZombies(int n) {
		for (int i = 0; i < n; i ++) {
			Entity zombie = new Zombie(new Point(Board.COLUMNS, new Random().nextInt(Board.ROWS)));
			entities.add(zombie);
			notifyListeners(Action.SPAWN_ENTITY, zombie);
		}
	}
	
	/**
	 * Whether a Moveable object will collide with game Entities.
	 * 
	 * @param m The Moveable object to check.
	 * @return boolean True if a collision has occurred.
	 */
	private boolean isCollision(Moveable m) {
		for(Entity e: entities) {
			// A collision will occur if the next position of Moveable is currently occupied.
			boolean willCollide = e.getPosition().x == m.nextPosition().x && e.getPosition().y == m.nextPosition().y;
			// A collision occurred if two entities are on top of each other.
			boolean hasCollided = e.getPosition().x == ((Entity) m).getPosition().x && e.getPosition().y == ((Entity) m).getPosition().y;
			// Zombie hit by bullet
			if (e instanceof Zombie && m instanceof Bullet && (hasCollided || willCollide)) {
				((Zombie) e).setHealth(((Bullet) m).getDamage());
				return true;
			}
			// Zombie collided with plant 
			if ((e instanceof PeaShooter || e instanceof Sunflower) && m instanceof Zombie && willCollide) {
				((Alive) e).setHealth(Zombie.DAMAGE);		
				return true;
			}
		}
		return false;
	}
	
	private void spawn(Point location) {
		if (isGameOver || plantType == null || isOccupied(location)) return;
		
		boolean isSunflowerPurchasable = sunPoints >= Sunflower.COST && Sunflower.isDeployable(gameCounter);
		boolean isPeaShooterPurchasable = sunPoints >= PeaShooter.COST && PeaShooter.isDeployable(gameCounter);
		boolean hasPurchased = false;
		
		if (isPeaShooterPurchasable && plantType == Action.TOGGLE_PEASHOOTER) {
			sunPoints -= PeaShooter.COST;
			entities.add(new PeaShooter(location));
			PeaShooter.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (isSunflowerPurchasable && plantType == Action.TOGGLE_SUNFLOWER) {
			sunPoints -= Sunflower.COST;
			entities.add(new Sunflower(location));
			Sunflower.setNextDeployable(gameCounter);
			hasPurchased = true;
		}
		
		if (hasPurchased) {
			notifyListeners(Action.SPAWN_ENTITY, entities.getLast());
			notifyListeners(Action.UPDATE_SUN_POINTS, sunPoints);
		}
		
		 isSunflowerPurchasable = sunPoints >= Sunflower.COST && Sunflower.isDeployable(gameCounter);
		 isPeaShooterPurchasable = sunPoints >= PeaShooter.COST && PeaShooter.isDeployable(gameCounter);
		
		notifyListeners(Action.TOGGLE_PEASHOOTER, isPeaShooterPurchasable);
		notifyListeners(Action.TOGGLE_SUNFLOWER, isSunflowerPurchasable);
	}
	
	


	private void nextIteration() {	
		if (isGameOver) return;
		
		boolean isSunflowerPurchasable = sunPoints >= Sunflower.COST && Sunflower.isDeployable(gameCounter);
		boolean isPeaShooterPurchasable = sunPoints >= PeaShooter.COST && PeaShooter.isDeployable(gameCounter);
		
		notifyListeners(Action.TOGGLE_PEASHOOTER, isPeaShooterPurchasable);
		notifyListeners(Action.TOGGLE_SUNFLOWER, isSunflowerPurchasable);
		
		// Clear Entities from board
		for(Entity e: entities) notifyListeners(Action.REMOVE_ENTITY, e);

		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity: entities) {
			if (entity instanceof Shooter && ((Shooter) entity).canShoot())  {
				// If PeaShooter can fire add new bullet to Entity list
				if (entity instanceof PeaShooter) tempEntities.add(new Bullet(new Point(entity.getPosition().x, entity.getPosition().y), PeaShooter.DAMAGE));
				// If sunflower can fire add sun reward.
				else if (entity instanceof Sunflower) sunPoints += Sun.REWARD;
			}
		}
		entities.addAll(tempEntities); // Add new Entities to Entities list	
		

		
		// Update position of Moveable Entities
		tempEntities = new LinkedList<Entity>();
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
			Entity entity = iter.next();
			// Ensure Entity is Moveable and is not waiting to be delete
			if (entity instanceof Moveable) {
				Moveable m = ((Moveable) entity);
				boolean isBullet = m instanceof Bullet;
				m.unlock(); // Unlock to allow update position on this game iteration
				if (!isCollision(m)) { 
					m.updatePosition(); // Update position if there is no collision
					// Remove bullet if domain is greater than game board columns
					if (isBullet && entity.getPosition().x >= Board.COLUMNS) {
						iter.remove();
					}
				} else if (isBullet) { 
					// Remove bullet on impact
					tempEntities.add(entity);
					iter.remove();
				} 
			}
		}
		entities.removeAll(tempEntities);	
		
		// Check for dead Entities
		tempEntities = new LinkedList<Entity>();
		for(Entity entity: entities) {	
			if (entity instanceof Alive) {	
				// Check if Entity is dead 
				if (((Alive) entity).getHealth() <= 0) {
					System.out.println(entity.getClass().getName() + " died");	
					tempEntities.add(entity);
				} else {
					// Print health of Entity if still alive
					System.out.println(entity.getClass().getName() + " health: " + ((Alive) entity).getHealth());
				}
			}
		}
		entities.removeAll(tempEntities);	
		
		// Add entities to board
		for(Entity e: entities) notifyListeners(Action.SPAWN_ENTITY, e);
		gameCounter++;
		// Add automatic welfare if payment period has elapsed 
		if (gameCounter % Model.PAYMENT_PERIOD == 0) sunPoints += Model.WELFARE;	
		notifyListeners(Action.UPDATE_SUN_POINTS, sunPoints);
		
		if (isRoundOver()) notifyListeners(Action.LOG_MESSAGE, "Congratulations, you beat the round!");
		if (isGameOver()) notifyListeners(Action.LOG_MESSAGE, "You lost!");
	}
	
	public void reducer(Dispatch dispatch) {
		Action type = dispatch.getType();
		Object payload = dispatch.getPayload();
		switch(type) {
		case NEXT_ITERATION:
			nextIteration(); 
			break;
		case TOGGLE_PEASHOOTER:
		case TOGGLE_SUNFLOWER:
			plantType = type;
			break;
		case SPAWN_ENTITY:
			spawn((Point) payload);
			break;
		case RESTART_GAME:
			for(Entity e: entities) notifyListeners(Action.REMOVE_ENTITY, e);
			initialize();
			break;
		default:
			break;
		}
	}
	
	private void notifyListeners(Action type, Object payload) {
		for(Listener listener : listeners) listener.handleEvent(new Dispatch(type, payload));
	}

	public void addActionListener(Listener listener) {
		listeners.add(listener);
		// Notify listener of initial balance
		notifyListeners(Action.UPDATE_SUN_POINTS, INITIAL_BALANCE); 
	}
	
}