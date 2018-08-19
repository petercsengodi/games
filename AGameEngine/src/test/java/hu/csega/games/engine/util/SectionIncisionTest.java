package hu.csega.games.engine.util;

import static hu.csega.games.engine.util.SectionIncision.incision;
import static org.junit.Assert.*;

import org.junit.Test;

public class SectionIncisionTest {

	@Test
	public void test() {
		// Normal incision.
		assertTrue(incision(12, 5, 12, 10, 0, 6, 20, 6));
		assertTrue(incision(0, 0, 10, 10, 0, 4, 10, 9));
		assertFalse(incision(0, 0, 10, 10, 0, 14, 10, 11));
		assertFalse(incision(0, 0, 10, 10, 0, 14, 12, 9.8));

		// Parallel cases.
		assertFalse(incision(12, 5, 12, 10, 12, 3, 12, 8));
		assertFalse(incision(12, 5, 12, 7, 12, 8, 12, 13));
		assertFalse(incision(12, 5, 12, 7, 12, 12, 12, 8));
		assertFalse(incision(12, 5, 12, 10, 10, 3, 10, 8));
		assertFalse(incision(0, 10, 4, 10, 3, 10, 16, 10));
		assertFalse(incision(0, 0, 10, 6, 0, 0, 20, 12));
		assertFalse(incision(0, 0, 10, 6, 0, 0-1, 20, 12-1));
	}

}
