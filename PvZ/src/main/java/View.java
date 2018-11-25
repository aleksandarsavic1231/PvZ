import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private JButton 
		addPeaShooterButton, 
		addSunflowerButton, 
		addWallnutButton, 
		addRepeaterButton,
		addBombButton, 
		undoButton, 
		redoButton;
		
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
		
	private UndoManager undoManager;
	
	/**
	 * Constructor.
	 */
	public View() {
		super("Plant vs. Zombies");
		// Initialize model
		model = new Model();
		// Initialize undo manager
		undoManager = new UndoManager();
		// Initialize content
		getContentPane().add(addBoard(), BorderLayout.CENTER);
		getContentPane().add(addFooter(), BorderLayout.PAGE_END);
		setJMenuBar(addMenuBar());
		// Subscribe to Events
		model.addActionListener(this);
		undoManager.addActionListener(this);
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
		restart.addActionListener(new RestartAction(model, undoManager));
		
		JMenuItem quit = new JMenuItem("Quit Game");
		quit.addActionListener(e -> System.exit(0) );
		
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
			tiles[i][j].addActionListener(new TileAction(model, undoManager, new Point(j, i)));
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
		addPeaShooterButton.addActionListener(new TogglePlantAction(model, Plant.PEA_SHOOTER));
		
		ImageIcon sunflowerLogo = new ImageIcon("src/main/resources/sunFlowerIcon.png");
		addSunflowerButton = new JButton(sunflowerLogo);
		addSunflowerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addSunflowerButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addSunflowerButton.addActionListener(new TogglePlantAction(model, Plant.SUNFLOWER));
		
		ImageIcon wallnutLogo = new ImageIcon("src/main/resources/wallnutIcon.png");
		addWallnutButton = new JButton(wallnutLogo);
		addWallnutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addWallnutButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addWallnutButton.addActionListener(new TogglePlantAction(model, Plant.WALNUT));
		
		ImageIcon repeaterLogo = new ImageIcon("src/main/resources/repeaterIcon.png");
		addRepeaterButton = new JButton(repeaterLogo);
		addRepeaterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addRepeaterButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addRepeaterButton.addActionListener(new TogglePlantAction(model, Plant.REPEATER));
		
		ImageIcon bombLogo = new ImageIcon("src/main/resources/cherryBombIcon.png");
		addBombButton = new JButton(bombLogo);
		addBombButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addBombButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addBombButton.addActionListener(new TogglePlantAction(model, Plant.BOMB));
		
		JButton nextIterationButton = new JButton("Next Iteration");
		nextIterationButton.setBorder(defaultBorder);
		nextIterationButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextIterationButton.addActionListener(new NextAction(model, undoManager));
		
		undoButton = new JButton("Undo");
		undoButton.setBorder(defaultBorder);
		undoButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		undoButton.addActionListener(e -> { undoManager.undo(); });
		
		redoButton = new JButton("Redo");
		redoButton.setBorder(defaultBorder);
		redoButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		redoButton.addActionListener(e -> { undoManager.redo(); });
			
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(addPeaShooterButton);
		buttonPanel.add(addSunflowerButton);
		buttonPanel.add(addWallnutButton);
		buttonPanel.add(addRepeaterButton);
		buttonPanel.add(addBombButton);
		buttonPanel.add(nextIterationButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 330, 10, 0));
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
				
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
		footerPanel.add(sunPointsLabel);
		footerPanel.add(buttonPanel);
		footerPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));

		return footerPanel;
	}
	
	@Override
	public void handleEvent(Event event) {
		switch(event.getType()) {
		case SPAWN_ENTITY: {
			Entity entity = ((EntityEvent) event).getEntity();
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
			else if (entity instanceof Walnut) tiles[i][j].setIcon(Walnut.IMAGE);
			else if (entity instanceof Repeater) tiles[i][j].setIcon(Repeater.IMAGE);
			else if (entity instanceof Bomb) tiles[i][j].setIcon(Bomb.IMAGE);
			break;
		}
		case REMOVE_ENTITY: {
			Entity entity = ((EntityEvent) event).getEntity();
			int i = entity.getPosition().y;
			int j = entity.getPosition().x;
			if (!Board.isValidLocation(i, j)) return;
			if (!(0 <= j && j < Board.COLUMNS && 0 <= i && i < Board.ROWS)) return;
			tiles[i][j].setIcon(null); // Set icon to default
			break; 
		}
		case UPDATE_BALANCE:
			sunPointsLabel.setText("Sun Points: " + model.getBalance());
			break;
		case ROUND_OVER:
			JOptionPane.showMessageDialog(null, "Congratulations, you beat the round!");
			break;
		case GAME_OVER:
			JOptionPane.showMessageDialog(null, "You lost the round!");
			break;
		case TOGGLE_SUNFLOWER:
			addSunflowerButton.setEnabled(model.isSunflowerPurchasable());
			break;
		case TOGGLE_PEASHOOTER:
			addPeaShooterButton.setEnabled(model.isPeaShooterPurchasable());
			break;
		case TOGGLE_WALLNUT:
			addWallnutButton.setEnabled(model.isWallnutPurchasable());
			break;
		case TOGGLE_REPEATER:
			addRepeaterButton.setEnabled(model.isRepeaterPurchasable());
		case TOGGLE_BOMB:
			addBombButton.setEnabled(model.isBombPurchasable());
			break;
		case UNDO:
			undoButton.setEnabled(undoManager.isUndoAvailable());
			break;
		case REDO:
			redoButton.setEnabled(undoManager.isRedoAvailable());
			break;
		default:
			break;
		}  
	}

	/**
	 * Main method.
	 * 
	 * @param args Arguments from standard out.
	 */
	public static void main(String args[]) {
		new View();
	}

}
