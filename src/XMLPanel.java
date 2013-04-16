import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class XMLPanel extends JPanel {
	private JButton xmlBtn = new JButton("XML Inlezen");
	
	public XMLPanel()
	{
		setBorder(BorderFactory.createTitledBorder("XML Inlezen"));
		
		setPreferredSize(new Dimension(500, 200));
		
		buildUI();
	}
	
	private void buildUI()
	{
		add(xmlBtn);
	}
}
