import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class XMLPanel extends JPanel {
	private JButton xmlBtn = new JButton("XML Inlezen");
	private JLabel fileLbl = new JLabel("Geen bestand gekozen");
	
	public XMLPanel()
	{
		setBorder(BorderFactory.createTitledBorder("XML Inlezen"));
		
		setPreferredSize(new Dimension(500, 160));
		
		buildUI();
	}
	
	private void buildUI()
	{
		// maak er een big ass button van
		xmlBtn.setPreferredSize(new Dimension(480, 100));
		add(xmlBtn);
		add(fileLbl);
	}
}
