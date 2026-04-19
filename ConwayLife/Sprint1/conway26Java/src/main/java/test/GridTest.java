package main.java.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.Grid;
import main.java.conway.domain.IGrid;



public class GridTest {
	private IGrid g;
    private final int ROWS = 5;
    private final int COLS = 5;
    
	@Before
	public void setup() {
		System.out.println("GridTest | setup");	
		g = new Grid(ROWS, COLS);		// creo una griglia 5x5
	}

	@After
	public void down() {
		System.out.println("GridTest | down");
	}
	
	/* per verificare che non possono esistere numero di righe e colonne negativi
	 * cerco di creare una griglia con valori negativi e verifico che viene lanciata un'eccezione */
	@Test(expected = IllegalArgumentException.class)
    public void testRowNegativoLanciaEccezione() {
		System.out.println("GridTest | doing natural row");
        new Grid(-1, 10);
    }
	@Test(expected = IllegalArgumentException.class)
	public void testColNegativoLanciaEccezione() {
		System.out.println("GridTest | doing natural cols");
        new Grid(10, -1);
    }
	
	/* controllo che getRow e getDim mi restituiscano le dimensioni con cui ho creato la griglia */
	@Test
	public void testDims() {
		System.out.println("GridTest | doing dims");
		int nr = g.getRows();
		int nc = g.getCols();
		assertTrue( nr==ROWS && nc==COLS);
	}
	
	/* controllo che in tutte le funzioni in cui devo passare row e col 
	 * venga lancia una eccezione se non rientrano nel range [0, getDim-1] */
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCell1() {
		System.out.println("GridTest | doing getCell with negative row");
		g.getCell(-1,3);	// row è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCell2() {
		System.out.println("GridTest | doing getCell with row >= getRows");
		g.getCell(5,3);	// row è maggiore di getRows-1
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCell3() {
		System.out.println("GridTest | doing getCell with negative col");
		g.getCell(2,-3);	// col è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCell4() {
		System.out.println("GridTest | doing getCell with col >= getCols");
		g.setCellValue(5,7,true);	// col è maggiore di getCols-1
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiSetCellValue1() {
		System.out.println("GridTest | doing setCellValue with negative row");
		g.setCellValue(-1,3,true);	// row è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiSetCellValue2() {
		System.out.println("GridTest | doing setCellValue with row >= getRows");
		g.setCellValue(5,3,true);	// row è maggiore di getRows-1
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiSetCellValue3() {
		System.out.println("GridTest | doing setCellValue with negative col");
		g.setCellValue(2,-3,true);	// col è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiSetCellValue4() {
		System.out.println("GridTest | doing setCellValue with col >= getCols");
		g.setCellValue(5,7,true);	// col è maggiore di getCols-1
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCellValue1() {
		System.out.println("GridTest | doing getCellValue with negative row");
		g.getCellValue(-1,3);	// row è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCellValue2() {
		System.out.println("GridTest | doing getCellValue with row >= getRows");
		g.getCellValue(5,3);	// row è maggiore di getRows-1
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCellValue3() {
		System.out.println("GridTest | doing getCellValue with negative col");
		g.getCellValue(2,-3);	// col è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiGetCellValue4() {
		System.out.println("GridTest | doing getCellValue with col >= getCols");
		g.getCellValue(5,7);	// col è maggiore di getCols-1
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiCount1() {
		System.out.println("GridTest | doing countAliveNeighbors with negative row");
		g.countAliveNeighbors(-1, 1);	// row è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiCount2() {
		System.out.println("GridTest | doing countAliveNeighbors with row >= getRows");
		g.countAliveNeighbors(8, 1);	// row è maggiore di getRows-1
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiCount3() {
		System.out.println("GridTest | doing countAliveNeighbors with negative col");
		g.countAliveNeighbors(1, -21);	// col è minore di 0
	}
	@Test(expected = IllegalArgumentException.class)
	public void testParametriErratiCount4() {
		System.out.println("GridTest | doing countAliveNeighbors with col >= getCols");
		g.countAliveNeighbors(0, 10);	// col è maggiore di getCols-1
	}
	
	/* controllo che setCellValue vada a settare la sola cella indicata e 
	 * contemporaneamente controllo anche getCell mi restituisca la cella che mi aspetto 
	 * inolte controllo che getCellValue mi restituisca lo stesso valore di getCell.isAlive*/
	@Test
	public void testGridCellValue() {
		System.out.println("GridTest | doing getCell e cellValue");
		g.setCellValue(0,0,true);
		assertEquals( g.getCell(0, 0).isAlive(), g.getCellValue(0,0) );
		assertEquals( g.getCell(0, 1).isAlive(), g.getCellValue(0,1) );
		assertTrue(   g.getCellValue(0,0) );
		assertFalse(  g.getCellValue(0,1) );
	}
	@Test
	public void testGridRep() {
		System.out.println("GridTest | doing gridRep");
 		System.out.println(""+g);
		assertTrue( g.toString().startsWith(". . . . ."));
	}
	
	/* controllo il funzionamento di reset: setto a true una cella, resetto e 
	 * vado a controllare che sia morta*/
    @Test
    public void testClear() {
    	System.out.println("GridTest | doing clear");
        g.getCell(1, 1).setStatus(true);
        g.reset();
        assertFalse(g.getCell(1, 1).isAlive());
    }
    
    /* controllo il funzionamento del calcolo dei vicini, andando a definire una configurazione e 
     * controllando se il risultato è quello atteso
     * sia per una cella al centro sia per un angolo (ha meno vicini) */
    @Test
    public void testCountAliveNeighborsBasic() {
    	System.out.println("GridTest | doing count alive neighbors BASIC");
        /*
          Configurazione (al centro):
          M V M
          M C M
          V M V
          C = Cella sotto esame (2,2). Vicini vivi attesi: 3
        */
        g.getCell(1, 2).setStatus(true);
        g.getCell(3, 1).setStatus(true);
        g.getCell(3, 3).setStatus(true);

        assertEquals(3, g.countAliveNeighbors(2, 2));
    }
    @Test
    public void testCountAliveNeighborsEdges() {
    	System.out.println("GridTest | doing count alive neighbors EDGES");
        // Test agli angoli (0,0)
        g.getCell(0, 1).setStatus(true);
        g.getCell(1, 1).setStatus(true);

        // La cella 0,0 ha 2 vicini vivi
        assertEquals(2, g.countAliveNeighbors(0, 0));
    }
}

