package application;
import java.util.ArrayList;

import exceptions.RequiredPropertyNotAvailableException;


public class ProductType {
	
	private String name;
	private ArrayList<PropertyType> propertyTypes;
	private ArrayList<Rule> rules;
	
	public ProductType(String name){
		this.name = name;
		propertyTypes = new ArrayList<>();
		rules = new ArrayList<>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<PropertyType> getPropertyTypes(){
		return this.propertyTypes;
	}
	
	public ArrayList<Rule> getRules(){
		return this.rules;
	}
	
	public void addPropertyType(PropertyType pt){
		propertyTypes.add(pt);
	}
	
	public void removePropertyType(PropertyType pt){
		propertyTypes.remove(pt);
		for(Rule rule : rules)
			try {
				checkRuleValidity(rule);
			} catch (RequiredPropertyNotAvailableException e) {
				System.out.println("Rule " + rule.getName() + " removed due to dependency on removed property.");
				this.removeRule(rule);
			}
	}
	
	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	private void checkRuleValidity(Rule rule) throws RequiredPropertyNotAvailableException{
		String[] items = rule.getRule().split("[^a-zA-Z0-9]+");
		for(String p : items){
			boolean present = false;
			if(!isInteger(p))
				for(PropertyType pt : propertyTypes){
					if(pt.getName().equals(p)) present = true;
				}
			else{
				present = true;
			}
			if(!present) throw new RequiredPropertyNotAvailableException();				
		}
	}
	
	public void addRule(Rule rule){
		try {
			checkRuleValidity(rule);
		} catch (RequiredPropertyNotAvailableException e) {
			e.printStackTrace();
			return;
		}
		rules.add(rule);
	}
	
	public void removeRule(Rule rule){
		rules.remove(rule);
	}

}
