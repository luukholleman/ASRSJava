package asrs;
/**
 * @author Luuk
 * @date 15 april
 */
import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import listener.ExecuteButtonPressedListener;
import listener.XMLUploadedListener;

import tspAlgorithm.BruteForce;
import tspAlgorithm.Greedy;
import tspAlgorithm.TSPAlgorithm;
import tspAlgorithm.TwoOpt;
import bppAlgorithm.AlmostWorstFit;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.BestFit;
import bppAlgorithm.Circulate;
import bppAlgorithm.FirstFit;
import bppAlgorithm.WorstFit;

public class ExecutionPanel extends JPanel implements ActionListener {
	// bpp algoritmes
	private ArrayList<BPPAlgorithm> bppAlgorithms = new ArrayList<BPPAlgorithm>();
	private ArrayList<TSPAlgorithm> tspAlgorithms = new ArrayList<TSPAlgorithm>();

	// de listeners voor de simulatie en uitvoeren knoppen
	private ArrayList<ExecuteButtonPressedListener> executeButtonPressedListeners = new ArrayList<ExecuteButtonPressedListener>();

	// de 2 knoppen
	private JButton simulateBtn = new JButton("Simulatie");
	private JButton executeBtn = new JButton("Uitvoeren");

	// bpp en tsp algoritme button group, zorgt ervoor dat radio button auto
	// worden uitgezet
	private ButtonGroup bppBtnGrp = new ButtonGroup();
	private ButtonGroup tspBtnGrp = new ButtonGroup();

	// de gekozen algoritmes, wordt gevuld na uitvoering van getXAlgorithmFromRadioButtons
	private BPPAlgorithm bppAlgorithm;
	private TSPAlgorithm tspAlgorithm;

	/**
	 * ctor
	 * 
	 * @author Luuk
	 */
	public ExecutionPanel() {
		setBorder(BorderFactory.createTitledBorder("Uitvoeren"));

		setPreferredSize(new Dimension(500, 220));

		bppAlgorithms.add(new FirstFit());
		bppAlgorithms.add(new BestFit());
		bppAlgorithms.add(new Circulate());
		bppAlgorithms.add(new WorstFit());
		bppAlgorithms.add(new AlmostWorstFit());

		tspAlgorithms.add(new BruteForce());
		tspAlgorithms.add(new Greedy());
		tspAlgorithms.add(new TwoOpt());

		buildUI();
	}

	/**
	 * Voegt een listener toe, wordt getriggerd als er een nieuw bestand wordt
	 * geupload
	 * 
	 * @param xul
	 */
	public void addExecutionListener(ExecuteButtonPressedListener el) {
		executeButtonPressedListeners.add(el);
	}

	/**
	 * Triggert het simulate button press event
	 * 
	 * @param xmlFileLocation
	 */
	private void simulateButtonPressed(BPPAlgorithm bpp, TSPAlgorithm tsp) {
		// trigger elk event
		for (ExecuteButtonPressedListener ebpl : executeButtonPressedListeners)
			ebpl.simulatePressed(bpp, tsp);
	}

	/**
	 * Triggert het button press uploaded event
	 * 
	 * @param xmlFileLocation
	 */
	private void executeButtonPressed(BPPAlgorithm bpp, TSPAlgorithm tsp,
			String com1, String com2) {
		// trigger elk event
		for (ExecuteButtonPressedListener ebpl : executeButtonPressedListeners)
			ebpl.executePressed(bpp, tsp, com1, com2);
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

		bppPanel.setPreferredSize(new Dimension(150, 130));
		tspPanel.setPreferredSize(new Dimension(150, 130));
		comPanel.setPreferredSize(new Dimension(170, 130));

		// de boxlayout laat de elementen stapelen
		bppPanel.setLayout(new BoxLayout(bppPanel, BoxLayout.PAGE_AXIS));
		tspPanel.setLayout(new BoxLayout(tspPanel, BoxLayout.PAGE_AXIS));
				
		// hoofdlabel
		bppPanel.add(new JLabel("BPP algoritme"));
		
		boolean firstBpp = true;

		// loop de bpp algoritmes en plaats de namen in radiobuttons
		for (BPPAlgorithm bppAlgorithm : bppAlgorithms) {
			JRadioButton rdBtn = new JRadioButton(bppAlgorithm.getName());
			bppBtnGrp.add(rdBtn);
			bppPanel.add(rdBtn);
			
			// de eerste moet altijd geselecteerd zijn
			if(firstBpp) {
				rdBtn.setSelected(true);
				firstBpp = false;
			}
		}

		// hoofdlabel
		tspPanel.add(new JLabel("TSP algoritme"));
		
		boolean firstTsp = true;

		// loop de tsp algoritmes en plaats de namen in radiobuttons
		for (TSPAlgorithm tspAlgorithm : tspAlgorithms) {
			JRadioButton rdBtn = new JRadioButton(tspAlgorithm.getName());
			tspBtnGrp.add(rdBtn);
			tspPanel.add(rdBtn);
			
			// de eerste moet altijd geselecteerd zijn
			if(firstTsp) {
				rdBtn.setSelected(true);
				firstTsp = false;
			}
		}
		
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox petList = new JComboBox(petStrings);
		petList.setSelectedIndex(4);
		petList.addActionListener(this);
		
		comPanel.add(petList);

		simulateBtn.addActionListener(this);
		executeBtn.addActionListener(this);

		simulateBtn.setPreferredSize(new Dimension(235, 50));
		executeBtn.setPreferredSize(new Dimension(235, 50));
		
		add(bppPanel);
		add(tspPanel);
		add(comPanel);
		add(simulateBtn);
		add(executeBtn);
	}

	/**
	 * Action performed
	 * 
	 * @author Luuk
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == simulateBtn || e.getSource() == executeBtn) {
			getBPPAlgorithmFromRadioButtons();
			getTSPAlgorithmFromRadioButtons();
		}
		
		if(e.getSource() == simulateBtn) {
			if(bppAlgorithm == null || tspAlgorithm == null)
				JOptionPane.showMessageDialog(this, "Selecteer eerst twee algoritmes.");
			else
				simulateButtonPressed(bppAlgorithm, tspAlgorithm);
		}
		if(e.getSource() == executeBtn) {
			if(bppAlgorithm == null || tspAlgorithm == null)
				JOptionPane.showMessageDialog(this, "Selecteer eerst twee algoritmes.");
			else
				executeButtonPressed(bppAlgorithm, tspAlgorithm, "com 1", "com 2");
		}		
	}

	/**
	 * Bepaal het gekozen tsp algoritme op basis van de radio buttons
	 * 
	 * @author Luuk
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
	 * @author Luuk
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
