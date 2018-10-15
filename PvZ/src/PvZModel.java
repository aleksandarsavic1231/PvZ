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
	public int turnCounter;

	private Board gameBoard;

	private Scanner reader;
	
	/**
	 * The period which sun points are automatically rewarded to balance.
	 */
	public static final int PAYMENT_PERIOD = 4;
	
	public PvZModel() {
		 entities = new ArrayList<Entity>();
		 sunpoints = 50; // Starting balance
		 gameBoard = new GameBoard();
		 turnCounter = 0;
	}	
	
	private Point getLocation(String entity) {
		System.out.println("Enter a location to spawn" + entity + ": ");
		// Read from standard out 
		reader = new Scanner(System.in);
		String input = reader.next();
		Point location = new Point(Character.getNumericValue(input.charAt(1)), input.charAt(0) - 65);
		return location;
	}
	
	private boolean isGameOver() {
		for(Entity e : entities) {
			if (e instanceof Zombie && e.getX() == -1) return true;
		}
		return false;
	}
	
	private void buyPlant() {
		// TODO: Fix tight coupling between plant name and cost
		// TODO: Maybe make this method functional 
		// TODO: Decide whether a player can buy multiple plants each game iteration
		// Check if user has sufficient balance to make purchase
		System.out.println("Sunpoints: " + sunpoints);
		if (sunpoints < Sunflower.COST && sunpoints < PeaShooter.COST) {
			System.out.println("Insuffient sunpoint balance.");
			return;
		}
		// Print available items to purchase 
		System.out.println("Items available for purcahse:");
		if (sunpoints < Sunflower.COST) {
			System.out.println("< Sunflower : " + Sunflower.COST + " >");
		}
		if (sunpoints < PeaShooter.COST) {
			System.out.println("< PeaShooter : " + Sunflower.COST + " >");
		} 
		// Read from standard out
		reader = new Scanner(System.in);
		String input = reader.next().toUpperCase();
		switch(input) {
			case "SUNFLOWER":	
				if (sunpoints < Sunflower.COST) {
					sunpoints -= Sunflower.COST;
					entities.add(new Sunflower(getLocation("Sunflower")));	
				}
				break;
			case "PEASHOOTER":
				if (sunpoints < PeaShooter.COST) {
					sunpoints -= PeaShooter.COST;
					entities.add(new PeaShooter(getLocation("Peashooter")));
				} 									
				break;
		}
	}
	
	private void gameLoop() {
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
			
			turnCounter++;
			gameBoard.print();
			
			if (turnCounter % PAYMENT_PERIOD == 0) {
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
