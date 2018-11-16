import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class View extends JFrame implements Listener {

	private static final long serialVersionUID = 1L;
	
	private JButton[][] tiles;
	
	private JLabel sunPointsLabel;
	
	private JButton addPeaShooterButton, addSunflowerButton;
		
	private Model model;
	
	public static final int WIDTH = 1000;
	
	public static final int HEIGHT = 600;

	public static final Color LIGHT_GREEN = new Color(0,255,51);
	
	public static final Color GREEN = new Color(0,153,0);
	
	public static final Color DARK_GREEN = new Color(0,102,0);
		
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
	
	private JMenuBar addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
 
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(initController(Action.RESTART_GAME, null));
		
		JMenuItem quit = new JMenuItem("Quit Game");
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
	
	private JPanel addBoard() {		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(Board.ROWS, Board.COLUMNS));
		tiles = new JButton[Board.ROWS][Board.COLUMNS];
		
		// Initialize tiles	
		Board.iterate((i, j) -> {
			tiles[i][j] = new JButton();
			tiles[i][j].addActionListener(initController(Action.SPAWN_ENTITY, new Point(j, i)));
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
	
	private JPanel addFooter() {
		Border defaultBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		
		sunPointsLabel = new JLabel();
		sunPointsLabel.setBorder(defaultBorder);
		sunPointsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		addPeaShooterButton = new JButton("Add PeaShooter");
		addPeaShooterButton.setBorder(defaultBorder);
		addPeaShooterButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addPeaShooterButton.addActionListener(initController(Action.TOGGLE_PEASHOOTER, null));
		addSunflowerButton = new JButton("Add Sunflower");
		addSunflowerButton.setBorder(defaultBorder);
		addSunflowerButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		addSunflowerButton.addActionListener(initController(Action.TOGGLE_SUNFLOWER, null));
		JButton nextIterationButton = new JButton("Next Iteration");
		nextIterationButton.setBorder(defaultBorder);
		nextIterationButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextIterationButton.addActionListener(initController(Action.NEXT_ITERATION, null));
			
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(addPeaShooterButton);
		buttonPanel.add(addSunflowerButton);
		buttonPanel.add(nextIterationButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 540, 10, 0));
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
				
		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
		footerPanel.add(sunPointsLabel);
		footerPanel.add(buttonPanel);
		footerPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));

		return footerPanel;
	}
	
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
			if (!Board.isValidLocation(i, j)) return;
			if (entity instanceof Zombie) tiles[i][j].setIcon(Zombie.IMAGE);
			else if (entity instanceof PeaShooter) tiles[i][j].setIcon(PeaShooter.IMAGE);
			else if (entity instanceof Sunflower) tiles[i][j].setIcon(Sunflower.IMAGE);
			else if (entity instanceof Bullet) tiles[i][j].setIcon(Bullet.IMAGE);
			break;
		}
		case REMOVE_ENTITY: {
			Entity entity = (Entity) payload;
			int i = entity.getPosition().y;
			int j = entity.getPosition().x;
			if (!Board.isValidLocation(i, j)) return;
			if (!(0 <= j && j < Board.COLUMNS && 0 <= i && i < Board.ROWS)) return;
			tiles[i][j].setIcon(null); 
			break;
		}
		case UPDATE_SUN_POINTS:
			sunPointsLabel.setText("Sun Points: " + (int) payload);
			break;
		case LOG_MESSAGE:
			JOptionPane.showMessageDialog(null, (String) payload);
			break;
		case TOGGLE_SUNFLOWER:
			System.out.println("Sunflower " +(boolean) payload);
			addSunflowerButton.setEnabled((boolean) payload);
			break;
		case TOGGLE_PEASHOOTER:
			System.out.println("PeaShooter " + (boolean) payload);
			addPeaShooterButton.setEnabled((boolean) payload);
			break;
		default:
			break;
		}  
	}

	public static void main(String args[]) {
		new View();
	}

}
