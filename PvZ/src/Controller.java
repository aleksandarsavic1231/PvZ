import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

		
		
		if(e.getSource() instanceof JButton && !addPlant) {
			
			JButton button = (JButton)e.getSource();
			String buttonName = button.getText();
			
			
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

			
			}
		}
			
		else if(e.getSource() instanceof JButton && addPlant) {
			  
			  for (int i = 0; i < GameBoard.ROWS; i++) {
					
					for(int j = 0; j < GameBoard.COLUMNS; j++) {
						
						if(e.getSource() == tiles[i][j]) {
							
							Point p = new Point(i,j);
							
							if(model.getLocation(i,j) != null) {
								
								switch(plant) {
								
								case "Sunflower":
									JOptionPane.showMessageDialog(null, "Sunflower added!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
									
									tiles[i][j].setIcon(v.getSunflower());
									model.setSunPoints(model.getSunPoints()-Sunflower.COST);
									v.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));
									model.getEntities().add(new Sunflower(model.getLocation(i,j)));
									Sunflower.setNextDeployable(model.getGameCounter());
									break;
									
								case "PeaShooter":
									
									JOptionPane.showMessageDialog(null, "PeaShooter added!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
									
									tiles[i][j].setIcon(v.getPeashooter());
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
	
	}
	

}
