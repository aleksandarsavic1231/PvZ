import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class PvZModel {

	/**
	 * Spawned entities. 
	 */
	private ArrayList<Entity> entities;
  	
	/**
	 * Game balance.
	 */
	private int sunpoints;
	
	/**
	 * Current game iteration.
	 */
	public int gameCounter;

	private Board gameBoard;

	private Scanner reader;
	
	/**
	 * The period which sun points are automatically rewarded to balance.
	 */
	public static final int PAYMENT_PERIOD = 4;
	
	public PvZModel() {
		 entities = new ArrayList<Entity>();
		 sunpoints = 500; // Starting balance
		 gameBoard = new GameBoard();
		 gameCounter = 0;
	}	
	
	private Point getLocation(String entity) {
		System.out.println("Enter a location to spawn " + entity + ": ");
		// Read from standard out 
		reader = new Scanner(System.in);
		String input = reader.next();
		Point location = new Point(Character.getNumericValue(input.charAt(1)), input.charAt(0) - 65);
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
		System.out.println("Sunpoints: " + sunpoints);
		if (sunpoints < Sunflower.COST && sunpoints < PeaShooter.COST) {
			System.out.println("Insuffient sunpoint balance.");
			sleepOneSecond();
			return;
		}
		// Print available items to purchase 
		System.out.println("Items available for purcahse:");
		if (sunpoints >= Sunflower.COST && Sunflower.isDeployable) {
			System.out.println("<Sunflower : " + Sunflower.COST + ">");

		}
		if (sunpoints >= PeaShooter.COST && PeaShooter.isDeployable) {
			System.out.println("<PeaShooter : " + PeaShooter.COST + ">");
		} 
		// Read from standard out
		reader = new Scanner(System.in);
		String input = reader.next().toUpperCase();
		switch(input) {
			case "SUNFLOWER":	
				if (sunpoints >= Sunflower.COST && Sunflower.isDeployable) {
					sunpoints -= Sunflower.COST;
					entities.add(new Sunflower(getLocation("Sunflower")));	
					PeaShooter.isDeployable = false;
				}
				break;
			case "PEASHOOTER":
				if (sunpoints >= PeaShooter.COST && PeaShooter.isDeployable) {
					sunpoints -= PeaShooter.COST;
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
	
	private void gameLoop() {
		// Order of priority
		// TODO: Fix location bug
		// TODO: Spawn zombies 
		// TODO: Spawn bullets 
		// TODO: Instances of moveable need to update position
		// TODO: Check if the round is over
		gameBoard.print();
		while (!isGameOver()) {
			gameBoard.clear();
			buyPlant();
			
			for(Entity e : entities) {
				gameBoard.addEntity(e.getX(), e.getY(), e.getLabel());
			}
			
			gameCounter++;
			gameBoard.print();
			
			if (gameCounter % PAYMENT_PERIOD == 0) {
				sunpoints += 25;
			}
		}
		// TODO: Need to close reader
	}
	
	public static void main(String args[]) {
		PvZModel PvZ = new PvZModel();
		PvZ.gameLoop();
	}
		
}
