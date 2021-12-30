import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class CheckFile {

	// Soll nach VWPAL duchen wnen vorhandne dann einfach raus gehen fertig
	/**
	 * Diese Methode prüft eingehende VW Dateien auf vorkommen einer Palette oder
	 * eines KLT's und modifiziert dementsprechend die CPS Segmente.
	 * 
	 * @param EdiDatei Eingehende EDI Datei (unverarbeitet)
	 * @param files    Eingehende EDI Datei, um Name des Files später zuordnen zu
	 *                 können
	 * @throws Exception
	 */
	public static void checkVWPALandCPS(String EdiDatei, File files, JTextArea display) throws Exception {

		// Vorkommen == true, Datei ohne Verarbeitung in den nächsten Ordner
		// verschieben.
		if (EdiDatei.contains("767E4")) {

			// Prüfung, ob der String VWPAL in der eingehenden Datei vorkommt.
			if (EdiDatei.contains("VWPAL") || EdiDatei.contains("0000PAL") || EdiDatei.contains("114003") || EdiDatei.contains("DB0011")) {

				// Prüfung, ob die EDI Datei ein leeres CPS Segment enthält
				if (EdiDatei.contains("CPS'")) {

					// Übergabe der EDi Datei zum entfernen des Leeren CPS Segements
					StringBuffer newEdiDatei = clearEmptyCPS(EdiDatei);

					// Prüfung, ob es sich um mehr als ein QTY12 Segment im Auftrag handelt
					if (stringCounter5(newEdiDatei.toString(), "QTY+12:") <= 1) {

						// Wenn mehr als ein QTY12 Feld enthalten ist wird die einzelneUmlagerung()
						// angestoßen
						einzelneUmlagerung(newEdiDatei, files, display);

						// Sind mehr als 2 QTY12 Felder enthalten wird die multyUmlagerung() angestoßen
					} else {
						// Start der Multiumlagerung
						multyUmlagerung(newEdiDatei, files, display);

					}

				} else {
					// Wenn kein leeres CPS Feld in der Datei vorhanden ist wird die Datei ohne
					// Verarbeitung in den Out Ordner gespeichert
					//FileImpExp.safeNewFile(EdiDatei, files);
					
					//##BugFix Slippery Slope
					Extensions.checkFile(EdiDatei, files,display);
					
					
				}

			} else if (EdiDatei.contains("CPS+1++1")) {
				// Wenn das Segment CPS+1++1 vorhanden ist wird die ganze Datei nach
				// fehlerhaften Segmenten untersucht.
				changeCPSforKLTonly(EdiDatei, files,display);

			} else {
				// Wenn nicht wird die Datei einfach verschoben ohne verarbeitet zu werden.
				//FileImpExp.safeNewFile(EdiDatei, files);
				
				//##BugFix Slippery Slope
				Extensions.checkFile(EdiDatei, files,display);
			}
		} else {

			// Prüfung, ob die EDI Datei ein leeres CPS Segment enthält
			if (EdiDatei.contains("CPS+1++1")) {
				// Wenn das Segment CPS+1++1 vorhanden ist wird die ganze Datei nach
				// fehlerhaften Segmenten untersucht.
				changeCPSforKLTonly(EdiDatei, files,display);

			} else {
				// Wenn die Abladestelle nicht nicht vorhanden ist wird die Datei einfach
				// unverarbeitet verschoben
				//FileImpExp.safeNewFile(EdiDatei, files);
				
				//##BugFix Slippery Slope
				Extensions.checkFile(EdiDatei, files,display);
			}
		}

	}

	/**
	 * Diese Methode passt die fehlerhaften CPS Segmente einer Only KLT Lieferung an
	 * und speicher die Datei.
	 * 
	 * @param ediD
	 * @param files
	 * @throws Exception
	 */
	public static void changeCPSforKLTonly(String ediD, File files,JTextArea display) throws Exception {

		String newData1 = ediD.replace("CPS+1++1", "CPS+1++4");

		if (newData1.contains("CPS+2++1")) {

			String newData2 = newData1.replace("CPS+2++1", "CPS+2++4");

			if (newData2.contains("CPS+3++1")) {
				String newData3 = newData2.replace("CPS+3++1", "CPS+3++4");

				if (newData3.contains("CPS+4++1")) {
					String newData4 = newData3.replace("CPS+4++1", "CPS+4++4");

				} else {
					//FileImpExp.safeNewFile(newData3, files);
					
					//##BugFix Slippery Slope
					Extensions.checkFile(newData3, files,display);

				}

			} else {
				//FileImpExp.safeNewFile(newData2, files);
				
				//##BugFix Slippery Slope
				Extensions.checkFile(newData2, files,display);

			}

		} else {
			//FileImpExp.safeNewFile(newData1, files);
			
			//##BugFix Slippery Slope
			Extensions.checkFile(newData1, files,display);

		}

	}

	/**
	 * Diese Methode stößt die Multiumlagerung an und verarbeitet diese
	 * 
	 * @param text2
	 * @param files
	 * @throws Exception
	 */
	public static void multyUmlagerung(StringBuffer text2, File files, JTextArea display) throws Exception {

		// Prüfung, ob in der Datei ein leere QTY 12 Segment steckt
		if (qty12SingleLeftCheck(text2) > 1) {
			// bearbeiten der Datei - Methode? Da sonst zu viel versammelt hier..

			StringBuffer text3 = clearEmptyQTY12Field(text2);
			StringBuffer text4 = clearFromQty11ToEmptyQty12Field(text3);
			StringBuffer text5 = clearEmptyQTY12Field(text4);
			StringBuffer text6 = clearNextEmpty12Field(text5);

			if (qty12SingleLeftCheck(text6) > 1) {

				StringBuffer text7 = clearEmptyQTY12Field(text6);
				StringBuffer text8 = clearFromQty11ToEmptyQty12Field(text7);
				StringBuffer text9 = clearEmptyQTY12Field(text8);
				StringBuffer text10 = clearNextEmpty12Field(text9);

				if (qty12SingleLeftCheck(text10) > 1) {

					StringBuffer text11 = clearEmptyQTY12Field(text10);
					StringBuffer text12 = clearFromQty11ToEmptyQty12Field(text11);
					StringBuffer text13 = clearEmptyQTY12Field(text12);
					StringBuffer text14 = clearNextEmpty12Field(text13);

					if (qty12SingleLeftCheck(text14) > 1) {

						StringBuffer text15 = clearEmptyQTY12Field(text14);
						StringBuffer text16 = clearFromQty11ToEmptyQty12Field(text15);
						StringBuffer text17 = clearEmptyQTY12Field(text16);
						StringBuffer text18 = clearNextEmpty12Field(text17);

						if (qty12SingleLeftCheck(text18) > 1) {

							StringBuffer text19 = clearEmptyQTY12Field(text18);
							StringBuffer text20 = clearFromQty11ToEmptyQty12Field(text19);
							StringBuffer text21 = clearEmptyQTY12Field(text20);
							StringBuffer text22 = clearNextEmpty12Field(text21);

							if (qty12SingleLeftCheck(text22) > 1) {

								StringBuffer text23 = clearEmptyQTY12Field(text22);
								StringBuffer text24 = clearFromQty11ToEmptyQty12Field(text23);
								StringBuffer text25 = clearEmptyQTY12Field(text24);
								StringBuffer text26 = clearNextEmpty12Field(text25);

								if (qty12SingleLeftCheck(text26) > 1) {

									StringBuffer text27 = clearEmptyQTY12Field(text26);
									StringBuffer text28 = clearFromQty11ToEmptyQty12Field(text27);
									StringBuffer text29 = clearEmptyQTY12Field(text28);
									StringBuffer text30 = clearNextEmpty12Field(text29);

									if (qty12SingleLeftCheck(text30) > 1) {

										StringBuffer text31 = clearEmptyQTY12Field(text30);
										StringBuffer text32 = clearFromQty11ToEmptyQty12Field(text31);
										StringBuffer text33 = clearEmptyQTY12Field(text32);
										StringBuffer text34 = clearNextEmpty12Field(text33);

										if (qty12SingleLeftCheck(text34) > 1) {

											StringBuffer text35 = clearEmptyQTY12Field(text34);
											StringBuffer text36 = clearFromQty11ToEmptyQty12Field(text35);
											StringBuffer text37 = clearEmptyQTY12Field(text36);
											StringBuffer text38 = clearNextEmpty12Field(text37);

											if (qty12SingleLeftCheck(text38) > 1) {

												StringBuffer text39 = clearEmptyQTY12Field(text38);
												StringBuffer text40 = clearFromQty11ToEmptyQty12Field(text39);
												StringBuffer text41 = clearEmptyQTY12Field(text40);
												StringBuffer text42 = clearNextEmpty12Field(text41);

												if (qty12SingleLeftCheck(text42) > 1) {

													StringBuffer text43 = clearEmptyQTY12Field(text42);
													StringBuffer text44 = clearFromQty11ToEmptyQty12Field(text43);
													StringBuffer text45 = clearEmptyQTY12Field(text44);
													StringBuffer text46 = clearNextEmpty12Field(text45);

													if (qty12SingleLeftCheck(text46) > 1) {

														StringBuffer text47 = clearEmptyQTY12Field(text46);
														StringBuffer text48 = clearFromQty11ToEmptyQty12Field(text47);
														StringBuffer text49 = clearEmptyQTY12Field(text48);
														StringBuffer text50 = clearNextEmpty12Field(text49);

														if (qty12SingleLeftCheck(text50) > 1) {

															StringBuffer text51 = clearEmptyQTY12Field(text50);
															StringBuffer text52 = clearFromQty11ToEmptyQty12Field(
																	text51);
															StringBuffer text53 = clearEmptyQTY12Field(text52);
															StringBuffer text54 = clearNextEmpty12Field(text53);

															if (qty12SingleLeftCheck(text54) > 1) {

																StringBuffer text55 = clearEmptyQTY12Field(text54);
																StringBuffer text56 = clearFromQty11ToEmptyQty12Field(
																		text55);
																StringBuffer text57 = clearEmptyQTY12Field(text56);
																StringBuffer text58 = clearNextEmpty12Field(text57);

																if (qty12SingleLeftCheck(text58) > 1) {

																	StringBuffer text59 = clearEmptyQTY12Field(text58);
																	StringBuffer text60 = clearFromQty11ToEmptyQty12Field(
																			text59);
																	StringBuffer text61 = clearEmptyQTY12Field(text60);
																	StringBuffer text62 = clearNextEmpty12Field(text61);

																	JOptionPane.showMessageDialog(null,
																			"Errorcode: Weasel");

																} else {
																	// Datei Speichern
																	multiUmlagerungsberechnung(text58, files,display);
																}

															} else {
																// Datei Speichern
																multiUmlagerungsberechnung(text54, files, display);
															}

														} else {
															// Datei Speichern
															multiUmlagerungsberechnung(text50, files, display);
														}

													} else {
														// Datei Speichern
														multiUmlagerungsberechnung(text46, files, display);
													}

												} else {
													// Datei Speichern
													multiUmlagerungsberechnung(text42, files, display);
												}

											} else {
												// Datei Speichern
												multiUmlagerungsberechnung(text38, files, display);
											}

										} else {
											// Datei Speichern
											multiUmlagerungsberechnung(text34, files , display);
										}
									} else {
										// Datei Speichern
										multiUmlagerungsberechnung(text30, files, display);
									}

								} else {
									// Datei Speichern
									multiUmlagerungsberechnung(text26, files, display);
								}
							} else {
								// Datei Speichern
								multiUmlagerungsberechnung(text22, files, display);
							}

						} else {
							// Datei Speichern
							multiUmlagerungsberechnung(text18, files, display);
						}

					} else {
						// Datei Speichern
						multiUmlagerungsberechnung(text14, files, display);
					}
				} else {
					// Datei Speichern
					multiUmlagerungsberechnung(text10, files, display);
				}

			} else {
				// Datei Speichern
				multiUmlagerungsberechnung(text6, files, display);
			}

		} else {
			// Datei Speichern
			multiUmlagerungsberechnung(text2, files, display);
		}

	}

	/**
	 * Diese Methode passt das QTY11 Segment mit dem richtigen QTY12 Wert zu Qty12
	 * an
	 * 
	 * @return
	 */
	public static String changeQTY11ToQTY12(StringBuffer text22) {

		String newText = (text22.toString().replace("QTY+12:0:PCE'", ""));

		return newText;

	}

	/**
	 * Diese Methode entfertn die leeren QTY12 Felder der MultiUmlagerungs Methode.
	 * 
	 * @param text22
	 * @return
	 */
	public static StringBuffer clearFromQty11ToEmptyQty12Field(StringBuffer text22) {

		String newData = null;
		StringBuffer newData1 = null;

		// Buffer erstellen für zusammenzusetzende Datei.
		StringBuffer text = new StringBuffer();

		StringBuffer text2 = new StringBuffer();

		// Bis zum CPS' Segment wird gespeichert
		newData = text22.substring(0, text22.indexOf("QTY+11:"));

		// Ab dem richtigen CPS Segment wird gespeichert
		newData1 = text.append(text22.substring(text22.indexOf("QTY+12:0:PCE'")));

		// Hier werden beide richtigen Teile zusammengefügt
		text2.append(newData);
		text2.append(newData1);

		return text2;

	}

	/**
	 * Diese Methode entfertn die leeren QTY12 Felder der MultiUmlagerungs Methode.
	 * 
	 * @param text22
	 * @return
	 */
	public static StringBuffer clearNextEmpty12Field(StringBuffer text22) {

		String newData = null;
		StringBuffer newData1 = null;

		// Buffer erstellen für zusammenzusetzende Datei.
		StringBuffer text = new StringBuffer();

		StringBuffer text2 = new StringBuffer();

		// Bis zum CPS' Segment wird gespeichert
		newData = text22.substring(0, text22.indexOf("QTY+12:0:PCE'"));
		String newData2 = newData.replace("QTY+11:", "QTY+12:");
		String newData4 = text22.substring(text22.indexOf("QTY+12:0:PCE'"));

		// Hier werden beide richtigen Teile zusammengefügt
		text2.append(newData2);

		text2.append(newData4);

		return text2;

	}

	/**
	 * Diese Methode entfertn die leeren QTY12 Felder der MultiUmlagerungs Methode.
	 * 
	 * @param text22
	 * @return
	 */
	public static StringBuffer clearEmptyQTY12Field(StringBuffer text22) {

		String newData = null;
		StringBuffer newData1 = null;

		// Buffer erstellen für zusammenzusetzende Datei.
		StringBuffer text = new StringBuffer();

		StringBuffer text2 = new StringBuffer();

		// Bis zum CPS' Segment wird gespeichert
		newData = text22.substring(0, text22.indexOf("QTY+12:0:PCE'"));

		// Ab dem richtigen CPS Segment wird gespeichert
		newData1 = text.append(text22.substring(text22.indexOf("QTY+11:")));

		// Hier werden beide richtigen Teile zusammengefügt
		text2.append(newData);
		text2.append(newData1);

		return text2;

	}

	/**
	 * Diese Methode kürzt die EDI Datei damit kein leeres CPS Sengemnt hinterlegt
	 * wird.
	 * 
	 * @param ediDatei
	 * @return
	 */
	public static StringBuffer clearEmptyCPS(String ediDatei) {

		String newData = null;
		StringBuffer newData1 = null;

		// Buffer erstellen für zusammenzusetzende Datei.
		StringBuffer text = new StringBuffer();

		StringBuffer text2 = new StringBuffer();

		// Bis zum CPS' Segment wird gespeichert
		newData = ediDatei.substring(0, ediDatei.indexOf("CPS'"));

		// Ab dem richtigen CPS Segment wird gespeichert
		newData1 = text.append(ediDatei.substring(ediDatei.indexOf("CPS+1++3'")));

		// Hier werden beide richtigen Teile zusammengefügt
		text2.append(newData);
		text2.append(newData1);

		return text2;
	}

	/**
	 * Diese Methode behandelt einzelne Umlagerungen und verarbeitet die Datei
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void multiUmlagerungsberechnung(StringBuffer text2, File files, JTextArea display) throws Exception {

		String newEdiDatei = null;

		// Nach der Richtigstellung der EDiDatei wird die Summe des QTY-12 Segments
		// geprüft

		if (text2.toString().contains("QTY+12:0:PCE'")) {

			// Buffer erstellen für zusammenzusetzende Datei.
			StringBuffer text4 = new StringBuffer();

			StringBuffer text5 = new StringBuffer();

			// Segment welche Anzhal Mengen QTY+189
			String valueQTY189 = (String) text2.toString().subSequence(text2.indexOf("QTY+189"),
					text2.indexOf("QTY+189") + 16);
			String value189_1 = (String) valueQTY189.subSequence(8, 12);

			// Segment wie viel Stück QTY+52
			String valueQTY52 = (String) text2.toString().subSequence(text2.indexOf("QTY+52"),
					text2.indexOf("QTY+52") + 16);
			String valueQTY52_1 = (String) valueQTY52.subSequence(7, 11);

			if (value189_1.contains(":")) {

				/**
				 * QTY 189
				 */
				String value189_2 = (String) value189_1.subSequence(0, 2);

				// String in einen Int casten, um Rechnen zu können
				// int x = Integer.valueOf(value189_2);
				/**
				 * QTY 52
				 */
				if (value189_2.contains(":")) {
					// 1 Stelle
					String value189_3 = (String) value189_2.subSequence(0, 1);

					if (valueQTY52_1.contains(":")) {

						String value52 = (String) valueQTY52_1.subSequence(0, 3);

						if (value52.contains(":")) {

							String value52_1 = (String) value52.subSequence(0, 2);

							if (value52_1.contains(":")) {

								String value52_2 = (String) value52.subSequence(0, 1);
								int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_3);

								qtyMultiUmlagerungsFieldMaker(res1, text2, files, display);

							} else {
								int res1 = Integer.valueOf(value52_1) * Integer.valueOf(value189_3);

								qtyMultiUmlagerungsFieldMaker(res1, text2, files, display);

							}

						} else {
							int result = Integer.valueOf(value52) * Integer.valueOf(value189_3);

							qtyMultiUmlagerungsFieldMaker(result, text2, files, display);

						}

					} else {

						int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_3);
						qtyMultiUmlagerungsFieldMaker(res2, text2, files, display);

					}

				} else if (valueQTY52_1.contains(":")) {

					String value52 = (String) valueQTY52_1.subSequence(0, 3);

					if (value52.contains(":")) {

						String value52_1 = (String) value52.subSequence(0, 2);

						if (value52_1.contains(":")) {
							String value52_2 = (String) value52.subSequence(0, 1);

							int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_2);
							qtyMultiUmlagerungsFieldMaker(res1, text2, files, display);

						} else {
							JOptionPane.showMessageDialog(null, "Errorcode: Bitcoin");

						}

					} else {
						int result = Integer.valueOf(value52) * Integer.valueOf(value189_2);
						qtyMultiUmlagerungsFieldMaker(result, text2, files, display);

					}

				} else {

					int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_2);
					qtyMultiUmlagerungsFieldMaker(res2, text2, files, display);

				}
			} else {

				/**
				 * Springt rein wenn QTY189 3 Stellen hat und QTY52 1
				 */
				if (valueQTY52_1.contains(":")) {

					String value52 = (String) valueQTY52_1.subSequence(0, 3);

					/**
					 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
					 */
					if (value52.contains(":")) {

						String value52_1 = (String) value52.subSequence(0, 2);

						/**
						 * Springt rein wenn QTY189 3 Stellen hat und QTY52 1
						 */
						if (value52_1.contains(":")) {

							String value52_2 = (String) value52.subSequence(0, 1);

							int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_1);
							qtyMultiUmlagerungsFieldMaker(res1, text2, files, display);

						} else {

							int res1 = Integer.valueOf(value52_1) * Integer.valueOf(value189_1);
							qtyMultiUmlagerungsFieldMaker(res1, text2, files, display);

						}
						/**
						 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
						 */
					} else {
						int result = Integer.valueOf(value52) * Integer.valueOf(value189_1);
						qtyMultiUmlagerungsFieldMaker(result, text2, files, display);

					}

				} else {

					int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_1);
					qtyMultiUmlagerungsFieldMaker(res2, text2, files, display);

				}

			}

		} else {
			//FileImpExp.safeNewFile(text2.toString(), files);
			
			//##BugFix Slippery Slope
			Extensions.checkFile(text2.toString(), files,display);
		}

	}

	/**
	 * Diese Methode behandelt einzelne Umlagerungen und verarbeitet die Datei
	 * 
	 * @throws Exception
	 */
	public static void einzelneUmlagerung(StringBuffer text2, File files, JTextArea display) throws Exception {

		// Nach der Richtigstellung der EDiDatei wird die Summe des QTY-12 Segments
		// geprüft

		if (text2.toString().contains("QTY+12:0:PCE'")) {

			// Buffer erstellen für zusammenzusetzende Datei.
			StringBuffer text4 = new StringBuffer();

			StringBuffer text5 = new StringBuffer();

			// Segment welche Anzhal Mengen QTY+189
			String valueQTY189 = (String) text2.toString().subSequence(text2.indexOf("QTY+189"),
					text2.indexOf("QTY+189") + 16);
			String value189_1 = (String) valueQTY189.subSequence(8, 12);

			// System.out.print(value189_1+ " ");
			// Segment wie viel Stück QTY+52
			String valueQTY52 = (String) text2.toString().subSequence(text2.indexOf("QTY+52"),
					text2.indexOf("QTY+52") + 16);
			String valueQTY52_1 = (String) valueQTY52.subSequence(7, 11);

			if (value189_1.contains(":")) {

				/**
				 * QTY 189
				 */
				String value189_2 = (String) value189_1.subSequence(0, 2);

				// String in einen Int casten, um Rechnen zu können
				// int x = Integer.valueOf(value189_2);
				/**
				 * QTY 52
				 */
				if (value189_2.contains(":")) {
					// 1 Stelle
					String value189_3 = (String) value189_2.subSequence(0, 1);

					if (valueQTY52_1.contains(":")) {

						String value52 = (String) valueQTY52_1.subSequence(0, 3);

						if (value52.contains(":")) {

							String value52_1 = (String) value52.subSequence(0, 2);

							if (value52_1.contains(":")) {

								String value52_2 = (String) value52.subSequence(0, 1);
								int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_3);

							} else {
								int res1 = Integer.valueOf(value52_1) * Integer.valueOf(value189_3);
								CheckFile.qtyFieldMaker(res1, text2, files, display);

							}

						} else {
							int result = Integer.valueOf(value52) * Integer.valueOf(value189_3);

							// Test, ob Programm richtig zusammensetzt
							CheckFile.qtyFieldMaker(result, text2, files, display);

						}

					} else {

						int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_3);
						CheckFile.qtyFieldMaker(res2, text2, files, display);

					}

				} else if (valueQTY52_1.contains(":")) {

					String value52 = (String) valueQTY52_1.subSequence(0, 3);

					if (value52.contains(":")) {

						String value52_1 = (String) value52.subSequence(0, 2);

						if (value52_1.contains(":")) {
							String value52_2 = (String) value52.subSequence(0, 1);

							int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_2);
							CheckFile.qtyFieldMaker(res1, text2, files, display);

						} else {
							JOptionPane.showMessageDialog(null, "Errorcode: Single-Ethereum");
						}

					} else {
						int result = Integer.valueOf(value52) * Integer.valueOf(value189_2);

						CheckFile.qtyFieldMaker(result, text2, files, display);

					}

				} else {

					int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_2);
					CheckFile.qtyFieldMaker(res2, text2, files, display);

				}
			} else {

				/**
				 * Springt rein wenn QTY189 3 Stellen hat und QTY52 1
				 */
				if (valueQTY52_1.contains(":")) {

					String value52 = (String) valueQTY52_1.subSequence(0, 3);

					/**
					 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
					 */
					if (value52.contains(":")) {

						String value52_1 = (String) value52.subSequence(0, 2);

						/**
						 * Springt rein wenn QTY189 3 Stellen hat und QTY52 1
						 */
						if (value52_1.contains(":")) {

							String value52_2 = (String) value52.subSequence(0, 1);

							int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_1);
							// System.out.print(res1 + " yooo hoho....tief 3");

							// Test, ob Programm richtig zusammensetzt
							CheckFile.qtyFieldMaker(res1, text2, files, display);

							/**
							 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
							 */
						} else {

							int res1 = Integer.valueOf(value52_1) * Integer.valueOf(value189_1);

							CheckFile.qtyFieldMaker(res1, text2, files, display);

						}
						/**
						 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
						 */
					} else {
						int result = Integer.valueOf(value52) * Integer.valueOf(value189_1);
						CheckFile.qtyFieldMaker(result, text2, files, display);

					}

				} else {

					int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_1);
					CheckFile.qtyFieldMaker(res2, text2, files, display);

				}

				/**
				 * Weiter machen wenn es um das Segment 189 geht Segment 52 kann jetzt
				 * multiplizieren von 30 bis 3000
				 */

			}

		} else {
			//FileImpExp.safeNewFile(text2.toString(), files);
			
			//##BugFix Slippery Slope
			Extensions.checkFile(text2.toString(), files, display);
			
		}

	}

	public static int calculationOfQTY12Segments(String ali1, File files, JTextArea display) throws Exception {

		String newEdiDatei = null;

		// Nach der Richtigstellung der EDiDatei wird die Summe des QTY-12 Segments
		// geprüft

		if (ali1.toString().contains("QTY+12:")) {

			// Segment welche Anzhal Mengen QTY+189
			String valueQTY189 = (String) ali1.toString().subSequence(ali1.indexOf("QTY+189"),
					ali1.indexOf("QTY+189") + 16);
			String value189_1 = (String) valueQTY189.subSequence(8, 12);
			System.out.print(ali1);
			// System.out.print(" Ich bin " + valueQTY189 + " so viels lul ");
			// Segment wie viel Stück QTY+52
			String valueQTY52 = (String) ali1.toString().subSequence(ali1.indexOf("QTY+52"),
					ali1.indexOf("QTY+52") + 16);
			String valueQTY52_1 = (String) valueQTY52.subSequence(7, 11);

			if (value189_1.contains(":")) {

				/**
				 * QTY 189
				 */
				String value189_2 = (String) value189_1.subSequence(0, 2);

				// String in einen Int casten, um Rechnen zu können
				// int x = Integer.valueOf(value189_2);
				/**
				 * QTY 52
				 */
				if (value189_2.contains(":")) {
					// 1 Stelle
					String value189_3 = (String) value189_2.subSequence(0, 1);

					if (valueQTY52_1.contains(":")) {

						String value52 = (String) valueQTY52_1.subSequence(0, 3);

						if (value52.contains(":")) {

							String value52_1 = (String) value52.subSequence(0, 2);

							if (value52_1.contains(":")) {

								String value52_2 = (String) value52.subSequence(0, 1);
								int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_3);

								return res1;

							} else {
								int res1 = Integer.valueOf(value52_1) * Integer.valueOf(value189_3);

								return res1;

							}

						} else {
							int result = Integer.valueOf(value52) * Integer.valueOf(value189_3);

							return result;

						}

					} else {

						int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_3);

						return res2;

					}

				} else if (valueQTY52_1.contains(":")) {

					String value52 = (String) valueQTY52_1.subSequence(0, 3);

					if (value52.contains(":")) {

						String value52_1 = (String) value52.subSequence(0, 2);

						if (value52_1.contains(":")) {
							String value52_2 = (String) value52.subSequence(0, 1);

							int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_2);
							return res1;

						} else {

							JOptionPane.showMessageDialog(null, "Errorcode: Multi-calculation-Bitcoin");
						}

					} else {
						int result = Integer.valueOf(value52) * Integer.valueOf(value189_2);
						return result;

					}

				} else {

					int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_2);

					return res2;

				}
			} else {

				/**
				 * Springt rein wenn QTY189 3 Stellen hat und QTY52 1
				 */
				if (valueQTY52_1.contains(":")) {

					String value52 = (String) valueQTY52_1.subSequence(0, 3);

					/**
					 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
					 */
					if (value52.contains(":")) {

						String value52_1 = (String) value52.subSequence(0, 2);

						/**
						 * Springt rein wenn QTY189 3 Stellen hat und QTY52 1
						 */
						if (value52_1.contains(":")) {

							String value52_2 = (String) value52.subSequence(0, 1);

							int res1 = Integer.valueOf(value52_2) * Integer.valueOf(value189_1);

							return res1;

							/**
							 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
							 */
						} else {

							int res1 = Integer.valueOf(value52_1) * Integer.valueOf(value189_1);
							return res1;

						}
						/**
						 * Springt rein wenn QTY189 3 Stellen hat und QTY52 2
						 */
					} else {
						int result = Integer.valueOf(value52) * Integer.valueOf(value189_1);
						return result;

					}

				} else {

					int res2 = Integer.valueOf(valueQTY52_1) * Integer.valueOf(value189_1);

					return res2;

				}

				/**
				 * Weiter machen wenn es um das Segment 189 geht Segment 52 kann jetzt
				 * multiplizieren von 30 bis 3000
				 */

			}

		} else {
			//FileImpExp.safeNewFile(ali1.toString(), files);
			
			//##BugFix Slippery Slope
			Extensions.checkFile(ali1.toString(), files, display);
		}
		return 0;

	}

	/**
	 * Diese Methode dient zur Zusammensetzung des QTY12 Segments für einen
	 * Umlagerungsauftrag mit mehreren Paletten und KLT's mit einem QTY12 Segment
	 * 
	 * @param qty12Value
	 * @throws Exception
	 */
	public static void qtyMultiUmlagerungsFieldMaker(int qty12Value, StringBuffer textWithQTY12, File files, JTextArea display)
			throws Exception {
		// Zuerst werden die Werte gesplittet und dann mit demneuen zuvor berechneten
		// Wert für QTY 12 zusammengefügt
		String newValue12_1 = "QTY+12:";
		String newValue12_2 = ":PCE'";

		// Zusammengesetztes QTY12 Feld
		String NewValueResult = newValue12_1 + qty12Value + newValue12_2;

		// Lokale Variablen die hier benötigt werden
		String newText;
		String newText2;

		// Ab hier wird die neue EDI Datei erzeugt und zusammengesetzt
		StringBuffer textNeeww = new StringBuffer();

		// Bis zum QTY+12:0:PCE' Segment wird gespeichert
		String newData1 = textWithQTY12.substring(0, textWithQTY12.indexOf("QTY+12:0:PCE'"));

		// Ab dem richtigen QTY+12:0:PCE' Segment wird gespeichert
		String newData2 = textWithQTY12.substring(textWithQTY12.indexOf("QTY+12:0:PCE'"));

		// Hier werden beide richtigen Teile zusammengefügt
		textNeeww.append(newData1);
		textNeeww.append(NewValueResult);

		String hey = newData2.toString().replace("QTY+12:0:PCE'", "");
		String hey2 = charBuilder(hey, "'");
		textNeeww.append(hey2);

		// Hier wird in Zukunft das Speichern der Datei aufgerufen
		buildSingleStringForCalculation(textNeeww.toString(), files, display);

	}

	/**
	 * Diese methode berechnet und setzt die neuen Teile der Edi datei für die
	 * Umlagerungsaufträge zusammen
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String buildSingleStringForCalculation(String text, File files, JTextArea display) throws Exception {

		// String an dem alle neuen Teile der EDi Datei angehängt werden.
		StringBuilder textNew = new StringBuilder();

		if (detect(text, "ALI+") == true)  {
			//System.out.print(text + "HALooooo TEST");

			String a = text.substring(0, text.indexOf("ALI+"));

			String a1 = charBuilder(text.toString(), "ALI+");

			String aliSegment = charBuilderBefore(a1.toString(), "RFF+");
			// Nur ali entfernen.
			String ali1 = charBuilder(a1.toString(), "RFF");

			int calculated = calculationOfQTY12Segments(a.toString(), files, display);
			System.out.print("Segment 1 " + calculated + " " + "  " + a1);

			if (detect(ali1, "ALI+") == true) {

				//System.out.print("Segment LUL " + ali1.toString());
				String b = ali1.substring(0, ali1.indexOf("ALI+"));
				String b1 = charBuilder(ali1.toString(), "ALI+");

				String aliSegment2 = charBuilderBefore(b1.toString(), "RFF+");
				// Nur ali entfernen.
				String ali2 = charBuilder(b1.toString(), "RFF");

				int calculated2 = calculationOfQTY12Segments(b.toString(), files, display);

				System.out.print("Segment 2 " + calculated2 + " ");

				if (detect(ali2, "ALI+") == true) {

					String c = ali2.substring(0, ali2.indexOf("ALI+"));
					String c1 = charBuilder(ali2.toString(), "ALI+");

					String aliSegment3 = charBuilderBefore(c1.toString(), "RFF+");
					// Nur ali entfernen.
					String ali3 = charBuilder(c1.toString(), "RFF");
					int calculated3 = calculationOfQTY12Segments(c.toString(), files, display);
					System.out.print(" Segment 3 " + calculated3 + "  ");

					if (detect(ali3, "ALI+") == true) {

						String d = ali3.substring(0, ali3.indexOf("ALI+"));
						String d1 = charBuilder(ali3.toString(), "ALI+");

						// Nur Ali Segment speichern
						String aliSegment4 = charBuilderBefore(d1.toString(), "RFF+");
						// Nur ali entfernen.
						String ali4 = charBuilder(d1.toString(), "RFF");

						int calculated4 = calculationOfQTY12Segments(d.toString(), files, display);
						System.out.print("Segment 4 " + calculated4 + " ");

						if (detect(ali4, "ALI+") == true) {

							String e = ali4.substring(0, ali4.indexOf("ALI+"));
							String e1 = charBuilder(ali4.toString(), "ALI+");

							// Nur Ali Segment speichern
							String aliSegment5 = charBuilderBefore(e1.toString(), "RFF+");
							// Nur ali entfernen.
							String ali5 = charBuilder(e1.toString(), "RFF");

							int calculated5 = calculationOfQTY12Segments(e.toString(), files, display);

							if (detect(ali5, "ALI+") == true) {

								String f = ali4.substring(0, ali5.indexOf("ALI+"));
								String f1 = charBuilder(ali5.toString(), "ALI+");

								// Nur Ali Segment speichern
								String aliSegment6 = charBuilderBefore(f1.toString(), "RFF+");
								// Nur ali entfernen.
								String ali6 = charBuilder(f1.toString(), "RFF");

								int calculated6 = calculationOfQTY12Segments(f.toString(), files, display);

							} else {
								String part1 = qtyFieldMakerForMultyumlagerung(calculated, a);
								String part2 = qtyFieldMakerForMultyumlagerung(calculated2, b);
								String part3 = qtyFieldMakerForMultyumlagerung(calculated3, c);
								String part4 = qtyFieldMakerForMultyumlagerung(calculated4, d);
								String part5 = qtyFieldMakerForMultyumlagerung(calculated5, e);

								String ende = e1;

								textNew.append(part1);
								textNew.append(aliSegment);
								textNew.append(part2);
								textNew.append(aliSegment2);
								textNew.append(part3);
								textNew.append(aliSegment3);
								textNew.append(part4);
								textNew.append(aliSegment4);
								textNew.append(part5);
								textNew.append(ende);

								//FileImpExp.safeNewFile(textNew.toString(), files);
								
								//##BugFix Slippery Slope
								Extensions.checkFile(textNew.toString(), files, display);

							}

						} else {

							System.out.print("Halllooo--------------------------------------------");

							String part1 = qtyFieldMakerForMultyumlagerung(calculated, a);
							String part2 = qtyFieldMakerForMultyumlagerung(calculated2, b);
							String part3 = qtyFieldMakerForMultyumlagerung(calculated3, c);
							String part4 = qtyFieldMakerForMultyumlagerung(calculated4, d);
							String ende = d1;

							textNew.append(part1);
							textNew.append(aliSegment);
							textNew.append(part2);
							textNew.append(aliSegment2);
							textNew.append(part3);
							textNew.append(aliSegment3);
							textNew.append(part4);
							textNew.append(ende);

							System.out.print(textNew);

							//FileImpExp.safeNewFile(textNew.toString(), files);
							
							//##BugFix Slippery Slope
							Extensions.checkFile(textNew.toString(), files, display);


						}

					} else {

						String part1 = qtyFieldMakerForMultyumlagerung(calculated, a);
						String part2 = qtyFieldMakerForMultyumlagerung(calculated2, b);
						String part3 = qtyFieldMakerForMultyumlagerung(calculated3, c);
						String ende = c1;

						textNew.append(part1);
						textNew.append(aliSegment);
						textNew.append(part2);
						textNew.append(aliSegment2);
						textNew.append(part3);
						textNew.append(ende);

						//FileImpExp.safeNewFile(textNew.toString(), files);
						
						//##BugFix Slippery Slope
						Extensions.checkFile(textNew.toString(), files, display);


					}
				} else {
					String part1 = qtyFieldMakerForMultyumlagerung(calculated, a);
					String part2 = qtyFieldMakerForMultyumlagerung(calculated2, b);
					String ende = b1;

					textNew.append(part1);
					textNew.append(aliSegment);
					textNew.append(part2);
					textNew.append(ende);

					//FileImpExp.safeNewFile(textNew.toString(), files);
					
					//##BugFix Slippery Slope
					Extensions.checkFile(textNew.toString(), files, display);


				}
			} else {
				String part1 = qtyFieldMakerForMultyumlagerung(calculated, a);
				String ende = a1;

				textNew.append(part1);
				textNew.append(aliSegment);
				textNew.append(ende);

				//FileImpExp.safeNewFile(textNew.toString(), files);
				
				//##BugFix Slippery Slope
				Extensions.checkFile(textNew.toString(), files, display);


			}
		} else {
			System.out.print("Errorcode: Pineapple");
		}
	
		return null;

}

	/**
	 * Diese Methode untersucht nach einem vorgegebenen String und Sucht diesen im
	 * text und gibt ein wahr oder falsch zurück
	 * 
	 * @param stringToDetect
	 * @return
	 */
	public static boolean detect(String text, String find) {

		boolean detected = false;

		if (text.contains(find)) {
			detected = true;
			return detected;
		} else {
			detected = false;
			return detected;
		}

	}

	/**
	 * Diese Methode zähl bis zum Char ' und setzt den String danach nochmal neu
	 * zusammen, um das QTY+11 Segment zu entfernen.
	 * 
	 * @param hey
	 * @return
	 */
	public static String charBuilder(String text, String charToDetect) {

		StringBuilder oldText = new StringBuilder();
		StringBuilder newText = new StringBuilder();

		oldText.append(text);

		int counterA = oldText.indexOf(charToDetect.toString());

		for (int i = 0; i < oldText.length(); i++) {

			if (i >= counterA) {
				newText.append(oldText.charAt(i));
			}

		}

		return newText.toString();

	}

	/**
	 * Diese Methode filtert ein Segment heraus und druckt alle bis zu diesem
	 * vorkommende Chars aus.
	 * 
	 * @param text
	 * @param charToDetect
	 * @return
	 */
	public static String charBuilderBefore(String text, String charToDetect) {

		StringBuilder oldText = new StringBuilder();
		StringBuilder newText = new StringBuilder();

		oldText.append(text);

		int counterA = oldText.indexOf(charToDetect.toString());

		for (int i = 0; i < oldText.length(); i++) {

			if (i < counterA) {
				newText.append(oldText.charAt(i));

			}

		}

		return newText.toString();

	}

	/**
	 * Diese Methode dient zur Zusammensetzung des QTY12 Segments für einen
	 * Umlagerungsauftrag mit einem QTY12 Segment
	 * 
	 * @param qty12Value
	 * @throws Exception
	 */
	public static void qtyFieldMaker(int qty12Value, StringBuffer textWithQTY12, File files, JTextArea display) throws Exception {
		// Zuerst werden die Werte gesplittet und dann mit demneuen zuvor berechneten
		// Wert für QTY 12 zusammengefügt
		String newValue12_1 = "QTY+12:";
		String newValue12_2 = ":PCE'";

		// Zusammengesetztes QTY12 Feld
		String NewValueResult = newValue12_1 + qty12Value + newValue12_2;

		// Lokale Variablen die hier benötigt werden
		String newText;
		String newText2;

		// Ab hier wird die neue EDI Datei erzeugt und zusammengesetzt
		StringBuffer textNeeww = new StringBuffer();

		// Bis zum CPS' Segment wird gespeichert
		String newData1 = textWithQTY12.substring(0, textWithQTY12.indexOf("QTY+12"));

		// Ab dem richtigen CPS Segment wird gespeichert
		String newData2 = textWithQTY12.substring(textWithQTY12.indexOf("ALI+"));

		// Hier werden beide richtigen Teile zusammengefügt
		textNeeww.append(newData1);
		textNeeww.append(NewValueResult);
		textNeeww.append("\n");
		textNeeww.append(newData2);

		//FileImpExp.safeNewFile(textNeeww.toString(), files);
		
		//##BugFix Slippery Slope
		Extensions.checkFile(textNeeww.toString(), files, display);


	}

	public static String qtyFieldMakerForMultyumlagerung(int qty12Value, String a) {
		// Zuerst werden die Werte gesplittet und dann mit demneuen zuvor berechneten
		// Wert für QTY 12 zusammengefügt
		String newValue12_1 = "QTY+12:";
		String newValue12_2 = ":PCE'";

		// Zusammengesetztes QTY12 Feld
		String NewValueResult = newValue12_1 + qty12Value + newValue12_2;

		// Lokale Variablen die hier benötigt werden
		String newText;
		String newText2;

		// Ab hier wird die neue EDI Datei erzeugt und zusammengesetzt
		StringBuffer textNeeww = new StringBuffer();

		// Bis zum CPS' Segment wird gespeichert
		String newData1 = a.substring(0, a.indexOf("QTY+12:"));

		// Ab dem richtigen CPS Segment wird gespeichert
		// String newData2 = a.substring(a.indexOf("RFF+"));

		// Hier werden beide richtigen Teile zusammengefügt
		textNeeww.append(newData1);
		textNeeww.append(NewValueResult);
		textNeeww.append("\n");

		return textNeeww.toString();

	}

	/**
	 * Diese Methode zählt die vorkommenden QTY12 Segmente, um entscheiden zu
	 * können, ob es sich um einen Umlagerungsauftrag mit mehreren Aufträgen darin
	 * handelt, da hier die Datei anders zusammengesetzt ist sowie anders
	 * verarbeitet werden muss.
	 * 
	 * @param text
	 * @return
	 */
	public static int stringCounter5(String text, String find) {

		String strFind = find.toString();
		int count = 0;
		int fromIndex = 0;

		int[] meinArray = new int[15];

		while ((fromIndex = text.indexOf(strFind, fromIndex)) != -1) {

			count++;
			fromIndex++;

			// System.out.print(" "+ fromIndex + " ");
		}

		return count;
	}

	/**
	 * Diese Methode prüft, ob ein Qty12 Segment leer ist
	 */
	public static int qty12SingleLeftCheck(StringBuffer text2) {
		String strFind = "QTY+12:0:PCE'";
		int count = 0;
		int fromIndex = 0;

		while ((fromIndex = text2.indexOf(strFind, fromIndex)) != -1) {

			count++;
			fromIndex++;

		}

		return count;

	}
	
}
