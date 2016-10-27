package exceptions;

public class ValueTypeMismatchException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7823130238529250298L;

	public ValueTypeMismatchException(){
		super("Property value type different than expected.");
	}

}
