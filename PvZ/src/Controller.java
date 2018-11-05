import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * 
 * 
 * @author
 * @version 5 Nov 18
 */
public class Controller implements ActionListener{

	/**
	 * 
	 */
	private View view;
	
	/**
	 * 
	 */
	private Model model;
	
	/**
	 * 
	 */
	private JButton[][] tiles;
	
	/**
	 * 
	 * @author kylehorne
	 */
	private static enum Plant {SUNFLOWER, PEASHOOTER};
	private Plant plant;
	
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		plant = null;
		init();
	}
	
	
	private void init() {
		view.setSunpointsLabel("Sunpoints: " + model.getSunPoints());
		// Add action listener to add plant button
		view.getAddButton().addActionListener(this);
		// Add action listener to next iteration button
		view.getNextButton().addActionListener(this);
		// Game board tiles
		this.tiles = view.getTiles();
		// Add actions listener to tiles
		Tile.iterateBoard((i, j) -> {
			JButton tile = tiles[i][j];
			tile.addActionListener(this);
			Color original = tile.getBackground();
			tile.addMouseListener(new java.awt.event.MouseAdapter() {
				
				public void mouseEntered(java.awt.event.MouseEvent e) {
					tile.setBackground(View.DARK_GREEN);
				}
				
				public void mouseExited(java.awt.event.MouseEvent e) {
					tile.setBackground(original);
				}
				
			});
		});
		// Add action listener to PeaShooter button
		view.getPeaShooterButton().addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) { 
				  JOptionPane.showMessageDialog(null, "Please select a tile to insert the PeaShooter!", "Add PeaShooter", JOptionPane.INFORMATION_MESSAGE);
				  Window w = SwingUtilities.getWindowAncestor(view.getPeaShooterButton());
				  if (w != null) w.setVisible(false);
				  plant = Plant.PEASHOOTER;	   
			  }
			    
		});
		// Add action listener to Sunflower button
		view.getSunflowerButton().addActionListener(new ActionListener() { 
			
			  public void actionPerformed(ActionEvent e1) { 
				  JOptionPane.showMessageDialog(null, "Please select a tile to insert the Sunflower", "Add Sunflower", JOptionPane.INFORMATION_MESSAGE);
				  Window w =SwingUtilities.getWindowAncestor(view.getSunflowerButton());
				  if (w != null) w.setVisible(false);
				  plant = Plant.SUNFLOWER;  
			  }
				    
		});
		// Add action listener to restart item in menu bar
		view.getRestartItem().addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) { 
				JOptionPane.showMessageDialog(null, "The game has restarted, enjoy!", "Restart", JOptionPane.INFORMATION_MESSAGE);
				model = new Model();  
				Tile.iterateBoard((i, j) -> {
					tiles[i][j].setIcon(null);
				});
				view.setSunpointsLabel("Sunpoints: " + model.getSunPoints());
			}
			    
		});
		// Add action listener to quit item in menu bar
		view.getQuitItem().addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent e) { 
				JOptionPane.showMessageDialog(null, " Thanks for playing!", "Quit", JOptionPane.INFORMATION_MESSAGE);	
				System.exit(0);	   
			}  
			
		});
	}
	
	private void addPlant() {
		Object[] fields = {	
				"Sunpoints: " + model.getSunPoints(), "\n",
				"Items available for purchase: ", "\n",
				view.getPeaShooterButton(), 
				view.getSunflowerButton(), "\n"
		};
		boolean isSunflowerPurchasable = model.getSunPoints() >= Sunflower.COST && Sunflower.isDeployable(model.getGameCounter());
		boolean isPeaShooterPurchasable = model.getSunPoints() >= PeaShooter.COST && PeaShooter.isDeployable(model.getGameCounter());
		if(!isSunflowerPurchasable) view.getSunflowerButton().setEnabled(false);
		if(!isPeaShooterPurchasable) view.getPeaShooterButton().setEnabled(false);
		if(isSunflowerPurchasable) view.getSunflowerButton().setEnabled(true);
		if(isPeaShooterPurchasable) view.getPeaShooterButton().setEnabled(true);
		JOptionPane.showConfirmDialog(null, fields, "Add Plant", JOptionPane.CANCEL_OPTION);
	}
	
	private void addEntityToView(Entity e) {
		int x = e.getX();
		int y = e.getY();
		if (0 <= x && x < Tile.COLUMNS && 0 <= y && y < Tile.ROWS) {
			JButton tile = tiles[y][x];
			if (e instanceof PeaShooter) tile.setIcon(PeaShooter.IMAGE);
			if (e instanceof Sunflower) tile.setIcon(Sunflower.IMAGE);
			if (e instanceof Zombie) tile.setIcon(Zombie.IMAGE);
			if (e instanceof Bullet) tile.setIcon(Bullet.IMAGE);
		}
	}
	
	private void nextTurn() {
		// Check if game or round is over
		if (model.isGameOver() || model.isRoundOver()) return;
		// Clear board of Entities
		Tile.iterateBoard((i, j) -> {
			tiles[i][j].setIcon(null);
		});
		// Spawn new Entities
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(Entity entity: model.getEntities()) {
			if (entity instanceof Shooter && ((Shooter) entity).canShoot())  {
				// If PeaShooter can fire add new bullet to Entity list
				if (entity instanceof PeaShooter) tempEntities.add(new Bullet(new Point(entity.getX(), entity.getY()), PeaShooter.DAMAGE));
				// If sunflower can fire add sun reward.
				else if (entity instanceof Sunflower) model.setSunPoints(model.getSunPoints()+Sun.REWARD);
			}
		}
		model.addEntities(tempEntities); // Add new Entities to Entities list	
		// Update position of Moveable Entities
		tempEntities = new LinkedList<Entity>();
		for(ListIterator<Entity> iter = model.getEntities().listIterator(); iter.hasNext(); ) {
			Entity entity = iter.next();
			// Ensure Entity is Moveable and is not waiting to be delete
			if (entity instanceof Moveable) {
				Moveable m = ((Moveable) entity);
				boolean isBullet = m instanceof Bullet;
				m.unlock(); // Unlock to allow update position on this game iteration
				if (!model.isCollision(m)) { 
					m.updatePosition(); // Update position if there is no collision
					// Remove bullet if domain is greater than game board columns
					if (isBullet && entity.getX() >= Tile.COLUMNS) iter.remove();
				} else if (isBullet) { 
					// Remove bullet on impact
					tempEntities.add(entity);
					iter.remove();
				} 
			}
		}
		model.removeEntities(tempEntities);	
		// Check for dead Entities
		tempEntities = new LinkedList<Entity>();
		for(Entity entity: model.getEntities()) {	
			if (entity instanceof Alive) {	
				// Check if Entity is dead 
				if (((Alive) entity).getHealth() <= 0) {
					System.out.println(entity.getClass().getName() + " died");
					tempEntities.add(entity);
				} else {
					// Print health of Entity if still alive
					System.out.println(entity.getClass().getName() + " health: " + ((Alive) entity).getHealth());
				}
			}
		}
		model.removeEntities(tempEntities);	
		// Add Entities to game board
		for(Entity entity: model.getEntities()) {
			addEntityToView(entity);
		}	
		model.incrementGameCounter();// Increase game counter
		// Add automatic welfare if payment period has elapsed 
		if (model.getGameCounter() % Model.PAYMENT_PERIOD == 0) model.setSunPoints(model.getSunPoints() + Model.WELFARE);
		//model.nextIteration();
		view.setSunpointsLabel("Sunpoints: " + model.getSunPoints());
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		// Add plant
		if(plant != null) {
			// Find the pressed tile
			Tile.iterateBoard((i, j) -> {
				if(e.getSource() == tiles[i][j] && !model.isOccupied(i, j)) {
					Point location = new Point(j, i);
					if (plant == Plant.SUNFLOWER) {
						JOptionPane.showMessageDialog(null, "Sunflower added!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
						tiles[i][j].setIcon(Sunflower.IMAGE);
						model.setSunPoints(model.getSunPoints()-Sunflower.COST);
						view.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));
						model.addEntity(new Sunflower(location));
						Sunflower.setNextDeployable(model.getGameCounter());
					} else if (plant == Plant.PEASHOOTER) {
						JOptionPane.showMessageDialog(null, "PeaShooter added!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
						tiles[i][j].setIcon(PeaShooter.IMAGE);
						model.setSunPoints(model.getSunPoints() - PeaShooter.COST);
						view.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));
						model.addEntity(new PeaShooter(location));
						PeaShooter.setNextDeployable(model.getGameCounter());
					} 	
					plant = null;
				} 		
			});
		}
		JButton button = (JButton)e.getSource();
		switch(button.getText()) {
			case "Add Plant":
				addPlant();
				break;
			case "Next Turn":
				nextTurn();
				break;
		}
	}
	
}