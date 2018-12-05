import org.w3c.dom.Node;

/**
 * Plant factory instantiates a Plant type from a XML encoding.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public class PlantFactory {
	
	/**
	 * Instantiate a Plant type from a XML encoding.
	 * 
	 * @param node The XML encoding of the Plant.
	 * @return Plant The instantiated Plant.
	 * @throws UnimplementedPlant
	 */
	public static Plant create(Node node) throws UnimplementedPlant {
		Plant plant;
		String type = node.getTextContent();
		if (type.equalsIgnoreCase("PEA_SHOOTER")) plant = Plant.PEA_SHOOTER;
		else if (type.equalsIgnoreCase("SUNFLOWER")) plant = Plant.SUNFLOWER;
		else if (type.equalsIgnoreCase("WALNUT")) plant = Plant.WALNUT;
		else if (type.equalsIgnoreCase("REPEATER")) plant = Plant.REPEATER;
		else if (type.equalsIgnoreCase("CHERRY_BOMB")) plant = Plant.CHERRY_BOMB;
		else if (type.equalsIgnoreCase("NULL")) plant = null;
		else throw new UnimplementedPlant(type + " cannot be created");
		return plant;
	}

}
