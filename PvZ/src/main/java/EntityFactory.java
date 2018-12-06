import java.awt.Point;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * EntityFactory can instantiate an Entity type from a XML encoding or instantiate a deep clone of a Entity.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public class EntityFactory implements Cloneable {
	
	/**
	 * Instantiate an Entity from a XML encoding.
	 * 
	 * @param node The XML encoding of the Entity.
	 * @return The instantiate Entity object.
	 * @throws UnimplementedEntity
	 */
	public static Entity create(Node node) throws UnimplementedEntity {
		Entity entity;
		String type = node.getNodeName();
		Element element = (Element) node;
		int x = Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent());
		int y = Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent());
		Point location = new Point(x, y);
		if (type.equalsIgnoreCase("RegularZombie")) {
			RegularZombie regularZombie = new RegularZombie(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			regularZombie.setHealth(health);
			entity = regularZombie;
		} else if (type.equalsIgnoreCase("PylonZombie")) {
			PylonZombie pylonZombie = new PylonZombie(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			pylonZombie.setHealth(health);
			entity = pylonZombie;
		} else if (type.equalsIgnoreCase("PeaShooter")) {
			PeaShooter peaShooter = new PeaShooter(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			peaShooter.setHealth(health);
			int nextDeployable = Integer.parseInt(element.getElementsByTagName("nextDeployable").item(0).getTextContent());
			PeaShooter.hardSetNextDeployable(nextDeployable);
			int fireRate = Integer.parseInt(element.getElementsByTagName("fireRate").item(0).getTextContent());
			peaShooter.setFireRate(fireRate);
			entity = peaShooter;
		} else if (type.equalsIgnoreCase("Walnut")) {
			Walnut walnut = new Walnut(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			walnut.setHealth(health);
			int nextDeployable = Integer.parseInt(element.getElementsByTagName("nextDeployable").item(0).getTextContent());
			Walnut.hardSetNextDeployable(nextDeployable);
			entity = walnut;
		} else if (type.equalsIgnoreCase("Sunflower")) {
			Sunflower sunflower = new Sunflower(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			sunflower.setHealth(health);
			int nextDeployable = Integer.parseInt(element.getElementsByTagName("nextDeployable").item(0).getTextContent());
			Sunflower.hardSetNextDeployable(nextDeployable);
			int fireRate = Integer.parseInt(element.getElementsByTagName("fireRate").item(0).getTextContent());
			sunflower.setFireRate(fireRate);
			entity = sunflower;
		} else if (type.equalsIgnoreCase("Bullet")) {
			int damage = Integer.parseInt(element.getElementsByTagName("damage").item(0).getTextContent());
			Bullet bullet = new Bullet(location, damage);
			boolean locked = Boolean.parseBoolean(element.getElementsByTagName("damage").item(0).getTextContent());
			bullet.setLocked(locked);
			entity = bullet;
		} else if (type.equalsIgnoreCase("Sun")) {
			entity = new Sun(location);
		} else if (type.equalsIgnoreCase("CherryBomb")) {
			CherryBomb cherryBomb = new CherryBomb(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			cherryBomb.setHealth(health);
			int nextDeployable = Integer.parseInt(element.getElementsByTagName("nextDeployable").item(0).getTextContent());
			CherryBomb.hardSetNextDeployable(nextDeployable);
			int fireRate = Integer.parseInt(element.getElementsByTagName("fireRate").item(0).getTextContent());
			cherryBomb.setFireRate(fireRate);
			entity = cherryBomb;
		} else if (type.equalsIgnoreCase("Repeater")) {
			Repeater repeater = new Repeater(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			repeater.setHealth(health);
			int nextDeployable = Integer.parseInt(element.getElementsByTagName("nextDeployable").item(0).getTextContent());
			Repeater.hardSetNextDeployable(nextDeployable);
			int fireRate = Integer.parseInt(element.getElementsByTagName("fireRate").item(0).getTextContent());
			repeater.setFireRate(fireRate);
			entity = repeater;
		}  else if (type.equalsIgnoreCase("Chomper")) {
			Chomper chomper = new Chomper(location);
			int health = Integer.parseInt(element.getElementsByTagName("health").item(0).getTextContent());
			chomper.setHealth(health);
			int nextDeployable = Integer.parseInt(element.getElementsByTagName("nextDeployable").item(0).getTextContent());
			Chomper.hardSetNextDeployable(nextDeployable);
			int fireRate = Integer.parseInt(element.getElementsByTagName("fireRate").item(0).getTextContent());
			chomper.setFireRate(fireRate);
			entity = chomper;
		} else throw new UnimplementedEntity(type + " cannot be created");
		return entity;
	}
	
	/**
	 * Deep clone the passed Entity Object.
	 * 
	 * @param entity The Entity to be cloned.
	 * @return Entity The cloned Entity.
	 * @throws UnimplementedEntity.
	 */
	public static Entity clone(Entity entity) throws UnimplementedEntity {
		if (entity != null) {
			Point spawnLocation = new Point(entity.getPosition());
			if (entity instanceof RegularZombie) return new RegularZombie(spawnLocation);
			else if (entity instanceof PylonZombie) return new PylonZombie(spawnLocation);
			else if (entity instanceof PeaShooter) return new PeaShooter(spawnLocation);
			else if (entity instanceof Walnut) return new Walnut(spawnLocation);
			else if (entity instanceof Sunflower) return new Sunflower(spawnLocation);
			else if (entity instanceof Bullet) return new Bullet(spawnLocation, ((Bullet) entity).getDamage());
			else if (entity instanceof Sun) return new Sun(spawnLocation);
			else if (entity instanceof CherryBomb) return new CherryBomb(spawnLocation);
			else if (entity instanceof Repeater) return new Repeater(spawnLocation);
			else if (entity instanceof Chomper) return new Chomper(spawnLocation);
			else throw new UnimplementedEntity(entity.getClass() + " not cloneable");
		} return null;
	}

}
