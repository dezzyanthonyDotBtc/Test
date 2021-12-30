import java.io.File;
import java.io.FilenameFilter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Diese Klasse dient zur automatischen Ausführung, um alle 30 Sekunden zu prüfen, ob eine zu bearbeitende
 * EDI Datei im Verzeichnis liegt.
 * 
 * Somit muss keiner mehr die Dateien manuell prüfen, sondern lediglich prüfen, ob das Programm läuft.
 * 
 * @author Dennis Lehle
 *
 */
public class TimerDataCheck {

	public static void timerTask(Functions func, Timer t, JTextArea display) {

		t.schedule(new TimerTask() {

			@Override
			public void run() {

				// Auslesen der EDi Dateien in diesem Verzeichnis - LIVE System
				//File root = new File("R:\\EDI\\ESCM\\OUT\\VW_CONVERT\\");
				
				File root = new File(func.getPath());
				//Auslesen Testsystem
				//File root = new File("C:\\Users\\admin-DL\\Desktop\\test\\");

				FilenameFilter beginswithm = new FilenameFilter() {
					public boolean accept(File directory, String filename) {
						return filename.startsWith("DUC");
					}
				};

				File[] files = root.listFiles(beginswithm);
				if (files != null) {

					for (File f : files) {

						try {
							FileImpExp.readFile(f,func, display);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				} else {
					System.out.println(files.toString());
			
				}

			}

		}, 0, 30000); // Alle 30 Sekunden

	}
	
	

}