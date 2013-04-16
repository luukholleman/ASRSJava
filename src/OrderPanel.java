import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


public class OrderPanel extends JPanel {
	public OrderPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Order"));
		
		setPreferredSize(new Dimension(970, 850));
	}
}
