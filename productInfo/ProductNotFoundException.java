package productInfo;

/**
 * Exception die gegooit wordt als een product niet in de database gegooit is
 * 
 * @author Tim
 *
 */
public class ProductNotFoundException extends Exception {

	/**
	 * 
	 * @param message Bericht wat het probleem is
	 */
	public ProductNotFoundException(String message)
	{
		super(message);
	}
}
