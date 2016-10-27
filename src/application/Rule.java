package application;

public class Rule {
	
	private String name, rule;
	
	public Rule(String name, String rule){
		this.name = name;
		this.rule = rule;
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getRule(){
		return this.rule;
	}

}
