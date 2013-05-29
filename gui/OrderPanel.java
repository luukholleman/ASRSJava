package gui;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import productInfo.Order;
import productInfo.Product;

/**
 * @author Luuk
 * 
 * Deze class zorgt voor het weergeven van de productinformatie in een tabel
 */
public class OrderPanel extends JPanel {

	private ProductModel productModel = new ProductModel();

	/**
	 * ctor
	 * 
	 * @author Luuk
	 */
	public OrderPanel() {
		setBorder(BorderFactory.createTitledBorder("Order"));

		setPreferredSize(new Dimension(670, 650));

		buildUI();
	}

	/**
	 * Bouwt de ui
	 * 
	 * @author Luuk
	 */
	private void buildUI() {
		// maak een tabel op basis van de nestedclass product model
		JTable table = new JTable(productModel);

		// zet de afmetingen
		table.getTableHeader().setPreferredSize(new Dimension(620, 20));
		table.setPreferredSize(new Dimension(620, 580));
		table.setPreferredScrollableViewportSize(new Dimension(650, 580));

		// cellen zijn niet aanpasbaar
		table.setEnabled(false);

		add(table.getTableHeader());
		add(table);
	}

	/**
	 * Zet de order die wordt weergegeven in de tabel, wordt automatisch
	 * geupdate
	 * 
	 * @author Luuk
	 * 
	 * @param order
	 */
	public void setOrder(Order order) {
		// eerst de tabel leeggooie
		productModel.removeAllRows();

		// producten toevoegen aan de productmodel
		for (Product product : order.getProducts()) {
			productModel.addElement(product);

			// fire het update event van dit product
			productModel.fireTableRowsUpdated(
					productModel.products.indexOf(product),
					productModel.products.indexOf(product));
		}
	}

	/**
	 * Update een status van een product, tabel wordt automatisch geupdate
	 * 
	 * @author Luuk
	 * 
	 * @param product
	 * @param status
	 */
	public void updateStatus(Product product, String status) {
		int index = productModel.products.indexOf(product);

		product.setStatus(status);

		productModel.fireTableRowsUpdated(index, index);
	}

	/**
	 * Nested class die wordt gebruikt door de tabel om hem dynamisch te maken
	 * 
	 * @author Luuk
	 * 
	 */
	private class ProductModel extends AbstractTableModel {

		/**
		 * De kolomnamen
		 */
		private final String[] columnNames = { "#", "Omschrijving", "Prijs",
				"Grootte", "Status" };

		/**
		 * Lijst van weer te geven producten
		 */
		private final LinkedList<Product> products;

		/**
		 * Ctor
		 * 
		 * @author Luuk
		 */
		private ProductModel() {
			products = new LinkedList<Product>();
		}

		/**
		 * Bepaalt de kolomnaam
		 * 
		 * @return string
		 */
		public String getColumnName(int column) {
			return columnNames[column];
		}

		/**
		 * Voeg een product toe aan de tabel, wordt automatisch geupdate
		 * 
		 * @author Luuk
		 * 
		 * @param product
		 */
		public void addElement(Product product) {
			// Adds the element in the last position in the list
			products.add(product);
			fireTableRowsInserted(products.size() - 1, products.size() - 1);
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return products.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return products.get(rowIndex).getId();
			case 1:
				return products.get(rowIndex).getDescription();
			case 2:
				return products.get(rowIndex).getPrice();
			case 3:
				return products.get(rowIndex).getSize();
			case 4:
				return products.get(rowIndex).getStatus();
			}
			return null;
		}

		/**
		 * Verwijder alle regels
		 * 
		 * @author Tim
		 */
		public void removeAllRows() {
			fireTableRowsDeleted(0, getRowCount());
			products.clear();
		}
	}
}
