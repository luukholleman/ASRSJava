package asrs;

import order.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Deze class leest de data uit het XML bestand en zet deze om in een order object
 * 
 * @param path
 * @author Jorn
 */
public class XMLLoader {
	
	public static Order readOrder(String path) throws ProductNotFoundException{
		
		// Maakt een order aan en maakt hem leeg, deze wordt later gevuld met informatie
		Order order = null;
		
		try {
			// Bouwt een JDOM document van het XML bestand met behulp van een SAX parser
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(path);
			Document document = (Document) builder.build(xmlFile);
			
			Element rootNode = document.getRootElement();
			
			// Haalt de datum uit het XML bestand
			String dateXML = rootNode.getChildText("date");
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateXML);
	
			// Haalt de totale prijs uit het XML bestand
			float totalPrice = Float.parseFloat(rootNode.getChildText("totalprice"));
	   
			// Haalt de klant informatie uit het XML bestand
			int customerId = Integer.parseInt(rootNode.getChildText("customernumber"));
			String customername = rootNode.getChildText("customername");
			Customer customer = new Customer(customerId, customername);

			//Vul de order met informatie
			order = new Order(date, totalPrice, customer);
			
			// Haalt de producten uit het XML bestand doormiddel van een for-loop
			List<Element> list = rootNode.getChildren("product");
			for (int i = 0; i < list.size(); i++) {
				
				Element node = (Element) list.get(i);
				int productId = Integer.parseInt(node.getChildText("productnumber"));
				String description = node.getChildText("description");
				float price = Float.parseFloat(node.getChildText("price"));
				
				//Haal het aangegeven aantal producten op
				for(int n = 0 ; n < Integer.parseInt(node.getChildText("amount")); n++) {
					Product product = new Product(productId, description, price);
					order.addProduct(product);
				}
			}
		}
		
		// Vangt de eventuele foutmeldingen op
		catch (IOException io) {
			System.out.println(io.getMessage());
		}
		
		catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		} 
		
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Returnt de gevulde order
		return order;
	}
}
