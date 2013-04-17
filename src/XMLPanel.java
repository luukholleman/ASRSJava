import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import listener.XMLUploadedListener;


public class XMLPanel extends JPanel {
	private JButton xmlBtn = new JButton("XML Inlezen");
	private JLabel fileLbl = new JLabel("Geen bestand gekozen");

	private ArrayList<XMLUploadedListener> xmlUploadListeners = new ArrayList<XMLUploadedListener>();
	
	public XMLPanel()
	{
		setBorder(BorderFactory.createTitledBorder("XML Inlezen"));
		
		setPreferredSize(new Dimension(500, 160));
		
		buildUI();
	}
	
	/**
	 * Voegt een listener toe, wordt getriggerd als er een nieuw bestand wordt geupload
	 * 
	 * @param xul
	 */
	public void addXMLUploadListener(XMLUploadedListener xul)
	{
		xmlUploadListeners.add(xul);
	}
	
	/**
	 * Triggert het xml uploaded event
	 * 
	 * @param xmlFileLocation
	 */
	private void xmlUploaded(String xmlFileLocation)
	{
		// trigger elk event
		for(XMLUploadedListener xul : xmlUploadListeners)
			xul.xmlUploaded(xmlFileLocation);
	}
	
	private void buildUI()
	{
		// maak er een big ass button van
		xmlBtn.setPreferredSize(new Dimension(480, 100));
		add(xmlBtn);
		add(fileLbl);
	}
}
