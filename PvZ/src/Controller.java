import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Controller implements ActionListener{

	private View v;
	private PvZModel model;
	private JButton[][] tiles;
	private boolean addPlant = false;
	private String plant;
	
	public Controller(View v, PvZModel model) {
		
		this.v = v;
		this.model = model;
		
	}
	
	
	public void initController() {
		// dsadSA
		this.tiles = v.getTiles();
		v.getAddButton().addActionListener(this);
		v.getNextButton().addActionListener(this);
		v.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));

		for (int i = 0; i < GameBoard.ROWS; i++) {
			
			for(int j = 0; j < GameBoard.COLUMNS; j++) {
				
				JButton tile = tiles[i][j];
				tiles[i][j].addActionListener(this);
				Color original = tile.getBackground();
				tile.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseEntered(java.awt.event.MouseEvent evt) {
						tile.setBackground(v.getDarkGreen());
					}
					public void mouseExited(java.awt.event.MouseEvent evt) {
						tile.setBackground(original);
					}
				});
			}
		}
		v.getPeaShooterButton().addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e1) { 
				  
				  JOptionPane.showMessageDialog(null, "Please select a tile to insert the PeaShooter!", "Add PeaShooter", JOptionPane.INFORMATION_MESSAGE);
				  Window w = SwingUtilities.getWindowAncestor(v.getPeaShooterButton());
				   if (w != null) {
					      w.setVisible(false);
				   }
				   addPlant = true;
				   plant = "PeaShooter";
				   
			  }
				    
		});
		
		v.getSunflowerButton().addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e1) { 
				  
				  JOptionPane.showMessageDialog(null, "Please select a tile to insert the Sunflower", "Add Sunflower", JOptionPane.INFORMATION_MESSAGE);
				  Window w =SwingUtilities.getWindowAncestor(v.getSunflowerButton());
				 
				    if (w != null) {
				      w.setVisible(false);
				    }
				  addPlant = true;   
				  plant = "Sunflower";
				  
			  }
				    
		});
		
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {

		
		
		if(e.getSource() instanceof JButton) {
			
			JButton button = (JButton)e.getSource();
			String buttonName = button.getText();
			
			
			if(addPlant) {
				
				 for (int i = 0; i < GameBoard.ROWS; i++) {
						
						for(int j = 0; j < GameBoard.COLUMNS; j++) {
							
							if(e.getSource() == tiles[i][j]) {
								
								Point p = new Point(i,j);
								
								if(model.getLocation(i,j) != null) {
									
									switch(plant) {
									
									case "Sunflower":
										JOptionPane.showMessageDialog(null, "Sunflower added!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
										
										tiles[i][j].setIcon(Sunflower.IMAGE);
										model.setSunPoints(model.getSunPoints()-Sunflower.COST);
										v.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));
										model.getEntities().add(new Sunflower(model.getLocation(i,j)));
										Sunflower.setNextDeployable(model.getGameCounter());
										break;
										
									case "PeaShooter":
										
										JOptionPane.showMessageDialog(null, "PeaShooter added!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
										
										tiles[i][j].setIcon(PeaShooter.IMAGE);
										model.setSunPoints(model.getSunPoints()-PeaShooter.COST);
										v.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));
										model.getEntities().add(new PeaShooter(model.getLocation(i,j)));
										PeaShooter.setNextDeployable(model.getGameCounter());
										break;
									
									}
									
									addPlant = false;
									
								}
								else if(model.isOccupied(p)){
									JOptionPane.showMessageDialog(null, "This location is currently occupied!", "Error", JOptionPane.ERROR_MESSAGE);
								}
								else {
									
									JOptionPane.showMessageDialog(null, "This is not a valid location", "Error", JOptionPane.ERROR_MESSAGE);

								}
						
								
								
							}
							
							
						}
						
					}
				
			}
			
			
			switch(buttonName) {
			
			case "Add Plant":
				
				Object[] fields = {
						
						"Sunpoints: " + model.getSunPoints(), "\n",
						"Items available for purchase: ", "\n",
						v.getPeaShooterButton(), 
						v.getSunflowerButton(), "\n"
				
				};
				

				boolean isSunflowerPurchasable = model.getSunPoints() >= Sunflower.COST && Sunflower.isDeployable(model.getGameCounter());
				boolean isPeaShooterPurchasable = model.getSunPoints() >= PeaShooter.COST && PeaShooter.isDeployable(model.getGameCounter());

				if(!isSunflowerPurchasable) v.getSunflowerButton().setEnabled(false);
				if(!isPeaShooterPurchasable) v.getPeaShooterButton().setEnabled(false);
				
			
				JOptionPane.showConfirmDialog(null, fields, "Add Plant", JOptionPane.CANCEL_OPTION);
				
				break;
				
				
			case "Next Turn":	
					boolean isRoundOver = false;
					
					if(model.getGameCounter() == 1) model.spawnZombies(1);
					
					model.getGameBoard().clear(); // Clear board of Entities
					for (int i = 0; i < GameBoard.ROWS; i++) {
						
						for(int j = 0; j < GameBoard.COLUMNS; j++) {
							
							tiles[i][j].setIcon(null);
					
						}
					}
						
					// Spawn new Entities
					LinkedList<Entity> tempEntities = new LinkedList<Entity>();
					LinkedList<Entity> entities = model.getEntities();
					
					for(Entity entity: entities) {
						if (entity instanceof Shooter && ((Shooter) entity).canShoot())  {
							// If PeaShooter can fire add new bullet to Entity list
							if (entity instanceof PeaShooter) tempEntities.add(new Bullet(new Point(entity.getY(), entity.getX()), PeaShooter.DAMAGE));
							// If sunflower can fire add sun reward.
							else if (entity instanceof Sunflower) model.setSunPoints(model.getSunPoints()+Sun.REWARD);
						}
					}
						entities.addAll(tempEntities); // Add new Entities to Entities list
						
						// Update position of Moveable Entities
						for(ListIterator<Entity> iter = entities.listIterator(); iter.hasNext(); ) {
							Entity entity = iter.next();
							// Ensure Entity is Moveable and is not waiting to be delete
							if (entity instanceof Moveable) {
								Moveable m = ((Moveable) entity);
								boolean isBullet = m instanceof Bullet;
								m.unlock(); // Unlock to allow update position on this game iteration
								if (!model.isCollision(m)) { 
									m.updatePosition(); // Update position if there is no collision
									// Remove bullet if domain is greater than game board columns
									if (isBullet && entity.getX() >= GameBoard.COLUMNS) iter.remove();
								} else if (isBullet) iter.remove(); // Remove bullet on impact
							}
						}
						
						// Check for dead Entities
						tempEntities = new LinkedList<Entity>();
						boolean deathOccurred = false;
						for(Entity entity: entities) {	
							if (entity instanceof Alive) {	
								// Check if Entity is dead 
								if (((Alive) entity).getHealth() <= 0) {
									System.out.println(entity.getClass().getName() + " died");
									tempEntities.add(entity);
									deathOccurred = true;
								} else {
									// Print health of Entity if still alive
									System.out.println(entity.getClass().getName() + " health: " + ((Alive) entity).getHealth());
								}
							}
						}
						entities.removeAll(tempEntities);
						
						// Add Entities to game board
						for(Entity entity: entities) {
							model.getGameBoard().addEntity(entity);
						}
						
						for(Entity entity: model.getEntities()) {
							if (entity instanceof PeaShooter) tiles[entity.getX()][entity.getY()].setIcon(PeaShooter.IMAGE);
							if (entity instanceof Sunflower) tiles[entity.getX()][entity.getY()].setIcon(Sunflower.IMAGE);
							if (entity instanceof Zombie) tiles[entity.getY()][entity.getX()].setIcon(Zombie.IMAGE);
							if (entity instanceof Bullet) tiles[entity.getY()][entity.getX()].setIcon(Bullet.IMAGE);
						}
						
						// Check if round is over if and only if death occurred
						if (deathOccurred && model.isRoundOver()) {
							isRoundOver = true;
							break;
						} 
						model.setGameCounter(model.getGameCounter()+1);// Increase game counter
						// Add automatic welfare if payment period has elapsed 
						if (model.getGameCounter() % model.PAYMENT_PERIOD == 0) model.setSunPoints(model.getSunPoints()+model.WELFARE);
					
			
			
						//model.nextIteration();
						v.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));
						break;
			
			}
					
					
					
				
					
			
			
		}
			
	}
	

}
