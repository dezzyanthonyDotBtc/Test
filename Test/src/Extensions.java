import java.io.File;

import javax.swing.JTextArea;

/**
 * Diese Klasse wird für Erweiterungen hinzugezogen, um den Grundcode nicht zu
 * sehr zu ändern
 * 
 * Stand: 23.12.2021
 * 
 * @author admin-dl
 *
 */
public class Extensions {

	// ##BugFix Slippery Slope
	// ###Stand 23.12.2021### Anpassung von doppelten Lieferscheinen

	/**
	 * Vor der eigentliche Speicherung, wird die erstellte Datei nochmals geprüft,
	 * ob sich 2 identische Lieferscheine darin befinden Wenn nicht, wird die Datei
	 * wie zuvor auch einfach gespeichert
	 * 
	 * @param s zu extrahierender String
	 * @param f die eigentliche Datei
	 * @throws Exception
	 */
	public static void checkFile(String s, File f, JTextArea display) throws Exception {

		// Einbauen eines Counters + Eine if Abfrage, wenn das RFF String mehr als 2x in
		// der Datei vorkommt keine aktion vorgenommen werden (Einfach Speichern)

		// Methode counter() wird aufgerufen
		// Übergabe des Textes, Was gesucht wird, wie lang der gesuchte String ist (0 =
		// 1; 1 = 2; 2; = 3 etc.)
		if (counter(s, "RFF+AAU", 6) == 2) {
			System.out.println("Ich habe " + counter(s, "RFF+AAU", 6) + " einen Lieferschein hinterlegt");

			// Ermittlung der Inizes der Lieferscheinnummern
			int posRFF1 = s.indexOf("RFF+AAU", 0);
			int posRFF2 = s.lastIndexOf("RFF+AAU");

			// String der 1ten Lieferscheinnummer
			StringBuilder sb = new StringBuilder();

			// String der 2ten Lieferscheinnummer
			StringBuilder sb2 = new StringBuilder();

			// Neuer String
			StringBuilder newText = new StringBuilder(s);

			// Mit dem Index aus Var posRFF1, wird die erste Lieferscheinnummer extrahiert
			// und
			sb.append(s.charAt(posRFF1 + 8));
			sb.append(s.charAt(posRFF1 + 9));
			sb.append(s.charAt(posRFF1 + 10));
			sb.append(s.charAt(posRFF1 + 11));
			sb.append(s.charAt(posRFF1 + 12));
			sb.append(s.charAt(posRFF1 + 13));
			sb.append(s.charAt(posRFF1 + 14));
			sb.append(s.charAt(posRFF1 + 15));

			// Casting des Strings in einen Int für einen besseren Vergleich
			int lieferschein1 = Integer.parseInt(sb.toString());

			// Mit dem Index aus Var posRFF2, wird die erste Lieferscheinnummer extrahiert
			// und
			sb2.append(s.charAt(posRFF2 + 8));
			sb2.append(s.charAt(posRFF2 + 9));
			sb2.append(s.charAt(posRFF2 + 10));
			sb2.append(s.charAt(posRFF2 + 11));
			sb2.append(s.charAt(posRFF2 + 12));
			sb2.append(s.charAt(posRFF2 + 13));
			sb2.append(s.charAt(posRFF2 + 14));
			sb2.append(s.charAt(posRFF2 + 15));

			// Casting des Strings in einen Int für einen besseren Vergleich
			int lieferschein2 = Integer.parseInt(sb2.toString());

			// Vergleich, ob die Lieferscheine identisch sind oder nicht.
			if (lieferschein1 == lieferschein2) {
				System.out.println("Die Lieferscheine sind identisch");

				// Lieferschein 1 wird um 1 reduziert, daher wurde der String vorhin in einen
				// int geparsed
				int lieferschein1NEWint = lieferschein1 - 1;

				// Nach der Anpassung des Lieferscheins, wird dieser wieder in einen String
				// umwandeln, um diesen einsetzen zu können
				String lieferschein1NEWString = String.valueOf(lieferschein1NEWint);

				// Ersetzen des alten Lieferscheins mit dem neu berechneten Lieferschein
				// Funktioniert leider aktuell nur so, da die replaceAll() Methode ebenso den
				// 2ten Lieferschein ersetzen würd
				newText.setCharAt(posRFF1 + 8, lieferschein1NEWString.charAt(0));
				newText.setCharAt(posRFF1 + 9, lieferschein1NEWString.charAt(1));
				newText.setCharAt(posRFF1 + 10, lieferschein1NEWString.charAt(2));
				newText.setCharAt(posRFF1 + 11, lieferschein1NEWString.charAt(3));
				newText.setCharAt(posRFF1 + 12, lieferschein1NEWString.charAt(4));
				newText.setCharAt(posRFF1 + 13, lieferschein1NEWString.charAt(5));
				newText.setCharAt(posRFF1 + 14, lieferschein1NEWString.charAt(6));
				newText.setCharAt(posRFF1 + 15, lieferschein1NEWString.charAt(7));

				// Übergabe der bearbeiteten Datei für die Speicherung
				FileImpExp.safeNewFile(newText.toString(), f, display);

			} else {
				// Wenn unterschiedlich, soll die Datei einfach wie zuvor auch gespreichert
				// werden
				// Übergabe zum Speichern der Datei
				System.out.println("Ich habe zwar 2x einen Lieferschein aber, die Lieferscheine sind NICHT identisch");
				FileImpExp.safeNewFile(s, f, display);

			}

			// Wenn der Counter nicht 2 ergibt, wird der Lieferschein nicht abgeändert
			// Anpassung von mehreren Lieferscheinen hier später noch einbauen
		} else {
			// Wenn unterschiedlich, soll die Datei einfach wie zuvor auch gespreichert
			// werden
			// Übergabe zum Speichern der Datei
			System.out.println("Ich habe mehr als 2 Lieferscheine hinterlegt");
			FileImpExp.safeNewFile(s, f, display);

		}

	}

	/**
	 * Methode zum zählen eines gewissen Strings in einem Text
	 * 
	 * @param text   Ganzer Text in dem gesucht werden soll
	 * @param var    Variable/Wort welches gesucht wird
	 * @param laenge Übergabe, wie lang das gesuchte Wort ist, um demensprechend in
	 *               den richtigen Case hinein zu springen
	 * @return counter
	 */
	public static int counter(String text, String var, int laenge) {

		// Setzen eines Counters
		int counter = 0;

		switch (laenge) {

		// CharSequence = 1
		case 0:

			// Die for Schleife zählt, wie oft das gewünschte Wort im String vorkommt
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == var.charAt(0)) {
					counter++;
				}

			}
			// CharSequence = 2
		case 1:

			// Die for Schleife zählt, wie oft das gewünschte Wort im String vorkommt
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == var.charAt(0) && text.charAt(i + 1) == var.charAt(1)) {
					counter++;
				}

			}
			// CharSequence = 3
		case 2:

			// Die for Schleife zählt, wie oft das gewünschte Wort im String vorkommt
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == var.charAt(0) && text.charAt(i + 1) == var.charAt(1)
						&& text.charAt(i + 2) == var.charAt(2)) {
					counter++;
				}

			}
			// CharSequence = 4
		case 3:

			// Die for Schleife zählt, wie oft das gewünschte Wort im String vorkommt
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == var.charAt(0) && text.charAt(i + 1) == var.charAt(1)
						&& text.charAt(i + 2) == var.charAt(2) && text.charAt(i + 3) == var.charAt(3)) {
					counter++;
				}

			}
			// CharSequence = 5
		case 4:

			// Die for Schleife zählt, wie oft das gewünschte Wort im String vorkommt
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == var.charAt(0) && text.charAt(i + 1) == var.charAt(1)
						&& text.charAt(i + 2) == var.charAt(2) && text.charAt(i + 3) == var.charAt(3)
						&& text.charAt(i + 4) == var.charAt(4)) {
					counter++;
				}

			}
			// CharSequence = 6
		case 5:

			// Die for Schleife zählt, wie oft das gewünschte Wort im String vorkommt
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == var.charAt(0) && text.charAt(i + 1) == var.charAt(1)
						&& text.charAt(i + 2) == var.charAt(2) && text.charAt(i + 3) == var.charAt(3)
						&& text.charAt(i + 4) == var.charAt(4) && text.charAt(i + 5) == var.charAt(5)) {
					counter++;
				}

			}
			// CharSequence = 7
		case 6:

			// Die for Schleife zählt, wie oft das gewünschte Wort im String vorkommt
			for (int i = 0; i < text.length(); i++) {

				if (text.charAt(i) == var.charAt(0) && text.charAt(i + 1) == var.charAt(1)
						&& text.charAt(i + 2) == var.charAt(2) && text.charAt(i + 3) == var.charAt(3)
						&& text.charAt(i + 4) == var.charAt(4) && text.charAt(i + 5) == var.charAt(5)
						&& text.charAt(i + 6) == var.charAt(6)) {
					counter++;
				}

			}

		}

		// Gibt den gewählten wert zurück
		return counter;

	}

}
