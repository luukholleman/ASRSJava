package asrs;

import gnu.io.CommPortIdentifier;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import listener.ExecuteButtonPressedListener;
import tspAlgorithm.BruteForce;
import tspAlgorithm.Column;
import tspAlgorithm.ForcedGreedy;
import tspAlgorithm.Greedy;
import tspAlgorithm.Simultaneously;
import tspAlgorithm.Random;
import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.AlmostWorstFit;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.BestFit;
import bppAlgorithm.Circulate;
import bppAlgorithm.FirstFit;
import bppAlgorithm.WorstFit;

/**
 * @author Luuk
 * 
 * Deze class zorgt voor de algoritmes en het starten daarvan
 * 
 * Hij geeft het panel weer waar de algoritmes en compoorten worden weergegeven
 * Verder zorgt hij voor het triggeren van de events van het starten van de opdracht
 */
public class ExecutionPanel extends JPanel implements ActionListener {
	/**
	 * Alle beschikbare bpp algoritmes
	 */
	private ArrayList<BPPAlgorithm> bppAlgorithms = new ArrayList<BPPAlgorithm>();
	
	/**
	 * Alle beschikbare tsp algoritmes
	 */
	private ArrayList<TSPAlgorithm> tspAlgorithms = new ArrayList<TSPAlgorithm>();

	/**
	 * De gebonden listeners, worden getriggerd als een opdracht wordt gestart
	 */
	private ArrayList<ExecuteButtonPressedListener> executeButtonPressedListeners = new ArrayList<ExecuteButtonPressedListener>();

	/**
	 * Simulatie button
	 */
	private JButton simulateBtn = new JButton("Simulatie");
	
	/**
	 * Simulatie task button
	 */
	private JButton simulateTaskBtn = new JButton("Simulatie Task");
	
	/**
	 * Uitvoeren button
	 */
	private JButton executeBtn = new JButton("Uitvoeren");

	/**
	 *  bpp button group, weergeeft de verschillende bpp algoritmes
	 */
	private ButtonGroup bppBtnGrp = new ButtonGroup();
	
	/**
	 *  tsp button group, weergeeft de verschillende tsp algoritmes
	 */
	private ButtonGroup tspBtnGrp = new ButtonGroup();

	/**
	 * Het bpp algoritme, wordt gevuld na het starten van een opdracht
	 */
	private BPPAlgorithm bppAlgorithm;
	
	/**
	 * Het tsp algoritme, wordt gevuld na het starten van een opdracht
	 */
	private TSPAlgorithm tspAlgorithm;

	/**
	 * Comport selectbox lopende band robot
	 */
	private JComboBox comportsBpp;
	
	/**
	 * Comport selectbox magazijn robot
	 */
	private JComboBox comportsTsp;

	/**
	 * Checkbox om gedetecteerde size te gebruken
	 */
	private JCheckBox detectedSize;
	
	/**
	 * De seed voor de task
	 */
	private JTextField seed = new JTextField();

	/**
	 * ctor
	 * 
	 * @author Luuk
	 */
	public ExecutionPanel() {
		// creeer een fieldset met title
		setBorder(BorderFactory.createTitledBorder("Uitvoeren"));

		setPreferredSize(new Dimension(500, 270));

		// voeg de bpp algoritmes toe
		bppAlgorithms.add(new FirstFit());
		bppAlgorithms.add(new BestFit());
		bppAlgorithms.add(new Circulate());
		bppAlgorithms.add(new WorstFit());
		bppAlgorithms.add(new AlmostWorstFit());

		// voeg de tsp algoritmes toe
		tspAlgorithms.add(new ForcedGreedy());
		tspAlgorithms.add(new BruteForce());
		tspAlgorithms.add(new Greedy());
		tspAlgorithms.add(new Column());
		tspAlgorithms.add(new Simultaneously());
		tspAlgorithms.add(new Random());

		buildUI();
	}

	/**
	 * Bouwt de ui
	 * 
	 * @author Luuk
	 * 
	 * @return void
	 */
	private void buildUI() {
		// 3 panels, 1 voor bpp, tsp en coms
		JPanel bppPanel = new JPanel();
		JPanel tspPanel = new JPanel();
		JPanel comPanel = new JPanel();

		bppPanel.setPreferredSize(new Dimension(150, 180));
		tspPanel.setPreferredSize(new Dimension(150, 180));
		comPanel.setPreferredSize(new Dimension(170, 180));

		// de boxlayout laat de elementen stapelen
		bppPanel.setLayout(new BoxLayout(bppPanel, BoxLayout.PAGE_AXIS));
		tspPanel.setLayout(new BoxLayout(tspPanel, BoxLayout.PAGE_AXIS));

		// hoofdlabel
		bppPanel.add(new JLabel("BPP algoritme"));

		boolean firstBpp = true;

		// loop de bpp algoritmes en plaats de namen in radiobuttons
		for (BPPAlgorithm bppAlgorithm : bppAlgorithms) {
			// maak een radio button aan met het algoritme, voeg hem toe aan de button groep en aan het panel om weer te geven
			JRadioButton rdBtn = new JRadioButton(bppAlgorithm.getName());
			bppBtnGrp.add(rdBtn);
			bppPanel.add(rdBtn);

			// de eerste moet altijd geselecteerd zijn
			if (firstBpp) {
				rdBtn.setSelected(true);
				firstBpp = false;
			}
		}

		// hoofdlabel
		tspPanel.add(new JLabel("TSP algoritme"));

		boolean firstTsp = true;

		// loop de tsp algoritmes en plaats de namen in radiobuttons
		for (TSPAlgorithm tspAlgorithm : tspAlgorithms) {
			// maak een radio button aan met het algoritme, voeg hem toe aan de button groep en aan het panel om weer te geven
			JRadioButton rdBtn = new JRadioButton(tspAlgorithm.getName());
			tspBtnGrp.add(rdBtn);
			tspPanel.add(rdBtn);

			// de eerste moet altijd geselecteerd zijn
			if (firstTsp) {
				rdBtn.setSelected(true);
				firstTsp = false;
			}
		}
		
		// hier worden de comports in geplaatst
		ArrayList<String> comports = new ArrayList<String>();

		// alle poorten ophalen
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// loop alle pooorten
		while (portEnum.hasMoreElements()) {
			// haal de poort op
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();

			// voeg de naam toe aan de array
			comports.add(currPortId.getName());
		}

		// convert de compoorten naar een array en zet ze in de combobox
		comportsBpp = new JComboBox(comports.toArray());
		comportsBpp.addActionListener(this);
		
		// convert de compoorten naar een array en zet ze in de combobox
		comportsTsp = new JComboBox(comports.toArray());
		comportsTsp.addActionListener(this);

		//Maak de checkbox voor de grootte
		detectedSize = new JCheckBox("Detecteer grote");

		JLabel bppArduino = new JLabel("Loopband Arduino");
		JLabel tspArduino = new JLabel("Magazijn Arduino");

		// voeg de label toe met de combobox daaronder
		comPanel.add(bppArduino);
		comPanel.add(comportsBpp);

		// voeg de label toe met de combobox daaronder
		comPanel.add(tspArduino);
		comPanel.add(comportsTsp);

		// seed textfield
		comPanel.add(seed);

		//size checkbox
		comPanel.add(detectedSize);
		
		// voeg de actionlisteners toe zodat het event kan worden getriggerd
		simulateBtn.addActionListener(this);
		simulateTaskBtn.addActionListener(this);
		executeBtn.addActionListener(this);

		simulateBtn.setPreferredSize(new Dimension(153, 50));
		simulateTaskBtn.setPreferredSize(new Dimension(153, 50));
		executeBtn.setPreferredSize(new Dimension(153, 50));

		seed.setPreferredSize(new Dimension(150, 20));

		// voeg de panels toe voor weergave
		add(bppPanel);
		add(tspPanel);
		add(comPanel);
		add(simulateBtn);
		add(simulateTaskBtn);
		add(executeBtn);
	}

	/**
	 * Voegt een listener toe, wordt getriggerd als er een nieuw bestand wordt
	 * geupload
	 * 
	 * @param listener
	 */
	public void addExecutionListener(ExecuteButtonPressedListener el) {
		executeButtonPressedListeners.add(el);
	}

	/**
	 * Triggert het simulate button press event
	 * 
	 * @param bpp
	 * @param tsp
	 */
	private void simulateButtonPressed(BPPAlgorithm bpp, TSPAlgorithm tsp) {
		// trigger elk event
		for (ExecuteButtonPressedListener ebpl : executeButtonPressedListeners)
			ebpl.simulatePressed(bpp, tsp, detectedSize.isSelected());
	}

	/**
	 * Triggert het simulate button press event
	 * 
	 * @param bpp
	 * @param tsp
	 * @param seed
	 */
	private void simulateTaskButtonPressed(BPPAlgorithm bpp, TSPAlgorithm tsp,
			String seed) {
		long longSeed = 0;
		try {
			longSeed = Long.parseLong(seed, 36);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Voer eerst een geldige seed in.");
			return;
		}
		// trigger elk event
		for (ExecuteButtonPressedListener ebpl : executeButtonPressedListeners)
			ebpl.simulateTaskPressed(bpp, tsp, longSeed);
	}

	/**
	 * Triggert het button press uploaded event
	 * 
	 * @param bpp
	 * @param tsp
	 * @param com1
	 * @param com2
	 */
	private void executeButtonPressed(BPPAlgorithm bpp, TSPAlgorithm tsp,
			CommPortIdentifier com1, CommPortIdentifier com2) {
		// trigger elk event
		for (ExecuteButtonPressedListener ebpl : executeButtonPressedListeners)
			ebpl.executePressed(bpp, tsp, com1, com2, detectedSize.isSelected());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getBPPAlgorithmFromRadioButtons();
		getTSPAlgorithmFromRadioButtons();

		if (e.getSource() == simulateBtn) {
			if (bppAlgorithm == null || tspAlgorithm == null)
				JOptionPane.showMessageDialog(this,
						"Selecteer eerst twee algoritmes.");
			else
				simulateButtonPressed(bppAlgorithm, tspAlgorithm);
		}
		if (e.getSource() == simulateTaskBtn) {
			if (bppAlgorithm == null || tspAlgorithm == null)
				JOptionPane.showMessageDialog(this,
						"Selecteer eerst twee algoritmes.");
			else
				simulateTaskButtonPressed(bppAlgorithm, tspAlgorithm,
						seed.getText());
		}
		if (e.getSource() == executeBtn) {
			if (bppAlgorithm == null || tspAlgorithm == null)
				JOptionPane.showMessageDialog(this,
						"Selecteer eerst twee algoritmes.");
			else {
				executeButtonPressed(bppAlgorithm, tspAlgorithm,
						stringToComport(comportsBpp.getSelectedItem()
								.toString()), stringToComport(comportsTsp
								.getSelectedItem().toString()));
			}
		}
	}
	
	/**
	 * Convert een string naar comport object
	 * 
	 * @param comport
	 * @return commport
	 */
	private CommPortIdentifier stringToComport(String comport) {
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		// Zoeken naar de poort
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();

			if (currPortId.getName().equals(comport)) {
				System.out.println("String to comport gevonden: " + comport);

				return currPortId;
			}
		}
		System.out.println("String to comport niet gevonden: " + comport);

		return null;
	}

	/**
	 * Bepaal het gekozen tsp algoritme op basis van de radio buttons
	 *  
	 * @return bppAlgorithm
	 */
	private BPPAlgorithm getBPPAlgorithmFromRadioButtons() {
		/**
		 * we moeten de buttongroups aflopen op de geselecteerde het
		 * geselecteerde object wordt in bbpAlgorithm geplaatst
		 */
		Enumeration<AbstractButton> allRadioButtons = bppBtnGrp.getElements();

		// loop de elementen
		while (allRadioButtons.hasMoreElements()) {
			// cast het element naar een radio button
			JRadioButton temp = (JRadioButton) allRadioButtons.nextElement();

			// is hij gekozen? loop de algoritmes en zoek op een naam
			// overeenkomst, gevonden? zet dit als het gekozen object
			if (temp.isSelected())
				for (BPPAlgorithm bppAlgorithm : bppAlgorithms)
					if (bppAlgorithm.getName().equals(temp.getText()))
						this.bppAlgorithm = bppAlgorithm;
		}

		return bppAlgorithm;
	}

	/**
	 * Bepaal het gekozen tsp algoritme op basis van de radio buttons
	 *  
	 * @return TSPAlgorithm
	 */
	private TSPAlgorithm getTSPAlgorithmFromRadioButtons() {
		/**
		 * we moeten de buttongroups aflopen op de geselecteerde het
		 * geselecteerde object wordt in bbpAlgorithm geplaatst
		 */
		Enumeration<AbstractButton> allRadioButtons = tspBtnGrp.getElements();

		// loop de elementen
		while (allRadioButtons.hasMoreElements()) {
			// cast het element naar een radio button
			JRadioButton temp = (JRadioButton) allRadioButtons.nextElement();

			// is hij gekozen? loop de algoritmes en zoek op een naam
			// overeenkomst, gevonden? zet dit als het gekozen object
			if (temp.isSelected())
				for (TSPAlgorithm tspAlgorithm : tspAlgorithms)
					if (tspAlgorithm.getName().equals(temp.getText()))
						this.tspAlgorithm = tspAlgorithm;
		}

		return tspAlgorithm;
	}
}
