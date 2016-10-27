package exceptions;

public class RequiredPropertyNotAvailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -664538342459060469L;

	public RequiredPropertyNotAvailableException(){
		super("Property used in rule computation not valid for product type.");
	}
}
