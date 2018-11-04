import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class View extends JFrame{

		//components
		private JButton[][] tiles;
		private JButton addButton;
		private JButton nextButton;
		private Container contentPane;
		private JPanel bottomPanel;
		private JPanel buttonPanel;
		private JPanel labelPanel;
		private JPanel gridPanel;
		private JLabel sunpointsLabel;
		private JTextField plantName;
		private JButton sunflowerButton, peaShooterButton;
		private JMenuBar bar;
	   	private JMenu jm;
	    private JMenuItem Quit;
	    private JMenuItem PlayAgain;
		private JMenuItem Undo;


		//images
		//private ImageIcon zombie = new ImageIcon("Zombie_PvZ-2.png");
		//private ImageIcon sunflower = new ImageIcon("sunflower.png");
		//private ImageIcon peashooter = new ImageIcon("Peashooter_2.png");
		
		//colors
		private Color lightGreen = new Color(0,255,51);
		private Color green = new Color(0,153,0);
		private Color darkGreen = new Color(0,102,0);
		
		public View(String title) {
		
			super(title);
			
			//add tiles to grid panel
			gridPanel = new JPanel();
			gridPanel.setLayout(new GridLayout(GameBoard.ROWS, GameBoard.COLUMNS));
			tiles = new JButton[GameBoard.ROWS][GameBoard.COLUMNS];
			
			for (int i = 0; i < GameBoard.ROWS; i++) {
				
				for(int j = 0; j < GameBoard.COLUMNS; j++) {
					
					tiles[i][j] = new JButton();
					tiles[i][j].setOpaque(true);
					
					tiles[i][j].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(0, 0, 0, 0)));
					//set background colors
					if((i + j) % 2 == 0) tiles[i][j].setBackground(lightGreen);
					else tiles[i][j].setBackground(green);		
					gridPanel.add(tiles[i][j]);
				}
				
			}
			
			
			//add label
			sunpointsLabel = new JLabel("Sunpoints: ");
			sunpointsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			//add label Panel
			labelPanel = new JPanel();
			labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
			labelPanel.add(sunpointsLabel);
			sunpointsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			
			//add buttons
			addButton = new JButton("Add Plant");
			addButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			nextButton = new JButton("Next Turn");
			nextButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			//add button panel
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			buttonPanel.add(addButton);
			buttonPanel.add(nextButton);
			addButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
			nextButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 500, 10, 0));
			
			
			//add bottom panel
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
			bottomPanel.add(labelPanel);
			bottomPanel.add(buttonPanel);
			buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
			labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			bottomPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
			
			//TextField
			plantName = new JTextField();

			
			//plant buttons
			sunflowerButton = new JButton("Sunflower");
			peaShooterButton = new JButton("PeaShooter");
			
			//set contentPane
			contentPane = this.getContentPane();
			contentPane.add(gridPanel, BorderLayout.CENTER);
			contentPane.add(bottomPanel, BorderLayout.PAGE_END);
			
			bar = new JMenuBar();
	        	jm = new JMenu("Menu");
	        	Undo = new JMenuItem("Play Again");
	        	PlayAgain = new JMenuItem("Play Again");
	        	Quit = new JMenuItem("Quit Game");
	        	jm.add(Undo);
	        	jm.add(PlayAgain);
	        	jm.add(Quit);
	        	bar.add(jm);

			//initialize
			this.setSize(800,600);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
		
		}
	
		public void addListener(ActionListener a){
			for (int i=0; i < GameBoard.ROWS; i++) {
				for (int j=0; j < GameBoard.COLUMNS; j++){
					tiles[i][j].addActionListener(a);
				}
			}
			addButton.addActionListener(a);
			nextButton.addActionListener(a);
			sunflowerButton.addActionListener(a);
			peaShooterButton.addActionListener(a);
			Undo.addActionListener(a);
			PlayAgain.addActionListener(a);
			Quit.addActionListener(a);

		}

		public JButton[][] getTiles() {
			return tiles;
		}

		public JButton getAddButton() {
			return addButton;
		}

		public JButton getNextButton() {
			return nextButton;
		}

		public Color getLightGreen() {
			return lightGreen;
		}

		public Color getGreen() {
			return green;
		}

		public Color getDarkGreen() {
			return darkGreen;
		}

		public JTextField getPlantName() {
			return plantName;
		}

		public JLabel getSunpointsLabel() {
			return sunpointsLabel;
		}


		public void setSunpointsLabel(String text) {
			this.sunpointsLabel.setText(text);
		}


		public JButton getSunflowerButton() {
			return sunflowerButton;
		}


		public JButton getPeaShooterButton() {
			return peaShooterButton;
		}
		
}
