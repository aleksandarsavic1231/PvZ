/**
 * 
 * @author kylehorne
 * @version 7 Nov 18
 */
public class Dispatch {

	/*
	 * 
	 */
	private final Action type;
	
	/*
	 * 
	 */
	private final Object payload;
	
	/**
	 * 
	 * @param type
	 * @param payload
	 */
	public Dispatch(Action type, Object payload) {
		this.type = type;
		this.payload = payload;
	}
	
	/*
	 * 
	 */
	public Action getType() {
		return type;
	}
	
	/*
	 * 
	 */
	public Object getPayload() {
		return payload;
	}
	
}
