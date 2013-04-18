import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial") // Nervende Warnung abschalten

/**
 * Einfache version eines Minesweeper-Games
 * @author Kim Jeker
 * @extends Frame 	
 * @implements ActionListener
 * @todo Aufräumen!!!
 */
public class KJMineSweeper extends Frame implements ActionListener {
	// Quasi-«Konstanten»
	private static int ROWS;
	private static int COLUMS;
	private static double MINEFIELDS_TO_NORMALFIELDS_RATIO;

	protected MineCell[][] fields;

	/**
	 * Konstruktor
	 * @param windowTitle	String		Titel des Fensters
	 * @param rows			int			Wie viele «Zeilen» soll das Spielfeld haben
	 * @param colums		int 		Wie viele Felder soll es in einer Zeile haben
	 * @param ratio		double			Ungefähres Verhältniss der Minenfelder zu den normalen Felder
	 */
	public KJMineSweeper(String windowTitle, int rows, int colums, double ratio) {
		ROWS = rows; 
		COLUMS = colums;
		MINEFIELDS_TO_NORMALFIELDS_RATIO = ratio;
		this.fields = new MineCell[ROWS][COLUMS];
		
		System.out.println("Settings");
		System.out.println("Rows: " + ROWS + "\t Colums: " + COLUMS + "\t Felder insgesammt: "+ROWS*COLUMS+ "\t Minefields Ratio: " + MINEFIELDS_TO_NORMALFIELDS_RATIO + "\t ~ number of Mines: "+(int)(ROWS*COLUMS*MINEFIELDS_TO_NORMALFIELDS_RATIO));
		System.out.println("--------------------------------------------------------------------------------------------------------");
		
		// Die nächste Zeile fügt einen sogenannten «WindowListener» dem Fensterobjekt zu.
		// In diesem Falle ist das eine anonyme Klasse, welche die Methode zum Schliessen des Fensters beinhaltet
		addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent e) {
				 e.getWindow().dispose(); // Fenster schliessen
			}
		});
		
		setLayout(new GridLayout(ROWS, COLUMS));
		
		// Alle Zellen hinzufügen
		this.prepare();

		// Fenster vorbereiten und anzeigen
		setTitle(windowTitle);
		pack(); // Fenster auf benötigte Grösse bringen
		setLocationRelativeTo(null); // Fenster zentrieren
		setVisible(true);
	}

	/**
	 * «Spielfeld» vorbereiten
	 */
	protected void prepare() {
		int mines = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMS; j++) {
				this.fields[i][j] = new MineCell(); // Zelle dem Array hinzufügen, damit man später wieder darauf zugreifen kann
				this.fields[i][j].setPosition(i, j);
				this.fields[i][j].isMine = (Math.random() <= MINEFIELDS_TO_NORMALFIELDS_RATIO ? true : false);
				if (this.fields[i][j].isMine) {
					mines++;
				}
				this.fields[i][j].addActionListener(this);
				
				add(this.fields[i][j]);
			}
		}
		System.out.println("Number of mines: "+mines+"\n");
		
		// Für jede Zelle die Anzahl an Mienen, die in der nähe sind, ermitteln
		for (int i = 0; i < this.fields.length; i++) {
			for (int j = 0; j < this.fields[i].length; j++) {
				this.analyzeCellsArround(this.fields[i][j]);
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		boolean doBreak = false;
		// Den geklickten Button suchen
		for (int i = 0; i < this.fields.length; i++) {
			for (int j = 0; j < this.fields[i].length; j++) {
				if (event.getSource() == this.fields[i][j]) {
					doBreak = true;
					this.didClick(this.fields[i][j]);
					break;
				}
			}

			if (doBreak) {
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param cell	MineCell 	 
	 * @return MineCell[]	Array mit Zellen in der Umgebung
	 */
	public MineCell[] getCellsArround(MineCell cell) {
		MineCell[] cells = new MineCell[9];
		
		// Zellen in der Reihe oben drann
		if (cell.getRow() > 0 && cell.getColum() > 0) {
			cells[0] = this.fields[cell.getRow()-1][cell.getColum()-1]; 
		} 
		
		if (cell.getRow() > 0) {
			cells[1] = this.fields[cell.getRow()-1][cell.getColum()]; 
		} 
		
		if (cell.getRow() > 0 && cell.getColum() < COLUMS-1) {
			cells[2] = this.fields[cell.getRow()-1][cell.getColum()+1]; 
		} 
		
		 
		// Zellen in der gleichen Reihe
		if (cell.getColum() > 0) {
			cells[3] = this.fields[cell.getRow()][cell.getColum()-1]; // Links von der Zelle
		} 
		
		if (cell.getColum() < COLUMS-1) {
			cells[4] = this.fields[cell.getRow()][cell.getColum()+1]; // Rechts von der Zelle
		} 
		
		// Zellen in der Reihe unten drann
		if (cell.getRow() < ROWS-1 && cell.getColum() > 0) {
			cells[5] = this.fields[cell.getRow()+1][cell.getColum()-1]; // Unterhalb der Zelle
		}
		
		if (cell.getRow() < ROWS-1) {
			cells[6] = this.fields[cell.getRow()+1][cell.getColum()]; // Unterhalb der Zelle
		}
		
		if (cell.getRow() < ROWS-1 && cell.getColum() < COLUMS-1) {
			cells[7] = this.fields[cell.getRow()+1][cell.getColum()+1]; // Unterhalb der Zelle
		}
		
		
		return cells;
	}
	
	/**
	 * Analysiert die Umgebung der Zelle und setzt deren Wert, wie viele Mienen in der nähe sind
	 * @param cell 	Die zu analysierende Zelle
	 */
	protected void analyzeCellsArround (MineCell cell) { 
		MineCell[] cellsToCheck = this.getCellsArround(cell);
		
		for (int i = 0; i < cellsToCheck.length; i++) {
			if (cellsToCheck[i] != null) {
				if (cellsToCheck[i].isMine) {
					cell.numberOfMinesArround++;
				}
			}
		}
	
	}

	/**
	 * 
	 * @param cell	Zelle, auf die geklickt wurde
	 */
	protected void didClick(MineCell cell) {
		System.out.println("Cell row:" + cell.getRow() + " colum:" + cell.getColum() + "\tIs Mine: " + cell.isMine + "\t Mines arround: "+cell.numberOfMinesArround);
		cell.click();
		if (cell.isMine) {
			this.loose();
		} else if (cell.numberOfMinesArround == 0) {
			// Deke alle Zellen, welche nicht in der nähe eine Miene liegen auf
			showAllEmptyFieldsNearBy(cell);
		}
	}
	
	protected void loose() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMS; j++) {
				fields[i][j].setEnabled(false);
				
				// Zeige alle Minen
				if (fields[i][j].isMine) {
					fields[i][j].click(); 
				}
			}
		}
		setTitle("Verloren! – " + getTitle());
		System.err.println("LOOSE!\n");
	}
	
	// TODO
	protected void win() {
		setTitle("Gewonnen! – " + getTitle());
		System.out.println("WIN!");
	}
	
	/**
	 * Diese Methode überprüft die Zellen in der Umgebung einer Zelle darauf, ob ihr numberOfMinesArround-Wert gleich 0 ist.
	 * Ist dies der Fall, geschehen zwei Dinge:
	 * 1.) wird die Methode click() der Zelle aufgerufen
	 * 2.) wird auf die Zelle noch einmal diese Methode (showAllEmptyFieldsNearBy()) aufgerufen -> Rekursion
	 * 
	 * @param cell	MineCell	Zelle, von der aus überprüft werden soll
	 */
	protected void showAllEmptyFieldsNearBy(MineCell cell) {
		cell.checked = true; // Setze den checked-Wert der Zelle, damit nacher nicht immer und immer wieder die gleiche Zelle überprüft wird (und dass dann am schluss unendlich viele Fehler wirft (StackOverflow))
		MineCell[] cellsToCheck = this.getCellsArround(cell); 
		
		for (int i = 0; i < cellsToCheck.length; i++) {
			if (cellsToCheck[i] != null) {
				if (cellsToCheck[i].numberOfMinesArround == 0 && !cellsToCheck[i].isMine && !cellsToCheck[i].checked && !cellsToCheck[i].wasClicked) {
					cellsToCheck[i].click(); // Trigger Klick
					showAllEmptyFieldsNearBy(cellsToCheck[i]); // ACHTUNG: REKURSION !!!!!!!
				}
			}
		}
	}

}