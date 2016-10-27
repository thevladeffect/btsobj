package exceptions;

public class PropertyTypeNotDefinedException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2137389133719249559L;

	public PropertyTypeNotDefinedException(){
		super("Property not defined for this product type.");
	}

}
