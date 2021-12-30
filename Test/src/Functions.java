import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * Diese Klasse erweitert das Programm um ein paar Funktionen
 * 
 * @author Dezzy
 *
 */
public class Functions {

	/**
	 * Private boolean um Aktivierungen abzuspeichern
	 */
	private boolean activated;

	/**
	 * Private String um Namen, der verarbeiteten Dateien abzuspeichern
	 */
	private String fileName;

	/**
	 * Private String um den Pfad zu speichern
	 */
	private static String path;

	/**
	 * Private String um den Archiv Pfad zu speichern
	 */
	private static String pathArchive;
	
	/**
	 * Private boolean um validit�t eines Links zu hinterlegen
	 */
	boolean isValid = false;

	/**
	 * Methode zum setzen der Automatik auf true oder false
	 * 
	 * @param active
	 */
	public void setAutomatik(boolean active) {
		// Referenz auf private boolean im Kopf der Klasse
		this.activated = active;

	}

	/**
	 * Methode zum erhalten/abfragen der Automatik, ob diese true oder false
	 * hinterlegt ist
	 * 
	 * @param active
	 */
	public boolean getAutomatik() {
		// Referenz auf private boolean im Kopf der Klasse
		return this.activated;
	}

	/**
	 * Methode zum setzen des FileNames
	 * 
	 * @param name
	 */
	public void setFileName(String name) {
		// Referenz auf private String im Kopf der Klasse
		this.fileName = name;

	}

	/**
	 * Methode zum erhalten/abfragen des FileNames, ob diese true oder false
	 * hinterlegt ist
	 * 
	 * @param name
	 */
	public String getFileName() {
		// Referenz auf private String im Kopf der Klasse
		return this.fileName;
	}

	/**
	 * Methode zum setzen des FileNames
	 * 
	 * @param name
	 */
	public void setPath(String pathP) {
		// Referenz auf private String im Kopf der Klasse
		this.path = pathP;

	}

	/**
	 * Methode zum erhalten/abfragen des FileNames, ob diese true oder false
	 * hinterlegt ist
	 */
	public String getPath() {
		// Referenz auf private String im Kopf der Klasse
		return this.path;
	}

	/**
	 * Methode zum setzen des FileNames
	 * 
	 * @param name
	 */
	public void setPathArchive(String pathPA) {
		// Referenz auf private String im Kopf der Klasse
		this.pathArchive = pathPA;

	}

	/**
	 * Methode zum erhalten/abfragen des Archiv-Pfads
	 */
	public String getPathArchive() {
		// Referenz auf private String im Kopf der Klasse
		return this.pathArchive;
	}
	
	/**
	 * Methode zum setzen eines Validen Pfades
	 * 
	 * @param name
	 */
	public void setValidity(boolean valid) {
		// Referenz auf private String im Kopf der Klasse
		this.isValid = valid;

	}

	/**
	 * Methode zum erhalten/abfragen der Validit�t eins Pfades
	 */
	public boolean getValidity() {
		// Referenz auf private String im Kopf der Klasse
		return this.isValid;
	}
	
}
