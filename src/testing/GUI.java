package testing;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import application.Product;
import application.ProductType;
import application.PropertyType;
import application.Rule;
import exceptions.PropertiesNotSetException;
import exceptions.PropertyTypeNotDefinedException;
import exceptions.RuleNotDefinedException;
import exceptions.ValueTypeMismatchException;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2587204112774912708L;
	private ArrayList<PropertyType> propertyTypes;
	private ArrayList<ProductType> productTypes;
	private ArrayList<Product> products;
	
	private String errors = "";
	private String output = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                GUI frame = new GUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
	
	private PropertyType getPropertyType(String name){
		PropertyType p = null;
		for(PropertyType pt : propertyTypes){
			if(pt.getName().equals(name)) p = pt;
		}
		if(p == null) this.errors += "Property type \"" + name + "\" does not exist.\n";
		return p;
	}

	
	private ProductType getProductType(String name){
		ProductType p = null;
		for(ProductType pt : productTypes){
			if(pt.getName().equals(name)) p = pt;
		}
		if(p == null) this.errors += "Product type \"" + name + "\" does not exist.\n";
		return p;
	}
	
	private Product getProduct(String name){
		Product p = null;
		for(Product pt : products){
			if(pt.getName().equals(name)) p = pt;
		}
		if(p == null) this.errors += "Product type \"" + name + "\" does not exist.\n";
		return p;
	}
	
	private static boolean isInteger(String s) {
	    try {
			//noinspection ResultOfMethodCallIgnored
			Integer.parseInt(s);
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	private GUI() {
		setResizable(false);
		setTitle("DSL");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(12, 11, 562, 317);
		contentPane.add(scrollPane1);
		
		JTextArea inputText = new JTextArea();
		inputText.setText("NewEntityType Cascaval\r\nNewPropertyType TermenValabilitate Integer\r\nNewPropertyType TaraOrigine String\r\nAddPropType Cascaval TermenValabilitate\r\nAddPropType Cascaval TaraOrigine\r\nAddRule Cascaval Pret 10-TermenValabilitate+10\r\nNewEntity CascavalDorna Cascaval\r\nAddNewProperty CascavalDorna TermenValabilitate 6\r\nAddNewProperty CascavalDorna TaraOrigine Romania\r\nNewEntity CascavalEmmental Cascaval\r\nAddNewProperty CascavalEmmental TermenValabilitate 12\r\nAddNewProperty CascavalEmmental TaraOrigine Germania\r\nApplyRule CascavalDorna Pret\r\nListAllProductTypes\r\nListWithRule Cascaval Pret");
		scrollPane1.setViewportView(inputText);
		inputText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 373, 562, 127);
		contentPane.add(scrollPane2);
		
		JTextArea outputText = new JTextArea();
		outputText.setBackground(SystemColor.inactiveCaptionBorder);
		outputText.setEditable(false);
		scrollPane2.setViewportView(outputText);
		outputText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JButton btnCompile = new JButton("Compile");
		btnCompile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String in = inputText.getText();
				
				propertyTypes = new ArrayList<>();
				productTypes = new ArrayList<>();
				products = new ArrayList<>();
				
				Scanner sc = new Scanner(new BufferedReader(new StringReader(in)));
				
				while (sc.hasNextLine()){
					
					String[] elements = sc.nextLine().split(" ");
					
					switch(elements[0]){
					case "NewEntityType":
						productTypes.add(new ProductType(elements[1]));
						break;
					case "NewPropertyType":
						Class<?> c = null;
						switch(elements[2]){
						case "String": 
							c = String.class;
							break;
						case "int":
						case "Integer":
							c = Integer.class;
							break;
						case "double":
						case "Double":
							//noinspection UnusedAssignment
							c = Double.class;
						case "float":
						case "Float":
							c = Float.class;
						}
						propertyTypes.add(new PropertyType(elements[1],c));
						break;
					case "AddPropType":
						getProductType(elements[1]).addPropertyType(getPropertyType(elements[2]));
						break;
					case "NewEntity":
						products.add(new Product(elements[1],getProductType(elements[2])));
						break;
					case "AddNewProperty":
						try {
							if(isInteger(elements[3]))
								getProduct(elements[1]).setProperty(elements[2], Integer.parseInt(elements[3]));
							else
								getProduct(elements[1]).setProperty(elements[2], elements[3]);
						} catch (PropertyTypeNotDefinedException
								| ValueTypeMismatchException e1) {
							//e1.printStackTrace();
							errors += e1.getMessage() + "\n";
						}
						break;
					case "RemovePropType":
						getProductType(elements[1]).removePropertyType(getPropertyType(elements[2]));
						break;
					case "AddRule":
						getProductType(elements[1]).addRule(new Rule(elements[2],elements[3]));
						break;
					case "ApplyRule":
						output += "Rule \"" + elements[2] + "\" applied on product \"" + elements[1] + "\" gives result: ";
						try {
							output += getProduct(elements[1]).applyRule(elements[2]) + "\n";
						} catch (PropertiesNotSetException
								| RuleNotDefinedException e1) {
							//e1.printStackTrace();
							errors += e1.getMessage() + "\n";
						}
						break;
					case "ListAllProductTypes":
						output += "All product types: ";
						for(ProductType t : productTypes)
							output += t.getName() + " ";
						output += '\n';
						break;
					case "ListWithRule":
						output += "Product type \"" + elements[1] + "\" items with rule \"" + elements[2] + "\" applied:\n";
						//e1.printStackTrace();
						products.stream().filter(p -> p.getType().getName().equals(elements[1])).forEach(p -> {
							output += p.getName() + " ";
							try {
								output += p.applyRule(elements[2]);
							} catch (PropertiesNotSetException
									| RuleNotDefinedException e1) {
								//e1.printStackTrace();
								errors += e1.getMessage() + "\n";
							}
							output += "\n";
						});
						break;
					
					}
				}
				
				sc.close();
				output = errors + output;
				outputText.setText(output);
				output = "";
				
			}
		});
		btnCompile.setBounds(243, 339, 89, 23);
		contentPane.add(btnCompile);
	}
}
