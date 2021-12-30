import java.io.BufferedReader;
import static java.nio.file.StandardCopyOption.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.util.Timer;
import java.util.TimerTask;

/**
 * In dieser Klasse werden Importe und Exporte von Lieferabrufen verwaltet.
 * 
 * @author Dennis Lehle
 *
 */
public class FileImpExp {

	static String pathA;
	static String pathArch;
	/**
	 * Diese Methode liest die zu konvertierenden Lieferabrufe ein und speichert
	 * diese in ein Vector ab.
	 * 
	 * @throws Exception
	 */
	public static void readFile(File files, Functions func,  JTextArea display) throws Exception {
		pathA = func.getPath();
		pathArch = func.getPathArchive();
	
		
		display.append(files.getName() + "...Datei gefunden....Verarbeitung...... \n");
		
		StringBuilder sb = new StringBuilder();
		// Hier wird die Datei eingelesen.
		BufferedReader br = new BufferedReader(new FileReader(files));

		// String in der der ganze Lieferabruf gespeichert wird
		String st;

		// Abfrage = solange der Inhalt der Textdatei keinen Wert mehr enthält wird
		// ausgelesen.
		while ((st = br.readLine()) != null) {
			
				sb.append(st);
				sb.append("\n");
			
		}
		br.close();
		br = null;
		//System.out.println(st);
		// Diese statische Methode der Klasse CheckFiles prüft eingelesene EDI Dateien.
		CheckFile.checkVWPALandCPS(sb.toString(), files, display);

	}

	/**
	 * Diese Methode speichert eine veränderte EDI Datei im hinterlegten Verzeichnis
	 * der EDI Schnittstelle mit neuer Debitornummer.
	 */
	public static void safeNewFile(String newFile, File file, JTextArea display) throws Exception {
			
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(pathA+ file.getName()));
			display.append(file.getName()+"....abgeschossen.....");
			System.out.println(pathA + " Bin in der Speicherung ");
		//	display.append("Automatik gestoppt... \n");
			// Eintragung wo die Datei geschrieben wird - Test
			//BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\admin-DL\\Desktop\\TestOut\\" + file.getName()));

			// LIVE-Verzeichnis hier wird gearbeitet und an den Kunden gesendet..
			//BufferedWriter writer = new BufferedWriter(new FileWriter("R:\\EDI\\ESCM\\OUT\\" + file.getName()));

			// Writer schließen und alles auf null setzen, dient zur Löschung der Files.
			writer.write(newFile);
			writer.close();
			writer = null;

			// Bearbeitetes File wird nun entfernt.
			safeNewFileIntoArchiv(newFile, file);
			//deleteFile(file);
	
	}
	
	/**
	 * Diese Methode speichert eine veränderte EDI Datei im hinterlegten Verzeichnis
	 * der EDI Schnittstelle mit neuer Debitornummer.
	 */
	public static void safeNewFileIntoArchiv(String newFile, File file) throws Exception {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(pathArch+ file.getName()));
		System.out.println(pathArch + " Bin im ARCHIV ");
		// Eintragung wo die Datei geschrieben wird - Test
		//BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\admin-DL\\Desktop\\TestOut\\" + file.getName()));

		// LIVE-Archiv-Verzeichnis hier wird gearbeitet und an den Kunden gesendet..
		//BufferedWriter writer = new BufferedWriter(new FileWriter("R:\\EDI\\ESCM\\OUT\\VW_CONVERT\\VW_OUT\\VW_ARCHIV\\" + file.getName()));

		// Writer schließen und alles auf null setzen, dient zur Löschung der Files.
		writer.write(newFile);
		writer.close();
		writer = null;

		// Bearbeitetes File wird nun entfernt.
		deleteFile(file);

	}

	/**
	 * Methode zum löchen von Files nach der Verarbeitung
	 * 
	 * @param path EDI Datei
	 */
	public static void deleteFile(File path) {
		
		path.delete();
	

		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					deleteFile(files[i]);
				} else {
					files[i].delete();
				}
			}

		}
	}
	
	
}
