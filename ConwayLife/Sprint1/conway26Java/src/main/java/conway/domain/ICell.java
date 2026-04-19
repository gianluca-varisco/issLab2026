package main.java.conway.domain;

public interface ICell {
	/* l'entità ha la capacità di rispondere con il proprio stato (viva o morta) */
	public boolean isAlive();
	
	/* l'entità ha la capacità di modificare il proprio stato interno secondo un valore booleano passato */
	public void setStatus(boolean status);
	
	/* l'entità ha la capacità di passare da viva a morta e viceversa */
	public void switchStatus();
}
