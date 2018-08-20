package hu.csega.games.engine.util;

import static hu.csega.games.engine.util.SectionIncision.incisionSections;
import static hu.csega.games.engine.util.SectionIncision.incisionSectionAndSphere;
import static hu.csega.games.engine.util.SectionIncision.incisionSpheres;
import static org.junit.Assert.*;

import org.junit.Test;

public class SectionIncisionTest {

	@Test
	public void testSections() {
		// Normal incision.
		assertTrue(incisionSections(12, 5, 12, 10, 0, 6, 20, 6));
		assertTrue(incisionSections(0, 0, 10, 10, 0, 4, 10, 9));
		assertFalse(incisionSections(0, 0, 10, 10, 0, 14, 10, 11));
		assertFalse(incisionSections(0, 0, 10, 10, 0, 14, 12, 9.8));

		assertTrue(incisionSections(12, 5, 12, 10, 10, 6, -10, 6));

		// Parallel cases.
		assertFalse(incisionSections(12, 5, 12, 10, 12, 3, 12, 8));
		assertFalse(incisionSections(12, 5, 12, 7, 12, 8, 12, 13));
		assertFalse(incisionSections(12, 5, 12, 7, 12, 12, 12, 8));
		assertFalse(incisionSections(12, 5, 12, 10, 10, 3, 10, 8));
		assertFalse(incisionSections(0, 10, 4, 10, 3, 10, 16, 10));
		assertFalse(incisionSections(0, 0, 10, 6, 0, 0, 20, 12));
		assertFalse(incisionSections(0, 0, 10, 6, 0, 0-1, 20, 12-1));
	}

	@Test
	public void testSectionAndShpere() {
		assertFalse(incisionSectionAndSphere(-10, 22, 10, 22, 0, 0, 20));
		assertTrue(incisionSectionAndSphere(-10, 20, 10, 20, 0, 0, 20));
		assertTrue(incisionSectionAndSphere(10, 18, -10, 18, 0, 0, 20));
	}

	@Test
	public void testAndShperes() {
		assertFalse(incisionSpheres(2, 2, 10, 2, 2, 20));
		assertTrue(incisionSpheres(0, 0, 10, 2, 2, 10));
		assertTrue(incisionSpheres(0, 0, 20, 20, 20, 20));
		assertFalse(incisionSpheres(0, 0, 20, 40, 40, 20));
	}

}
