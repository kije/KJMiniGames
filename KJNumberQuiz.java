import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")

public class KJNumberQuiz extends JFrame implements ActionListener {

	public KJNumberQuiz(String windowTitle) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setTitle(windowTitle);
		
		
		pack();
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
