package main.java.conway.domain;

public class Cell implements ICell{
	/* Definisco la rappresentazione concreta di una cella */ 
	private boolean value;
	
	/* Costruttore default -> assumo che ogni cella venga creata morta */
	public Cell() {
		this.value = false;
	}

	/* Costruttore che mi permette di specificare lo stato in cui nasce la cella */
	public Cell(boolean b) {
		this.value = b;
	}

	@Override
	public boolean isAlive() {
		return value;
	}

	@Override
	public void setStatus(boolean status) {
		value = status;
	}

	@Override
	public void switchStatus() {
		value = !value;		
	}

}
