import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
	
	
	
	public static void main(String[]args) throws IOException {
		//Properties-Objekt erstellen
		Properties prob = new Properties();
		
		//Auslesen eines Pfades aus der Config Datei
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.properties"));
		prob.load(stream);
		stream.close();
		String path = prob.getProperty("path");	
		
		
		
		//Auslesen eines Pfades aus der Config Datei
		BufferedInputStream streamArchive = new BufferedInputStream(new FileInputStream("config.properties"));
		prob.load(streamArchive);
		stream.close();
		String archivePath = prob.getProperty("archivePath");	
		
		//AUfruf des Konstruktors und Übergabe der aktuellen Pfade
		Menu nml = new Menu(path,archivePath);
	     nml.setVisible(true);

	}

}
