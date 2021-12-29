import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.*;
	 
	public class Menu extends JFrame {
		
	    // Menüleiste 
	    JMenuBar menuBar;
	 
	    // Menü "Datei"
	    JMenu fileMenu;
	 
	    // Menü "Datei"
	    JMenu infoMenu;
	    
	    // Menüpunkt "Öffnen"
	    JMenuItem openItem;
	 
	    // Menüpunkt "Schließen"
	    JMenuItem closeItem;
	    
	    // Menüpunkt "Support"
	    JMenuItem support;
	    
	    // Menüpunkt "Version"
	    JMenuItem version;
	    
	    //Button Start Automatik
	    JButton startAutomatik = new JButton("Start Automatik");
	    
	    //JButton Stop Automatik
	    JButton stopAutomatik = new JButton("Stop Automatik");
	    
	    //Button Pfad bearbeiten
	    JButton changePath = new JButton("Bearbeiten");
	    
	    //JButtonPfad speichern
	    JButton safePath = new JButton("Speichern");
	    
	  //Button Pfad bearbeiten
	    JButton changePathArchive = new JButton("Bearbeiten");
	    
	    //JButtonPfad speichern
	    JButton safePathArchive = new JButton("Speichern");
	    
	    //Buttons für Ja & Nein Dialog Fenster
		JButton yesButton = new JButton ("Ja");
		JButton noButton = new JButton ("Nein");
		
		 //Buttons für Ja & Nein Dialog Fenster-Archiv
		JButton yesButtonArchive = new JButton ("Ja");
		JButton noButtonArchive = new JButton ("Nein");
		
		
		//Dialog Fenster zur Abfrage von Speicherung des Pfades
		Dialog dialog = new JDialog(); 
		
		//Dialog Fenster zur Abfrage von Speicherung des Pfades
		Dialog dialogArchiv = new JDialog(); 
		
		//Haupt-Panel zu dem alle einzelnen Panels hinzugefügt werden
		JPanel mainPanel = new JPanel();
	  
	 
	    public Menu(String path1, String archivePath1) {
	        this.setTitle("VW Converter - 2.1.0");
	        //Größe des Frames hinterlegen
	        this.setSize(550, 300);
	        //Menschliche Vergrößerung des Fensters vermeiden
	        this.setResizable(false);
	        //Die Applikation öffnet das Fenster in der Mitte des Monitors
	        this.setLocationRelativeTo(null);
	 
	        // Menüleiste wird erzeugt
	        menuBar = new JMenuBar();
	 
	        // Menü "Datei" wird erzeugt
	        fileMenu = new JMenu("Datei");
	        
	        // Menü "Info" wird erzeugt
	        infoMenu = new JMenu("Info");
	        
	        //Erzeugen eines Panels für den Pfad
	        JPanel panel = new JPanel();
	         
	        //Erzeugen eines Panels für den Archiv-Pfad
	        JPanel panelArchive = new JPanel();
	        
	        //Erzeugung des Button Panels der Automation des Programms
	        JPanel automatik = new JPanel();
	        
	        //Erzeugen eines Labels
		    JLabel label = new JLabel("Read-Path:");
		    
		    //Erzeugen eines Labels
		    JLabel labelArchive = new JLabel("Archive-Path:");
		    
		    //Erzeugen eines TextFields - read-Path
		    JTextField path = new JTextField("",15);
		    path.setText(path1);
			path.setForeground(Color.BLUE);
			path.setBackground(Color.YELLOW);
			//Textfield ausgrauen
			path.setEnabled(false);
			//Das Feld wird auf nicht editierbar gesetzt, da dies vom Button freigegeben werden soll
			path.setEditable(false);
			
			 //Erzeugen eines TextFields
		    JTextField pathArchive = new JTextField("",15);
		    pathArchive.setText(archivePath1);
		    pathArchive.setForeground(Color.BLUE);
		    pathArchive.setBackground(Color.YELLOW);
		    //Textfield ausgrauen
		    pathArchive.setEnabled(false);
			//Das Feld wird auf nicht editierbar gesetzt, da dies vom Button freigegeben werden soll
		    pathArchive.setEditable(false);
			
		    //-----------Label, TextField & Buttons für den Pfad hinzufügen
		    
			 //Label dem Panel hinzufügen
		    panel.add(label);		    
			//TextField dem panel hinzufügen
			panel.add(path);
			//Buttons zur Bearbeitung/Speicherung des neuen Pfades
			panel.add(changePath);
			panel.add(safePath);
			
			
			//-----------Label, TextField & Buttons für den Archiv-Pfad hinzufügen
			 //Label dem Panel hinzufügen
			panelArchive.add(labelArchive);		    
			//TextField dem panel hinzufügen
			panelArchive.add(pathArchive);
			//Buttons zur Bearbeitung/Speicherung des neuen Pfades
			panelArchive.add(changePathArchive);
			panelArchive.add(safePathArchive);
		
			//----------Buttons für die Automatik hinzufügen
			automatik.add(startAutomatik);
			automatik.add(stopAutomatik);
		    
			//Alle dem PANEL hinzugefügten Felder/ Buttons etc. dem JFrame hinzufügen, daher this., da die Klasse selber ein JFrame ist
			
			mainPanel.add(panel);
			mainPanel.add(panelArchive);
			mainPanel.add(automatik);
			
			//Main-Panel dem JFrame hinzufügen
			this.add(mainPanel);
		
		   // this.add(panelArchive);
		    
	        // Menüpunkte werden erzeugt
	        openItem = new JMenuItem("Öffnen");
	        closeItem = new JMenuItem("Schließen");
	        support = new JMenuItem("Support");
	        version = new JMenuItem("Version");
	 
	        // Menüpunkte werden dem Datei-Menü hinzugefügt
	        fileMenu.add(openItem);
	        fileMenu.add(closeItem);
	        infoMenu.add(support);
	        infoMenu.add(version);
	 
	        //Datei-Menü wird der Menüleiste hinzugefügt
	        menuBar.add(fileMenu);
	        menuBar.add(infoMenu);
	 
	        //Menüleiste wird dem JFrame hinzugefügt
	        this.add(menuBar, BorderLayout.NORTH);
	        
	        
	        
/**
* Ab hier werden die ActionListener implementiert, damit der Converter auf Knopfdrücke reagieren kann
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
	            }
	        });
	 
	        // ActionListener wird als anonyme Klasse eingebunden
	        closeItem.addActionListener(new java.awt.event.ActionListener() {
	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	                //Programm schließen
	                System.exit(0);
	                dispose();
	            }
	        });
	        
	        // ActionListener wird als anonyme Klasse eingebunden
	        support.addActionListener(new java.awt.event.ActionListener() {
	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	            	//Bild für die Erroranzeige wird erzeigt
   	            	ImageIcon icon = new ImageIcon("support.png");
   	                //Display die Warnung
	            	JOptionPane.showMessageDialog(null, "Mail: NAV-Support@npr-europe.com"+ "\n" + "Tel: 0000"+"\n" + "\n" + "Autor: Dennis Lehle", "Support",JOptionPane.INFORMATION_MESSAGE, icon);
	        		
	            }
	        });
	        
	        
	        // ActionListener wird als anonyme Klasse eingebunden
	        version.addActionListener(new java.awt.event.ActionListener() {
	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	            	//Bild für die Erroranzeige wird erzeigt
   	            	ImageIcon icon = new ImageIcon("infoVersion.png");
   	                //Display die Warnung
	            	JOptionPane.showMessageDialog(null,
	            										"2.0.0 - MainFrame - release: 08.08.2020"+"\n"+
	            										"2.0.1 - NoName - release: 21.01.2021" +"\n"+ 
	            										"2.0.2 - Slippery Slope - release: 23.12.2021"+"\n"+ 
	            										"2.1.0 - NewLight - release: (Datum)"
	            										
	            										, "Versionsverlauf", JOptionPane.INFORMATION_MESSAGE, icon);
	            										
	        		
	            }
	        });
	        
//-------------------DIALOG-FENSTER-Implementierung-SPEICHERUNG-NEUER-PFAD-----------------------------------
	        
	        
	     // ActionListener wird als anonyme Klasse eingebunden
	        changePath.addActionListener(new java.awt.event.ActionListener() {
	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	            	//Das Textfeld wird bearbeitbar und die Ausgrauung wird entfernt
	            	path.setEnabled(true);
	            	path.setEditable(true);
	            	

	            }
	        }); 
	        
	        // ActionListener wird als anonyme Klasse eingebunden
	        safePath.addActionListener(new java.awt.event.ActionListener() {
	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	            	
	            	//Vergleich neuer INPUT mit altem INPUT
	   	            if(path.getText().toString().equals(path1.toString())) {
	   	                //Ausgrgauung wird aktiviert
	   	            	path.setEnabled(false);
		            	//Bild für die Erroranzeige wird erzeigt
	   	            	ImageIcon icon = new ImageIcon("error.png");
	   	                //Display die Warnung
		            	JOptionPane.showMessageDialog(null, "Uppps - Keine Änderung erkannt", "Meldung", JOptionPane.INFORMATION_MESSAGE, icon);
	   	            	
	   	            } else {
	   	         	    //Erstellen des DialogFensters, Panel und der Ja und Nein Buttons
		        		JPanel panel = new JPanel();
		        		//Hinzufügen der Button zum Panel
		        		panel.add(yesButton);
		        		panel.add(noButton);
		        		//Sichtbarkeit des Panels setzen
		        		panel.setVisible(true);
		        		//Panel dem Dialog Fenster hinzufügen
		        		dialog.add(panel);
		        		//Setzen des Titels, muss hier geschehen, da nicht über den Konstruktor möglich
		        		dialog.setTitle("Wollen Sie den Pfad wirklich ändern?");
		        		//Setzen der DialogFenster größe
		        		dialog.setSize(400,100);
		        		//Fenster öffnet sich in der Mitte des Monitors
		        		dialog.setLocationRelativeTo(null);
		        		//Setzen des Objektes/Fensters auf Modual (Modal = keine Schließung oder Änderunga anderer Fenster möglich
		        		dialog.setModal(true);
		        		//Deaktivierung der menschlichen Fensteranpassung
	   	        		dialog.setResizable(false);
		        		//Sichtbarkeit des Fensters setzen
		        		dialog.setVisible(true);	
		        		//Nach der Speicherung, soll das Feld erneut auf NICHT bearbeitbar gesetzt werden
		            	path.setEditable(false);
		            	//Nach der Speicherung wird erneut das Fels ausgegraut
		            	path.setEnabled(false);
	   	            	
	   	            }
	            }
	        }); 
	        
	    	
    		// ActionListener wird als anonyme Klasse eingebunden
    		yesButton.addActionListener(new java.awt.event.ActionListener() {
	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	            	
	            	//Das Textfeld wird bearbeitbar 
	            	
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
	            	path.setEditable(false);
	            	path.setEnabled(false);
	            	dialog.setVisible(false);
	            	dialog.dispose();
	            	ImageIcon icon = new ImageIcon("check.png");
	            	JOptionPane.showMessageDialog(null, "Änderung erfolgreich", "Meldung", JOptionPane.INFORMATION_MESSAGE, icon);

	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	//erneute Abfrage der Properties und ersezen des Wertes in dieser Klasse, um nach Speicherung nicht nochmal speichern zu können
	            	
	            }
	        }); 
    		
    		// ActionListener wird als anonyme Klasse eingebunden
    		noButton.addActionListener(new java.awt.event.ActionListener() {
	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	            	
	            	//Das Textfeld wird bearbeitbar = NEIN & der alte Wert wird erneut geladen, da keine Änderung gewünscht
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
   	            	//Das Textfeld wird bearbeitbar und die Ausgrauung wird entfernt
   	            	pathArchive.setEnabled(true);
   	            	pathArchive.setEditable(true);

   	            }
   	        }); 
   	        
   	        // ActionListener wird als anonyme Klasse eingebunden
   	        safePathArchive.addActionListener(new java.awt.event.ActionListener() {
   	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
   	            public void actionPerformed(java.awt.event.ActionEvent e) {
   	            	
   	            	
   	            	//Vergleich neuer INPUT mit altem INPUT
   	            if(pathArchive.getText().toString().equals(archivePath1.toString())) {
   	            	//Ausgrgauung wird aktiviert
   	            	pathArchive.setEnabled(false);
   	            	//Bild für die Erroranzeige wird erzeigt
   	            	ImageIcon icon = new ImageIcon("error.png");
   	            	//Display die Warnung
	            	JOptionPane.showMessageDialog(null, "Uppps - Keine Änderung erkannt", "Meldung", JOptionPane.INFORMATION_MESSAGE, icon);

   	            } else {
   	            	//Erstellen des DialogFensters, Panel und der Ja und Nein Buttons	
   	        		JPanel panel = new JPanel();
   	        		//Hinzufügen der Button zum Panel
   	        		panel.add(yesButtonArchive);
   	        		panel.add(noButtonArchive);
   	        		//Sichtbarkeit des Panels setzen
   	        		panel.setVisible(true);
   	        		//Panel dem Dialog Fenster hinzufügen
   	        		dialogArchiv.add(panel);
   	        		//Setzen des Titels, muss hier geschehen, da nicht über den Konstruktor möglich
   	        		dialogArchiv.setTitle("Wollen Sie den Archiv-Pfad wirklich ändern?");
   	        		//Setzen der DialogFenster größe
   	        		dialogArchiv.setSize(400,100);
   	        	    //Fenster öffnet sich in der Mitte des Monitors
   	        		dialogArchiv.setLocationRelativeTo(null);
   	        		//Deaktivierung der menschlichen Fensteranpassung
   	        		dialogArchiv.setResizable(false);
   	        		//Setzen des Objektes/Fensters auf Modual (Modal = keine Schließung oder Änderunga anderer Fenster möglich
   	        		dialogArchiv.setModal(true);
   	        		//Sichtbarkeit des Fensters setzen
   	        		dialogArchiv.setVisible(true);
   	            	//Nach der Speicherung, soll das Feld erneut auf NICHT bearbeitbar gesetzt werden
   	            	pathArchive.setEditable(false);
   	            	//Nach der Speicherung wird auch das Feld erneut ausgegaurt
   	            	pathArchive.setEnabled(false);
 
   	            }
   	            	
   	            	
   	            }
   	        }); 
   	        
   	    	
       		// ActionListener wird als anonyme Klasse eingebunden
       		yesButtonArchive.addActionListener(new java.awt.event.ActionListener() {
   	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
   	            public void actionPerformed(java.awt.event.ActionEvent e) {
   	            	
   	            	//Das Textfeld wird bearbeitbar 
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
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	  	
   	            	//Das Textfeld wird bearbeitbar 
	            	//pathArchive.setText(archivePath1);
   	            	pathArchive.setEditable(false);
   	            	pathArchive.setEnabled(false);
   	            	dialogArchiv.setVisible(false);
   	            	dialogArchiv.dispose();
   	            	
   	            	ImageIcon icon = new ImageIcon("check.png");
	            	JOptionPane.showMessageDialog(null, "Änderung erfolgreich", "Meldung", JOptionPane.INFORMATION_MESSAGE, icon);

	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	//erneute Abfrage der Properties und ersezen des Wertes in dieser Klasse, um nach Speicherung nicht nochmal speichern zu können
   	            }
   	        }); 
       		
       		// ActionListener wird als anonyme Klasse eingebunden
       		noButtonArchive.addActionListener(new java.awt.event.ActionListener() {
   	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
   	            public void actionPerformed(java.awt.event.ActionEvent e) {
   	            	
   	            	//Das Textfeld wird bearbeitbar 
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
   	            	
   	            	//Starten des Programms 
   	            	
   	            	
   	            	ImageIcon icon = new ImageIcon("start.png");
	            	JOptionPane.showMessageDialog(null, "Automatische Verarbeitung gestartet", "Start", JOptionPane.INFORMATION_MESSAGE, icon);


   	            }
   	        }); 
       		
       		// ActionListener wird als anonyme Klasse eingebunden
       		stopAutomatik.addActionListener(new java.awt.event.ActionListener() {
   	            // Beim Drücken des Menüpunktes wird actionPerformed aufgerufen
   	            public void actionPerformed(java.awt.event.ActionEvent e) {
   	            	
   	            	//Stoppen des Programms
   	            	
   	            	
   	            	ImageIcon icon = new ImageIcon("stop.png");
	            	JOptionPane.showMessageDialog(null, "Automatische Verarbeitung gestoppt", "Stop", JOptionPane.INFORMATION_MESSAGE, icon);

   	            }
   	        }); 
	    }
	
	
}
