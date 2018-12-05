import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Sun is a object that provides reward to the player.
 * 
 * @author kylehorne
 * @version 28 Oct 18
 */
public class Sun extends Entity {

	/**
	 * The reward to the player.
	 */
	public static final int REWARD = 25;
	
	/**
	 * Icon image of Sun objects.
	 */
	public static final ImageIcon IMAGE = new ImageIcon("src/main/resources/customSun.png");
	
	/**
	 * Constructor.
	 * 
	 * @param position The spawn position of this.
	 */
	public Sun(Point position) {
		super(position);
	}

	@Override
	public String toXMLString() {
		return "<Sun>" + 
					"<Point>" + 
						"<x>" + getPosition().x + "</x>" + 
						"<y>" + getPosition().y + "</y>" + 
					"</Point>" + 
				"</Sun>";
	}
	
}
