import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
@SuppressWarnings("serial")

public class KJNumberQuiz extends JFrame implements ActionListener {

	public KJNumberQuiz(String windowTitle) {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { 
				 e.getWindow().dispose(); // Fenster schliessen
			}
		});
		
		setTitle(windowTitle);
		
		
		pack();
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
