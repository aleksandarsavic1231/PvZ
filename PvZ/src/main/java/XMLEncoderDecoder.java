import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ...
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public abstract class XMLEncoderDecoder {

	/**
	 * Save this to local storage.
	 */
	public void save() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(new File("./" + getClass().getName() + ".xml"));
			XMLEncoder encoder = new XMLEncoder(fileOutputStream);
			encoder.writeObject(this);
			encoder.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load this from local storage.
	 */
	public void load() {
		try {
			FileInputStream fileInputStream = new FileInputStream(new File("./" + getClass().getName() + ".xml"));
			XMLDecoder decoder = new XMLDecoder(fileInputStream);
			reinitialize(decoder.readObject());
			decoder.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public abstract void reinitialize(Object object);
	
}
