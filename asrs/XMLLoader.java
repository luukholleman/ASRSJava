package asrs;

import java.util.Date;

public class XMLLoader {
	
	public Order readOrder(String path){
		
		File fXmlFile = new File("/Users/mkyong/staff.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		Order(date, totalPrice, customer);
	}
}