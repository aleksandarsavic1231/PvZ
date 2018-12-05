/**
 * A Encodable Object can be encoded into XML format.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public interface Encodable {

	/**
	 * Get the XML encoded representation of this.
	 * 
	 * @return String The Object encoded in XML.
	 */
	public  String toXMLString();
	
}
