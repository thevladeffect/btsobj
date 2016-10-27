package exceptions;

public class RuleNotDefinedException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6119971335310297391L;

	public RuleNotDefinedException(){
		super("Rule not defined for this product.");
	}

}
