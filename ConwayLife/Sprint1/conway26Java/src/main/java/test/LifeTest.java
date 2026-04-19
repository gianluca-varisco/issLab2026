package main.java.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.Life;
import main.java.conway.domain.LifeInterface;



public class LifeTest {
	private static final int nRows=5;
	private static final int nCols=5;
	private LifeInterface lifemodel; 

	@Before
	public void setup() {
		System.out.println("LifeTest | setup");	
		lifemodel = new Life(nRows,nCols);
	}

	@After
	public void down() {
		System.out.println("LifeTest | down");
	}

	/* controllo con una configurazione nota se l'evoluzione del gioco è quella che mi aspetto */
	@Test
	public void testOscilla() {
		System.out.println("LifeTest | oscilla "  );
		// Configurazione orizzontale
	    lifemodel.setCell(2, 1, true); 
	    lifemodel.setCell(2, 2, true);
	    lifemodel.setCell(2, 3, true);
	    System.out.println("testOscilla | Stato Iniziale:\n" + lifemodel.getGrid());

	    lifemodel.nextGeneration();
	    System.out.println("testOscilla | after 1 gen:\n" + lifemodel.getGrid());
	    // Verifica che sia diventato verticale
	    assertTrue(lifemodel.isAlive(1, 2)); 
	    assertTrue(lifemodel.isAlive(2, 2));
	    assertTrue(lifemodel.isAlive(3, 2));
	    assertFalse(lifemodel.isAlive(2, 1));

	    lifemodel.nextGeneration();
	    System.out.println("testOscilla | after 2 gen :\n" + lifemodel.getGrid());
	    // Verifica che sia tornato orizzontale (Periodo 2)
	    assertTrue(lifemodel.isAlive(2, 1));
	    assertTrue(lifemodel.isAlive(2, 2));
	    assertTrue(lifemodel.isAlive(2, 3));
	}	

	//@Test
	//Eliminato perchè basato su rappresentazione concreta
//	public void testOscillaFromFile() throws Exception {
//	    // Carico un Blinker (periodo 2)
//	    System.out.println( "testOscillaFromFile ---------------------" );
//	    boolean[][] initial = PatternLoader.loadFromResource("src/test/resources/blinker.txt", 5, 5);
//	    
// 	    Life lifemodel = new Life(initial);
//	    
//	    System.out.println( lifemodel.gridRep() );
//	    System.out.println( "________________________ testOscillaFromFile " );    
////
//	    lifemodel.nextGeneration(); // Generazione 1 (cambia stato)
//	    System.out.println( lifemodel.gridRep() );
//	    System.out.println( "________________________ testOscillaFromFile " );
//	    lifemodel.nextGeneration(); // Generazione 2 (deve tornare all'originale)
//	    System.out.println( lifemodel.gridRep() );
//	    System.out.println( "________________________ testOscillaFromFile " );
////
// 	    assertArrayEquals("L'oscillatore deve tornare allo stato iniziale dopo 2 passi", 
// 	                      initial, lifemodel.getGrid());
//	}
}
