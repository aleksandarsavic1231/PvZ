import java.awt.Point;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * UndoableFactory can instantiate an Undoable Objects from a XML encoding.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public class UndoableFactory {
		
	public static Undoable create(Node node) throws UnimplementedUndoable, UnimplementedPlant {
		Undoable undoable;
		String type = node.getNodeName();
		Element element = (Element) node; 
		// Get last Entities from XML encoding
		NodeList entityList = element.getElementsByTagName("lastEntities").item(0).getChildNodes();
		LinkedList<Entity> tempEntities = new LinkedList<Entity>();
		for(int i = 0; i < entityList.getLength(); i++)
			try {
				tempEntities.add(EntityFactory.create(entityList.item(i)));
			} catch (UnimplementedEntity e) {
				e.printStackTrace();
			}
		// Get last balance from XML encoding
		int lastBalance = Integer.parseInt(element.getElementsByTagName("lastBalance").item(0).getTextContent());
		// Instantiate command based on type
		if (type.equalsIgnoreCase("NextCommand")) {
			// Set lastBalance, lastEntities for nextCommand
			NextCommand nextCommand = new NextCommand();
			nextCommand.setLastBalance(lastBalance);
			nextCommand.setLastEntities(tempEntities);
			undoable = nextCommand;
		} else if (type.equalsIgnoreCase("TileCommand")) {
			// Get location of tile clicked from XML encoding
			int x = Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent());
			int y = Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent());
			TileCommand tileCommand = new TileCommand(new Point(x, y));
			// Set lastBalance of tileCommand
			tileCommand.setLastBalance(lastBalance);
			// Get and set lastToggledPlant, lastDeployable, foundSun from XML encoding for tileCommand
			tileCommand.setLastToggledPlant(PlantFactory.create(element.getElementsByTagName("lastToggledPlant").item(0)));
			tileCommand.setLastDeployable(Integer.parseInt(element.getElementsByTagName("lastDeployable").item(0).getTextContent()));
			tileCommand.setFoundSun(Boolean.parseBoolean(element.getElementsByTagName("foundSun").item(0).getTextContent()));
			// Set lastEntities for tileCommand
			tileCommand.setLastEntities(tempEntities);
			undoable = tileCommand;
		} else throw new UnimplementedUndoable(type + " cannot be created");
		return undoable;
	}

}
