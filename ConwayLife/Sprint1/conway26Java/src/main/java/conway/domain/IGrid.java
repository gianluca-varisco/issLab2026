package main.java.conway.domain;

public interface IGrid {
	/* l'entità deve essere in grado di restituire le proprie dimensioni */
	public int getRows();	// primitiva
    public int getCols();	// primitiva
    
    /* entità deve essere in grado di restituire una sua componente */
    public ICell getCell(int row, int col) throws IllegalArgumentException;		// primitiva
    
    /* entità può settare lo stato di una cella indicata */
    public void setCellValue(int row, int col, boolean state) throws IllegalArgumentException;	// non primitiva
    
    /* entità può ottenere il valore di una cella indicata*/
    public boolean getCellValue(int row, int col) throws IllegalArgumentException;	// non primitiva
    
    /* entità può contare i vicini di una cella indicata */
    public int countAliveNeighbors(int row, int col) throws IllegalArgumentException;	// non primitiva
    
    /* l'entità fornisce un metodo per resettare lo stato di tutti i componenti (celle) */
    public void reset(); // // non primitiva
}
