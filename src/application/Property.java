package application;
import exceptions.ValueTypeMismatchException;


public class Property {
	
	private PropertyType type;
	private Object value;
	
	public Property(PropertyType type, Object value) throws ValueTypeMismatchException{
		
		this.type = type;
		
		Class<?> typeClass = this.type.getType();
		if(typeClass.isInstance(value)){
			this.value = value;
		}
		else{
			throw new ValueTypeMismatchException();
		}

	}
	
	public PropertyType getType(){
		return this.type;
	}
	
	public Object getValue(){
		return this.value;
	}

}
