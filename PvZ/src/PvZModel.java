import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class PvZModel {

	private ArrayList<Entity> entities;
  	
	private int sunpoints;
	
	public int turnCounter;
	
	private Board gameBoard;

	private Scanner reader;
	
	public PvZModel() {
		 entities = new ArrayList<Entity>();
		 sunpoints = 50;
		 gameBoard = new GameBoard();
		 turnCounter = 0;
	}	
	
	private Point getInput() {
		System.out.println("Enter a location:");
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
	
	@SuppressWarnings("unused")
	private void buy() {
		
		System.out.println("Enter the plant you would like to purchase:");
		reader = new Scanner(System.in);
		String input = reader.next().toLowerCase();	
		
		switch(input) {
		
		case "sunflower":
			
			if(sunpoints >= Sunflower.COST && Sunflower.RECHARGE_TIME == 0) {
				
				sunpoints -= Sunflower.COST;
				System.out.println("You just purchased a Sunflower!");
				entities.add(new Sunflower(getInput()));
			}
			
			else {
				
				System.out.println("You do not have enough sunpoints to purchase this!");
			}
			
			
			break;
			
		case "peashooter":
			
			if(sunpoints > PeaShooter.COST && PeaShooter.RECHARGE_TIME == 0) {
				

				sunpoints -= PeaShooter.COST;
				System.out.println("You just purchased a PeaShooter!");
				entities.add(new PeaShooter(getInput()));			
			}
			
			else {
				
				System.out.println("You do not have enough sunpoints to purchase this!");
				
			}
			
			break;
			
		default: 
			
			System.out.println("Next turn!");
			break;
		
		}
		
		
		
	}
	
	private void gameLoop() {
		// TODO: CHECK IF USER HAS BEAT ROUND 
		// TODO: NEED TO SPAWN ZOMBIES
		// TODO: NEED TO ASK USER WHAT THE WANT TO BUY
		// TODO: NEED PAYMENT SYSTEM
		// TODO: LOCATION BUG
		gameBoard.print();
		while (!isGameOver()) {
			gameBoard.clear();

			System.out.println("Sunpoints: " + sunpoints);
			buy();
			
			for(Entity e : entities) {
				if (e instanceof Moveable) ((Moveable) e).updatePosition();
				gameBoard.addEntity(e.getX(), e.getY(), e.getLabel());
				
				
				
			}
			turnCounter++;
			gameBoard.print();
		}
		// TODO: NEED TO CLOSE READER
	}
	
	public static void main(String args[]) {
		PvZModel PvZ = new PvZModel();
		PvZ.gameLoop();
	}
		
}
