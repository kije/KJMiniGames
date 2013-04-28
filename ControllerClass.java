import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
@SuppressWarnings("serial")

/*
 * INFO
 * 
 * CODING STYLE
 * 
 * 
 * AUFBAU
 * 
 * 
 * COPYRIGHT 
 * ©2013 by Kim Jeker (info@kije.ch)
 * Source available on GitHub (https://github.com/kije/KJMiniGames)
 * 
 */

public class ControllerClass extends JFrame implements ActionListener {
	public KJMineSweeper mineSweeper;
	public KJNumberQuiz numberQuiz;
	public JButton mineSweeperButton = new JButton("MineSweeper");
	public JButton numbersQuizButton = new JButton("Zahlen raten");
	
	public ControllerClass() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("Projekt - Modul 103 - Kim Jeker");
		
		setLayout(new FlowLayout());
		

		mineSweeperButton.addActionListener(this);
		add(mineSweeperButton);
		
		numbersQuizButton.addActionListener(this);
		add(numbersQuizButton);
		
		pack();
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ControllerClass();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == mineSweeperButton) {
			mineSweeper = new KJMineSweeper("MineSweeper", 15, 15, 0.2);
		} else if (event.getSource() == numbersQuizButton) {
			numberQuiz = new KJNumberQuiz("Number Quiz");
		}
	}
}