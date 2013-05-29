package asrs;

/**
 * Expeption die gegooit wordt als de database connectie mislukt
 * @author Tim
 *
 */
public class DatabaseConnectionFailedException extends Exception {
	/**
	 * 
	 * @param message Bericht over de exception
	 */
	public DatabaseConnectionFailedException(String message)
	{
		super(message);
	}
}
