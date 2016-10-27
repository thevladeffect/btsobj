package exceptions;

public class PropertiesNotSetException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6594939937269946624L;

	public PropertiesNotSetException(){
		super("One more more properties not yet set.");
	}

}
