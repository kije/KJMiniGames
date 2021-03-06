import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")

public class KJNumberQuiz extends JFrame implements ActionListener {
	public JLabel infoLabel = new JLabel();
	public JLabel messageLabel = new JLabel();
	public JLabel tryLabel = new JLabel();
	public JTextField inputField = new JTextField();
	public JButton actionButton = new JButton();

	public int[] range = new int[2];
	public int factor = 15;
	private GameStates gameState = GameStates.NOT_INIT;

	public int unguessableNumber;

	private int tries = 0;

	public KJNumberQuiz(String windowTitle) {
		// Fenster vorbereiten
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(windowTitle);
		setLayout(null); // Null-Layout
		setResizable(false); 
		setMinimumSize(new Dimension(400,300));


		prepare(); // Fensterinhalt hinzufügen / Spiel aufbauen

		pack();
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}

	public void prepare() {
		tries = 1;

		// Anzeige mit den Versuchen
		tryLabel.setBounds(10, 0, 90, 30);
		tryLabel.setForeground(Color.GRAY); // Schriftfarbe setzen
		updateTries();
		add(tryLabel);

		infoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Text Zentrieren 
		infoLabel.setBounds(0, 40, this.getBounds().width, 30); // Label über die ganze Breite
		generateRandomNumber();
		infoLabel.setText(String.format("Die Zahl liegt irgendwo zwischen %d und %d!", range[0], range[1])); 
		add(infoLabel);

		inputField.setBounds((int)(this.getBounds().width/6*1), (int)(infoLabel.getBounds().y + infoLabel.getBounds().height + 30), (int)(this.getBounds().width/3*2), 30); // Label mit jeweils 1/6 der Fensterbreite Abstand zum Rand positionieren
		inputField.setText("");
		inputField.setFocusCycleRoot(true); // damit von anfang an in das Feld geschrieben werden kann (das Feld hat den Focus von anfang an)
		add(inputField);

		actionButton.setBounds((int)(this.getBounds().width/6*1), (int)(inputField.getBounds().y + inputField.getBounds().height + 30), (int)(this.getBounds().width/3*2), 30);
		actionButton.setText("Raten!!!");
		actionButton.addActionListener(this);
		add(actionButton);

		messageLabel.setBounds(0, (int)(actionButton.getBounds().y + inputField.getBounds().height + 30), this.getBounds().width, 30);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setText("");
		add(messageLabel);

		gameState = GameStates.RUNNING; // GamesState is running ;)

	}

	public void updateTries() {
		tryLabel.setText(String.format("Versuche: %d", tries));
	}

	// Im Folgenden wird zuerst eine Methode definiert, und dann wird diese Methode überladen (Also nochmals mit gleichem Namen aber anderen Parametern definiert)
	// Das führt dazu, dass ich die Methode auf zwei verschiedene Arten aufrufen kann (entweder mit oder ohne Parameter)

	protected void generateRandomNumber() {
		// Weils Spass macht: Zwei unnötig komplexe Formeln für zufällige Zahlen
		int min = this.range[0] = (int)Math.min(Math.max(Math.pow(Math.random(),-2), 1), 40); // GENERATE A RANDOM NUMBER ;)
		int max = (int)Math.max(Math.random()*this.factor+1, min+5); // GENERATE A BIGER RANDOM NUMBER ;)

		generateRandomNumber(min,max);
	}

	protected void generateRandomNumber(int min, int max) { // Überladene Methode
		max = max-min;
		unguessableNumber = (int)(Math.random()*max+min);

		range[0] = min;
		range[1] = max + min;

		System.out.println(unguessableNumber);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == actionButton) { 
			switch(gameState) {
				case RETRY:
					tries++;
				case RUNNING:
					if ((!inputField.getText().isEmpty()) && Integer.parseInt(inputField.getText()) == unguessableNumber) {
						messageLabel.setForeground(Color.GREEN);
						messageLabel.setText("Super, du hast die Zahl gefunden!");
						actionButton.setText("Neues Spiel?");
						gameState = GameStates.WIN;
					} else {
						messageLabel.setForeground(Color.RED);
						messageLabel.setText(String.format("Nicht ganz! Zahl zu %s! Versuch es noch einmal!", (Integer.parseInt(inputField.getText()) > unguessableNumber ? "gross" : "klein")));
						actionButton.setText("Nochmals Raten!");
						gameState = GameStates.RETRY;
					}
					break;

				case WIN: 
					prepare();
					break;
			}
			inputField.setText("");
			updateTries();
		}
	}

}
