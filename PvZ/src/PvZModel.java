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
public class PvZModel {

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
	 * Object that implements the board interface.
	 */
	private Board gameBoard;
	
	/**
	 * The period which sun points are automatically rewarded to balance (welfare)
	 */
	public static final int PAYMENT_PERIOD = 4;
	
	/**
	 * The sun points automatically deposited every payment period
	 */
	public static final int WELFARE = 25;
	
	public static final int INITIAL_BALANCE = 1000;
	
	/**
	 * Constructor.
	 */
	public PvZModel() {
		 entities = new LinkedList<Entity>();
		 sunPoints = INITIAL_BALANCE; 
		 gameBoard = new GameBoard();
		 gameCounter = 0;
	}	

	/**
	 * Check whether a position is occupied by another Entity excluding Bullet.
	 * 
	 * @param p The location to check.
	 * @return boolean True if position is currently occupied by another Entity excluding Bullet.
	 */
	public boolean isOccupied(Point p) {
		for(Entity e : entities) {
			if (!(e instanceof Bullet) && e.getX() == p.x && e.getY() == p.y) return true; 
		}
		return false;
	}

	/**
	 * Get the location to spawn a plant from input by the user.
	 * 
	 * @param entityName The name of the plant to spawn.
	 * @return Point The location to spawn a new plant.
	 */
	public Point getLocation(int x, int y) {
		// Ensure valid location on game board
		Point p = gameBoard.isValidLocation(x,y);
		if (p == null) {
			return p;
		}
		// Ensure location is not currently occupied by another Entity
		if (isOccupied(p)) {
			p = null;
			return p;
		}
		// Return valid spawn location
		return p;
	}

	/**
	 * Check if game is over (player has lost the game).
	 * Game is over if and only if a Zombie has traversed the board.
	 * 
	 * @return boolean True if game is over.
	 */
	public boolean isGameOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getX() == 0) return true;
		}
		return false;
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
			entities.add(new Zombie(new Point(GameBoard.COLUMNS, new Random().nextInt(GameBoard.ROWS))));
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
	public void setGameCounter(int gameCounter) {
		this.gameCounter = gameCounter;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Board getGameBoard() {
		return gameBoard;
	}

	/**
	 * 
	 * 
	 * @param gameBoard
	 */
	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;
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
	public void setEntities(LinkedList<Entity> entities) {
		this.entities = entities;
	}
	
}