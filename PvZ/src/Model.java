import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

/** 
 * The game Model of the Plant vs. Zombies.
 * The Model contains the game state and logic.
 * 
 * @author kylehorne
 * @version 29 Oct 18
 */
public class Model {

	/**
	 * Spawned entities. 
	 */
	private LinkedList<Entity> entities;
  	
	/**
	 * Game balance.
	 */
	private int sunPoints;
	
	/**
	 * Current game iteration.
	 */
	public int gameCounter;

	/**
	 * The period which sun points are automatically rewarded to balance (welfare)
	 */
	public static final int PAYMENT_PERIOD = 4;
	
	/**
	 * The sun points automatically deposited every payment period
	 */
	public static final int WELFARE = 25;
	
	/**
	 * 
	 */
	public static final int INITIAL_BALANCE = 100;
	
	/**
	 * 
	 */
	private boolean gameOver;
	
	/**
	 * 
	 */
	private boolean roundOver;
	
	/**
	 * Constructor.
	 */
	public Model() {
		 entities = new LinkedList<Entity>();
		 sunPoints = INITIAL_BALANCE; 
		 gameCounter = 0;
		 spawnZombies(1);
		 gameOver = false;
	}	

	/**
	 * Check whether a position is occupied by another Entity excluding Bullet.
	 * 
	 * @param p The location to check.
	 * @return boolean True if position is currently occupied by another Entity excluding Bullet.
	 */
	public boolean isOccupied(int x, int y) {
		if (!roundOver) {
			for(Entity e : entities) {
				if (!(e instanceof Bullet) && e.getX() == x && e.getY() == y) {
					roundOver = true;
				} 
			}
		}
		return roundOver;
	}

	/**
	 * Check if game is over (player has lost the game).
	 * Game is over if and only if a Zombie has traversed the board.
	 * 
	 * @return boolean True if game is over.
	 */
	public boolean isGameOver() {
		if (!gameOver) {
			for(Entity e : entities) {
				if (e instanceof Zombie && e.getX() == 0) {
					gameOver = true;
				}
			}	
		}
		return gameOver;
	}
	
	/**
	 * Check if the current round is over.
	 * The round is over if all instances of Zombie have died.
	 * 
	 * @return boolean True if the round is over.
	 */
	public boolean isRoundOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie) return false;
		}
		return true;
	}
	
	/**
	 * Spawn n Zombies at a random location.
	 * 
	 * @param n The number of zombies to spawn.
	 */
	public void spawnZombies(int n) {
		for (int i = 0; i < n; i ++) {
			entities.add(new Zombie(new Point(Tile.COLUMNS, new Random().nextInt(Tile.ROWS))));
		}
	}
	
	/**
	 * Whether a Moveable object will collide with game Entities.
	 * 
	 * @param m The Moveable object to check.
	 * @return boolean True if a collision has occurred.
	 */
	public boolean isCollision(Moveable m) {
		for(Entity e: entities) {
			// A collision will occur if the next position of Moveable is currently occupied.
			boolean willCollide = e.getX() == m.nextPosition().getX() && e.getY() == m.nextPosition().getY();
			// A collision occurred if two entities are on top of each other.
			boolean hasCollided = e.getX() == ((Entity) m).getX() && e.getY() == ((Entity) m).getY();
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
	
	/**
	 *
	 * 
	 * @return
	 */
	public int getSunPoints() {
		return sunPoints;
	}

	/**
	 * 
	 * 
	 * @param sunPoints
	 */
	public void setSunPoints(int sunPoints) {
		this.sunPoints = sunPoints;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public int getGameCounter() {
		return gameCounter;
	}

	/**
	 * 
	 * 
	 * @param gameCounter
	 */
	public void incrementGameCounter() {
		this.gameCounter++;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public LinkedList<Entity> getEntities() {
		return entities;
	}

	/**
	 * 
	 * 
	 * @param entities
	 */
	public void addEntities(LinkedList<Entity> entities) {
		this.entities.addAll(entities);
	}
	
	/**
	 * 
	 * 
	 * @param entities
	 */
	public void removeEntities(LinkedList<Entity> entities) {
		this.entities.removeAll(entities);
	}
	
	/**
	 * 
	 * 
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
	
}