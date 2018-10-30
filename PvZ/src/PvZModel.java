import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

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

	private Scanner reader;
	
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
	private boolean isOccupied(Point p) {
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
	private Point getLocation(String plantName) {
		System.out.println("Enter a location to spawn new " + plantName + ": ");
		reader = new Scanner(System.in);
		String input = reader.next().toUpperCase();
		// Ensure valid spawn location 
		Point p = gameBoard.isValidLocation(input);
		if (p == null) {
			return getLocation(plantName);
		}
		// Ensure location is not currently occupied by another Entity
		if (isOccupied(p)) {
			System.out.println("Location " + input + " is currently occupied.");
			return getLocation(plantName);
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
	private boolean isGameOver() {
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
	private boolean isRoundOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie) return false;
		}
		return true;
	}

	/**
	 * Get the next move by the player. 
	 * Ask whether they would like to purchase anything from store.
	 * If they would like to purchase something from store ask where they would like to spawn the purchase.
	 */
	private void nextMove() {
		System.out.println("Sun points: " + sunPoints);
		boolean isSunflowerPurchasable = sunPoints >= Sunflower.COST && Sunflower.isDeployable(gameCounter);
		boolean isPeaShooterPurchasable = sunPoints >= PeaShooter.COST && PeaShooter.isDeployable(gameCounter);
		if (!(isSunflowerPurchasable || isPeaShooterPurchasable)) {
			System.out.println("No store items deployable.");
		} else {
			// Print available items to purchase 
			System.out.println("Items available for purchase:");
			String sunflowerName = Sunflower.class.getName();
			if (isSunflowerPurchasable) {
				System.out.println("<" + sunflowerName + "> : " + Sunflower.COST + " Sun points");
			}
			String peaShooterName = PeaShooter.class.getName();
			if (isPeaShooterPurchasable) {
				System.out.println("<" + peaShooterName + "> : " + PeaShooter.COST + " Sun points");
			} 
			System.out.println("Press <Enter> to proceed without purchases");
			// Read from standard out
			reader = new Scanner(System.in);
			String input = reader.nextLine().toUpperCase();
			if (isSunflowerPurchasable && sunflowerName.toUpperCase().equals(input)) {
				sunPoints -= Sunflower.COST;
				entities.add(new Sunflower(getLocation(sunflowerName)));	
				Sunflower.setNextDeployable(gameCounter);
			} else if (isPeaShooterPurchasable && peaShooterName.toUpperCase().equals(input)) {
				sunPoints -= PeaShooter.COST;
				entities.add(new PeaShooter(getLocation(peaShooterName)));
				PeaShooter.setNextDeployable(gameCounter);
			} 
			else if(input.isEmpty()) {
				return;
			}
			else {
				System.out.println("Invalid input!");
				nextMove();
			}
		}
	}
	
	/**
	 * Spawn n Zombies at a random location.
	 * 
	 * @param n The number of zombies to spawn.
	 */
	private void spawnZombies(int n) {
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
	private boolean isCollision(Moveable m) {
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
	 * Game loop of Plant vs. Zombies.
	 */
	private void gameLoop() {
		gameBoard.print();
		spawnZombies(1);
		boolean isRoundOver = false;
		while(!isGameOver()) {
			gameBoard.clear(); // Clear board of Entities
			
			// Spawn new Entities
			LinkedList<Entity> tempEntities = new LinkedList<Entity>();
			nextMove(); // Get next move by user
			for(Entity e: entities) {
				if (e instanceof Shooter && ((Shooter) e).canShoot())  {
					// If PeaShooter can fire add new bullet to Entity list
					if (e instanceof PeaShooter) tempEntities.add(new Bullet(new Point(e.getX(), e.getY()), PeaShooter.DAMAGE));
					// If sunflower can fire add sun reward.
					else if (e instanceof Sunflower) sunPoints += Sun.REWARD;
				}
			}
			entities.addAll(tempEntities); // Add new Entities to Entities list
			
			// Update position of Moveable Entities
			for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
				Entity e = iter.next();
				// Ensure Entity is Moveable and is not waiting to be delete
				if (e instanceof Moveable) {
					Moveable m = ((Moveable) e);
					boolean isBullet = m instanceof Bullet;
					m.unlock(); // Unlock to allow update position on this game iteration
					if (!isCollision(m)) { 
						m.updatePosition(); // Update position if there is no collision
						// Remove bullet if domain is greater than game board columns
						if (isBullet && e.getX() >= GameBoard.COLUMNS) iter.remove();
					} else if (isBullet) iter.remove(); // Remove bullet on impact
				}
			}
			
			// Check for dead Entities
			tempEntities = new LinkedList<Entity>();
			boolean deathOccurred = false;
			for(Entity e: entities) {	
				if (e instanceof Alive) {	
					// Check if Entity is dead 
					if (((Alive) e).getHealth() <= 0) {
						System.out.println(e.getClass().getName() + " died");
						tempEntities.add(e);
						deathOccurred = true;
					} else {
						// Print health of Entity if still alive
						System.out.println(e.getClass().getName() + " health: " + ((Alive) e).getHealth());
					}
				}
			}
			entities.removeAll(tempEntities);
			
			// Add Entities to game board
			for(Entity e: entities) {
				gameBoard.addEntity(e);
			}
			
			gameBoard.print(); // Print game board
			// Check if round is over if and only if death occurred
			if (deathOccurred && isRoundOver()) {
				isRoundOver = true;
				break;
			} 
			gameCounter++; // Increase game counter
			// Add automatic welfare if payment period has elapsed 
			if (gameCounter % PAYMENT_PERIOD == 0) sunPoints += WELFARE;
		}
		
		if (isRoundOver) System.out.println("You beat the round"); 
		else System.out.println("You lost");
		reader.close();
	}
	
	/**
	 * Main  method.
	 * Initialize game and run game loop.
	 * 
	 * @param args The arguments from standard out.
	 */
	public static void main(String args[]) {
		PvZModel PvZ = new PvZModel();
		PvZ.gameLoop();
	}
		
}