import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * 
 */

/**
 * @author Luuk
 *
 */
public class Main extends JFrame {
	/**
	 * Panels voor elke fieldset
	 */
	private JPanel xmlPanel = new XMLPanel();
	private JPanel executionPanel = new ExecutionPanel();
	private JPanel customerPanel = new CustomerPanel();
	private JPanel orderPanel = new OrderPanel();
	
	/**
	 * de linker en rechterkant van het scherm
	 * zie ctor voor gebruik
	 */
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// zet de look and feel naar windows of osx
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println("Unable to load Windows look and feel");
		}
		
		// creeer het scherm
		JFrame main = new Main();
		// TODO Auto-generated method stub
	}
	
	public Main()
	{
		setSize(1500, 900);
		
		// sluit het proces als je op kruisje drukt
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * Het scherm is op te delen in 2 kolommen
		 * De flowlayout zorgt voor de kolommen en de panels 
		 * zorgen dat we meerdere componenten in 1 kant kunnen stoppen
		 */
		setLayout(new BorderLayout());
		
		
		leftPanel.setPreferredSize(new Dimension(500, 900));

		leftPanel.add(xmlPanel);
		
		leftPanel.add(executionPanel);
		
		leftPanel.add(customerPanel);
		
		rightPanel.add(orderPanel);
		
		rightPanel.getWidth();
		
		add(leftPanel, BorderLayout.WEST);
		
		add(rightPanel, BorderLayout.CENTER);
		
		// als laatste, maak hem zichtbaar
		setVisible(true);
	}

}
