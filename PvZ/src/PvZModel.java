import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class PvZModel {

	private ArrayList<Entity> entities;
  	
	private int sunpoints;
	
	private Board gameBoard;

	private Scanner reader;
	
	public PvZModel() {
		 entities = new ArrayList<Entity>();
		 sunpoints = 50;
		 gameBoard = new GameBoard();
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
			///entities.add(new Zombie(getInput()));
			
			for(Entity e : entities) {
				if (e instanceof Moveable) ((Moveable) e).updatePosition();
			}
				
			for(Entity e : entities) {
				gameBoard.addEntity(e.getX(), e.getY(), e.getLabel());
			}
			
			gameBoard.print();
		}
		// TODO: NEED TO CLOSE READER
	}
	
	public static void main(String args[]) {
		PvZModel PvZ = new PvZModel();
		PvZ.gameLoop();
	}
		
}
