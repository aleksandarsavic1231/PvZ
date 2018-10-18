import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

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
	
	public static final int INITIAL_BALANCE = 200;
	
	public PvZModel() {
		 entities = new LinkedList<Entity>();
		 sunPoints = INITIAL_BALANCE; 
		 gameBoard = new GameBoard();
		 gameCounter = 0;
	}	
	
	private Entity isOccupied(Point p) {
		for(Entity e : entities) {
			if (e.getX() == p.x && e.getY() == p.y) return e; 
		}
		return null;
	}
	
	private Point getLocation(String entityName) {
		// TODO: Fix tight coupling of game board 
		System.out.println("Enter a location to spawn new " + entityName + ": ");
		reader = new Scanner(System.in);
		String input = reader.next();
		int x = input.charAt(0) - 65;
		int y = Character.getNumericValue(input.charAt(1));
		// Ensure valid input
		if (input.length() != 2 || !(x <= 0 && x <= GameBoard.COLUMNS && 0 <= y && y <= GameBoard.ROWS)) {
			System.out.println("Invalid spawn location.");
			return getLocation(entityName);
		}
		Point p = new Point(x, y);
		// Ensure location is not currently occupied
		Entity e = isOccupied(p);
		if (e != null) {
			System.out.println("Location " + input + " currently occupied by " + e.getClass().getName() + ".");
			return getLocation(entityName);
		}
		// Return spawn location
		return p;
	}
	
	private boolean isGameOver() {
		// TODO: Make functional method to iterate over entities
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getX() == -1) return true;
		}
		return false;
	}
	
	private boolean isRoundOver() {
		// TODO: Make functional method to iterate over entities
		for(Entity e : entities) {
			if (e instanceof Zombie) return false;
		}
		return true;
	}
	
	private void sleepOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void nextMove() {
		// TODO: Make functional 
		// Update isDeployable
		Sunflower.isDeployable(gameCounter);
		PeaShooter.isDeployable(gameCounter);	
		System.out.println("Sun points: " + sunPoints);
		// Check for purchasable items
		boolean isSunflowerPurchasable = sunPoints >= Sunflower.COST && Sunflower.isDeployable;
		boolean isPeaShooterPurchasable = sunPoints >= PeaShooter.COST && PeaShooter.isDeployable;
		if (!(isSunflowerPurchasable && isPeaShooterPurchasable)) {
			System.out.println("No store items deployable.");
		} else {
			// Print available items to purchase 
			System.out.println("Items available for purcahse:");
			String sunflowerName = Sunflower.class.getName();
			if (isSunflowerPurchasable) {
				System.out.println("<" + sunflowerName + " : " + Sunflower.COST + " Sun points>");
			}
			String peaShooterName = PeaShooter.class.getName();
			if (isPeaShooterPurchasable) {
				System.out.println("<" + peaShooterName + " : " + PeaShooter.COST + " Sun points>");
			} 
			// Read from standard out
			reader = new Scanner(System.in);
			String input = reader.next().toUpperCase();
			if (isSunflowerPurchasable && sunflowerName.toUpperCase().equals(input)) {
				sunPoints -= Sunflower.COST;
				entities.add(new Sunflower(getLocation(sunflowerName)));	
				Sunflower.isDeployable = false;
			} else if (isPeaShooterPurchasable && peaShooterName.toUpperCase().equals(input)) {
				sunPoints -= PeaShooter.COST;
				entities.add(new PeaShooter(getLocation(peaShooterName)));
				PeaShooter.isDeployable = false;
			} else {
				System.out.println("Invalid input!");
				nextMove();
			}
		}
		sleepOneSecond();
	}
	
	private void spawnZombies(int n) {
		for (int i = 0; i < n; i ++) {
			entities.add(new Zombie(new Point(GameBoard.COLUMNS - 1, 0)));
		}
	}
	
	private boolean isCollision(Moveable m) {
		// TODO: Make functional 
		boolean isCollision = false;
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
			// Ensure the current position of Entity is not the next move of Moveable
			// Ignore if Entity and Moveable are instances of Zombie
			// (Zombies may share next position)
			Entity e = iter.next();
			boolean isZombie = e instanceof Zombie;
			if ((!(isZombie && (m instanceof Zombie)) 
					&& ((e.getX() == m.nextPosition().getX() && e.getY() == m.nextPosition().getY()) 
					|| (e.getX() == m.nextPosition().getX() && e.getY() == m.nextPosition().getY())))) {
				isCollision = true;
				// Zombie hit by bullet
				if (isZombie && m instanceof Bullet) {
					((Zombie) e).setHealth(((Bullet) m).getDamage());
					// Zombie should update position regardless of being hit by bullet 
					if (isCollision((Moveable) e)) {
						((Zombie) e).updatePosition();
					}
					break;
				}
				// Zombie collided with plant 
				if ((e instanceof PeaShooter || e instanceof Sunflower) && m instanceof Zombie) {
					((Alive) e).setHealth(Zombie.DAMAGE);				
					break;
				}
			}
		}
		return isCollision;
	}

	private void gameLoop() {
		// TODO: Player can not add plant on top of plant
		// TODO: Spawn zombies at random intervals 
		// TODO: Make multiple rounds
		// TODO: Change entity label to static 
		// TODO: Add Java doc comments
		// TODO: Add tests, update UML diagram, update README.md
		gameBoard.print();
		spawnZombies(1);
		while (!isGameOver()) {
			gameBoard.clear();
			nextMove();
			boolean deathOccurred = false;
			for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
				Entity e = iter.next();
				// Unlock Moveable entity
				if (e instanceof Moveable) ((Moveable) e).unlock();
				// Add entity to current game board
				gameBoard.addEntity(e);
				// Check if Shooter can fire 
				if (e instanceof Shooter && ((Shooter) e).canShoot())  {
					// Instantiate new bullet if Entity is instance of PeaShooter
					if (e instanceof PeaShooter) {
						iter.add(new Bullet(new Point(e.getX() + 1, e.getY()), PeaShooter.DAMAGE));
					}
					// Increase sun points if Entity is instance of Sunflower
					if (e instanceof Sunflower) {
						sunPoints += Sun.REWARD;
					}
				}
				// Update location of entity if instance of Moveable	
				if (e instanceof Moveable) {
					if (!isCollision((Moveable) e)) {	
						// Lock Moveable entity from moving again during current game iteration
						((Moveable) e).updatePosition();
					} else if (e instanceof Bullet) {
						// Remove bullet on impact
						iter.remove();
					}
				}
				// Check for dead entities
				if (e instanceof Alive && ((Alive) e).getHealth() <= 0) {
					System.out.println(e.getClass().getName() + " has died.");
					gameBoard.removeEntity(e);
					iter.remove();
					deathOccurred = true;
				}
			}
			gameBoard.print();
			// Check for end of round only if instance of Alive died during current game iteration.
			if (deathOccurred && isRoundOver()) break;
			gameCounter++;
			// Add automatic welfare if payment period has elapsed 
			if (gameCounter % PAYMENT_PERIOD == 0) sunPoints += WELFARE;
		}
		if (isRoundOver()) System.out.println("You beat the round."); 
		if (isGameOver()) System.out.println("Game over.");
		// TODO: Need to close reader
		// https://goo.gl/jJzzG3
	}
	
	public static void main(String args[]) {
		PvZModel PvZ = new PvZModel();
		PvZ.gameLoop();
	}
		
}
