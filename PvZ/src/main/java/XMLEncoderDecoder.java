/**
 * A instance of XMLEncoderDecoder contains a save and load implementation.
 * 
 * @author kylehorne
 * @version 5 Dec 18
 */
public interface XMLEncoderDecoder {

	/**
	 * Save this to local storage.
	 */
	public void save();
	
	/**
	 * Load this from local storage.
	 */
	public void load();
	
}
