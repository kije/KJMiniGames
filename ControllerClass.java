import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
@SuppressWarnings("serial")

/*
 *	Main Class - Here starts all 
 *
 * 
 * COPYRIGHT 
 * ©2013 by Kim Jeker (info@kije.ch)
 * Source available on GitHub (https://github.com/kije/KJMiniGames)
 * 
 */

public class ControllerClass extends JFrame implements ActionListener {
	// Die einzelnen "Unterprogramme"
	public KJMineSweeper mineSweeper;
	public KJNumberQuiz numberQuiz;
	public KJMiniGamesDocumentation documentation;

	// Die Buttons um die verschiedenen Anwendungen zu starten
	public JButton mineSweeperButton = new JButton("MineSweeper");
	public JButton numbersQuizButton = new JButton("Zahlen raten");
	public JButton documentationButton = new JButton("Dokumentation");

	/************* MAIN **************/
	public static void main(String[] args) {
		new ControllerClass();
	}
	/*********** END MAIN ***********/
	
	public ControllerClass() {
		// Fenster vorbereiten
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Projekt - Modul 103 - Kim Jeker");
		setLayout(new FlowLayout());
		setResizable(false); 
		
		// Buttons hinzufügen
		mineSweeperButton.addActionListener(this);
		add(mineSweeperButton);
		
		numbersQuizButton.addActionListener(this);
		add(numbersQuizButton);

		documentationButton.addActionListener(this);
		add(documentationButton);
		
		pack();
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}
	

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == mineSweeperButton) {
			mineSweeper = new KJMineSweeper("MineSweeper", 15, 15, 0.1); // Minesweeper starten
		} else if (event.getSource() == numbersQuizButton) {
			numberQuiz = new KJNumberQuiz("Number Quiz"); // Number Quiz starten
		} else if (event.getSource() == documentationButton) {
			documentation = new KJMiniGamesDocumentation("Dokumentation"); // Dokumentation anzeigen
		}
	}
}