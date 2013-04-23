package asrs;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CustomerPanel extends JPanel {
	// de velden
	JLabel customerIdVal = new JLabel();
	JLabel customerNameVal = new JLabel();
	JLabel dateVal = new JLabel();
	JLabel totalPriceVal = new JLabel();
	
	public CustomerPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Klantinformatie"));
		
		setPreferredSize(new Dimension(500, 200));
		
		buildUI();
	}

	private void buildUI() {
		JPanel columnPanel = new JPanel();
		JPanel valuePanel = new JPanel();

		columnPanel.setPreferredSize(new Dimension(100, 200));
		valuePanel.setPreferredSize(new Dimension(360, 200));
		
		// de boxlayout laat de elementen stapelen
		columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.PAGE_AXIS));
		valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));

		JLabel customerIdLbl = new JLabel("Klantnummer");
		JLabel customerNameLbl = new JLabel("Klantnaam");
		JLabel dateLbl = new JLabel("Datum");
		JLabel totalPriceLbl = new JLabel("Totale prijs");
		
		customerIdLbl.setPreferredSize(new Dimension(100, 20));

		columnPanel.add(customerIdLbl);
		columnPanel.add(customerNameLbl);
		columnPanel.add(dateLbl);
		columnPanel.add(totalPriceLbl);

		valuePanel.add(customerIdVal);
		valuePanel.add(customerNameVal);
		valuePanel.add(dateVal);
		valuePanel.add(totalPriceVal);
		
		add(columnPanel);
		add(valuePanel);
	}
	
	public void setCustomerId(int id)
	{
		customerIdVal.setText(Integer.toString(id));
	}
	
	public void setCustomerName(String name)
	{
		customerNameVal.setText(name);
	}
	
	public void setDate(Date date)
	{
		dateVal.setText(date.toLocaleString());
	}
	
	public void setTotalPrice(float totalPrice)
	{
		totalPriceVal.setText(Float.toString(totalPrice));
	}
}
