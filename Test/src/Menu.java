import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Menu extends JFrame {

	// Timer Objekt
	Timer t = new Timer();

	// Objekt erzeugen, um auf die setAutomatik Funktion zugreifen zu k�nnen
	Functions f = new Functions();

	JTextArea display = new JTextArea(12, 40);

	// Menüleiste
	JMenuBar menuBar;

	// Menü "Datei"
	JMenu fileMenu;

	// Menü "Datei"
	JMenu infoMenu;

	// Menü "Datei"
	JLabel userMenu;

	// Menüpunkt "Öffnen"
	JMenuItem openItem;

	// Menüpunkt "Schließen"
	JMenuItem closeItem;

	// Menüpunkt "Support"
	JMenuItem support;

	// Menüpunkt "Version"
	JMenuItem version;

	// Button Start Automatik
	JButton startAutomatik = new JButton("Start Automatik");

	// JButton Stop Automatik
	JButton stopAutomatik = new JButton("Stop Automatik");

	// Button Pfad bearbeiten
	JButton changePath = new JButton("Bearbeiten");

	// JButtonPfad speichern
	JButton safePath = new JButton("Speichern");

	// Button Pfad bearbeiten
	JButton changePathArchive = new JButton("Bearbeiten");

	// JButtonPfad speichern
	JButton safePathArchive = new JButton("Speichern");

	// Buttons für Ja & Nein Dialog Fenster
	JButton yesButton = new JButton("Ja");
	JButton noButton = new JButton("Nein");

	// Buttons für Ja & Nein Dialog Fenster-Archiv
	JButton yesButtonArchive = new JButton("Ja");
	JButton noButtonArchive = new JButton("Nein");

	// Dialog Fenster zur Abfrage von Speicherung des Pfades
	Dialog dialog = new JDialog();

	// Dialog Fenster zur Abfrage von Speicherung des Pfades
	Dialog dialogArchiv = new JDialog();

	// Haupt-Panel zu dem alle einzelnen Panels hinzugefügt werden
	JPanel mainPanel = new JPanel();

	/**
	 * Aufbau des gesamten Menues des Progamms
	 * 
	 * @param path1        Lese-Pfad
	 * @param archivePath1 Archiv-Pfad
	 */
	public Menu(String path1, String archivePath1) {

		// java.net.URL url = ClassLoader.getSystemResource("logo.png");
		// Titel der Applikation setzen
		this.setTitle("VW Converter - 2.1.0");

		// Größe des Frames hinterlegen
		this.setSize(550, 460);

		// Menschliche Vergrößerung des Fensters vermeiden
		this.setResizable(false);

		// Die Applikation öffnet das Fenster in der Mitte des Monitors
		this.setLocationRelativeTo(null);

		// Menüleiste wird erzeugt
		menuBar = new JMenuBar();

		// Menü "Datei" wird erzeugt
		fileMenu = new JMenu("Datei");

		// Menü "Info" wird erzeugt
		infoMenu = new JMenu("Info");

		// Menü "User" wird erzeugt
		userMenu = new JLabel("Benutzer: " + System.getProperty("user.name"));

		// Erzeugen eines Panels für den Pfad
		JPanel panel = new JPanel();

		// Erzeugen eines Panels für den Archiv-Pfad
		JPanel panelArchive = new JPanel();

		// Erzeugung des Button Panels der Automation des Programms
		JPanel automatik = new JPanel();

		// Erzeugen eines Labels
		JLabel label = new JLabel("READ:");

		// Erzeugen eines Labels
		ImageIcon icon4 = new ImageIcon("logo.png");
		JLabel labellogo = new JLabel(icon4);

		// Erzeugen eines Labels
		JLabel labelArchive = new JLabel("ARCHIV:");

		// Erzeugen eines TextFields - read-Path
		JTextField path = new JTextField("", 22);
		path.setText(path1);
		path.setForeground(Color.black.brighter());
		path.setBackground(Color.white);
		// Textfield ausgrauen
		path.setEnabled(false);
		// Das Feld wird auf nicht editierbar gesetzt, da dies vom Button freigegeben
		// werden soll
		path.setEditable(false);

		// Erzeugen eines TextFields
		JTextField pathArchive = new JTextField("", 21);
		pathArchive.setText(archivePath1);
		pathArchive.setForeground(Color.black.brighter());
		pathArchive.setBackground(Color.white);

		// Textfield ausgrauen
		pathArchive.setEnabled(false);
		// Das Feld wird auf nicht editierbar gesetzt, da dies vom Button freigegeben
		// werden soll
		pathArchive.setEditable(false);

		// -----------Label, TextField & Buttons für den Pfad hinzufügen
		// Label dem Panel hinzufügen
		panel.add(label);
		// TextField dem panel hinzufügen
		panel.add(path);
		// Buttons zur Bearbeitung/Speicherung des neuen Pfades
		panel.add(changePath);
		panel.add(safePath);

		// -----------Label, TextField & Buttons für den Archiv-Pfad hinzufügen
		// Label dem Panel hinzufügen
		panelArchive.add(labelArchive);
		// TextField dem panel hinzufügen
		panelArchive.add(pathArchive);
		// Buttons zur Bearbeitung/Speicherung des neuen Pfades
		panelArchive.add(changePathArchive);
		panelArchive.add(safePathArchive);

		// ----------Buttons für die Automatik hinzufügen
		automatik.add(startAutomatik);
		automatik.add(stopAutomatik);
		// Logo hinzuf�gen
		automatik.add(labellogo);

		// Hinzufügen einer Scrollabl Area für Infos zur Verarbeitung
		JPanel infoPanel = new JPanel();

		infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Info Area"));
		display.setEditable(false);
		// set textArea non-editable
		JScrollPane scroll = new JScrollPane(display);

		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// Add Textarea in to middle panel
		infoPanel.add(scroll);

		// Alle dem PANEL hinzugefügten Felder/ Buttons etc. dem JFrame hinzufügen,
		// daher this., da die Klasse selber ein JFrame ist
		mainPanel.add(panel);
		mainPanel.add(panelArchive);
		mainPanel.add(automatik);
		mainPanel.add(infoPanel);

		// Main-Panel dem JFrame hinzufügen
		this.add(mainPanel);

		// Menüpunkte werden erzeugt
		openItem = new JMenuItem("Manuell verarbeiten");
		closeItem = new JMenuItem("Schliessen");
		support = new JMenuItem("Support");
		version = new JMenuItem("Version");

		// Menüpunkte werden dem Datei-Menü hinzugefügt
		fileMenu.add(openItem);
		fileMenu.add(closeItem);
		infoMenu.add(support);
		infoMenu.add(version);

		// Datei-Menü wird der Menüleiste hinzugefügt
		menuBar.add(fileMenu);
		menuBar.add(infoMenu);
		menuBar.add(userMenu);

		// Menüleiste wird dem JFrame hinzugefügt
		this.add(menuBar, BorderLayout.NORTH);

		/**
		 * Ab hier werden die ActionListener implementiert, damit der Converter auf
		 * Knopfdrücke reagieren kann
		 */

//-------------------MENÜBAR-Implementierung-------------------------------------------------------------------

		// ActionListener wird als anonyme Klasse eingebunden
		openItem.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Dateiauswahldialog wird erzeugt...
				JFileChooser fc = new JFileChooser();
				// ... und angezeigt
				fc.showOpenDialog(null);
				// Speichert das ausgew�hlte File in Variable File
				File file = fc.getSelectedFile();

				// Manuelle Verarbeitung einzelner Dateien
				if (file != null) {

					try {
						// setzen der Pfade in die functions Methoden
						f.setPath(path.getText());
						f.setPathArchive(pathArchive.getText());

						FileImpExp.readFile(file, f, display);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					display.append("\n" + "Auswahl abgebrochen..........\n");
				}

			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		closeItem.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Programm schließen
				System.exit(0);
				dispose();
			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		support.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Bild für die Erroranzeige wird erzeigt
				ImageIcon icon = new ImageIcon("support.png");
				// Display die Warnung
				JOptionPane.showMessageDialog(null,
						"Mail: NAV-Support@npr-europe.com" + "\n" + "Tel: 0000" + "\n" + "\n" + "Autor: Dennis Lehle",
						"Support", JOptionPane.INFORMATION_MESSAGE, icon);

			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		version.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Bild für die Erroranzeige wird erzeigt
				ImageIcon icon = new ImageIcon("infoVersion.png");
				// Display die Warnung
				JOptionPane.showMessageDialog(null,
						"2.0.0 - MainFrame - release: 08.08.2020" + "\n" + "2.0.1 - NoName - release: 21.01.2021" + "\n"
								+ "2.0.2 - Slippery Slope - release: 23.12.2021" + "\n"
								+ "2.1.0 - NewLight - release: (Datum)"

						, "Versionsverlauf", JOptionPane.INFORMATION_MESSAGE, icon);

			}
		});

//-------------------DIALOG-FENSTER-Implementierung-SPEICHERUNG-NEUER-PFAD-----------------------------------

		// ActionListener wird als anonyme Klasse eingebunden
		changePath.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Das Textfeld wird bearbeitbar und die Ausgrauung wird entfernt

				boolean activ = f.getAutomatik();
				System.out.println(activ);
				if (activ == false) {
					path.setEnabled(true);
					path.setEditable(true);
				} else {
					// Bild für die Erroranzeige wird erzeigt
					ImageIcon icon = new ImageIcon("error.png");
					// Display die Warnung
					JOptionPane.showMessageDialog(null, "Automatische Verarbeitung AKTIV \n", "Meldung",
							JOptionPane.INFORMATION_MESSAGE, icon);
				}
			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		safePath.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Properties-Objekt erstellen
				Properties prob = new Properties();
				// Auslesen eines Pfades aus der Config Datei
				BufferedInputStream stream;
				try {
					stream = new BufferedInputStream(new FileInputStream("config.properties"));
					prob.load(stream);
					stream.close();
					String pathActual = prob.getProperty("path");

					// Auslesen der Aktivierung der Automatik
					boolean activ = f.getAutomatik();

					// Prüfen, ob die Automatik noch aktiv ist
					if (activ == true) {
						path.setText(pathActual);
						ImageIcon icon = new ImageIcon("error.png");
						JOptionPane.showMessageDialog(null, "Automatische Verarbeitung AKTIV \n", "Stop",
								JOptionPane.INFORMATION_MESSAGE, icon);
					} else {
						// Vergleich neuer INPUT mit altem INPUT
						if (path.getText().toString().equals(pathActual)) {
							// Ausgrgauung wird aktiviert
							path.setEnabled(false);
							// Bild für die Erroranzeige wird erzeigt
							ImageIcon icon = new ImageIcon("error.png");
							// Display die Warnung
							JOptionPane.showMessageDialog(null, "Uppps - Keine Aenderung erkannt", "Meldung",
									JOptionPane.INFORMATION_MESSAGE, icon);

						} else {
							//Icon f�r die Abfrage "?" erstellen
							ImageIcon icon2 = new ImageIcon("check.png");
							//Weitere Abfrage, ob der hintelregte Pfad G�ltigkeit hat
							if (f.pathValidity(path.getText().toString())== true) {				
							// Erstellen des DialogFensters, Panel und der Ja und Nein Buttons
							JPanel panelButton = new JPanel();
							JLabel lab = new JLabel ("        Wollen Sie den Pfad wirklich Aendern?                   ");
							JLabel lab2 = new JLabel (icon2);
							// Hinzufügen der Button zum Panel
							panelButton.add(lab2);
							panelButton.add(lab);
							panelButton.add(yesButton);
							panelButton.add(noButton);
							// Sichtbarkeit des Panels setzen
							panelButton.setVisible(true);
							// Panel dem Dialog Fenster hinzufügen
							dialog.add(panelButton);
							// Setzen des Titels, muss hier geschehen, da nicht über den Konstruktor
							// möglich
							dialog.setTitle("Meldung");
							// Setzen der DialogFenster größe
							dialog.setSize(400, 150);
							// Fenster öffnet sich in der Mitte des Monitors
							dialog.setLocationRelativeTo(null);
							// Setzen des Objektes/Fensters auf Modual (Modal = keine Schließung oder
							// Änderunga anderer Fenster möglich
							dialog.setModal(true);
							// Deaktivierung der menschlichen Fensteranpassung
							dialog.setResizable(false);
							// Sichtbarkeit des Fensters setzen
							dialog.setVisible(true);
							// Nach der Speicherung, soll das Feld erneut auf NICHT bearbeitbar gesetzt
							// werden
							path.setEditable(false);
							// Nach der Speicherung wird erneut das Fels ausgegraut
							path.setEnabled(false);
							
							} else {
								// Setzen der Editierbarkeit auf false
								path.setEditable(false);
								// Setzen der Aktivierung auf false
								path.setEnabled(false);
								//Aufruf des aktuellen hinterlgten "G�LTIGEN" Pfades, welche in den properties hintergt ist
								path.setText(pathActual);
								// Seten des Bildes f�r die Meldung
								ImageIcon icon = new ImageIcon("error.png");
								// DialogMessage wird erzeugt, um dem User zu sagen, hier falsches Directory
								// hinterlegt
								JOptionPane.showMessageDialog(null, "Kein g�ltiger Read-Pfad hinterlegt!!!",
										"Meldung", JOptionPane.INFORMATION_MESSAGE, icon);
							}
						}

					}
				} catch (IOException e1) {
					display.append("\n" + e1 + "\n");
					e1.printStackTrace();
				}

			}
		});
		// ActionListener wird als anonyme Klasse eingebunden
		yesButton.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {

				// Das Textfeld wird bearbeitbar

				try {
					FileInputStream in = new FileInputStream("config.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();

					FileOutputStream out = new FileOutputStream("config.properties");
					props.setProperty("path", path.getText().toString());
					props.store(out, null);
					out.close();

				} catch (FileNotFoundException e1) {
					display.append("\n" + e1 + "\n");
					e1.printStackTrace();
				} catch (IOException e1) {
					display.append("\n" + e1 + "\n");
					e1.printStackTrace();
				}

				path.setEditable(false);
				path.setEnabled(false);
				dialog.setVisible(false);
				dialog.dispose();
				ImageIcon icon = new ImageIcon("check.png");
				JOptionPane.showMessageDialog(null, "Aenderung erfolgreich", "Meldung", JOptionPane.INFORMATION_MESSAGE,
						icon);

				// erneute Abfrage der Properties und ersezen des Wertes in dieser Klasse, um
				// nach Speicherung nicht nochmal speichern zu können

			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		noButton.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {

				// Das Textfeld wird bearbeitbar = NEIN & der alte Wert wird erneut geladen, da
				// keine Änderung gewünscht
				path.setText(path1);
				path.setEditable(false);
				path.setEnabled(false);
				dialog.setVisible(false);
				dialog.dispose();
			}
		});

//-------------------DIALOG-FENSTER-Implementierung-SPEICHERUNG-NEUER-ARCHIV-PFAD-----------------------------------

		// ActionListener wird als anonyme Klasse eingebunden
		changePathArchive.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Das Textfeld wird bearbeitbar und die Ausgrauung wird entfernt
				boolean activ = f.getAutomatik();
				System.out.println(activ);
				if (activ == false) {
					pathArchive.setEnabled(true);
					pathArchive.setEditable(true);
				} else {
					// Bild für die Erroranzeige wird erzeigt
					ImageIcon icon = new ImageIcon("error.png");
					// Display die Warnung
					JOptionPane.showMessageDialog(null, "Automatische Verarbeitung AKTIV \n", "Meldung",
							JOptionPane.INFORMATION_MESSAGE, icon);
				}
			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		safePathArchive.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {

				// Properties-Objekt erstellen
				Properties prob = new Properties();
				// Auslesen eines Pfades aus der Config Datei
				BufferedInputStream stream;
				try {
					stream = new BufferedInputStream(new FileInputStream("config.properties"));
					prob.load(stream);
					stream.close();
					String pathActualArchiv = prob.getProperty("archivePath");

					// Auslesen der Aktivierung der Automatik
					boolean activ = f.getAutomatik();

					// Prüfen, ob die Automatik noch aktiv ist
					if (activ == true) {
						pathArchive.setText(pathActualArchiv);
						ImageIcon icon = new ImageIcon("error.png");
						JOptionPane.showMessageDialog(null, "Automatische Verarbeitung AKTIV \n", "Meldung",
								JOptionPane.INFORMATION_MESSAGE, icon);
					} else {

						// Vergleich neuer INPUT mit altem INPUT
						if (pathArchive.getText().toString().equals(pathActualArchiv)) {
							// Ausgrgauung wird aktiviert
							pathArchive.setEnabled(false);
							// Bild für die Erroranzeige wird erzeigt
							ImageIcon icon = new ImageIcon("error.png");
							// Display die Warnung
							JOptionPane.showMessageDialog(null, "Uppps - Keine Aenderung erkannt", "Meldung",
									JOptionPane.INFORMATION_MESSAGE, icon);

						} else {
							
							//Icon f�r die Abfrage "?" erstellen
							ImageIcon icon2 = new ImageIcon("check.png");							
							//Weitere Abfrage, ob der hintelregte Pfad G�ltigkeit hat
							if (f.pathValidity(pathArchive.getText().toString())== true) {
								// Erstellen des DialogFensters, Panel und der Ja und Nein Buttons
								JPanel panelButtonAr = new JPanel();	
								// Hinzufügen der Button zum Panel
								JLabel lab = new JLabel ("        Wollen Sie den Pfad wirklich Aendern?                   ");
								JLabel lab2 = new JLabel (icon2);			
								// Hinzufügen der Button & Label zum Panel
								panelButtonAr.add(lab2);
								panelButtonAr.add(lab);
								panelButtonAr.add(yesButtonArchive);
								panelButtonAr.add(noButtonArchive);
								// Sichtbarkeit des Panels setzen
								panelButtonAr.setVisible(true);
								// Panel dem Dialog Fenster hinzufügen
								dialogArchiv.add(panelButtonAr);
								// Setzen des Titels, muss hier geschehen, da nicht über den Konstruktor
								// möglich
								dialogArchiv.setTitle("Meldung");
								// Setzen der DialogFenster größe
								dialogArchiv.setSize(400, 150);
								// Fenster öffnet sich in der Mitte des Monitors
								dialogArchiv.setLocationRelativeTo(null);
								// Deaktivierung der menschlichen Fensteranpassung
								dialogArchiv.setResizable(false);
								// Setzen des Objektes/Fensters auf Modual (Modal = keine Schließung oder
								// Änderunga anderer Fenster möglich
								dialogArchiv.setModal(true);
								// Sichtbarkeit des Fensters setzen
								dialogArchiv.setVisible(true);
								// Nach der Speicherung, soll das Feld erneut auf NICHT bearbeitbar gesetzt
								// werden
								pathArchive.setEditable(false);
								// Nach der Speicherung wird auch das Feld erneut ausgegaurt
								pathArchive.setEnabled(false);
							} else {
								// Setzen der Editierbarkeit auf false
								pathArchive.setEditable(false);
								// Setzen der Aktivierung auf false
								pathArchive.setEnabled(false);
								//Aufruf des  aktuellen "G�LTIGEN" Pfades
								pathArchive.setText(pathActualArchiv);
								// Seten des Bildes f�r die Meldung
								ImageIcon icon = new ImageIcon("error.png");
								// DialogMessage wird erzeugt, um dem User zu sagen, hier falsches Directory
								// hinterlegt
								JOptionPane.showMessageDialog(null, "Kein g�ltiger Archiv-Pfad hinterlegt!!!",
										"Meldung", JOptionPane.INFORMATION_MESSAGE, icon);
							}
						}
					}
				} catch (IOException e1) {
					display.append("\n" + e1 + "\n");
					e1.printStackTrace();
				}

			}

		});

		// ActionListener wird als anonyme Klasse eingebunden
		yesButtonArchive.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {

				// Das Textfeld wird bearbeitbar
				try {
					FileInputStream in = new FileInputStream("config.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();

					FileOutputStream out = new FileOutputStream("config.properties");
					props.setProperty("archivePath", pathArchive.getText().toString());
					props.store(out, null);
					out.close();

				} catch (FileNotFoundException e1) {
					display.append("\n" + e1 + "\n");
					e1.printStackTrace();
				} catch (IOException e1) {
					display.append("\n" + e1 + "\n");
					e1.printStackTrace();
				}

				// Das Textfeld wird bearbeitbar
				// pathArchive.setText(archivePath1);
				pathArchive.setEditable(false);
				pathArchive.setEnabled(false);
				dialogArchiv.setVisible(false);
				dialogArchiv.dispose();

				ImageIcon icon = new ImageIcon("check.png");
				JOptionPane.showMessageDialog(null, "Aenderung erfolgreich", "Meldung", JOptionPane.INFORMATION_MESSAGE,
						icon);

				// erneute Abfrage der Properties und ersezen des Wertes in dieser Klasse, um
				// nach Speicherung nicht nochmal speichern zu können
			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		noButtonArchive.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {

				// Das Textfeld wird bearbeitbar
				pathArchive.setText(archivePath1);
				pathArchive.setEditable(false);
				pathArchive.setEnabled(false);
				dialogArchiv.setVisible(false);
				dialogArchiv.dispose();

			}
		});

//--------------------------Buttons-für-die-Automatik-des-Converters-------------------------------       		

		// ActionListener wird als anonyme Klasse eingebunden
		startAutomatik.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Um das aktuelle Datum abzufragen
				DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");

				if (f.getAutomatik() == false) {
					// Setzen der Editierbarkeit auf false, wenn die Automaitk gestartet wird
					path.setEnabled(false);
					path.setEditable(false);
					pathArchive.setEnabled(false);
					pathArchive.setEditable(false);
					display.append("\n" + dtf3.format(LocalDateTime.now()) + " - " + "Automatik gestartet.... \n");
					// Setzen der Automatik von false auf true
					f.setAutomatik(true);
					// Properties-Objekt erstellen
					Properties probPath = new Properties();
					Properties probPathArchive = new Properties();
					// Auslesen eines Pfades aus der Config Datei
					BufferedInputStream stream;
					// Auslesen eines Pfades aus der Config Datei
					BufferedInputStream streamArchive;
					try {
						stream = new BufferedInputStream(new FileInputStream("config.properties"));
						streamArchive = new BufferedInputStream(new FileInputStream("config.properties"));

						probPath.load(stream);
						probPathArchive.load(streamArchive);

						stream.close();
						streamArchive.close();

						// Speicherung der aktuellen Werte f�r die Pfade (Read & Archiv)
						String pathActual = probPath.getProperty("path");
						String archivePathActual = probPathArchive.getProperty("archivePath");

						// Setzen der aktuallen Pfade, falls eine ungespeicherte �nderung in den
						// Textfeldern hijnterlegt wurde
						path.setText(pathActual);
						pathArchive.setText(archivePathActual);
						// Setzen des Pfades in ein Objekt
						f.setPath(path.getText());
						f.setPathArchive(pathArchive.getText());

						// METHODE
						// Einsteigen in die Verarbeitung
						TimerDataCheck.timerTask(f, t, display);

						ImageIcon icon = new ImageIcon("start.png");
						JOptionPane.showMessageDialog(null, "Automatische Verarbeitung gestartet", "Start",
								JOptionPane.INFORMATION_MESSAGE, icon);

					} catch (IOException e1) {
						display.append("\n" + e1 + "\n");
						e1.printStackTrace();
					}

				} else {
					display.append("\n" + dtf3.format(LocalDateTime.now()) + " - " + "Automatik bereits AKTIV... \n");
				}
			}
		});

		// ActionListener wird als anonyme Klasse eingebunden
		stopAutomatik.addActionListener(new java.awt.event.ActionListener() {
			// Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Um das aktuelle Datum abzufragen
				DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");

				if (f.getAutomatik() == true) {
					f.setAutomatik(false);
					// Verarbeitung anhalten
					t.cancel();
					// �berschreibung des alten Timers, da nach der Cancel Methode der alte Timer
					// nicht mehr verwendet werdnen darf
					t = new Timer();
					// Anzeige, Verarbeitung beendet
					display.append("\n" + dtf3.format(LocalDateTime.now()) + " - " + "Automatik gestoppt... \n");
					ImageIcon icon = new ImageIcon("stop.png");
					JOptionPane.showMessageDialog(null, "Automatische Verarbeitung gestoppt", "Meldung",
							JOptionPane.INFORMATION_MESSAGE, icon);

				} else {
					display.append("\n" + dtf3.format(LocalDateTime.now()) + " - " + "Keine Automatik AKTIV..." + "\n");
				}
			}
		});
	}

}
