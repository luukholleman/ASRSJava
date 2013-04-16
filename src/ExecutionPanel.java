import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


public class ExecutionPanel extends JPanel {
	public ExecutionPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Uitvoeren"));
		
		setPreferredSize(new Dimension(500, 200));
	}
}
