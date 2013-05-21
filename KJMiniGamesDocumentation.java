import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
@SuppressWarnings("serial")

public class KJMiniGamesDocumentation extends JFrame implements ActionListener {
	public JFrame minesweeperFrame = new JFrame();
	public JFrame numberquizFrame = new JFrame();

	public JButton minesweeperButton = new JButton();
	public JButton numberquizButton = new JButton();
	
	public KJMiniGamesDocumentation(String windowTitle) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(windowTitle);
		setLayout(new GridLayout(2,1));

		minesweeperButton.addActionListener(this);
		numberquizButton.addActionListener(this);

		minesweeperButton.setText("Minesweeper Manual");
		numberquizButton.setText("NumberQuiz Manual");

		add(minesweeperButton);
		add(numberquizButton);

		prepareManuals();

		pack();
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}

	/**
	 * 
	 */
	public void prepareManuals() {
		// MINESWEEPER 
		minesweeperFrame.setLayout(new FlowLayout());
		minesweeperFrame.setTitle("MINESWEEPER MANUAL");
		minesweeperFrame.add(new JLabel("Ziel des Spiels ist es alle Bomben in dem Feld aufzudecken. ")); 
		minesweeperFrame.add(new JLabel("Durch Klicken mit der Maus wird ein Feld aufgedeckt."));
		minesweeperFrame.add(new JLabel("Ist dieses Feld eine Bombe, so ist das Spiel verloren.")); 
		minesweeperFrame.add(new JLabel("Erscheint eine Zahl in einem Feld, so gibt diese an, "));
		minesweeperFrame.add(new JLabel("wieviel Minen in den benachbarten 8 Feldern sind.")); 
		minesweeperFrame.setSize(390, 150);
		minesweeperFrame.setLocationRelativeTo(null);
		minesweeperFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// NUMBERQUIZ
		numberquizFrame.setLayout(new FlowLayout());
		numberquizFrame.setTitle("NUMBERQUIZ MANUAL");
		numberquizFrame.add(new JLabel("In diesem Spiel geh es darum, eine zufällige Zahl zu eraten.")); 
		numberquizFrame.add(new JLabel("Die Zahl befindet sich zwischen den zwei angenzeigten Zahlen.")); 
		numberquizFrame.setSize(410, 100);
		numberquizFrame.setLocationRelativeTo(null);
		numberquizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == minesweeperButton) {
			minesweeperFrame.setVisible(true);
		} else if (event.getSource() == numberquizButton) {
			numberquizFrame.setVisible(true);
		}
	}
}