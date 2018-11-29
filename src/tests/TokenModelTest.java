package tests;

import static org.junit.Assert.*;

import droptoken.TokenController;
import droptoken.TokenModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/** This class has tests to test the basic functionality of TokenModel class */
public final class TokenModelTest {
	@Rule public Timeout globalTimeout = Timeout.seconds(10); // efficiency

	private TokenController controller;
	private TokenModel model;

	@Before
	public void setUp() {
		model = new TokenModel();
		controller = new TokenController(model);
	}

	///////////////////////////////////////////////////////////////////////////////////////
	////  takeTurn
	///////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testTakeTurnPopulatesBoard() {
		assertEquals(controller.performPut("PUT 2"), "OK");
		assertEquals(model.getPlayerTurns().get(0), 2, 0);
	}

	@Test
	public void testTakeTurnOutOfBoundsPrintsError() {
		assertEquals(controller.performPut("PUT 5"), "ERROR");
	}

	@Test
	public void testTakeTurnOutOfBoundsDoesntPopulate() {
		assertEquals(controller.performPut("PUT 5"), "ERROR");
		assertTrue(model.getPlayerTurns().isEmpty());
	}

	@Test
	public void testWinDoesntAllowMorePuts() {
		finishGame(controller);
		assertEquals(controller.performPut("PUT " + 2), "ERROR");
		assertTrue(model.isGameWon());
		assertEquals(model.getPlayerTurns().size(), 7, 0);
	}

	@Test
	public void testDrawDoesntAllowMorePuts() {
		drawGame(controller);
		assertEquals(controller.performPut("PUT " + 2), "ERROR");
		assertTrue(model.isGameDrawn());
		assertEquals(model.getPlayerTurns().size(), 16, 0);
	}

	private void finishGame(TokenController controller) {
		for (int i = 1; i <= 4; i++) {
			controller.performPut("PUT " + i);
			controller.performPut("PUT " + i);
		}
	}

	private void drawGame(TokenController controller) {
		for (int j = 0; j < 2; j++) {
			for (int i = 3; i <= 4; i++) {
				controller.performPut("PUT " + i);
			}
			for (int i = 4; i >= 1; i--) {
				controller.performPut("PUT " + i);
			}
			for (int i = 1; i <= 2; i++) {
				controller.performPut("PUT " + i);
			}
		}
	}



}
