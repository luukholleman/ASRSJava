import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;


public class OrderPanel extends JPanel {
	public OrderPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Order"));
		
		setPreferredSize(new Dimension(970, 850));
		
		buildUI();
	}
	
	private void buildUI() {
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
		
		Integer intie = new Integer(5);
		
		Object[][] data = {
		{"Kathy", "Smith",
		"Snowboarding", intie, new Boolean(false)},
		{"John", "Doe",
		"Rowing", new Integer(3), new Boolean(true)},
		{"Sue", "Black",
		"Knitting", new Integer(2), new Boolean(false)},
		{"Jane", "White",
		"Speed reading", new Integer(20), new Boolean(true)},
		{"Joe", "Brown",
		"Pool", new Integer(10), new Boolean(false)}
		};

		JTable table = new JTable(data, columnNames);
		table.getTableHeader().setPreferredSize(new Dimension(920, 20));
		table.setPreferredSize(new Dimension(920, 830));
		table.setPreferredScrollableViewportSize(new Dimension(950, 830));
		
		intie = 897;
		
		table.updateUI();
		
		table.firePropertyChange("# of Years", 5, 15);
		
		table.setEnabled(false);

		add(table.getTableHeader());
		add(table);
	}
}
