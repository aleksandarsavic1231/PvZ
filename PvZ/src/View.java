import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;

/**
 * 
 * @author 
 * @version 5 Nov 18
 */
public class View extends JFrame{

	private static final long serialVersionUID = 1L;
	
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
	private JMenuItem Restart;
	//private JMenuItem Undo;

	public static final Color LIGHT_GREEN = new Color(0,255,51);
	public static final Color GREEN = new Color(0,153,0);
	public static final Color DARK_GREEN = new Color(0,102,0);
		
	public View(String title) {
		super(title);
		setResizable(false);
		//add tiles to grid panel
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(Tile.ROWS, Tile.COLUMNS));
		tiles = new JButton[Tile.ROWS][Tile.COLUMNS];
			
		Tile.iterateBoard((i, j) -> {
			tiles[i][j] = new JButton();
			tiles[i][j].setOpaque(true);
			
			tiles[i][j].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(0, 0, 0, 0)));
			//set background colors
			if((i + j) % 2 == 0) tiles[i][j].setBackground(LIGHT_GREEN);
			else tiles[i][j].setBackground(GREEN);		
			gridPanel.add(tiles[i][j]);
		});
			
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
		//Undo = new JMenuItem("Undo");
		Restart = new JMenuItem("Restart");
		Quit = new JMenuItem("Quit Game");
		//jm.add(Undo);
		jm.add(Restart);
		jm.add(Quit);
		bar.add(jm);
		this.setJMenuBar(bar);
		
		//initialize
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
	}
	
	public void addListener(ActionListener a) {
		Tile.iterateBoard((i, j) -> tiles[i][j].addActionListener(a));
		addButton.addActionListener(a);
		nextButton.addActionListener(a);
		sunflowerButton.addActionListener(a);
		peaShooterButton.addActionListener(a);
		//Undo.addActionListener(a);
		Restart.addActionListener(a);
		Quit.addActionListener(a);
	}
	
	public JMenuItem getQuitItem() {
		return Quit;
	}
	
	public JMenuItem getRestartItem() {
		return Restart;
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