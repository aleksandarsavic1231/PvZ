import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PvZModel {

	/**
	 * Spawned entities. 
	 */
	private ArrayList<Entity> entities;
  	
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
	
	public static final int INITIAL_BALANCE = 500;
	
	public PvZModel() {
		 entities = new ArrayList<Entity>();
		 sunPoints = INITIAL_BALANCE; 
		 gameBoard = new GameBoard();
		 gameCounter = 0;
	}	
	
	private Point getLocation(String entity) {
		System.out.println("Enter a location to spawn " + entity + ": ");
		// Read from standard out 
		reader = new Scanner(System.in);
		String input = reader.next();
		// TODO: Fix tight coupling of game board 
		Point location = new Point(input.charAt(0) - 65, Character.getNumericValue(input.charAt(1)));
		return location;
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

	private void buyPlant() {
		// Order of priority
		// TODO: Ask again if user enters invalid input (also need option to not buy anything)
		// TODO: Fix tight coupling between plant name, cost, and isDeployable
		// TODO: Make this method functional 
		// TODO: Decide whether a player can buy multiple plants each game iteration
		// Update isDeployable
		Sunflower.isDeployable(gameCounter);
		PeaShooter.isDeployable(gameCounter);
		// Check if user has sufficient balance to make purchase
		System.out.println("sunPoints: " + sunPoints);
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
			entities.add(new Zombie(new Point(GameBoard.COLUMNS - 1, new Random().nextInt(GameBoard.ROWS))));
		}
	}
	
	private boolean isCollision(Moveable m) {
		for (Entity e : entities) {
			// Check if next move by entity is same as Moveable
			// Ensure entity is not the same class as Moveable 
			// This would occur when two entities are on top of each other
			// Next position would be the same so we can ignore
			if (e.getClass() != m.getClass() && m.nextPosition().getX() == e.getX() && m.nextPosition().getY() == e.getY()) {
				return true;
			}
		}
		return false;
	}
	
	private void gameLoop() {
		// Order of priority
		// TODO: Entities take damage
		// TODO: Player can not add plant on plant
		// TODO: Spawn zombies at random intervals 
		// TODO: Make multiple rounds
		// TODO: Check if the round is over
		// TODO: Change entity label to static 
		// TODO: Add test, update UML diagram, update README.md
		gameBoard.print();
		spawnZombies(5); 
		while (!isGameOver()) {
			gameBoard.clear();
			// Get next move
			buyPlant();
			
			ArrayList<Bullet> newBullets = new ArrayList<Bullet>();
			for(Entity e : entities) {
				gameBoard.addEntity(e);
				// Can entity shoot
				if (e instanceof Shooter && ((Shooter) e).canShoot())  {
					if (e instanceof PeaShooter) {
						// Instantiate new bullet with location in front of PeaShooter
						newBullets.add(new Bullet(new Point(e.getX() + 1, e.getY()), PeaShooter.DAMAGE));
					} else if (e instanceof Sunflower) {
						// Increase sun points
						sunPoints += Sun.WORTH;
					}
				}
				// Update location of entity if instance of Moveable 
				if (e instanceof Moveable && !isCollision((Moveable) e)) ((Moveable) e).updatePosition();
			}
			// Add new bullets to entity list
			entities.addAll(newBullets);
			
			gameBoard.print();
			gameCounter++;
			
			// Add automatic welfare
			if (gameCounter % PAYMENT_PERIOD == 0) {
				sunPoints += WELFARE;
			}
		}
		System.out.println("Game is over!");
		// TODO: Need to close reader
		// https://goo.gl/jJzzG3
	}
	
	public static void main(String args[]) {
		PvZModel PvZ = new PvZModel();
		PvZ.gameLoop();
	}
		
}
