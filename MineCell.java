import java.awt.*;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MineCell extends JButton { // Eine Subklasse von JButton erstellen, um diese dann als Feld im Spiel zu verwenden

	public boolean isMine = false;
	protected int[] position = new int[2]; // Position im "Grid" (Hat keinen Effekt auf das aussehen, nur dazu da, einige Metainformationen zu zelle zu haben)
	// ich denke, das folgende ist selbsterklärend:
	public boolean wasClicked = false;
	public int numberOfMinesArround = 0;
	public boolean checked = false;
	public Dimension size = new Dimension(50,50);

	public MineCell() {
		super(); // Konstruktor der Superklasse aufrufen
		setFocusable(false); // Nervenden baluen Rand bei jeder zelle abschalten (Mac?!?)
		this.setPreferredSize(size);
	}

	public void setPosition(int row, int colum) {
		position[0] = row;
		position[1] = colum;
	}


	public int getRow() {
		return position[0];
	}

	public int getColum() {
		return position[1];
	}

	/**
	 * Diese Methode sorg dafür, dass die Zelle wie erwartet auf einen Klick reagiert
	 */
	public void click() {
		wasClicked = true;
		setText((isMine ? "+" : ""+numberOfMinesArround));
		setEnabled(false);
	}
}
