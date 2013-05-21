import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial") // Nervende Warnung abschalten

/**
 * Einfache version eines Minesweeper-Games
 * @author Kim Jeker
 * @extends Frame 	
 * @implements ActionListener
 * @todo Aufr?umen!!!
 */
public class KJMineSweeper extends JFrame implements ActionListener {
	// Quasi-"Konstanten"
	private static int ROWS;
	private static int COLUMS;
	private static double MINEFIELDS_TO_NORMALFIELDS_RATIO;

	// "Statistik"
	private int numberOfMines = 0;
	private int numberClickedFields = 0;

	protected MineCell[][] fields;

	/**
	 * Konstruktor
	 * @param windowTitle	String		Titel des Fensters
	 * @param rows			int			Wie viele «Zeilen» soll das Spielfeld haben
	 * @param colums		int 		Wie viele Felder soll es in einer Zeile haben
	 * @param ratio		double			Ungef?hres Verhältniss der Minenfelder zu den normalen Felder
	 */
	public KJMineSweeper(String windowTitle, int rows, int colums, double ratio) {
		ROWS = rows; 
		COLUMS = colums;
		MINEFIELDS_TO_NORMALFIELDS_RATIO = ratio;
		fields = new MineCell[ROWS][COLUMS];
		
		System.out.println("Settings");
		System.out.println("Rows: " + ROWS + "\t Colums: " + COLUMS + "\t Felder insgesammt: "+ROWS*COLUMS+ "\t Minefields Ratio: " + MINEFIELDS_TO_NORMALFIELDS_RATIO + "\t ~ number of Mines: "+(int)(ROWS*COLUMS*MINEFIELDS_TO_NORMALFIELDS_RATIO));
		System.out.println("--------------------------------------------------------------------------------------------------------");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new GridLayout(ROWS, COLUMS));
		
		// Alle Zellen hinzufügen
		prepare();

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
				fields[i][j] = new MineCell(); // Zelle dem Array hinzuf?gen, damit man später wieder darauf zugreifen kann
				fields[i][j].setPosition(i, j);
				fields[i][j].isMine = (Math.random() <= MINEFIELDS_TO_NORMALFIELDS_RATIO ? true : false);
				if (fields[i][j].isMine) {
					mines++;
				}
				fields[i][j].addActionListener(this);
				
				add(fields[i][j]);
			}
		}
		System.out.println("Number of mines: "+mines+"\n");
		numberOfMines = mines;
		
		// Für jede Zelle die Anzahl an Mienen, die in der nähe sind, ermitteln
		for (MineCell[] row : fields) {
			for (MineCell cell : row) {
				analyzeCellsArround(cell);
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		boolean doBreak = false;
		// Den geklickten Button suchen
		for (MineCell[] row : fields) {
			for (MineCell cell : row) {
				if (event.getSource() == cell) {
					doBreak = true;
					didClick(cell);
					break;
				}
			}

			if (doBreak) {
				break;
			}
		}

		if (hasWon()) {
			win();
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
			cells[0] = fields[cell.getRow()-1][cell.getColum()-1]; 
		} 
		
		if (cell.getRow() > 0) {
			cells[1] = fields[cell.getRow()-1][cell.getColum()]; 
		} 
		
		if (cell.getRow() > 0 && cell.getColum() < COLUMS-1) {
			cells[2] = fields[cell.getRow()-1][cell.getColum()+1]; 
		} 
		
		 
		// Zellen in der gleichen Reihe
		if (cell.getColum() > 0) {
			cells[3] = fields[cell.getRow()][cell.getColum()-1]; 
		} 
		
		if (cell.getColum() < COLUMS-1) {
			cells[4] = fields[cell.getRow()][cell.getColum()+1]; 
		} 
		
		// Zellen in der Reihe unten drann
		if (cell.getRow() < ROWS-1 && cell.getColum() > 0) {
			cells[5] = fields[cell.getRow()+1][cell.getColum()-1]; 
		}
		
		if (cell.getRow() < ROWS-1) {
			cells[6] = fields[cell.getRow()+1][cell.getColum()]; 
		}
		
		if (cell.getRow() < ROWS-1 && cell.getColum() < COLUMS-1) {
			cells[7] = fields[cell.getRow()+1][cell.getColum()+1]; 
		}
		
		
		return cells;
	}
	
	/**
	 * Analysiert die Umgebung der Zelle und setzt deren Wert, wie viele Mienen in der nähe sind
	 * @param cell 	Die zu analysierende Zelle
	 */
	protected void analyzeCellsArround (MineCell cell) { 
		MineCell[] cellsToCheck = getCellsArround(cell);
		
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
			loose();
		} else if (cell.numberOfMinesArround == 0) {
			numberClickedFields++;
			// Deke alle Zellen, welche nicht in der n?he eine Miene liegen auf
			showAllEmptyFieldsNearBy(cell);
		}
	}
	
	protected void loose() {
		for (MineCell[] row : fields) {
			for (MineCell cell : row) {
				cell.setEnabled(false);
				
				// Zeige alle Minen
				if (cell.isMine) {
					cell.click(); 
				}
			}
		}
		setTitle("Verloren! - " + getTitle());
		System.err.println("LOOSE!\n");
	}
	

	protected boolean hasWon() { // Funktioniert das???
		int totalField = ROWS*COLUMS;
		int notMineFields = totalField-numberOfMines;
		return (notMineFields<numberClickedFields);
	}

	protected void win() {
		for (MineCell[] row : fields) {
			for (MineCell cell : row) {
				cell.setEnabled(false);
				
				// Zeige alle Minen
				if (cell.isMine) {
					cell.click(); 
				}
			}
		}
		setTitle("Gewonnen! - " + getTitle());
		System.out.println("WIN!");
	}
	
	/**
	 * Diese Methode Überprüft die Zellen in der Umgebung einer Zelle darauf, ob ihr numberOfMinesArround-Wert gleich 0 ist.
	 * Ist dies der Fall, geschehen zwei Dinge:
	 * 1.) wird die Methode click() der Zelle aufgerufen
	 * 2.) wird auf die Zelle noch einmal diese Methode (showAllEmptyFieldsNearBy()) aufgerufen -> Rekursion
	 * 
	 * @param cell	MineCell	Zelle, von der aus überprüft werden soll
	 */
	protected void showAllEmptyFieldsNearBy(MineCell cell) {
		cell.checked = true; // Setze den checked-Wert der Zelle, damit nacher nicht immer und immer wieder die gleiche Zelle überprüft wird (und dass dann am schluss unendlich viele Fehler wirft (StackOverflow) weil eine "Endlosschleife" erzeugt wird)
		MineCell[] cellsToCheck = getCellsArround(cell); 
		
		for (int i = 0; i < cellsToCheck.length; i++) {
			if (cellsToCheck[i] != null) {
				if (cellsToCheck[i].numberOfMinesArround == 0 && !cellsToCheck[i].isMine && !cellsToCheck[i].checked && !cellsToCheck[i].wasClicked) {
					cellsToCheck[i].click(); // Trigger Klick
					numberClickedFields++;
					showAllEmptyFieldsNearBy(cellsToCheck[i]); // ACHTUNG: REKURSION !!!!!!!
				}
			}
		}
	}

}