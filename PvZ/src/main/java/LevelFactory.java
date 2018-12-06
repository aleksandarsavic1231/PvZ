import org.w3c.dom.Node;

public class LevelFactory {
	
	public static Level create(Node node)
	throws UnimplementedLevel {
		Level level;
		String type = node.getTextContent();
		if (type.equalsIgnoreCase("ONE")) level = Level.ONE;
		else if (type.equalsIgnoreCase("TWO")) level = Level.TWO;
		else throw new UnimplementedLevel(type + " cannot be created");
		return level;		
	}

}
