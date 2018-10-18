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
	
	public static final int INITIAL_BALANCE = 100;
	
	public PvZModel() {
		 entities = new LinkedList<Entity>();
		 sunPoints = INITIAL_BALANCE; 
		 gameBoard = new GameBoard();
		 gameCounter = 0;
	}	
	
	private Point getLocation(String entity) {
		System.out.println("Enter a location to spawn " + entity + ": ");
		reader = new Scanner(System.in);
		String input = reader.next();
		// TODO: Fix tight coupling of game board 
		return new Point(input.charAt(0) - 65, Character.getNumericValue(input.charAt(1)));
	}
	
	private boolean isGameOver() {
		// TODO: Make functional method to iterate over entities
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getX() == -1) return true;
		}
		return false;
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
		// Order of priority
		// TODO: Ask again if user enters invalid input (also need option to not buy anything)
		// TODO: Fix tight coupling between plant name, cost, and isDeployable
		// TODO: Make this method functional 
		// TODO: Decide whether a player can buy multiple plants each game iteration
		// Update isDeployable
		Sunflower.isDeployable(gameCounter);
		PeaShooter.isDeployable(gameCounter);
		// Check if user has sufficient balance to make purchase
		System.out.println("Sun points: " + sunPoints);
		if (sunPoints < Sunflower.COST && sunPoints < PeaShooter.COST) {
			System.out.println("Insuffient sunpoint balance.");
			sleepOneSecond();
			return;
		}
		// Print available items to purchase 
		System.out.println("Items available for purcahse:");
		if (sunPoints >= Sunflower.COST && Sunflower.isDeployable) {
			System.out.println("<Sunflower : " + Sunflower.COST + ">");

		}
		if (sunPoints >= PeaShooter.COST && PeaShooter.isDeployable) {
			System.out.println("<PeaShooter : " + PeaShooter.COST + ">");
		} 
		// Read from standard out
		reader = new Scanner(System.in);
		String input = reader.next().toUpperCase();
		switch(input) {
			case "SUNFLOWER":	
				if (sunPoints >= Sunflower.COST && Sunflower.isDeployable) {
					sunPoints -= Sunflower.COST;
					entities.add(new Sunflower(getLocation("Sunflower")));	
					PeaShooter.isDeployable = false;
				}
				break;
			case "PEASHOOTER":
				if (sunPoints >= PeaShooter.COST && PeaShooter.isDeployable) {
					sunPoints -= PeaShooter.COST;
					entities.add(new PeaShooter(getLocation("Peashooter")));
					PeaShooter.isDeployable = false;
				} 									
				break;
			default:
				System.out.println("Invalid input || Insuffient funds || No plants deployable");
				sleepOneSecond();
				break;
		}
	}
	
	private void spawnZombies(int n) {
		for (int i = 0; i < n; i ++) {
			entities.add(new Zombie(new Point(GameBoard.COLUMNS - 1, 0)));
		}
	}
	
	private boolean isCollision(Moveable m) {
		// TODO: Refractor / make functional
		boolean isCollision = false;
		for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
			// Ensure the current position of Entity is not the next move of Moveable
			// Ignore if Entity and Moveable are instances of Zombie
			// (Zombies may share next position)
			Entity e = iter.next();
			boolean isZombie = e instanceof Zombie;
			if ((!(isZombie && (m instanceof Zombie)) 
					&& e.getX() == m.nextPosition().getX() 
					&& e.getY() == m.nextPosition().getY())) {
				isCollision = true;
				// Zombie hit by bullet
				if (isZombie && m instanceof Bullet) {
					((Zombie) e).setHealth(((Bullet) m).getDamage());
					// Zombie should update position regardless of being hit by bullet 
					((Zombie) e).updatePosition();
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
					System.out.println(e.getClass().getName() + " has died!");
					iter.remove();
				}
			}
			gameBoard.print();
			gameCounter++;
			// Add automatic welfare if payment period has elapsed 
			if (gameCounter % PAYMENT_PERIOD == 0) sunPoints += WELFARE;
		}
		if (isGameOver()) System.out.println("Game over!");
		// TODO: Need to close reader
		// https://goo.gl/jJzzG3
	}
	
	public static void main(String args[]) {
		PvZModel PvZ = new PvZModel();
		PvZ.gameLoop();
	}
		
}
