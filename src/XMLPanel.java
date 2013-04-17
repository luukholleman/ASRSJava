import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import listener.XMLUploadedListener;


public class XMLPanel extends JPanel implements ActionListener {
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
		
		xmlBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// laat een file choose melding zien
		if(e.getSource() == xmlBtn)
		{
			JFileChooser fc = new JFileChooser();
			
			// is de ok knop ingedrukt? of dubbelklik op een file
	        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	        	// file opalen
	            File file = fc.getSelectedFile();
	            
	            // extentie controleren
	            String filename = file.getName();
	            String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
	            
	            // als het een xml file is triggeren we het event
	            if(extension.equals("xml"))
	            {
	            	xmlUploaded(file.getAbsolutePath());
	            	
	            	return;
	            }
	            // geen xml file, laat een melding zien
	            JOptionPane.showMessageDialog(null, "Geen xml bestand");
	        }
		}
		// TODO Auto-generated method stub
		
	}
}
