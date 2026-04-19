package main.java.conway.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Grid implements IGrid{
	/* Definisco la rappresentazione concreta di una griglia */ 
	private final int rows;
    private final int cols;
	private ICell[][] griglia;
	
	//senza costruttore non è duttile, dimensione fissa, costruisce la griglia come pare a lui
	
	public Grid(int nr, int nc) {		// maggiori di 0
		if (nr < 0 || nc < 0)
			throw new IllegalArgumentException("Le dimensioni devono essere naturali (>= 0)");
		
		this.rows = nr;
		this.cols = nc;
		
        this.griglia = new ICell[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.griglia[i][j] = new Cell();
            }
        }
	}

	/*public Grid(boolean[][] initialGrid) {
		this.rows = initialGrid.length; 	
        this.cols = initialGrid[0].length;
        
        this.griglia = new ICell[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.griglia[i][j] = new Cell(initialGrid[i][j]);
            }
        }
	}*/

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	@Override
	public ICell getCell(int row, int col) {
		if (row < 0 || row >= this.rows || col < 0 || col >= this.cols)
			throw new IllegalArgumentException("Riga e colonna devono essere comprese tra 0 e getDim-1");
		return griglia[row][col];
	}

	@Override
	public int countAliveNeighbors(int row, int col) {
		if (row < 0 || row >= this.rows || col < 0 || col >= this.cols)
			throw new IllegalArgumentException("Riga e colonna devono essere comprese tra 0 e getDim-1");
	    int count = 0;

	    // Iteriamo da -1 a +1 rispetto alla posizione della cella (r, c)
	    // Questo crea un'area di scansione 3x3
	    for (int i = -1; i <= 1; i++) {
	        for (int j = -1; j <= 1; j++) {
	            
	            // 1. Saltiamo il caso (0,0) che è la cella stessa
	            if (i == 0 && j == 0) {
	                continue;
	            }

	            int neighborRow = row + i;
	            int neighborCol = col + j;

	            // 2. Verifichiamo che i vicini siano entro i confini della griglia
	            if (neighborRow >= 0 && neighborRow < rows && 
	                neighborCol >= 0 && neighborCol < cols) {
	                
	                // 3. Se la cella in quella posizione è viva, incrementiamo il contatore
	                if (griglia[neighborRow][neighborCol].isAlive()) {
	                    count++;
	                }
	            }
	        }
	    }

	    return count;
	}
	
	@Override
	public void setCellValue(int row, int col, boolean state) {
		if (row < 0 || row >= this.rows || col < 0 || col >= this.cols)
			throw new IllegalArgumentException("Riga e colonna devono essere comprese tra 0 e getDim-1");
		griglia[row][col].setStatus(state);
	}

	@Override
	public boolean getCellValue(int row, int col) {
		if (row < 0 || row >= this.rows || col < 0 || col >= this.cols)
			throw new IllegalArgumentException("Riga e colonna devono essere comprese tra 0 e getDim-1");
		return griglia[row][col].isAlive();
	}

	@Override
	public void reset() {
		for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.griglia[i][j].setStatus(false);;
            }
        }
	}
	
	public String toString() {
	    return Arrays.stream( griglia ) // Stream di Cell[] (le righe)
        .map(row -> {
            // Trasformiamo ogni riga in una stringa di . e O
            StringBuilder sb = new StringBuilder();
            for (ICell cell : row) {
                sb.append(cell.isAlive() ? "O " : ". ");
            }
            return sb.toString();
        })
        .collect(Collectors.joining("\n")); // Uniamo le righe con un a capo  
  }
}
