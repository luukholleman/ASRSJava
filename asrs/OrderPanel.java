package asrs;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;


public class OrderPanel extends JPanel {
	public OrderPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Order"));
		
		setPreferredSize(new Dimension(670, 650));
		
		buildUI();
	}
	
	private void buildUI() {
		String[] columnNames = {"Artikelnummer",
                "Omschrijving",
                "Prijs",
                "Grootte",
                "Bedrag",
                "Status"};
		
		Integer intie = new Integer(5);
		
		Object[][] data = {
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
				{123, "Artikel", 100, 3, 300, ""},
		};

		JTable table = new JTable(data, columnNames);
		table.getTableHeader().setPreferredSize(new Dimension(620, 20));
		table.setPreferredSize(new Dimension(620, 580));
		table.setPreferredScrollableViewportSize(new Dimension(650, 580));
				
		table.updateUI();
		
		table.firePropertyChange("# of Years", 5, 15);
		
		table.setEnabled(false);

		add(table.getTableHeader());
		add(table);
	}
}
