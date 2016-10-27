package application;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import exceptions.PropertiesNotSetException;
import exceptions.PropertyTypeNotDefinedException;
import exceptions.RuleNotDefinedException;
import exceptions.ValueTypeMismatchException;

public class Product {
	
	private String name;
	private ProductType type;
	private ArrayList<Property> properties;
	
	public Product(String name, ProductType type){
		this.name = name;
		this.type = type;
		properties = new ArrayList<>();
	}
	
	public ProductType getType(){
		return this.type;
	}
	
	public ArrayList<Property> getProperties(){
		return this.properties;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setProperty(String name, Object value) throws PropertyTypeNotDefinedException, ValueTypeMismatchException{
		Property property = null;
		
		for(PropertyType t : type.getPropertyTypes())
			if(t.getName().equals(name))
				property = new Property(t,value);
		
		if(property == null) throw new PropertyTypeNotDefinedException();

		properties.add(property);
	}
	
	private Property getProperty(String name){
		for(Property p : properties)
			if(p.getType().getName().equals(name)) return p;
		return null;
	}
	
	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public double applyRule(String name) throws PropertiesNotSetException, RuleNotDefinedException{
		
		Rule rule = null;
		for (Rule r : type.getRules())
			if(name.equals(r.getName())) rule = r;
		if (rule == null) throw new RuleNotDefinedException();
		
		if (properties.size() != type.getPropertyTypes().size()) throw new PropertiesNotSetException();
		
		String r = rule.getRule();
		String[] items = r.split("[^a-zA-Z0-9]+");
		
		for(String i : items)
			if(!isInteger(i)){
				r = r.replace(i, ""+getProperty(i).getValue());
			}
		
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    
	    double result;
	    String res = "";
		try {
			res = engine.eval(r).toString();
		} catch (ScriptException e) {
			System.out.println("Rule is not a valid arithmetic operation.");
			e.printStackTrace();
		}
		
		result = Double.parseDouble(res);

		return result;
		
	}
	

}
