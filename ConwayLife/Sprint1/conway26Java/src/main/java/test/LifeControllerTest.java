package main.java.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.devices.MockOutdev;
import main.java.conway.domain.GameController;
import main.java.conway.domain.IOutDev;
import main.java.conway.domain.Life;
import main.java.conway.domain.LifeController;
import main.java.conway.domain.LifeInterface;
import unibo.basicomm23.utils.CommUtils;

public class LifeControllerTest {
	private static final int nRows=5;
	private static final int nCols=5;
	
	@Before
	public void setup() {
		System.out.println("ConwayLifeTest | setup");	
	}

	@After
	public void down() {
		System.out.println("ConwayLifeTest | down");
	}

	/* controllo che LifeController funzioni */
	@Test
	public void testAppl() {
		LifeInterface gameModel       = new Life(nRows, nCols);
		IOutDev outputDevice          = new MockOutdev();
		GameController lifeController = new LifeController(gameModel, outputDevice);
		int genTime                   = lifeController.getGenTime();
        lifeController.switchCellState(2, 1);
        lifeController.switchCellState(2, 2);
        lifeController.switchCellState(2, 3);
        lifeController.onStart();
        //gameModel.nextGeneration();
        int nstep = 4;
        int delay = genTime * nstep;
        lifeController.onStart();
        CommUtils.delay(delay);
        lifeController.onStop();
        assertTrue( lifeController.numEpoch() == (nstep-1) );
        lifeController.onClear();
        outputDevice.close();
        assertTrue( lifeController.numEpoch() == 0 );
        
//        MainConwayLifeJava app = new MainConwayLifeJava();
//        app.configureTheSystemWitMockOutdev();
	}
}
