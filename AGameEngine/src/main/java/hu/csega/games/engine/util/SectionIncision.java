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
	public static boolean incisionSections(
			double x0, double y0, double x1, double y1,
			double x2, double y2, double x3, double y3) {

		double dx2 = x3-x2;
		double dy2 = y3-y2;

		double B = dx2*(y1-y0) - (x1-x0)*dy2;
		if(Math.abs(B) < EPSILON) // This eliminates the parallel case.
			return false;

		double A = (x0-x2)*dy2 - dx2*(y0-y2);
		if(B > 0)
			return (0 <= A) && (A <= B);
		else
			return (0 >= A) && (A >= B);
	}

	public static boolean incisionSectionAndSphere(
			double x0, double y0, double x1, double y1,
			double sx, double sy, double sr) {

		double dx = x1 - x0;
		double dy = y1 - y0;
		double dsx = x0 - sx;
		double dsy = y0 - sy;

		double a = dx*dx + dy*dy;
		double b = 2.0*(dsx*dx + dsy*dy);
		double c = dsx*dsx + dsy*dsy - sr*sr;

		double D = b*b - 4.0*a*c;

		if(Math.abs(D) < EPSILON)
			return -b <= 2.0*a;

		if(D < 0)
			return false;

		double rightSide = b+2*a;
		double rightSide2 = rightSide * rightSide;

		boolean ret = (rightSide < 0 && D >= rightSide2) || (rightSide > 0 && D <= rightSide2);
		return ret;
	}

	public static boolean incisionSpheres(
			double x0, double y0, double r0,
			double x1, double y1, double r1) {

		if(Math.abs(x1 - x0) < EPSILON && Math.abs(y1 - y0) < EPSILON)
			return false;

		double r02 = r0*r0;
		double r12 = r1*r1;
		double x02 = x0*x0;
		double x12 = x1*x1;
		double y02 = y0*y0;
		double y12 = y1*y1;

		double dx = x1 - x0;
		double dy = y1 - y0;
		double dx2 = dx*dx;
		double dy2 = dy*dy;

		double C = (r02 - r12 + x12 - x02 + y12 - y02) / 2.0;
		double E = dy*y0 - C;

		double a = dx2 + dy2;
		double b = 2.0*(dx*E - dy2*x0);
		double c = E*E + dy2*(x02 - r02);

		double D = b*b - 4.0*a*c;
		return D >= 0;
	}
}
