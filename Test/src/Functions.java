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

}
