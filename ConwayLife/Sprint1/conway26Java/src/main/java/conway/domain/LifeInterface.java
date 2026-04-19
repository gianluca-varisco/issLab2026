package main.java.conway.domain;

public interface LifeInterface {
	/** Calcola l'evoluzione dello stato alla generazione successiva */
    void nextGeneration();	// primitiva

    /** Restituisce lo stato di una cella specifica */
    boolean isAlive(int row, int col) throws IllegalArgumentException;	// non primitiva

    /** Imposta lo stato di una cella*/
    void setCell(int row, int col, boolean alive) throws IllegalArgumentException;	// non primitiva

    /** Restituisce il numero di righe e colonne */
//    int getRows();	// non primitiva
//    int getCols();	// non primitiva
    
    /** Restituisce la Cella */
    ICell getCell(int row, int col) throws IllegalArgumentException;	// non primitiva
    
    /** Restituisce la grid */
    IGrid getGrid();	// primitiva
    
    /** pulisce */
    void resetGrids();	// non primitiva
    
    /** Restituisce una rappresentazione grafica testuale della grglia*/
    public String gridRep( );	// non primitiva
}
