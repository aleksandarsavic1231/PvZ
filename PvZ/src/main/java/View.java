import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.*;

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
import javax.swing.border.Border;

/**
 * The View of the PvZ game.
 * 
 * @author aleksandarsavic1231
 * @version 16 Nov 18
 */
public class View extends JFrame implements Listener {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The tiles making up the PvZ Board.
	 */
	private JButton[][] tiles;
	
	/**
	 * The sun points balance label.
	 */
	private JLabel sunPointsLabel;
	
	/**
	 * The buttons to add a Plant to PvZ board.
	 */
	private JButton addPeaShooterButton, addSunflowerButton, addWallnutButton;
		
	/**
	 * The PvZ model.
	 */
	private Model model;
	
	/**
	 * The View width.
	 */
	public static final int WIDTH = 1000;
	
	/** 
	 * The View height.
	 */
	public static final int HEIGHT = 600;

	/**
	 * Custom colors used on View.
	 */
	public static final Color LIGHT_GREEN = new Color(0,255,51);
	public static final Color GREEN = new Color(0,153,0);
	public static final Color DARK_GREEN = new Color(0,102,0);
		
	/**
	 * Constructor.
	 */
	public View() {
		super("Plant vs. Zombies");
		// Initialize model
		model = new Model();
		// Initialize content
		getContentPane().add(addBoard(), BorderLayout.CENTER);
		getContentPane().add(addFooter(), BorderLayout.PAGE_END);
		setJMenuBar(addMenuBar());
		// Subscribe to model
		model.addActionListener(this);
		// Customize frame
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	/**
	 * Instantiate the menu bar.
	 * 
	 * @return JMenuBar MenuBar containing the PvZ menu.
	 */
	private JMenuBar addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
 
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(initController(Action.RESTART_GAME, null));
		
		JMenuItem quit = new JMenuItem("Quit Game");
		// Brute force quit
		// Will need to change for future milestones as we will need to store the model state 
		quit.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				System.exit(0);  
			}  
		});
		
		menu.add(restart);
		menu.add(quit);
		menuBar.add(menu);

		return menuBar;
	}
	
	/**
	 * Instantiate the PvZ board.
	 * 
	 * @return JPanel Panel containing the PvZ board.
	 */
	private JPanel addBoard() {		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(Board.ROWS, Board.COLUMNS));
		tiles = new JButton[Board.ROWS][Board.COLUMNS];
		
		// Initialize tiles	
		Board.iterate((i, j) -> {
			tiles[i][j] = new JButton();
			tiles[i][j].addActionListener(initController(Action.TILE_CLICKED, new Point(j, i)));
			tiles[i][j].setOpaque(true); // Required for OSX
			tiles[i][j].setBorderPainted(false);
			// Set tile color
			Color original;
			if((i + j) % 2 == 0) original = LIGHT_GREEN;
			else original = GREEN;	
			tiles[i][j].setBackground(original);
			boardPanel.add(tiles[i][j]);
			// Add mouse listener to tile
			// Mouse listener is independent of model state
			tiles[i][j].addMouseListener(new MouseAdapter() {
				
				public void mouseEntered(MouseEvent e) {
					tiles[i][j].setBackground(View.DARK_GREEN);
				}
				
				public void mouseExited(MouseEvent e) {
					tiles[i][j].setBackground(original);
				}
				
			});
		});
		return boardPanel;	
	}
	
	/**
	 * Instantiate the footer of PvZ frame.
	 * 
	 * @return JPanel Panel containing the footer.
	 */
	private JPanel addFooter() {
		Border defaultBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		
		sunPointsLabel = new JLabel();
		sunPointsLabel.setBorder(defaultBorder);
		sunPointsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		ImageIcon peashooterLogo = new ImageIcon("src/main/resources/peaIcon.png");
		addPeaShooterButton = new JButton(peashooterLogo);
		addPeaShooterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addPeaShooterButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addPeaShooterButton.addActionListener(initController(Action.TOGGLE_PEASHOOTER, null));
		
		ImageIcon sunflowerLogo = new ImageIcon("src/main/resources/sunFlowerIcon.png");
		addSunflowerButton = new JButton(sunflowerLogo);
		addSunflowerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addSunflowerButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addSunflowerButton.addActionListener(initController(Action.TOGGLE_SUNFLOWER, null));
		
		ImageIcon wallnutLogo = new ImageIcon("src/main/resources/wallnutIcon.png");
		addWallnutButton = new JButton(wallnutLogo);
		addWallnutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addWallnutButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addWallnutButton.addActionListener(initController(Action.TOGGLE_WALLNUT, null));
		
		JButton nextIterationButton = new JButton("Next Iteration");
		nextIterationButton.setBorder(defaultBorder);
		nextIterationButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextIterationButton.addActionListener(initController(Action.NEXT_ITERATION, null));
		
		JButton undoButton = new JButton("Undo");
		undoButton.setBorder(defaultBorder);
		undoButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		undoButton.addActionListener(initController(Action.UNDO_BUTTON, null));
		
		JButton redoButton = new JButton("Redo");
		redoButton.setBorder(defaultBorder);
		redoButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		redoButton.addActionListener(initController(Action.REDO_BUTTON, null));
			
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(addPeaShooterButton);
		buttonPanel.add(addSunflowerButton);
		buttonPanel.add(addWallnutButton);
		buttonPanel.add(nextIterationButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 400, 10, 0));
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
				
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
		footerPanel.add(sunPointsLabel);
		footerPanel.add(buttonPanel);
		footerPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));

		return footerPanel;
	}
	
	/**
	 * Instantiate a new Controller.
	 * 
	 * @param action The action triggered.
	 * @param payload The payload coupled with the action.
	 * @return controller The instantiated Controller.
	 */
	private Controller initController(Action action, Object payload) {
		return new Controller(model, new Event(action, payload)); 
	} 
	
	@Override
	public void handleEvent(Event event) {
		Object payload = event.getPayload();
		switch(event.getType()) {
		case SPAWN_ENTITY: {
			Entity entity = (Entity) payload;
			int i = entity.getPosition().y;
			int j = entity.getPosition().x;
			// Ensure valid location on View
			// A Entity may have location outside the view (Zombies spawn outside Board domain for instance) 
			if (!Board.isValidLocation(i, j)) return;
			// Set icon of button based on Entity type
			if (entity instanceof RegularZombie) {
				if (((RegularZombie) entity).getHealth() == RegularZombie.INITIAL_HEALTH) tiles[i][j].setIcon(RegularZombie.HEALTHY_ZOMBIE);
				else tiles[i][j].setIcon(RegularZombie.HURT_ZOMBIE);
			} else if (entity instanceof PylonZombie) {
				if (((PylonZombie) entity).getHealth() == PylonZombie.INITIAL_HEALTH) tiles[i][j].setIcon(PylonZombie.HEALTHY_PYLON);
				else tiles[i][j].setIcon(PylonZombie.DAMAGED_PYLON);
			}
			else if (entity instanceof PeaShooter) tiles[i][j].setIcon(PeaShooter.IMAGE);
			else if (entity instanceof Sunflower) tiles[i][j].setIcon(Sunflower.IMAGE);
			else if (entity instanceof Bullet) tiles[i][j].setIcon(Bullet.IMAGE);
			else if (entity instanceof Sun) tiles[i][j].setIcon(Sun.IMAGE);
			else if (entity instanceof Wallnut) tiles[i][j].setIcon(Wallnut.IMAGE);
			break;
		}
		case REMOVE_ENTITY: {
			Entity entity = (Entity) payload;
			int i = entity.getPosition().y;
			int j = entity.getPosition().x;
			if (!Board.isValidLocation(i, j)) return;
			if (!(0 <= j && j < Board.COLUMNS && 0 <= i && i < Board.ROWS)) return;
			tiles[i][j].setIcon(null); // Set icon to default
			break; 
		}
		case UPDATE_SUN_POINTS:
			sunPointsLabel.setText("Sun Points: " + (int) payload);
			break;
		case LOG_MESSAGE:
			JOptionPane.showMessageDialog(null, (String) payload);
			break;
		case TOGGLE_SUNFLOWER:
			addSunflowerButton.setEnabled((boolean) payload);
			break;
		case TOGGLE_PEASHOOTER:
			addPeaShooterButton.setEnabled((boolean) payload);
			break;
		case TOGGLE_WALLNUT:
			addWallnutButton.setEnabled((boolean) payload);
			break;
		default:
			break;
		}  
	}

	public static void Music() {
		
		InputStream music;
		
		try {
			music = new FileInputStream(new File("Medival.wav"));
			AudioStream sound = new AudioStream(music);
			AudioPlayer.player.start(sound);
		}catch(Exception e) {}
		
	}
	
	/**
	 * Main method.
	 * 
	 * @param args Arguments from standard out.
	 */
	public static void main(String args[]) {
		Music();
		new View();
	}

}
