import java.awt.*;
import java.awt.event.*;
@SuppressWarnings("serial")

public class ControllerClass extends Frame implements ActionListener {
	public static KJMineSweeper mineSweeper;
	public Button mineSweeperButton = new Button("MineSweeper");
	
	public ControllerClass() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { 
				 e.getWindow().dispose(); // Fenster schliessen
				 System.err.println("EXIT");
			     System.exit(0); // Programm beenden
			}
		});
		
		setTitle("Projekt Ð Modul 103 Ð Kim Jeker");
		
		setLayout(new FlowLayout());
		
		this.mineSweeperButton.addActionListener(this);
		
		add(this.mineSweeperButton);
		
		
		pack();
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ControllerClass();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.mineSweeperButton) {
			mineSweeper = new KJMineSweeper("MineSweeper", 15, 15, 0.25);
		}
	}
}