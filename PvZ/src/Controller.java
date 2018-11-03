import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Controller implements ActionListener{

	private View v;
	private PvZModel model;
	
	
	public Controller(View v, PvZModel model) {
		
		this.v = v;
		this.model = model;
		
	}
	
	
	public void initController() {
		
		v.setSunpointsLabel("Sunpoints: "+String.valueOf(model.getSunPoints()));
		v.getAddButton().addActionListener(this);
		v.getNextButton().addActionListener(this);
		v.getSunflowerButton().addActionListener(this);
		v.getPeashooterButton().addActionListener(this);
		
		
//		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
//		    public void mouseEntered(java.awt.event.MouseEvent evt) {
//		        jButton1.setBackground(Color.GREEN);
//		    }
//		    public void mouseExited(java.awt.event.MouseEvent evt) {
//		        jButton1.setBackground(UIManager.getColor("control"));
//		    }
//		});
		
	}




	@Override
	public void actionPerformed(ActionEvent e) {

		Object[] fields = {
				
				"Sunpoints: " + model.getSunPoints(), "\n",
				"Items available for purchase: ", "\n",
				v.getSunflowerButton(), 
				v.getPeashooterButton(), "\n"
		
		};

		if(e.getSource() instanceof JButton) {
			
			JButton button = (JButton)e.getSource();
			String buttonName = button.getText();
			
			
			switch(buttonName) {
			
			case "Add Plant":
				
				
				boolean isSunflowerPurchasable = model.getSunPoints() >= Sunflower.COST && Sunflower.isDeployable(model.getGameCounter());
				boolean isPeaShooterPurchasable = model.getSunPoints() >= PeaShooter.COST && PeaShooter.isDeployable(model.getGameCounter());

				if(!isSunflowerPurchasable) v.getSunflowerButton().setEnabled(false);
				if(!isPeaShooterPurchasable) v.getPeashooterButton().setEnabled(false);
				
				JOptionPane.showConfirmDialog(null, fields, "Add Plant", JOptionPane.CANCEL_OPTION);


				
				
		
			
			}
		
		}
	}
	
	
	

}
