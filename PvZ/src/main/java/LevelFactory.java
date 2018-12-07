import org.w3c.dom.Node;

/**
 * LevelFactory can instantiate a Level from a XML encoding.
 * 
 * @author kylehorne
 * @version 7 Dec 18
 */
public class LevelFactory {
	
	/**
	 * Instantiate an Entity from a XML encoding.
	 * 
	 * @param node The XML encoding of the Entity.
	 * @return Level The instantiated Level.
	 * @throws UnimplementedLevel
	 */
	public static Level create(Node node)
	throws UnimplementedLevel {
		Level level;
		String type = node.getTextContent();
		if (type.equalsIgnoreCase("ONE")) level = Level.ONE;
		else if (type.equalsIgnoreCase("TWO")) level = Level.TWO;
		else if (type.equalsIgnoreCase("THREE")) level = Level.THREE;
		else throw new UnimplementedLevel(type + " cannot be created");
		return level;		
	}

}
