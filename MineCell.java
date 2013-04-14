import java.awt.Button;

@SuppressWarnings("serial")
public class MineCell extends Button {

	public boolean isMine = false;
	protected int[] position = new int[2]; // Position im "Grid"
	public boolean wasClicked = false;
	public int numberOfMinesArround = 0;
	public boolean checked = false;

	public MineCell() {
		super(); // Konstruktor der Superklasse aufrufen
		setFocusable(false); // Nervenden baluen Rand bei jeder zelle abschalten
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

	public void click() {
		wasClicked = true;
		setLabel((isMine ? "+" : ""+numberOfMinesArround));
		setEnabled(false);
	}
}
