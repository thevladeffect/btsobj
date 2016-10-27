package application;

public class PropertyType {
	
	private String name;
	private Class<?> type;
	
	public PropertyType(String name, Class<?> type){
		this.name = name;
		this.type = type;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Class<?> getType(){
		return this.type;
	}

}
