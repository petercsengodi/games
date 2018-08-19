package hu.csega.games.engine.util;

public class SectionIncision {

	public static final double EPSILON = 0.00001;

	/**
	 * Checks if the two sections meet in one single point.
	 * That being said, if the sections are parallel, the response is always `false`
	 * (because there is either no incision or there are infinite number of incision points).
	 * <br/>
	 * This method is only good for checking if there is an incision; it doesn't calculate,
	 * where exactly the incision is.
	 */
	public static boolean incision(
			double x0, double y0, double x1, double y1,
			double x2, double y2, double x3, double y3) {

		double dx2 = x3-x2;
		double dy2 = y3-y2;

		double B = dx2*(y1-y0) - (x1-x0)*dy2;
		if(B <= 0) // This eliminates the parallel case.
			return false;

		double A = (x0-x2)*dy2 - dx2*(y0-y2);
		if(A < 0)
			return false;

		return A <= B;
	}

}
