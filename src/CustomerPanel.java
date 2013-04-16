import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


public class CustomerPanel extends JPanel {
	public CustomerPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Klantinformatie"));
		
		setPreferredSize(new Dimension(500, 200));
	}
}
