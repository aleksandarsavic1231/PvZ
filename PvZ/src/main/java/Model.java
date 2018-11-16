import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/** 
 * The Model contains the game state and logic of PvZ.
 * 
 * @author kylehorne
 * @version 29 Oct 18
 */
public class Model {
	
	private Action plantToggled;
	
	private LinkedList<Listener> listeners;

	/**
	 * Spawned entities. 
	 */
	private LinkedList<Entity> entities;
  	
	private boolean isRunning;
	
	/**
	 * Game balance.
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
		listeners = new LinkedList<Listener>();
		init();
	}	
	
	public void init() {
		isRunning = true;
		entities = new LinkedList<Entity>();
		balance = INITIAL_BALANCE; 
		gameCounter = 0;
		spawnZombies(1);
		plantToggled = null;
	}

	private boolean isOccupied(Point location) {
		for(Entity e : entities) {
			if (!(e instanceof Bullet) && e.getPosition().x == location.x && e.getPosition().y == location.y) {
				return true;
			} 
		}
		return false;
	}

	private boolean isGameOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getPosition().x == 0) {
				isRunning = false;
				return true;
			}
		}
		return false;
	}

	private boolean isRoundOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie) return false;
		}
		isRunning = false;
		return true;
	}

	private void spawnZombies(int n) {
		for (int i = 0; i < n; i ++) {
			Entity zombie = new Zombie(new Point(Board.COLUMNS, new Random().nextInt(Board.ROWS)));
			entities.add(zombie);
			notifyOfSpawn(zombie);
		}
	}
	
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
	
	private boolean isSunflowerPurchasable() {
		return Sunflower.COST <= balance && Sunflower.isDeployable(gameCounter);
	}
	
	private boolean isPeaShooterPurchasable() {
		return PeaShooter.COST <= balance && PeaShooter.isDeployable(gameCounter);
	}
	
	private void spawnPlant(Point location) {
		// Check default conditions to execute
		if (!isRunning || plantToggled == null || isOccupied(location)) return;
		// Ensure toggled plant is purchasable
		boolean hasPurchased = false;
		if (plantToggled == Action.TOGGLE_PEASHOOTER && isPeaShooterPurchasable()) {
			balance -= PeaShooter.COST;
			entities.add(new PeaShooter(location));
			PeaShooter.setNextDeployable(gameCounter);
			hasPurchased = true;
		} else if (plantToggled == Action.TOGGLE_SUNFLOWER && isSunflowerPurchasable()) {
			balance -= Sunflower.COST;
			entities.add(new Sunflower(location));
			Sunflower.setNextDeployable(gameCounter);
			hasPurchased = true;
		} 
		// If successful purchase spawn plant and update new balance
		if (hasPurchased) {
			notifyOfSpawn(entities.getLast());
			notifyOfBalance();
			plantToggled = null;
		}
	}
	
	private void updateShooters() {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity: entities) {
			if (entity instanceof Shooter && ((Shooter) entity).canShoot())  {
				// If PeaShooter can fire add new bullet to Entity list
				if (entity instanceof PeaShooter) tempEntities.add(new Bullet(new Point(entity.getPosition().x, entity.getPosition().y), PeaShooter.DAMAGE));
				// If sunflower can fire add sun reward.
				else if (entity instanceof Sunflower) balance += Sun.REWARD;
			}
		}
		entities.addAll(tempEntities);
	}
	
	private void updateMoveables() {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
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
					if (isBullet && entity.getPosition().x >= Board.COLUMNS) iter.remove();
				} else if (isBullet) { 
					// Remove bullet on impact
					tempEntities.add(entity);
					iter.remove();
				} 
			}
		}
		entities.removeAll(tempEntities);
	}
	
	private void checkForDead() {
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity: entities) {	
			if (entity instanceof Alive) {	
				// Check if Entity is dead 
				if (((Alive) entity).getHealth() <= 0)tempEntities.add(entity);
			}
		}
		entities.removeAll(tempEntities);
	}
	
	private void nextIteration() { 
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
		boolean isRoundOver = isRoundOver();
		boolean isGameOver = isGameOver();
		if (isRoundOver || isGameOver) {
			isRunning = false;
			if (isRoundOver) notifyOfMessage("Congratulations, you beat the round!");
			else notifyOfMessage("You lost the round!");
		}
	}
	
	private void notifyOfMessage(String message) {
		notifyListeners(Action.LOG_MESSAGE, message);
	} 
	
	private void notifyOfBalance() {
		notifyListeners(Action.UPDATE_SUN_POINTS, balance);
		notifyListeners(Action.TOGGLE_PEASHOOTER, isPeaShooterPurchasable());
		notifyListeners(Action.TOGGLE_SUNFLOWER, isSunflowerPurchasable());
	}
	
	private void notifyOfSpawn(Entity e) {
		notifyListeners(Action.SPAWN_ENTITY, e);
	}
	
	private void spawnEntities() {
		for(Entity e: entities) notifyOfSpawn(e);
	}
	
	private void clearBoard() {
		for(Entity e: entities) notifyListeners(Action.REMOVE_ENTITY, e);
	}
	
	public void reducer(Event event) {
		switch(event.getType()) {
		case NEXT_ITERATION:
			nextIteration(); 
			break;
		case TOGGLE_PEASHOOTER:
		case TOGGLE_SUNFLOWER:
			plantToggled = event.getType();
			break;
		case SPAWN_ENTITY:
			spawnPlant((Point) event.getPayload());
			break;
		case RESTART_GAME:
			clearBoard();
			PeaShooter.resetNextDeployable();
			Sunflower.resetNextDeployable();
			init();
			notifyOfBalance();
			break;
		default:
			break;
		}
	}
	
	private void notifyListeners(Action type, Object payload) {
		for(Listener listener : listeners) listener.handleEvent(new Event(type, payload));
	}

	public void addActionListener(Listener listener) {
		listeners.add(listener);
		// Notify listener of initial balance
		notifyOfBalance();
	}
	
}