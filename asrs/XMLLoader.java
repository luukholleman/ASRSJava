package asrs;

import order.Customer;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class XMLLoader {
	
	public static Order readOrder(String path){
		
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(path);
		
		Order order = null;
		
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			
			// Get date from XML
			String dateXML = rootNode.getChildText("date");
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateXML);
			System.out.println(date);
			
			// Get totalPrice from XML
			float totalPrice = Float.parseFloat(rootNode.getChildText("totalprice"));
			System.out.println(totalPrice);
		   
			// Get customer from XML
			int customerid = Integer.parseInt(rootNode.getChildText("customernumber"));
			String customername = rootNode.getChildText("customername");
			Customer customer = new Customer(customerid, customername);
			System.out.println(customername);
			
			List list = rootNode.getChildren("product");
			
			for (int i = 0; i < list.size(); i++) {
				
				Element node = (Element) list.get(i);
				
			}
			
			order = new Order(date, totalPrice, customer);
		}
		
		catch (IOException io) {
			System.out.println(io.getMessage());
		}
		
		catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		} 
		
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		return order;
	}
}