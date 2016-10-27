package testing;

import exceptions.PropertiesNotSetException;
import exceptions.PropertyTypeNotDefinedException;
import exceptions.RuleNotDefinedException;
import exceptions.ValueTypeMismatchException;
import application.Product;
import application.ProductType;
import application.PropertyType;
import application.Rule;

public class Main {

	public static void main(String[] args) {
		
		PropertyType price = new PropertyType("price", Double.class);
		PropertyType color = new PropertyType("color", String.class);
		PropertyType size = new PropertyType("size", Integer.class);
		
		ProductType shirt = new ProductType("shirt");
		shirt.addPropertyType(price);
		shirt.addPropertyType(color);
		shirt.addPropertyType(size);
		shirt.addRule(new Rule("discountPrice", "price-15"));
		
		ProductType socks = new ProductType("socks");
		socks.addPropertyType(price);
		socks.addPropertyType(color);
		
		ProductType scarf = new ProductType("scarf");
		scarf.addPropertyType(price);
		
		Product shirt1 = new Product("shirt1", shirt);
		Product scarf1 = new Product("scarf1", scarf);
		
		try {
			shirt1.setProperty("price", 45.00);
			shirt1.setProperty("color", "blue");
			//shirt1.setProperty("size", 'L'); //ValueTypeMismatchException
			shirt1.setProperty("size", 52);
			scarf1.setProperty("price", 20.00);
			//scarf1.setProperty("color", "black"); //PropertyNotDefinedException
		} catch (PropertyTypeNotDefinedException | ValueTypeMismatchException e) {
			e.printStackTrace();
			return;
		}
		
		double dprice;
		try {
			dprice = shirt1.applyRule("discountPrice");
		} catch (PropertiesNotSetException | RuleNotDefinedException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println(dprice);

	}

}
