import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Instances of XMLEncoderDecoder have a save and load implementation.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public interface XMLEncoderDecoder {

	/**
	 * Save this to local storage.
	 */
	public void save() 
	throws IOException;
	
	/**
	 * Load this from local storage.
	 */
	public void load()  
	throws IOException, SAXException, ParserConfigurationException ;
	
}
