package listener;

/**
 * @author Luuk
 *
 * Listener voor als er een xml bestand wordt geuplaoad
 */
public interface XMLUploadedListener {
	public abstract void xmlUploaded(String xmlFileLocation);
}
