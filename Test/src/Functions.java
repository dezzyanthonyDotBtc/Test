/**
 * Diese Klasse erweitert das Programm um ein paar Funktionen
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
	 * Methode zum setzen der Automatik auf true oder false
	 * @param active
	 */
	public void setAutomatik(boolean active) {
		//Referenz auf private boolean im Kopf der Klasse
		this.activated = active;
	
	}
	
	/**
	 * Methode zum erhalten/abfragen der Automatik, ob diese true oder false hinterlegt ist
	 * @param active
	 */
	public boolean getAutomatik() {
		//Referenz auf private boolean im Kopf der Klasse
		return this.activated;
	}
	
	
	/**
	 * Methode zum setzen des FileNames
	 * @param name
	 */
	public void setFileName(String name) {
		//Referenz auf private String im Kopf der Klasse
		this.fileName = name;
	
	}
	
	/**
	 * Methode zum erhalten/abfragen des FileNames, ob diese true oder false hinterlegt ist
	 * @param name
	 */
	public String getFileName() {
		//Referenz auf private String im Kopf der Klasse
		return this.fileName;
	}

}
