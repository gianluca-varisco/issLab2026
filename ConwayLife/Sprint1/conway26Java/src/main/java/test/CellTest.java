package main.java.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.Cell;
import main.java.conway.domain.ICell;

public class CellTest {
	private ICell c; 
	@Before
	public void setup() {
		System.out.println("CellTest | setup");	
		c = new Cell();		// celle nascono morte
	}
	@After
	public void down() {
		System.out.println("CellTest | down");
	}
	
	/* settando lo stato della cella con true mi aspetto che 
	 * quando la interrogo sul proprio stato mi dica di essere viva */
	@Test
	public void TestCellAlive() {
		System.out.println("CellTest | doing alive");
		c.setStatus(true);
		boolean r = c.isAlive();
		assertTrue(r);
	}
	
	/* settando lo stato della cella con false mi aspetto che 
	 * quando la interrogo sul proprio stato mi dica di essere morta */
	@Test
	public void TestCellDead() {
		System.out.println("CellTest | doing dead");
		c.setStatus(false);
		boolean r = c.isAlive();
		assertTrue(!r);	// oppure assertFalse(r);
	}
	
	/* mi aspetto che andando a definire lo stato e poi a cambiarlo con switch 
	 * quando interrogo la cella sul proprio stato mi dica l'opposto di quello che avevo definito */
	@Test
	public void TestSwitchStatus() {
		System.out.println("CellTest | doing switch status");
		c.setStatus(true);
		c.switchStatus();
		assertFalse(c.isAlive());
		c.switchStatus();
		assertTrue(c.isAlive());
	}
}
