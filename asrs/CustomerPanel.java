/**
 * @author Luuk
 * 
 * Deze class weergeeft de klantinformatie in de GUI
 */
package asrs;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomerPanel extends JPanel {
	/**
	 * Klantnummer string
	 */
	JLabel customerIdLbl = new JLabel("Klantnummer");

	/**
	 * Klantnaam string
	 */
	JLabel customerNameLbl = new JLabel("Klantnaam");

	/**
	 * Datum string
	 */
	JLabel dateLbl = new JLabel("Datum");

	/**
	 * Totale prijs string
	 */
	JLabel totalPriceLbl = new JLabel("Totale prijs");

	/**
	 * Label voor weergeven klant id
	 */
	JLabel customerIdVal = new JLabel("Klantnummer");

	/**
	 * Label voor weergeven klantnaam
	 */
	JLabel customerNameVal = new JLabel("Klantnaam");

	/**
	 * Label voor weergeven order datum
	 */
	JLabel dateVal = new JLabel("Datum");

	/**
	 * Label voor weergeven totale prijs
	 */
	JLabel totalPriceVal = new JLabel("Totaal");

	/**
	 * Ctor
	 */
	public CustomerPanel() {
		// verander de border zodat dit gesloten wordt
		setBorder(BorderFactory.createTitledBorder("Klantinformatie"));

		// afmetingen
		setPreferredSize(new Dimension(500, 200));

		// ui bouwen
		buildUI();
	}

	/**
	 * Bouwt de ui
	 * 
	 * @return void
	 */
	private void buildUI() {
		// panels aanmaken
		JPanel columnPanel = new JPanel();
		JPanel valuePanel = new JPanel();

		// afmetingen bepalen van de panels
		columnPanel.setPreferredSize(new Dimension(100, 150));
		valuePanel.setPreferredSize(new Dimension(360, 150));

		// de boxlayout laat de elementen stapelen
		columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.PAGE_AXIS));
		valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));

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

	/**
	 * Zet de klant id
	 * 
	 * @param id
	 */
	public void setCustomerId(int id) {
		customerIdVal.setText(Integer.toString(id));
	}

	/**
	 * Zet de klantnaam
	 * 
	 * @param name
	 */
	public void setCustomerName(String name) {
		customerNameVal.setText(name);
	}

	/**
	 * Zet de datum
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		dateVal.setText(df.format(date));
	}

	/**
	 * Zet de totale prijs
	 * 
	 * @param totalPrice
	 */
	public void setTotalPrice(float totalPrice) {
		totalPriceVal.setText(Float.toString(totalPrice));
	}
}
